/*
 * Terra Hacked Client
 */
package net.ccbluex.liquidbounce.ui.client;

import net.ccbluex.liquidbounce.Terra;
import net.ccbluex.liquidbounce.api.minecraft.client.gui.IGuiButton;
import net.ccbluex.liquidbounce.api.minecraft.client.gui.IGuiScreen;
import net.ccbluex.liquidbounce.api.util.WrappedGuiScreen;
import net.ccbluex.liquidbounce.features.special.AntiForge;
import net.ccbluex.liquidbounce.ui.font.Fonts;
import org.lwjgl.input.Keyboard;

import java.io.IOException;

public class GuiAntiForge extends WrappedGuiScreen {

    private final IGuiScreen prevGui;

    private IGuiButton enabledButton;
    private IGuiButton fmlButton;
    private IGuiButton proxyButton;
    private IGuiButton payloadButton;

    public GuiAntiForge(final IGuiScreen prevGui) {
        this.prevGui = prevGui;
    }

    @Override
    public void initGui() {
        representedScreen.getButtonList().add(enabledButton = classProvider.createGuiButton(1, representedScreen.getWidth() / 2 - 100, representedScreen.getHeight() / 4 + 35, "Enabled (" + (AntiForge.enabled ? "On" : "Off") + ")"));
        representedScreen.getButtonList().add(fmlButton = classProvider.createGuiButton(2, representedScreen.getWidth() / 2 - 100, representedScreen.getHeight() / 4 + 50 + 25, "Block FML (" + (AntiForge.blockFML ? "On" : "Off") + ")"));
        representedScreen.getButtonList().add(proxyButton = classProvider.createGuiButton(3, representedScreen.getWidth() / 2 - 100, representedScreen.getHeight() / 4 + 50 + 25 * 2, "Block FML Proxy Packet (" + (AntiForge.blockProxyPacket ? "On" : "Off") + ")"));
        representedScreen.getButtonList().add(payloadButton = classProvider.createGuiButton(4, representedScreen.getWidth() / 2 - 100, representedScreen.getHeight() / 4 + 50 + 25 * 3, "Block Payload Packets (" + (AntiForge.blockPayloadPackets ? "On" : "Off") + ")"));

        representedScreen.getButtonList().add(classProvider.createGuiButton(0, representedScreen.getWidth() / 2 - 100, representedScreen.getHeight() / 4 + 55 + 25 * 4 + 5, "Back"));
    }

    @Override
    public void actionPerformed(IGuiButton button) {
        switch (button.getId()) {
            case 1:
                AntiForge.enabled = !AntiForge.enabled;
                enabledButton.setDisplayString("Enabled (" + (AntiForge.enabled ? "On" : "Off") + ")");
                Terra.fileManager.saveConfig(Terra.fileManager.valuesConfig);
                break;
            case 2:
                AntiForge.blockFML = !AntiForge.blockFML;
                enabledButton.setDisplayString("Block FML (" + (AntiForge.blockFML ? "On" : "Off") + ")");
                Terra.fileManager.saveConfig(Terra.fileManager.valuesConfig);
                break;
            case 3:
                AntiForge.blockProxyPacket = !AntiForge.blockProxyPacket;
                enabledButton.setDisplayString("Block FML Proxy Packet (" + (AntiForge.blockProxyPacket ? "On" : "Off") + ")");
                Terra.fileManager.saveConfig(Terra.fileManager.valuesConfig);
                break;
            case 4:
                AntiForge.blockPayloadPackets = !AntiForge.blockPayloadPackets;
                enabledButton.setDisplayString("Block Payload Packets (" + (AntiForge.blockPayloadPackets ? "On" : "Off") + ")");
                Terra.fileManager.saveConfig(Terra.fileManager.valuesConfig);
                break;
            case 0:
                mc.displayGuiScreen(prevGui);
                break;
        }
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        representedScreen.drawBackground(0);
        Fonts.font180.drawCenteredString("AntiForge", (int) (representedScreen.getWidth() / 2F), (int) (representedScreen.getHeight() / 8F + 5F), 4673984, true);

        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    public void keyTyped(char typedChar, int keyCode) throws IOException {
        if(Keyboard.KEY_ESCAPE == keyCode) {
            mc.displayGuiScreen(prevGui);
            return;
        }

        super.keyTyped(typedChar, keyCode);
    }
}