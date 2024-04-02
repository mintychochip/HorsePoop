package mintychochip.mintychochip.horsepoop.listener;

import org.bukkit.event.Listener;

public class HorsePerkListener implements Listener {

//  private final int fallDamageThreshHold = 100;
//
//  private final TraitFetcher traitFetcher;
//
//  private final GenomeGrabber genomeGrabber;
//
//  public HorsePerkListener(TraitFetcher traitFetcher, GenomeGrabber genomeGrabber) {
//    this.traitFetcher = traitFetcher;
//    this.genomeGrabber = genomeGrabber;
//  }
//
//  @EventHandler(priority = EventPriority.MONITOR)
//  private void onHorseFallDamage(final EntityDamageEvent event) {
//    if (event.isCancelled()) {
//      return;
//    }
//    Entity entity = event.getEntity();
//    if (entity instanceof LivingEntity livingEntity) {
//      if (event.getCause() != EntityDamageEvent.DamageCause.FALL) {
//        return;
//      }
//      AnimalGenome animalGenome = genomeGrabber.grab(livingEntity);
//      if (animalGenome == null) {
//        return;
//      }
//      if (!traitFetcher.geneTraitInList(animalGenome.getGenes(), GeneticAttribute.JUMP_STRENGTH)) {
//        return;
//      }
//      double numericAttribute = traitFetcher.getNumericValue(animalGenome,
//          GeneticAttribute.JUMP_STRENGTH);
//      if (numericAttribute < fallDamageThreshHold) { //change magic number
//        return;
//      }
//      event.setCancelled(true); //simplified formula, can add scalin
//    }
//  }
//
//  @EventHandler
//  private void extinguishingFireHorse(final EntityDamageEvent event) {
//    if (event.isCancelled()) {
//      return;
//    }
//    Entity entity = event.getEntity();
//    if (entity instanceof LivingEntity livingEntity) {
//      if (!(event.getCause() == DamageCause.FIRE || event.getCause() == DamageCause.FIRE_TICK)) {
//        return;
//      }
//      AnimalGenome animalGenome = genomeGrabber.grab(livingEntity);
//      if (animalGenome == null) {
//        return;
//      }
//      if (!traitFetcher.geneTraitInList(animalGenome.getGenes(), GeneticAttribute.FIRE_RESISTANT)) {
//        return;
//      }
//      Gene fire = traitFetcher.getGeneFromList(animalGenome, GeneticAttribute.FIRE_RESISTANT);
//      if (fire != null) {
//        livingEntity.setFireTicks(0);
//        event.setCancelled(true);
//      }
//    }
//  }
//
//  @EventHandler
//  private void lavaExtinguishingFireHorse(final EntityDamageEvent event) {
//    if (event.isCancelled()) {
//      return;
//    }
//    Entity entity = event.getEntity();
//    if (entity instanceof LivingEntity livingEntity) {
//      if (!(event.getCause() == EntityDamageEvent.DamageCause.LAVA)) {
//        return;
//      }
//      AnimalGenome animalGenome = genomeGrabber.grab(livingEntity);
//      if (animalGenome == null) {
//        return;
//      }
//      if (!traitFetcher.geneTraitInList(animalGenome.getGenes(), GeneticAttribute.FIRE_RESISTANT)) {
//        return;
//      }
//      Gene fire = traitFetcher.getGeneFromList(animalGenome, GeneticAttribute.FIRE_RESISTANT);
//      if (fire != null) {
//        if (traitFetcher.getMendelian(fire).getPhenotype() == MendelianType.MENDELIAN_RECESSIVE) {
//          livingEntity.setFireTicks(0);
//          event.setCancelled(true);
//        }
//      }
//    }
//  }
//
//  @EventHandler(priority = EventPriority.MONITOR)
//  private void horseIceWalking(final FawnyRidingMoveEvent event) {
//    if (event.isCancelled()) {
//      return;
//    }
//    AnimalGenome animalGenome = event.getAnimalGenome();
//    double radius = 2.5;
//    Gene iceWalking = traitFetcher.getGeneFromList(animalGenome, GeneticAttribute.ICE_WALKER);
//    if (iceWalking != null) {
//      if (traitFetcher.getMendelian(iceWalking).getPhenotype()
//          == MendelianType.MENDELIAN_DOMINANT) {
//        Location location = event.getPlayer().getLocation().subtract(0, 1, 0);
//        for (double i = radius * -1; i < radius; i++) {
//          for (double j = -1 * radius; j < radius; j++) {
//            Location newLocation = new Location(location.getWorld(), location.getX() + i,
//                location.getY(),
//                location.getZ() + j);
//            if (location.distance(newLocation) < radius) {
//              if (newLocation.getBlock().getType() == Material.WATER) {
//                newLocation.getBlock().setType(Material.FROSTED_ICE);
//              }
//            }
//          }
//        }
//      }
//    }
//  }
//
//  @EventHandler
//  private void onVehicleMove(final FawnyRidingMoveEvent event) {
//    AnimalGenome animalGenome = event.getAnimalGenome();
//    Gene geneFromTrait = traitFetcher.getGeneFromList(animalGenome, GeneticAttribute.PARTICLE);
//    if (geneFromTrait == null) {
//      return;
//    }
//    Particle particle = new Gson().fromJson(geneFromTrait.getValue(), Particle.class);
//    if (particle == null) {
//      return;
//    }
//    Particle.DustTransition dustTransition = null;
//    if (particle == Particle.REDSTONE || particle == Particle.FALLING_DUST) {
//      dustTransition = new Particle.DustTransition(Color.RED, Color.PURPLE, 1.0f);
//    }
//    Location location = event.getPlayer().getLocation();
//
//    location.getWorld()
//        .spawnParticle(Particle.CHERRY_LEAVES, location.add(0, 0.5, 0), 5, 0.3, 0.3, 0.3, 0,
//            dustTransition, false);
//  }
}
