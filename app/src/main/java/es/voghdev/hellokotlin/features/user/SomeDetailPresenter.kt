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

import android.content.Context
import com.appandweb.peep.global.coroutine
import es.voghdev.hellokotlin.features.order.Invoice
import es.voghdev.hellokotlin.global.Presenter
import org.jetbrains.anko.doAsync

class SomeDetailPresenter(val context: Context, val userRepository: UserRepository) :
        Presenter<SomeDetailPresenter.MVPView, SomeDetailPresenter.Navigator>() {

    override fun initialize() {
        doAsync() {
            val users = userRepository.getUsers()

            if (users.isNotEmpty())
                view?.showUsers(users)
            else
                view?.showEmptyCase()
        }
    }

    suspend fun onSomeEventHappened() {
        coroutine {
            userRepository.performSomeBlockingOperation()
        }.await()

        view?.showSomeResult()
    }

    suspend fun onSomeOtherEventHappened() {
        coroutine {
            userRepository.performSomeBlockingOperationWithResult()
        }.await().let { result ->
            view?.showUsers(result)
        }
    }

    suspend fun onEventWithParameterHappened(customerId: Long) {
        coroutine {
            userRepository.performSomeBlockingOperationWithAParameter(Invoice(customerId = customerId, amount = 50f))
        }.await().let { result ->
            view?.showSomeResult()
        }
    }

    interface MVPView {
        fun showUsers(users: List<User>)
        fun showEmptyCase()
        fun showSomeResult()
    }

    interface Navigator
}
