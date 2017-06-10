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

import es.voghdev.hellokotlin.features.order.Invoice
import junit.framework.Assert.*
import org.junit.Test

class AggregateOperationsTest {
    @Test
    fun `example usage of the "any" operator`() {
        val list = listOf(1, 2, 3, 4, 5, 6)

        assertFalse(list.any { it > 10 })
    }

    @Test
    fun `both "any" and "all" operators should work with Strings`() {
        val list = listOf("Hello", "We're", "playing", "with", "kotlin")

        assertTrue(list.any { it.length > 6 })
        assertTrue(list.all { it.length >= 1 })
    }

    @Test
    fun `"count" operator should count well`() {
        val list = listOf("One", "Two", "Three", "Four", "Four")

        assertEquals(2, list.count { it.equals("Four") })
        assertEquals(1, list.count { it.equals("Two") })
        assertEquals(0, list.count { it.equals("Six") })
    }

    @Test
    fun `fold operator should do the right thing`() {
        val list = listOf("ABC", "DEF", "GHI", "JKL", "MNO")

        assertEquals("abcdefghijklmno", list.fold("") { total, next -> total + next.toLowerCase() })
    }

    @Test
    fun `"foldRight" does the same, starting from the last item`() {
        val list = listOf("Lord", "Of", "The", "Rings")

        assertEquals("Lord Of The Rings", list.foldRightIndexed("") {
            index, total, next ->
            if (index < 3)
                total + " " + next
            else
                total + next
        })
    }

    @Test
    fun `maxBy maximizes a list depending on a function`() {
        val m = Invoice(5003L, 50f)
        val n = Invoice(5000L, 10f)

        val list = listOf(
                n,
                Invoice(5001L, 20f),
                Invoice(5002L, 30f),
                m
        )

        assertEquals(m, list.maxBy { it.amount })
        assertEquals(n, list.minBy { it.customerId })
    }

    @Test
    fun `"none" operator should work as expected`() {
        val list = listOf("Lord", "Of", "The", "Rings")

        assertTrue(list.none { it.length > 10 })
    }

    @Test
    fun `"sumBy" function should do its task`() {
        val list = listOf(3, 4, 5)

        assertEquals(-12, list.sumBy { -it })
    }
}
