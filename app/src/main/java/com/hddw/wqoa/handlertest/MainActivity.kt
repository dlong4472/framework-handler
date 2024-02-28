package com.hddw.wqoa.handlertest

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.os.MessageQueue
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.hddw.wqoa.handlertest.ui.theme.HandlerTestTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HandlerTestTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Greeting("Android")
                }
            }
        }
        Handler(Looper.getMainLooper()).post {
//            println("HandlerTest: ${Thread.currentThread().name}")
        }

        Thread {
            Looper.prepare()
            val handler = Looper.myLooper()?.let {
                object : Handler(it) {
                    override fun handleMessage(msg: Message) {
                        println("HandlerTest: ${Thread.currentThread().name}")
                    }
                }
            }
            handler?.sendMessage(Message.obtain())
            Looper.loop()
        }.start()

        Looper.getMainLooper().queue.addIdleHandler {
            println("HandlerTest---addIdleHandler: ${Thread.currentThread().name}")
            false
        }
    }
}

val h = Handler(Looper.getMainLooper()) {
    println("HandlerTest: ${Thread.currentThread().name}")

    true

}

fun test() {
    h.sendEmptyMessage(1)
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    HandlerTestTheme {
        Greeting("Android")
    }
}