package com.project.autoservicemobile.ui.profile

import androidx.fragment.app.viewModels
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.project.autoservicemobile.MainActivity
import com.project.autoservicemobile.R
import com.project.autoservicemobile.common.AuthenticatedListener
import com.project.autoservicemobile.common.BaseFragment
import com.project.autoservicemobile.common.DismissListener
import com.project.autoservicemobile.databinding.FragmentProfileBinding
import com.project.autoservicemobile.ui.login.SignInOrUpBottomSheetDialog
import com.project.autoservicemobile.ui.profile.models.NavigationItemEnum
import com.project.autoservicemobile.ui.registrations.RegistrationsRecyclerAdapter
import com.project.autoservicemobile.ui.registrations.details.RegistrationDetailsBottomSheetDialog
import com.project.autoservicemobile.ui.registrations.models.RegistrationUI
import com.project.common.data.RequestResult
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : BaseFragment(), AuthenticatedListener, DismissListener {

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
            carsTitle.setText(R.string.your_cars_text)
            registrationsTitle.setText(R.string.registrations_title)
            locationTitle.setText(R.string.location_title)
            locationDescription.setText((R.string.location_description))
            navigationView.navigationMutableList.value = listOf(
                NavigationItemEnum.FAQ,
                NavigationItemEnum.CALL,
                NavigationItemEnum.EMAIL
            )

            registrationsRecyclerView.layoutManager = LinearLayoutManager(context)
            (registrationsRecyclerView.layoutManager as LinearLayoutManager).orientation = LinearLayoutManager.HORIZONTAL
            val dividerItemDecoration =
                DividerItemDecoration(requireContext(), RecyclerView.HORIZONTAL)
            dividerItemDecoration.setDrawable(resources.getDrawable(R.drawable.recycler_divider))
            registrationsRecyclerView.addItemDecoration(dividerItemDecoration)
            registrationsRecyclerView.adapter = RegistrationsRecyclerAdapter {
                openRegistrationDetails(it)
            }

            appBar.setRightOnClickListener{
                navigateToUserData()
            }

            carsContainer.setOnClickListener{
                navigateToCars()
            }

            middleContainer.setOnClickListener{
                navigateToStations()
            }

            _viewModel.isAuth.observe(viewLifecycleOwner){
                when(it){
                    is RequestResult.Error -> openSignBottomSheet()
                    is RequestResult.Loading -> {}
                    is RequestResult.Success -> {}
                }
            }

            _viewModel.profileData.observe(viewLifecycleOwner){
                when(it){
                    is RequestResult.Error -> TODO()
                    is RequestResult.Loading -> {
                        favoritesShimmer.visibility = View.VISIBLE
                        carsShimmer.visibility = View.VISIBLE
                        favoritesDescription.visibility = View.GONE
                        carsDescription.visibility = View.GONE
                    }
                    is RequestResult.Success -> {
                        favoritesDescription.visibility = View.VISIBLE
                        carsDescription.visibility = View.VISIBLE
                        favoritesShimmer.visibility = View.GONE
                        carsShimmer.visibility = View.GONE

                        favoritesDescription.text = it.data.favoritesText
                        carsDescription.text = it.data.carsText
                    }
                }
            }

            _viewModel.registrations.observe(viewLifecycleOwner){
                when(it){
                    is RequestResult.Error -> {
                        registrationsTitle.visibility = View.GONE
                        recyclerContainer.visibility = View.GONE
                    }
                    is RequestResult.Loading -> {
                        registrationsTitle.visibility = View.VISIBLE
                        recyclerContainer.visibility = View.VISIBLE

                        registrationShimmer.visibility = View.VISIBLE
                        registrationsRecyclerView.visibility = View.GONE
                    }
                    is RequestResult.Success -> {
                        registrationsTitle.visibility = View.VISIBLE
                        recyclerContainer.visibility = View.VISIBLE

                        registrationsRecyclerView.visibility = View.VISIBLE
                        registrationShimmer.visibility = View.GONE

                        (registrationsRecyclerView.adapter as RegistrationsRecyclerAdapter).items = it.data
                        registrationsRecyclerView.adapter!!.notifyDataSetChanged()
                    }
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        _viewModel.isAuthenticated(coroutinesErrorHandler)
        _viewModel.updateRegistrations(coroutinesErrorHandler)
    }

    override fun onStart() {
        super.onStart()

        (requireActivity() as MainActivity).showNavBar()
        (requireActivity() as MainActivity).changeSystemNavBarColor(com.google.android.material.R.attr.colorSecondary)
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
        _viewModel.updateRegistrations(coroutinesErrorHandler)
    }
    private fun openRegistrationDetails(registration: RegistrationUI){
        val modalBottomSheet = RegistrationDetailsBottomSheetDialog(registration, this)
        modalBottomSheet.show(requireActivity().supportFragmentManager, RegistrationDetailsBottomSheetDialog.TAG)
    }

    override fun onChildDismiss() {
        _viewModel.updateRegistrations(coroutinesErrorHandler)
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

    private fun navigateToStations(){
        (requireActivity() as MainActivity).navController.navigate((R.id.action_profileFragment_to_mapFragment))
    }
}