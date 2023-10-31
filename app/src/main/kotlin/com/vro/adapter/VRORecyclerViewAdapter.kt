package com.vro.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.CallSuper
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.vro.adapter.VRORecyclerViewAdapter.VROBaseViewHolder
import org.koin.core.component.KoinComponent

abstract class VRORecyclerViewAdapter<I, VB : ViewBinding>(
    diffCallback: DiffUtil.ItemCallback<I> = getDefaultDiffCallback()
) : ListAdapter<I, VROBaseViewHolder<I, VB>>(diffCallback), KoinComponent {

    protected abstract fun VB.bind(item: I, position: Int)

    final override fun onBindViewHolder(holder: VROBaseViewHolder<I, VB>, position: Int) {}

    protected open val enableMultiViewHolder: ((view: ViewGroup, viewType: Int) -> VROBaseAdapterViewHolder)? = null

    abstract fun createViewBinding(inflater: LayoutInflater, container: ViewGroup?): VB?

    protected lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VROBaseViewHolder<I, VB> {
        context = parent.context
        return enableMultiViewHolder?.invoke(parent, viewType)
            ?: run {
                createViewBinding(LayoutInflater.from(parent.context), parent)?.let {
                    VROBaseAdapterViewHolder(it)
                }
            } ?: throw Exception("Adapter must have a binding. Set view on enableMultiViewHolder or using createViewBinding function")
    }

    open fun VB.onContainerClickListener(item: I, position: Int) = Unit

    override fun onBindViewHolder(holder: VROBaseViewHolder<I, VB>, position: Int, payloads: MutableList<Any>) {
        super.onBindViewHolder(holder, position, payloads)
        holder.bind(getItem(position), position)

    }

    @CallSuper
    open fun updateList(listUpdate: List<I>) {
        submitList(listUpdate)
    }

    @CallSuper
    open fun addItem(item: I, position: Int = itemCount) {
        currentList.add(position, item)
        submitList(currentList)
    }

    @CallSuper
    open fun removeItem(position: Int) {
        currentList.removeAt(position)
        submitList(currentList)
    }

    protected open inner class VROBaseAdapterViewHolder(private val view: VB) : VROBaseViewHolder<I, VB>(view) {
        override fun bind(item: I, position: Int) {
            view.bind(item, position)
            view.root.setOnClickListener { view.onContainerClickListener(item, position) }
        }
    }

    abstract class VROBaseViewHolder<I, VB : ViewBinding>(view: VB) : RecyclerView.ViewHolder(view.root) {
        abstract fun bind(item: I, position: Int)
    }

    companion object {
        fun <I> getDefaultDiffCallback() = object : DiffUtil.ItemCallback<I>() {
            override fun areItemsTheSame(oldItem: I & Any, newItem: I & Any): Boolean {
                return oldItem == newItem
            }

            @SuppressLint("DiffUtilEquals")
            override fun areContentsTheSame(oldItem: I & Any, newItem: I & Any): Boolean {
                return oldItem.equals(newItem)
            }
        }
    }
}