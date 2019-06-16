package ru.hse.spb.kazakov

import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.*
import org.openqa.selenium.WebDriver
import org.openqa.selenium.opera.OperaDriver
import org.openqa.selenium.opera.OperaOptions
import ru.hse.spb.kazakov.page.IssuesPage
import ru.hse.spb.kazakov.page.LoginPage
import ru.hse.spb.kazakov.page.YouTrackPage

private const val TEST_LOGIN = "testing-account"
private const val TEST_PASSWORD = "123456"
private const val LOGIN_URL = "http://localhost:8080/login"

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class YouTrackIssuesTest {
    private lateinit var driver: WebDriver
    private lateinit var issuesPage: IssuesPage

    @BeforeAll
    fun setUp() {
        driver = OperaDriver()
        register()
    }

    private fun register() {
        driver.get(LOGIN_URL)
        val loginPage = LoginPage(driver)
        val registerPage = loginPage.registerPage()
        registerPage.fullName.enter(TEST_LOGIN)
        registerPage.email.enter(TEST_LOGIN)
        registerPage.login.enter(TEST_LOGIN)
        registerPage.password.enter(TEST_PASSWORD)
        registerPage.confirmPassword.enter(TEST_PASSWORD)
        registerPage.register()
    }

    @BeforeEach
    fun logIn() {
        driver.get(LOGIN_URL)
        val loginPage = LoginPage(driver)
        loginPage.enterLogin(TEST_LOGIN)
        loginPage.enterPassword(TEST_PASSWORD)
        issuesPage = loginPage.logIn()!!.issuesPage()
    }

    @AfterAll
    fun tearDown() {
        driver.quit()
    }

    @Test
    fun testAddInvalidIssue() {
        var currentPage: YouTrackPage
        currentPage = issuesPage
        val issuesNumber = currentPage.issuesNumber()

        currentPage = currentPage.newIssuePage()
        currentPage.summary.enter("")
        currentPage.description.enter("")


        assertNull(currentPage.createIssue())
        assertNotNull(currentPage.getErrorPopUp())
        currentPage = currentPage.issuesPage()
        assertEquals(issuesNumber, currentPage.issuesNumber())
    }

    @Test
    fun testAddEmptyDescriptionIssue() {
        testAddIssue("title", "", "title", "No description")
    }

    @Test
    fun testAddValidIssue() {
        testAddIssue("title", "description")
    }

    @Test
    fun testAddMultipleIssues() {
        val issuesNumber = issuesPage.issuesNumber()
        val firstIssueSummary = "issue 1"
        val firstIssueDescription = "description"
        val secondIssueSummary = "issue 2"
        val secondIssueDescription = "Description "

        var currentPage: YouTrackPage
        currentPage = addIssue(firstIssueSummary, firstIssueDescription, issuesPage)
        currentPage = addIssue(secondIssueSummary, secondIssueDescription, currentPage)

        assertEquals(issuesNumber + 2, currentPage.issuesNumber())
        currentPage = checkIssue(firstIssueSummary, firstIssueDescription, 1, currentPage)
        checkIssue(secondIssueSummary, secondIssueDescription, 0, currentPage)
    }

    @Test
    fun testAddMultiLanguageIssue() {
        testAddIssue("問題", "Описанме")
    }

    @Test
    fun testAddUnicodeCharsIssue() {
        testAddIssue("(づ｡◕‿‿◕｡)づ᳄", "⁂₰ $%∞")
    }

    @Test
    fun testAddLongIssue() {
        testAddIssue("summary".repeat(100), "description".repeat(100))
    }

    @Test
    fun testAddMultilineIssue() {
        val summary = "fdso\ngfdg\n\n\ngfd\n"
        val description = "\nfds\n\ngfd00\n"
        testAddIssue(summary, description, summary.filter { it != '\n' }, "fds\n\ngfd00")
    }

    @Test
    fun testAddIdenticalIssue() {
        val issuesNumber = issuesPage.issuesNumber()
        val summary = "issue"
        val description = "description"

        var currentPage: YouTrackPage
        currentPage = addIssue(summary, description, issuesPage)
        currentPage = addIssue(summary, description, currentPage)

        assertEquals(issuesNumber + 2, currentPage.issuesNumber())
        currentPage = checkIssue(summary, description, 1, currentPage)
        checkIssue(summary, description, 0, currentPage)
    }

    @Test
    fun testAddWhitespaceIssue() {
        testAddIssue("   sth gf   gf  ", "     q            f         ", "sth gf gf")
    }

    private fun testAddIssue(
        summary: String, description: String,
        expectedSummary: String = summary, expectedDescription: String = description
    ) {
        val issuesNumber = issuesPage.issuesNumber()
        val issuesPage = addIssue(summary, description, issuesPage)
        assertEquals(issuesNumber + 1, issuesPage.issuesNumber())
        checkIssue(expectedSummary, expectedDescription, 0, issuesPage)
    }

    private fun addIssue(summary: String, description: String, page: IssuesPage): IssuesPage {
        var currentPage: YouTrackPage
        currentPage = page.newIssuePage()
        currentPage.summary.enter(summary)
        currentPage.description.enter(description)
        currentPage = currentPage.createIssue()!!
        return currentPage.issuesPage()
    }

    private fun checkIssue(summary: String, description: String, issueIndex: Int, page: IssuesPage): IssuesPage {
        val issuePage = page.openIssue(issueIndex)
        assertEquals(summary, issuePage.getSummary())
        assertEquals(description, issuePage.getDescription())
        return issuesPage.issuesPage()
    }
}
