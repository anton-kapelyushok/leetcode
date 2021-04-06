package searchsuggestionssystem;

import org.junit.jupiter.api.Test
import java.util.*
import kotlin.test.assertEquals

class Solution {
    fun suggestedProducts(products: Array<String>, searchWord: String): List<List<String>> {
        Arrays.sort(products)

        return (searchWord.indices).map { start ->
            val prefix = searchWord.substring(0..start)

            var l = 0
            var r = products.size - 1

            while (l < r) {
                val m = (l + r) / 2

                if (products[m] >= prefix) {
                    r = m
                } else {
                    l = m + 1
                }
            }

            (l..l+2).mapNotNull {
                if (it in products.indices && products[it].startsWith(prefix)) products[it]
                else null
            }
        }
    }
}

class SolutionTest {

    private val s = Solution()

    @Test
    fun test1() {
        assertEquals(listOf(listOf("baggage", "bags", "banner"), listOf("baggage", "bags", "banner"), listOf("baggage", "bags"), listOf("bags")), s.suggestedProducts(arrayOf("bags","baggage","banner","box","cloths"), "bags"))
    }

}
