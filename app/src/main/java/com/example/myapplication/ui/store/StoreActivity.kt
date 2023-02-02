package com.example.myapplication.ui.store

import android.content.Intent
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.view.Menu
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.GridLayoutManager
import com.example.myapplication.R
import com.example.myapplication.databinding.ActivityStoreBinding
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException


class StoreActivity : AppCompatActivity() {

    private lateinit var viewBinding: ActivityStoreBinding
    private lateinit var toolbar : Toolbar

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        viewBinding = ActivityStoreBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        supportActionBar?.title = "<"

        val dataList: ArrayList<Data> = arrayListOf()

        dataList.apply {
            add(Data("매장1", "옷1", "10000"))
            add(Data("매장1", "옷2", "15000"))
            add(Data("매장1", "옷3", "10000"))
            add(Data("매장1", "옷4", "20000"))
            add(Data("매장1", "옷5", "10000"))
            add(Data("매장1", "옷6", "10000"))
            add(Data("매장1", "옷7", "10000"))
            add(Data("매장1", "옷8", "10000"))
            add(Data("매장1", "옷9", "10000"))
        }

        val dataRVadapter = DataRVAdapter(dataList, applicationContext)

        viewBinding.rvData.adapter = dataRVadapter
        viewBinding.rvData.layoutManager = GridLayoutManager(applicationContext, 2)

        viewBinding.rvData.run {
            adapter = dataRVadapter
            val spanCount = 2
            val space = 30
            addItemDecoration(GridSpaceItemDecoration(spanCount, space))
        }

        dataRVadapter.setItemClickListener(object: DataRVAdapter.OnItemClickListener {
            override fun onClick(v: View, position: Int) {
                // 클릭 시 이벤트 작성
                val intent = Intent(applicationContext, ClothActivity::class.java)
                intent.putExtra("storeName", dataList[position].store)
                Log.d("intent", dataList[position].store)
                intent.putExtra("clothName", dataList[position].cloth)
                intent.putExtra("clothPrice", dataList[position].price.toInt())
                startActivity(intent)
            }
        })
    }

    override fun onCreateOptionsMenu(menu : Menu) : Boolean{
        menuInflater.inflate(R.menu.menu_appbar, menu)
        return true

    }
}