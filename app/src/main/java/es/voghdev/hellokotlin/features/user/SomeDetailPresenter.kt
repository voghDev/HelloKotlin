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
package es.voghdev.hellokotlin.features.user

import es.voghdev.hellokotlin.global.Presenter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SomeDetailPresenter(val userRepository: UserRepository) :
    Presenter<SomeDetailPresenter.MVPView, SomeDetailPresenter.Navigator>(), CoroutineScope {

    override suspend fun initialize() = Unit

    override suspend fun resume() {
        requestUsers()
    }

    override fun destroy() = Unit

    private fun requestUsers() = launch {
        coroutine {
            val result = userRepository.getUsers()

            withContext(Dispatchers.Main) {
                view?.showUsers(result)
            }
        }
    }

    interface MVPView {
        fun showUsers(users: List<User>)
        fun showEmptyCase()
        fun showSomeResult()
        fun showTitle(title: String)
    }

    interface Navigator
}
