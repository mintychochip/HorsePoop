package mintychochip.mintychochip.horsepoop.factories;

import com.google.gson.Gson;
import java.util.Random;
import mintychochip.genesis.Genesis;
import mintychochip.genesis.config.abstraction.GenesisConfigurationSection;
import mintychochip.genesis.util.Rarity;
import mintychochip.mintychochip.horsepoop.HorseMarker;
import mintychochip.mintychochip.horsepoop.config.AnimalTraitMeta;
import mintychochip.mintychochip.horsepoop.config.AnimalTraitWrapper;
import mintychochip.mintychochip.horsepoop.config.ConfigManager;
import mintychochip.mintychochip.horsepoop.config.configs.EntityConfig;
import mintychochip.mintychochip.horsepoop.HorsePoop;
import mintychochip.mintychochip.horsepoop.container.Gene;
import mintychochip.mintychochip.horsepoop.container.Gene.GeneType;
import mintychochip.mintychochip.horsepoop.container.MendelianGene;
import mintychochip.mintychochip.horsepoop.container.Trait;
import mintychochip.mintychochip.horsepoop.container.enums.MendelianAllele;
import mintychochip.mintychochip.horsepoop.container.enums.attributes.specific.GeneticAttribute;
import org.bukkit.Bukkit;
import org.bukkit.Particle;
import org.bukkit.entity.EntityType;
import org.jetbrains.annotations.NotNull;

public class GeneFactory {

  private final ConfigManager configManager;

  private final Gson gson = HorsePoop.GSON;

  private GeneFactory(ConfigManager configManager) {
    this.configManager = configManager;
  }

  public static GeneFactory createInstance(ConfigManager configManager) {
    return new GeneFactory(configManager);
  }

  public Gene createInstance(@NotNull Trait trait, @NotNull String value, boolean conserved,
      boolean crossable) {
    return Gene.createInstance(trait, value, conserved, crossable, this);
  }

  public Gene createInstance(@NotNull Trait trait, @NotNull EntityType entityType,
      @NotNull Rarity rarity) {
    return Gene.createInstance(trait, entityType, rarity, this);
  }

  public Gene createInstance(@NotNull AnimalTraitWrapper animalTraitWrapper,
      @NotNull EntityType entityType, @NotNull Rarity rarity) {
    return Gene.createInstance(
        configManager.getEntityConfig().getTraitFromWrapper(animalTraitWrapper), entityType, rarity,
        this);
  }

  public EntityConfig getHorseConfig() {
    return configManager.getEntityConfig();
  }


  public ConfigManager getConfigManager() {
    return configManager;
  }

  public static String generateValue(AnimalTraitWrapper animalTraitWrapper,
      ConfigManager configManager, Rarity rarity) {
    Gson gson = HorsePoop.GSON;
    Random random = Genesis.RANDOM;
    Trait trait = configManager.getEntityConfig().getTraitFromWrapper(animalTraitWrapper);
    double mul = configManager.getSettingsConfig().getMultiplierByRarity(rarity);
    AnimalTraitMeta meta = animalTraitWrapper.meta();
    String value = null;
    switch (trait.getGeneType()) {
      case NUMERIC -> {
        double minimum = meta.getMinimum();
        double maximum = meta.getMaximum();
        double child = mul * random.nextDouble(minimum, maximum);
        if (child > maximum) {
          child = maximum;
        }
        value = gson.toJson(child);
      }
      case MENDELIAN -> { //when spawning, dominant/recessive is random
        double chance = meta.getChance();
        value = gson.toJson(
            MendelianGene.createInstance(MendelianAllele.createAllele(chance),
                MendelianAllele.createAllele(chance)));
      }
      case ENUM -> {
        if (trait == GeneticAttribute.PARTICLE) {
          Particle[] values = Particle.values();
          int i = random.nextInt(0, values.length);
          value = values[i].toString();
        }
      }
      case INTEGER -> {
        int minimum = (int) meta.getMinimum();
        int maximum = (int) meta.getMaximum();

        int child = (int) mul * random.nextInt(minimum, maximum);
        if (child > maximum) {
          child = maximum;
        }
        value = gson.toJson(child);
      }
    }
    return value;
  }
}