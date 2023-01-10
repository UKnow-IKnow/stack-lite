package com.example.stackoverflow

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.stackoverflow.adapters.QuestionAdapter
import com.example.stackoverflow.model.Question
import com.example.stackoverflow.utils.openQuestion
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_question.*
import kotlinx.coroutines.Job

@AndroidEntryPoint
class QuestionFragment : Fragment() {

    private val viewModel: QuestionViewModel by viewModels()
    private var job: Job? = null
    private lateinit var questionsAdapter: QuestionAdapter
    private lateinit var searchedQuestionsAdapter: QuestionAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_question, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpRecyclerView()
    }

    private fun setUpRecyclerView() {

        questions_trending_rv.setHasFixedSize(true)
        questionsAdapter = QuestionAdapter(object : QuestionAdapter.OnClickListener {
            override fun openQuestion(questionItem: Question) {
                openQuestion(questionItem, requireContext())
            }
        }, requireContext())
        questions_trending_rv.adapter = questionsAdapter

//        questions_searched_rv.setHasFixedSize(true)
//        searchedQuestionsAdapter = QuestionAdapter(object : QuestionAdapter.OnClickListener {
//            override fun openQuestion(questionItem: Question) {
//                openQuestion(questionItem, requireContext())
//            }
//        }, requireContext())
//        questions_searched_rv.adapter = searchedQuestionsAdapter


    }

}