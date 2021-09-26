package serverplugin

import serverplugin.job.Farmer
import serverplugin.psy.Kalistar
import serverplugin.psy.Trindamier
import org.bukkit.Bukkit
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.command.ConsoleCommandSender
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockBreakEvent
import org.bukkit.event.entity.EntityDamageByEntityEvent
import org.bukkit.event.entity.EntityShootBowEvent
import org.bukkit.event.entity.PlayerDeathEvent
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryOpenEvent
import org.bukkit.event.player.*
import org.bukkit.plugin.java.JavaPlugin
import serverplugin.psy.Irelia
import serverplugin.psy.Leona
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
        return Ccommand().onCommand(sender, args, config,this)
    }
    @EventHandler(priority = EventPriority.LOW)
    fun entitydamagebyentityevent(e:EntityDamageByEntityEvent)
    {
        Trindamier().entityDamageByEntityEvent(e,config)
        Leona().attack(e,config,this)
    }
    @EventHandler(priority = EventPriority.LOW)
    fun blockbreakevent(e:BlockBreakEvent)
    {
        Farmer().onBreak(e,config,server)
    }
    @EventHandler(priority = EventPriority.LOW)
    fun playerinteractevent(e:PlayerInteractEvent)
    {
        Protect().interact(e,config,server)
    }
    @EventHandler(priority = EventPriority.LOW)
    fun playerinteractatentity(e:PlayerInteractAtEntityEvent)
    {
        Irelia().interactEntity(e,config,this)
    }
    @EventHandler(priority = EventPriority.LOW)
    fun entityshootbowevent(e: EntityShootBowEvent)
    {
        Kalistar().kalistar(e,config)
    }







    //stunned
    @EventHandler(priority = EventPriority.HIGHEST)
    fun blockbreak(e:BlockBreakEvent)
    {
        e.isCancelled=config.getBoolean("${e.player.name}.stun")
    }
    @EventHandler(priority = EventPriority.HIGHEST)
    fun dam(e:EntityDamageByEntityEvent)
    {
        if(e.damager is Player){
            e.isCancelled=config.getBoolean("${e.damager.name}.stun")
        }
    }
    @EventHandler(priority = EventPriority.HIGHEST)
    fun ice(e:InventoryClickEvent)
    {
        if(e.whoClicked is Player){
            e.isCancelled=config.getBoolean("${(e.whoClicked as Player).player?.name}.stun")
        }
    }
    @EventHandler(priority = EventPriority.HIGHEST)
    fun ioe(e:InventoryOpenEvent)
    {
        e.isCancelled=config.getBoolean("${e.player.name}.stun")
    }
    @EventHandler(priority = EventPriority.HIGHEST)
    fun death(e:PlayerDeathEvent)
    {
        config.set("${e.entity.name}.stun",false)
    }
    @EventHandler(priority = EventPriority.HIGHEST)
    fun pie(e:PlayerInteractEvent)
    {
        e.isCancelled=config.getBoolean("${e.player.name}.stun")
    }
    @EventHandler(priority = EventPriority.HIGHEST)
    fun pme(e:PlayerMoveEvent)
    {
        e.isCancelled=config.getBoolean("${e.player.name}.stun")
    }
    @EventHandler(priority = EventPriority.HIGHEST)
    fun ptse(e:PlayerToggleSprintEvent)
    {
        e.isCancelled=config.getBoolean("${e.player.name}.stun")
    }
    @EventHandler(priority = EventPriority.HIGHEST)
    fun pte(e:PlayerTeleportEvent)
    {
        e.isCancelled=config.getBoolean("${e.player.name}.stun")
    }
    @EventHandler(priority = EventPriority.HIGHEST)
    fun piae(e:PlayerInteractAtEntityEvent)
    {
        e.isCancelled=config.getBoolean("${e.player.name}.stun")
    }

}