package se.opensciencehack.ateam.natural.analysis

import se.opensciencehack.ateam.natural.data.getEvents

fun main() {
    val events = getEvents()
    val amps = events
        .filter { it.detectors[1].totalSampleCnt > 0 && it.detectors[11].totalSampleCnt > 4 }
        .map { it.detectors[1].a }
        .sorted()
        .toList()

    val min = amps.first()
    val max = amps.last()
    val avg = amps.average()
    val mid = amps[amps.size / 2]
    val bot = amps[(amps.size * 0.15).toInt()]
    val top = amps[(amps.size * 0.85).toInt()]

    println("min = $min")
    println("max = $max")
    println("avg = $avg")
    println("mid = $mid")
    println("bot = $bot")
    println("top = $top")
}
