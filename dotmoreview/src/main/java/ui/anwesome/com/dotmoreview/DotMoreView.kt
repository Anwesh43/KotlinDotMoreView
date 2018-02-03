package ui.anwesome.com.dotmoreview

/**
 * Created by anweshmishra on 04/02/18.
 */
import android.view.*
import android.content.*
import android.graphics.*
import java.util.concurrent.ConcurrentLinkedQueue

class DotMoreView(ctx:Context,var n:Int = 5):View(ctx) {
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
        val dotState = DotState()
        fun draw(canvas:Canvas, paint:Paint, gap:Float,y:Float) {
            canvas.drawCircle((i-1)*gap+gap*dotState.scale,y,gap/5,paint)
        }
        fun update(stopcb:(Float)->Unit) {
            dotState.update(stopcb)
        }
        fun startUpdating(startcb:()->Unit) {
            dotState.startUpdating(startcb)
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
    data class DotContainerState(var n:Int,var j:Int = 0,var jDir:Int = 1) {
        fun incrementCounter() {
            j += jDir
            if(j == n || j == -1) {
                jDir *= -1
                j += jDir
            }
        }
        fun executeCb(cb:(Int)->Unit) {
            cb(j)
        }
    }
    data class DotContainer(var w:Float,var h:Float,var n:Int) {
        val dots:ConcurrentLinkedQueue<Dot> = ConcurrentLinkedQueue()
        val state = DotContainerState(n)
        init {
            for(i in 0..n) {
                dots.add(Dot(i+1))
            }
        }
        fun update(stopcb:(Float)->Unit) {
            state.executeCb {
                dots?.at(it)?.update(stopcb)
            }
        }
        fun startUpdating(startcb:()->Unit) {
            state.executeCb {
                dots?.at(it)?.startUpdating(startcb)
            }
        }
        fun draw(canvas:Canvas,paint:Paint) {
            if(n > 0) {
                val gap = (w/2)/(n+1)
                paint.color = Color.parseColor("#4527A0")
                canvas.save()
                canvas.translate(w/2,h/2)
                for(p in 0..1) {
                    canvas.save()
                    canvas.scale(p*2-1f,1f)
                    state.executeCb { j ->
                        for (i in 0..j) {
                            dots.at(i)?.draw(canvas, paint, gap, 0f)
                        }
                    }
                    canvas.restore()
                }
                canvas.restore()
            }
        }
    }
    data class Animator(var view:View,var animated:Boolean = false) {
        fun animate(updatecb:()->Unit) {
            if(animated) {
                updatecb()
                try {
                    Thread.sleep(50)
                    view.invalidate()
                } catch(ex: Exception) {

                }
            }
        }
        fun start() {
            if(!animated) {
                animated = true
                view.postInvalidate()
            }
        }
        fun stop() {
            if(animated) {
                animated = false
            }
        }
    }
}
fun ConcurrentLinkedQueue<DotMoreView.Dot>.at(i:Int):DotMoreView.Dot? {
    var j = 0
    forEach {
        if(j == i) {
            return it
        }
        j++
    }
    return null
}