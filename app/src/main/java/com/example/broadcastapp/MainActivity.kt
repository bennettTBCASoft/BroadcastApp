package com.example.broadcastapp

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    // 建立BroadcastReceiver 物件
    private val receiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent) {
            intent.extras?.let {
                val tv_msg = findViewById<TextView>(R.id.textView)
                tv_msg.text = "${it.getString("msg")}"
            }
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<Button>(R.id.btn_music).setOnClickListener {
            register("music")
        }

        findViewById<Button>(R.id.btn_news).setOnClickListener {
            register("news")
        }

        findViewById<Button>(R.id.btn_sports).setOnClickListener {
            register("sport")
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(receiver)
    }

    fun register(channel: String) {
        // 建立 IntentFilter 物件來指定接收的頻道，並註冊Receiver
        val intentFilter = IntentFilter(channel)
        registerReceiver(receiver, intentFilter)
        // 建立 Intent物件，使其夾帶頻道資料，並啟動MyService服務
        val intent = Intent(this, MyService::class.java)
        startService(intent.putExtra("channel", channel))
    }
}