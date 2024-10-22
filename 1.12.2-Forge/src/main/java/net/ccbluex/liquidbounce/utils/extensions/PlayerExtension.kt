/*
 * Terra Hacked Client
 */
package net.ccbluex.liquidbounce.utils.extensions

import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntity
import net.ccbluex.liquidbounce.api.minecraft.client.entity.player.IEntityPlayer
import net.ccbluex.liquidbounce.api.minecraft.util.IAxisAlignedBB
import net.ccbluex.liquidbounce.api.minecraft.util.WVec3
import net.ccbluex.liquidbounce.utils.MinecraftInstance
import net.minecraft.entity.Entity
import net.minecraft.util.math.AxisAlignedBB
import kotlin.math.abs
import kotlin.math.pow
import kotlin.math.sqrt

/**
 * Allows to get the distance between the current entity and [entity] from the nearest corner of the bounding box
 */
fun IEntity.getDistanceToEntityBox(entity: IEntity): Double {
    val eyes = this.getPositionEyes(1F)
    val pos = getNearestPointBB(eyes, entity.entityBoundingBox)
    val xDist = abs(pos.xCoord - eyes.xCoord)
    val yDist = abs(pos.yCoord - eyes.yCoord)
    val zDist = abs(pos.zCoord - eyes.zCoord)
    return sqrt(xDist.pow(2) + yDist.pow(2) + zDist.pow(2))
}

fun IEntityPlayer.getPing(): Int {
    val playerInfo = MinecraftInstance.mc.netHandler.getPlayerInfo(uniqueID)
    return playerInfo?.responseTime ?: 0
}

fun getNearestPointBB(eye: WVec3, box: IAxisAlignedBB): WVec3 {
    val origin = doubleArrayOf(eye.xCoord, eye.yCoord, eye.zCoord)
    val destMins = doubleArrayOf(box.minX, box.minY, box.minZ)
    val destMaxs = doubleArrayOf(box.maxX, box.maxY, box.maxZ)
    for (i in 0..2) {
        if (origin[i] > destMaxs[i]) origin[i] = destMaxs[i] else if (origin[i] < destMins[i]) origin[i] = destMins[i]
    }
    return WVec3(origin[0], origin[1], origin[2])
}

val Entity.hitBox: AxisAlignedBB
    get() {
        val borderSize = collisionBorderSize.toDouble()
        return entityBoundingBox.expand(borderSize, borderSize, borderSize)
    }
