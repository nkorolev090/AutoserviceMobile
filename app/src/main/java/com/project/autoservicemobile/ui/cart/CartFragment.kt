package com.project.autoservicemobile.ui.cart

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.project.autoservicemobile.MainActivity
import com.project.autoservicemobile.R
import com.project.autoservicemobile.common.AuthenticatedListener
import com.project.autoservicemobile.common.BaseFragment
import com.project.autoservicemobile.common.CoroutinesErrorHandler
import com.project.autoservicemobile.databinding.FragmentCartBinding
import com.project.autoservicemobile.ui.home.NewsRecyclerAdapter
import com.project.autoservicemobile.ui.login.SignInOrUpBottomSheetDialog
import com.project.common.data.RequestResult
import com.project.common.data.StatusCodeEnum
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CartFragment : BaseFragment(), AuthenticatedListener {

    private var _binding: FragmentCartBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val _viewModel: CartViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCartBinding.inflate(inflater, container, false)
        val root: View = binding.root

        setup()
        return root
    }

    override fun onResume() {
        super.onResume()
        _viewModel.isAuthenticated(coroutinesErrorHandler)
    }

    override fun onPause() {
        super.onPause()
        _viewModel.isAuth.postValue(RequestResult.Loading())
    }

    override fun onAuthenticated() {
        loadCart()
    }

    override fun onNavigateToHomeFragment() {
        (requireActivity() as MainActivity).navController.navigate(R.id.action_navigation_cart_to_navigation_home)
    }

    private fun setup(){
       with(binding){
           totalText.text = _viewModel.totalText
           saleText.text = _viewModel.saleText
           subTotalText.text = _viewModel.subTotalText

           promocodeInput.hint = _viewModel.promocodeHintText

           applyPromocodeBtn.text = _viewModel.applyBtnText
           applyPromocodeBtn.setOnClickListener{_ ->
               _viewModel.onApplyPromocodeClick()
           }

           carSelectContainer.setOnClickListener{
               navigateToCars()
           }

           createRegBtn.text = _viewModel.createRegBtnText
           createRegBtn.setOnClickListener{_ ->
               _viewModel.createRegistration(coroutinesErrorHandler)
           }

           cartRecycler.layoutManager = LinearLayoutManager(context)
           cartRecycler.adapter = CartRecyclerAdapter{
               _viewModel.removeBreakdownFromCart(
                   it.slot.service!!.id,
                   coroutinesErrorHandler)
           }


           _viewModel.cart.observe(viewLifecycleOwner){
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

                       totalValueText.text = it.data.total
                       saleValueText.text = it.data.discountValue
                       subTotalValueText.text = it.data.subtotal

                       (cartRecycler.adapter as CartRecyclerAdapter).items = it.data.cartItems
                       cartRecycler.adapter?.notifyDataSetChanged()
                   }
               }
           }

           _viewModel.defaultCar.observe(viewLifecycleOwner){
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
                       carTitle.text = it.data.br_mod
                       carDescription.text = it.data.number
                   }
               }
           }

           _viewModel.createdRegistration.observe(viewLifecycleOwner){
               when{
                   it is RequestResult.Success ->{
                       _viewModel.updateCart(coroutinesErrorHandler)
                       _viewModel.createdRegistration.postValue(RequestResult.Loading())
                   }
               }
           }
       }

        _viewModel.isAuth.observe(viewLifecycleOwner){
            if (it is RequestResult.Error) {
                openSignBottomSheet()
            }
            if(it is RequestResult.Success){
                loadCart()
            }
        }
    }

    private fun loadCart(){
        _viewModel.updateCart(coroutinesErrorHandler)

        _viewModel.updateDefaultCar(coroutinesErrorHandler)
    }

    private fun openSignBottomSheet() {
        val modalBottomSheet = SignInOrUpBottomSheetDialog(this)
        modalBottomSheet.setCancelable(false)
        modalBottomSheet.show(
            requireActivity().supportFragmentManager,
            SignInOrUpBottomSheetDialog.TAG
        )
    }

    private fun navigateToCars(){
        (requireActivity() as MainActivity).navController.navigate(R.id.action_navigation_cart_to_carsFragment)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}