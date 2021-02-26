package com.test.apollocache

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.alabs.testCache.graphQL.LaunchListQuery
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class MainActivity : AppCompatActivity(R.layout.activity_main), CoroutineScope {

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        textViewFlow.setOnClickListener {
            textViewFlow.text = "Starting Request..."

            launch {
                try {
                    val startTime = System.currentTimeMillis()
                    createApollo(applicationContext)
                        .runApiCall {
                            LaunchListQuery()
                        }.collect {
                            val resultTime = System.currentTimeMillis() - startTime
                            textViewFlow.text = "Request took $resultTime ms\nClick again to fetch data"
                        }
                } catch (e: Exception) {
                    e.printStackTrace()
                    textViewFlow.text = "Request error"
                }
            }
        }
    }
}