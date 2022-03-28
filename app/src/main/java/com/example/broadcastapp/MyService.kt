package com.example.broadcastapp

import android.app.Service
import android.content.Intent
import android.os.IBinder

class MyService : Service() {
    private var channel = ""
    private lateinit var thread: Thread

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        //解析Intent取得字串訊息
        intent?.extras?.let {
            channel = it.getString("channel", "")
        }

        broadcast(
            when(channel) {
                "music" -> "Welcome to my music channel"
                "news" -> "Welcome to my news channel"
                "sport" -> "Welcome to my sport channel"
                else -> "channel is error."
            }
        )

        // 若 thread 被初始化過且正在運行，則中斷它
        if(::thread.isInitialized && thread.isAlive)
            thread.interrupt()

        thread = Thread {
            try {
                Thread.sleep(3000)
                broadcast(
                    when(channel) {
                        "music" -> "即將播放本月TOP10音樂"
                        "news" -> "為你提供獨家新聞"
                        "sport" -> "即將播放本週NBA賽事"
                        else -> "存物交接"
                    }
                )
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }
        }

        thread.start()
        return START_STICKY

    }

    private fun broadcast(msg: String) = sendBroadcast(Intent(channel).putExtra("msg", msg))

    override fun onBind(intent: Intent): IBinder? = null
}