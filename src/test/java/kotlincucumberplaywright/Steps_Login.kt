package kotlincucumberplaywright

import io.cucumber.java.en.And
import io.cucumber.java.en.Given
import io.cucumber.java.en.Then
import io.cucumber.java.en.When

import org.junit.Assert.*

class Steps_Login {

    var driverMethods = Driver()
    var configs = Configs()
    var selectors = Selectors()

    @Given("I open the web page")
    fun openTheWebPage() {
        driverMethods.loadAUrl(configs.mainURL)
    }

    @When("I login as a {string} user")
    fun loginAsAUser(userType: String){
        when (userType){
            "standard" -> {
                driverMethods.typeText(configs.validUser, selectors.userName)
                driverMethods.typeText(configs.password, selectors.password)
            }
            "locked" ->{
                driverMethods.typeText(configs.lockedUser, selectors.userName)
                driverMethods.typeText(configs.password, selectors.password)
            }
            "no_username" ->  driverMethods.typeText(configs.password, selectors.password)
            "no_password" -> driverMethods.typeText(configs.validUser, selectors.userName)
            "wrong_username" -> {
                driverMethods.typeText(configs.wrongUser, selectors.userName)
                driverMethods.typeText(configs.password, selectors.password)
            }
            "wrong_password" -> {
                driverMethods.typeText(configs.validUser, selectors.userName)
                driverMethods.typeText(configs.wrongPassword, selectors.password)
            }
            else -> throw IllegalArgumentException("Incorrect User Type : $userType")
        }
        driverMethods.clickButton(selectors.loginButton)
    }

    @Then("I should see {string} in the {string}")
    fun shouldSeeMessageInPage(message: String, page: String){
        when(page){
            "homepage" -> {
                assertEquals(driverMethods.getTextFromElement(selectors.homepageTitle), message)
                driverMethods.elementVisibleOrNot(selectors.loginButton)?.let { assertFalse(it) }
            }
            "loginpage" -> {
                assertEquals(driverMethods.getTextFromElement(selectors.loginpageTitle), message)
                driverMethods.elementVisibleOrNot(selectors.loginButton)?.let { assertTrue(it) }
            }
            else -> throw IllegalArgumentException("Incorrect User Type : $page")
        }
    }

    @Then("I should see the login error message {string}")
    fun shouldSeeTheLoginErrorMessage(message: String?) {
        assertTrue(driverMethods.getTextFromElement(selectors.errorMessage)!!.contains(message!!))
    }

    @When("I logout of the webpage")
    @Throws(InterruptedException::class)
    fun iLogoutOfTheWebpage() {
        driverMethods.clickButton(selectors.menu)
        Thread.sleep(5000)
        driverMethods.clickButton(selectors.logoutButton)
    }
}