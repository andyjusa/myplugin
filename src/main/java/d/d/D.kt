package d.d

import org.bukkit.*
import org.bukkit.block.Block
import org.bukkit.block.data.Ageable
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.command.ConsoleCommandSender
import org.bukkit.enchantments.Enchantment
import org.bukkit.entity.*
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockBreakEvent
import org.bukkit.event.entity.EntityDamageByEntityEvent
import org.bukkit.event.entity.EntityShootBowEvent
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.inventory.ItemStack
import org.bukkit.plugin.java.JavaPlugin
import org.bukkit.util.Vector
import java.io.File
import java.util.function.Predicate
import kotlin.math.ceil

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
        e.isCancelled=blocking(e.block,e.player)
        if (e.block.type == Material.WHEAT && !e.isCancelled) {
            val r = ceil(Math.random() * 10000)
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
    @EventHandler
    fun intrusion(e:PlayerInteractEvent)
    {
        if(e.clickedBlock!=null)
        {
            e.isCancelled=blocking(e.clickedBlock as Block,e.player)
        }

    }
    private fun blocking(b:Block, p:Player):Boolean
    {
        val pl =server.offlinePlayers
        val onpl =server.onlinePlayers
        var inBlock:Boolean=false
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
    fun kalistar(e:EntityShootBowEvent){
        var player =e.entity
        val arrow = e.projectile
        if(arrow is Arrow&&player is Player)
        {
            val start = player.eyeLocation
            val direction = start.direction
            val world = start.world
            val range=10.0
            val arrowSize=1.5
            e.isCancelled=true
            var pre:Predicate<Entity> = Predicate{ num: Entity -> num !=player && num is LivingEntity}
            val hitResult = world?.rayTrace(
                start,
                direction,
                range,
                FluidCollisionMode.NEVER,
                true,
                arrowSize,
                pre
            )
            var isfire = false
            e.bow?.enchantments?.get(Enchantment.ARROW_FIRE).let()
            {
                isfire=true
            }
            hitResult?.hitEntity.let { target ->
                target?.location?.let {
                    target?.world.spawnParticle(Particle.FIREWORKS_SPARK,
                        it,10,0.0,0.0,0.0,0.1,null,true)
                }
                target?.let { CD.sendMessage(it.name)
                    it as LivingEntity
                    it.damage(5.0,player)
                    it.fireTicks=if(isfire)100 else it.fireTicks
                    player.location.y+=0.5
                    player.velocity.y=0.0
                    var velocity = player.velocity.normalize().multiply(1.6)
                    velocity.y=0.3
                    player.velocity=velocity
                }
            }
        }
    }
    @EventHandler
    fun trindamier(e:EntityDamageByEntityEvent)
    {
        var entity =e.entity
        var player = e.damager
        if(player is Player)
        {
            var dam=(20-player.health)*2
            e.damage+=dam
        }
    }
}