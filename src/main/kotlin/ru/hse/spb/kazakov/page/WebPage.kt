package ru.hse.spb.kazakov.page

import org.openqa.selenium.WebDriver
import org.openqa.selenium.support.PageFactory

abstract class WebPage(protected val driver: WebDriver) {
    init {
        PageFactory.initElements(driver, this)
    }
}