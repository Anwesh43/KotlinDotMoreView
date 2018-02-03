package ui.anwesome.com.dotmoreview

/**
 * Created by anweshmishra on 04/02/18.
 */
import android.view.*
import android.content.*
import android.graphics.*
class DotMoreView(ctx:Context):View(ctx) {
    val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    override fun onTouchEvent(event:MotionEvent):Boolean {
        when(event.action) {
            MotionEvent.ACTION_DOWN -> {

            }
        }
        return true
    }
    override fun onDraw(canvas:Canvas) {

    }
    data class Dot(var i:Int) {
        fun draw(canvas:Canvas, paint:Paint, gap:Float,y:Float) {
            canvas.drawCircle((i-1)*gap+gap,y,gap/5,paint)
        }
        fun update(stopcb:(Float)->Unit) {

        }
        fun startUpdating(startcb:(Float)->Unit) {

        }
    }
    data class DotState(var scale:Float = 0f,var dir:Float = 0f,var prevScale:Float = 0f) {
        fun update(stopcb:(Float)->Unit) {
            scale += 0.1f*dir
            if(Math.abs(scale - prevScale) > 1) {
                scale = prevScale + dir
                this.dir = 0f
                prevScale = scale
                stopcb(scale)
            }
        }
        fun startUpdating(startcb:()->Unit) {
            if(dir == 0f) {
                dir = 1 - 2*scale
                startcb()
            }
        }
    }
}