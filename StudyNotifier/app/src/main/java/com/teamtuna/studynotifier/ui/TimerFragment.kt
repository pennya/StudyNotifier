package com.teamtuna.studynotifier.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.teamtuna.studynotifier.R
import com.teamtuna.studynotifier.base.BaseCoroutineFragment
import com.teamtuna.studynotifier.util.milliSecondsToString
import com.teamtuna.studynotifier.viewmodel.PushViewModel
import kotlinx.android.synthetic.main.fragment_timer.*
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.ticker
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat

class TimerFragment : BaseCoroutineFragment() {

    private var isTimerRunning = false
    private var runningTime = 0L
    private lateinit var tickerChannel: ReceiveChannel<Unit>
    private lateinit var pushViewModel: PushViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        pushViewModel = ViewModelProviders.of(this).get(PushViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_timer, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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

                pushViewModel.addPushMessage("스터디 시작!")
            } else {
                circleImageView.setImageResource(R.drawable.ic_play)
                isTimerRunning = false
                tickerChannel.cancel()
                findNavController().navigate(R.id.action_TimerFragment_to_StudyAddFragment)
            }
        }
    }

    companion object {
        val TIME_PATTERN = SimpleDateFormat( "HH:mm:ss")
    }
}
