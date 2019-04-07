package se.opensciencehack.ateam.natural.analysis

import se.opensciencehack.ateam.natural.data.getEvents
import se.opensciencehack.ateam.natural.utils.Utils
import weka.clusterers.SimpleKMeans
import weka.core.converters.ConverterUtils

fun main() {
    val dataSource = ConverterUtils.DataSource(Utils.classLoader.getResource("output.csv").file)
    val data = dataSource.dataSet
    val options =
        "-init 0 -max-candidates 100 -periodic-pruning 10000 -min-density 2.0 -t1 -1.25 -t2 -1.0 -N 2 -A \"weka.core.EuclideanDistance -R first-last\" -I 500 -num-slots 1 -S 10"
    val kmean = SimpleKMeans()
    kmean.options = weka.core.Utils.splitOptions(options)
    kmean.buildClusterer(data)
    println(kmean.toString())

    val detectorEvent = mutableListOf<Int>()

    val events = getEvents()
    val muons = mutableSetOf<Int>()
    val radiation = mutableSetOf<Int>()
    events
        .forEach { event ->
            if (event.detectors[4].totalSampleCnt > 0) {
                detectorEvent.add(event.id)
                if (event.detectors[0].totalSampleCnt > 0) {
                    muons.add(event.id)
                } else {
                    radiation.add(event.id)
                }
            }
            if (event.detectors[11].totalSampleCnt > 0) {
                detectorEvent.add(event.id)
                if (event.detectors[1].totalSampleCnt > 0) {
                    muons.add(event.id)
                } else {
                    radiation.add(event.id)
                }
            }
        }

    val groups = IntArray(4)

    for (i in 0 until data.size) {
        val clusterId = kmean.clusterInstance(data[i])
        val eventId = detectorEvent[i]
        if (clusterId == 0) {
            if (eventId in muons) {
                groups[0]++
            }
            if (eventId in radiation) {
                groups[1]++
            }
        }
        if (clusterId == 1) {
            if (eventId in muons) {
                groups[2]++
            }
            if (eventId in radiation) {
                groups[3]++
            }
        }
    }

    groups.forEach { println(it) }
}
