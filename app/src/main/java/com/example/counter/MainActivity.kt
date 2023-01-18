package com.example.counter

import androidx.appcompat.app.AppCompatActivity
import android.widget.TextView
import android.os.Bundle
import android.os.Handler
import com.example.counter.R
import com.example.counter.MainActivity.MyCounter
import android.os.Looper
import android.os.Message
import android.view.View
import android.widget.Button
import java.lang.Exception

class MainActivity : AppCompatActivity(), View.OnClickListener {
    var buttonStart: Button? = null
    var buttonStop: Button? = null
    var counterValue: TextView? = null
    var counter = 0
    var running = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        buttonStart = findViewById<View>(R.id.btn_start) as Button
        buttonStart!!.setOnClickListener(this)
        buttonStop = findViewById<View>(R.id.btn_stop) as Button
        buttonStop!!.setOnClickListener(this)
        counterValue = findViewById<View>(R.id.txt_value) as TextView
    }

    override fun onClick(v: View) {
        if (v == buttonStart) {
            counterStart()
        } else if (v == buttonStop) {
            counterStop()
        }
    }

    private fun counterStop() {
        running = false
        //buttonStart.setEnabled(true);
        //buttonStop.setEnabled(false);
    }

    private fun counterStart() {
        counter = 0
        running = true
        println("Start ->" + Thread.currentThread().name)
        MyCounter().start()
        //buttonStart.setEnabled(false);
        //buttonStop.setEnabled(true);
    }

    var handler: Handler = object : Handler(Looper.getMainLooper()) {
        override fun handleMessage(mes: Message) {
            counterValue!!.text = mes.what.toString()
        }
    }

    internal inner class MyCounter : Thread() {
        override fun run() {
            println("MyCounter ->" + currentThread().name)
            while (running) {
                counter++
                handler.sendEmptyMessage(counter)
                try {
                    sleep(1000)
                } catch (e: Exception) {
                }
            }
        }
    }
}