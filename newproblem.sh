#/bin/bash

name=$(echo $1 | sed -e 's/ //g')
pkg=$(echo $1 | tr '[:upper:]' '[:lower:]' | sed 's/ //g')
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

}" > "src/$pkg/$name.kt"
git add "src/$pkg/$name.kt"
idea "src/$pkg/$name.kt"
