package mintychochip.mintychochip.horsepoop.listener;

import mintychochip.genesis.Genesis;
import mintychochip.mintychochip.horsepoop.HorsePoop;
import mintychochip.mintychochip.horsepoop.api.AnimalCreationEvent;
import mintychochip.mintychochip.horsepoop.container.AnimalGenome;
import mintychochip.mintychochip.horsepoop.container.Gene;
import mintychochip.mintychochip.horsepoop.container.MendelianGene;
import mintychochip.mintychochip.horsepoop.container.attributes.GeneticAttribute;
import mintychochip.mintychochip.horsepoop.container.enums.MendelianType;
import mintychochip.mintychochip.horsepoop.horse.HorseLifeTimeManager;
import org.bukkit.Bukkit;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.AbstractHorse;
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
    private void setFieldsOnAnimalCreation(final AnimalCreationEvent event) { //mutable, could be changed to tameables later

        AbstractHorse abstractHorse = event.getAbstractHorse();
        AnimalGenome genome = event.getGenome();
        abstractHorse.setOwner(Bukkit.getPlayer("chinaisfashion"));
        horseLifeTimeManager.addTameable(abstractHorse,genome);
        PersistentDataContainer persistentDataContainer = abstractHorse.getPersistentDataContainer();
        persistentDataContainer.set(Genesis.getKey("horse"), PersistentDataType.STRING, HorsePoop.GSON.toJson(genome));
        abstractHorse.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(genome.getNumericAttribute(GeneticAttribute.SPEED));
        String string = abstractHorse.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).getBaseValue() + "";
        abstractHorse.getAttribute(Attribute.HORSE_JUMP_STRENGTH).setBaseValue(genome.getNumericAttribute(GeneticAttribute.JUMP_STRENGTH));

        String s = abstractHorse.getAttribute(Attribute.HORSE_JUMP_STRENGTH).getBaseValue() + "";
        Bukkit.broadcastMessage("JUMPSTRENGTH: " + s);
        Gene glow = genome.getGeneFromTrait(GeneticAttribute.GLOW);
        if (glow != null) {
            MendelianGene mendelianGene = Genesis.GSON.fromJson(glow.getValue(), MendelianGene.class);
            Bukkit.broadcastMessage(mendelianGene.getAlleleA() + " " + mendelianGene.getAlleleB());
            if (glow.getPhenotype() == MendelianType.MENDELIAN_RECESSIVE) {
                abstractHorse.setGlowing(true);
            }
        }
    }
}
