package mintychochip.mintychochip.horsepoop.listener.perks;

import mintychochip.mintychochip.horsepoop.api.Fetcher;
import mintychochip.mintychochip.horsepoop.api.events.FawnyDamageEvent;
import mintychochip.mintychochip.horsepoop.api.events.FawnyRidingMoveEvent;
import mintychochip.mintychochip.horsepoop.api.markers.Gene;
import mintychochip.mintychochip.horsepoop.container.AnimalGenome;
import mintychochip.mintychochip.horsepoop.container.BaseTrait;
import mintychochip.mintychochip.horsepoop.container.MendelianGene;
import mintychochip.mintychochip.horsepoop.container.ValueFetcher;
import mintychochip.mintychochip.horsepoop.container.enums.MendelianType;
import mintychochip.mintychochip.horsepoop.container.enums.traits.GeneticAttribute;
import mintychochip.mintychochip.horsepoop.container.grabber.GenomeGrasper;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.persistence.PersistentDataContainer;

public class GenericGeneListener implements Listener {

  private final GenomeGrasper grasper;

  public GenericGeneListener(GenomeGrasper grasper) {
    this.grasper = grasper;
  }

  private final Fetcher<Gene> geneFetcher = new ValueFetcher<>();

  @EventHandler(priority = EventPriority.MONITOR)
  private void onAnimalDamage(final EntityDamageEvent event) {
    Entity entity = event.getEntity();
    if (entity instanceof LivingEntity livingEntity) {
      AnimalGenome animalGenome = grasper.grab(livingEntity);
      if (animalGenome == null) {
        return;
      }
      Bukkit.getPluginManager().callEvent(
          new FawnyDamageEvent(animalGenome, livingEntity, event.getCause(), event::setCancelled,
              event::setDamage, event.getDamage()));
    }
  }

  @EventHandler(priority = EventPriority.MONITOR)
  private void onAnimalFireDamage(final FawnyDamageEvent event) {
    AnimalGenome animalGenome = event.getAnimalGenome();
    BaseTrait<Gene> fire = geneFetcher.getTraitFromList(animalGenome.getGenes(),
        GeneticAttribute.FIRE_RESISTANT);
    if (fire == null) {
      return;
    }
    MendelianGene fireMendelian = geneFetcher.getMendelian(fire);
    if (fireMendelian == null) {
      return;
    }
    DamageCause damageCause = event.getCause();
    LivingEntity livingEntity = event.getLivingEntity();
    if (damageCause == DamageCause.FIRE_TICK || damageCause == DamageCause.FIRE) {
      event.cancelSuper(true);
      livingEntity.setFireTicks(0);
    } else if (damageCause == DamageCause.LAVA
        && fireMendelian.getPhenotype() == MendelianType.MENDELIAN_RECESSIVE) {
      event.cancelSuper(true);
    }
  }

  @EventHandler
  private void onVehicleMove(final FawnyRidingMoveEvent event) {
    AnimalGenome animalGenome = event.getAnimalGenome();
    Particle particle = geneFetcher.getEnumValue(animalGenome.getGenes(),
        GeneticAttribute.PARTICLE, Particle.class);
    Particle.DustTransition dustTransition = null;
    if (particle == Particle.REDSTONE || particle == Particle.FALLING_DUST) {
      dustTransition = new Particle.DustTransition(Color.RED, Color.PURPLE, 1.0f);
    }
    Location location = event.getPlayer().getLocation();
    PersistentDataContainer pdc = event.getLivingEntity()
        .getPersistentDataContainer();
    location.getWorld()
        .spawnParticle(Particle.CHERRY_LEAVES, location.add(0, 0.5, 0), 5, 0.3, 0.3, 0.3, 0,
            null, false);
  }

  @EventHandler(priority = EventPriority.MONITOR)
  private void horseIceWalking(final FawnyRidingMoveEvent event) {
    if (event.isCancelled()) {
      return;
    }
    AnimalGenome animalGenome = event.getAnimalGenome();
    double radius = 2.5;
    BaseTrait<Gene> iceWalking = geneFetcher.getTraitFromList(animalGenome.getGenes(),
        GeneticAttribute.ICE_WALKER);
    if (iceWalking != null) {
      if (geneFetcher.getMendelian(iceWalking).getPhenotype()
          == MendelianType.MENDELIAN_DOMINANT) {
        Location location = event.getPlayer().getLocation().subtract(0, 1, 0);
        for (double i = radius * -1; i < radius; i++) {
          for (double j = -1 * radius; j < radius; j++) {
            Location newLocation = new Location(location.getWorld(), location.getX() + i,
                location.getY(),
                location.getZ() + j);
            if (location.distance(newLocation) < radius) {
              if (newLocation.getBlock().getType() == Material.WATER) {
                newLocation.getBlock().setType(Material.FROSTED_ICE);
              }
            }
          }
        }
      }
    }
  }
}
