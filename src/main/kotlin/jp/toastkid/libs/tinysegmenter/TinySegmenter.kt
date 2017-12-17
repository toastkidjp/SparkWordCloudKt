package jp.toastkid.libs.tinysegmenter

import java.util.ArrayList
import java.util.regex.Pattern

/**
 * 形態素分解のできる JavaScript ライブラリ[TinySegmenter](http://chasen.org/~taku/software/TinySegmenter/) の Java 移植版
 * <HR></HR>
 * (130616) 先頭文字列とストップ文字とのマッチング処理追加<BR></BR>
 * (130414) 条件次第でフィルタを実行するよう修正<BR></BR>
 * (130404) 作成、遅くて使い物にならないので要改良<BR></BR>
 * @author Toast kid
 */
object TinySegmenter {

    /** バイアス  */
    val BIAS__ = -332

    /** 学習スコアのマップ.  */
    private val sMap: ScoreMap = ScoreMap()
    /** 結果に含めない文字の正規表現.  */
    private val stopChars = Pattern.compile("[。\\?\\*、,\\.．「」\\(\\)（）\\-\\[\\]\\|…・＜＞/_#%&/:『』]", Pattern.DOTALL)
    /** 文字種別の判別器.  */
    private val cc: CharacterClassifier = CharacterClassifier()
    /** ひらがなを結果に含めるか否か  */
    var isAllowHiragana = true
    /** 1文字を結果に含めるか否か  */
    var isAllowChar = true
    /** 数値を結果に含めるか否か  */
    var isAllowNum = true

    /**
     * 引数の文字列を形態素分解し、Listに格納して返す。
     * <HR></HR>
     * (130404) 作成開始<BR></BR>
     * @param input
     * *
     * @return List
     */
    fun segment(input: String?): List<String>? {
        if (input == null || input.isEmpty()) {
            return null
        }
        // 返却する形態素
        val result = ArrayList<String>(100)
        val seg = ArrayList<String>(100)
        seg.add("B3")
        seg.add("B2")
        seg.add("B1")
        val ctype = ArrayList<String>(100)
        ctype.add("O")
        ctype.add("O")
        ctype.add("O")
        val o = input.split("")
        for (i in o.indices) {
            seg.add(o[i])
            ctype.add(cc.classify(o[i]))
        }
        seg.add("E1")
        seg.add("E2")
        seg.add("E3")
        ctype.add("O")
        ctype.add("O")
        ctype.add("O")
        val word = StringBuilder(200)
        word.append(seg.get(3))
        var p1 = "U"
        var p2 = "U"
        var p3 = "U"
        for (i in 4..seg.size - 3 - 1) {
            var score = BIAS__
            val w1 = seg.get(i - 3)
            val w2 = seg.get(i - 2)
            val w3 = seg.get(i - 1)
            val w4 = seg.get(i)
            val w5 = seg.get(i + 1)
            val w6 = seg.get(i + 2)
            val c1 = ctype.get(i - 3)
            val c2 = ctype.get(i - 2)
            val c3 = ctype.get(i - 1)
            val c4 = ctype.get(i)
            val c5 = ctype.get(i + 1)
            val c6 = ctype.get(i + 2)
            // スコア計算
            score += score(sMap.UP1, p1)
            score += score(sMap.UP2, p2)
            score += score(sMap.UP3, p3)
            score += score(sMap.BP1, p1 + p2)
            score += score(sMap.BP2, p2 + p3)
            score += score(sMap.UW1, w1)
            score += score(sMap.UW2, w2)
            score += score(sMap.UW3, w3)
            score += score(sMap.UW4, w4)
            score += score(sMap.UW5, w5)
            score += score(sMap.UW6, w6)
            score += score(sMap.BW1, w2 + w3)
            score += score(sMap.BW2, w3 + w4)
            score += score(sMap.BW3, w4 + w5)
            score += score(sMap.TW1, w1 + w2 + w3)
            score += score(sMap.TW2, w2 + w3 + w4)
            score += score(sMap.TW3, w3 + w4 + w5)
            score += score(sMap.TW4, w4 + w5 + w6)
            score += score(sMap.UC1, c1)
            score += score(sMap.UC2, c2)
            score += score(sMap.UC3, c3)
            score += score(sMap.UC4, c4)
            score += score(sMap.UC5, c5)
            score += score(sMap.UC6, c6)
            score += score(sMap.BC1, c2 + c3)
            score += score(sMap.BC2, c3 + c4)
            score += score(sMap.BC3, c4 + c5)
            score += score(sMap.TC1, c1 + c2 + c3)
            score += score(sMap.TC2, c2 + c3 + c4)
            score += score(sMap.TC3, c3 + c4 + c5)
            score += score(sMap.TC4, c4 + c5 + c6)
            score += score(sMap.UQ1, p1 + c1)
            score += score(sMap.UQ2, p2 + c2)
            score += score(sMap.UQ3, p3 + c3)
            score += score(sMap.BQ1, p2 + c2 + c3)
            score += score(sMap.BQ2, p2 + c3 + c4)
            score += score(sMap.BQ3, p3 + c2 + c3)
            score += score(sMap.BQ4, p3 + c3 + c4)
            score += score(sMap.TQ1, p2 + c1 + c2 + c3)
            score += score(sMap.TQ2, p2 + c2 + c3 + c4)
            score += score(sMap.TQ3, p3 + c1 + c2 + c3)
            score += score(sMap.TQ4, p3 + c2 + c3 + c4)
            var p = "O"
            if (0 < score) {
                if (isAllowWord(word.toString().trim())) {
                    result.add(word.toString())
                }
                word.delete(0, word.length)
                p = "B"
            }
            p1 = p2
            p2 = p3
            p3 = p
            word.append(seg.get(i))
        }
        //result.add(word.toString());
        return result
    }

    /**
     * 対応するスコアを返す。
     * <HR></HR>
     * (130406) 作成<BR></BR>
     * @param str
     * *
     * @return score
     */
    fun score(map: Map<String, Int>, str: String): Int {
        if (map.containsKey(str)) {
            return map[str]?.toInt() ?: 0
        }
        return 0
    }

    /**
     * 結果に含めてよい形態素か否かを判定して返す。
     * <HR></HR>
     * (130616) 先頭文字列とストップ文字とのマッチング処理追加<BR></BR>
     * (130414) 作成<BR></BR>
     * @param word
     * *
     * @return 結果に含める形態素なら true
     */
    private fun isAllowWord(word: String): Boolean {
        if (!isAllowHiragana && "I" == cc.classify(word)) {
            return false
        }
        var firstChar = word
        if (1 < word.length) {
            firstChar = word.substring(0, 1)
        }
        if (!isAllowChar && stopChars.matcher(firstChar).matches()) {
            return false
        }
        return word.isNotEmpty()
    }

}
