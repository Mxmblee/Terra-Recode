/*
 * Terra Hacked Client
 */

package net.ccbluex.liquidbounce.injection.backend

import net.ccbluex.liquidbounce.api.minecraft.network.play.client.ICPacketUseEntity
import net.ccbluex.liquidbounce.injection.backend.utils.wrap
import net.minecraft.network.play.client.CPacketUseEntity

class CPacketUseEntityImpl<T : CPacketUseEntity>(wrapped: T) : PacketImpl<T>(wrapped), ICPacketUseEntity {
    override val action: ICPacketUseEntity.WAction
        get() = wrapped.action.wrap()
}

inline fun ICPacketUseEntity.unwrap(): CPacketUseEntity = (this as CPacketUseEntityImpl<*>).wrapped
inline fun CPacketUseEntity.wrap(): ICPacketUseEntity = CPacketUseEntityImpl(this)