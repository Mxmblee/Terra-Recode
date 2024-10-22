/*
 * Terra Hacked Client
 */

package net.ccbluex.liquidbounce.injection.backend

import net.ccbluex.liquidbounce.api.minecraft.inventory.ISlot
import net.ccbluex.liquidbounce.api.minecraft.item.IItemStack
import net.minecraft.inventory.Slot

class SlotImpl(val wrapped: Slot) : ISlot {
    override val slotNumber: Int
        get() = wrapped.slotNumber
    override val stack: IItemStack?
        get() = wrapped.stack?.wrap()


    override fun equals(other: Any?): Boolean {
        return other is SlotImpl && other.wrapped == this.wrapped
    }
    override val hasStack: Boolean
    get() = wrapped.hasStack
}

inline fun ISlot.unwrap(): Slot = (this as SlotImpl).wrapped
inline fun Slot.wrap(): ISlot = SlotImpl(this)