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

import es.voghdev.hellokotlin.features.user.User
import junit.framework.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.MockitoAnnotations

class ElvisOperatorTest() {

    @Before fun setUp() {
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun `should use elvis operator with a null array`() {
        val users: List<User>? = null

        val result = "${users?.size ?: "No"} users"

        assertEquals("No users", result)
    }

    @Test
    fun `should use elvis operator with a non-null array`() {
        val users: List<User>? = listOf(
                User(name = "John"),
                User(name = "Anne")
        )

        val result = "${users?.size ?: "No"} users"

        assertEquals("2 users", result)
        assertEquals(2, users?.size)
    }
}
