package mintychochip.mintychochip.horsepoop.listener;

import mintychochip.genesis.Genesis;
import mintychochip.mintychochip.horsepoop.HorsePoop;
import mintychochip.mintychochip.horsepoop.api.AnimalSetGenomeFields;
import mintychochip.mintychochip.horsepoop.container.AnimalGenome;
import mintychochip.mintychochip.horsepoop.container.Gene;
import mintychochip.mintychochip.horsepoop.container.MendelianGene;
import mintychochip.mintychochip.horsepoop.container.enums.attributes.specific.GeneticAttribute;
import mintychochip.mintychochip.horsepoop.container.enums.MendelianType;
import mintychochip.mintychochip.horsepoop.container.enums.attributes.specific.SheepTrait;
import mintychochip.mintychochip.horsepoop.horse.HorseLifeTimeManager;
import org.bukkit.Bukkit;
import org.bukkit.DyeColor;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

public class AnimalCreationListener implements Listener {
    private final HorseLifeTimeManager horseLifeTimeManager;

    public AnimalCreationListener(HorseLifeTimeManager horseLifeTimeManager) {
        this.horseLifeTimeManager = horseLifeTimeManager;
    }

    @EventHandler(priority = EventPriority.MONITOR)
    private void setFieldsOnAnimalCreation(final AnimalSetGenomeFields event) { //mutable, could be changed to tameables later

        LivingEntity livingEntity = event.getLivingEntity();
        AnimalGenome genome = event.getGenome();
        PersistentDataContainer persistentDataContainer = livingEntity.getPersistentDataContainer();
        persistentDataContainer.set(HorsePoop.GENOME_KEY, PersistentDataType.STRING, HorsePoop.GSON.toJson(genome));
        EntityType type = livingEntity.getType();
        if(genome.getGeneFromTrait(GeneticAttribute.CONSTITUTION) != null) {
            horseLifeTimeManager.addlivingEntity(livingEntity, genome);
        }
        switch(type) {
            case COW -> {
            }
        }
        if (livingEntity instanceof AbstractHorse abstractHorse) {
            abstractHorse.setOwner(Bukkit.getPlayer("chinaisfashion"));
            horseLifeTimeManager.addlivingEntity(abstractHorse, genome);
            abstractHorse.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(genome.getNumericAttribute(GeneticAttribute.SPEED));
            String string = abstractHorse.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).getBaseValue() + "";
            abstractHorse.getAttribute(Attribute.HORSE_JUMP_STRENGTH).setBaseValue(genome.getNumericAttribute(GeneticAttribute.JUMP_STRENGTH));

            String s = abstractHorse.getAttribute(Attribute.HORSE_JUMP_STRENGTH).getBaseValue() + "";
            Gene glow = genome.getGeneFromTrait(GeneticAttribute.GLOW);
            if (glow != null) {
                MendelianGene mendelianGene = Genesis.GSON.fromJson(glow.getValue(), MendelianGene.class);
                if (glow.getPhenotype() == MendelianType.MENDELIAN_RECESSIVE) {
                    abstractHorse.setGlowing(true);
                }
            }
        }
        if (livingEntity instanceof Sheep sheep) {
            DyeColor dyeColor = SheepTrait.calculateDyeColor(genome, sheep);
            sheep.setColor(dyeColor);
        }

    }

}
