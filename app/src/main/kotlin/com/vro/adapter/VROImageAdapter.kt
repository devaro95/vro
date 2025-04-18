package com.vro.adapter

import android.content.Context
import android.view.*
import android.widget.ImageView
import androidx.viewpager.widget.PagerAdapter
import com.vro.app.R

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
        val imageView = itemView.findViewById<ImageView>(R.id.ivImageAdapter)
        imageView.setImageResource(imageList[position])
        container.addView(itemView)
        return itemView
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }
}