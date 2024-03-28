package mintychochip.mintychochip.horsepoop.config;

import mintychochip.genesis.config.abstraction.GenericConfig;
import mintychochip.genesis.config.abstraction.GenesisConfigurationSection;
import mintychochip.genesis.util.Rarity;
import org.bukkit.plugin.java.JavaPlugin;

public class SettingsConfig extends GenericConfig {

  public static class Marker {

    public static final String RECOMBINANCE_SETTINGS = "recombinance-settings";
    public static final String CHANCE = "chance";
    public static final String MAX_COUNT = "max-count";

    //For cow-related traits
    public static final String COW_TRAITS = "cow-traits";
    public static final String GOLDEN_MILK_CHANCE = "golden-milk-chance";

    public static final String STRAWBERRY_MILK_CHANCE = "strawberry-milk-chance";
    public static final String MALE_MILK = "male-milk";

    //For gene-printing format

    public static final String HEADER = "header";

    public static final String CHARACTER = "character";

    public static final String BODY = "body";

    public static final String CENTER = "centered";
  }

  public SettingsConfig(String path, JavaPlugin plugin) {
    super(path, plugin);
  }

  public String getString(GenesisConfigurationSection section, String string) {
    return section.getString(string);
  }
  public double getDouble(GenesisConfigurationSection section, String marker) {
    return section.getDouble(marker);
  }
  public double getRecombinanceChance() {
    return 0.5;
  }

  public int getRecombinanceMaxCount() {
    return 3;
  }

  // Cow-related functions

  public boolean getMaleMilked() {
    return this.getMainConfigurationSection(Marker.COW_TRAITS).getBoolean(Marker.MALE_MILK);
  }

  // Gene-format related functions
  public String getHeader() {
    return this.getMainConfigurationSection("gene-format").getString(Marker.HEADER);
  }

  public String getBody() {
    return this.getMainConfigurationSection("gene-format").getString(Marker.BODY);
  }

  public boolean getCentered() {
    return this.getMainConfigurationSection("gene-format").getBoolean(Marker.CENTER);
  }

  public String getCharacter() {
    return this.getMainConfigurationSection("gene-format").getString(Marker.CHARACTER);
  }

  public int getMutationsByRarity(Rarity rarity) {
    return this.getMainConfigurationSection("rarity.mutations").getInt(rarity.toPlainString());
  }
}
