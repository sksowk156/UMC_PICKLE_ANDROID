package com.example.myapplication.main.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.databinding.FragmentHomeBinding
import com.smarteist.autoimageslider.SliderView


class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null


    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View {
        val homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)


        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        //val textView: TextView = binding.textHome
      //  homeViewModel.text.observe(viewLifecycleOwner) {
       //     textView.text = it
       // }


        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        // on below line we are creating a variable
        // for our array list for storing our images.
        lateinit var imageUrl: ArrayList<String>

        imageUrl = ArrayList()

        // on below line we are adding data to our image url array list.
        imageUrl =
            (imageUrl + "https://images.unsplash.com/photo-1549298916-b41d501d3772?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxzZWFyY2h8NHx8c2hvZXN8ZW58MHx8MHx8&auto=format&fit=crop&w=600&q=60") as ArrayList<String>
        imageUrl =
            (imageUrl + "https://images.unsplash.com/photo-1662532577856-e8ee8b138a8b?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxjb2xsZWN0aW9uLXBhZ2V8NXw5NzY3MDkyfHxlbnwwfHx8fA%3D%3D&auto=format&fit=crop&w=600&q=60") as ArrayList<String>
        imageUrl =
            (imageUrl + "https://images.unsplash.com/photo-1566150905458-1bf1fc113f0d?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxzZWFyY2h8NXx8YmFnfGVufDB8fDB8fA%3D%3D&auto=format&fit=crop&w=600&q=60") as ArrayList<String>


        // on below line we are creating
        // a variable for our slider view.
        lateinit var sliderView: SliderView


        sliderView=binding.slider

        // on below line we are creating
        // a variable for our slider adapter.
        lateinit var sliderAdapter: SliderAdapter

        sliderAdapter=SliderAdapter(imageUrl)

        sliderView.autoCycleDirection = SliderView.LAYOUT_DIRECTION_LTR

        // on below line we are setting adapter for our slider.
        sliderView.setSliderAdapter(sliderAdapter)

        // on below line we are setting scroll time
        // in seconds for our slider view.
        sliderView.scrollTimeInSec = 3

        // on below line we are setting auto cycle
        // to true to auto slide our items.
        sliderView.isAutoCycle = true

        // on below line we are calling start
        // auto cycle to start our cycle.
        sliderView.startAutoCycle()





    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }





}