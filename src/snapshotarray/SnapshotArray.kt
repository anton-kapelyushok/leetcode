package snapshotarray

import org.junit.jupiter.api.Test
import java.util.*

class SnapshotArray(length: Int) {
    var currentSnapshotId = 0

    val data = List(length) { TreeMap<Int, Int>() }

    fun set(index: Int, value: Int) {
        data[index][currentSnapshotId] = value
    }

    fun snap(): Int {
        val snapshotId = currentSnapshotId++
        // do nothing here
        return snapshotId
    }

    fun get(index: Int, snapshotId: Int): Int {
        return data[index].floorEntry(snapshotId)?.value ?: 0
    }

}

/**
 * Your SnapshotArray object will be instantiated and called as such:
 * var obj = SnapshotArray(length)
 * obj.set(index,`val`)
 * var param_2 = obj.snap()
 * var param_3 = obj.get(index,snap_id)
 */
class SolutionTest {


    @Test
    fun test1() {
    }

}
