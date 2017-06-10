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
import android.os.PersistableBundle
import es.voghdev.hellokotlin.R
import es.voghdev.hellokotlin.features.user.datasource.GetUsersApiDataSource
import es.voghdev.hellokotlin.features.user.datasource.GetUsersDBDataSource
import es.voghdev.hellokotlin.features.user.datasource.InsertUserApiDataSource
import es.voghdev.hellokotlin.global.BaseActivity
import kotlinx.android.synthetic.main.activity_some_detail.*

class SomeDetailActivity : BaseActivity(),
        SomeDetailPresenter.MVPView, SomeDetailPresenter.Navigator {
    override fun getLayoutId(): Int {
        return R.layout.activity_some_detail
    }

    var presenter: SomeDetailPresenter? = null
    lateinit var userRepository: UserRepository

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        userRepository = UserRepository(
                getUsersApiDataSource = GetUsersApiDataSource(),
                getUsersDbDataSource = GetUsersDBDataSource(),
                insertUserApi = InsertUserApiDataSource())

        presenter = SomeDetailPresenter(this, userRepository)
        presenter?.initialize()
        presenter?.view = this
        presenter?.navigator = this
    }

    override fun showUsers(users: List<User>) {
        tvTitle.text = getString(R.string.users_found_param, users.size)
    }

    override fun showEmptyCase() {
        tvTitle.text = getString(R.string.no_results)
    }
}
