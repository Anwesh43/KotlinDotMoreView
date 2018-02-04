package ui.anwesome.com.kotlindotmoreview

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import android.widget.Toast
import ui.anwesome.com.dotmoreview.DotMoreView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val view = DotMoreView.create(this)
        view.addDotMoreListener({
            Toast.makeText(this,"expanded",Toast.LENGTH_SHORT).show()
        },{
            Toast.makeText(this,"collapsed",Toast.LENGTH_SHORT).show()
        })
        fullScreen()
    }
}
fun MainActivity.fullScreen() {
    supportActionBar?.hide()
    window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
}
