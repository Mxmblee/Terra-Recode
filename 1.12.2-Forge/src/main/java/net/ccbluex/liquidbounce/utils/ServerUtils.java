/*
 * Terra Hacked Client
 */
package net.ccbluex.liquidbounce.utils;

import net.ccbluex.liquidbounce.api.minecraft.client.multiplayer.IServerData;
import net.ccbluex.liquidbounce.ui.client.GuiMainMenu;
import net.minecraft.client.gui.GuiMultiplayer;
import net.minecraft.client.multiplayer.GuiConnecting;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public final class ServerUtils extends MinecraftInstance {

    public static IServerData serverData;

    public static void connectToLastServer() {
        if(serverData == null)
            return;

        mc.displayGuiScreen(classProvider.createGuiConnecting(classProvider.createGuiMultiplayer(classProvider.wrapGuiScreen(new GuiMainMenu())), mc, serverData));
    }

    public static String getRemoteIp() {
        String serverIp = "Singleplayer";

        if (mc.getTheWorld().isRemote()) {
            final IServerData serverData = mc.getCurrentServerData();

            if(serverData != null)
                serverIp = serverData.getServerIP();
        }

        return serverIp;
    }
}