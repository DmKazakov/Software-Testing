package ru.hse.spb.kazakov.page

import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.support.FindBy
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.support.ui.WebDriverWait

class IssuesPage(driver: WebDriver) : YouTrackPage(driver) {
    @FindBy(css = "a[href='#newissue=yes']")
    private val newIssueButton: WebElement? = null
    private val issuesLinks: List<WebElement>

    init {
        waitFor(newIssueButton)
        issuesLinks = driver.findElements(By.className("issue-summary"))
    }

    fun newIssuePage(): NewIssuePage {
        newIssueButton!!.click()
        return NewIssuePage(driver)
    }

    fun openIssue(index: Int): IssuePage {
        issuesLinks[index].click()
        return IssuePage(driver)
    }

    fun issuesNumber() = issuesLinks.size
}