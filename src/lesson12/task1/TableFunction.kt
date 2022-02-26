@file:Suppress("UNUSED_PARAMETER")

package lesson12.task1


import kotlin.math.abs

/**
 * Класс "табличная функция".
 *
 * Общая сложность задания -- средняя, общая ценность в баллах -- 16.
 * Объект класса хранит таблицу значений функции (y) от одного аргумента (x).
 * В таблицу можно добавлять и удалять пары (x, y),
 * найти в ней ближайшую пару (x, y) по заданному x,
 * найти (интерполяцией или экстраполяцией) значение y по заданному x.
 *
 * Класс должен иметь конструктор по умолчанию (без параметров).
 */
class TableFunction {

    var map = mutableMapOf<Double, Double>()

    /**
     * Количество пар в таблице
     */
    val size: Int get() = map.size

    /**
     * Добавить новую пару.
     * Вернуть true, если пары с заданным x ещё нет,
     * или false, если она уже есть (в этом случае перезаписать значение y)
     */
    fun add(x: Double, y: Double): Boolean {
        return if (x !in map) {
            map += x to y
            true
        } else false
    }

    /**
     * Удалить пару с заданным значением x.
     * Вернуть true, если пара была удалена.
     */
    fun remove(x: Double): Boolean {
        return if (x !in map) false
        else {
            map -= x
            true
        }
    }

    /**
     * Вернуть коллекцию из всех пар в таблице
     */
    fun getPairs(): Collection<Pair<Double, Double>> {
        val collection = mutableListOf<Pair<Double, Double>>()
        for ((x, y) in map) {
            collection += Pair(x, y)
        }
        return collection
    }

    /**
     * Вернуть пару, ближайшую к заданному x.
     * Если существует две ближайшие пары, вернуть пару с меньшим значением x.
     * Если таблица пуста, бросить IllegalStateException.
     */
    fun findPair(x: Double): Pair<Double, Double>? {
        if (map.isEmpty()) throw IllegalStateException()
        if (x in map) return Pair(x, map.getOrDefault(x, 0.0))
        var minDif = x - map.keys.first()
        for ((key) in map) {
            val dif = key - x
            if ((abs(dif) == abs(minDif) && dif > minDif) || (abs(dif) <= abs(minDif))) minDif = dif
        } //дополнить
        return Pair(x + minDif, map.getOrDefault(x + minDif, 0.0))
    }

    /**
     * Вернуть значение y по-заданному x.
     * Если в таблице есть пара с заданным x, взять значение y из неё.
     * Если в таблице есть всего одна пара, взять значение y из неё.
     * Если таблица пуста, бросить IllegalStateException.
     * Если существуют две пары, такие, что x1 < x < x2, использовать интерполяцию.
     * Если их нет, но существуют две пары, такие, что x1 < x2 < x или x > x2 > x1, использовать экстраполяцию.
     */
    private fun extrapolation(x: Double, x1: Double, x2: Double): Double {
        val y1 = map.getOrDefault(x1, 0.0)
        val y2 = map.getOrDefault(x2, 0.0)
        return y1 + ((y2 - y1) / (x2 - x1)) * (x - x1)
    }

    fun getValue(x: Double): Double {
        if (x in map) return map.getOrDefault(x, 0.0)
        if (map.size == 1) return map.getOrDefault(map.keys.first(), 0.0)
        if (map.isEmpty()) throw IllegalArgumentException()
        map = map.toSortedMap()
        if (x < map.keys.first()) {
            val x1 = map.keys.elementAt(0)
            val x2 = map.keys.elementAt(1)
            return extrapolation(x, x1, x2)
        } else if (x > map.keys.last()) {
            val x1 = map.keys.last()
            val x2 = map.keys.elementAt(map.size - 1)
            return extrapolation(x, x1, x2)
        } else {
            for (i in map.keys.indices) {
                if (x < map.keys.elementAt(i) && x > map.keys.elementAt(i - 1)) {
                    val x1 = map.keys.elementAt(i - 1)
                    val x2 = map.keys.elementAt(i)
                    val y1 = map.getOrDefault(x1, 0.0)
                    val y2 = map.getOrDefault(x2, 0.0)
                    return y1 + (x - x1) / (x2 - x1) * (y2 - y1)
                }
            }
        }
        return 0.0
    }

    /**
     * Таблицы равны, если в них одинаковое количество пар,
     * и любая пара из второй таблицы входит также и в первую
     */
    override fun equals(other: Any?): Boolean =
        if (other !is TableFunction) false
        else {
            map == other.map
        }

    override fun hashCode(): Int {
        var result = map.hashCode()
        result = 31 * result + size
        return result
    }
}
