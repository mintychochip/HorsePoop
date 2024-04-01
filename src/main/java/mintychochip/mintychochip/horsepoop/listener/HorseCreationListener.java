package mintychochip.mintychochip.horsepoop.listener;

import java.util.ArrayList;
import java.util.List;
import mintychochip.mintychochip.horsepoop.api.AnimalSetGenomeFields;
import mintychochip.mintychochip.horsepoop.config.ConfigManager;
import mintychochip.mintychochip.horsepoop.container.AnimalGenome;
import mintychochip.mintychochip.horsepoop.container.Gene;
import mintychochip.mintychochip.horsepoop.factories.GeneFactory;
import mintychochip.mintychochip.horsepoop.factories.GenomeFactory;
import mintychochip.mintychochip.horsepoop.factories.crosser.GenomeCrosser;
import mintychochip.mintychochip.horsepoop.util.DataExtractor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityBreedEvent;
import org.bukkit.event.entity.EntitySpawnEvent;

public class HorseCreationListener implements Listener { //monitor listeners only

  private List<LivingEntity> bredHorses = new ArrayList<>();
  private final GeneFactory geneFactory;
  private final GenomeFactory genomeFactory;
  private final GenomeCrosser genomeCrosser;


  public HorseCreationListener(ConfigManager configManager, GeneFactory geneFactory,
      GenomeFactory genomeFactory, GenomeCrosser genomeCrosser) {
    this.geneFactory = geneFactory;
    this.genomeFactory = genomeFactory;
    this.genomeCrosser = genomeCrosser;
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
    List<Gene> genes = genomeCrosser.crossGenomes(fatherGenes,motherGenes, event.getEntityType());
    if (genes == null) {
      event.setCancelled(true);
    }
    AnimalGenome instance = AnimalGenome.createInstance(null, genes, genomeCrosser);
    if(instance == null) {
      event.setCancelled(true);
    }
    Bukkit.getPluginManager()
        .callEvent(new AnimalSetGenomeFields(event.getEntity(), instance));
  }


//  @EventHandler(priority = EventPriority.LOW)
//  private void cancelSameSexBreeding(final EntityBreedEvent event) {
//    LivingEntity father = event.getFather();
//    LivingEntity mother = event.getMother();
//    if (father instanceof Breedable f && mother instanceof Breedable m) {
//      AnimalGenome fatherGene = DataExtractor.extractGenomicData(father);
//      AnimalGenome motherGene = DataExtractor.extractGenomicData(mother);
//      if (fatherGene == null || motherGene == null) {
//        return;
//      }
//      if (fatherGene.getCharacteristic(Characteristic) == motherGene.getGender()) { //if they are homo, then cancel
//        event.setCancelled(true);
//        f.setBreed(false);
//        m.setBreed(false);
//      }
//      //add homosexual experience
//    }
//  }
  @EventHandler(priority = EventPriority.MONITOR)
  private void onHorseSpawn(final EntitySpawnEvent event) {
    if (event.isCancelled()) {
      return;
    }
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
    AnimalGenome animalGenome = DataExtractor.extractGenomicData(entity);
    if(animalGenome != null) {
      Bukkit.broadcastMessage(animalGenome.getGenes().toString());
      Bukkit.getPluginManager().callEvent(new AnimalSetGenomeFields(livingEntity,animalGenome));
      return;
    }
    Bukkit.getPluginManager().callEvent(new AnimalSetGenomeFields(livingEntity,
        genomeFactory.createInstance(entity.getType(), geneFactory, genomeFactory)));
  }
}
