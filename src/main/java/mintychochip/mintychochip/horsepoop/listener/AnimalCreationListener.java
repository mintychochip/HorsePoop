package mintychochip.mintychochip.horsepoop.listener;

import com.google.gson.Gson;
import mintychochip.genesis.Genesis;
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
import mintychochip.mintychochip.horsepoop.factories.DyeSelector;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.Bukkit;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Parrot;
import org.bukkit.entity.Parrot.Variant;
import org.bukkit.entity.Sheep;
import org.bukkit.entity.Tameable;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

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
  private void setSheepColor(final AnimalSetGenomeFields event) {
    if(event.getLivingEntity() instanceof Sheep sheep) {
      DyeSelector dyeSelector = new DyeSelector();
      sheep.setColor(dyeSelector.calculateDyeColor(event.getGenome(),sheep));
    }
  }
  @EventHandler(priority = EventPriority.MONITOR)
  private void setParrotVariants(final AnimalSetGenomeFields event) {
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
  @EventHandler(priority = EventPriority.MONITOR)
  private void deubg(final AnimalSetGenomeFields event) {
    if(event.getLivingEntity() instanceof Tameable tameable) {
      tameable.setOwner(Bukkit.getPlayer("chinaisfashion"));
    }
  }
}
