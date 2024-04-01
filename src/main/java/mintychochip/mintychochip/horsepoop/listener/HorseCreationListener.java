package mintychochip.mintychochip.horsepoop.listener;

import java.util.ArrayList;
import java.util.List;
import mintychochip.mintychochip.horsepoop.api.AnimalSetGenomeFields;
import mintychochip.mintychochip.horsepoop.config.ConfigManager;
import mintychochip.mintychochip.horsepoop.container.AnimalGenome;
import mintychochip.mintychochip.horsepoop.container.Characteristic;
import mintychochip.mintychochip.horsepoop.container.Gene;
import mintychochip.mintychochip.horsepoop.factories.GeneFactory;
import mintychochip.mintychochip.horsepoop.factories.sequential.crosser.abstraction.GenomeCrosser;
import mintychochip.mintychochip.horsepoop.factories.sequential.instancer.gene.abstraction.GenomeInstancer;
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
  private final GenomeCrosser genomeCrosser;

  private final GenomeInstancer genomeInstancer;


  public HorseCreationListener(ConfigManager configManager, GeneFactory geneFactory, GenomeCrosser genomeCrosser, GenomeInstancer genomeInstancer) {
    this.geneFactory = geneFactory;
    this.genomeCrosser = genomeCrosser;
    this.genomeInstancer = genomeInstancer;
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
    List<Characteristic> list = genomeInstancer.getCharInstancer()
        .instanceTraits(event.getEntityType()).stream().filter(x -> x instanceof Characteristic)
        .map(x -> (Characteristic) x).toList();
    AnimalGenome instance = AnimalGenome.createInstance(list, genes, genomeInstancer);
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
    if (!geneFactory.getConfigManager().getEntityConfig().getEnabledEntityTypes().contains(livingEntity.getType())) {
      return;
    }
    if (bredHorses.contains(entity)) {
      bredHorses.remove(entity);
      return;
    }
//    AnimalGenome animalGenome = DataExtractor.extractGenomicData(entity);
//    if(animalGenome != null) {
//      Bukkit.broadcastMessage(animalGenome.getGenes().toString());
//      Bukkit.getPluginManager().callEvent(new AnimalSetGenomeFields(livingEntity,animalGenome));
//      return;
//    }

    AnimalGenome animalGenome = genomeInstancer.instanceGenome(livingEntity.getType());
    if(animalGenome == null) {
      return;
    }
    Bukkit.getPluginManager().callEvent(new AnimalSetGenomeFields(livingEntity,
        animalGenome));
  }
}
