#/bin/bash

pkg=$(echo $1 | tr '[:upper:]' '[:lower:]' | sed -e 's/ //')
mkdir "src/$pkg"
echo "package $pkg;

import kotlin.test.assertEquals
import org.junit.jupiter.api.Test

class Solution {

}

class SolutionTest {

    private val s = Solution()

    @Test
    fun test1() {
        assertEquals(0, s.solve())
    }

}" > "src/$pkg/$1.kt"
idea "src/$pkg/$1.kt"
