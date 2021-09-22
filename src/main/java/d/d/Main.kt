package d.d

import d.d.job.farmer
import d.d.psy.Kalistar
import d.d.psy.Trindamier
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.block.Block
import org.bukkit.block.data.Ageable
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.command.ConsoleCommandSender
import org.bukkit.configuration.file.FileConfiguration
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockBreakEvent
import org.bukkit.event.entity.EntityDamageByEntityEvent
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.inventory.ItemStack
import org.bukkit.plugin.java.JavaPlugin
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType
import org.bukkit.scheduler.BukkitRunnable
import java.io.File
import kotlin.math.ceil


class Main : JavaPlugin(),Listener,CommandExecutor{
    private var cd: ConsoleCommandSender = Bukkit.getConsoleSender()
    override fun onEnable() {
        cd.sendMessage("Enable plugin")
        saveConfig()
        val c = File(dataFolder, "config.yml")
        if (c.length() == 0L) {
            config.options().copyDefaults(true)
            saveConfig()
        }
        server.pluginManager.registerEvents(Kalistar(), this)
        server.pluginManager.registerEvents(farmer(), this)
        server.pluginManager.registerEvents(Trindamier(), this)
        getCommand("jm")?.setExecutor(Commnad())
    }
    override fun onDisable() {
        saveConfig()
        cd.sendMessage("Disable Plugin")
    }
}