package mintychochip.mintychochip.horsepoop.listener;

import java.util.ArrayList;
import java.util.List;
import mintychochip.mintychochip.horsepoop.api.AnimalSetGenomeFields;
import mintychochip.mintychochip.horsepoop.config.ConfigManager;
import mintychochip.mintychochip.horsepoop.container.AnimalGenome;
import mintychochip.mintychochip.horsepoop.container.grabber.GenomeGrasper;
import mintychochip.mintychochip.horsepoop.factories.sequential.instancer.gene.abstraction.GenomeGenerator;
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
    private final ConfigManager configManager;

    private final GenomeGenerator genomeGenerator;

    private final GenomeGrasper grasper;

    public HorseCreationListener(ConfigManager configManager, GenomeGenerator genomeGenerator, GenomeGrasper grasper) {
        this.configManager = configManager;
        this.genomeGenerator = genomeGenerator;
        this.grasper = grasper;
    }

  @EventHandler(priority = EventPriority.LOWEST)
  private void conservativeCrossBreeding(final EntityBreedEvent event) {
    LivingEntity father = event.getFather();
    LivingEntity mother = event.getMother();
    AnimalGenome fatherGenes = grasper.grab(father);
    AnimalGenome motherGenes = grasper.grab(mother);
    if (fatherGenes == null || motherGenes == null) {
      return;
    }
    bredHorses.add(event.getEntity());
    AnimalGenome instance = genomeGenerator.instanceGenome(father.getType());
    if (instance == null) {
      event.setCancelled(true);
    }
    Bukkit.getPluginManager()
        .callEvent(new AnimalSetGenomeFields(event.getEntity(), instance));
  }


//    @EventHandler(priority = EventPriority.LOW)
//    private void cancelSameSexBreeding(final EntityBreedEvent event) {
//        LivingEntity father = event.getFather();
//        LivingEntity mother = event.getMother();
//        if (father instanceof Breedable f && mother instanceof Breedable m) {
//            AnimalGenome fatherGene = grasper.grab(father);
//            AnimalGenome motherGene = grasper.grab(mother);
//            if (fatherGene == null || motherGene == null) {
//                return;
//            }
//            Fetcher<Intrinsic> fetcher = new ValueFetcher<>();
//            if (fetcher.getEnumValue(fatherGene.getIntrinsics(), IntrinsicTraitEnum.GENDER) instanceof
//                    ){ //if they are homo, then cancel
//                event.setCancelled(true);
//                f.setBreed(false);
//                m.setBreed(false);
//            }
//            //add homosexual experience
//        }
//    }

    @EventHandler(priority = EventPriority.MONITOR)
    private void onEntitySpawn(final EntitySpawnEvent event) {
        if (event.isCancelled()) {
            return;
        }
        Entity entity = event.getEntity();
        if (!(entity instanceof LivingEntity livingEntity)) {
            return;
        }
        if (!configManager.getEntityConfig().getEnabledEntityTypes().contains(livingEntity.getType())) {
            return;
        }
        if (bredHorses.contains(entity)) {
            bredHorses.remove(entity);
            return;
        }
        AnimalGenome animalGenome = grasper.grab(livingEntity);
        if (animalGenome != null) {
            Bukkit.getPluginManager().callEvent(new AnimalSetGenomeFields(livingEntity, animalGenome));
            return;
        }
        animalGenome = genomeGenerator.instanceGenome(livingEntity.getType());
        if (animalGenome == null) {
            return;
        }
        Bukkit.getPluginManager().callEvent(new AnimalSetGenomeFields(livingEntity,
                animalGenome));
    }
}
