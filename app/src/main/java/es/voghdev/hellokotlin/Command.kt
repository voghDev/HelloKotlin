package es.voghdev.hellokotlin

interface Command<T> {
    fun execute(): T
}