package ui.anwesome.com.kotlindotmoreview

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import ui.anwesome.com.dotmoreview.DotMoreView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DotMoreView.create(this)
    }
}
