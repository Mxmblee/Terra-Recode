/*
 * Terra Hacked Client
 */

package net.ccbluex.liquidbounce.api.minecraft.client.network

import net.ccbluex.liquidbounce.api.minecraft.INetworkManager
import net.ccbluex.liquidbounce.api.minecraft.network.IPacket
import java.util.*

interface IINetHandlerPlayClient {
    val networkManager: INetworkManager
    val playerInfoMap: Collection<INetworkPlayerInfo>

    fun getPlayerInfo(uuid: UUID): INetworkPlayerInfo?
    fun addToSendQueue(classProviderCPacketHeldItemChange: IPacket)

}