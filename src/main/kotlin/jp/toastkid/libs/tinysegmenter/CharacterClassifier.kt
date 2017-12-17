package jp.toastkid.libs.tinysegmenter

import java.util.HashMap
import java.util.regex.Pattern

/**

 * <HR></HR>
 * (130404) 作成開始<BR></BR>
 * @author Toast kid
 */
class CharacterClassifier {
    private val patterns = arrayOf("[一二三四五六七八九十百千万億兆]:M", "[一-龠々〆ヵヶ]:H", "[ぁ-ん]:I", "[ァ-ヴーｱ-ﾝﾞｰ]:K", "[a-zA-Zａ-ｚＡ-Ｚ]:A", "[0-9０-９]:N")
    private val pMap: Map<String, Pattern>

    /**
     * <HR></HR>
     * (130404) 作成開始<BR></BR>
     */
    init {
        pMap = HashMap<String, Pattern>(patterns.size)
        for (i in patterns.indices) {
            val pats = patterns[i].split(":")
            pMap.put(pats[1], Pattern.compile(pats[0], Pattern.DOTALL))
        }
    }

    /**
     * 文字種別を判定して返す。
     * <HR></HR>
     * (130404) 作成開始<BR></BR>
     * @param str
     * *
     * @return 文字種別
     */
    fun classify(str: String): String {
        val iterator = pMap.keys.iterator()
        while (iterator.hasNext()) {
            val key = iterator.next()
            if (pMap[key]?.matcher(str)?.find() ?: false) {
                return key
            }
        }
        return "O"
    }
}
