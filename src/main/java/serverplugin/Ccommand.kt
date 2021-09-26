package serverplugin

import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.command.CommandSender
import org.bukkit.configuration.file.FileConfiguration
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import java.io.File

class Ccommand {
    fun onCommand(
        sender: CommandSender,
        args: Array<out String>,
        config: FileConfiguration,
        main: Main
    ): Boolean {
        if (sender is Player) {
            when (args[0]) {
                "point1" -> {
                    config.set("${sender.name}.x", sender.location.blockX)
                    config.set("${sender.name}.z", sender.location.blockZ)
                }
                "point2" -> {
                    config.set("${sender.name}.x1", sender.location.blockX)
                    config.set("${sender.name}.z1", sender.location.blockZ)
                }
                "position" -> {
                    if (sender.inventory.itemInMainHand.type == Material.DIAMOND && sender.inventory.itemInMainHand.amount >= 5 && sender.name == args[1]) {
                        sender.sendMessage("${args[1]}:x ${sender.location.x}    y ${sender.location.y}     z ${sender.location.z}")
                        val item: ItemStack = sender.inventory.itemInMainHand
                        item.amount -= 5
                        sender.inventory.setItemInMainHand(item)
                    }
                }
                "sethome" -> {
                    val home: String = args[1]
                    config.set("${sender.name}.${home}.x", sender.location.x)
                    config.set("${sender.name}.${home}.y", sender.location.y)
                    config.set("${sender.name}.${home}.z", sender.location.z)
                }
                "home" -> {
                    val home: String = args[1]
                    sender.sendMessage("a")
                    val loc: Location = sender.location
                    loc.x = config.getDouble("${sender.name}.${home}.x")
                    loc.y = config.getDouble("${sender.name}.${home}.y")
                    loc.z = config.getDouble("${sender.name}.${home}.z")
                    sender.teleport(loc)
                }
                "save" -> {
                    main.saveConfig()
                }
                "psy" -> {
                    config.set("${sender.name}.psy", args[1])
                }
                "load" -> {
                    main.reloadConfig()
                }
            }
            return true
        }else{
            return true
        }
    }
}