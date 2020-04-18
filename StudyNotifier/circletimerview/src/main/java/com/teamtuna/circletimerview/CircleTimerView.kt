package com.teamtuna.circletimerview

import android.content.Context
import android.content.res.Resources
import android.graphics.*
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import android.widget.Toast
import kotlinx.android.synthetic.main.circle_timer_view.view.*


class CircleTimerView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    private var mWidth = 0
    private var mHeight = 0
    private var angle = 0F
    private val mBackgroundColor = Paint(Paint.ANTI_ALIAS_FLAG)
    private val mLineColor = Paint(Paint.ANTI_ALIAS_FLAG)
    private val mLineBackgroundColor = Paint(Paint.ANTI_ALIAS_FLAG)
    private val mLineButtonEffect = Paint(Paint.ANTI_ALIAS_FLAG)

    private var count = 1

    init {
        setWillNotDraw(false)
        LayoutInflater.from(context).inflate(R.layout.circle_timer_view, this)

        // 배경
        mBackgroundColor.color = Color.RED

        // 라인 배경
        mLineBackgroundColor.color = Color.GRAY

        // 타이머 라인
        mLineColor.apply {
            color = Color.YELLOW
            strokeWidth = dpToPx(16).toFloat()
            strokeCap = Paint.Cap.ROUND
            style = Paint.Style.STROKE
        }

        // 타이머 원 블러
        mLineButtonEffect.apply {
            maskFilter = BlurMaskFilter(
                (2 * 16 / 3).toFloat(),
                BlurMaskFilter.Blur.NORMAL
            )
            color = Color.YELLOW
            strokeWidth = 16F
            style = Paint.Style.FILL
        }

        play.setOnClickListener {
            play.setColorFilter(Color.parseColor("#FFFFFF"))
            play.setImageResource(R.drawable.ic_stop_black_24dp)
            Toast.makeText(context, "Start!!", Toast.LENGTH_SHORT).show()
            play.post {
                count = 1
                playWork()
            }
        }
    }

    private fun playWork() {
        val run1 = {
            angle = count++.toFloat()
            invalidate()
            if (count <= 360) {
                playWork()
            } else {
                play.setColorFilter(Color.parseColor("#FFFFFF"))
                play.setImageResource(R.drawable.ic_play_arrow_black_24dp)
                count = 1
                angle = 0F
                invalidate()
            }
        }
        play.postDelayed(run1, 30)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        mWidth = w
        mHeight = h
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        val centerX = (mWidth / 2).toFloat()
        val centerY = (mHeight / 2).toFloat()
        val radius = centerX - dpToPx(32)
        val radiusForLineBackground = centerX - dpToPx(16)
        val radiusForLine = centerX - dpToPx(24)

        // 라인 배경 그리기
        canvas?.drawCircle(centerX, centerY, radiusForLineBackground, mLineBackgroundColor)

        // 배경 그리기
        canvas?.drawCircle(centerX, centerY, radius, mBackgroundColor)

        val lineRectF = RectF(
            centerX - radiusForLine,
            centerY - radiusForLine,
            centerX + radiusForLine,
            centerY + radiusForLine
        )
        // 라인 그리기
        canvas?.drawArc(lineRectF, 270F, angle, false, mLineColor)
    }

    private fun dpToPx(sizeDp: Int): Int {
        val displayMetrics = Resources.getSystem().displayMetrics
        return (sizeDp * displayMetrics.density).toInt()
    }
}