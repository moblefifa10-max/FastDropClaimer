package com.fastclaim.app

import java.util.regex.Pattern

object TextProcessor {
    fun normalizeUnicode(input: String): String {
        val sb = StringBuilder()
        var i = 0
        while (i < input.length) {
            val codePoint = input.codePointAt(i)
            when (codePoint) {
                in 0x1D7EC..0x1D7F5 -> sb.append((codePoint - 0x1D7EC + '0'.code).toChar())
                in 0x1D5D4..0x1D5ED -> sb.append((codePoint - 0x1D5D4 + 'A'.code).toChar())
                in 0x1D5EE..0x1D607 -> sb.append((codePoint - 0x1D5EE + 'a'.code).toChar())
                else -> sb.appendCodePoint(codePoint)
            }
            i += Character.charCount(codePoint)
        }
        return sb.toString()
    }

    fun extractClaimCode(rawText: String): String? {
        val normalized = normalizeUnicode(rawText)
        val pattern = Pattern.compile("(?i)(\\.claim\\s+[A-Z0-9]+)")
        val matcher = pattern.matcher(normalized)
        return if (matcher.find()) matcher.group(1) else null
    }
}
