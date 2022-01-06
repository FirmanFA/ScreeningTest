package com.screeningtest.data

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserViewModel constructor(private val userRepository: UserRepository)  : ViewModel()   {
    val user = MutableLiveData<User>()
    val error = MutableLiveData<String>()

    fun getAllUsers(page:Int, perPage:Int){
        userRepository.getALlUsers(page,perPage).enqueue(object : Callback<User> {
            override fun onResponse(call: Call<User>, response: Response<User>) {
                user.postValue(response.body())
            }

            override fun onFailure(call: Call<User>, t: Throwable) {
                error.postValue(t.localizedMessage)
            }


        })
    }


}