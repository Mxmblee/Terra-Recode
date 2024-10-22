/*
 * Terra Hacked Client
 */
package net.ccbluex.liquidbounce.file.configs;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import net.ccbluex.liquidbounce.Terra;
import net.ccbluex.liquidbounce.api.minecraft.client.block.IBlock;
import net.ccbluex.liquidbounce.features.module.modules.render.XRay;
import net.ccbluex.liquidbounce.file.FileConfig;
import net.ccbluex.liquidbounce.file.FileManager;
import net.ccbluex.liquidbounce.utils.ClientUtils;

import java.io.*;

public class XRayConfig extends FileConfig {

    /**
     * Constructor of config
     *
     * @param file of config
     */
    public XRayConfig(final File file) {
        super(file);
    }

    /**
     * Load config from file
     *
     * @throws IOException
     */
    @Override
    protected void loadConfig() throws IOException {
        final XRay xRay = (XRay) Terra.moduleManager.getModule(XRay.class);

        if (xRay == null) {
            ClientUtils.getLogger().error("[FileManager] Failed to find xray module.");
            return;
        }

        final JsonArray jsonArray = new JsonParser().parse(new BufferedReader(new FileReader(getFile()))).getAsJsonArray();

        xRay.getXrayBlocks().clear();

        for (final JsonElement jsonElement : jsonArray) {
            try {
                final IBlock block = Terra.wrapper.getFunctions().getBlockFromName(jsonElement.getAsString());

                if (xRay.getXrayBlocks().contains(block)) {
                    ClientUtils.getLogger().error("[FileManager] Skipped xray block '" + block.getRegistryName() + "' because the block is already added.");
                    continue;
                }

                xRay.getXrayBlocks().add(block);
            } catch (final Throwable throwable) {
                ClientUtils.getLogger().error("[FileManager] Failed to add block to xray.", throwable);
            }
        }
    }

    /**
     * Save config to file
     *
     * @throws IOException
     */
    @Override
    protected void saveConfig() throws IOException {
        final XRay xRay = (XRay) Terra.moduleManager.getModule(XRay.class);

        if (xRay == null) {
            ClientUtils.getLogger().error("[FileManager] Failed to find xray module.");
            return;
        }

        final JsonArray jsonArray = new JsonArray();

        for (final IBlock block : xRay.getXrayBlocks())
            jsonArray.add(FileManager.PRETTY_GSON.toJsonTree(Terra.wrapper.getFunctions().getIdFromBlock(block)));

        final PrintWriter printWriter = new PrintWriter(new FileWriter(getFile()));
        printWriter.println(FileManager.PRETTY_GSON.toJson(jsonArray));
        printWriter.close();
    }
}
