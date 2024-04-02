package mintychochip.mintychochip.horsepoop.config.configs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import mintychochip.genesis.config.abstraction.GenericConfig;
import mintychochip.genesis.util.EnumUtil;
import mintychochip.mintychochip.horsepoop.config.CharacteristicTraitMeta;
import mintychochip.mintychochip.horsepoop.config.GeneTraitMeta;
import mintychochip.mintychochip.horsepoop.container.CharacteristicTrait;
import mintychochip.mintychochip.horsepoop.container.GeneTrait;
import mintychochip.mintychochip.horsepoop.container.Trait;
import mintychochip.mintychochip.horsepoop.container.enums.attributes.GenericGeneTrait;
import mintychochip.mintychochip.horsepoop.container.enums.attributes.specific.CowGeneTrait;
import mintychochip.mintychochip.horsepoop.container.enums.attributes.specific.GeneticAttribute;
import mintychochip.mintychochip.horsepoop.container.enums.attributes.specific.SheepGeneTrait;
import mintychochip.mintychochip.horsepoop.container.enums.characteristics.GenericCharacteristicTrait;
import org.bukkit.Bukkit;
import org.bukkit.entity.EntityType;
import org.bukkit.plugin.java.JavaPlugin;

public class EntityConfig extends GenericConfig {

  private Set<EntityType> enabledEntityTypes;
  private TraitConfig<GeneTrait, GeneTraitMeta> geneConfig = new TraitConfig<>(GeneTrait.class,
      GeneTraitMeta.class);
  private TraitConfig<CharacteristicTrait, CharacteristicTraitMeta> characteristicConfig = new TraitConfig<>(
      CharacteristicTrait.class,
      CharacteristicTraitMeta.class);

  private void loadAllTraits() {
    geneConfig.loadEnums(GeneticAttribute.class,GeneticAttribute.values());
    geneConfig.loadEnums(SheepGeneTrait.class,SheepGeneTrait.values());
    geneConfig.loadEnums(GenericGeneTrait.class,GenericGeneTrait.values());
    geneConfig.loadEnums(CowGeneTrait.class,CowGeneTrait.values());
    characteristicConfig.loadEnums(GenericCharacteristicTrait.class,GenericCharacteristicTrait.values());
  }

  private <T extends Trait> boolean isEnumImpl(Class<T> enumClass, Class<?> interfaceClass) {
    return interfaceClass.isAssignableFrom(enumClass) && enumClass.isEnum();
  }

  public EntityConfig(String path, JavaPlugin plugin) {
    super(path, plugin);
    loadAllTraits();
    Bukkit.getScheduler().runTaskLater(plugin, x -> {
      reload();
    }, 3L);
  }

  public TraitConfig<GeneTrait, GeneTraitMeta> geneConfig() {
    return geneConfig;
  }

  public TraitConfig<CharacteristicTrait, CharacteristicTraitMeta> characteristicConfig() {
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
