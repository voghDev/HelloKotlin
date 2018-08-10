package es.voghdev.hellokotlin.domain

import android.content.Context

class AndroidResLocator(val context: Context) : ResLocator {
    override fun getString(resId: Int): String {
        return context.getString(resId)
    }

    override fun getStringArray(resId: Int): List<String> {
        return context.resources?.getStringArray(resId)?.toList() ?: emptyList()
    }
}