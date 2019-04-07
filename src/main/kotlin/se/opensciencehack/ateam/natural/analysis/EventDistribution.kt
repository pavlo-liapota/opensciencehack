package se.opensciencehack.ateam.natural.analysis

import se.opensciencehack.ateam.natural.data.getEvents

fun main() {
    val events = getEvents()
    val counts = events
        .map {
            var result = 0
            it.detectors
                .map { it.totalSampleCnt > 0 }
                .forEachIndexed { index, v ->
                    if (v) result = result.setBit(index)
                }
            result
        }
        .groupingBy { it }
        .eachCount()

    counts
        .toList()
        .sortedByDescending { it.second }
        .forEach { (value, count) ->
            val indexes = mutableListOf<Int>()
            repeat(12) { index ->
                if (value.getBit(index)) {
                    indexes.add(index)
                }
            }
            print(indexes.joinToString(prefix = "{", postfix = "}"))
            println(" $count")
        }
}

fun Int.getBit(index: Int): Boolean {
    return ((this shr index) and 1) == 1
}

fun Int.setBit(index: Int): Int {
    return this or (1 shl index)
}


