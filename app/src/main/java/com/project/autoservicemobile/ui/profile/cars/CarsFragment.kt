package com.project.autoservicemobile.ui.profile.cars

import androidx.fragment.app.viewModels
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.project.autoservicemobile.MainActivity
import com.project.autoservicemobile.R
import com.project.autoservicemobile.common.BaseFragment
import com.project.autoservicemobile.databinding.FragmentCarsBinding
import com.project.common.data.RequestResult
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CarsFragment : BaseFragment() {

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

        _viewModel.getCars(coroutinesErrorHandler)
        _viewModel.getDefaultCar(coroutinesErrorHandler)
    }

    private fun setup() {
        with(binding) {
            carsTitle.setText(R.string.your_cars_text)
            defaultCarTitle.setText(R.string.default_car_title)

            appBar.setLeftOnClickListener {
                (requireActivity() as MainActivity).goPreviousFragment()
            }

            with(binding) {
                carsRecycler.layoutManager = LinearLayoutManager(context)
                carsRecycler.adapter = CarsRecyclerAdapter { item ->
                    _viewModel.setDefaultCar(item, coroutinesErrorHandler)
                }

                _viewModel.cars.observe(viewLifecycleOwner) {
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

                _viewModel.defaultCar.observe(viewLifecycleOwner) {
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
                            defaultCarBrandText.text = it.data.br_mod
                            defaultCarModelText.text = it.data.number
                        }
                    }
                }

                _viewModel.defaultCarUpdated.observe(viewLifecycleOwner){
                    when(it){
                        is RequestResult.Error -> {}
                        is RequestResult.Loading -> {}
                        is RequestResult.Success -> {
                            _viewModel.getCars(coroutinesErrorHandler)
                            _viewModel.getDefaultCar(coroutinesErrorHandler)
                        }
                    }
                }
            }
        }
    }
}