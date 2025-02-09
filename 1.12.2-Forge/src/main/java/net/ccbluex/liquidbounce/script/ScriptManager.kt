/*
 * Terra Hacked Client
 */
package net.ccbluex.liquidbounce.script

import net.ccbluex.liquidbounce.Terra
import net.ccbluex.liquidbounce.utils.ClientUtils
import java.io.File
import java.io.FileFilter

class ScriptManager {

    val scripts = mutableListOf<Script>()

    val scriptsFolder = File(Terra.fileManager.dir, "scripts")
    private val scriptFileExtension = ".js"

    /**
     * Loads all scripts inside the scripts folder.
     */
    fun loadScripts() {
        if(!scriptsFolder.exists())
            scriptsFolder.mkdir()

        scriptsFolder.listFiles(FileFilter { it.name.endsWith(scriptFileExtension) }).forEach { loadScript(it) }
    }

    /**
     * Unloads all scripts.
     */
    fun unloadScripts() {
        scripts.clear()
    }

    /**
     * Loads a script from a file.
     */
    fun loadScript(scriptFile : File) {
        try {
            scripts.add(Script(scriptFile))
            ClientUtils.getLogger().info("[ScriptAPI] Successfully loaded script '${scriptFile.name}'.")
        } catch(t : Throwable) {
            ClientUtils.getLogger().error("[ScriptAPI] Failed to load script '${scriptFile.name}'.", t)
        }
    }

    /**
     * Enables all scripts.
     */
    fun enableScripts() {
        scripts.forEach { it.onEnable() }
    }

    /**
     * Disables all scripts.
     */
    fun disableScripts() {
        scripts.forEach { it.onDisable() }
    }

    /**
     * Imports a script.
     * @param file JavaScript file to be imported.
     */
    fun importScript(file : File) {
        val scriptFile = File(scriptsFolder, file.name)
        file.copyTo(scriptFile)

        loadScript(scriptFile)
        ClientUtils.getLogger().info("[ScriptAPI]  Successfully imported script '${scriptFile.name}'.")
    }

    /**
     * Deletes a script.
     * @param script Script to be deleted.
     */
    fun deleteScript(script : Script) {
        script.onDisable()
        scripts.remove(script)
        script.scriptFile.delete()

        ClientUtils.getLogger().info("[ScriptAPI]  Successfully deleted script '${script.scriptFile.name}'.")
    }

    /**
     * Reloads all scripts.
     */
    fun reloadScripts() {
        disableScripts()
        unloadScripts()
        loadScripts()
        enableScripts()

        ClientUtils.getLogger().info("[ScriptAPI]  Successfully reloaded scripts.")
    }
}