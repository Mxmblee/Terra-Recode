/*
 * Terra Hacked Client
 */

package net.ccbluex.liquidbounce.api.util

interface IWrappedArray<T> : Iterable<T> {
    operator fun get(index: Int): T
    operator fun set(index: Int, value: T)

    companion object {
        inline fun <T> IWrappedArray<out T>.forEachIndexed(action: (index: Int, T) -> Unit): Unit {
            var index = 0
            for (item in this) action(index++, item)
        }
    }
}