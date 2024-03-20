package mintychochip.mintychochip.horsepoop.listener;

import com.google.gson.Gson;
import mintychochip.genesis.Genesis;
import mintychochip.mintychochip.horsepoop.HorsePoop;
import mintychochip.mintychochip.horsepoop.api.AnimalCreationEvent;
import mintychochip.mintychochip.horsepoop.container.AnimalGenome;
import mintychochip.mintychochip.horsepoop.factories.GeneFactory;
import mintychochip.mintychochip.horsepoop.factories.GenomeFactory;
import mintychochip.mintychochip.horsepoop.horse.HorseLifeTimeManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityBreedEvent;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HorseCreationListener implements Listener { //monitor listeners only

    private List<AbstractHorse> bredHorses = new ArrayList<>();


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
        if (father instanceof AbstractHorse f && mother instanceof AbstractHorse m) { //plugin isn't adapted for normal aniamls yet
            AnimalGenome fatherGenes = extractData(f);
            AnimalGenome motherGenes = extractData(m);
            bredHorses.add((AbstractHorse) event.getEntity());

            Bukkit.getPluginManager().callEvent(new AnimalCreationEvent((AbstractHorse) event.getEntity(), genomeFactory.crossGenome(fatherGenes, motherGenes)));
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    private void cancelSameSexBreeding(final EntityBreedEvent event) {
        LivingEntity father = event.getFather();
        LivingEntity mother = event.getMother();
        if (father instanceof AbstractHorse f && mother instanceof AbstractHorse m) {
            if (checkForSameSex(extractData(f), extractData(m))) { //if they are homo, then cancel
                event.setCancelled(true);
                event.getBreeder().sendMessage("ppppp");
                f.setBreed(false);
                m.setBreed(false);
            }
        }
    }

    private AnimalGenome extractData(AbstractHorse horse) {
        PersistentDataContainer persistentDataContainer = horse.getPersistentDataContainer();
        String s = persistentDataContainer.get(Genesis.getKey("horse"), PersistentDataType.STRING);
        return new Gson().fromJson(s, AnimalGenome.class);
    }

    private boolean checkForSameSex(AnimalGenome f, AnimalGenome m) {
        return f.getGender() == m.getGender(); // returns true if they are homo
    }

    @EventHandler(priority = EventPriority.MONITOR)
    private void onHorseSpawn(final EntitySpawnEvent event) {
        Entity entity = event.getEntity();
        if(!(entity instanceof LivingEntity livingEntity)) {
            return;
        }

        if (bredHorses.contains(entity)) {
            Bukkit.broadcastMessage("cancelled natural spawning");
            return;
        }
        if (event.isCancelled()) {
            return;
        }
        Bukkit.getPluginManager().callEvent(new AnimalCreationEvent((AbstractHorse) livingEntity, AnimalGenome.createInstance(geneFactory, 3)));
    }
}
