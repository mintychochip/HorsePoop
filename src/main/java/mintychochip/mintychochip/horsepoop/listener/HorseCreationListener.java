package mintychochip.mintychochip.horsepoop.listener;

import mintychochip.mintychochip.horsepoop.api.AnimalSetGenomeFields;
import mintychochip.mintychochip.horsepoop.config.ConfigManager;
import mintychochip.mintychochip.horsepoop.container.AnimalGenome;
import mintychochip.mintychochip.horsepoop.factories.GeneFactory;
import mintychochip.mintychochip.horsepoop.factories.GenomeFactory;
import mintychochip.mintychochip.horsepoop.util.DataExtractor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Breedable;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityBreedEvent;
import org.bukkit.event.entity.EntitySpawnEvent;

import java.util.ArrayList;
import java.util.List;

public class HorseCreationListener implements Listener { //monitor listeners only

  private List<LivingEntity> bredHorses = new ArrayList<>();
  private final GeneFactory geneFactory;
  private final GenomeFactory genomeFactory;

  private final ConfigManager configManager;
  public HorseCreationListener(ConfigManager configManager,GeneFactory geneFactory, GenomeFactory genomeFactory) {
    this.geneFactory = geneFactory;
    this.genomeFactory = genomeFactory;
    this.configManager = configManager;
  }

  @EventHandler(priority = EventPriority.LOWEST)
  private void conservativeCrossBreeding(final EntityBreedEvent event) {
    LivingEntity father = event.getFather();
    LivingEntity mother = event.getMother();
    AnimalGenome fatherGenes = DataExtractor.extractGenomicData(father);
    AnimalGenome motherGenes = DataExtractor.extractGenomicData(mother);
    if (fatherGenes == null || motherGenes == null) {
      return;
    }
    bredHorses.add(event.getEntity());
    AnimalGenome animalGenome = fatherGenes.crossGenome(motherGenes,event.getEntityType(),geneFactory);
    if (animalGenome == null) {
      event.setCancelled(true);
    }
      Bukkit.getPluginManager()
          .callEvent(new AnimalSetGenomeFields(event.getEntity(), animalGenome));
  }


  @EventHandler(priority = EventPriority.LOW)
  private void cancelSameSexBreeding(final EntityBreedEvent event) {
    LivingEntity father = event.getFather();
    LivingEntity mother = event.getMother();
    if (father instanceof Breedable f && mother instanceof Breedable m) {
      AnimalGenome fatherGene = DataExtractor.extractGenomicData(father);
      AnimalGenome motherGene = DataExtractor.extractGenomicData(mother);
      if (fatherGene == null || motherGene == null) {
        return;
      }
      if (fatherGene.getGender() == motherGene.getGender()) { //if they are homo, then cancel
        event.setCancelled(true);
        f.setBreed(false);
        m.setBreed(false);
      }
      //add homosexual experience
    }
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
    if (!geneFactory.getHorseConfig().getEnabledEntityTypes().contains(livingEntity.getType())) {
      return;
    }
    if (bredHorses.contains(entity)) {
      bredHorses.remove(entity);
      return;
    }
    if (event.isCancelled()) {
      return;
    }
    Bukkit.getPluginManager().callEvent(new AnimalSetGenomeFields(livingEntity, genomeFactory.createInstance(entity.getType(),geneFactory,genomeFactory)));
  }
}
