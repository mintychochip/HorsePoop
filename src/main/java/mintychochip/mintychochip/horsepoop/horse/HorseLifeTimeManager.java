package mintychochip.mintychochip.horsepoop.horse;

import mintychochip.mintychochip.horsepoop.container.AnimalGenome;
import org.bukkit.entity.Tameable;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.Map;

public class HorseLifeTimeManager {

    private final Map<Tameable, AnimalGenome> tameableAnimalGeneticDataMap = new HashMap<>();

    public HorseLifeTimeManager(JavaPlugin plugin) {
        new BukkitRunnable() {
            public void run() {
                for (Tameable tameable : tameableAnimalGeneticDataMap.keySet()) {
                    AnimalGenome animalGenome = tameableAnimalGeneticDataMap.get(tameable);
                    tameable.setCustomName(animalGenome.getTimeRemaining() / 1000 + " " + animalGenome.getGender());
                    tameable.setCustomNameVisible(true);
                    if (animalGenome.getTimeRemaining() < 0) {
                        tameable.setHealth(0);
                    }
                }

            }
        }.runTaskTimer(plugin, 3L, 2L); //20L is 1second, so longevity times
    }
    public void addTameable(Tameable tameable, AnimalGenome animalGenome) {
        if(tameable == null) {
            return;
        }
        tameableAnimalGeneticDataMap.put(tameable, animalGenome);
        tameable.setCustomName(animalGenome.getTimeRemaining() / 1000 + " " + animalGenome.getGender());
        tameable.setCustomNameVisible(true);
    }

    public Map<Tameable, AnimalGenome> getTameableAnimalGeneticDataMap() {
        return tameableAnimalGeneticDataMap;
    }

}
