package com.example.myapplication.ui.search

import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.MenuProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.ApplicationClass
import com.example.myapplication.R
import com.example.myapplication.databinding.ActivitySearchBinding
import com.example.myapplication.ui.base.BaseActivity
import kotlinx.android.synthetic.main.toolbar_content.view.*

class SearchActivity : BaseActivity<ActivitySearchBinding>(R.layout.activity_search){
    // 검색 기록을 저장하는 배열
    private var searchHistoryDataList = ArrayList<SearchHistroyData>()
    // 검색 기록 어댑터
    private lateinit var searchhistoryAdapter: SearchhistoryAdapter

    private lateinit var toolbar: Toolbar
    protected lateinit var toolbarinnerlayout: ConstraintLayout
    protected lateinit var toolbarlayout: ConstraintLayout
    protected lateinit var toolbarmenusearch: MenuItem

    override fun init() {
        toolbar = binding.searchToolbar.toolbarToolbar
        toolbarinnerlayout = binding.searchContent.contentInnerlayout
        toolbarlayout = binding.searchContent.contentLayout

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.menu_search_appbar, menu)
                toolbarmenusearch = toolbar.menu.findItem(R.id.search)

                val searchView = menu.findItem(R.id.search).actionView as SearchView

                var searchViewEditText =
                    searchView.findViewById<EditText>(androidx.appcompat.R.id.search_src_text)

                // SearchView 가 Icon 화 되어 시작할지 펼쳐진 상태에서 시작할지 설정
                searchView.setIconifiedByDefault(false)

                searchView.setOnQueryTextListener(object :
                    SearchView.OnQueryTextListener {
                    // // 검색 후 엔터를 쳤을 때
                    override fun onQueryTextSubmit(query: String): Boolean {
                        // // 글자를 하나라도 쳤을 때
                        if (!query.isNullOrEmpty()) {
                            val newSearchData = SearchHistroyData(query)
                            // 검색 기록 추가하기(역순으로)
                            searchHistoryDataList.add(0,newSearchData)
                            // 쉐어드에 저장하기
                            ApplicationClass.sharedPreferencesmanager.setsearchhistoryString(
                                ApplicationClass.KEY_SEARCH_HISTORY,
                                searchHistoryDataList
                            )
                            // recycler 데이터 갱신 요청
                            searchhistoryAdapter.notifyDataSetChanged()

                            // // 화면 전환 및 검색 결과 보여주기(API 요청)
                            // 검색 기록 보여주는 창 가리고
                            toolbarlayout.visibility = View.VISIBLE
                            toolbarinnerlayout.visibility = View.INVISIBLE
                            // 검색 기록 보여주는 fragment 보여주기
                            supportFragmentManager.beginTransaction()
                                .replace(toolbarlayout.id, SearchresultFragment(), "searchresult")
                                .commitAllowingStateLoss()
                            // 엔터를 쳤기 때문에 커서를 없앤다.
                            searchView.clearFocus()
                        }
                        return true
                    }

                    // // 검색 중일 때
                    override fun onQueryTextChange(newText: String?): Boolean {
                        if (newText?.length!! > 0) { // 글자를 입력하거나 지웠을 때(글자에 변화 생겼을 때)
                            // 검색 결과 fragment가 있었다면 지운다.
                            if (supportFragmentManager.findFragmentByTag("searchresult") != null) {
                                supportFragmentManager.beginTransaction()
                                    .remove(supportFragmentManager.findFragmentByTag("searchresult")!!)
                                    .commitAllowingStateLoss()
                            }
                            // 검색기록은 보여주지 않는다. (원래는 추천 검색어가 떠야한다.)
                            toolbarlayout.visibility = View.VISIBLE
                            toolbarinnerlayout.visibility = View.INVISIBLE
                        } else { // 글자를 다 지워서 없을 때 -> 검색 기록만 남아있어야한다.
                            // 검색 결과 fragment가 있었다면 지운다.
                            if (supportFragmentManager.findFragmentByTag("searchresult") != null) {
                                supportFragmentManager.beginTransaction()
                                    .remove(supportFragmentManager.findFragmentByTag("searchresult")!!)
                                    .commitAllowingStateLoss()
                            }
                            // 검색 기록만 보인다.
                            toolbarlayout.visibility = View.VISIBLE
                            toolbarinnerlayout.visibility = View.VISIBLE
                        }
                        return true
                    }
                })

                searchView.setOnQueryTextFocusChangeListener { view, hasFocus ->
                    when (hasFocus) {
                        true -> {// 검색창에 커서가 생김
                            // 검색 결과 fragment가 있다면 fragment를 지우지 않고 그대로 유지한다.
                            if (supportFragmentManager.findFragmentByTag("searchresult") == null) { // 없다면
                                if(searchViewEditText.text.length > 0){ // 입력한 글자가 있는 경우, 추천 검색어를 보여준다.
                                    // 검색 content를 보여준다.( 검색기록이 기본적으로 보여진다. )
                                    toolbarlayout.visibility = View.VISIBLE
                                    toolbarinnerlayout.visibility = View.INVISIBLE
                                }else{ // 입력한 글자가 없는 경우, 검색 기록을 보여준다.
                                    toolbarlayout.visibility = View.VISIBLE
                                    toolbarinnerlayout.visibility = View.VISIBLE
                                }
                            }
                        }
                        false -> {
                            // // 검색창에서 커서가 사라짐
                        }
                    }
                }

                toolbarmenusearch.setOnActionExpandListener(object : MenuItem.OnActionExpandListener {
                    override fun onMenuItemActionExpand(item: MenuItem?): Boolean {
                        // 검색창이 펼쳐 졌을 때
                        initSearchHistory()
                        return true
                    }

                    override fun onMenuItemActionCollapse(item: MenuItem?): Boolean {
                        // 검색창이 닫혔 때
                        // 검색창 초기화
//                        searchViewEditText.text.clear()
//                        if(supportFragmentManager.findFragmentByTag("searchresult")!=null){
//                            supportFragmentManager.beginTransaction()
//                                .remove(supportFragmentManager.findFragmentByTag("searchresult")!!)
//                                .commitAllowingStateLoss()
//                        }
//                        toolbarinnerlayout.visibility = View.INVISIBLE
//                        toolbarlayout.visibility = View.INVISIBLE
                        finish()

                        return true
                    }
                })
                toolbarmenusearch.expandActionView()
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when (menuItem.itemId) {
                    android.R.id.home -> { // 뒤로가기 버튼을 눌렀을 때
                        finish()
                        true
                    }
                    R.id.notification -> { // 알림 버튼을 눌렀을 때
                        true
                    }
                    else -> false
                }
            }
        })
    }

    protected fun initSearchHistory() {
        // 쉐어드에서 데이터 가져오기
        searchHistoryDataList =
            ApplicationClass.sharedPreferencesmanager.getsearchhistoryString(ApplicationClass.KEY_SEARCH_HISTORY) as ArrayList<SearchHistroyData>

        // 기록을 보여줄 recycler의 어댑터, 어댑터 클릭 이벤트 처리
        searchhistoryAdapter =
            SearchhistoryAdapter(object : SearchhistoryAdapter.ItemClickListener {
                // recycler 아이템 중 텍스트를 클릭했을 때 -> 해당 텍스트로 재검색
                override fun onTextItemClick(view: View, position: Int) {

                }

                // recycler 아이템 중 x 이미지를 클릭했을 때 -> 데이터 삭제
                override fun onImageItemClick(view: View, position: Int) {
                    searchHistoryDataList.removeAt(position)
                    // 쉐어드 데이터를 덮어씌우는 것
                    ApplicationClass.sharedPreferencesmanager.setsearchhistoryString(
                        ApplicationClass.KEY_SEARCH_HISTORY,
                        searchHistoryDataList
                    )
                    // 데이터 변경 알리기
                    searchhistoryAdapter.notifyDataSetChanged()
                }
            })

        // recycler의 어댑터 연결
        toolbarlayout.content_recycler.adapter = searchhistoryAdapter
        // 그리드 레이아웃으로 설정
        var layoutManager = GridLayoutManager(applicationContext, 3) as LinearLayoutManager
        toolbarlayout.content_recycler.layoutManager = layoutManager
        // 어댑터에 쉐어드 데이터 넣기
        searchhistoryAdapter.userList = searchHistoryDataList


        // 데이터 변경 알리기
        searchhistoryAdapter.notifyDataSetChanged()
    }
}