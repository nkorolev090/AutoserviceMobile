package com.project.autoservicemobile.ui.services.slots

import android.app.DatePickerDialog
import androidx.fragment.app.viewModels
import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.project.autoservicemobile.MainActivity
import com.project.autoservicemobile.R
import com.project.autoservicemobile.common.CoroutinesErrorHandler
import com.project.autoservicemobile.common.ToCartListener
import com.project.autoservicemobile.databinding.FragmentSlotsBinding
import com.project.autoservicemobile.ui.cart.models.SlotUI
import com.project.autoservicemobile.ui.customViews.ErrorPage
import com.project.autoservicemobile.ui.services.models.ServiceUI
import com.project.common.data.RequestResult
import com.project.common.data.StatusCodeEnum
import dagger.hilt.android.AndroidEntryPoint
import java.time.LocalDate
import java.util.Calendar

@AndroidEntryPoint
class SlotsBottomSheetDialog(
    private val service: ServiceUI,
    private val toCartListener: ToCartListener
) : BottomSheetDialogFragment() {

    private var _binding: FragmentSlotsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.

    private val _viewModel: SlotsViewModel by viewModels()
    private val binding get() = _binding!!

    private var _errorPage: ErrorPage? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSlotsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        setup()

        return root;
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume")

        _viewModel.updateSlots(
            binding.dateInput.text.toString(),
            service.id,
            object : CoroutinesErrorHandler {
                override fun onError(message: String) {
                    Log.d(TAG, "Error! $message")
                }
            })
    }

    private fun setup() {
        with(binding) {
            titleText.text = _viewModel.title

            val date = LocalDate.now()
            dateInput.setText("${date.dayOfMonth}.${date.monthValue}.${date.year}")

            dateInput.setOnClickListener(dateClickListener)

            slotsRecycler.layoutManager = LinearLayoutManager(context)

            val dividerItemDecoration =
                DividerItemDecoration(requireContext(), RecyclerView.VERTICAL)
            dividerItemDecoration.setDrawable(resources.getDrawable(R.drawable.recycler_divider))
            slotsRecycler.addItemDecoration(dividerItemDecoration)

            slotsRecycler.adapter = SlotsRecyclerAdapter {
                onSlotSelected(it)
            }

            toCartBtn.isClickable = false
            toCartBtn.isEnabled = false
            toCartBtn.visibility = View.GONE
            toCartBtn.setText(R.string.to_cart)
            toCartBtn.setOnClickListener {
                if (_viewModel.selectedSlot != null) {
                    toCartListener.onAddOrRemoveToCart(_viewModel.selectedSlot!!.service!!)
                    Log.d(TAG, "${_viewModel.selectedSlot?.startDateText} ${_viewModel.selectedSlot?.service?.title}", )
                    _viewModel.addSlotToCart(
                        _viewModel.selectedSlot!!,
                        object : CoroutinesErrorHandler {
                            override fun onError(message: String) {
                                Log.d(TAG, "Error! $message")
                            }
                        }
                    )
                    dismiss()
                }
            }

            _viewModel.slots.observe(viewLifecycleOwner) {
                when (it) {
                    is RequestResult.Error -> {
                        Log.d(TAG, it.message.toString())
                        if(_errorPage == null && it.code is StatusCodeEnum) {
                            showErrorPage(it.code as StatusCodeEnum)
                        }
                        (slotsRecycler.adapter as SlotsRecyclerAdapter).items = listOf()
                        slotsRecycler.adapter?.notifyDataSetChanged()
                    }

                    is RequestResult.Loading -> Log.d(TAG, "Loading")
                    is RequestResult.Success -> {
                        if(_errorPage != null){
                            removeErrorPage()
                        }
                        (slotsRecycler.adapter as SlotsRecyclerAdapter).items = it.data
                        slotsRecycler.adapter?.notifyDataSetChanged()
                    }
                }
            }
        }
    }

    private fun onSlotSelected(it: SlotUI) {
        if (_viewModel.selectedSlot?.id == it.id) {
            _viewModel.selectedSlot = null
            if (binding.toCartBtn.isEnabled) hideBtnAnim()
        } else {
            _viewModel.selectedSlot = it
            _viewModel.selectedSlot!!.service = service
            if (binding.toCartBtn.isEnabled.not()) showBtnAnim()
        }
    }

    private fun showBtnAnim() {
        val anim = AnimationUtils.loadAnimation(requireContext(), R.anim.bottom_to_top_apperance)
        with(binding.toCartBtn) {
            visibility = View.VISIBLE
            startAnimation(anim)
            isClickable = true
            isEnabled = true
        }

    }

    private fun hideBtnAnim() {
        val anim = AnimationUtils.loadAnimation(requireContext(), R.anim.top_to_bottom_hide_appearance)
        with(binding.toCartBtn) {
            isClickable = false
            isEnabled = false
            startAnimation(anim)
            visibility = View.GONE
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
                errorPage.showWithAnim()
            }
            StatusCodeEnum.NO_CONTENT ->{
                _errorPage = ErrorPage.NoSlots(requireContext())
                val errorPage = _errorPage as ErrorPage.NoSlots
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

                errorPage.setOnButtonClick(dateClickListener)

                layout.addView(errorPage);
                errorPage.showWithAnim()
            }
            else -> {

            }
        }
    }

    private val dateClickListener = OnClickListener{
        // on below line we are getting
        // the instance of our calendar.
        val c = Calendar.getInstance()

        // on below line we are getting
        // our day, month and year.
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        // on below line we are creating a
        // variable for date picker dialog.
        val datePickerDialog = DatePickerDialog(
            // on below line we are passing context.
            requireContext(),
            { _, year, monthOfYear, dayOfMonth ->
                // on below line we are setting
                // date to our edit text.
                val dat = (dayOfMonth.toString() + "." + (monthOfYear + 1) + "." + year)
                binding.dateInput.setText(dat)

                _viewModel.updateSlots(
                    dat,
                    service.id,
                    object : CoroutinesErrorHandler {
                        override fun onError(message: String) {
                            Log.d(TAG, "Error! $message")
                        }
                    })
            },
            // on below line we are passing year, month
            // and day for the selected date in our date picker.
            year,
            month,
            day
        )
        // at last we are calling show
        // to display our date picker dialog.
        datePickerDialog.show()
    }

    private fun removeErrorPage(){
        if (_errorPage != null){
            _errorPage!!.hideWithAnim()
            binding.contentContainer.removeView(_errorPage)
            _errorPage = null
        }

    }

    companion object {
        const val TAG = "SlotsBottomSheetDialog"
    }
}