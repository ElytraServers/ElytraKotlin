package cn.elytra.kotlin

import org.bukkit.plugin.java.JavaPlugin

import org.bukkit.ChatColor.*
import org.bukkit.command.Command
import org.bukkit.command.CommandSender

class ElytraKotlinPlugin: JavaPlugin() {

	override fun onEnable() {
		logger.info("${BLUE}Kotlin ${GOLD}${KotlinVersion.CURRENT} ${BLUE}Installed!")
	}

	override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
		if(label == "kotlin") {
			sender.sendMessage("${GRAY}Kotlin ${GOLD}${KotlinVersion.CURRENT} ${GRAY}Installed.")
			return true
		}
		return false
	}

}