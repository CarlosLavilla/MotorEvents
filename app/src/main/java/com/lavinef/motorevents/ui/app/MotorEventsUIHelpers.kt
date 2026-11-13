package com.lavinef.motorevents.ui.app

private const val REGIONAL_INDICATOR_SYMBOL_A = 0x1F1E6
private const val RACING_FLAG = 0x1F3C1
private const val COUNTRY_CODE_LENGTH = 2

private val countryCodeMap = mapOf(
    "Australia" to "AU",
    "Morocco" to "MA",
    "UK" to "GB",
    "USA" to "US",
    "Sweden" to "SE",
    "Germany" to "DE",
    "Bahrain" to "BH",
    "Azerbaijan" to "AZ",
    "Portugal" to "PT",
    "Switzerland" to "CH",
    "India" to "IN",
    "Spain" to "ES",
    "France" to "FR",
    "Argentina" to "AR",
    "South Africa" to "ZA",
    "Hungary" to "HU",
    "Italy" to "IT",
    "Brazil" to "BR",
    "Turkey" to "TR",
    "China" to "CN",
    "Japan" to "JP",
    "Canada" to "CA",
    "Monaco" to "MC",
    "Austria" to "AT",
    "Belgium" to "BE",
    "Netherlands" to "NL",
    "Singapore" to "SG",
    "Mexico" to "MX",
    "Qatar" to "QA",
    "Malaysia" to "MY",
    "Abu Dhabi" to "AE",
    "United Arab Emirates" to "AE",
    "UAE" to "AE",
    "Saudi Arabia" to "SA",
    "Korea" to "KR"
)

/**
 * Method to obtain the emoji representation of a country from the country name.
 *
 * @param country name of the country.
 * @return String containing the String representation of the country.
 */
fun getFlagEmoji(country: String): String {
    val countryCode = countryCodeMap.getOrElse(country) { "" }

    if (countryCode.length != COUNTRY_CODE_LENGTH) return String(Character.toChars(RACING_FLAG))

    return countryCode.uppercase().let { code ->
        val baseCharOffset = 'A'.code
        val firstCharOffset = code[0].code - baseCharOffset
        val secondCharOffset = code[1].code - baseCharOffset

        val firstCodePoint = REGIONAL_INDICATOR_SYMBOL_A + firstCharOffset
        val secondCodePoint = REGIONAL_INDICATOR_SYMBOL_A + secondCharOffset

        String(Character.toChars(firstCodePoint)) + String(Character.toChars(secondCodePoint))
    }
}
