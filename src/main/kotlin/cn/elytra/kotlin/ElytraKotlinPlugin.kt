package cn.elytra.kotlin

import org.bukkit.ChatColor.*
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.plugin.java.JavaPlugin
import java.io.File
import java.net.URLClassLoader
import java.util.logging.Level

class ElytraKotlinPlugin: JavaPlugin() {

	override fun onEnable() {
		logger.info("${BLUE}Kotlin ${GOLD}${KotlinVersion.CURRENT} ${BLUE}Installed!")

		runCatching {
			loadExtraJars()
		}.onFailure {
			logger.log(Level.WARNING, "Error occurs while loading extra jars", it)
		}
	}

	private val extraFolder = File(dataFolder, "extra").also {
		if(!it.exists()) { it.mkdirs() }
	}

	lateinit var extraClassLoader: URLClassLoader

	private fun loadExtraJars() {
		val jarFiles = extraFolder.listFiles()
		val jarUrls = jarFiles?.map { it.toURI().toURL() }?.toTypedArray()
		if(jarUrls == null) {
			logger.info { "${BLUE}No extra jar found to be load" }
		} else {
			logger.info { "${BLUE}Trying to load ${jarUrls.size} extra jars in extra folder" }
			jarUrls.forEach { logger.info { " - ${it.file}" } }

			extraClassLoader = URLClassLoader.newInstance(jarUrls, classLoader)
			logger.info { "${BLUE}Extra jars has been loaded" }
		}
	}

	override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
		if(label == "kotlin") {
			when(args.getOrNull(0)) {
				"class" -> {
					val className = args.getOrNull(1)
					val initialize = args.getOrNull(2) == "true"
					if(className == null) {
						sender.sendMessage("${GRAY}/kotlin class ${RED}<className> ${GRAY}[initialize]")
					} else {
						runCatching {
							Class.forName(className, initialize, extraClassLoader)
						}.onFailure {
							when(it) {
								is ClassNotFoundException -> {
									sender.sendMessage("${GOLD}[ClassNotFoundException] ${RED}$className")
								}
								is NoClassDefFoundError -> {
									sender.sendMessage("${GOLD}[NoClassDefFoundError] ${RED}${it.message}")
								}
								else -> {
									sender.sendMessage("${GOLD}${it.javaClass.simpleName} ${RED}${it.message} (Check Console for more info)")
								}
							}
							logger.log(Level.INFO, "Error occurs while finding $className", it)
						}.onSuccess {
							sender.sendMessage("${GREEN}Found ${it.canonicalName}")
						}
					}
				}
				else -> {
					sender.sendMessage("${GRAY}Kotlin ${GOLD}${KotlinVersion.CURRENT} ${GRAY}Installed.")
				}
			}
			return true
		}
		return false
	}

	override fun onTabComplete(
		sender: CommandSender,
		command: Command,
		label: String,
		args: Array<out String>
	): List<String>? {
		if(label == "kotlin") {
			println(args.toList())
			return when(args.getOrNull(0)) {
				"class" -> {
					null
				}
				else -> {
					listOf("class")
				}
			}
		}
		return null
	}

}