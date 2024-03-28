package mintychochip.mintychochip.horsepoop.horse;

import mintychochip.mintychochip.horsepoop.container.AnimalGenome;
import org.bukkit.entity.LivingEntity;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.Map;

public class HorseLifeTimeManager {

//    private final Map<LivingEntity, AnimalGenome> tameableAnimalGeneticDataMap = new HashMap<>();
//
//    public HorseLifeTimeManager(JavaPlugin plugin) {
//        new BukkitRunnable() {
//            public void run() {
//                for (LivingEntity livingEntity : tameableAnimalGeneticDataMap.keySet()) {
//                    AnimalGenome animalGenome = tameableAnimalGeneticDataMap.get(livingEntity);
//                    livingEntity.setCustomName(animalGenome.getTimeRemaining() / 1000 + " " + animalGenome.getGender());
//                    livingEntity.setCustomNameVisible(true);
//                    if (animalGenome.getTimeRemaining() < 0) {
//                        livingEntity.setHealth(0);
//                    }
//                }
//
//            }
//        }.runTaskTimer(plugin, 3L, 2L); //20L is 1second, so longevity times
//    }
//    public void addlivingEntity(LivingEntity livingEntity, AnimalGenome animalGenome) {
//        if(livingEntity == null) {
//            return;
//        }
//        tameableAnimalGeneticDataMap.put(livingEntity, animalGenome);
//        livingEntity.setCustomName(animalGenome.getTimeRemaining() / 1000 + " " + animalGenome.getGender());
//        livingEntity.setCustomNameVisible(true);
//    }
//
//    public Map<LivingEntity, AnimalGenome> getTameableAnimalGeneticDataMap() {
//        return tameableAnimalGeneticDataMap;
//    }
}
