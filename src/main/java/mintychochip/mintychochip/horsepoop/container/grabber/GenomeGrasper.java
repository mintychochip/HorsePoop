package mintychochip.mintychochip.horsepoop.container.grabber;

import mintychochip.mintychochip.horsepoop.container.AnimalGenome;
import org.bukkit.entity.LivingEntity;

public interface GenomeGrasper {

  void toss(LivingEntity livingEntity, AnimalGenome animalGenome);

  AnimalGenome grab(LivingEntity livingEntity);

}

