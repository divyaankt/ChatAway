package com.example.divyaank.chataway.recyclerview.item

import android.content.Context
import com.example.divyaank.chataway.R
import com.example.divyaank.chataway.glide.GlideApp
import com.example.divyaank.chataway.model.ImageMessage
import com.example.divyaank.chataway.model.Message
import com.example.divyaank.chataway.util.StorageUtil
import com.xwray.groupie.kotlinandroidextensions.ViewHolder
import kotlinx.android.synthetic.main.item_image_message.*


class ImageMessageItem(val message: ImageMessage,
                       val context: Context): MessageItem(message){
    override fun bind(viewHolder: ViewHolder, position: Int) {
        super.bind(viewHolder, position)
        GlideApp.with(context)
                .load(StorageUtil.pathToReference(message.imagePath))
                .placeholder(R.drawable.ic_image_black_24dp)
                .into(viewHolder.imageView_message_image)
    }

    override fun getLayout() = R.layout.item_image_message

    override fun isSameAs(other: com.xwray.groupie.Item<*>?): Boolean {
        if (other !is ImageMessageItem)
            return false
        if (this.message != other.message)
            return false
        return true
    }

    override fun equals(other: Any?): Boolean {
        return isSameAs(other as? ImageMessageItem)
    }

    override fun hashCode(): Int {
        var result = message.hashCode()
        result = 31 * result + context.hashCode()
        return result
    }
}