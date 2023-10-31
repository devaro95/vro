package com.vro.dialog

import android.util.Log
import androidx.fragment.app.FragmentManager
import androidx.viewbinding.ViewBinding
import com.vro.dialog.VRODialog.BaseDialogListener
import com.vro.dialog.VRODialog.VRODialogData

abstract class VRODialogProvider constructor(private val fragmentManager: FragmentManager) : BaseDialogProviderInterface {

    var dialog: VRODialog<VRODialogData, ViewBinding>? = null

    abstract fun generateDialog(dialogData: VRODialogData?): VRODialog<*, *>

    override fun show(dialogData: VRODialogData?) {
        fragmentManager.findFragmentByTag(getTag())?.also {
            dialog = (it as VRODialog<VRODialogData, ViewBinding>).apply {
                updateDialogData(this, dialogData)
            }
        } ?: also {
            if (dialog == null) {
                dialog = generateDialog(dialogData) as VRODialog<VRODialogData, ViewBinding>
            }
            dialog?.let { dialog ->
                updateDialogData(dialog, dialogData)
                fragmentManager.executePendingTransactions()
                if (!dialog.isVisible && !dialog.isAdded) {
                    dialog.show(fragmentManager, getTag())
                }

            }
        }
    }

    private fun updateDialogData(
        dialog: VRODialog<VRODialogData, ViewBinding>,
        dialogData: VRODialogData?,
    ) {
        dialog.dialogListener = dialogListener
        dialogData?.also {
            dialog.updateData {
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

    override var dialogListener: BaseDialogListener? = null
        set(value) {
            field = value
            dialog?.dialogListener = value
        }

    private fun getTag(): String {
        return dialog?.javaClass?.name?.toString() ?: javaClass.name.toString()
    }
}

interface BaseDialogProviderInterface {
    fun show(dialogData: VRODialogData?)
    fun hide()
    var dialogListener: BaseDialogListener?
}