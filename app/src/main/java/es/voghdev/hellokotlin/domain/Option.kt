package es.voghdev.hellokotlin.domain

sealed class Option<A>

object None : Option<Nothing>()
data class Just<A>(val a: A) : Option<A>()
