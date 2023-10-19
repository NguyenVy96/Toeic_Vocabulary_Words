package com.vynguyen.toeicvocabularywords.data

import com.vynguyen.toeicvocabularywords.R
import com.vynguyen.toeicvocabularywords.utils.Words
import com.vynguyen.toeicvocabularywords.vocabulary.Vocabulary

object VocabularyData {

    private var dataList = HashMap<Int, List<Vocabulary>>()

    init {
        createDataLesson0()
    }

    private fun createDataLesson0() {
        val lesson = listOf<Vocabulary>(
            Vocabulary(
                R.drawable.ic_abide_by,
                R.raw.abide_by,
                "abide by",
                "əˈbaɪd baɪ",
                Words.V,
                listOf("tuân theo")
            ),
            Vocabulary(
                R.drawable.ic_agreement,
                R.raw.agreement,
                "agreement",
                "əˈɡrimənt",
                Words.N,
                listOf("sự thỏa thuân", "hợp đồng")
            ),
            Vocabulary(
                R.drawable.ic_assurance,
                R.raw.assurance,
                "assurance",
                "əˈʃʊrəns",
                Words.N,
                listOf("sự đảm bảo", "sự cam đoan")
            ),
            Vocabulary(
                R.drawable.ic_cancellation,
                R.raw.cancellation,
                "cancellation",
                "ˌkænsəˈleɪʃən",
                Words.N,
                listOf("sự hủy bỏ")
            ),
            Vocabulary(
                R.drawable.ic_determine,
                R.raw.determine,
                "determine",
                "dəˈtɜrmən",
                Words.V,
                listOf("xác định", "quyết định")
            ),
            Vocabulary(
                R.drawable.ic_engagement,
                R.raw.engagement,
                "engagement",
                "ɛnˈɡeɪʤmənt",
                Words.N,
                listOf("sự cam kết", "hôn ước")
            ),
            Vocabulary(
                R.drawable.ic_establish,
                R.raw.establish,
                "establish",
                "ɪˈstæblɪʃ",
                Words.V,
                listOf("thành lập", "củng cố")
            ),
            Vocabulary(
                R.drawable.ic_obligate,
                R.raw.obligate,
                "obligate",
                "ˈɑbləˌɡeɪt",
                Words.V,
                listOf("bắt buộc", "ép buộc")
            ),
            Vocabulary(
                R.drawable.ic_party,
                R.raw.party,
                "party",
                "ˈpɑrti",
                Words.N,
                listOf("đảng", "bên")
            ),
            Vocabulary(
                R.drawable.ic_provision,
                R.raw.provision,
                "provision",
                "prəˈvɪʒən",
                Words.N,
                listOf("sự cung cấp", "sự dự phòng")
            ),
            Vocabulary(
                R.drawable.ic_resolve,
                R.raw.resolve,
                "resolve",
                "riˈzɑlv",
                Words.V,
                listOf("giải quyết")
            ),
            Vocabulary(
                R.drawable.ic_specific,
                R.raw.specific,
                "specific",
                "spɪˈsɪfɪk",
                Words.ADJ,
                listOf("rõ ràng", "cụ thể")
            )
        )

        dataList[0] = lesson
    }

    fun getVocabularyData(lesson: Int): List<Vocabulary>? {
        val list = dataList[lesson]
        if (list.isNullOrEmpty()) {
            return dataList[0]
        }
        return list
    }
}