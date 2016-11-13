package es.voghdev.hellokotlin

import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).

 * @see [Testing documentation](http://d.android.com/tools/testing)
 */
class ExampleUnitTest {
    @Test
    @Throws(Exception::class)
    fun addition_isCorrect() {
        assertEquals(4, (2 + 2).toLong())
    }

    @Test
    fun kotlinShouldHaveOptionals() {
        var b: String? = null

        var l = b?.length

        assertEquals(null, l)
    }

    @Test
    fun listWithNullsExampleShouldWork() {
        val listWithNulls: List<String?> = listOf("A", null)
        for (item in listWithNulls) {
            item?.let {
                println(it) // prints A and ignores null
            }
        }
    }

    @Test
    fun elvisOperatorShouldWorkAndBeShorter() {
        var b: String? = null

        val oldStyleVersion: Int = if (b != null) b.length else -1
        val elvisOperatorVersion = b?.length ?: -1

        assertEquals(oldStyleVersion, -1)
        assertEquals(elvisOperatorVersion, -1)
    }
}