/*
 * Terra Hacked Client
 */

package net.ccbluex.liquidbounce.injection.backend

import net.ccbluex.liquidbounce.api.minecraft.network.play.client.ICPacketCustomPayload
import net.ccbluex.liquidbounce.api.network.IPacketBuffer
import net.minecraft.network.play.client.CPacketCustomPayload

class CPacketCustomPayloadImpl<T : CPacketCustomPayload>(wrapped: T) : PacketImpl<T>(wrapped), ICPacketCustomPayload {
    override var data: IPacketBuffer
        get() = wrapped.data.wrap()
        set(value) {
            wrapped.data = value.unwrap()
        }
    override val channelName: String
        get() = wrapped.channelName

}

inline fun ICPacketCustomPayload.unwrap(): CPacketCustomPayload = (this as CPacketCustomPayloadImpl<*>).wrapped
inline fun CPacketCustomPayload.wrap(): ICPacketCustomPayload = CPacketCustomPayloadImpl(this)