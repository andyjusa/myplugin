package d.d

import d.d.job.farmer
import d.d.psy.Kalistar
import d.d.psy.Trindamier
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.block.Block
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

class Main() : JavaPlugin(),CommandExecutor, Listener {
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
        sender as Player
        if(args[0]=="0"){
            config.set("${sender.name}.x",sender.location.blockX)
            config.set("${sender.name}.z",sender.location.blockZ)
        }else if(args[0]=="1"){
            config.set("${sender.name}.x1",sender.location.blockX)
            config.set("${sender.name}.z1",sender.location.blockZ)
        }
        else if(args[0]=="위치구입")
        {
            val p: Player? = Bukkit.getPlayer(args[1])
            if (p != null) {
                if(sender.inventory.itemInMainHand.type== Material.DIAMOND&&sender.inventory.itemInMainHand.amount>=5&& p.name==args[1]) {
                    sender.sendMessage("${args[1]}:x ${p.location.x}    y ${p.location.y}     z ${p.location.z}")
                    val item: ItemStack =p.inventory.itemInMainHand
                    item.amount-=5
                    p.inventory.setItemInMainHand(item)
                }
            }
        }else if(args[0]=="sethome")
        {
            val home: String =args[1]
            config.set("${sender.name}.${home}.x",sender.location.x)
            config.set("${sender.name}.${home}.y",sender.location.y)
            config.set("${sender.name}.${home}.z",sender.location.z)
        }else if(args[0]=="home")
        {
            val home: String =args[1]
            sender.sendMessage("a")
            val loc: Location =sender.location
            loc.x= config.getDouble("${sender.name}.${home}.x")
            loc.y= config.getDouble("${sender.name}.${home}.y")
            loc.z= config.getDouble("${sender.name}.${home}.z")
            sender.teleport(loc)
        }else if(args[0]=="save")
        {
            Main().saveConfig()
        }else if(args[0]=="psy")
        {
            config.set("${sender.name}.psy",args[1])
        }
        else {
            sender.sendMessage("error")
        }
        return true
    }
    @EventHandler
    fun entitydamagebyentityevent(e:EntityDamageByEntityEvent)
    {
        Trindamier().entitydamagebyentityevent(e,config)
    }
    @EventHandler
    fun blockbreakevent(e:BlockBreakEvent)
    {
        farmer().onBreak(e)
    }
    @EventHandler
    fun playerinteractevent(e:PlayerInteractEvent)
    {
        Protect().interact(e)
    }
    fun blocking(b: Block, p: Player):Boolean
    {
        var config=Main().config
        val server=Main().server
        val pl =server.offlinePlayers
        val onpl =server.onlinePlayers
        var inBlock =false
        for (a in pl){
            val x0=config.getInt("${a.name}.x")
            val z0=config.getInt("${a.name}.z")
            val x1=config.getInt("${a.name}.x1")
            val z1=config.getInt("${a.name}.z1")
            val X = if(x0>x1) x0 else x1
            val x = if(x1>x0) x0 else x1
            val Z = if(z0>z1) z0 else z1
            val z = if(z1>z0) z0 else z1
            if((b.location.blockX in x..X)&&(b.location.blockY in z..Z)){
                inBlock = a.name != p.name

            }
        }
        for (a in onpl){
            val x0=config.getInt("${a.name}.x")
            val z0=config.getInt("${a.name}.z")
            val x1=config.getInt("${a.name}.x1")
            val z1=config.getInt("${a.name}.z1")
            val X = if(x0>x1) x0 else x1
            val x = if(x1>x0) x0 else x1
            val Z = if(z0>z1) z0 else z1
            val z = if(z1>z0) z0 else z1
            if((b.location.blockX in x..X)&&(b.location.blockY in z..Z)){
                inBlock = a.name != p.name
            }
        }
        return inBlock
    }
    @EventHandler
    fun entityshootbowevent(e: EntityShootBowEvent)
    {
        Kalistar().kalistar(e,config)
    }
}