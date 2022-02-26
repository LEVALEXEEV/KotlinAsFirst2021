@file:Suppress("UNUSED_PARAMETER")

package lesson11.task1

/**
 * Фабричный метод для создания комплексного числа из строки вида x+yi
 */
fun Complex(s: String): Complex {
    return if ("+" in s) {
        val parts = s.split("+")
        (Complex(parts[0].toDouble(), parts[1].removeSuffix("i").toDouble()))
    } else {
        val parts = s.split("-")
        (Complex(parts[0].toDouble(), parts[1].removeSuffix("i").toDouble() * -1.0))
    }
}//проверить правильность данных

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
    operator fun unaryMinus(): Complex = Complex(
        re * -1.0,
        im * -1.0
    )

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
        if ((other.re * other.re + other.im * other.im) != 0.0) {
            return Complex(
                (re * other.re + im * other.im) / (other.re * other.re + other.im * other.im),
                (im * other.re - re * other.im) / (other.re * other.re + other.im * other.im)
            )
        } else throw IllegalStateException()
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
        val reMinus = if (re > 0) "+" else "-"
        val imMinus = if (im > 0) "+" else "-"
        return "$reMinus$re$imMinus${im}i"//знак
    }

    override fun hashCode(): Int {
        var result = re.hashCode()
        result = 31 * result + im.hashCode()
        return result
    }
}
