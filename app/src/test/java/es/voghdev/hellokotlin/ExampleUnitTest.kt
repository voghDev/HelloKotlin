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

import org.jetbrains.anko.custom.async
import org.jetbrains.anko.doAsync
import org.junit.Assert.assertEquals
import org.junit.Test
import java.util.concurrent.Executors

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

    @Test
    fun shouldExecuteAsyncBlocks() {
        val executor = Executors.newScheduledThreadPool(1)
        async(executor) {
            println("This is async(). It is deprecated")
        }
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
    fun shouldBeAbleToUseBasicArrayOperations() {
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
    fun withOperatorShouldWork() {
        val arr = listOf("Hello", "We", "Are", "Learning", "Kotlin")

        with(arr) {
            println(size)

            val b = this.asReversed()

            assertEquals(5, b.size)
            assertEquals("Kotlin", b[0])
        }
    }

    inline fun sampleInline(code: () -> Unit) {
        if( 1 == 1 ) {
            code()
        }
    }

    @Test
    fun shouldUseInlineFunctionsInATest() {
        sampleInline {
            println("printed in an inline function")
        }
    }

    @Test
    fun shouldVerifyInsideAnAsynchronousCall() {

    }
}