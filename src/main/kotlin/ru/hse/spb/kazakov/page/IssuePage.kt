package ru.hse.spb.kazakov.page

import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.support.FindBy

class IssuePage(driver: WebDriver) : YouTrackPage(driver) {
    @FindBy(id = "id_l.I.ic.icr.it.issSum")
    private val summary: WebElement? = null
    @FindBy(id = "id_l.I.ic.icr.d.description")
    private val description: WebElement? = null

    init {
        waitFor(summary)
        waitFor(description)
    }

    fun getSummary(): String = summary!!.text

    fun getDescription() = description!!.text
}