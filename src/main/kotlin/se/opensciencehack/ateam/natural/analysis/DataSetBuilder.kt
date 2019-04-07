package se.opensciencehack.ateam.natural.analysis

import se.opensciencehack.ateam.natural.data.getEvents
import java.io.File

fun main() {
    val events = getEvents()
    File("output.csv").bufferedWriter().use {
        it.write("q,a,totalSampleCnt,fallSampleCnt,riseSampleCnt")
        it.newLine()
        events.forEach { event ->
            listOf(event.detectors[4], event.detectors[11])
                .forEach { d ->
                    if (d.totalSampleCnt > 0) {
                        it.write("${d.q},${d.a},${d.totalSampleCnt},${d.fallSampleCnt},${d.riseSampleCnt}")
                        it.newLine()
                    }
                }
        }
    }
}
