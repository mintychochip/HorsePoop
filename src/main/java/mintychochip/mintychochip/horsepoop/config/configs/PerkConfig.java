package mintychochip.mintychochip.horsepoop.config.configs;

import java.security.Key;
import java.util.function.Consumer;
import mintychochip.genesis.config.abstraction.GenericConfig;
import mintychochip.genesis.config.abstraction.GenesisConfigurationSection;
import mintychochip.mintychochip.horsepoop.api.TraitEnum;
import mintychochip.mintychochip.horsepoop.util.Pair;
import org.bukkit.plugin.java.JavaPlugin;

public class PerkConfig extends GenericConfig {

  public PerkConfig(String path, JavaPlugin plugin) {
    super(path, plugin);
  }

  interface Keyable {

    String getKey();
  }

  public enum Code implements Keyable {

    FALLING ("falling");

    private final String key;

    Code (String key) {
      this.key = key;
    }
    @Override
    public String getKey() {
      return key;
    }
  }

  public enum Level implements Keyable {
    HIGH("high"),
    MEDIUM("medium"),
    LOW("low");

    private final String key;

    Level(String key) {
      this.key = key;
    }
    @Override
    public String getKey() {
      return key;
    }
  }

  public Pair<Double, Double> getNumericThreshold(Level level, Code code) {
    GenesisConfigurationSection mainConfigurationSection = getMainConfigurationSection(
        code.getKey() + "." + level.getKey());
    return new Pair<>(mainConfigurationSection.getDouble("threshold"),
        mainConfigurationSection.getDouble("reduction"));
  }

}
