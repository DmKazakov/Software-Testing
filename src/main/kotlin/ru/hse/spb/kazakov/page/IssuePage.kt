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

    fun getSummary(): String = summary!!.text

    fun getDescription(): String {
        val text = description!!.findElements(By.className("text"))
        return if (text.isEmpty()) "" else text[0].text
    }
}