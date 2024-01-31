package com.vro.dialog

import android.app.Dialog
import android.content.DialogInterface
import android.graphics.Color
import android.graphics.Point
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import androidx.viewbinding.ViewBinding
import com.vro.R
import org.koin.android.scope.createScope
import org.koin.core.component.KoinScopeComponent
import org.koin.core.scope.Scope

abstract class VRODialog<T : VRODialog.VRODialogData, VB : ViewBinding> : DialogFragment(), DialogInterface.OnShowListener, KoinScopeComponent {

    private var _binding: VB? = null

    protected val binding get() = _binding!!

    var dialogListener: VRODialogListener? = null

    override val scope: Scope by lazy { createScope(this) }

    var data: T? = null
        set(value) {
            field = value
            onDataSetup(value)
        }

    private var contentView: VB? = null

    private var isDismissed: Boolean = false

    protected open val isModal: Boolean = false

    protected abstract fun VB.setupData(data: T)

    protected abstract fun VB.onLoadFirstTime(data: T)

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

        contentView = binding
        data?.let { data -> binding.onLoadFirstTime(data) }
        onDataSetup(data)
        isCancelable = cancelable
        return binding.root
    }

    abstract fun createViewBinding(inflater: LayoutInflater, container: ViewGroup?): VB

    override fun show(manager: FragmentManager, tag: String?) {
        manager.apply {
            val oldFragment = findFragmentByTag(tag)
            val ft = beginTransaction()
            oldFragment?.let { ft.remove(oldFragment) }
            ft.add(this@VRODialog, tag)
            ft.commit()
        }
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.also { win ->
            val display = win.windowManager.defaultDisplay
            val size = Point()
            display.getSize(size)

            data?.run {
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

    fun updateData(updateAction: T?.() -> T): T {
        data = data.let(updateAction)
        return data.let(updateAction)
    }

    private fun onDataSetup(data: Any?) {
        if (data != null) {
            (data as? T)?.apply {
                contentView?.also { view ->
                    view.setupData(this)
                }
            } ?: throw Exception("Data type not matching with the dialog")
        }
    }

    override fun onShow(p0: DialogInterface?) {
        if (isDismissed)
            dismissAllowingStateLoss()
    }

    interface VRODialogData {
        val proportionWidth: Float?
        val proportionHeight: Float?
    }

    override fun onDestroyView() {
        contentView = null
        data = null
        dialogListener = null
        super.onDestroyView()
    }

    class Provider constructor(fragmentManager: FragmentManager, private val className: VRODialog<*, *>) : VRODialogProvider(fragmentManager) {
        override fun generateDialog(dialogData: VRODialogData?): VRODialog<*, *> = className
    }
}