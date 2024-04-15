package mintychochip.mintychochip.horsepoop.api.events;

import java.util.function.Consumer;
import mintychochip.mintychochip.horsepoop.container.AnimalGenome;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.jetbrains.annotations.NotNull;

public abstract class IntermittentEvent extends FawnyEvent {
  protected IntermittentEvent(
      @NotNull AnimalGenome animalGenome,
      @NotNull LivingEntity livingEntity) {
    super(animalGenome, livingEntity);
  } //event that attempts to override all native code, boiler plate code for all methods
}
