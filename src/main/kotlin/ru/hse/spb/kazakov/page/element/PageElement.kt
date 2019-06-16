package ru.hse.spb.kazakov.page.element

import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.support.ui.WebDriverWait

private const val TIME_OUT_IN_SECONDS = 10L

abstract class PageElement(
    private val locator: By,
    private val driver: WebDriver
) {
    protected val webElement: WebElement

    init {
        waitForVisibility()
        webElement = driver.findElement(locator)
    }

    private fun waitForVisibility() {
        val webDriverWait = WebDriverWait(driver, TIME_OUT_IN_SECONDS)
        webDriverWait.until(
            ExpectedConditions.visibilityOfElementLocated(locator)
        )
    }
}