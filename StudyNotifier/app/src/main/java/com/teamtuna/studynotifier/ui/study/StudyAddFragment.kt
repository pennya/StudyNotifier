package com.teamtuna.studynotifier.ui.study

import android.os.Bundle
import android.view.*
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.teamtuna.studynotifier.R
import com.teamtuna.studynotifier.base.BaseCoroutineFragment
import com.teamtuna.studynotifier.ui.data.UiResult
import com.teamtuna.studynotifier.util.Toast
import com.teamtuna.studynotifier.util.afterTextChanged
import com.teamtuna.studynotifier.util.milliSecondsToString
import com.teamtuna.studynotifier.viewmodel.PushViewModel
import kotlinx.android.synthetic.main.fragment_study_add.*


class StudyAddFragment : BaseCoroutineFragment() {

    private lateinit var studyViewModel: StudyViewModel
    private lateinit var pushViewModel: PushViewModel

    private var runningTime: Long = 0L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        studyViewModel = ViewModelProviders.of(this).get(StudyViewModel::class.java)
        pushViewModel = ViewModelProviders.of(this).get(PushViewModel::class.java)

        runningTime = arguments?.getLong("runningTime") ?: 0L
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_study_add, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)

        studyTime.text = milliSecondsToString(runningTime)
        studyTitleEdit.afterTextChanged {
            studyViewModel.studyDataChanged(studyTitleEdit.text.toString())
        }

        observeUi()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_study_add, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_save -> {
                studyViewModel.addStudy(
                    studyTitleEdit.text.toString(),
                    studyDescriptionEdit.text.toString(),
                    runningTime
                )
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun observeUi() {
        studyViewModel.studyFormState.observe(viewLifecycleOwner, Observer {
            if (it.titleError.isNotEmpty())
                studyTitleEdit.error = it.titleError
        })

        studyViewModel.study.observe(viewLifecycleOwner, Observer {
            when (it) {
                is UiResult.Success -> {
                    pushViewModel.addPushMessage("스터디 종료!")
                    findNavController().navigate(R.id.action_StudyAddFragment_to_TimerFragment)
                }
                is UiResult.Error -> {
                    it.exception?.printStackTrace()
                    context?.Toast("스터디 생성 실패")
                }
            }
        })

        studyViewModel.dataLoading.observe(viewLifecycleOwner, Observer { isDataLoading ->
            if (isDataLoading) {
                showProgress()
            } else {
                dismissProgress()
            }
        })
    }
}
