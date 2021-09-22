package d.d.psy

import d.d.Main
import org.bukkit.FluidCollisionMode
import org.bukkit.Particle
import org.bukkit.enchantments.Enchantment
import org.bukkit.entity.Arrow
import org.bukkit.entity.Entity
import org.bukkit.entity.LivingEntity
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityShootBowEvent
import java.util.function.Predicate

class Kalistar : Listener {
    @EventHandler
    fun kalistar(e: EntityShootBowEvent){
        var config= Main().config
        var player =e.entity
        val arrow = e.projectile
        if(arrow is Arrow &&player is Player &&config.getString("${player.name}.psy")=="kalistar")
        {
            val start = player.eyeLocation
            val direction = start.direction
            val world = start.world
            val range=10.0
            val arrowSize=1.5
            e.isCancelled=true
            var pre: Predicate<Entity> = Predicate{ num: Entity -> num !=player && num is LivingEntity }
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
                if (it != null) {
                    isfire= it>0
                }
            }
            hitResult?.hitEntity.let { target ->
                target?.location?.let {
                    target?.world.spawnParticle(
                        Particle.FIREWORKS_SPARK,
                        it,10,0.0,0.0,0.0,0.1,null,true)
                }
                target?.let {
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
}