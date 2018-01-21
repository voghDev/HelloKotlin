package es.voghdev.hellokotlin

import com.nhaarman.mockito_kotlin.argumentCaptor
import com.nhaarman.mockito_kotlin.whenever
import es.voghdev.hellokotlin.domain.ResLocator
import es.voghdev.hellokotlin.features.invoice.Invoice
import es.voghdev.hellokotlin.features.user.SomeDetailPresenter
import es.voghdev.hellokotlin.features.user.User
import es.voghdev.hellokotlin.features.user.UserRepository
import es.voghdev.hellokotlin.features.user.usecase.GetUsers
import es.voghdev.hellokotlin.features.user.usecase.InsertUser
import es.voghdev.hellokotlin.global.await
import junit.framework.Assert.assertNotNull
import kotlinx.coroutines.experimental.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations

class SomeDetailPresenterTest {
    @Mock
    lateinit var mockUserRepository: UserRepository

    @Mock lateinit var mockResLocator: ResLocator

    @Mock
    lateinit var mockView: SomeDetailPresenter.MVPView

    @Mock
    lateinit var mockGetUsersApi: GetUsers

    @Mock
    lateinit var mockGetUsersDb: GetUsers

    @Mock
    lateinit var mockInsertUser: InsertUser

    lateinit var presenter: SomeDetailPresenter

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)

        presenter = SomeDetailPresenter(mockResLocator, mockUserRepository)
        presenter.view = mockView
    }

    @Test
    fun `should request a List of users on start`() {
        givenAllStringsAreMocked()

        assertNotNull(presenter)

        presenter.initialize().await()

        verify(mockUserRepository, times(1))?.getUsers()
    }

    @Test
    fun `should show user list if request has results`() {
        givenAllStringsAreMocked()

        whenever(mockUserRepository.getUsers()).thenReturn(listOf(User(name = "John")))

        assertNotNull(presenter)

        presenter.initialize().await()

        verify(mockView, times(1))?.showUsers(anyList())
    }

    @Test
    fun `should show empty case if request has no results`() {
        givenAllStringsAreMocked()

        assertNotNull(presenter)

        presenter.initialize().await()

        verify(mockView, times(1))?.showEmptyCase()
    }

    @Test
    fun `should call a simple method containing a coroutine in runBlocking mode`() {
        runBlocking {
            presenter.onSomeEventHappened()
        }

        verify(mockView).showSomeResult()
    }

    @Test
    fun `should call a method that contains a coroutine and returns a result using runBlocking`() {
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
    fun `should call a method that contains a coroutine and verify assertions in the repository`() {
        val invoice = Invoice(customerId = 15L, amount = 10f)

        runBlocking {
            presenter.onEventWithParameterHappened(invoice.customerId)
        }

        val captor = argumentCaptor<Invoice>()

        verify(mockUserRepository).performSomeBlockingOperationWithAParameter(captor.capture())
        verify(mockView).showSomeResult()

        assertEquals(15L, captor.firstValue.customerId)
        assertEquals(50f, captor.firstValue.amount)
        assertNotEquals(10f, captor.firstValue.amount)
    }

    @Test
    fun `should call a method that contains a coroutine using a non-mocked repository with mock DataSources`() {
        val nonMockedRepository = UserRepository(mockGetUsersApi, mockGetUsersDb, mockInsertUser)

        val presenter = SomeDetailPresenter(mockResLocator, nonMockedRepository)
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

    private fun givenAllStringsAreMocked() {
        whenever(mockResLocator.getString(R.string.tech_debt_is_paid)).thenReturn("Relax man, I pay my tech debt!")
    }
}
