package ru.hse.spb.kazakov.page.element

import org.openqa.selenium.By
import org.openqa.selenium.WebDriver

class TextField(element: By, driver: WebDriver) : PageElement(element, driver) {
    fun enter(text: String) {
        webElement.clear()
        webElement.sendKeys(text)
    }
}