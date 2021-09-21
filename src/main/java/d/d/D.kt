package d.d

import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.block.data.Ageable
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.command.ConsoleCommandSender
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockBreakEvent
import org.bukkit.inventory.ItemStack
import org.bukkit.plugin.java.JavaPlugin
import java.io.File

class D : JavaPlugin(),Listener,CommandExecutor{
    var CD: ConsoleCommandSender = Bukkit.getConsoleSender()
    override fun onEnable() {
        CD.sendMessage("Enable plugin")
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
        CD.sendMessage("Disable Plugin")
    }
    @EventHandler
    fun onBreak(e: BlockBreakEvent) {
        val pl =server.offlinePlayers
        val onpl =server.onlinePlayers
        for (a in pl){
            val x0=config.getInt("${a.name}.x")
            val z0=config.getInt("${a.name}.z")
            val x1=config.getInt("${a.name}.x1")
            val z1=config.getInt("${a.name}.z1")
            val X = if(x0>x1) x0 else x1
            val x = if(x1>x0) x0 else x1
            val Z = if(z0>z1) z0 else z1
            val z = if(z1>z0) z0 else z1
            if((e.block.location.blockX in x..X)&&(e.block.location.blockY in z..Z)){
                e.isCancelled = a.name != e.player.name
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
            if((e.block.location.blockX in x..X)&&(e.block.location.blockY in z..Z)){
                e.isCancelled = a.name != e.player.name
            }
        }
        if (e.block.type == Material.WHEAT && !e.isCancelled) {
            val r = Math.ceil(Math.random() * 10000)
            val ag= e.block.blockData as Ageable
            if(ag.age == 7)
            {
                if (r > 9998) {
                    e.player.inventory.addItem(ItemStack(Material.NETHERITE_SCRAP, 1))
                    e.player.sendMessage(ChatColor.DARK_PURPLE.toString() + "네더라이트 X1")
                    e.isDropItems = false
                } else if (r > 4000) {
                    e.player.sendMessage(ChatColor.YELLOW.toString() + "밀 X1")
                } else if (r > 3500) {
                    e.isDropItems = false
                    e.player.inventory.addItem(ItemStack(Material.COAL, 1))
                    e.player.sendMessage(ChatColor.BLACK.toString() + "석탄 X1")
                } else if (r > 2500) {
                    e.isDropItems = false
                    e.player.inventory.addItem(ItemStack(Material.IRON_ORE, 1))
                    e.player.sendMessage(ChatColor.WHITE.toString() + "철 X1")
                } else if (r > 1500) {
                    e.isDropItems = false
                    e.player.inventory.addItem(ItemStack(Material.GOLD_ORE, 1))
                    e.player.sendMessage(ChatColor.GOLD.toString() + "금 X1")
                } else if (r > 1000) {
                    e.isDropItems = false
                    e.player.inventory.addItem(ItemStack(Material.LAPIS_LAZULI, 4))
                    e.player.sendMessage(ChatColor.BLUE.toString() + "청금석 X4")
                }else if (r > 200) {
                    e.isDropItems = false
                    e.player.inventory.addItem(ItemStack(Material.REDSTONE, 5))
                    e.player.sendMessage(ChatColor.BLUE.toString() + "레드스톤 X5")
                }
                else if (r > 50) {
                    e.isDropItems = false
                    e.player.inventory.addItem(ItemStack(Material.DIAMOND, 1))
                    e.player.sendMessage(ChatColor.BLUE.toString() + "다이아 X1")
                } else if (r > 0) {
                    e.isDropItems = false
                    val loc = e.block.location
                    e.player.sendMessage("ㅋ")
                    loc.block.type = Material.WATER
                }
            }

        }
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
            val p: Player? =Bukkit.getPlayer(args[1])
            if (p != null) {
                if(sender.inventory.itemInMainHand.type== Material.DIAMOND&&sender.inventory.itemInMainHand.amount>=5&& p.name==args[1]) {
                    sender.sendMessage("${args[1]}:x ${p.location.x}    y ${p.location.y}     z ${p.location.z}")
                    val item: ItemStack=p.inventory.itemInMainHand
                    item.amount-=5
                    p.inventory.setItemInMainHand(item)
                }
            }
        }else if(args[0]=="sethome")
        {
            var home: String? =args[1]
            config.set("${sender.name}.${home}.x",sender.location.x)
            config.set("${sender.name}.${home}.y",sender.location.y)
            config.set("${sender.name}.${home}.z",sender.location.z)
        }else if(args[0]=="home")
        {
            var home: String? =args[1]
            sender.sendMessage("a")
            var loc:Location=sender.location
            loc.x= config.getDouble("${sender.name}.${home}.x")
            loc.y= config.getDouble("${sender.name}.${home}.y")
            loc.z= config.getDouble("${sender.name}.${home}.z")
            sender.teleport(loc)
        }else if(args[0]=="save")
        {
            saveConfig()
        }
        return true
    }
}