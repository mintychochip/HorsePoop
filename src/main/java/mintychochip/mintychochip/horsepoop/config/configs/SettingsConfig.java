package mintychochip.mintychochip.horsepoop.config.configs;

import java.util.List;
import java.util.Random;
import mintychochip.genesis.config.abstraction.GenericConfig;
import mintychochip.genesis.config.abstraction.GenesisConfigurationSection;
import mintychochip.genesis.util.Rarity;
import mintychochip.mintychochip.horsepoop.container.AnimalGenome.Gender;
import org.bukkit.plugin.java.JavaPlugin;

public class SettingsConfig extends GenericConfig {

  public static class Marker {

    public static final String RECOMBINANCE_SETTINGS = "recombinance-settings";
    public static final String CHANCE = "chance";
    public static final String MAX_COUNT = "max-count";

    //For cow-related traits
    public static final String COW_TRAITS = "cow-traits";
    public static final String GOLDEN_WEIGHT = "golden-milk-weight";

    public static final String STRAWBERRY_WEIGHT = "strawberry-milk-weight";
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
  public double getRecombinanceChance() {
    return 0.5;
  }

  public int getRecombinanceMaxCount() {
    return 3;
  }

  // Cow-related functions

  public boolean getMaleMilked() {
    return this.getBoolean(Marker.MALE_MILK);
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
    return this.getMainConfigurationSection("mutations").getInt(rarity.toPlainString());
  }
  public double getMultiplierByRarity(Rarity rarity) {
    return this.getMainConfigurationSection("multipliers").getDouble(rarity.toPlainString());
  }
  public String getRandomHorseName(Gender gender, Random random) {
    String defaultName = "pls";
    GenesisConfigurationSection names = this.getMainConfigurationSection("random-names");

    if (names.isNull()) return defaultName;

    String genderKey = (gender == Gender.FEMALE) ? "female" : "male";
    List<String> stringList = names.getStringList(genderKey);
    if(stringList == null) {
      return defaultName;
    }
    String name = stringList.isEmpty() ? defaultName : stringList.get(random.nextInt(stringList.size()));
    return (name == null) ? defaultName : name;
  }
}
