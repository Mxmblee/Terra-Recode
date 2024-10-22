/*
 * Terra Hacked Client
 */

package net.ccbluex.liquidbounce.injection.backend

import net.ccbluex.liquidbounce.api.minecraft.network.play.server.ISPacketPosLook
import net.minecraft.network.play.server.SPacketPlayerPosLook

class SPacketPosLookImpl<T : SPacketPlayerPosLook>(wrapped: T) : PacketImpl<T>(wrapped), ISPacketPosLook {
    override var yaw: Float
        get() = wrapped.yaw
        set(value) {
            wrapped.yaw = value
        }
    override var pitch: Float
        get() = wrapped.pitch
        set(value) {
            wrapped.pitch = value
        }

}

inline fun ISPacketPosLook.unwrap(): SPacketPlayerPosLook = (this as SPacketPosLookImpl<*>).wrapped
inline fun SPacketPlayerPosLook.wrap(): ISPacketPosLook = SPacketPosLookImpl(this)