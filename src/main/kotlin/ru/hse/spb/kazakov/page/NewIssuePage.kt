package ru.hse.spb.kazakov.page

import org.openqa.selenium.By
import org.openqa.selenium.TimeoutException
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.support.FindBy
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.support.ui.WebDriverWait
import ru.hse.spb.kazakov.page.element.TextField

class NewIssuePage(driver: WebDriver) : YouTrackPage(driver) {
    @FindBy(css = "button[id^='id_l.I.ni.ei.submitButton']")
    private val createIssueButton: WebElement? = null
    val summary = TextField(By.id("id_l.I.ni.ei.eit.summary"), driver)
    val description = TextField(By.id("id_l.I.ni.ei.eit.description"), driver)

    init {
        waitFor(createIssueButton)
    }

    fun createIssue(): IssuesPage? {
        createIssueButton!!.click()
        return try {
            IssuesPage(driver)
        } catch (exception: TimeoutException) {
            null
        }
    }
}