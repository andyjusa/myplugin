package d.d

import d.d.job.farmer
import d.d.psy.Kalistar
import d.d.psy.Trindamier
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.command.ConsoleCommandSender
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockBreakEvent
import org.bukkit.event.entity.EntityDamageByEntityEvent
import org.bukkit.event.entity.EntityShootBowEvent
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.inventory.ItemStack
import org.bukkit.plugin.java.JavaPlugin
import java.io.File

class Main : JavaPlugin(),CommandExecutor, Listener {
    private var cd: ConsoleCommandSender = Bukkit.getConsoleSender()
    override fun onEnable() {
        cd.sendMessage("Enable plugin")
        saveConfig()
        val c = File(dataFolder, "config.yml")
        if (c.length() == 0L) {
            config.options().copyDefaults(true)
            saveConfig()
        }
        server.pluginManager.registerEvents(this, this)
        getCommand("jm")?.setExecutor(this)
    }
    override fun onDisable() {
        saveConfig()
        cd.sendMessage("Disable Plugin")
    }
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        return command().onCommand(sender,command,label,args,config,dataFolder)
    }
    @EventHandler
    fun entitydamagebyentityevent(e:EntityDamageByEntityEvent)
    {
        Trindamier().entitydamagebyentityevent(e,config)
    }
    @EventHandler
    fun blockbreakevent(e:BlockBreakEvent)
    {
        farmer().onBreak(e,config,server)
    }
    @EventHandler
    fun playerinteractevent(e:PlayerInteractEvent)
    {
        Protect().interact(e,config,server)
    }
    @EventHandler
    fun entityshootbowevent(e: EntityShootBowEvent)
    {
        Kalistar().kalistar(e,config)
    }
}