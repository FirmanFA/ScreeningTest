package com.screeningtest

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.opengl.Visibility
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.screeningtest.data.RetrofitService
import com.screeningtest.data.UserRepository
import com.screeningtest.data.UserViewModel
import com.screeningtest.data.UserViewModelFactory

class ThirdActivity : AppCompatActivity() {

    lateinit var sharedPreferences: SharedPreferences
    private val retrofitService = RetrofitService.getInstance()
    lateinit var adapter: UserAdapter
    lateinit var viewModel: UserViewModel
    private var totalPage = 1
    private var totalData = 1
    private var perPage = 10
    private var currentPage = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_third)

        sharedPreferences = getSharedPreferences("PREF", Context.MODE_PRIVATE)

        val rvUser = findViewById<RecyclerView>(R.id.rvUser)
        val swipeUser = findViewById<SwipeRefreshLayout>(R.id.swipeUser)
        val tvEmpty = findViewById<TextView>(R.id.tvEmpty)
        val btnBack = findViewById<Button>(R.id.btnBack)

        btnBack.setOnClickListener {
            onBackPressed()
            finish()
        }

        swipeUser.setOnRefreshListener {
            adapter.clear()
            currentPage = 1
            viewModel.getAllUsers(currentPage,perPage)

        }

        adapter = UserAdapter {
            setShared(it.firstName+" "+it.lastName)
            Toast.makeText(this, "Username Selected", Toast.LENGTH_SHORT).show()
        }

        viewModel = ViewModelProvider(this,UserViewModelFactory(UserRepository
            (retrofitService)))[UserViewModel::class.java]

        viewModel.user.observe(this, {
            tvEmpty.visibility = View.GONE
            adapter.setUsers(it.data)
            totalPage = it.totalPages!!
            perPage = it.perPage!!
            currentPage = it.page!!
            totalData = it.total!!
            swipeUser.isRefreshing = false
        })

        viewModel.getAllUsers(currentPage, perPage)

        val layoutManager = LinearLayoutManager(this)
        rvUser.layoutManager = layoutManager
        rvUser.setHasFixedSize(true)
        rvUser.adapter = adapter

        rvUser.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {

                val visibleItemCount = layoutManager.childCount
                val pastVisibleItem = layoutManager.findFirstVisibleItemPosition()
                val total = adapter.itemCount
                if (currentPage < totalPage){
                    if (visibleItemCount + pastVisibleItem>=total){
                        currentPage++
                        viewModel.getAllUsers(currentPage,perPage)
                    }
                }
                super.onScrolled(recyclerView, dx, dy)
            }
        })
    }

    private fun setShared(username:String){
        val editor = sharedPreferences.edit()
        editor.putString("USER_KEY", username)
        editor.apply()
    }
}