package se.opensciencehack.ateam.natural.data

data class Detector(
    val q: Double,
    val maxTime: Double,
    val startTime: Double,
    val a: Int,
    val totalSampleCnt: Int,
    val fallSampleCnt: Int,
    val riseSampleCnt: Int,
    val halfMaxSampleCnt: Int,
    val quality: Int
)
