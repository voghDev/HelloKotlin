package es.voghdev.hellokotlin

import android.content.Context
import junit.framework.Assert.assertNotNull
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
        Thread.sleep(200)
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
}
