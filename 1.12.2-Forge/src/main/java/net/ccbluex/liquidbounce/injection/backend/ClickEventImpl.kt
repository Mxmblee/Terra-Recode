/*
 * Terra Hacked Client
 */

package net.ccbluex.liquidbounce.injection.backend

import net.ccbluex.liquidbounce.api.minecraft.event.IClickEvent
import net.minecraft.util.text.event.ClickEvent

class ClickEventImpl(val wrapped: ClickEvent) : IClickEvent {

    override fun equals(other: Any?): Boolean {
        return other is ClickEventImpl && other.wrapped == this.wrapped
    }
}

inline fun IClickEvent.unwrap(): ClickEvent = (this as ClickEventImpl).wrapped
inline fun ClickEvent.wrap(): IClickEvent = ClickEventImpl(this)