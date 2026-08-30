package timebasedkeyvaluestore


import org.junit.jupiter.api.Test
import java.util.*
import kotlin.test.assertEquals

class TimeMap() {
    private val data = mutableMapOf<String, TreeMap<Int, String>>()

    fun set(key: String, value: String, timestamp: Int) {
        val treemap = data.computeIfAbsent(key) { TreeMap() }
        treemap[timestamp] = value
    }

    fun get(key: String, timestamp: Int): String? {
        return data[key]?.floorEntry(timestamp)?.value
    }

}

class SolutionTest {


    @Test
    fun test1() {
        val timeMap = TimeMap()
        timeMap.set("foo", "bar", 1) // store the key "foo" and value "bar" along with timestamp = 1.
        assertEquals(
            "bar",
            timeMap.get("foo", 1) // return "bar"
        )
        assertEquals(
            "bar",
            timeMap.get("foo", 3), // return "bar", since there is no value corresponding to foo at timestamp 3 and timestamp 2, then the only value is at timestamp 1 is "bar".
        )
        timeMap.set("foo", "bar2", 4) // store the key "foo" and value "bar2" along with timestamp = 4.
        assertEquals(
            "bar2",
            timeMap.get("foo", 4), // return "bar2"
        )
        assertEquals(
            "bar2",
            timeMap.get("foo", 5) // return "bar2"
        )

    }

}
