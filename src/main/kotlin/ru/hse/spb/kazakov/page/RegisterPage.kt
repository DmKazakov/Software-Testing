package ru.hse.spb.kazakov.page

import org.openqa.selenium.By
import org.openqa.selenium.TimeoutException
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.support.FindBy
import ru.hse.spb.kazakov.page.element.TextField

class RegisterPage(driver: WebDriver) : WebPage(driver) {
    @FindBy(id = "id_l.R.register")
    private val registerButton: WebElement? = null
    val fullName = TextField(By.id("id_l.R.user_fullName"), driver)
    val email = TextField(By.id("id_l.R.user_email"), driver)
    val login = TextField(By.id("id_l.R.user_login"), driver)
    val password = TextField(By.id("id_l.R.password"), driver)
    val confirmPassword = TextField(By.id("id_l.R.confirmPassword"), driver)

    fun register(): YouTrackPage? {
        registerButton!!.click()
        return try {
            YouTrackPage(driver)
        } catch (exception: TimeoutException) {
            null
        }
    }
}