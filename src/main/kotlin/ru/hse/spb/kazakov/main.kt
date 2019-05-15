package ru.hse.spb.kazakov

import org.openqa.selenium.opera.OperaDriver
import org.openqa.selenium.opera.OperaOptions
import ru.hse.spb.kazakov.page.LoginPage


fun main() {
    System.setProperty("webdriver.opera.driver", "/home/dmitry/HW/Test/operadriver_linux64/operadriver")
    val options = OperaOptions()
    options.setBinary("/usr/lib/x86_64-linux-gnu/opera/opera")
    val driver = OperaDriver(options)
    driver.get("http://localhost:8080/login")
    val loginPage = LoginPage(driver)
    loginPage.enterLogin("q")
    loginPage.enterPassword("123456")
    var p = loginPage.logIn()
    p = p!!.issuesPage()
    p = p.openIssue(5)
    print(p.getSummary())
    print(p.getDescription())
    p.issuesPage()
}
