package mintychochip.mintychochip.horsepoop.listener;

import com.google.gson.Gson;
import mintychochip.mintychochip.horsepoop.HorsePoop;
import mintychochip.mintychochip.horsepoop.api.AnimalSetGenomeFields;
import mintychochip.mintychochip.horsepoop.container.AnimalGenome;
import mintychochip.mintychochip.horsepoop.factories.GeneFactory;
import mintychochip.mintychochip.horsepoop.factories.GenomeFactory;
import org.bukkit.Bukkit;
import org.bukkit.entity.AbstractHorse;
import org.bukkit.entity.Breedable;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityBreedEvent;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.List;

public class HorseCreationListener implements Listener { //monitor listeners only

    private List<LivingEntity> bredHorses = new ArrayList<>();
    private final GeneFactory geneFactory;

    private final GenomeFactory genomeFactory;

    public HorseCreationListener(GeneFactory geneFactory, GenomeFactory genomeFactory) {
        this.geneFactory = geneFactory;
        this.genomeFactory = genomeFactory;
    }

    @EventHandler(priority = EventPriority.MONITOR)
    private void conservativeCrossBreeding(final EntityBreedEvent event) {
        LivingEntity father = event.getFather();
        LivingEntity mother = event.getMother();
        AnimalGenome fatherGenes = extractData(father);
        AnimalGenome motherGenes = extractData(mother);
        bredHorses.add(event.getEntity());
        Bukkit.getPluginManager().callEvent(new AnimalSetGenomeFields(event.getEntity(), genomeFactory.crossGenome(fatherGenes, motherGenes, event.getEntityType())));

    }

    @EventHandler(priority = EventPriority.LOWEST)
    private void cancelSameSexBreeding(final EntityBreedEvent event) {
        LivingEntity father = event.getFather();
        LivingEntity mother = event.getMother();
        if (father instanceof Breedable f && mother instanceof Breedable m) {
            if (checkForSameSex(extractData(father), extractData(mother))) { //if they are homo, then cancel
                event.setCancelled(true);
                f.setBreed(false);
                m.setBreed(false);
            }
        }
    }

    private AnimalGenome extractData(LivingEntity livingEntity) {
        PersistentDataContainer persistentDataContainer = livingEntity.getPersistentDataContainer();
        String s = persistentDataContainer.get(HorsePoop.GENOME_KEY, PersistentDataType.STRING);
        return new Gson().fromJson(s, AnimalGenome.class);
    }

    private boolean checkForSameSex(AnimalGenome f, AnimalGenome m) {
        return f.getGender() == m.getGender(); // returns true if they are homo
    }

    @EventHandler(priority = EventPriority.MONITOR)
    private void onHorseSpawn(final EntitySpawnEvent event) {
        Entity entity = event.getEntity();
        if (!(entity instanceof LivingEntity livingEntity)) {
            return;
        }
        if (!geneFactory.getHorseConfig().getEnabledEntityTypes().contains(livingEntity.getType().toString())) {
            return;
        }
        if (bredHorses.contains(entity)) {
            Bukkit.broadcastMessage("cancelled natural spawning");
            return;
        }
        if (event.isCancelled()) {
            return;
        }
        Bukkit.getPluginManager().callEvent(new AnimalSetGenomeFields(livingEntity, AnimalGenome.createInstance(geneFactory, event.getEntityType(), 3)));
    }
}
