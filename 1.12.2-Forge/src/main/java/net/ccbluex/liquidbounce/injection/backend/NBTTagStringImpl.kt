/*
 * Terra Hacked Client
 */

package net.ccbluex.liquidbounce.injection.backend

import net.ccbluex.liquidbounce.api.minecraft.nbt.INBTTagString
import net.minecraft.nbt.NBTTagString

class NBTTagStringImpl<T : NBTTagString>(wrapped: T) : NBTBaseImpl<T>(wrapped), INBTTagString

inline fun INBTTagString.unwrap(): NBTTagString = (this as NBTTagStringImpl<*>).wrapped
inline fun NBTTagString.wrap(): INBTTagString = NBTTagStringImpl(this)