package kotlincucumberplaywright

import com.microsoft.playwright.BrowserType
import com.microsoft.playwright.Page
import com.microsoft.playwright.Playwright
import io.cucumber.java.After
import io.cucumber.java.Before
import io.cucumber.java.Scenario
import kotlincucumberplaywright.Hooks.webDriver.page

class Hooks {
    object webDriver {
        var page: Page? = null
    }

    @Before
    fun executedBefore() {
        val playwright = Playwright.create()
        val browser = playwright.chromium().launch(BrowserType.LaunchOptions().setHeadless(false).setChannel("msedge"))
        page = browser.newPage()
    }

    @After
    fun executedAfter(scenario: Scenario) {
        scenario.attach(page?.screenshot(), "image/png", "image1")
        page?.close()
    }
}