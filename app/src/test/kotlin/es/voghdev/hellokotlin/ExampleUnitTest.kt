/*
 * Copyright (C) 2017 Olmo Gallegos Hernández.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package es.voghdev.hellokotlin

import es.voghdev.hellokotlin.domain.Just
import es.voghdev.hellokotlin.domain.None
import es.voghdev.hellokotlin.domain.Option
import es.voghdev.hellokotlin.domain.model.Configuration
import es.voghdev.hellokotlin.features.order.Invoice
import es.voghdev.hellokotlin.global.startsWithUppercaseLetter
import org.jetbrains.anko.doAsync
import org.junit.Assert.*
import org.junit.Test

class ExampleUnitTest {
    val textWithThreeLines = """Text in
three lines
should have two carriage returns """.trimIndent()

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

    @Test
    fun shouldExecuteAsyncBlocksUsingDoAsync() {
        doAsync {
            println("This is doAsync(). It belongs to Anko")
        }
    }

    @Test
    fun incAndDecShouldWork() {
        val a = 5;

        assertEquals(6, a.inc())
        assertEquals(4, a.dec())
    }

    @Test
    fun unarySignOperatorsShouldWork() {
        val a = 10
        val b = a.unaryPlus()
        val c = a.unaryMinus()

        assertEquals(10, b)
        assertEquals(+10, b)
        assertEquals(-10, c)

        val abs = (b + c).unaryPlus()

        assertEquals(0, abs)
    }

    @Test
    fun `should be able to use basic array operations`() {
        val a = listOf(1, 2, 3)

        assertEquals(1, a[0])
        assertEquals(2, a[1])

        // a[1] = 7 does not compile, a is not mutable

        val b = mutableListOf(1, 2, 3, 4, 5, 6, 7, 8)

        assertEquals(2, b[1])

        b[1] = 5

        assertEquals(5, b[1])
    }

    @Test
    fun `the "with" operator should work`() {
        val arr = listOf("Hello", "We", "Are", "Learning", "Kotlin")

        with(arr) {
            println(size)

            val b = this.asReversed()

            assertEquals(5, b.size)
            assertEquals("Kotlin", b[0])
        }
    }

    inline fun sampleInline(code: () -> Unit) {
        if (1 == 1) {
            code()
        }
    }

    @Test
    fun `should be able to use inline functions in a Test`() {
        sampleInline {
            println("printed in an inline function")
        }
    }

    @Test
    fun `should verify inside an asynchronous call`() {

    }

    @Test
    fun `should create a Configuration object`() {
        val conf = Configuration(mapOf(
                "height" to 1920,
                "width" to 1080,
                "dp" to 445,
                "deviceName" to "Google Nexus 5"
        ))

        assertNotNull(conf)
        assertEquals(1080, conf.width)
    }

    @Test
    fun `should calculate properly if some strings start with an uppercase letter`() {
        assertFalse("lowerCaseString".startsWithUppercaseLetter())
        assertTrue("This is a String".startsWithUppercaseLetter())
        assertFalse("1234 Starts with a number".startsWithUppercaseLetter())
        assertFalse("".startsWithUppercaseLetter())
        assertFalse(" ".startsWithUppercaseLetter())
        assertFalse("_".startsWithUppercaseLetter())
    }

    @Test
    fun `should be able to create an instance of an option class with the positive case`() {
        val invoice = Invoice(10L, 5f)

        val option: Option<Invoice> = Just(invoice)

        assertNotNull(option)
    }

    @Test
    fun `should be able to create an instance of an option class with the absence of object case`() {
        val option = None

        assertNotNull(option)
        assertTrue(option is Option<Nothing>)
    }

    @Test
    fun `should pass this test with curious stuff from Kotlin`() {
        val text = """Text in
            two lines """.trimIndent()

        assertTrue(text.contains("\n"))
    }

    @Test
    fun `should count two carriage returns in text with three lines`() {
        val map = textWithThreeLines.map { it }

        val filtered = map.filter { it.equals('\n') }

        val count = filtered.count()

        assertNotNull(filtered)

        assertEquals(2, count)
    }

    @Test
    fun `should do the same as previous in a more compact, FP-like version`() {
        val count = textWithThreeLines
                .map { it }
                .filter { it.equals('\n') }
                .count()

        assertEquals(2, count)
    }
}