/*
 * Terra Hacked Client
 */
package net.ccbluex.liquidbounce.features.module.modules.misc

import net.ccbluex.liquidbounce.Terra
import net.ccbluex.liquidbounce.event.EventTarget
import net.ccbluex.liquidbounce.event.Render2DEvent
import net.ccbluex.liquidbounce.features.module.Module
import net.ccbluex.liquidbounce.features.module.ModuleCategory
import net.ccbluex.liquidbounce.features.module.ModuleInfo
import net.ccbluex.liquidbounce.utils.ClientUtils
import net.ccbluex.liquidbounce.utils.render.ColorUtils.stripColor
import org.lwjgl.input.Mouse

@ModuleInfo(name = "MidClick", description = "Allows you to add a player as a friend by right clicking him.", category = ModuleCategory.MISC)
class MidClick : Module() {
    private var wasDown = false

    @EventTarget
    fun onRender(event: Render2DEvent?) {
        if (mc.currentScreen != null)
            return

        if (!wasDown && Mouse.isButtonDown(2)) {
            val entity = mc.objectMouseOver!!.entityHit

            if (classProvider.isEntityPlayer(entity)) {
                val playerName = stripColor(entity!!.name)
                val friendsConfig = Terra.fileManager.friendsConfig

                if (!friendsConfig.isFriend(playerName)) {
                    friendsConfig.addFriend(playerName)
                    Terra.fileManager.saveConfig(friendsConfig)
                    ClientUtils.displayChatMessage("§a§l$playerName§c was added to your friends.")
                } else {
                    friendsConfig.removeFriend(playerName)
                    Terra.fileManager.saveConfig(friendsConfig)
                    ClientUtils.displayChatMessage("§a§l$playerName§c was removed from your friends.")
                }

            } else
                ClientUtils.displayChatMessage("§c§lError: §aYou need to select a player.")
        }
        wasDown = Mouse.isButtonDown(2)
    }
}