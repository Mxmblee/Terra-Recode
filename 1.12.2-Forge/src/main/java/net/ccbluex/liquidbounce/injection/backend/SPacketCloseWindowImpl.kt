/*
 * Terra Hacked Client
 */

package net.ccbluex.liquidbounce.injection.backend

import net.ccbluex.liquidbounce.api.minecraft.network.play.client.ISPacketCloseWindow
import net.minecraft.network.play.server.SPacketCloseWindow

class SPacketCloseWindowImpl<T : SPacketCloseWindow>(wrapped: T) : PacketImpl<T>(wrapped), ISPacketCloseWindow {
    override val windowId: Int
        get() = wrapped.windowId
}

inline fun ISPacketCloseWindow.unwrap(): SPacketCloseWindow = (this as SPacketCloseWindowImpl<*>).wrapped
inline fun SPacketCloseWindow.wrap(): ISPacketCloseWindow = SPacketCloseWindowImpl(this)