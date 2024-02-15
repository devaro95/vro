package com.vro.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.viewpager.widget.PagerAdapter
import com.vro.R

class VROImageAdapter(
    val context: Context,
    private val imageList: List<Int>,
) : PagerAdapter() {

    override fun getCount(): Int {
        return imageList.size
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val itemView = LayoutInflater.from(context).inflate(R.layout.item_image_adapter, container, false)
        val imageView = itemView.findViewById<ImageView>(R.id.ivImageAdapter).apply {
            setImageResource(imageList[position])
        }
        container.addView(imageView, position)
        return imageView
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }
}