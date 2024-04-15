package mintychochip.mintychochip.horsepoop.api.events;

import mintychochip.genesis.events.AbstractEvent;
import mintychochip.mintychochip.horsepoop.container.AnimalGenome;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.jetbrains.annotations.NotNull;

public abstract class FawnyEvent extends AbstractEvent {

  @NotNull
  protected final AnimalGenome animalGenome;
  @NotNull
  protected final LivingEntity livingEntity;

  protected FawnyEvent(@NotNull AnimalGenome animalGenome, @NotNull LivingEntity livingEntity) {
    this.animalGenome = animalGenome;
    this.livingEntity = livingEntity;
  }

  public @NotNull LivingEntity getLivingEntity() {
    return livingEntity;
  }

  public @NotNull AnimalGenome getAnimalGenome() {
    return animalGenome;
  }
}
