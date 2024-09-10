package com.vro.dialog

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.DialogInterface
import android.graphics.Color
import android.graphics.Point
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.view.*
import androidx.annotation.RequiresApi
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import androidx.viewbinding.ViewBinding
import com.vro.R
import org.koin.android.scope.createScope
import org.koin.core.component.KoinScopeComponent
import org.koin.core.scope.Scope

@SuppressLint("NewApi")
@Suppress("UNCHECKED_CAST", "DEPRECATION")
abstract class VRODialogBasics<S : VRODialogState, VB : ViewBinding> : DialogFragment(), DialogInterface.OnShowListener, KoinScopeComponent {
    private var _binding: VB? = null

    protected val binding get() = _binding

    var dialogListener: VRODialogListener? = null

    override val scope: Scope by lazy { createScope(this) }

    private var state: S? = null
        set(value) {
            field = value
            state?.let { state ->
                binding?.let { binding ->
                    onViewUpdate(binding, state)
                }
            }
        }

    private var isDismissed: Boolean = false

    protected open val isModal: Boolean = false

    protected abstract fun onViewUpdate(binding: VB, state: S)

    protected abstract fun VB.onLoadFirstTime(state: S)

    protected open fun initialize(binding: VB, state: S) = Unit

    abstract val cancelable: Boolean

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.setOnShowListener(this)
        dialog.setOnKeyListener { _, keyCode, event ->
            dialogListener?.let {
                if (keyCode == KeyEvent.KEYCODE_BACK && event.action == KeyEvent.ACTION_DOWN && cancelable) {
                    it.onBackPressed()
                }
            }
            false
        }

        return dialog
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = createViewBinding(inflater, container)
        dialog?.window?.apply {
            setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            requestFeature(Window.FEATURE_NO_TITLE)
        }
        state?.let { state -> initialize(binding!!, state) }
        isCancelable = cancelable
        return binding!!.root
    }

    abstract fun createViewBinding(inflater: LayoutInflater, container: ViewGroup?): VB

    override fun show(manager: FragmentManager, tag: String?) {
        manager.apply {
            val oldFragment = findFragmentByTag(tag)
            val ft = beginTransaction()
            oldFragment?.let { ft.remove(oldFragment) }
            ft.add(this@VRODialogBasics, tag)
            ft.commit()
        }
    }

    @RequiresApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    override fun onStart() {
        super.onStart()
        dialog?.window?.also { win ->
            val display = win.windowManager.defaultDisplay
            val size = Point()
            display.getSize(size)

            state?.run {
                if (isModal) {
                    win.setWindowAnimations(R.style.InfoDialogAnimation)
                    win.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
                } else {
                    val width = proportionWidth?.let { (it * size.x).toInt() }
                        ?: ViewGroup.LayoutParams.WRAP_CONTENT
                    val height = proportionHeight?.let { (it * size.y).toInt() }
                        ?: ViewGroup.LayoutParams.WRAP_CONTENT
                    win.setLayout(width, height)
                }
            }
        }
    }

    fun updateState(updateAction: S?.() -> S): S {
        state = state.let(updateAction)
        return state.let(updateAction)
    }

    override fun onShow(p0: DialogInterface?) {
        if (isDismissed)
            dismissAllowingStateLoss()
    }

    override fun onDestroyView() {
        state = null
        dialogListener = null
        super.onDestroyView()
    }
}