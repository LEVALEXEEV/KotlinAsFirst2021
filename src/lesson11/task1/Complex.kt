@file:Suppress("UNUSED_PARAMETER")

package lesson11.task1

/**
 * Фабричный метод для создания комплексного числа из строки вида x+yi
 */
fun Complex(s: String): Complex {
    if (Regex("[0-9]+\\+|-[0-9]+i").find(s) == null) throw IllegalStateException()
    return if ("+" in s) {
        val parts = s.split("+")
        (Complex(parts[0].toDouble(), parts[1].removeSuffix("i").toDouble()))
    } else {
        val parts = s.split("-")
        (Complex(parts[0].toDouble(), parts[1].removeSuffix("i").toDouble() * -1.0))
    }
}

/**
 * Класс "комплексное число".
 *
 * Общая сложность задания -- лёгкая, общая ценность в баллах -- 8.
 * Объект класса -- комплексное число вида x+yi.
 * Про принципы работы с комплексными числами см. статью Википедии "Комплексное число".
 *
 * Аргументы конструктора -- вещественная и мнимая часть числа.
 */
class Complex(val re: Double, val im: Double) {

    /**
     * Конструктор из вещественного числа
     */
    constructor(x: Double) : this(x, 0.0)

    /**
     * Сложение.
     */
    operator fun plus(other: Complex): Complex = Complex(this.re + other.re, this.im + other.im)

    /**
     * Смена знака (у обеих частей числа)
     */
    operator fun unaryMinus(): Complex = Complex(-re, -im)

    /**
     * Вычитание
     */
    operator fun minus(other: Complex): Complex = Complex(re - other.re, im - other.im)

    /**
     * Умножение
     */
    operator fun times(other: Complex): Complex =
        Complex(re * other.re - im * other.im, im * other.re + re * other.im)

    /**
     * Деление
     */
    operator fun div(other: Complex): Complex {
        val d = other.re * other.re + other.im * other.im
        try {
            if ((d) != 0.0) {
                return Complex(
                    (re * other.re + im * other.im) / d,
                    (im * other.re - re * other.im) / d
                )
            } else throw IllegalStateException()
        } catch (ex: Exception) {
            println("Exception")
        }
        return Complex(0.0, 0.0)
    }

    /**
     * Сравнение на равенство
     */
    override fun equals(other: Any?): Boolean = when {
        this === other -> true
        other is Complex -> re == other.re && im == other.im
        else -> false
    }

    /**
     * Преобразование в строку
     */
    override fun toString(): String {
        val imPlus = if (im > 0) "+" else ""
        return "${re.toInt()}$imPlus${im.toInt()}i"
    }

    override fun hashCode(): Int {
        var result = re.hashCode()
        result = 31 * result + im.hashCode()
        return result
    }
}
