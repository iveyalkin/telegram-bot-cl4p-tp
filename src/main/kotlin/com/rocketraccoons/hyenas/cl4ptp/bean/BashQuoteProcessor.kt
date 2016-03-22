package com.rocketraccoons.hyenas.cl4ptp.bean

import org.jsoup.Jsoup
import org.jsoup.select.Elements

/**
 * Created by instu_000 on 3/4/2016.
 */
class BashQuoteProcessor : QuoteProcessor {
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

    fun getNewQuote(rawInputQuote: String) {
        val html = Jsoup.parse(rawInputQuote)
        val elements = html?.select("#body.quote")
        if (null != elements) {
            val quotes = extractQuotes(elements)
        }
    }

    private fun extractQuotes(elements: Elements): Any {
        return elements.map {
            BashQuote(it.select(".id")?.text() ?: "",
                    it.select(".date")?.text() ?: "",
                    it.select(".rating")?.text()?.toInt() ?: 0,
                    it.select(".text")?.text() ?: "")
        }
    }

    class BashQuote(val id: String, val date: String, val rating: Int, val text: String)
}
