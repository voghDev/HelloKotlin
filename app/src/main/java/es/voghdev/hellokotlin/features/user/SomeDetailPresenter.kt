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

import es.voghdev.hellokotlin.R
import es.voghdev.hellokotlin.domain.ResLocator
import es.voghdev.hellokotlin.features.invoice.Invoice
import es.voghdev.hellokotlin.global.Presenter
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import org.jetbrains.anko.doAsync

class SomeDetailPresenter(val dispatcher: CoroutineDispatcher, val resLocator: ResLocator, val userRepository: UserRepository) :
    Presenter<SomeDetailPresenter.MVPView, SomeDetailPresenter.Navigator>() {

    override fun initialize() {
        val title = resLocator.getString(R.string.tech_debt_is_paid)
        view?.showTitle(title)

        doAsync {
            val users = userRepository.getUsers()

            if (users.isNotEmpty()) view?.showUsers(users) else view?.showEmptyCase()
        }
    }

    suspend fun onSomeEventHappened() {
        GlobalScope.launch(dispatcher) {
            async {
                userRepository.performSomeBlockingOperation()
            }.await()

            view?.showSomeResult()
        }
    }

    suspend fun onSomeOtherEventHappened() {
        GlobalScope.launch(dispatcher) {
            val result = async { userRepository.performSomeBlockingOperationWithResult() }.await()

            view?.showUsers(result)
        }
    }

    suspend fun onEventWithParameterHappened(customerId: Long) {
        GlobalScope.launch(dispatcher) {
            async {
                userRepository.performSomeBlockingOperationWithAParameter(Invoice(customerId = customerId, amount = 50f))
            }.await()
            view?.showSomeResult()
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
