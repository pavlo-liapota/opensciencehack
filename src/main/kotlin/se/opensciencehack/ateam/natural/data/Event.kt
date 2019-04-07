package se.opensciencehack.ateam.natural.data

import se.opensciencehack.ateam.natural.utils.Utils
import se.opensciencehack.ateam.natural.utils.splitIntoLines
import java.io.File

data class Event(
    val id: Int,
    val sec: Int,
    val ns: Int,
    val detectors: List<Detector>
) {
    companion object {
        private val numberChars = charArrayOf('0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '.', '+', '-', '3')

        private val delimiterChars = charArrayOf(' ', '\n', '\r', '\t', ']')

        fun parse(line: String): Event {
            var index = 0

            fun parseNext(delimiters: CharArray): String {
                index = line.indexOfAny(numberChars, index)
                val pos = line.indexOfAny(delimiters, index)
                return line.substring(index until pos).trim().also {
                    index = pos + 1
                }
            }

            fun parseInt(vararg delimiters: Char): Int {
                return parseNext(delimiters).toInt()
            }

            fun parseDouble(vararg delimiters: Char): Double {
                return parseNext(delimiters).toDouble()
            }

            fun parseIntArray(): IntArray {
                return IntArray(12) { parseInt(*delimiterChars) }
            }

            fun parseDoubleArray(): DoubleArray {
                return DoubleArray(12) { parseDouble(*delimiterChars) }
            }

            parseInt(',')
            val id = parseInt(',')
            val sec = parseInt(',')
            val ns = parseInt(',')
            val q = parseDoubleArray()
            val maxTime = parseDoubleArray()
            val startTime = parseDoubleArray()
            val a = parseIntArray()
            val totalSampleCnt = parseIntArray()
            val fallSampleCnt = parseIntArray()
            val riseSampleCnt = parseIntArray()
            parseIntArray()
            val halfMaxSampleCnt = parseIntArray()
            val quality = parseIntArray()
            val detectors = (0..11).map {
                Detector(
                    q[it],
                    maxTime[it],
                    startTime[it],
                    a[it],
                    totalSampleCnt[it],
                    fallSampleCnt[it],
                    riseSampleCnt[it],
                    halfMaxSampleCnt[it],
                    quality[it]
                )
            }
            return Event(
                id,
                sec,
                ns,
                detectors
            )
        }

        fun parseAll(filePath: String): Sequence<Event> {
            return File(filePath)
                .readLines()
                .asSequence()
                .drop(1)
                .splitIntoLines()
                .map { parse(it) }
        }
    }
}

fun getEvents(): Sequence<Event> {
    val fileName = "reduced_data_mar_22_00000.csv"
    val filePath = Utils.classLoader.getResource(fileName).file
    return Event.parseAll(filePath)
}
