package com.example.myapplication.ui.main.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewParent
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.myapplication.databinding.RvlistItemBinding
import com.example.myapplication.R
import kotlinx.android.synthetic.main.rvlist_item.view.*

class RecyclerViewAdapter(private val dataSet:ArrayList<List<String>>):
    RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {
        override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType:Int,
            ):ViewHolder{

            val binding=RvlistItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
            return ViewHolder(binding)
        }

        override fun onBindViewHolder(holder: ViewHolder,position:Int) {
            holder.bind(dataSet[position])
        }

    override fun getItemCount(): Int {
        return dataSet.size
    }

   /* class ViewHolder(view: View):RecyclerView.ViewHolder(view){
        private val tvMain:TextView=view.tv_main
        private val tvSub:TextView=view.tv_sub

        fun bind(data:List<String>){
            tvMain.text=data[0]
            tvSub.text=data[1]
        }
    }*/

    class ViewHolder(private val binding:RvlistItemBinding):RecyclerView.ViewHolder(binding.root){
        fun bind(data:List<String>){
            binding.tvMain.text=data[0]
            binding.tvSub.text=data[1]
        }
    }

}