package d.d

import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

class Commnad : CommandExecutor{
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        sender as Player
        var config = Main().config
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
}