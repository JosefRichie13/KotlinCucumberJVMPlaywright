package kotlincucumberplaywright

class Driver {

    fun loadAUrl(url: String?) {
        Hooks.webDriver.page?.navigate(url)
    }

    fun getTextFromElement(element: String?): String? {
        return Hooks.webDriver.page?.locator(element)?.textContent()
    }

    fun clickButton(element: String?) {
        Hooks.webDriver.page?.locator(element)?.click()
    }

    fun typeText(textToType: String?, element: String?) {
        Hooks.webDriver.page?.locator(element)?.fill(textToType)
    }

    fun elementVisibleOrNot(element: String?): Boolean? {
        return Hooks.webDriver.page?.locator(element)?.isVisible
    }

    fun getSpecificTextFromAListOfElements(element: String?, index: Int?): String? {
        return index?.let { Hooks.webDriver.page?.locator(element)?.nth(it)?.textContent()}
    }

    fun getAllTextFromAListOfElements(element: String?): List<String>? {
        return Hooks.webDriver.page?.locator(element)?.allTextContents()
    }

    fun selectFromDropdownUsingText(element: String?, selectOptionInText: String?) {
        Hooks.webDriver.page?.locator(element)?.selectOption(selectOptionInText)
    }

    fun getTheCurrentURL(): String? {
        return Hooks.webDriver.page?.url()
    }
}