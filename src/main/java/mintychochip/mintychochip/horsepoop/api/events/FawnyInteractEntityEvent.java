package mintychochip.mintychochip.horsepoop.api.events;

import mintychochip.mintychochip.horsepoop.api.EventCreator;
import mintychochip.mintychochip.horsepoop.container.AnimalGenome;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.Cancellable;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Nullable;

public class FawnyInteractEntityEvent extends FawnyEvent implements Cancellable {

  private boolean cancelled = false;

  private final Player player;
  @Nullable
  private ItemStack instrument;

  private final LivingEntity livingEntity;

  private FawnyInteractEntityEvent(
      @NotNull AnimalGenome animalGenome,
      @NotNull LivingEntity livingEntity, Player player) {
    super(animalGenome, livingEntity.getType());
    this.livingEntity = livingEntity;
    this.player = player;
  }

  public LivingEntity getLivingEntity() {
    return livingEntity;
  }

  private FawnyInteractEntityEvent(
      @NotNull AnimalGenome animalGenome,
      @NotNull LivingEntity livingEntity, Player player, @Nullable ItemStack instrument) {
    this(animalGenome, livingEntity, player);
    this.instrument = instrument;
  }

  public static FawnyInteractEntityEvent createInstance(AnimalGenome animalGenome,
      LivingEntity livingEntity, Player player, EventCreator eventCreator) {
    return new FawnyInteractEntityEvent(animalGenome, livingEntity, player);
  }

  public void setInstrument(@Nullable ItemStack instrument) {
    this.instrument = instrument;
  }

  public @Nullable ItemStack getInstrument() {
    return instrument;
  }

  public Player getPlayer() {
    return player;
  }

  @Override
  public boolean isCancelled() {
    return false;
  }

  @Override
  public void setCancelled(boolean b) {

  }
}
