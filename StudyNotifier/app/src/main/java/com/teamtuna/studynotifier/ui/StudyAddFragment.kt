package com.teamtuna.studynotifier.ui

import android.os.Bundle
import android.view.*
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.teamtuna.studynotifier.R
import com.teamtuna.studynotifier.base.BaseCoroutineFragment
import com.teamtuna.studynotifier.viewmodel.PushViewModel
import com.teamtuna.studynotifier.viewmodel.StudyAddViewModel


class StudyAddFragment : BaseCoroutineFragment() {

    private lateinit var studyAddViewModel: StudyAddViewModel
    private lateinit var pushViewModel: PushViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        studyAddViewModel = ViewModelProviders.of(this).get(StudyAddViewModel::class.java)
        pushViewModel = ViewModelProviders.of(this).get(PushViewModel::class.java)
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

        observeUi()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_study_add, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_save -> {
                studyAddViewModel.addStudy()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun observeUi() {
        studyAddViewModel.study.observe(viewLifecycleOwner, Observer {
            pushViewModel.addPushMessage("스터디 종료!")
            findNavController().navigate(R.id.action_StudyAddFragment_to_TimerFragment)
        })

        studyAddViewModel.dataLoading.observe(viewLifecycleOwner, Observer { isDataLoading ->
            if (isDataLoading) {
                showProgress()
            } else {
                dismissProgress()
            }
        })
    }
}
