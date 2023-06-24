package com.yunshuting.eightdiagrams.mv

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.yunshuting.eightdiagrams.DetailInfoActivity
import com.yunshuting.eightdiagrams.R
import com.yunshuting.eightdiagrams.bean.DiagramBean
import kotlinx.android.synthetic.main.gua_item_layout.view.imageView
import kotlinx.android.synthetic.main.gua_item_layout.view.textView

class MyAdapter(private val dataList: List<DiagramBean>) : RecyclerView.Adapter<MyAdapter.ViewHolder>() {
    var mContext: Context? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.gua_item_layout, parent, false)
        mContext = parent.context
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = dataList[position]
        holder.bind(data)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(data: DiagramBean) {
            // Bind data to views here
            val imgId = mContext?.getResources()?.getIdentifier("gua"+data.DiagramNum.toString(),"drawable",mContext?.packageName)
//            imgId?.apply {
//                itemView.imageView.setImageResource(imgId)
//            }

            val layoutParams = itemView.imageView.layoutParams
            val imageWidth = layoutParams.width;
            val imageHeight = imageWidth * 202 /162

            Glide.with(itemView.imageView.context)
                .load(imgId)
                .fitCenter()
                .override(imageWidth,imageHeight)
                .into(itemView.imageView)
            itemView.textView.text =  data.DiagramNum.toString()+"-"+data.DiagramName


            itemView.setOnClickListener {
                val intent = Intent(itemView?.context, DetailInfoActivity::class.java)
                intent.putExtra("guaNum", data.DiagramNum)
                itemView?.context?.startActivity(intent)
            }
        }
    }
}