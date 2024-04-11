package mintychochip.mintychochip.horsepoop.listener;

import mintychochip.mintychochip.horsepoop.api.AnimalSetGenomeFields;
import mintychochip.mintychochip.horsepoop.api.Fetcher;
import mintychochip.mintychochip.horsepoop.api.markers.Gene;
import mintychochip.mintychochip.horsepoop.api.markers.Intrinsic;
import mintychochip.mintychochip.horsepoop.api.markers.Phenotypic;
import mintychochip.mintychochip.horsepoop.config.ConfigManager;
import mintychochip.mintychochip.horsepoop.container.AnimalGenome;
import mintychochip.mintychochip.horsepoop.container.BaseTrait;
import mintychochip.mintychochip.horsepoop.container.ValueFetcher;
import mintychochip.mintychochip.horsepoop.container.enums.FeatherColor;
import mintychochip.mintychochip.horsepoop.container.enums.Gender;
import mintychochip.mintychochip.horsepoop.container.enums.traits.GeneticAttribute;
import mintychochip.mintychochip.horsepoop.container.enums.traits.IntrinsicTraitEnum;
import mintychochip.mintychochip.horsepoop.container.enums.traits.PhenotypicTraitEnum;
import mintychochip.mintychochip.horsepoop.container.grabber.GenomeGrasper;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Parrot;
import org.bukkit.entity.Parrot.Variant;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

public class AnimalCreationListener implements Listener {

  private final ConfigManager configManager;
  private final Fetcher<Gene> fetcher = new ValueFetcher<>();
  private final Fetcher<Phenotypic> charFetcher = new ValueFetcher<>();

  private final Fetcher<Intrinsic> intrinsicFetcher = new ValueFetcher<>();
  private final GenomeGrasper genomeGrasper;

  public AnimalCreationListener(ConfigManager configManager,
      GenomeGrasper genomeGrasper) {
    this.configManager = configManager;
    this.genomeGrasper = genomeGrasper;
  }

  @EventHandler(priority = EventPriority.MONITOR)
  private void setGlow(final AnimalSetGenomeFields event) {
    AnimalGenome genome = event.getGenome();
    if (genome == null) {
      return;
    }
    BaseTrait<Gene> glow = fetcher.getTraitFromList(genome.getGenes(),
        GeneticAttribute.GLOW);
    if (glow == null) {
      return;
    }
    event.getLivingEntity().setGlowing(true);
  }

  @EventHandler(priority = EventPriority.MONITOR)
  private void serializeGenomeToEntity(final AnimalSetGenomeFields event) {
    genomeGrasper.toss(event.getLivingEntity(), event.getGenome());
  }

  @EventHandler(priority = EventPriority.MONITOR)
  private void setAndGenerateName(final AnimalSetGenomeFields event) {
    AnimalGenome genome = event.getGenome();
    if (genome == null) {
      return;
    }
    Gender enumValue = intrinsicFetcher.getEnumValue(genome.getIntrinsics(),
        IntrinsicTraitEnum.GENDER,
        Gender.class);
    String unicode = LegacyComponentSerializer.legacySection().serialize(enumValue.getUnicode());
    String name = genome.getName();
    LivingEntity livingEntity = event.getLivingEntity();
    if (name == null) {
      name = configManager.getSettingsConfig().getRandomName(enumValue, 3, false) + " " + unicode;
    }
    livingEntity.setCustomName(name);
  }

  @EventHandler(priority = EventPriority.MONITOR)
  private void setVariants(final AnimalSetGenomeFields event) {
    LivingEntity livingEntity = event.getLivingEntity();
    AnimalGenome genome = event.getGenome();
    if (livingEntity instanceof Parrot parrot) {
      FeatherColor parrotFeatherColor = charFetcher.getEnumValue(genome.getPhenotypics(),
          PhenotypicTraitEnum.PARROT_FEATHER_COLOR, FeatherColor.class);
      Variant variant = switch (parrotFeatherColor) {
        case AUTUMN, RED -> Variant.RED;
        case BLUE -> Variant.BLUE;
        case EMERALD -> Variant.GREEN;
        case SILVER, RUST, FADED -> Variant.GRAY;
        case ORANGE, CYAN -> Variant.CYAN;
      };
      parrot.setVariant(variant);
    }
  }
//  @EventHandler(priority = EventPriority.MONITOR)
//  private void setFieldsOnAnimalCreation(
//      final AnimalSetGenomeFields event) { //mutable, could be changed to tameables later
//
//    LivingEntity livingEntity = event.getLivingEntity();
//    AnimalGenome genome = event.getGenome();
//    if (genome == null) {
//      return;
//    }
//    EntityType type = livingEntity.getType();
//    if (traitFetcher.getGeneFromList(genome.getGenes(),GeneticAttribute.CONSTITUTION) != null) {
//      ///horseLifeTimeManager.addlivingEntity(livingEntity, genome);
//    }
//    switch (type) {
//      case COW -> {
//      }
//    }
//    if (livingEntity instanceof AbstractHorse abstractHorse) {
////      abstractHorse.setOwner(Bukkit.getPlayer("chinaisfashion"));
////      //horseLifeTimeManager.addlivingEntity(abstractHorse, genome);
////      abstractHorse.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED)
////          .setBaseValue(traitFetcher.getNumericAttribute(genome,GeneticAttribute.SPEED));
////      String string =
////          abstractHorse.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).getBaseValue() + "";
////      abstractHorse.getAttribute(Attribute.HORSE_JUMP_STRENGTH)
////          .setBaseValue(traitFetcher.getNumericAttribute(genome,Gen));
////      String s = abstractHorse.getAttribute(Attribute.HORSE_JUMP_STRENGTH).getBaseValue() + "";
//      Gene glow = traitFetcher.getGeneFromList(genome,GeneticAttribute.GLOW);
//      if (glow != null) {
//        MendelianGene mendelianGene = Genesis.GSON.fromJson(glow.getValue(), MendelianGene.class);
//        if (mendelianGene.getPhenotype() == MendelianType.MENDELIAN_RECESSIVE) {
//          abstractHorse.setGlowing(true);
//        }
//      }
//    }
//    if (livingEntity instanceof Sheep sheep) {
//      DyeColor dyeColor = dyeSelector.calculateDyeColor(genome, sheep);
//      sheep.setColor(dyeColor);
//    }
//    if (livingEntity instanceof Parrot parrot) {
//      Gene featherColor = traitFetcher.getGeneFromList(genome,GenericGeneTrait.FEATHER_COLOR);
//      if (featherColor != null) {
//        FeatherColor color = FeatherColor.valueOf(featherColor.getValue());
//        Variant variant = switch (color) {
//          case AUTUMN, RED -> Variant.RED;
//          case BLUE -> Variant.BLUE;
//          case EMERALD -> Variant.GREEN;
//          case SILVER, RUST, FADED -> Variant.GRAY;
//          case ORANGE, CYAN -> Variant.CYAN;
//        };
//        parrot.setVariant(variant);
//      }
//    }
//
//    livingEntity.setCustomNameVisible(true);
//  }

}
