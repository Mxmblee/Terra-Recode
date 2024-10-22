/*
 * Terra Hacked Client
 */

package net.ccbluex.liquidbounce.injection.backend

import net.ccbluex.liquidbounce.api.minecraft.network.IEnumConnectionState
import net.ccbluex.liquidbounce.api.minecraft.network.handshake.client.ICPacketHandshake
import net.minecraft.network.handshake.client.C00Handshake

class CPacketHandshakeImpl<T : C00Handshake>(wrapped: T) : PacketImpl<T>(wrapped), ICPacketHandshake {
    override val port: Int
        get() = wrapped.port
    override var ip: String
        get() = wrapped.ip
        set(value) {
            wrapped.ip = value
        }
    override val requestedState: IEnumConnectionState
        get() = wrapped.requestedState.wrap()
}

inline fun ICPacketHandshake.unwrap(): C00Handshake = (this as CPacketHandshakeImpl<*>).wrapped
inline fun C00Handshake.wrap(): ICPacketHandshake = CPacketHandshakeImpl(this)