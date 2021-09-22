package d.d.job

import d.d.Protect
import org.bukkit.ChatColor
import org.bukkit.Material
import org.bukkit.Server
import org.bukkit.block.data.Ageable
import org.bukkit.configuration.file.FileConfiguration
import org.bukkit.event.block.BlockBreakEvent
import org.bukkit.inventory.ItemStack
import kotlin.math.ceil

class farmer{
    fun onBreak(e: BlockBreakEvent,config: FileConfiguration,server: Server) {
        e.isCancelled=Protect().blocking(e.block,e.player,config,server)
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
}