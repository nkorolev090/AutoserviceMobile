package com.project.autoservicemobile.ui.cart

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.project.autoservicedata.common.RequestResult
import com.project.autoservicemobile.common.AuthenticatedListener
import com.project.autoservicemobile.common.CoroutinesErrorHandler
import com.project.autoservicemobile.databinding.FragmentCartBinding
import com.project.autoservicemobile.ui.login.SignInOrUpBottomSheetDialog
import com.project.autoservicemobile.ui.services.ServicesRecyclerAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CartFragment : Fragment(), AuthenticatedListener {

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
        _viewModel.isAuthenticated(object : CoroutinesErrorHandler {
            override fun onError(message: String) {
                Log.d("SignInBottomSheetDialog", "Error! $message")
            }
        })
    }

    override fun onPause() {
        super.onPause()
        _viewModel.isAuth.postValue(RequestResult.Loading())
    }

    override fun onAuthenticated() {
        //getCartData
    }

    private fun setup(){
       with(binding){
           totalText.text = _viewModel.totalText
           saleText.text = _viewModel.saleText
           subTotalText.text = _viewModel.subTotalText

           totalValueText.text = _viewModel.totalValueText
           saleValueText.text = _viewModel.saleValueText
           subTotalValueText.text = _viewModel.subTotalValueText

           promocodeInput.hint = _viewModel.promocodeHintText

           applyPromocodeBtn.text = _viewModel.applyBtnText
           applyPromocodeBtn.setOnClickListener{_ ->
               _viewModel.onApplyPromocodeClick()
           }

           createRegBtn.text = _viewModel.createRegBtnText
           createRegBtn.setOnClickListener{_ ->
               _viewModel.onCreateRegistrationClick()
           }

           cartRecycler.layoutManager = LinearLayoutManager(context)
           _viewModel.cartItems.observe(viewLifecycleOwner){
               cartRecycler.adapter = CartRecyclerAdapter(it,
                   {item -> {}}
               )
           }
       }

        _viewModel.isAuth.observe(viewLifecycleOwner){
            if (it is RequestResult.Error) {
                openSignBottomSheet()
            }
            if(it is RequestResult.Success){
                //getCartData
            }
        }
    }

    private fun openSignBottomSheet() {
        val modalBottomSheet = SignInOrUpBottomSheetDialog(this)
        modalBottomSheet.setCancelable(false)
        modalBottomSheet.show(
            requireActivity().supportFragmentManager,
            SignInOrUpBottomSheetDialog.TAG
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}