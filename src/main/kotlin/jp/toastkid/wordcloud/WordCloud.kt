package jp.toastkid.wordcloud

import java.util.HashMap

import jp.toastkid.libs.tinysegmenter.TinySegmenter

/**
 * Word cloud lib.

 * @author Toast kid
 */
class WordCloud {

    /**
     * count words in sentence.
     * @param sentence
     * *
     * @return
     */
    /*fun count(sentence: String): String {
        val data = HashMap(100)
        TinySegmenter.isAllowHiragana = false
        TinySegmenter.isAllowChar = false
        TinySegmenter.isAllowNum = false
        val list = TinySegmenter.segment(sentence)
        for (item in list) {
            item = item.replaceAll("\\s", "")
            if (item.contains(LINE_SEPARATOR)) {
                continue
            }
            var count = 1
            if (data.containsKey(item)) {
                count = data.get(item) + count
            }
            data.put(item, count)
        }
        return extract(data)
    }*/

    /**
     * extract string from data map.
     * @param data
     * *
     * @return
     */
    /*private fun extract(data: Map<String, Integer>): String {
        val dataStr = StringBuilder()
        dataStr.append("[")
        if (!data.isEmpty()) {
            for (key in data.keySet()) {
                if (1 < dataStr.length()) {
                    dataStr.append(", ")
                }
                dataStr.append("{\"word\"")
                        .append(": ")
                        .append(doubleQuote(key))
                        .append(",")
                        .append("\"count\": ")
                        .append(doubleQuote(Integer.toString(data[key])))
                        .append("}")
            }
        }
        dataStr.append("]")
        return dataStr.toString()
    }*/

    companion object {

        /** line separator.  */
        private val LINE_SEPARATOR = System.lineSeparator()

        /**
         * surround double quote.
         * @param str
         * *
         * @return
         */
        private fun doubleQuote(str: String): String {
            return "\"" + str + "\""
        }
    }

}
