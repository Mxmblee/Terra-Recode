/*
 * Terra Hacked Client
 */

package net.ccbluex.liquidbounce.api.minecraft.client.gui.inventory

interface IGuiChest : IGuiContainer {
    val inventoryRows: Int
    val lowerChestInventory: IIInventory?
}