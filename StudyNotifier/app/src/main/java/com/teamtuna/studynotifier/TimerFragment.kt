package com.teamtuna.studynotifier

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.teamtuna.studynotifier.util.milliSecondsToString
import kotlinx.android.synthetic.main.fragment_timer.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.ticker
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import kotlin.coroutines.CoroutineContext

class TimerFragment : Fragment(), CoroutineScope {

    private lateinit var job: Job

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    private var isTimerRunning = false
    private var runningTime = 0L
    private lateinit var tickerChannel: ReceiveChannel<Unit>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_timer, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        job = Job()

        circleImageView.setOnClickListener {
            if (!isTimerRunning) {
                circleImageView.setImageResource(R.drawable.ic_stop)
                isTimerRunning = true
                tickerChannel = ticker(delayMillis = 1_000, initialDelayMillis = 1_000)
                launch {
                    for (event in tickerChannel) {
                        runningTime += 1_000L
                        textTimer.text = milliSecondsToString(runningTime)
                    }
                }
                // push viewModel
            } else {
                circleImageView.setImageResource(R.drawable.ic_play)
                isTimerRunning = false
                tickerChannel.cancel()
                findNavController().navigate(R.id.action_TimerFragment_to_StudyAddFragment)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        job.cancel()
    }

    companion object {
        val TIME_PATTERN = SimpleDateFormat( "HH:mm:ss")
    }
}
