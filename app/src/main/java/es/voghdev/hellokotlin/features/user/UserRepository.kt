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

import es.voghdev.hellokotlin.domain.TimedCachePolicy
import es.voghdev.hellokotlin.features.invoice.Invoice
import es.voghdev.hellokotlin.features.user.usecase.GetUsers
import es.voghdev.hellokotlin.features.user.usecase.InsertUser
import es.voghdev.hellokotlin.global.CachePolicy

class UserRepository(val getUsersApiDataSource: GetUsers, val getUsersDbDataSource: GetUsers, val insertUserApiDataSource: InsertUser) : GetUsers, InsertUser by insertUserApiDataSource {
    var cachePolicy: CachePolicy? = null
    var cache: MutableList<User> = ArrayList<User>()

    override fun getUsers(): List<User> {
        if (cachePolicy?.isCacheDirty() ?: false)
            cache.clear()

        cache = getUsersApiDataSource.getUsers() as MutableList<User>
        cachePolicy = TimedCachePolicy(15000)

        return cache
    }

    fun performSomeBlockingOperation() {
        Thread.sleep(10)
    }

    fun performSomeBlockingOperationWithResult(): List<User> {
        Thread.sleep(10)
        return listOf(
                User(name = "John doe", email = "john@mail.com"),
                User(name = "Jane doe", email = "jane@mail.com")
        )
    }

    fun performSomeBlockingOperationWithAParameter(invoice: Invoice): Float {
        Thread.sleep(10)
        return invoice.amount
    }
}
