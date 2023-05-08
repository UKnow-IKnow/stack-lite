package com.example.stackoverflow.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.stackoverflow.QuestionViewModel
import com.example.stackoverflow.R
import com.example.stackoverflow.adapters.QuestionAdapter
import com.example.stackoverflow.broadcast.NetworkError
import com.example.stackoverflow.model.Question
import com.example.stackoverflow.utils.Resource
import com.example.stackoverflow.utils.openQuestion
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_question.*
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay

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

        NetworkError(requireContext()).observe(viewLifecycleOwner){
            if (!it){
                //No Internet connection
                findNavController().navigate(R.id.action_questionFragment_to_noInternetActivity)
                findNavController().navigateUp()
            }else{

            }
        }

        setUpRecyclerView()
        clearQuery.setOnClickListener {
            searchET.text.clear()
            hideKeyboard()
        }

        filterIcon.setOnClickListener {
            findNavController().navigate(R.id.action_questionFragment_to_searchWithTagFragment)
        }

        viewModel.questions.observe(viewLifecycleOwner) { resource ->
            when (resource) {
                is Resource.Success -> {
                    shimmerEffect.isGone = true
                    shimmerEffect.stopShimmer()
                    questions_rv.isGone = false
                    questionsAdapter.submitList(resource.data!!.items)
                }

                is Resource.Error -> {
                    Toast.makeText(requireContext(), resource.message, Toast.LENGTH_SHORT).show()

                }

                is Resource.Loading -> {
                    Toast.makeText(requireContext(), "Loading", Toast.LENGTH_SHORT).show()
                    shimmerEffect.isGone = false
                    shimmerEffect.stopShimmer()
                }
            }
        }

        viewModel.searchedQuestions.observe(viewLifecycleOwner) { resource ->
            when (resource) {
                is Resource.Success -> {
                    progress_bar.isGone = true
                    if (resource.data!!.items.isNotEmpty()) {
                        searchedQuestionsAdapter.submitList(resource.data.items)
                        questions_searched_rv.isGone = false
                        null_search.isGone = true
                    } else {
                        null_search.isGone = false
                        questions_searched_rv.isGone = true
                    }
                }
                else -> {}
            }
        }

        searchET?.doOnTextChanged { text, _, _, _ ->
            text?.let {
                if (it.trim().isNotBlank() && it.trim().isNotEmpty()) {
                    clearQuery.isGone = false
                    searchIcon.isVisible = false
                    questions_rv.isGone = true

                    search_key_text.text = "Searched questions"
                    progress_bar.isGone = false
                    null_search.isGone = true
                    error_network.isGone = true
                    questions_searched_rv.isGone = true
                    searchQuestion(it.trim().toString())
                } else {
                    clearQuery.isGone = true
                    searchIcon.isVisible = true
                    search_key_text.text = "Questions"
                    questions_rv.isGone = false
                    questions_searched_rv.isGone = true
                }
            }
        }

    }


    private fun setUpRecyclerView() {

        questions_rv.setHasFixedSize(true)
        questionsAdapter = QuestionAdapter(object : QuestionAdapter.OnClickListener {
            override fun openQuestion(questionItem: Question) {
                openQuestion(questionItem, requireContext())
            }
        }, requireContext())
        questions_rv.adapter = questionsAdapter

        questions_searched_rv.setHasFixedSize(true)
        searchedQuestionsAdapter = QuestionAdapter(object : QuestionAdapter.OnClickListener {
            override fun openQuestion(questionItem: Question) {
                openQuestion(questionItem, requireContext())
            }
        }, requireContext())
        questions_searched_rv.adapter = searchedQuestionsAdapter

    }

    private fun hideKeyboard() {
        val inputMethodManager: InputMethodManager =
            requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(requireView().windowToken, 0)
    }

    private fun searchQuestion(query: String) {
        job?.cancel()
        job = viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            delay(2000)
            viewModel.searchQuestions(query)
        }
    }
}