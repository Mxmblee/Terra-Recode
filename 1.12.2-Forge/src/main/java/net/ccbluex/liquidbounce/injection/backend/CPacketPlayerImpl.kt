/*
 * Terra Hacked Client
 */

package net.ccbluex.liquidbounce.injection.backend

import net.ccbluex.liquidbounce.api.minecraft.network.play.client.ICPacketPlayer
import net.minecraft.network.play.client.CPacketPlayer

class CPacketPlayerImpl<T : CPacketPlayer>(wrapped: T) : PacketImpl<T>(wrapped), ICPacketPlayer {
    override var x: Double
        get() = wrapped.x
        set(value) {
            wrapped.x = value
        }
    override var y: Double
        get() = wrapped.y
        set(value) {
            wrapped.y = value
        }
    override var z: Double
        get() = wrapped.z
        set(value) {
            wrapped.z = value
        }
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
    override var onGround: Boolean
        get() = wrapped.onGround
        set(value) {
            wrapped.onGround = value
        }
    override var rotating: Boolean
        get() = wrapped.rotating
        set(value) {
            wrapped.rotating = value
        }

}

inline fun ICPacketPlayer.unwrap(): CPacketPlayer = (this as CPacketPlayerImpl<*>).wrapped
inline fun CPacketPlayer.wrap(): ICPacketPlayer = CPacketPlayerImpl(this)