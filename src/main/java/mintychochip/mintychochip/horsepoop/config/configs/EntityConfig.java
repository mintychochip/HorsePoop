package mintychochip.mintychochip.horsepoop.config.configs;

import java.util.Set;
import java.util.stream.Collectors;
import mintychochip.genesis.config.abstraction.GenericConfig;
import mintychochip.genesis.util.EnumUtil;
import mintychochip.mintychochip.horsepoop.api.Phenotypic;
import mintychochip.mintychochip.horsepoop.api.Gene;
import mintychochip.mintychochip.horsepoop.api.TraitEnum;
import mintychochip.mintychochip.horsepoop.container.enums.attributes.GenericGene;
import mintychochip.mintychochip.horsepoop.container.enums.attributes.specific.CowGene;
import mintychochip.mintychochip.horsepoop.container.enums.attributes.specific.GeneticAttribute;
import mintychochip.mintychochip.horsepoop.container.enums.attributes.specific.SheepGene;
import mintychochip.mintychochip.horsepoop.container.enums.characteristics.GenericCharacteristicTraitEnum;
import org.bukkit.Bukkit;
import org.bukkit.entity.EntityType;
import org.bukkit.plugin.java.JavaPlugin;

public class EntityConfig extends GenericConfig {

  private Set<EntityType> enabledEntityTypes;
  private TraitConfig<Gene> geneConfig = new TraitConfig<>(Gene.class);
  private TraitConfig<Phenotypic> characteristicConfig = new TraitConfig<>(
      Phenotypic.class);

  private void loadAllTraits() {
    geneConfig.loadEnums(GeneticAttribute.class,GeneticAttribute.values());
    geneConfig.loadEnums(SheepGene.class, SheepGene.values());
    geneConfig.loadEnums(GenericGene.class, GenericGene.values());
    geneConfig.loadEnums(CowGene.class, CowGene.values());
    characteristicConfig.loadEnums(GenericCharacteristicTraitEnum.class, GenericCharacteristicTraitEnum.values());
  }

  private <T extends TraitEnum> boolean isEnumImpl(Class<T> enumClass, Class<?> interfaceClass) {
    return interfaceClass.isAssignableFrom(enumClass) && enumClass.isEnum();
  }

  public EntityConfig(String path, JavaPlugin plugin) {
    super(path, plugin);
    loadAllTraits();
    Bukkit.getScheduler().runTaskLater(plugin, x -> {
      reload();
    }, 3L);
  }

  public TraitConfig<Gene> geneConfig() {
    return geneConfig;
  }

  public TraitConfig<Phenotypic> characteristicConfig() {
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
    //this.entityTypeTraitMap = resetEntityTypeTraitMap();
  }

  private Set<EntityType> loadEnabledEntityTypes() {
    return this.getKeys(false).stream().map(String::toUpperCase)
        .filter(x -> EnumUtil.isInEnum(EntityType.class, x))
        .map(x -> Enum.valueOf(EntityType.class, x)).collect(
            Collectors.toSet());
  }
}
