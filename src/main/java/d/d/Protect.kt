package d.d

import org.bukkit.block.Block
import org.bukkit.event.EventHandler
import org.bukkit.event.player.PlayerInteractEvent

class Protect {
    @EventHandler
    fun intrusion(e: PlayerInteractEvent)
    {
        if(e.clickedBlock!=null)
        {
            e.isCancelled=region().blocking(e.clickedBlock as Block,e.player)
        }

    }
}