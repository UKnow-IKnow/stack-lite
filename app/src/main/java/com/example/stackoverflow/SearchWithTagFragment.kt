package com.example.stackoverflow

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isGone
import androidx.fragment.app.activityViewModels
import com.example.stackoverflow.adapters.QuestionAdapter
import com.example.stackoverflow.databinding.FragmentSearchWithTagBinding
import com.example.stackoverflow.model.Question
import com.example.stackoverflow.utils.Resource
import com.example.stackoverflow.utils.openQuestion
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_search_with_tag.*

@AndroidEntryPoint
class SearchWithTagFragment : BottomSheetDialogFragment() {

    private var _binding: FragmentSearchWithTagBinding? = null
    private val binding get() = _binding
    private val viewModel by activityViewModels<QuestionViewModel>()
    private lateinit var tagAdapter: QuestionAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentSearchWithTagBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpRecyclerView()

        close_tags.setOnClickListener {
            dismiss()
            tagAdapter.submitList(null)
        }

        use_tags_button.setOnClickListener {
            tags_input_text.editText?.text?.toString()?.trim()?.let { tags ->
                tags_input_text.error = null
                viewModel.searchWithFilter(tags)
            } ?: kotlin.run { tags_input_text.error = "Cannot be empty" }
        }

        viewModel.taggedQuestions.observe(viewLifecycleOwner) { resource ->
            when (resource) {

                is Resource.Success -> binding?.apply {
                    useTagsButton.text = "Use Tags"
                    searchingTagsProgressbar.isGone = true

                    if (resource.data!!.items.isNotEmpty()) {
                        tagAdapter.submitList(resource.data.items)
                        questionsAfterTagRv.isGone = false
                        nullSearchTags.isGone = true
                    } else {
                        nullSearchTags.isGone = false
                        questionsAfterTagRv.isGone = true
                    }
                }

                is Resource.Error -> binding?.apply {
                    searchingTagsProgressbar.isGone = true
                    errorNetworkTags.isGone = false
                    useTagsButton.text = "Use Tags"
                    Toast.makeText(requireContext(), "Error!!!", Toast.LENGTH_SHORT).show()
                }

                is Resource.Loading -> binding?.apply {
                    searchingTagsProgressbar.isGone = false
                    errorNetworkTags.isGone = true
                    useTagsButton.text = "Loading"
                    nullSearchTags.isGone = true
                    questionsAfterTagRv.isGone = true
                }
            }
        }
    }

    private fun setUpRecyclerView() {
        binding?.apply {
            questionsAfterTagRv.setHasFixedSize(true)
            tagAdapter = QuestionAdapter(object : QuestionAdapter.OnClickListener {
                override fun openQuestion(questionItem: Question) {
                    openQuestion(questionItem, requireContext())
                }
            }, requireContext())
            questionsAfterTagRv.adapter = tagAdapter
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}