package com.example.stackoverflow.adapters


import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.stackoverflow.databinding.QuestionItemBinding
import com.example.stackoverflow.model.Question
import com.example.stackoverflow.utils.getTime


class QuestionAdapter(
    private val clickListener: OnClickListener, private val context: Context
) : ListAdapter<Question, QuestionAdapter.ItemViewHolder>(DiffUtilCallBack()) {

    inner class ItemViewHolder(private val binding: QuestionItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(questionItem: Question) {
            binding.apply {
                questionItemText.text = questionItem.title
                Glide.with(context).load(questionItem.owner.profile_image).into(questionItemAuthor)
                questionItemAuthorText.text = questionItem.owner.display_name
                questionItemTimestampText.getTime(questionItem.last_edit_date.toLong())
                val tagAdapter = TagAdapter()
                questionItemTagsRv.setHasFixedSize(true)
                questionItemTagsRv.adapter = tagAdapter
                tagAdapter.submitList(questionItem.tags)
                root.setOnClickListener {
                    clickListener.openQuestion(questionItem)
                }
            }
        }
    }

    class DiffUtilCallBack : DiffUtil.ItemCallback<Question>() {
        override fun areItemsTheSame(oldItem: Question, newItem: Question): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Question, newItem: Question): Boolean {
            return oldItem == newItem
        }

    }

    interface OnClickListener {
        fun openQuestion(questionItem: Question) {}
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(
            QuestionItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val questionItem = getItem(position)
        holder.bind(questionItem)
    }
}