package util

fun <T> Collection<T>.forEachSelfPaired(includeSelfPair: Boolean, block: (i1: T, i2: T) -> Unit) {
    forEachIndexed { index1, i1 ->
        forEachIndexed { index2, i2 ->
            if (includeSelfPair || index1 != index2) block(i1, i2)
        }
    }
}
