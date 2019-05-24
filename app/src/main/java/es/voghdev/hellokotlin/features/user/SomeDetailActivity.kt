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

import android.os.Bundle
import android.widget.TextView
import es.voghdev.hellokotlin.R
import es.voghdev.hellokotlin.features.user.datasource.GetUsersApiDataSource
import es.voghdev.hellokotlin.features.user.datasource.GetUsersDBDataSource
import es.voghdev.hellokotlin.features.user.datasource.InsertUserApiDataSource
import es.voghdev.hellokotlin.global.BaseActivity
import kotlinx.coroutines.launch

class SomeDetailActivity : BaseActivity(),
    SomeDetailPresenter.MVPView, SomeDetailPresenter.Navigator {
    var presenter: SomeDetailPresenter? = null
    lateinit var userRepository: UserRepository
    var tvTitle: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        userRepository = UserRepository(
            getUsersApiDataSource = GetUsersApiDataSource(),
            getUsersDbDataSource = GetUsersDBDataSource(),
            insertUserApiDataSource = InsertUserApiDataSource())

        presenter = SomeDetailPresenter(userRepository)

        presenter?.view = this
        presenter?.navigator = this

        tvTitle = findViewById(R.id.tvTitle)

        presenter?.launch {
            presenter?.initialize()
        }
    }

    override fun onResume() {
        super.onResume()

//        presenter?.launch {
//            presenter?.resume()
//        }
    }

    override fun getLayoutId(): Int = R.layout.activity_some_detail

    override fun showUsers(users: List<User>) = runOnUiThread {
        tvTitle?.text = getString(R.string.users_found_param, users.size)
    }

    override fun showEmptyCase() {
        tvTitle?.text = getString(R.string.no_results)
    }

    override fun showSomeResult() {
        tvTitle?.text = "Ok, result has arrived"
    }

    override fun showTitle(title: String) {
        tvTitle?.text = title
    }
}
