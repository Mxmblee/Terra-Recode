/*
 * Terra Hacked Client
 */

package net.ccbluex.liquidbounce.injection.backend

import net.ccbluex.liquidbounce.api.minecraft.potion.IPotion
import net.minecraft.potion.Potion

class PotionImpl(val wrapped: Potion) : IPotion {
    override val liquidColor: Int
        get() = wrapped.liquidColor
    override val id: Int
        get() = Potion.getIdFromPotion(wrapped)
    override val name: String
        get() = wrapped.name
    override val hasStatusIcon: Boolean
        get() = wrapped.hasStatusIcon()
    override val statusIconIndex: Int
        get() = wrapped.statusIconIndex

    override fun equals(other: Any?): Boolean {
        return other is PotionImpl && other.wrapped == this.wrapped
    }
}

inline fun IPotion.unwrap(): Potion = (this as PotionImpl).wrapped
inline fun Potion.wrap(): IPotion = PotionImpl(this)