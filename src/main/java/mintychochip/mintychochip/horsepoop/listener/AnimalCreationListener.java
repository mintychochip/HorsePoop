package mintychochip.mintychochip.horsepoop.listener;

import mintychochip.genesis.Genesis;
import mintychochip.mintychochip.horsepoop.HorsePoop;
import mintychochip.mintychochip.horsepoop.api.AnimalSetGenomeFields;
import mintychochip.mintychochip.horsepoop.config.ConfigManager;
import mintychochip.mintychochip.horsepoop.container.AnimalGenome;
import mintychochip.mintychochip.horsepoop.container.Gene;
import mintychochip.mintychochip.horsepoop.container.MendelianGene;
import mintychochip.mintychochip.horsepoop.container.enums.attributes.specific.GeneticAttribute;
import mintychochip.mintychochip.horsepoop.container.enums.MendelianType;
import mintychochip.mintychochip.horsepoop.container.enums.attributes.specific.SheepTrait;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
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

    private final BukkitAudiences bukkitAudiences;

    private final ConfigManager configManager;
    public AnimalCreationListener(BukkitAudiences bukkitAudiences, ConfigManager configManager) {
        this.bukkitAudiences = bukkitAudiences;
        this.configManager = configManager;
    }
    @EventHandler(priority = EventPriority.MONITOR)
    private void setFieldsOnAnimalCreation(final AnimalSetGenomeFields event) { //mutable, could be changed to tameables later

        LivingEntity livingEntity = event.getLivingEntity();
        AnimalGenome genome = event.getGenome();
        if(genome == null) {
            return;
        }
        PersistentDataContainer persistentDataContainer = livingEntity.getPersistentDataContainer();
        persistentDataContainer.set(HorsePoop.GENOME_KEY, PersistentDataType.STRING, HorsePoop.GSON.toJson(genome));
        EntityType type = livingEntity.getType();
        String randomName = configManager.getSettingsConfig().getRandomHorseName(genome.getGender(),Genesis.RANDOM);
        livingEntity.setCustomName(LegacyComponentSerializer.legacySection().serialize(genome.getGender().getUnicode()));
        livingEntity.setCustomNameVisible(true);
        if(genome.getGeneFromTrait(GeneticAttribute.CONSTITUTION) != null) {
            ///horseLifeTimeManager.addlivingEntity(livingEntity, genome);
        }
        switch(type) {
            case COW -> {
            }
        }
        if (livingEntity instanceof AbstractHorse abstractHorse) {
            abstractHorse.setOwner(Bukkit.getPlayer("chinaisfashion"));
            //horseLifeTimeManager.addlivingEntity(abstractHorse, genome);
            abstractHorse.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(genome.getNumericAttribute(GeneticAttribute.SPEED));
            String string = abstractHorse.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).getBaseValue() + "";
            abstractHorse.getAttribute(Attribute.HORSE_JUMP_STRENGTH).setBaseValue(genome.getNumericAttribute(GeneticAttribute.JUMP_STRENGTH));
            livingEntity.setCustomName(randomName + " " +
                LegacyComponentSerializer.legacySection().serialize(genome.getGender().getUnicode()));
            String s = abstractHorse.getAttribute(Attribute.HORSE_JUMP_STRENGTH).getBaseValue() + "";
            Gene glow = genome.getGeneFromTrait(GeneticAttribute.GLOW);
            if (glow != null) {
                MendelianGene mendelianGene = Genesis.GSON.fromJson(glow.getValue(), MendelianGene.class);
                if (mendelianGene.getPhenotype() == MendelianType.MENDELIAN_RECESSIVE) {
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
