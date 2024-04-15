package mintychochip.mintychochip.horsepoop.api.events;

import java.util.function.Consumer;
import mintychochip.mintychochip.horsepoop.container.AnimalGenome;
import org.bukkit.Bukkit;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.Cancellable;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;

public final class FawnyDamageEvent extends IntermittentEvent implements Cancellable {

  private final Consumer<Boolean> superConsumer;

  private final Consumer<Double> damageConsumer;

  private final DamageCause cause;

  private double damage;

  public FawnyDamageEvent(AnimalGenome animalGenome, LivingEntity livingEntity, DamageCause cause,
      Consumer<Boolean> superConsumer, Consumer<Double> damageConsumer, double damage) {
    super(animalGenome, livingEntity);
    this.superConsumer = superConsumer;
    this.damageConsumer = damageConsumer;
    this.cause = cause;
    this.damage = damage;
  }

  public double getDamage() {
    return damage;
  }

  public DamageCause getCause() {
    return cause;
  }

  public void setDamage(double damage) {
    this.damage = damage;
    damageConsumer.accept(damage);
  }

  public void cancelSuper(boolean b) {
    superConsumer.accept(b);
  }

  private boolean cancelled = false;

  @Override
  public boolean isCancelled() {
    return cancelled;
  }

  @Override
  public void setCancelled(boolean b) {
    this.cancelled = b;
  }
}
