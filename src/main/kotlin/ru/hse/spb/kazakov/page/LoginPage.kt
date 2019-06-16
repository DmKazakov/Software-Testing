package ru.hse.spb.kazakov.page

import org.openqa.selenium.By
import org.openqa.selenium.TimeoutException
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.support.FindBy
import ru.hse.spb.kazakov.page.element.TextField

class LoginPage(driver: WebDriver) : WebPage(driver) {
    @FindBy(id = "id_l.L.loginButton")
    private val loginButton: WebElement? = null
    @FindBy(id = "id_l.L.registerLink")
    private val registerLink: WebElement? = null
    private val login = TextField(By.id("id_l.L.login"), driver)
    private val password = TextField(By.id("id_l.L.password"), driver)

    fun enterLogin(login: String) = this.login.enter(login)

    fun enterPassword(password: String) = this.password.enter(password)

    fun logIn(): YouTrackPage? {
        loginButton!!.click()
        return try {
            YouTrackPage(driver)
        } catch (exception: TimeoutException) {
            null
        }
    }

    fun registerPage(): RegisterPage {
        registerLink!!.click()
        return RegisterPage(driver)
    }
}