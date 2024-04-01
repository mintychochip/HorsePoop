package mintychochip.mintychochip.horsepoop.container.enums.attributes.specific;

import com.google.gson.Gson;
import mintychochip.mintychochip.horsepoop.container.AnimalGenome;
import mintychochip.mintychochip.horsepoop.container.GeneTrait;
import mintychochip.mintychochip.horsepoop.container.ValueType;
import mintychochip.mintychochip.horsepoop.container.enums.MendelianType;
import mintychochip.mintychochip.horsepoop.container.enums.attributes.TraitType;
import org.bukkit.DyeColor;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;

public enum SheepGeneTrait implements GeneTrait { //traits designed for sheep
  RED("red", ValueType.MENDELIAN,
      "Encodes for sheep phenotypic color, reference the wiki for the different color combinations."),
  BLUE("blue", ValueType.MENDELIAN,
      "Encodes for sheep phenotypic color, reference the wiki for the different color combinations."),
  GREEN("green", ValueType.MENDELIAN,
      "Encodes for sheep phenotypic color, reference the wiki for the different color combinations."),
  WHITE_OVERRIDE("override", ValueType.MENDELIAN,
      "Epistatic "), //dominant phenotypes always exhibit white, allows for masking of colors
  BRIGHTNESS("brightness", ValueType.INTEGER, "Provided 'override' is recessive, if there are no color genes, then the color will be selected on the brightness.");

  // numeric
  private final String key;

  private final ValueType valueType;
  private final String description;

  SheepGeneTrait(String key, ValueType valueType, String description) {
    this.key = key;
    this.valueType = valueType;
    this.description = description;
  }
  @Override
  public String getNamespacedKey() {
    return TraitType.SHEEP.getKey() + ":" + key;
  }

  @Override
  public String getKey() {
    return key;
  }

  @Override
  public String getShortDescription() {
    return description;
  }

  @Override
  public ValueType getValueType() {
    return valueType;
  }
}
