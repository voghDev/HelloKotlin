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
package es.voghdev.hellokotlin.global

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

abstract class Presenter<T1, T2>() {
    open suspend fun initialize() { /* Empty */
    }

    open suspend fun resume() { /* Empty */
    }

    open suspend fun pause() { /* Empty */
    }

    open fun destroy() {
        view = null
        navigator = null

        job.cancel()
    }

    var view: T1? = null
    var navigator: T2? = null

    val job = Job()

    val coroutineContext = Dispatchers.Main + job

    suspend fun <T> coroutine(
        context: CoroutineContext = Dispatchers.Main,
        block: suspend CoroutineScope.() -> T
    ): T = withContext(context, block)
}
