package com.example.sohan.customcalender

import org.junit.Assert
import org.junit.Test
import java.util.HashSet

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see [Testing documentation](http://d.android.com/tools/testing)
 */
class ExampleUnitTest {
    @Test
    @Throws(Exception::class)
    fun addition_isCorrect() {
        Assert.assertEquals(4, (2 + 2).toLong())
        val hash = HashSet<String>()
        hash.add("sohan")
        hash.add("sohan")
        hash.forEach{
            print(it)
        }
    }
}