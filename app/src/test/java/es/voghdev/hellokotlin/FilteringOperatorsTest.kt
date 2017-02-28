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

import junit.framework.Assert.assertEquals
import org.junit.Test

class FilteringOperatorsTest {
    @Test
    fun `"filter" operator should use only even elements`() {
        val list = listOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)

        assertEquals(5, list.filter { it % 2 == 0 }.size)
    }

    @Test
    fun `"filterNot" operator should use the rest`() {
        val list = listOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)

        assertEquals(5, list.filterNot { it % 2 == 0 }.size)
        assertEquals(listOf(1,3,5,7,9), list.filterNot { it % 2 == 0 })
    }

    @Test
    fun `"slice" operator can select various items from a list`() {
        val list = listOf("Spain", "Portugal", "France", "Morocco", "Belgium")

        assertEquals(listOf("Spain", "Portugal"), list.slice(listOf(0, 1)))
        assertEquals("Spain", list.slice(listOf(0, 1)).elementAt(0))
        assertEquals("Portugal", list.slice(listOf(0, 1)).elementAt(1))
    }

    @Test
    fun `"takeWhile" operator extracts element while a condition is satisfied`() {
        val list = listOf("Spain", "Portugal", "France", "Morocco", "Belgium")

         list.takeWhile { it.length > 5 }
    }
}
