/*
 * Terra Hacked Client
 */

package net.ccbluex.liquidbounce.injection.backend

import com.mojang.authlib.GameProfile
import net.ccbluex.liquidbounce.api.IExtractedFunctions
import net.ccbluex.liquidbounce.api.minecraft.client.block.IBlock
import net.ccbluex.liquidbounce.api.minecraft.enchantments.IEnchantment
import net.ccbluex.liquidbounce.api.minecraft.entity.IEnumCreatureAttribute
import net.ccbluex.liquidbounce.api.minecraft.inventory.IContainer
import net.ccbluex.liquidbounce.api.minecraft.inventory.ISlot
import net.ccbluex.liquidbounce.api.minecraft.item.IItem
import net.ccbluex.liquidbounce.api.minecraft.item.IItemStack
import net.ccbluex.liquidbounce.api.minecraft.potion.IPotion
import net.ccbluex.liquidbounce.api.minecraft.scoreboard.ITeam
import net.ccbluex.liquidbounce.api.minecraft.tileentity.ITileEntity
import net.ccbluex.liquidbounce.api.minecraft.util.IIChatComponent
import net.ccbluex.liquidbounce.api.minecraft.util.IResourceLocation
import net.ccbluex.liquidbounce.api.util.WrappedCollection
import net.minecraft.block.Block
import net.minecraft.client.Minecraft
import net.minecraft.client.renderer.GlStateManager
import net.minecraft.client.renderer.OpenGlHelper
import net.minecraft.client.renderer.RenderHelper
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher
import net.minecraft.client.resources.I18n
import net.minecraft.client.settings.GameSettings
import net.minecraft.enchantment.Enchantment
import net.minecraft.enchantment.EnchantmentHelper
import net.minecraft.init.Items
import net.minecraft.inventory.Container
import net.minecraft.inventory.Slot
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.potion.Potion
import net.minecraft.scoreboard.ScorePlayerTeam
import net.minecraft.util.ResourceLocation
import net.minecraft.util.text.ITextComponent
import java.lang.reflect.Field

object ExtractedFunctionsImpl : IExtractedFunctions {
    private var fastRenderField: Field? = null

    init {
        try {
            val declaredField = GameSettings::class.java.getDeclaredField("ofFastRender")

            fastRenderField = declaredField

            if (!declaredField.isAccessible)
                declaredField.isAccessible = true
        } catch (ignored: NoSuchFieldException) {
        }
    }

    override fun getBlockById(id: Int): IBlock? = Block.getBlockById(id)?.let(::BlockImpl)

    override fun getIdFromBlock(block: IBlock): Int = Block.getIdFromBlock(block.unwrap())

    override fun getModifierForCreature(heldItem: IItemStack?, creatureAttribute: IEnumCreatureAttribute): Float = EnchantmentHelper.getModifierForCreature(heldItem?.unwrap(), creatureAttribute.unwrap())

    override fun getObjectFromItemRegistry(res: IResourceLocation): IItem? = Item.REGISTRY.getObject(res.unwrap())?.wrap()

    override fun renderTileEntity(tileEntity: ITileEntity, partialTicks: Float, destroyStage: Int) = TileEntityRendererDispatcher.instance.render(tileEntity.unwrap(), partialTicks, destroyStage)

    override fun getBlockFromName(name: String): IBlock? = Block.getBlockFromName(name)?.wrap()

    override fun getItemByName(name: String): IItem? = (Items::class.java.getField(name).get(null) as Item).wrap()

    override fun getEnchantmentByLocation(location: String): IEnchantment? = Enchantment.getEnchantmentByLocation(location)?.wrap()

    override fun getEnchantmentById(enchantID: Int): IEnchantment? = Enchantment.getEnchantmentByID(enchantID)?.wrap()

    override fun getEnchantments(): Collection<IResourceLocation> = WrappedCollection(Enchantment.REGISTRY.keys, IResourceLocation::unwrap, ResourceLocation::wrap)

    override fun getItemRegistryKeys(): Collection<IResourceLocation> = WrappedCollection(Item.REGISTRY.keys, IResourceLocation::unwrap, ResourceLocation::wrap)

    override fun getBlockRegistryKeys(): Collection<IResourceLocation> = WrappedCollection(Block.REGISTRY.keys, IResourceLocation::unwrap, ResourceLocation::wrap)

    override fun disableStandardItemLighting() = RenderHelper.disableStandardItemLighting()

    override fun formatI18n(key: String, vararg values: String): String = I18n.format(key, values)

    override fun sessionServiceJoinServer(profile: GameProfile, token: String, sessionHash: String) = Minecraft.getMinecraft().sessionService.joinServer(profile, token, sessionHash)

    override fun getPotionById(potionID: Int): IPotion = Potion.getPotionById(potionID)!!.wrap()

    override fun enableStandardItemLighting() = RenderHelper.enableStandardItemLighting()

    override fun scoreboardFormatPlayerName(scorePlayerTeam: ITeam?, playerName: String): String = ScorePlayerTeam.formatPlayerName(scorePlayerTeam?.unwrap(), playerName)

    override fun disableFastRender() {
        try {
            val fastRenderer = fastRenderField

            if (fastRenderer != null) {
                if (!fastRenderer.isAccessible)
                    fastRenderer.isAccessible = true

                fastRenderer.setBoolean(Minecraft.getMinecraft().gameSettings, false)
            }
        } catch (ignored: IllegalAccessException) {
        }
    }

    override fun jsonToComponent(toString: String): IIChatComponent = ITextComponent.Serializer.jsonToComponent(toString)!!.wrap()
    override fun setActiveTextureLightMapTexUnit() = GlStateManager.setActiveTexture(OpenGlHelper.lightmapTexUnit)

    override fun setActiveTextureDefaultTexUnit() = GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit)

    override fun getItemById(id: Int): IItem? = Item.getItemById(id).wrap()
    override fun getIdFromItem(sb: IItem): Int = Item.getIdFromItem(sb.unwrap())

    override fun canAddItemToSlot(slotIn: ISlot, stack: IItemStack, stackSizeMatters: Boolean): Boolean = Container.canAddItemToSlot(slotIn.unwrap(),stack.unwrap(),stackSizeMatters)

}