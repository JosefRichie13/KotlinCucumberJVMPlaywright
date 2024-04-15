package kotlincucumberplaywright

import io.cucumber.java.en.And
import io.cucumber.java.en.Then

import org.junit.Assert.*

class Steps_Footer {

    var driverMethods = Driver()
    var configs = Configs()
    var selectors = Selectors()

    @And("I confirm that the footer is {string}")
    fun verifyFooterStatus(visibleStatus: String) {
        if (visibleStatus == "not visible") {
            driverMethods.elementVisibleOrNot(selectors.footer)?.let { assertFalse(it) }
        } else {
            driverMethods.elementVisibleOrNot(selectors.footer)?.let { assertTrue(it) }
        }
    }

    @Then("the {string} icon in the footer should open {string}")
    fun checkFooterRedirectURL(redirectPage: String, redirectURL: String) {
        when (redirectPage) {
            "Twitter" -> {
                val newURL = driverMethods.getURLFromANewTab(selectors.footerTwitter)
                assertEquals(newURL, redirectURL)
            }
            "Facebook" -> {
                val newURL = driverMethods.getURLFromANewTab(selectors.footerFacebook)
                assertEquals(newURL, redirectURL)
            }
            "LinkedIn" -> {
                val newURL = driverMethods.getURLFromANewTab(selectors.footerLinkedin)
                assertEquals(newURL, redirectURL)
            }
            else -> throw IllegalArgumentException("Incorrect Footer Icon : $redirectPage")
        }
    }
}