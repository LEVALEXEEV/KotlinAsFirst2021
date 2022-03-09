package lesson11.task1

import lesson6.task1.computeDeviceCells
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Tag

internal class ComplexTest {

    private fun assertApproxEquals(expected: Complex, actual: Complex, eps: Double) {
        assertEquals(expected.re, actual.re, eps)
        assertEquals(expected.im, actual.im, eps)
    }

    @Test
    @Tag("2")
    fun complex() {
        assertApproxEquals(Complex("4-2i"), Complex(4.0, -2.0), 1e-10)
        assertApproxEquals(Complex("4+2i"), Complex(4.0, 2.0), 1e-10)
        assertThrows(IllegalStateException::class.java) { Complex("") }
        assertThrows(IllegalStateException::class.java) { Complex("affairs") }
    }

    @Test
    @Tag("2")
    fun plus() {
        assertApproxEquals(Complex("4-2i"), Complex("1+2i") + Complex("3-4i"), 1e-10)
    }

    @Test
    @Tag("2")
    operator fun unaryMinus() {
        assertApproxEquals(Complex(1.0, -2.0), -Complex(-1.0, 2.0), 1e-10)
    }

    @Test
    @Tag("2")
    fun minus() {
        assertApproxEquals(Complex("2-6i"), Complex("3-4i") - Complex("1+2i"), 1e-10)
    }

    @Test
    @Tag("4")
    fun times() {
        assertApproxEquals(Complex("11+2i"), Complex("1+2i") * Complex("3-4i"), 1e-10)
    }

    @Test
    @Tag("4")
    fun div() {
        assertApproxEquals(Complex("1+2i"), Complex("11+2i") / Complex("3-4i"), 1e-10)
        assertThrows(IllegalStateException::class.java) { Complex("11+2i") / Complex("0-0i") }
    }

    @Test
    @Tag("2")
    fun equals() {
        assertEquals(true, Complex("1+2i") == Complex(1.0, 2.0))
        assertEquals(false, Complex("1+2i") == Complex(0.0, 2.0))
    }

    @Test
    @Tag("2")
    fun ToString() {
        assertEquals("4-2i", Complex(4.0, -2.0).toString())
        assertEquals("-4-2i", Complex(-4.0, -2.0).toString())
        assertEquals("4+2i", Complex(4.0, 2.0).toString())
        assertEquals("-4+2i", Complex(-4.0, 2.0).toString())
    }

    @Test
    @Tag("2")
    fun HashCode() {
        assertEquals(Complex(4.0, 2.0).hashCode(), 4.0.hashCode() * 31 + 2.0.hashCode())
    }
}