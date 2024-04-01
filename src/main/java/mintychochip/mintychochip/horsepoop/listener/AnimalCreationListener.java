package mintychochip.mintychochip.horsepoop.listener;

import mintychochip.genesis.Genesis;
import mintychochip.mintychochip.horsepoop.HorsePoop;
import mintychochip.mintychochip.horsepoop.api.AnimalSetGenomeFields;
import mintychochip.mintychochip.horsepoop.config.ConfigManager;
import mintychochip.mintychochip.horsepoop.container.AnimalGenome;
import mintychochip.mintychochip.horsepoop.container.Gene;
import mintychochip.mintychochip.horsepoop.container.MendelianGene;
import mintychochip.mintychochip.horsepoop.container.TraitFetcher;
import mintychochip.mintychochip.horsepoop.container.enums.FeatherColor;
import mintychochip.mintychochip.horsepoop.container.enums.attributes.GenericGeneTrait;
import mintychochip.mintychochip.horsepoop.container.enums.attributes.specific.GeneticAttribute;
import mintychochip.mintychochip.horsepoop.container.enums.MendelianType;
import mintychochip.mintychochip.horsepoop.container.enums.attributes.specific.SheepGeneTrait;
import mintychochip.mintychochip.horsepoop.factories.DyeSelector;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import org.bukkit.Bukkit;
import org.bukkit.DyeColor;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.*;
import org.bukkit.entity.Parrot.Variant;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

public class AnimalCreationListener implements Listener {

  private final BukkitAudiences bukkitAudiences;

  private final ConfigManager configManager;

  private final DyeSelector dyeSelector;
  private final TraitFetcher traitFetcher;
  public AnimalCreationListener(BukkitAudiences bukkitAudiences, ConfigManager configManager, TraitFetcher traitFetcher) {
    this.bukkitAudiences = bukkitAudiences;
    this.configManager = configManager;
    this.traitFetcher = traitFetcher;
    dyeSelector = new DyeSelector(traitFetcher);

  }

  @EventHandler(priority = EventPriority.MONITOR)
  private void setFieldsOnAnimalCreation(
      final AnimalSetGenomeFields event) { //mutable, could be changed to tameables later

    LivingEntity livingEntity = event.getLivingEntity();
    AnimalGenome genome = event.getGenome();
    if (genome == null) {
      return;
    }
//    String genderUnicode = LegacyComponentSerializer.legacySection()
//        .serialize(genome.getGender().getUnicode());
//    String name = genome.getName();
//    if (name == null) {
//      name = configManager.getSettingsConfig()
//          .getRandomName(genome.getGender(), 3, false) + " " + genderUnicode;
//      genome.setName(name);
//      livingEntity.setCustomName(name);
//    } else {
//      livingEntity.setCustomName(name);
//    }
    PersistentDataContainer persistentDataContainer = livingEntity.getPersistentDataContainer();
    persistentDataContainer.set(HorsePoop.GENOME_KEY, PersistentDataType.STRING,
        HorsePoop.GSON.toJson(genome));
    EntityType type = livingEntity.getType();
    if (traitFetcher.getGeneFromGeneList(genome.getGenes(),GeneticAttribute.CONSTITUTION) != null) {
      ///horseLifeTimeManager.addlivingEntity(livingEntity, genome);
    }
    switch (type) {
      case COW -> {
      }
    }
    if (livingEntity instanceof AbstractHorse abstractHorse) {
//      abstractHorse.setOwner(Bukkit.getPlayer("chinaisfashion"));
//      //horseLifeTimeManager.addlivingEntity(abstractHorse, genome);
//      abstractHorse.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED)
//          .setBaseValue(traitFetcher.getNumericAttribute(genome,GeneticAttribute.SPEED));
//      String string =
//          abstractHorse.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).getBaseValue() + "";
//      abstractHorse.getAttribute(Attribute.HORSE_JUMP_STRENGTH)
//          .setBaseValue(traitFetcher.getNumericAttribute(genome,Gen));
//      String s = abstractHorse.getAttribute(Attribute.HORSE_JUMP_STRENGTH).getBaseValue() + "";
      Gene glow = traitFetcher.getGeneFromGeneList(genome,GeneticAttribute.GLOW);
      if (glow != null) {
        MendelianGene mendelianGene = Genesis.GSON.fromJson(glow.getValue(), MendelianGene.class);
        if (mendelianGene.getPhenotype() == MendelianType.MENDELIAN_RECESSIVE) {
          abstractHorse.setGlowing(true);
        }
      }
    }
    if (livingEntity instanceof Sheep sheep) {
      DyeColor dyeColor = dyeSelector.calculateDyeColor(genome, sheep);
      sheep.setColor(dyeColor);
    }
    if (livingEntity instanceof Parrot parrot) {
      Gene featherColor = traitFetcher.getGeneFromGeneList(genome,GenericGeneTrait.FEATHER_COLOR);
      if (featherColor != null) {
        FeatherColor color = FeatherColor.valueOf(featherColor.getValue());
        Variant variant = switch (color) {
          case AUTUMN, RED -> Variant.RED;
          case BLUE -> Variant.BLUE;
          case EMERALD -> Variant.GREEN;
          case SILVER, RUST, FADED -> Variant.GRAY;
          case ORANGE, CYAN -> Variant.CYAN;
        };
        parrot.setVariant(variant);
      }
    }

    livingEntity.setCustomNameVisible(true);
  }

}
