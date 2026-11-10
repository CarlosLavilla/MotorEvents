package com.lavinef.motorevents.ui.theme

/**
 * Method to obtain the emoji representation of a country from the country name.
 *
 * @param country name of the country.
 * @return String containing the String representation of the country.
 */
fun getFlagEmoji(country: String): String {
    val countryCode = when (country) {
        "Australia" -> "AU"
        "Morocco" -> "MA"
        "UK" -> "GB"
        "USA" -> "US"
        "Sweden" -> "SE"
        "Germany" -> "DE"
        "Bahrain" -> "BH"
        "Azerbaijan" -> "AZ"
        "Portugal" -> "PT"
        "Switzerland" -> "CH"
        "India" -> "IN"
        "Spain" -> "ES"
        "France" -> "FR"
        "Argentina" -> "AR"
        "South Africa" -> "ZA"
        "Hungary" -> "HU"
        "Italy" -> "IT"
        "Brazil" -> "BR"
        "Turkey" -> "TR"
        "China" -> "CN"
        "Japan" -> "JP"
        "Canada" -> "CA"
        "Monaco" -> "MC"
        "Austria" -> "AT"
        "Belgium" -> "BE"
        "Netherlands" -> "NL"
        "Singapore" -> "SG"
        "Mexico" -> "MX"
        "Qatar" -> "QA"
        "Malaysia" -> "MY"
        "Abu Dhabi", "United Arab Emirates", "UAE" -> "AE"
        "Saudi Arabia" -> "SA"
        "Korea" -> "KR"
        else -> "🏁"
    }
    return countryCode.uppercase().let { code ->
        if (code.length != 2) return code

        val baseCharOffset = 'A'.code
        val firstCharOffset = code[0].code - baseCharOffset
        val secondCharOffset = code[1].code - baseCharOffset

        val firstCodePoint = 0x1F1E6 + firstCharOffset
        val secondCodePoint = 0x1F1E6 + secondCharOffset

        String(Character.toChars(firstCodePoint)) + String(Character.toChars(secondCodePoint))
    }
}