package com.screeningtest.data

import androidx.lifecycle.LiveData

class UserRepository constructor(private val retrofitService: RetrofitService)  {
    fun getALlUsers(page:Int, perPage:Int) = retrofitService.getAllUsers(page,perPage)
}