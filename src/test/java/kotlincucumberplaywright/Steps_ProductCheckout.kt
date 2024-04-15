package kotlincucumberplaywright

import io.cucumber.datatable.DataTable
import io.cucumber.java.en.And
import io.cucumber.java.en.Then
import org.junit.Assert.assertEquals
import java.util.function.Function
import java.util.stream.Collectors
import kotlin.math.roundToInt

class Steps_ProductCheckout {

    var driverMethods = Driver()
    var configs = Configs()
    var selectors = Selectors()

    @And("I add {string} to the cart")
    fun addToCart(productToAdd: String) {
        addOrRemoveProductFromCart(productToAdd)
    }

    @And("I remove {string} from the cart")
    fun removeFromTheCart(productToRemove: String) {
        addOrRemoveProductFromCart(productToRemove)
    }

    fun addOrRemoveProductFromCart(productType: String){
        when(productType){
            "Sauce Labs Backpack" -> driverMethods.clickButton(selectors.productBackpack)
            "Sauce Labs Bike Light" -> driverMethods.clickButton(selectors.productBikelight)
            "Sauce Labs Bolt T-Shirt" -> driverMethods.clickButton(selectors.productTshirt)
            "Sauce Labs Fleece Jacket" -> driverMethods.clickButton(selectors.productJacket)
            "Sauce Labs Onesie" -> driverMethods.clickButton(selectors.productOnesie)
            "Test.allTheThings() T-Shirt (Red)" -> driverMethods.clickButton(selectors.productTshirtRed)
            else -> throw IllegalArgumentException("Incorrect User Type : $productType")
        }
    }

    @And("I click on the cart")
    fun clickOnTheCart() {
        driverMethods.clickButton(selectors.cart)
    }

    @And("I checkout")
    fun checkout() {
        driverMethods.clickButton(selectors.checkout)
    }

    @And("I enter my information to continue")
    fun enterMyInfoAndContinue(table: DataTable) {
        driverMethods.typeText(table.asMaps()[0]["FirstName"], selectors.firstName)
        driverMethods.typeText(table.asMaps()[0]["LastName"], selectors.lastName)
        driverMethods.typeText(table.asMaps()[0]["Zip"], selectors.zipCode)
        driverMethods.clickButton(selectors.continueButton)
    }

    @And("I confirm my order")
    fun confirmMyOrder() {
        driverMethods.clickButton(selectors.finishButton)
    }

    @Then("I should see {string} after the order is placed")
    fun seeMessageAfterOrderPlacement(message: String?) {
        assertEquals(driverMethods.getTextFromElement(selectors.checkoutBanner), message)
    }

   /*
   We get the Tax shown in the UI, extract the number, convert it into float and store it in a variable, taxCalculatedByAPP
   We get the non taxed sum shown in the UI, extract the number, convert into float, multiple it by 0.08 (8%), round the result off to 2 and store it in a variable, taxCalculatedByCODE
   Then we check if both taxCalculatedByAPP and taxCalculatedByCODE are equal

   We get the total shown in the UI, extract the number, convert it into float and store it in a variable, totalCalculatedByAPP
   We get the non tax added total shown in the UI, extract the number, convert into float, add the tax calculated (taxCalculatedByCODE) and store it in a variable, totalCalculatedByCODE
   Then we check if both totalCalculatedByAPP and totalCalculatedByCODE are equal
   */
    @Then("I should see the tax calculated at 8 percent")
    fun verifyTheTaxCalculation() {
        val taxCalculatedByAPP = driverMethods.getTextFromElement(selectors.taxCalculated)!!.replace("[^\\d.]".toRegex(), "").toFloat()
        val taxCalculatedByCODE = (driverMethods.getTextFromElement(selectors.subtotal)!!.replace("[^\\d.]".toRegex(), "").toFloat() * 0.08 * 100f).roundToInt() / 100f
        assertEquals(taxCalculatedByAPP, taxCalculatedByCODE)

        val totalCalculatedByAPP = driverMethods.getTextFromElement(selectors.fullTotal)!!.replace("[^\\d.]".toRegex(), "").toFloat()
        val totalCalculatedByCODE = driverMethods.getTextFromElement(selectors.subtotal)!!.replace("[^\\d.]".toRegex(), "").toFloat() + taxCalculatedByAPP
        assertEquals(totalCalculatedByAPP, totalCalculatedByCODE)
    }

    /*
    We get the individual prices into an array, individualPrices
    We remove the $ sign, convert the array elements into float and sum it. Storing it in a variable, sumCalculatedByCODE
    We get the total displayed in the UI, extract the number, convert into float and store it in a variable, sumCalculatedByAPP
    Then we check if sumCalculatedByCODE and sumCalculatedByAPP are equal
   */
    @Then("I should see the individual items total correctly")
    fun verifyIndividualItemsTotal() {
        val individualPrices = driverMethods.getAllTextFromAListOfElements(selectors.priceList)
        val individualPricesInFloatWithoutSign = individualPrices!!.stream().map { s: String -> s.substring(1)}.collect(Collectors.toList())
        val sumCalculatedByCODE = individualPricesInFloatWithoutSign.stream().map { s: String? -> java.lang.Float.valueOf(s) }.reduce(0f) { a: Float, b: Float -> java.lang.Float.sum(a, b) }
        val sumCalculatedByAPP = driverMethods.getTextFromElement(selectors.subtotal)!!.replace("[^\\d.]".toRegex(), "").toFloat()
        assertEquals(sumCalculatedByCODE, sumCalculatedByAPP)
    }

}