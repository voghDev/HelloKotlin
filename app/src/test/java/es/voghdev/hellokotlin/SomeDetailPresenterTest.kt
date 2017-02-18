package es.voghdev.hellokotlin

import android.content.Context
import junit.framework.Assert.assertNotNull
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations

class SomeDetailPresenterTest {
    @Mock
    lateinit var mockUserRepository: UserRepository

    @Mock
    lateinit var mockContext: Context

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun `should request a List of users on start`() {
        val mockView = mock(SomeDetailPresenter.MVPView::class.java)
        val presenter = SomeDetailPresenter(mockContext, mockUserRepository)

        assertNotNull(presenter)

        presenter.view = mockView
        presenter.initialize()

        verify(mockView, times(1))?.showUsers(anyList())
        verify(mockUserRepository, times(1))?.getUsers()
    }
}
