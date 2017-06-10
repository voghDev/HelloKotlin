package es.voghdev.hellokotlin.features.order

import kotlin.properties.Delegates

class Order {
    lateinit var invoice : Invoice

    var observations : String by Delegates.observable("") {
        p, old, new -> 1
    }

    companion object {
        val defaultPrefix : String = "#0071"
    }
}
