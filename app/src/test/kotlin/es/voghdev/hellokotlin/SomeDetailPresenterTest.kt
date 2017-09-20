package es.voghdev.hellokotlin

import android.content.Context
import com.nhaarman.mockito_kotlin.argumentCaptor
import com.nhaarman.mockito_kotlin.whenever
import es.voghdev.hellokotlin.features.user.SomeDetailPresenter
import es.voghdev.hellokotlin.features.user.User
import es.voghdev.hellokotlin.features.user.UserRepository
import es.voghdev.hellokotlin.features.user.usecase.GetUsers
import es.voghdev.hellokotlin.features.user.usecase.InsertUser
import junit.framework.Assert.assertNotNull
import kotlinx.coroutines.experimental.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations

class SomeDetailPresenterTest {
    @Mock
    lateinit var mockUserRepository: UserRepository

    @Mock
    lateinit var mockContext: Context

    @Mock
    lateinit var mockView: SomeDetailPresenter.MVPView

    @Mock
    lateinit var mockGetUsersApi: GetUsers

    @Mock
    lateinit var mockGetUsersDb: GetUsers

    @Mock
    lateinit var mockInsertUser: InsertUser

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun `should request a List of users on start`() {
        val presenter = SomeDetailPresenter(mockContext, mockUserRepository)

        assertNotNull(presenter)

        presenter.view = mockView
        presenter.initialize()

        waitForAsyncBlocksToFinish()

        verify(mockUserRepository, times(1))?.getUsers()
    }

    private fun waitForAsyncBlocksToFinish() {
        Thread.sleep(30)
    }

    @Test
    fun `should show user list if request has results`() {
        val presenter = SomeDetailPresenter(mockContext, mockUserRepository)
        `when`(mockUserRepository.getUsers()).thenReturn(listOf(User(name = "John")))
        assertNotNull(presenter)

        presenter.view = mockView
        presenter.initialize()

        waitForAsyncBlocksToFinish()

        verify(mockView, times(1))?.showUsers(anyList())
    }

    @Test
    fun `should show empty case if request has no results`() {
        val presenter = SomeDetailPresenter(mockContext, mockUserRepository)

        assertNotNull(presenter)

        presenter.view = mockView
        presenter.initialize()

        waitForAsyncBlocksToFinish()

        verify(mockView, times(1))?.showEmptyCase()
    }

    @Test
    fun `should call a simple method containing a coroutine in runBlocking mode`() {
        val presenter = SomeDetailPresenter(mockContext, mockUserRepository)
        presenter.view = mockView

        runBlocking {
            presenter.onSomeEventHappened()
        }

        verify(mockView).showSomeResult()
    }

    @Test
    fun `should call a method that contains a coroutine and returns a result using runBlocking`() {
        val presenter = SomeDetailPresenter(mockContext, mockUserRepository)
        presenter.view = mockView

        whenever(mockUserRepository.performSomeBlockingOperationWithResult()).thenReturn(listOf(
                User(name = "User 001"),
                User(name = "User 002")
        ))

        runBlocking {
            presenter.onSomeOtherEventHappened()
        }

        val captor = argumentCaptor<List<User>>()

        verify(mockView).showUsers(captor.capture())

        assertEquals(2, captor.firstValue.size)
        assertEquals("User 001", captor.firstValue[0].name)
        assertEquals("User 002", captor.firstValue[1].name)
    }

    @Test
    fun `should call a method that contains a coroutine using a non-mocked repository with mock DataSources`() {
        val nonMockedRepository = UserRepository(mockGetUsersApi, mockGetUsersDb, mockInsertUser)

        val presenter = SomeDetailPresenter(mockContext, nonMockedRepository)
        presenter.view = mockView

        runBlocking {
            presenter.onSomeOtherEventHappened()
        }

        val captor = argumentCaptor<List<User>>()

        verify(mockView).showUsers(captor.capture())

        assertEquals(2, captor.firstValue.size)
        assertEquals("John doe", captor.firstValue[0].name)
        assertEquals("Jane doe", captor.firstValue[1].name)
    }
}
