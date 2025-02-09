/*
 * Terra Hacked Client
 */
package net.ccbluex.liquidbounce.injection.forge.mixins.block;

import net.ccbluex.liquidbounce.Terra;
import net.ccbluex.liquidbounce.features.module.modules.movement.NoSlow;
import net.minecraft.block.BlockSoulSand;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Objects;

@Mixin(BlockSoulSand.class)
@SideOnly(Side.CLIENT)
public class MixinBlockSoulSand {

    @Inject(method = "onEntityCollidedWithBlock", at = @At("HEAD"), cancellable = true)
    private void onEntityCollidedWithBlock(CallbackInfo callbackInfo) {
        final NoSlow noSlow = (NoSlow) Terra.moduleManager.getModule(NoSlow.class);

        if (Objects.requireNonNull(noSlow).getState() && noSlow.getSoulsandValue().get())
            callbackInfo.cancel();
    }
}