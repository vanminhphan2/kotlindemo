package com.example.rio.kottlin_demo.ui.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.rio.kottlin_demo.R
import com.example.rio.kottlin_demo.data.model.User
import com.example.rio.kottlin_demo.utils.inf.MyCallBack
import java.util.ArrayList

class ListSearchResultAdapter (private var listData: ArrayList<User>, var context: Context?) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val VIEW_TYPE_EMPTY = 0
    val VIEW_TYPE_NORMAL = 1
    private var myCallBack: MyCallBack? = null

    fun setMyCallback(myCallBack: MyCallBack){
        this.myCallBack=myCallBack
    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): RecyclerView.ViewHolder {
        when (p1) {
            VIEW_TYPE_NORMAL -> return NormalViewHolder(
                LayoutInflater.from(p0.getContext()).inflate(
                    R.layout.item_search_result,
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
                    R.layout.item_search_result,
                    p0,
                    false
                )
            )
        }

    }

    fun setListData(list: ArrayList<User>){
        this.listData=list
        notifyDataSetChanged()
    }

    override fun getItemViewType(position: Int): Int {
        if (!listData.isEmpty()) {
            return VIEW_TYPE_NORMAL
        } else {
            return VIEW_TYPE_EMPTY
        }
    }


    override fun getItemCount(): Int {
//        Log.e("Rio ","getItemCount " +listData.size)
        return listData.size
    }

    override fun onBindViewHolder(p0: RecyclerView.ViewHolder, p1: Int) {
        if (p0 is NormalViewHolder) {
            p0.tvNameBox.setText(listData.get(p1).name)
            Glide.with(this.context!!)
                .load(R.drawable.logo_splash)
                .apply(RequestOptions.circleCropTransform())
                .into(p0.imgAva)
            p0.itemLayout.setOnClickListener {
                myCallBack!!.onItemClick(listData.get(p1).id)
            }
        } else if (p0 is LoadMoreViewHolder) {

        }
    }

    internal class NormalViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var imgAva: ImageView
        var imgIb: ImageView
        var tvNameBox: TextView
        var itemLayout: RelativeLayout

        init {
            imgAva=itemView.findViewById(R.id.img_ava)
            imgIb=itemView.findViewById(R.id.img_ib)
            tvNameBox=itemView.findViewById(R.id.tv_name_box)
            itemLayout=itemView.findViewById(R.id.item_layout)
        }

    }

    class LoadMoreViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    }
}