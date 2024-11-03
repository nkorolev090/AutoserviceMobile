package com.project.autoservicemobile.ui.profile

import androidx.fragment.app.viewModels
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.project.autoservicemobile.MainActivity
import com.project.autoservicemobile.R
import com.project.autoservicemobile.common.AuthenticatedListener
import com.project.autoservicemobile.common.CoroutinesErrorHandler
import com.project.autoservicemobile.databinding.FragmentProfileBinding
import com.project.autoservicemobile.ui.login.SignInOrUpBottomSheetDialog
import com.project.common.data.RequestResult
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : Fragment(), AuthenticatedListener {

    private var _binding: FragmentProfileBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val _viewModel: ProfileViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        val root: View = binding.root

        setup()

        return root;
    }

    private fun setup() {
        with(binding) {
            favoritesTitle.setText(R.string.favorites_text)
            carsTitle.setText(R.string.cars_text)

            appBar.setRightOnClickListener{
                navigateToUserData()
            }

            carsContainer.setOnClickListener{
                navigateToCars()
            }

            _viewModel.isAuth.observe(viewLifecycleOwner){
                if (it is RequestResult.Error) {
                    openSignBottomSheet()
                }
                if(it is RequestResult.Success){
                    //_viewModel.updateUserData(object : CoroutinesErrorHandler {
//                        override fun onError(message: String) {
//                            Log.d("SignInBottomSheetDialog", "Error! $message")
//                        }
//                    })
                }
            }

            _viewModel.profileData.observe(viewLifecycleOwner){
                if (it is RequestResult.Error) {

                }
                if(it is RequestResult.Success){
                    favoritesDescription.text = it.data.favoritesText
                    carsDescription.text = it.data.carsText
                }
            }


        }
    }

    override fun onResume() {
        super.onResume()
        _viewModel.isAuthenticated(object : CoroutinesErrorHandler {
            override fun onError(message: String) {
                Log.d("ProfileFragment", "Error! $message")
            }
        })
    }

    private fun openSignBottomSheet() {
        val modalBottomSheet = SignInOrUpBottomSheetDialog(this)
        modalBottomSheet.setCancelable(false)
        modalBottomSheet.show(
            requireActivity().supportFragmentManager,
            SignInOrUpBottomSheetDialog.TAG
        )
    }

    override fun onPause() {
        super.onPause()
        _viewModel.isAuth.postValue(RequestResult.Loading())
    }
    override fun onAuthenticated() {
//        _viewModel.updateUserData(object : CoroutinesErrorHandler {
//            override fun onError(message: String) {
//                Log.d("SignInBottomSheetDialog", "Error! $message")
//            }
//        })
    }

    override fun onNavigateToHomeFragment() {
        (requireActivity() as MainActivity).navController.navigate(R.id.action_profileFragment_to_navigation_home)
    }

    private fun navigateToUserData(){
        (requireActivity() as MainActivity).navController.navigate(R.id.action_profileFragment_to_navigation_userdata)
    }

    private fun navigateToCars(){
        (requireActivity() as MainActivity).navController.navigate(R.id.action_profileFragment_to_carsFragment)
    }
}