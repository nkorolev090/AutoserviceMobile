package com.project.autoservicemobile.common

import android.util.Log
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import com.project.autoservicemobile.R
import com.project.autoservicemobile.ui.cart.CartFragment
import com.project.autoservicemobile.ui.customViews.ErrorPage
import com.project.autoservicemobile.ui.home.HomeFragment
import com.project.autoservicemobile.ui.registrations.RegistrationsFragment
import com.project.autoservicemobile.ui.services.ServicesFragment
import com.project.common.data.StatusCodeEnum

open class BaseFragment : Fragment() {
    protected var _errorPage: ErrorPage? = null

    val coroutinesErrorHandler = object : CoroutinesErrorHandler {
        override fun onError(message: String) {
            Log.d(TAG, "Error! $message")
        }
    }

    val TAG = this::class.simpleName ?: "ErrorClassName"

    protected fun showErrorPage(statusCode: StatusCodeEnum?, layout: ConstraintLayout) {
        when (statusCode) {
            StatusCodeEnum.NO_CONTENT -> {
                _errorPage = ErrorPage.NoContent(requireContext())
                val errorPage = _errorPage as ErrorPage.NoContent
                errorPage.id = View.generateViewId()

                val layoutParams = ConstraintLayout.LayoutParams(
                    ConstraintLayout.LayoutParams.MATCH_PARENT,
                    ConstraintLayout.LayoutParams.MATCH_PARENT
                )
                layoutParams.topToTop = layout.id
                layoutParams.startToStart = layout.id
                layoutParams.endToEnd = layout.id
                layoutParams.bottomToBottom = layout.id

                errorPage.layoutParams = layoutParams

                when (this) {
                    is HomeFragment -> {
                        errorPage.errorTextView.setText(R.string.no_content_text)
                    }

                    is ServicesFragment -> {
                        errorPage.errorTitleTextView.setText(R.string.error_services_text)
                        errorPage.errorTextView.setText(R.string.no_content_services_text)
                    }

                    is CartFragment -> {
                        errorPage.errorTitleTextView.setText(R.string.error_cart_text)
                        errorPage.errorTextView.setText(R.string.no_content_cart_text)
                    }

                    is RegistrationsFragment -> {
                        errorPage.errorTitleTextView.setText(R.string.error_registrations_text)
                        errorPage.errorTextView.setText(R.string.no_content_reqistrations_text)
                    }
                }
                layout.addView(errorPage);
                errorPage.bringToFront()
            }

            else -> {
                _errorPage = ErrorPage.NoConnection(requireContext())
                val errorPage = _errorPage as ErrorPage.NoConnection
                errorPage.id = View.generateViewId()

                val layoutParams = ConstraintLayout.LayoutParams(
                    ConstraintLayout.LayoutParams.MATCH_PARENT,
                    ConstraintLayout.LayoutParams.MATCH_PARENT
                )
                layoutParams.topToTop = layout.id
                layoutParams.startToStart = layout.id
                layoutParams.endToEnd = layout.id
                layoutParams.bottomToBottom = layout.id

                errorPage.layoutParams = layoutParams
                layout.addView(errorPage);
                errorPage.bringToFront()
            }
        }
    }

    protected fun removeErrorPage(layout: ConstraintLayout) {
        if (_errorPage != null) {
            layout.removeView(_errorPage)
            _errorPage = null
        }
    }
}