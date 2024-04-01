package mintychochip.mintychochip.horsepoop.listener;

import com.google.gson.Gson;
import mintychochip.mintychochip.horsepoop.api.events.FawnyRidingMoveEvent;
import mintychochip.mintychochip.horsepoop.container.AnimalGenome;
import mintychochip.mintychochip.horsepoop.container.Gene;
import mintychochip.mintychochip.horsepoop.container.TraitFetcher;
import mintychochip.mintychochip.horsepoop.container.enums.MendelianType;
import mintychochip.mintychochip.horsepoop.container.enums.attributes.specific.GeneticAttribute;
import mintychochip.mintychochip.horsepoop.util.DataExtractor;
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

public class HorsePerkListener implements Listener {

    private final int fallDamageThreshHold = 100;

    private final TraitFetcher traitFetcher;

    public HorsePerkListener(TraitFetcher traitFetcher) {
        this.traitFetcher = traitFetcher;
    }

    @EventHandler (priority = EventPriority.MONITOR)
    private void onHorseFallDamage(final EntityDamageEvent event) {
        if(event.isCancelled()) {
            return;
        }
        if (event.getCause() != EntityDamageEvent.DamageCause.FALL) {
            return;
        }
        AnimalGenome animalGenome = DataExtractor.extractGenomicData(event.getEntity());
        if(animalGenome == null) {
            return;
        }
        double numericAttribute = traitFetcher.getNumericAttribute(animalGenome,GeneticAttribute.JUMP_STRENGTH);
        if (numericAttribute < fallDamageThreshHold) { //change magic number
            return;
        }
        event.setCancelled(true); //simplified formula, can add scalin
    }
    @EventHandler
    private void extinguishingFireHorse(final EntityDamageEvent event) {
        if(event.isCancelled()) {
            return;
        }
        Entity entity = event.getEntity();
        if(!(entity instanceof LivingEntity livingEntity)) {
            return;
        }
        if(!(event.getCause() == DamageCause.FIRE || event.getCause() == DamageCause.FIRE_TICK)) {
            return;
        }
        AnimalGenome animalGenome = DataExtractor.extractGenomicData(livingEntity);
        if(animalGenome == null) {
            return;
        }
        Gene fire = traitFetcher.getGeneFromGeneList(animalGenome,GeneticAttribute.FIRE_RESISTANT);
        if(fire != null) {
            livingEntity.setFireTicks(0);
            event.setCancelled(true);
        }
    }
    @EventHandler
    private void lavaExtinguishingFireHorse(final EntityDamageEvent event) {
        if(event.isCancelled()) {
            return;
        }
        Entity entity = event.getEntity();
        if(!(event.getCause() == EntityDamageEvent.DamageCause.LAVA)) {
            return;
        }
        if(!(entity instanceof LivingEntity livingEntity)) {
            return;
        }
        AnimalGenome animalGenome = DataExtractor.extractGenomicData(entity);
        if(animalGenome == null) {
            return;
        }
        Gene fire = traitFetcher.getGeneFromGeneList(animalGenome,GeneticAttribute.FIRE_RESISTANT);
        if(fire != null) {
            if(traitFetcher.getMendelian(fire).getPhenotype() == MendelianType.MENDELIAN_RECESSIVE) {
                livingEntity.setFireTicks(0);
                event.setCancelled(true);
            }
        }
    }


    @EventHandler (priority = EventPriority.MONITOR)
    private void horseIceWalking(final FawnyRidingMoveEvent event) {
        if(event.isCancelled()) {
            return;
        }
        AnimalGenome animalGenome = event.getAnimalGenome();
        double radius = 2.5;
        Gene iceWalking = traitFetcher.getGeneFromGeneList(animalGenome,GeneticAttribute.ICE_WALKER);
        if(iceWalking != null) {
            if(traitFetcher.getMendelian(iceWalking).getPhenotype() == MendelianType.MENDELIAN_DOMINANT) {
                Location location = event.getPlayer().getLocation().subtract(0,1,0);
                for(double i = radius * -1; i < radius; i++) {
                    for(double j = -1 * radius; j < radius; j++) {
                        Location newLocation = new Location(location.getWorld(), location.getX() + i,
                            location.getY(),
                            location.getZ() + j);
                        if(location.distance(newLocation) < radius) {
                            if(newLocation.getBlock().getType() == Material.WATER) {
                                newLocation.getBlock().setType(Material.FROSTED_ICE);
                            }
                        }
                    }
                }
            }
        }
    }
    @EventHandler
    private void onVehicleMove(final FawnyRidingMoveEvent event) {
        AnimalGenome animalGenome = event.getAnimalGenome();
        Gene geneFromTrait = traitFetcher.getGeneFromGeneList(animalGenome,GeneticAttribute.PARTICLE);
        if(geneFromTrait == null) {
            return;
        }
        Particle particle = new Gson().fromJson(geneFromTrait.getValue(), Particle.class);
        if (particle == null) {
            return;
        }
        Particle.DustTransition dustTransition = null;
        if (particle == Particle.REDSTONE || particle == Particle.FALLING_DUST) {
            dustTransition = new Particle.DustTransition(Color.RED, Color.PURPLE, 1.0f);
        }
        Location location = event.getPlayer().getLocation();

        location.getWorld()
            .spawnParticle(Particle.CHERRY_LEAVES, location.add(0, 0.5, 0), 5, 0.3, 0.3, 0.3, 0,
                dustTransition, false);
    }
}
