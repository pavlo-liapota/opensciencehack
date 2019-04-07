package se.opensciencehack.ateam.natural.utils

private const val columnCount = 14

fun Sequence<String>.splitIntoLines(): Sequence<String> {
    val original = this
    var comaCnt = 0
    val list = mutableListOf<String>()
    return sequence {
        original.forEach {
            list.add(it)
            comaCnt += it.count { it == ',' }
            if (comaCnt == columnCount - 1) {
                // skip header
                if (!list[0].startsWith(",")) {
                    yield(list.joinToString(separator = " "))
                }
                comaCnt = 0
                list.clear()
            }
        }
    }
}
