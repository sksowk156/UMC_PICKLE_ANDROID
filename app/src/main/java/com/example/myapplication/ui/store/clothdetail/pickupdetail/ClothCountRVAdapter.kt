package com.example.myapplication.ui.store.clothdetail.pickupdetail

import android.content.Context
import android.util.SparseBooleanArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.databinding.ItemClothCountBinding
import com.example.myapplication.ui.store.ClothCount
import kotlinx.android.synthetic.main.item_cloth_count.view.*

class ClothCountRVAdapter(private val dataList: ArrayList<ClothCount>, context : Context): RecyclerView.Adapter<ClothCountRVAdapter.DataViewHolder>() {
    private val switchStatus = SparseBooleanArray()
    private lateinit var itemClickListener: OnItemClickListener
    private val context = context
    private var clothPrice : Int = 0

    inner class DataViewHolder(private val viewBinding: ItemClothCountBinding) : RecyclerView.ViewHolder(viewBinding.root) {
        fun bind(clothCount: ClothCount) {
            //클릭한 item의 정보를 전달
            viewBinding.tvColor.text = (clothCount.color+"/"+clothCount.size)
            viewBinding.tvCount.text = clothCount.count.toString()
            viewBinding.tvMultiPrice.text = clothCount.clothPrice.toString() + "원"

            clothPrice = clothCount.clothPrice
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder {
        val viewBinding = ItemClothCountBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DataViewHolder(viewBinding)
    }

    override fun getItemCount(): Int = dataList.size

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        // (1) 리스트 내 항목 클릭 시 onClick() 호출
        val item = dataList[position]
        var multiPrice = 0
        holder.bind(item)
        holder.itemView.iv_close.setOnClickListener {  //(3)을 사용
            dataList.removeAt(position)
            this.notifyDataSetChanged()
        }

        holder.itemView.iv_plus.setOnClickListener {
            item.count = item.count + 1
            holder.itemView.tv_count.text = item.count.toString()
            holder.itemView.tv_multi_price.text = (item.count * clothPrice).toString() + "원"
        }

        holder.itemView.iv_minus.setOnClickListener{
            if(item.count > 0)
                 item.count = item.count - 1
            holder.itemView.tv_count.text = item.count.toString()
            holder.itemView.tv_multi_price.text = (item.count * clothPrice).toString() + "원"
        }
    }

    // (2) 리스너 인터페이스
    interface OnItemClickListener {
        fun onPlusMinusClick(v: View, position: Int) : Int
    }

    // 외부에서 클릭 시 이벤트 설정
    fun setItemClickListener(onItemClickListener: OnItemClickListener) {
        this.itemClickListener = onItemClickListener
    }
}
