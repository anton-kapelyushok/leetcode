#!/usr/bin/env bash

title="$1"

name=$(printf '%s' "$title" | sed -E 's/[[:space:]]+//g; s/-/_/g')
[[ "$name" =~ ^[0-9] ]] && name="_$name"

pkg=$(printf '%s' "$name" | tr '[:upper:]' '[:lower:]')

mkdir -p "src/$pkg"

cat > "src/$pkg/$name.kt" <<EOF
package $pkg

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
}
EOF

git add "src/$pkg/$name.kt"
idea "src/$pkg/$name.kt"