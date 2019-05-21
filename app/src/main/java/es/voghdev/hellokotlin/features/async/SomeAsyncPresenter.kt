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
package es.voghdev.hellokotlin.features.async

import android.content.Context
import es.voghdev.hellokotlin.R
import es.voghdev.hellokotlin.domain.AsyncCall
import es.voghdev.hellokotlin.domain.model.SampleData
import es.voghdev.hellokotlin.global.Presenter

class SomeAsyncPresenter() :
    Presenter<SomeAsyncPresenter.MVPView, SomeAsyncPresenter.Navigator>() {

    interface MVPView {
        fun showSuccess(s: String)
        fun showError(s: String)
        fun showLoading()
        fun showName(name: String)
    }

    interface Navigator
}