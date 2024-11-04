package com.project.autoservicemobile.ui.registrations

import androidx.fragment.app.viewModels
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.project.autoservicemobile.MainActivity
import com.project.autoservicemobile.R
import com.project.autoservicemobile.common.BaseFragment
import com.project.autoservicemobile.common.CoroutinesErrorHandler
import com.project.autoservicemobile.common.DismissListener
import com.project.autoservicemobile.databinding.FragmentRegistrationsBinding
import com.project.autoservicemobile.ui.customViews.ErrorPage
import com.project.autoservicemobile.ui.registrations.details.RegistrationDetailsBottomSheetDialog
import com.project.autoservicemobile.ui.registrations.models.RegistrationUI
import com.project.autoservicemobile.ui.services.slots.SlotsBottomSheetDialog
import com.project.autoservicemobile.ui.services.slots.SlotsRecyclerAdapter
import com.project.common.data.RequestResult
import com.project.common.data.StatusCodeEnum
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegistrationsFragment : BaseFragment(), DismissListener {

    private var _binding: FragmentRegistrationsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val _viewModel: RegistrationsViewModel by viewModels()

    private var _errorPage: ErrorPage? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegistrationsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        setup()

        return root;
    }

    override fun onResume() {
        super.onResume()
        _viewModel.updateRegistrations(coroutinesErrorHandler)
    }

    private fun setup(){
        with(binding){
            appBar.setLeftOnClickListener(View.OnClickListener {
                onGoToHomeClick()
            })

            registrationsRecycler.layoutManager = LinearLayoutManager(context)
            val dividerItemDecoration =
                DividerItemDecoration(requireContext(), RecyclerView.VERTICAL)
            dividerItemDecoration.setDrawable(resources.getDrawable(R.drawable.recycler_divider))
            registrationsRecycler.addItemDecoration(dividerItemDecoration)
            registrationsRecycler.adapter = RegistrationsRecyclerAdapter{
                openRegistrationDetails(it)
            }

            _viewModel.registrations.observe(viewLifecycleOwner){
                when (it) {
                    is RequestResult.Error -> {
                        Log.d(SlotsBottomSheetDialog.TAG, it.message.toString())
                        if(_errorPage == null && it.code is StatusCodeEnum) {
                            showErrorPage(it.code as StatusCodeEnum)
                        }
                        (registrationsRecycler.adapter as RegistrationsRecyclerAdapter).items = listOf()
                        registrationsRecycler.adapter?.notifyDataSetChanged()
                    }

                    is RequestResult.Loading -> Log.d(TAG, "Loading")
                    is RequestResult.Success -> {
                        if(_errorPage != null){
                            removeErrorPage()
                        }
                        (registrationsRecycler.adapter as RegistrationsRecyclerAdapter).items = it.data
                        registrationsRecycler.adapter?.notifyDataSetChanged()
                    }
                }
            }
        }

    }

    private fun showErrorPage(statusCode: StatusCodeEnum) {
        when(statusCode){
            StatusCodeEnum.CONNECTION_TIMED_OUT ->{
                _errorPage = ErrorPage.NoConnection(requireContext())
                val errorPage = _errorPage as ErrorPage.NoConnection
                errorPage.id = View.generateViewId()

                val layout = binding.contentContainer
                val layoutParams = ConstraintLayout.LayoutParams(
                    ConstraintLayout.LayoutParams.MATCH_PARENT,
                    ConstraintLayout.LayoutParams.MATCH_PARENT)
                layoutParams.topToTop = layout.id
                layoutParams.startToStart = layout.id
                layoutParams.endToEnd = layout.id
                layoutParams.bottomToBottom = layout.id

                errorPage.layoutParams = layoutParams
                layout.addView(errorPage);
            }
            StatusCodeEnum.NO_CONTENT ->{
                _errorPage = ErrorPage.NoContent(requireContext())
                val errorPage = _errorPage as ErrorPage.NoContent
                errorPage.id = View.generateViewId()

                val layout = binding.contentContainer
                val layoutParams = ConstraintLayout.LayoutParams(
                    ConstraintLayout.LayoutParams.MATCH_PARENT,
                    ConstraintLayout.LayoutParams.MATCH_PARENT)
                layoutParams.topToTop = layout.id
                layoutParams.startToStart = layout.id
                layoutParams.endToEnd = layout.id
                layoutParams.bottomToBottom = layout.id

                errorPage.layoutParams = layoutParams
                layout.addView(errorPage);
            }
            else -> {

            }
        }
    }

    override fun onChildDismiss() {
        _viewModel.updateRegistrations(coroutinesErrorHandler)
    }

    private fun removeErrorPage(){
        binding.contentContainer.removeView(_errorPage)
        _errorPage = null
    }

    private fun onGoToHomeClick(){
        (requireActivity() as MainActivity).navController.navigate(R.id.action_registrationsFragment_to_navigation_home)
    }

    private fun openRegistrationDetails(registration: RegistrationUI){
        val modalBottomSheet = RegistrationDetailsBottomSheetDialog(registration, this)
        modalBottomSheet.show(requireActivity().supportFragmentManager, RegistrationDetailsBottomSheetDialog.TAG)
    }

    companion object{
        const val TAG = "RegistrationsFragment"
    }
}