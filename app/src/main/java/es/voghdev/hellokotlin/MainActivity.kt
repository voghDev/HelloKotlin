package es.voghdev.hellokotlin

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.DisplayMetrics
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.toast
import org.jetbrains.anko.uiThread

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button1.setOnClickListener {
            toast("You wrote: ${editText1.text}")

            doAsync() {
                Thread.sleep(2500)
                uiThread {
                    val width: Int = screenWidth()
                    val height: Int = screenHeight()

                    toast("Screen size is: $width x $height")
                }
            }
        }

    }

    fun Context.screenWidth(): Int {
        val displayMetrics : DisplayMetrics = DisplayMetrics()
        (this as Activity).windowManager.defaultDisplay.getMetrics(displayMetrics)
        return displayMetrics.widthPixels
    }

    fun Context.screenHeight(): Int {
        val displayMetrics : DisplayMetrics = DisplayMetrics()
        (this as Activity).windowManager.defaultDisplay.getMetrics(displayMetrics)
        return displayMetrics.heightPixels
    }
}
