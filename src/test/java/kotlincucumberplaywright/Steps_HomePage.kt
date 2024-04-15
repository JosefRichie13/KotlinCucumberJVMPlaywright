package kotlincucumberplaywright

import io.cucumber.java.en.And
import io.cucumber.java.en.Then

import org.junit.Assert.assertEquals
import java.util.stream.Collectors


class Steps_HomePage {

    var driverMethods = Driver()
    var configs = Configs()
    var selectors = Selectors()

    @And("the number of items in the cart bubble is {string}")
    fun verifyItemNumberInCart(cartNumber: String?) {
        assertEquals(driverMethods.getTextFromElement(selectors.itemNumberInCart), cartNumber)
    }

    @Then("the sort {string} should work correctly")
    fun checkTheSortOrder(sortType: String){
        when (sortType){
            /*
            We get the list of prices from the home page and store it in a list, noSortPrices.
            Then remove the $ sign, convert the prices to floats and sort it Descending
            The result is stored it in an List, sortedPricesHighToLowBYCODE

            We then select the Price (high to low) option from the dropdown, repeat the above step
            We end up with prices sorted by the application in an List, sortedPricesHighToLowBYAPP
            Finally we compare both Lists if they are the same
            */
            "Price (high to low)" -> {
                val noSortPrices = driverMethods.getAllTextFromAListOfElements(selectors.priceList)
                val sortedPricesHighToLowBYCODE = noSortPrices!!.stream().map { s: String -> s.substring(1).toFloat() }.collect(Collectors.toList()).stream().sorted(Comparator.reverseOrder()).collect(Collectors.toList())
                driverMethods.selectFromDropdownUsingText(selectors.productSort, sortType)

                val priceAfterSortByUI = driverMethods.getAllTextFromAListOfElements(selectors.priceList)
                val sortedPricesHighToLowBYAPP = priceAfterSortByUI!!.stream().map { s: String -> s.substring(1).toFloat() }.collect(Collectors.toList())

                assertEquals(sortedPricesHighToLowBYCODE, sortedPricesHighToLowBYAPP)
            }
            "Price (low to high)" -> {
                val noSortPrices = driverMethods.getAllTextFromAListOfElements(selectors.priceList)
                val sortedPricesLowToHighBYCODE = noSortPrices!!.stream().map { s: String -> s.substring(1).toFloat() }.collect(Collectors.toList()).stream().sorted().collect(Collectors.toList())
                driverMethods.selectFromDropdownUsingText(selectors.productSort, sortType)

                val priceAfterSortByUI = driverMethods.getAllTextFromAListOfElements(selectors.priceList)
                val sortedPricesLowToHighBYAPP = priceAfterSortByUI!!.stream().map { s: String -> s.substring(1).toFloat() }.collect(Collectors.toList())

                assertEquals(sortedPricesLowToHighBYCODE, sortedPricesLowToHighBYAPP)
            }
            /*
            We get the names of products from the home page and store them in a List, noSortNames
            We then sort it Descending and store it in a List, sortedNamesZtoAByCODE
            We then select the Name (Z to A) option from the dropdown
            We end up with prices sorted by the application in a List, sortedNamesZtoAByAPP
            Finally we compare both Lists if they are the same
            */
            "Name (Z to A)" -> {
                val noSortNames = driverMethods.getAllTextFromAListOfElements(selectors.productList)
                val sortedNamesZtoAByCODE = noSortNames!!.stream().sorted(Comparator.reverseOrder()).collect(Collectors.toList())
                driverMethods.selectFromDropdownUsingText(selectors.productSort, sortType)

                val sortedNamesZtoAByAPP = driverMethods.getAllTextFromAListOfElements(selectors.productList)
                assertEquals(sortedNamesZtoAByCODE, sortedNamesZtoAByAPP)
            }
            "Name (A to Z)" -> {
                val noSortNames = driverMethods.getAllTextFromAListOfElements(selectors.productList)
                val sortedNamesZtoAByCODE = noSortNames!!.stream().sorted().collect(Collectors.toList())
                driverMethods.selectFromDropdownUsingText(selectors.productSort, sortType)

                val sortedNamesZtoAByAPP = driverMethods.getAllTextFromAListOfElements(selectors.productList)
                assertEquals(sortedNamesZtoAByCODE, sortedNamesZtoAByAPP)
            }
            else -> throw IllegalArgumentException("Incorrect User Type : $sortType")
        }
    }

}