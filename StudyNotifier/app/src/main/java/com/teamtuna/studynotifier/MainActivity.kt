package com.teamtuna.studynotifier

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.teamtuna.studynotifier.util.milliSecondsToString
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.ticker
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import kotlin.coroutines.CoroutineContext

class MainActivity : AppCompatActivity(), CoroutineScope {

    private lateinit var job: Job

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    private var isTimerRunning = false
    private var runningTime = 0L
    private lateinit var tickerChannel: ReceiveChannel<Unit>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        job = Job()

        circleImageView.setOnClickListener {
            if (!isTimerRunning) {
                circleImageView.setImageResource(R.drawable.ic_stop_black_24dp)
                isTimerRunning = true
                tickerChannel = ticker(delayMillis = 1_000, initialDelayMillis = 1_000)
                launch {
                    for (event in tickerChannel) {
                        runningTime += 1_000L
                        textTimer.text = milliSecondsToString(runningTime)
                    }
                }
            } else {
                circleImageView.setImageResource(R.drawable.ic_play_arrow_black_24dp)
                isTimerRunning = false
                tickerChannel.cancel()
            }
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    companion object {
        val TIME_PATTERN = SimpleDateFormat( "HH:mm:ss")
    }
}
