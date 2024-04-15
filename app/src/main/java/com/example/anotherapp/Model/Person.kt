package com.example.anotherapp.Model

import android.content.Context
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class Person(var id:Int, var name: String, var age: Int, var password: String,var description :String,var link:String,var imageUrl:String) {
}