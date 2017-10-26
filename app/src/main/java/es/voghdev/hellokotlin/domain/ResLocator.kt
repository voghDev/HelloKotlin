package es.voghdev.hellokotlin.domain

interface ResLocator {
    fun getString(resId: Int): String
    fun getStringArray(resId: Int): List<String>
}
