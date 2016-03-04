package com.rocketraccoons.hyenas.cl4ptp.bean

/**
 * Created by instu_000 on 3/4/2016.
 */
class BushQuoteProcessor : QuoteProcessor {
    override fun handleQuote(rawInputQuote: String): String {
        val delims = arrayOf("<' + '/span><' + 'div", "<' + '/div><' + 'small><' + 'a");
        val strs = rawInputQuote.split(delimiters = *delims, ignoreCase = true);
        return strs[1].substring(strs[1].indexOf('>') + 1)
                .replace("<' + 'br>", "\n")
                .replace("<' + 'br />", "\n")
                .replace("&quot;", "\"")
                .replace("&lt;", "<")
                .replace("&gt;", ">");
    }
}
