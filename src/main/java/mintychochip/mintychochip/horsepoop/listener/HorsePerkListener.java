package mintychochip.mintychochip.horsepoop.listener;

import mintychochip.genesis.Genesis;
import mintychochip.mintychochip.horsepoop.HorsePoop;
import mintychochip.mintychochip.horsepoop.container.AnimalGenome;
import mintychochip.mintychochip.horsepoop.container.Gene;
import mintychochip.mintychochip.horsepoop.container.attributes.GeneticAttribute;
import mintychochip.mintychochip.horsepoop.container.enums.MendelianType;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.AbstractHorse;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

public class HorsePerkListener implements Listener {

    private final int fallDamageThreshHold = 100;

    @EventHandler
    private void onHorseFallDamage(final EntityDamageEvent event) {
        Entity entity = event.getEntity();
        if (!(entity instanceof AbstractHorse abstractHorse)) {
            return;
        }
        if (event.getCause() != EntityDamageEvent.DamageCause.FALL) {
            return;
        }
        PersistentDataContainer persistentDataContainer = abstractHorse.getPersistentDataContainer();
        if (!persistentDataContainer.has(HorsePoop.GENOME_KEY, PersistentDataType.STRING)) {
            return;
        }

        AnimalGenome animalGenome = Genesis.GSON.fromJson(persistentDataContainer.get(HorsePoop.GENOME_KEY, PersistentDataType.STRING), AnimalGenome.class);

        double numericAttribute = animalGenome.getNumericAttribute(GeneticAttribute.JUMP_STRENGTH);
        if (numericAttribute < fallDamageThreshHold) { //change magic number
            return;
        }
        event.setCancelled(true); //simplified formula, can add scalin
    }
    @EventHandler
    private void onHorseReflectDamage(final EntityDamageEvent event) {

    }
    @EventHandler
    private void extinguishingFireHorse(final EntityDamageEvent event) {
        Entity entity = event.getEntity();
        if(!(entity instanceof AbstractHorse abstractHorse)) {
            return;
        }
        if(!(event.getCause() == EntityDamageEvent.DamageCause.FIRE)) {
            return;
        }
        PersistentDataContainer persistentDataContainer = abstractHorse.getPersistentDataContainer();
        if(!persistentDataContainer.has(HorsePoop.GENOME_KEY,PersistentDataType.STRING)) {
            return;
        }
        AnimalGenome animalGenome = Genesis.GSON.fromJson(persistentDataContainer.get(HorsePoop.GENOME_KEY, PersistentDataType.STRING), AnimalGenome.class);

        Gene fire = animalGenome.getGeneFromTrait(GeneticAttribute.FIRE_RESISTANT);
        if(fire != null) {
            abstractHorse.setFireTicks(0);
            event.setCancelled(true);
        }
    }

    @EventHandler
    private void lavaExtinguishingFireHorse(final EntityDamageEvent event) {
        Entity entity = event.getEntity();
        if(!(entity instanceof AbstractHorse abstractHorse)) {
            return;
        }
        if(!(event.getCause() == EntityDamageEvent.DamageCause.LAVA)) {
            return;
        }
        PersistentDataContainer persistentDataContainer = abstractHorse.getPersistentDataContainer();
        if(!persistentDataContainer.has(HorsePoop.GENOME_KEY,PersistentDataType.STRING)) {
            return;
        }
        AnimalGenome animalGenome = Genesis.GSON.fromJson(persistentDataContainer.get(HorsePoop.GENOME_KEY, PersistentDataType.STRING), AnimalGenome.class);

        Gene fire = animalGenome.getGeneFromTrait(GeneticAttribute.FIRE_RESISTANT);
        if(fire != null) {
            if(fire.getPhenotype() == MendelianType.MENDELIAN_RECESSIVE) {
                abstractHorse.setFireTicks(0);
                event.setCancelled(true);
            }
        }
    }


    @EventHandler
    private void horseIceWalking(final PlayerMoveEvent event) {
        Player player = event.getPlayer();
        if (!player.isInsideVehicle()) {
            return;
        }
        Entity entity = player.getVehicle();
        if(!(entity instanceof AbstractHorse abstractHorse)) {
            return;
        }
        PersistentDataContainer persistentDataContainer = abstractHorse.getPersistentDataContainer();
        if(!persistentDataContainer.has(HorsePoop.GENOME_KEY,PersistentDataType.STRING)) {
            return;
        }
        AnimalGenome animalGenome = Genesis.GSON.fromJson(persistentDataContainer.get(HorsePoop.GENOME_KEY, PersistentDataType.STRING), AnimalGenome.class);

        Gene iceWalking = animalGenome.getGeneFromTrait(GeneticAttribute.ICE_WALKER);
        if(iceWalking != null) {
            if(iceWalking.getPhenotype() == MendelianType.MENDELIAN_DOMINANT) {
                Location location = player.getLocation().subtract(0,1,0);
                if(location.getBlock().getType() == Material.WATER) {
                    location.getBlock().setType(Material.FROSTED_ICE);
                }
            }
        }
    }
    @EventHandler
    private void horseWalk(final PlayerMoveEvent event) {

    }
}
