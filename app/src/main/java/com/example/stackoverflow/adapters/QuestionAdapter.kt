package com.example.stackoverflow.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.stackoverflow.R
import com.example.stackoverflow.model.Question
import com.example.stackoverflow.utils.getTime
import kotlinx.android.synthetic.main.question_item.view.*

class QuestionAdapter(
    private val clickListener: OnClickListener,
    private val context: Context
): RecyclerView.Adapter<QuestionAdapter.QuestionViewHolder>() {

    inner class QuestionViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)

    private val diffUtilCallback = object : DiffUtil.ItemCallback<Question>(){
        override fun areItemsTheSame(oldItem: Question, newItem: Question): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Question, newItem: Question): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, diffUtilCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuestionViewHolder {
        return QuestionViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.question_item,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: QuestionViewHolder, position: Int) {
        val question = differ.currentList[position]
        holder.itemView.apply {
            question_item_text.text = question?.title
            Glide.with(this).load(question.owner.profile_image).into(question_item_author)
            question_item_author_text.text = question.owner.display_name
            question_item_timestamp_text.getTime(question.last_edit_date.toLong())
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    interface OnClickListener {
        fun openQuestion(questionItem: Question) {}
    }
}