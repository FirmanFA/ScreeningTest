package com.screeningtest

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.screeningtest.data.User
import com.screeningtest.data.UserList
import com.screeningtest.data.UserViewModel

class UserAdapter(private val clickListener: (UserList) -> Unit): RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    var userList = mutableListOf<UserList>()

    fun setUsers(users: List<UserList>?){
        this.userList.addAll(users!!)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.user_item, parent, false)
        return UserViewHolder(view)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val user = userList[position]
        holder.bind(user, clickListener)
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    fun clear() {
        userList.clear()
        notifyDataSetChanged()
    }

    class UserViewHolder(view:View):RecyclerView.ViewHolder(view){
        fun bind(user:UserList, clickListener:(UserList) -> Unit){

            val tvItemName = itemView.findViewById<TextView>(R.id.tvItemName)
            val tvItemEmail = itemView.findViewById<TextView>(R.id.tvItemEmail)
            val ivAvatar = itemView.findViewById<ImageView>(R.id.ivAvatar)
            tvItemName.text = user.firstName + " " + user.lastName
            tvItemEmail.text = user.email
            Glide.with(itemView.context)
                .load(user.avatar)
                .circleCrop()
                .into(ivAvatar)


            itemView.setOnClickListener {
                clickListener(user)
            }

        }
    }


}