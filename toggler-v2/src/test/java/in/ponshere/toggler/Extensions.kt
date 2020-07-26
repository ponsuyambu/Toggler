package `in`.ponshere.toggler

import org.junit.Assert

//infix fun String.isEquals(other: String) = Assert.assertEquals(this, other)

infix fun<T> T.isEquals(other: T) = Assert.assertEquals(this, other)
infix fun<T> Array<T>.isEqualArrays(other: Array<T>) = Assert.assertArrayEquals(this, other)
