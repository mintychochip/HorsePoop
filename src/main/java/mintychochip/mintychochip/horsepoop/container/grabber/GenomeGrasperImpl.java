package mintychochip.mintychochip.horsepoop.container.grabber;

import com.google.gson.Gson;
import mintychochip.mintychochip.horsepoop.container.AnimalGenome;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.LivingEntity;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

public class GenomeGrasperImpl implements GenomeGrasper {

  private final Gson gson;
  private final NamespacedKey genomeKey;

  public GenomeGrasperImpl(Gson gson, NamespacedKey genomeKey) {
    this.gson = gson;
    this.genomeKey = genomeKey;
  }

  @Override
  public AnimalGenome grab(LivingEntity livingEntity) {
    PersistentDataContainer persistentDataContainer = livingEntity.getPersistentDataContainer();
    if (!persistentDataContainer.has(genomeKey)) {
      return null;
    }
    String s = persistentDataContainer.get(genomeKey, PersistentDataType.STRING);
    return gson.fromJson(s, AnimalGenome.class);
  }

  @Override
  public void toss(LivingEntity livingEntity, AnimalGenome animalGenome) {
    PersistentDataContainer persistentDataContainer = livingEntity.getPersistentDataContainer();
    animalGenome.setName(null);
    String json = gson.toJson(animalGenome);
    Bukkit.broadcastMessage(json);
    persistentDataContainer.set(genomeKey, PersistentDataType.STRING, json);
  }
}

