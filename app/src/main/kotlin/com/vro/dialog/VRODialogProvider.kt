package com.vro.dialog

import android.util.Log
import androidx.fragment.app.FragmentManager
import androidx.viewbinding.ViewBinding

@Suppress("UNCHECKED_CAST")
abstract class VRODialogProvider(private val fragmentManager: FragmentManager) : BaseDialogProviderInterface {

    var dialog: VRODialogBasics<VRODialogState, ViewBinding>? = null

    abstract fun generateDialog(dialogState: VRODialogState?): VRODialogBasics<*, *>

    override fun show(dialogState: VRODialogState?) {
        fragmentManager.findFragmentByTag(getTag())?.also {
            dialog = (it as VRODialogBasics<VRODialogState, ViewBinding>).apply {
                updateDialogState(this, dialogState)
            }
        } ?: also {
            if (dialog == null) {
                dialog = generateDialog(dialogState) as VRODialogBasics<VRODialogState, ViewBinding>
            }
            dialog?.let { dialog ->
                updateDialogState(dialog, dialogState)
                fragmentManager.executePendingTransactions()
                if (!dialog.isVisible && !dialog.isAdded) {
                    dialog.show(fragmentManager, getTag())
                }
            }
        }
    }

    private fun updateDialogState(
        dialog: VRODialogBasics<VRODialogState, ViewBinding>,
        dialogState: VRODialogState?,
    ) {
        dialog.dialogListener = dialogListener
        dialogState?.also {
            dialog.updateState {
                it
            }
        }
    }

    override fun hide() {
        dialog?.let {
            if (!it.isHidden) {
                Log.d(getTag(), "Alternative dialog totally hidden")
                it.dismiss()
            }
            fragmentManager.beginTransaction().remove(it).commit()
        }
        dialog = null
    }

    override var dialogListener: VRODialogListener? = null
        set(value) {
            field = value
            dialog?.dialogListener = value
        }

    private fun getTag(): String {
        return dialog?.javaClass?.name?.toString() ?: javaClass.name.toString()
    }
}

interface BaseDialogProviderInterface {
    fun show(dialogState: VRODialogState?)
    fun hide()
    var dialogListener: VRODialogListener?
}