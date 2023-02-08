package com.example.myapplication.ui.base

import android.os.Bundle
import android.text.InputFilter
import android.view.*
import android.widget.EditText
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.annotation.LayoutRes
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.ApplicationClass
import com.example.myapplication.ApplicationClass.Companion.KEY_SEARCH_HISTORY
import com.example.myapplication.R
import com.example.myapplication.databinding.ToolbarBinding
import com.example.myapplication.databinding.ToolbarContentBinding
import com.example.myapplication.ui.main.search.SearchHistroyData
import com.example.myapplication.ui.main.search.SearchhistoryAdapter
import com.example.myapplication.ui.main.search.SearchresultFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.toolbar_content.view.*


abstract class BaseFragment<T : ViewDataBinding>(
    @LayoutRes val layoutResId: Int
) : Fragment() {
    private var _binding: T? = null
    protected val binding get() = _binding!!

    // 뒤로가기 버튼을 눌렀을 때를 위한 callback 변수
    private lateinit var callback: OnBackPressedCallback

    // 툴바 변수들
    protected lateinit var toolbarlayout: ConstraintLayout
    protected lateinit var toolbarinnerlayout: ConstraintLayout
    protected lateinit var toolbar: Toolbar
    protected lateinit var toolbarmenusearch: MenuItem
    protected lateinit var toolbarmenunotification: MenuItem
    protected lateinit var searchView : SearchView

    // 검색 기록을 저장하는 배열
    private var searchHistoryDataList = ArrayList<SearchHistroyData>()

    // 검색 기록 어댑터
    private lateinit var searchhistoryAdapter: SearchhistoryAdapter

    protected open fun savedatainit(){

    }
    protected abstract fun init()

    protected fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    // 하단 바 숨길때
    protected fun hideBottomNavigation(bool: Boolean) {
        if(requireActivity().findViewById<BottomNavigationView>(R.id.second_bottomNavigationView)!=null){
            val bottom: BottomNavigationView =
                requireActivity().findViewById(R.id.second_bottomNavigationView)
            if (bool == true) {
                bottom.visibility = View.GONE
            } else {
                bottom.visibility = View.VISIBLE
            }
        }
    }

    // 뒤로가기 버튼을 눌렀을 때
    protected fun backpress() {
        // 최상위 Fragment가 아닐 때만 뒤로가기 버튼 활성화
        // -> 최상위 Fragment는 bottomnavigationview에 올라가는 것이기 때문에 뒤로가기 버튼을 활성화 하면
        // Activity의 supportfragmentmanger의 stack에서 popbackstack 하게 된다.( 단, 메뉴 선택 기록을 기록했을 경우 )
        callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (childFragmentManager.backStackEntryCount != 0) {
                    childFragmentManager
                        .popBackStackImmediate(null, 0)
                } else if (toolbarmenusearch.isActionViewExpanded) { // 검색창을 눌렀을 경우
                    toolbarmenusearch.collapseActionView()
                } else {
                    requireActivity().finish()
                }
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)
    }

    protected fun initSubAppbar(name: String, backbtn: Boolean, item: Boolean) {
        toolbar.setTitle(name)
        if (backbtn) {
            // 뒤로가기 버튼 추가
            toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_ios_new_24)
        } else {
            // 뒤로가기 버튼 지우기
            toolbar.setNavigationIcon(null)
        }
        toolbarmenunotification.setVisible(item)
        toolbarmenusearch.setVisible(item)
    }

    protected fun initAppbar(
        toolbarContentBinding: ToolbarContentBinding,
        toolbarBinding: ToolbarBinding,
        firstlayoutname: String,
        backbtn: Boolean,
        item : Boolean
    ) {
        // 툴바, 툴바 검색 기록 레이아웃, 툴바 메뉴 연결
        toolbarlayout = toolbarContentBinding.contentLayout
        toolbarinnerlayout = toolbarContentBinding.contentInnerlayout
        toolbar = toolbarBinding.toolbarToolbar
        // 툴바 메뉴 추가
        toolbar.inflateMenu(R.menu.menu_appbar)
        toolbarmenunotification = toolbar.menu.findItem(R.id.notification)
        toolbarmenusearch = toolbar.menu.findItem(R.id.search)

        initSubAppbar(firstlayoutname, backbtn, item)
        // 툴바 뒤로가기 버튼 클릭 시 효과
        toolbar.setNavigationOnClickListener {
            if (childFragmentManager.backStackEntryCount > 0) {
                childFragmentManager.popBackStackImmediate(null, 0)
            }else{
//                val intent = Intent(requireContext(), SecondActivity::class.java) //지금 액티비티에서 다른 액티비티로 이동하는 인텐트 설정
//                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP //인텐트 플래그 설정
//                startActivity(intent) //인텐트 이동
//                requireActivity().finish() //현재 액티비티 종료
                requireActivity().onBackPressed()
            }
        }

        backpress()

        // searchView 선언
        searchView = toolbarmenusearch.actionView as SearchView
        // searchView 힌트
        searchView.queryHint = "검색중"
        // searchView editText
        var searchViewEditText =
            searchView.findViewById<EditText>(androidx.appcompat.R.id.search_src_text)
        searchViewEditText.apply {
            this.filters = arrayOf(InputFilter.LengthFilter(12))
        }
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
                    searchHistoryDataList.add(0, newSearchData)
                    // 쉐어드에 저장하기
                    ApplicationClass.sharedPreferencesmanager.setsearchhistoryString(
                        KEY_SEARCH_HISTORY,
                        searchHistoryDataList
                    )
                    // recycler 데이터 갱신 요청
                    searchhistoryAdapter.notifyDataSetChanged()

                    // // 화면 전환 및 검색 결과 보여주기(API 요청)
                    // 검색 기록 보여주는 창 가리고
                    toolbarinnerlayout.visibility = View.INVISIBLE
                    // 검색 기록 보여주는 fragment 보여주기
                    childFragmentManager.beginTransaction()
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
                    if (childFragmentManager.findFragmentByTag("searchresult") != null) {
                        childFragmentManager.beginTransaction()
                            .remove(childFragmentManager.findFragmentByTag("searchresult")!!)
                            .commitAllowingStateLoss()
                    }
                    // 검색기록은 보여주지 않는다. (원래는 추천 검색어가 떠야한다.)
                    toolbarlayout.visibility = View.VISIBLE
                    toolbarinnerlayout.visibility = View.INVISIBLE
                } else { // 글자를 다 지워서 없을 때 -> 검색 기록만 남아있어야한다.
                    // 검색 결과 fragment가 있었다면 지운다.
                    if (childFragmentManager.findFragmentByTag("searchresult") != null) {
                        childFragmentManager.beginTransaction()
                            .remove(childFragmentManager.findFragmentByTag("searchresult")!!)
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
                true -> { // 검색창에 커서가 생김
                    // 검색 결과 fragment가 있다면 fragment를 지우지 않고 그대로 유지한다.
                    if (childFragmentManager.findFragmentByTag("searchresult") == null) { // 없다면
                        if (searchViewEditText.text.length > 0) { // 입력한 글자가 있는 경우, 추천 검색어를 보여준다.
                            // 검색 content를 보여준다.( 검색기록이 기본적으로 보여진다. )
                            toolbarlayout.visibility = View.VISIBLE
                            toolbarinnerlayout.visibility = View.INVISIBLE
                        } else { // 입력한 글자가 없는 경우, 검색 기록을 보여준다.
                            toolbarlayout.visibility = View.VISIBLE
                            toolbarinnerlayout.visibility = View.VISIBLE
                        }
                    }
                    // 하단바를 가린다.
                    hideBottomNavigation(true)
                }
                false -> {// 검색창에서 커서가 사라짐
                }
            }
        }

        toolbarmenusearch.setOnActionExpandListener(object : MenuItem.OnActionExpandListener {
            override fun onMenuItemActionExpand(item: MenuItem?): Boolean {
                initSearchHistory()
                return true
            }

            override fun onMenuItemActionCollapse(item: MenuItem?): Boolean {
                // 검색창이 닫혔 때
                // 검색창 초기화
                searchViewEditText.text.clear()
                if (childFragmentManager.findFragmentByTag("searchresult") != null) {
                    childFragmentManager.beginTransaction()
                        .remove(childFragmentManager.findFragmentByTag("searchresult")!!)
                        .commitAllowingStateLoss()
                }
                toolbarinnerlayout.visibility = View.INVISIBLE
                toolbarlayout.visibility = View.INVISIBLE
                hideBottomNavigation(false)
                return true
            }
        })
    }

    protected fun initSearchHistory() {
        // 쉐어드에서 데이터 가져오기
        searchHistoryDataList =
            ApplicationClass.sharedPreferencesmanager.getsearchhistoryString(KEY_SEARCH_HISTORY) as ArrayList<SearchHistroyData>

        // 기록을 보여줄 recycler의 어댑터, 어댑터 클릭 이벤트 처리
        searchhistoryAdapter =
            SearchhistoryAdapter(object : SearchhistoryAdapter.ItemClickListener {
                // recycler 아이템 중 텍스트를 클릭했을 때 -> 해당 텍스트로 재검색
                override fun onTextItemClick(view: View, position: Int) {
                    // // 화면 전환 및 검색 결과 보여주기(API 요청)
                    // 검색 기록 보여주는 창 가리고
                    toolbarinnerlayout.visibility = View.INVISIBLE
                    // 검색 기록 보여주는 fragment 보여주기
                    childFragmentManager.beginTransaction()
                        .replace(toolbarlayout.id, SearchresultFragment(), "searchresult")
                        .commitAllowingStateLoss()
                    // 엔터를 쳤기 때문에 커서를 없앤다.
                    searchView.clearFocus()
                }

                // recycler 아이템 중 x 이미지를 클릭했을 때 -> 데이터 삭제
                override fun onImageItemClick(view: View, position: Int) {
                    searchHistoryDataList.removeAt(position)
                    // 쉐어드 데이터를 덮어씌우는 것
                    ApplicationClass.sharedPreferencesmanager.setsearchhistoryString(
                        KEY_SEARCH_HISTORY,
                        searchHistoryDataList
                    )
                    // 데이터 변경 알리기
                    searchhistoryAdapter.notifyDataSetChanged()
                }
            })

        // recycler의 어댑터 연결
        toolbarlayout.content_recycler.adapter = searchhistoryAdapter
        // 그리드 레이아웃으로 설정
        var layoutManager = GridLayoutManager(requireContext(), 3) as LinearLayoutManager
        toolbarlayout.content_recycler.layoutManager = layoutManager
        // 어댑터에 쉐어드 데이터 넣기
        searchhistoryAdapter.userList = searchHistoryDataList


        // 데이터 변경 알리기
        searchhistoryAdapter.notifyDataSetChanged()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DataBindingUtil.inflate(inflater, layoutResId, container, false)
        if(savedInstanceState == null){
            savedatainit()
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner = viewLifecycleOwner
        init()
//        backpress() -> 백 버튼을 baseFragment에 넣으면 baseFragment를 상속하는 fragment들을 호출할 때마다 back callback이 초기화 된다.
        // 그래서 backbutton 이벤트 처리시 사용되는 toolbarmenusearch 변수가 초기화 되어 있지 않아 에러를 발생하게 된다.
        // parentFragment에서 한번만 초기화되면 되므로 backpress()메소드를 parentFragment에서 한번만 호출하는 initAppbar()메소드 안에 넣는다.
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    // 프래그먼트가 종료되면 callback 변수 제거
    override fun onDetach() {
        super.onDetach()
        if (::callback.isInitialized) {
            callback.remove()
        }
    }

    // Activity에서 fragment들을 바꿀때마다 각각의 fragment들에서 초기화된 toolbarmenusearch도 같이 바껴야한다.
    // 하지만 hide show로 바꾸었기 때문에 Activity는 toolbarmenusearch값을 가장 마지막으로 초기화 시킨 fragment의 값으로 저장한다.(초기화 가능횟수는 하단바 fragment 갯수로 제한된다.)
    // 그래서 초기화된 fragment들은 hide show가 바뀔 때마다 toolbarmenusearch값을 갱신시키지 못한다.
    // fragment들의 hide show 상태를 구분할 수 있는 메소드에 backpress 이벤트를 다시 초기화시켜 toolbarmenusearch값을 현재 보고 있는 fragment의 toolbarmenusearch값으로 갱신한다.
    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        if (::toolbarmenusearch.isInitialized) {
            if (!hidden) {
                backpress()
            }
        }
    }
}