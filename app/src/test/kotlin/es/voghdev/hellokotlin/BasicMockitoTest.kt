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

import es.voghdev.hellokotlin.features.invoice.Invoice
import es.voghdev.hellokotlin.features.user.User
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertNotNull
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentCaptor
import org.mockito.Captor
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import org.mockito.MockitoAnnotations

class BasicMockitoTest() {

    @Captor val argumentCaptor: ArgumentCaptor<Float>? = null

    @Mock val mockUser: User? = null

    @Before fun setUp() {
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun shouldNotThrowAnExceptionWhenMockingFinalClassesAnymore() {
        val user = mock(User::class.java)
        `when`(user.address).thenReturn("Santa Paula Av. 137")

        assertNotNull(user)

        assertEquals("Santa Paula Av. 137", user.address)
    }

    @Test
    fun shouldInitializeAnnotationsTheSameWayThanJava() {
        assertNotNull(argumentCaptor)
        assertNotNull(mockUser)
    }

    @Test
    fun shouldMockOpenEntities() {
        val inv = mock(Invoice::class.java)
        assertNotNull(inv)

        `when`(inv.amount).thenReturn(50f)

        val result = inv.amount

        assertEquals(50f, result)
    }
}
