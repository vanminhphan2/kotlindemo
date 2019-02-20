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
import com.example.rio.kottlin_demo.data.model.Message
import java.util.ArrayList

class ListChatAdapter(private val listData: ArrayList<Message>, var context: Context?,var idUserLogin:String) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val VIEW_TYPE_EMPTY = 0
    val VIEW_TYPE_RECEIVE = 1
    val VIEW_TYPE_SEND= 2

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): RecyclerView.ViewHolder {
        when (p1) {
            VIEW_TYPE_RECEIVE -> return ReceiveViewHolder(
                LayoutInflater.from(p0.getContext()).inflate(
                    R.layout.item_receive,
                    p0,
                    false
                )
            )
            VIEW_TYPE_SEND -> return SendViewHolder(
                LayoutInflater.from(p0.getContext()).inflate(
                    R.layout.item_send,
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

            else -> return LoadMoreViewHolder(
                LayoutInflater.from(p0.getContext()).inflate(
                    R.layout.item_loadmore,
                    p0,
                    false
                )
            )
        }

    }

    override fun getItemViewType(position: Int): Int {
        if (!listData.isEmpty()) {
            if(listData.get(position).idUser.equals(idUserLogin)){

                Log.e("Rio ", "getItemViewType 123 123  : "+idUserLogin)
                return VIEW_TYPE_SEND
            }
            else{
                Log.e("Rio ", "getItemViewType 123 111  : "+idUserLogin)
                return VIEW_TYPE_RECEIVE
            }

        } else {
            return VIEW_TYPE_EMPTY
        }
    }


    override fun getItemCount(): Int {
        return listData.size
    }

    override fun onBindViewHolder(p0: RecyclerView.ViewHolder, p1: Int) {
        if (p0 is SendViewHolder) {
            p0.tvContent.setText(listData.get(p1).contentMess)
//            p0.tvNameBox.setText(listData.get(p1).name)
//            Glide.with(this.context!!)
//                .load(R.drawable.logo_splash)
//                .apply(RequestOptions.circleCropTransform())
//                .into(p0.imgAva)
//            p0.rlItem.setOnClickListener({ v -> onClickItem.onItemClick(users.get(i)) })
        } else if (p0 is ReceiveViewHolder) {
            p0.tvContent.setText(listData.get(p1).contentMess)
        }
        else{

        }
    }

    internal class SendViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {


        //        @BindView(R.id.img_ava)
//        var imgAva: ImageView
        //        @BindView(R.id.tv_name_box)
//        var tvNameBox: TextView
        //        @BindView(R.id.tv_content)
        var tvContent: TextView
        //        @BindView(R.id.item_layout)
//        var itemLayout: RelativeLayout

        init {
//            imgAva=itemView.findViewById(R.id.img_ava)
//            tvNameBox=itemView.findViewById(R.id.tv_name_box)
            tvContent=itemView.findViewById(R.id.tv_content)
//            itemLayout=itemView.findViewById(R.id.item_layout)
//            ButterKnife.bind(this, itemView)
        }

    }

    internal class ReceiveViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        //        @BindView(R.id.img_ava)
//        var imgAva: ImageView
        //        @BindView(R.id.tv_name_box)
//        var tvNameBox: TextView
        //        @BindView(R.id.tv_content)
        var tvContent: TextView
        //        @BindView(R.id.item_layout)
//        var itemLayout: RelativeLayout

        init {
//            imgAva=itemView.findViewById(R.id.img_ava)
//            tvNameBox=itemView.findViewById(R.id.tv_name_box)
            tvContent=itemView.findViewById(R.id.tv_content)
//            itemLayout=itemView.findViewById(R.id.item_layout)
//            ButterKnife.bind(this, itemView)
        }

    }

    class LoadMoreViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    }
}