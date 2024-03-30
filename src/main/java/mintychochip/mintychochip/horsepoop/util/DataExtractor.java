package mintychochip.mintychochip.horsepoop.util;

import com.google.gson.Gson;
import mintychochip.mintychochip.horsepoop.HorsePoop;
import mintychochip.mintychochip.horsepoop.container.AnimalGenome;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

public class DataExtractor {

    public static AnimalGenome extractGenomicData(Entity entity) {
        return entity instanceof LivingEntity livingEntity ? extractGenomicData(livingEntity) : null;
    }
    public static AnimalGenome extractGenomicData(LivingEntity livingEntity) {
        PersistentDataContainer persistentDataContainer = livingEntity.getPersistentDataContainer();
        if (!persistentDataContainer.has(HorsePoop.GENOME_KEY)) {
            return null;
        }
        String s = persistentDataContainer.get(HorsePoop.GENOME_KEY, PersistentDataType.STRING);
        return new Gson().fromJson(s, AnimalGenome.class);
    }
}
