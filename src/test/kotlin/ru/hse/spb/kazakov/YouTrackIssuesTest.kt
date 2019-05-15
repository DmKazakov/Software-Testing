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
        System.setProperty("webdriver.opera.driver", "/home/dmitry/HW/Test/operadriver_linux64/operadriver")
        val options = OperaOptions()
        options.setBinary("/usr/lib/x86_64-linux-gnu/opera/opera")
        driver = OperaDriver(options)
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
        currentPage = currentPage.issuesPage()
        assertEquals(issuesNumber, currentPage.issuesNumber())
    }

    @Test
    fun testAddEmptyDescriptionIssue() {
        val issuesNumber = issuesPage.issuesNumber()
        val summary = "summary"

        val issuesPage = addIssue(summary, "", issuesPage)
        assertEquals(issuesNumber + 1, issuesPage.issuesNumber())
        checkIssue(summary, "", 0, issuesPage)
    }

    @Test
    fun testAddValidIssue() {
        val issuesNumber = issuesPage.issuesNumber()
        val summary = "title"
        val description = "description"

        val issuesPage = addIssue(summary, description, issuesPage)
        assertEquals(issuesNumber + 1, issuesPage.issuesNumber())
        checkIssue(summary, description, 0, issuesPage)
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