package mintychochip.mintychochip.horsepoop.config.configs;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import mintychochip.genesis.config.abstraction.GenericConfig;
import mintychochip.genesis.util.EnumUtil;
import mintychochip.mintychochip.horsepoop.api.Gene;
import mintychochip.mintychochip.horsepoop.api.Intrinsic;
import mintychochip.mintychochip.horsepoop.api.Phenotypic;
import mintychochip.mintychochip.horsepoop.api.TraitEnum;
import mintychochip.mintychochip.horsepoop.container.enums.traits.CowGene;
import mintychochip.mintychochip.horsepoop.container.enums.traits.GenericGene;
import mintychochip.mintychochip.horsepoop.container.enums.traits.GeneticAttribute;
import mintychochip.mintychochip.horsepoop.container.enums.traits.IntrinsicTraitEnum;
import mintychochip.mintychochip.horsepoop.container.enums.traits.PhenotypicTraitEnum;
import mintychochip.mintychochip.horsepoop.container.enums.traits.SheepGene;
import org.bukkit.entity.EntityType;
import org.bukkit.plugin.java.JavaPlugin;

public class EntityConfig extends GenericConfig {

  private Set<EntityType> enabledEntityTypes;
  private final TraitConfig<Gene> geneConfig = new TraitConfig<>(Gene.class);
  private final TraitConfig<Phenotypic> characteristicConfig = new TraitConfig<>(
      Phenotypic.class);
  private final TraitConfig<Intrinsic> intrinsicConfig = new TraitConfig<>(Intrinsic.class);

  private final Map<String,TraitEnum> traitEnumMap = new HashMap<>();
  private void loadAllTraits() {
    geneConfig.loadEnums(GeneticAttribute.values());
    geneConfig.loadEnums(SheepGene.values());
    geneConfig.loadEnums(GenericGene.values());
    geneConfig.loadEnums(CowGene.values());
    characteristicConfig.loadEnums(PhenotypicTraitEnum.values());
    intrinsicConfig.loadEnums(IntrinsicTraitEnum.values());
  }
  public EntityConfig(String path, JavaPlugin plugin) {
    super(path, plugin);
    loadAllTraits();
    reload();
  }

  public TraitConfig<Intrinsic> getIntrinsicConfig() {
    return intrinsicConfig;
  }

  public TraitConfig<Gene> getGeneConfig() {
    return geneConfig;
  }

  public TraitConfig<Phenotypic> getCharacteristicConfig() {
    return characteristicConfig;
  }

  public boolean isEntityEnabled(EntityType entityType) {
    return enabledEntityTypes.contains(entityType);
  }

  public Set<EntityType> getEnabledEntityTypes() {
    return enabledEntityTypes;
  }

  @Override
  public void reload() {
    super.reload();
    this.enabledEntityTypes = loadEnabledEntityTypes();
    this.geneConfig.loadTraitConfigs(this);
    this.characteristicConfig.loadTraitConfigs(this);
    this.intrinsicConfig.loadTraitConfigs(this);
    //this.entityTypeTraitMap = resetEntityTypeTraitMap();
  }

  private Set<EntityType> loadEnabledEntityTypes() {
    return this.getKeys(false).stream().map(String::toUpperCase)
        .filter(x -> EnumUtil.isInEnum(EntityType.class, x))
        .map(x -> Enum.valueOf(EntityType.class, x)).collect(
            Collectors.toSet());
  }
}
