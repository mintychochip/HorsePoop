package mintychochip.mintychochip.horsepoop.listener;

import com.google.gson.Gson;
import mintychochip.mintychochip.horsepoop.HorsePoop;
import mintychochip.mintychochip.horsepoop.container.AnimalGenome;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.AbstractHorse;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;

public class ParticleListener implements Listener {

    private final Gson gson;
    private ParticleListener(Gson gson) {
        this.gson = gson;
    }
    public static ParticleListener createParticleListener(JavaPlugin plugin, Gson gson) {
        return new ParticleListener(gson);
    }

    @EventHandler
    private void onVehicleMove(final PlayerMoveEvent event) {
        if(!event.getPlayer().isInsideVehicle()) {
            return;
        }
        Player player = event.getPlayer();
        Entity vehicle = event.getPlayer().getVehicle();
        if(vehicle instanceof AbstractHorse abstractHorse) {
            PersistentDataContainer persistentDataContainer = abstractHorse.getPersistentDataContainer();
            if (!persistentDataContainer.has(HorsePoop.GENOME_KEY, PersistentDataType.STRING)) {
                return;
            }
            String s = persistentDataContainer.get(HorsePoop.GENOME_KEY, PersistentDataType.STRING);
            AnimalGenome animalGenome = gson.fromJson(s, AnimalGenome.class);
            Particle particle = animalGenome.getParticle();
            if(particle == null) {
                return;
            }
            Particle.DustTransition dustTransition = null;
            if(particle == Particle.REDSTONE || particle == Particle.FALLING_DUST) {
                dustTransition = new Particle.DustTransition(Color.RED,Color.PURPLE, 1.0f);
            }
            Location location = vehicle.getLocation();

            location.getWorld().spawnParticle(Particle.CHERRY_LEAVES,location.add(0,0.5,0),5,0.3,0.3,0.3,0,dustTransition,false);
        }
    }
}
