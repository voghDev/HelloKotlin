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

import android.content.Context
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class SomeDetailPresenter(context: Context, userRepository: UserRepository?) :
        Presenter<SomeDetailPresenter.MVPView, SomeDetailPresenter.Navigator>() {

    override fun initialize() {

        doAsync {
//            val users = userRepository?.getUsers()
            val users = listOf<User>(
                    User(name = "John", email = "john@company.com"),
                    User(name = "Lisa", email = "lisa@company.com"))
            uiThread { view?.showUsers(users) }
        }
    }

    interface MVPView {
        fun showUsers(users: List<User>)
    }

    interface Navigator
}
