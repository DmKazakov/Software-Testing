package ru.hse.spb.kazakov.page

import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.support.FindBy
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.support.ui.WebDriverWait


open class YouTrackPage(driver: WebDriver) : WebPage(driver) {
    @FindBy(css = "a[href='/issues']")
    private val issuesLink: WebElement? = null

    init {
        waitFor(issuesLink)
    }

    fun issuesPage(): IssuesPage {
        issuesLink!!.click()
        return IssuesPage(driver)
    }

    protected fun waitFor(element: WebElement?) =
        WebDriverWait(driver, TIME_OUT_IN_SECONDS).until(
            ExpectedConditions.visibilityOf(element)
        )

    private companion object {
        const val TIME_OUT_IN_SECONDS = 10L
    }
}