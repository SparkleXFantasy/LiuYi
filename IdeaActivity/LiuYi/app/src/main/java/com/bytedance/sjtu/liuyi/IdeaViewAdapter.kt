package com.bytedance.sjtu.liuyi

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class IdeaViewAdapter: RecyclerView.Adapter<IdeaViewAdapter.IdeaViewHolder>() {
    private var ideaList : MutableList<IdeaItem> = arrayListOf()
    inner class IdeaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val ideaItemTextView: TextView = itemView.findViewById(R.id.tv_idea_item_text)
        private val ideaItemImageView: ImageView = itemView.findViewById(R.id.iv_idea_item_image)
        private val ideaItemTimeView : TextView = itemView.findViewById(R.id.tv_idea_item_time)

        fun bindView(ideaItem : IdeaItem) {
            ideaItemTextView.text = ideaItem.text
            ideaItemTimeView.text = tag2Time(ideaItem.tag)
            ideaItemImageView.setImageResource(R.drawable.banana)
        }

        private fun tag2Time(tag : String) : String {
            val timeList = tag.split('-')
            return "${timeList[3]}:${timeList[4]}:${timeList[5]}"
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IdeaViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.idea_item_view, parent, false)
        return IdeaViewHolder(view)
    }

    override fun onBindViewHolder(holder: IdeaViewHolder, position: Int) {
        holder.bindView(ideaList[position])
    }

    override fun getItemCount(): Int {
        return ideaList.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setIdeaList(newList: MutableList<IdeaItem>) {
        ideaList.clear()
        ideaList.addAll(newList)
        notifyDataSetChanged()
    }
}