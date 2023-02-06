package com.example.myapplication.ui.store.storedetail

import android.content.Context
import android.util.SparseBooleanArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.databinding.ItemDataBinding
import com.example.myapplication.ui.store.Data


class DataRVAdapter(private val dataList: ArrayList<Data>, context : Context): RecyclerView.Adapter<DataRVAdapter.DataViewHolder>() {
    private val switchStatus = SparseBooleanArray()
    private lateinit var itemClickListener : OnItemClickListener
    private val context = context

    inner class DataViewHolder(private val viewBinding: ItemDataBinding): RecyclerView.ViewHolder(viewBinding.root){
        fun bind(data: Data){
            //클릭한 item의 정보를 전달
            viewBinding.tvStoreName1.text = data.store
            viewBinding.tvClothName1.text = data.cloth
            viewBinding.tvClothPrice1.text = data.price
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder {
        val viewBinding = ItemDataBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DataViewHolder(viewBinding)
    }

    override fun getItemCount(): Int = dataList.size

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        // (1) 리스트 내 항목 클릭 시 onClick() 호출
        val item = dataList[position]
        holder.bind(item)
        holder.itemView.setOnClickListener {  //(3)을 사용
            itemClickListener.onClick(it, position)

//            val intent = Intent(context, ClothActivity::class.java)
//            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK); // 1줄 추가함
//            intent.putExtra("storeName", item.store) //변수값 인텐트로 넘기기
//            //Log.d("ggg",item.store)
//            intent.putExtra("clothName", item.cloth)
//            intent.putExtra("clothPrce", item.price)
//            context.startActivity(intent) //액티비티 열기
        }
    }

    // (2) 리스너 인터페이스
    interface OnItemClickListener {
        fun onClick(v: View, position: Int)
    }

    // 외부에서 클릭 시 이벤트 설정
    fun setItemClickListener(onItemClickListener: OnItemClickListener) {
        this.itemClickListener = onItemClickListener
    }


}