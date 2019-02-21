package com.example.rio.kottlin_demo.ui.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.rio.kottlin_demo.R
import com.example.rio.kottlin_demo.data.model.Box
import java.util.ArrayList

class ListBoxAdapter(private var listBox: ArrayList<Box>, var context: Context?) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val VIEW_TYPE_EMPTY = 0
    val VIEW_TYPE_NORMAL = 1

    fun setListData(list:ArrayList<Box>){
        this.listBox=list
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): RecyclerView.ViewHolder {
        when (p1) {
            VIEW_TYPE_NORMAL -> return NormalViewHolder(
                LayoutInflater.from(p0.getContext()).inflate(
                    R.layout.item_box,
                    p0,
                    false
                )
            )

            VIEW_TYPE_EMPTY -> return LoadMoreViewHolder(
                LayoutInflater.from(p0.getContext()).inflate(
                    R.layout.item_loadmore,
                    p0,
                    false
                )
            )

            else -> return NormalViewHolder(
                LayoutInflater.from(p0.getContext()).inflate(
                    R.layout.item_box,
                    p0,
                    false
                )
            )
        }

    }

    override fun getItemViewType(position: Int): Int {
        if (!listBox.isEmpty()) {
            return VIEW_TYPE_NORMAL
        } else {
            return VIEW_TYPE_EMPTY
        }
    }


    override fun getItemCount(): Int {
        return listBox.size
    }

    override fun onBindViewHolder(p0: RecyclerView.ViewHolder, p1: Int) {
        if (p0 is NormalViewHolder) {
            p0.tvContent.setText(listBox.get(p1).content)
            p0.tvNameBox.setText(listBox.get(p1).name)
            Glide.with(this.context!!)
                .load(R.drawable.logo_splash)
                .apply(RequestOptions.circleCropTransform())
                .into(p0.imgAva)
//            p0.rlItem.setOnClickListener({ v -> onClickItem.onItemClick(users.get(i)) })
        } else if (p0 is LoadMoreViewHolder) {

        }
    }

    internal class NormalViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

//        @BindView(R.id.img_ava)
        var imgAva: ImageView
//        @BindView(R.id.tv_name_box)
         var tvNameBox: TextView
//        @BindView(R.id.tv_content)
         var tvContent: TextView
//        @BindView(R.id.item_layout)
         var itemLayout: RelativeLayout

        init {
            imgAva=itemView.findViewById(R.id.img_ava)
            tvNameBox=itemView.findViewById(R.id.tv_name_box)
            tvContent=itemView.findViewById(R.id.tv_content)
            itemLayout=itemView.findViewById(R.id.item_layout)
//            ButterKnife.bind(this, itemView)
        }

    }

    class LoadMoreViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    }
}