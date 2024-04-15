package mintychochip.mintychochip.horsepoop.listener.perks;

import java.util.function.BiConsumer;
import mintychochip.mintychochip.horsepoop.api.Fetcher;
import mintychochip.mintychochip.horsepoop.api.events.FawnyDamageEvent;
import mintychochip.mintychochip.horsepoop.api.markers.Phenotypic;
import mintychochip.mintychochip.horsepoop.config.configs.PerkConfig;
import mintychochip.mintychochip.horsepoop.config.configs.PerkConfig.Code;
import mintychochip.mintychochip.horsepoop.config.configs.PerkConfig.Level;
import mintychochip.mintychochip.horsepoop.container.AnimalGenome;
import mintychochip.mintychochip.horsepoop.container.ValueFetcher;
import mintychochip.mintychochip.horsepoop.container.enums.traits.PhenotypicTraitEnum;
import mintychochip.mintychochip.horsepoop.container.grabber.GenomeGrasper;
import mintychochip.mintychochip.horsepoop.util.Pair;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;

public class GenericPhenotypicListener implements Listener {

  private final GenomeGrasper grasper;

  private final PerkConfig perkConfig;

  public GenericPhenotypicListener(GenomeGrasper grasper, PerkConfig perkConfig) {
    this.grasper = grasper;
    this.perkConfig = perkConfig;
  }

  private final Fetcher<Phenotypic> phenotypicFetcher = new ValueFetcher<>();

  @EventHandler(priority = EventPriority.MONITOR)
  private void onAnimalFall(final FawnyDamageEvent event) {
      if (event.getCause() != DamageCause.FALL) {
        return;
      }
      AnimalGenome grab = grasper.grab(event.getLivingEntity());
      if (grab == null) {
        return;
      }
      double numeric = phenotypicFetcher.getNumeric(grab.getPhenotypics(),
          PhenotypicTraitEnum.JUMP_STRENGTH);
      if (numeric == 0) {
        return;
      }
      Pair<Double, Double> high = perkConfig.getNumericThreshold(Level.HIGH,
          Code.FALLING);
      Pair<Double, Double> medium = perkConfig.getNumericThreshold(Level.MEDIUM,
          Code.FALLING);
      Pair<Double, Double> low = perkConfig.getNumericThreshold(Level.LOW,
          Code.FALLING);
      double damage = event.getDamage();
      if (numeric > high.getFirst()) {
        event.cancelSuper(true);
      } else if (numeric > medium.getFirst()) {
        damage = this.reduceValue(damage,medium.getSecond());
      } else if (numeric > low.getFirst()) {
        damage = this.reduceValue(damage,low.getSecond());
      }
      event.setDamage(damage);
  }
  private double reduceValue(double damage, double reduction) {
    return damage * (1 - reduction);
  }
}
