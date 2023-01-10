package com.example.stackoverflow

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.stackoverflow.adapters.QuestionAdapter
import com.example.stackoverflow.databinding.FragmentSearchWithTagBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


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
    }

    private fun setUpRecyclerView() {

    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}