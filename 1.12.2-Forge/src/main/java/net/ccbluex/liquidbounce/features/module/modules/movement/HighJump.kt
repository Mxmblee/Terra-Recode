/*
 * Terra Hacked Client
 */
package net.ccbluex.liquidbounce.features.module.modules.movement

import net.ccbluex.liquidbounce.api.minecraft.util.WBlockPos
import net.ccbluex.liquidbounce.event.EventTarget
import net.ccbluex.liquidbounce.event.JumpEvent
import net.ccbluex.liquidbounce.event.MoveEvent
import net.ccbluex.liquidbounce.event.UpdateEvent
import net.ccbluex.liquidbounce.features.module.Module
import net.ccbluex.liquidbounce.features.module.ModuleCategory
import net.ccbluex.liquidbounce.features.module.ModuleInfo
import net.ccbluex.liquidbounce.utils.MovementUtils
import net.ccbluex.liquidbounce.utils.block.BlockUtils.getBlock
import net.ccbluex.liquidbounce.value.BoolValue
import net.ccbluex.liquidbounce.value.FloatValue
import net.ccbluex.liquidbounce.value.ListValue

@ModuleInfo(name = "HighJump", description = "Allows you to jump higher.", category = ModuleCategory.MOVEMENT)
class HighJump : Module() {
    private val heightValue = FloatValue("Height", 2f, 1.1f, 5f)
    private val modeValue = ListValue("Mode", arrayOf("Vanilla", "Damage", "AACv3", "DAC", "Mineplex"), "Vanilla")
    private val glassValue = BoolValue("OnlyGlassPane", false)

    @EventTarget
    fun onUpdate(event: UpdateEvent?) {
        val thePlayer = mc.thePlayer!!

        if (glassValue.get() && !classProvider.isBlockPane(getBlock(WBlockPos(thePlayer.posX, thePlayer.posY, thePlayer.posZ))))
            return

        when (modeValue.get().toLowerCase()) {
            "damage" -> if (thePlayer.hurtTime > 0 && thePlayer.onGround) thePlayer.motionY += 0.42f * heightValue.get()
            "aacv3" -> if (!thePlayer.onGround) thePlayer.motionY += 0.059
            "dac" -> if (!thePlayer.onGround) thePlayer.motionY += 0.049999
            "mineplex" -> if (!thePlayer.onGround) MovementUtils.strafe(0.35f)
        }
    }

    @EventTarget
    fun onMove(event: MoveEvent?) {
        val thePlayer = mc.thePlayer ?: return

        if (glassValue.get() && !classProvider.isBlockPane(getBlock(WBlockPos(thePlayer.posX, thePlayer.posY, thePlayer.posZ))))
            return
        if (!thePlayer.onGround) {
            if ("mineplex" == modeValue.get().toLowerCase()) {
                thePlayer.motionY += if (thePlayer.fallDistance == 0.0f) 0.0499 else 0.05
            }
        }
    }

    @EventTarget
    fun onJump(event: JumpEvent) {
        val thePlayer = mc.thePlayer ?: return

        if (glassValue.get() && !classProvider.isBlockPane(getBlock(WBlockPos(thePlayer.posX, thePlayer.posY, thePlayer.posZ))))
            return
        when (modeValue.get().toLowerCase()) {
            "vanilla" -> event.motion = event.motion * heightValue.get()
            "mineplex" -> event.motion = 0.47f
        }
    }

    override val tag: String
        get() = modeValue.get()
}