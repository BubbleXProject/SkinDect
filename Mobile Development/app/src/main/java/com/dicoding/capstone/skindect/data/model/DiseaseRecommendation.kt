package com.dicoding.capstone.skindect.data.model

fun getDiseaseRecommendation(predict: String): String {
    return when (predict.lowercase()) {
        "panu" -> """
            • Gunakan obat antijamur (krim, salep, atau shampoo).
            • Jaga kebersihan tubuh.
            • Hindari pakaian yang terlalu ketat dan lembap.
        """.trimIndent()
        "kurap" -> """
            • Gunakan salep antijamur seperti clotrimazole.
            • Hindari berbagi pakaian atau handuk.
            • Jaga area kulit tetap kering dan bersih.
        """.trimIndent()
        "kudis" -> """
            • Gunakan krim atau losion khusus untuk kudis (permethrin).
            • Bersihkan pakaian dan tempat tidur secara menyeluruh.
            • Konsultasi dengan dokter jika gejala memburuk.
        """.trimIndent()
        "cacar" -> """
            • Hindari menggaruk ruam untuk mencegah infeksi.
            • Gunakan lotion calamine untuk mengurangi gatal.
            • Istirahat yang cukup dan konsumsi makanan bergizi.
        """.trimIndent()
        "biduran" -> """
            • Hindari pemicu alergi (makanan, cuaca, dll.).
            • Gunakan antihistamin untuk meredakan gatal.
            • Kompres dingin area yang terkena.
        """.trimIndent()
        "eksim" -> """
            • Gunakan pelembap secara rutin.
            • Hindari pemicu iritasi seperti sabun keras.
            • Gunakan krim kortikosteroid jika direkomendasikan dokter.
        """.trimIndent()
        else -> "Rekomendasi tidak tersedia untuk penyakit ini."
    }
}
