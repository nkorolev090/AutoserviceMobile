package com.project.autoservicemobile.ui.profile.cars

import androidx.fragment.app.viewModels
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.project.autoservicemobile.MainActivity
import com.project.autoservicemobile.R
import com.project.autoservicemobile.common.CoroutinesErrorHandler
import com.project.autoservicemobile.databinding.FragmentCarsBinding
import com.project.autoservicemobile.databinding.FragmentProfileBinding
import com.project.autoservicemobile.ui.cart.CartRecyclerAdapter
import com.project.autoservicemobile.ui.home.NewsRecyclerAdapter
import com.project.autoservicemobile.ui.profile.ProfileViewModel
import com.project.common.data.RequestResult
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CarsFragment : Fragment() {

    private val _viewModel: CarsViewModel by viewModels()

    private var _binding: FragmentCarsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCarsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        setup()

        return root;
    }

    override fun onResume() {
        super.onResume()

        _viewModel.updateCars(object : CoroutinesErrorHandler {
            override fun onError(message: String) {
                Log.d("CarsFragment", "Error! $message")
            }
        })

        with(binding) {
            carsRecycler.layoutManager = LinearLayoutManager(context)
            carsRecycler.adapter = CarsRecyclerAdapter { item ->
                _viewModel.setDefaultCar(item, object : CoroutinesErrorHandler {
                    override fun onError(message: String) {
                        Log.d("CarsFragment", "Error! $message")
                    }
                })
            }

            _viewModel.cars.observe(viewLifecycleOwner){
                when (it) {
                    is RequestResult.Error -> {
                        //trobber.visibility = View.GONE

//                       if(_errorPage == null) {
//                           showErrorPage(StatusCodeEnum.CONNECTION_TIMED_OUT)
//                       }
                    }

                    is RequestResult.Loading -> {
//                       if(_errorPage != null){
//                           removeErrorPage()
//                       }
//                       trobber.visibility = View.VISIBLE
                    }

                    is RequestResult.Success -> {
//                       if(_errorPage != null){
//                           removeErrorPage()
//                       }
//                       trobber.visibility = View.GONE
//
//                       if(newsRecycler.childCount == 0){
//                           startAnims()
//                       }

                        (carsRecycler.adapter as CarsRecyclerAdapter).items = it.data
                        carsRecycler.adapter?.notifyDataSetChanged()
                    }
                }
            }
        }

    }

    private fun setup() {
        with(binding) {
            appBar.setLeftOnClickListener {
                (requireActivity() as MainActivity).goPreviousFragment()
            }
        }
    }
}