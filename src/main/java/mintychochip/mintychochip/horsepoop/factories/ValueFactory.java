package mintychochip.mintychochip.horsepoop.factories;

import com.google.gson.Gson;
import java.util.Random;
import mintychochip.genesis.util.Rarity;
import mintychochip.mintychochip.horsepoop.config.GeneTraitMeta;
import mintychochip.mintychochip.horsepoop.config.TraitMeta;
import mintychochip.mintychochip.horsepoop.container.MendelianGene;
import mintychochip.mintychochip.horsepoop.container.Trait;
import mintychochip.mintychochip.horsepoop.container.ValueType;
import mintychochip.mintychochip.horsepoop.container.enums.FeatherColor;
import mintychochip.mintychochip.horsepoop.container.enums.Gender;
import mintychochip.mintychochip.horsepoop.container.enums.MendelianAllele;
import mintychochip.mintychochip.horsepoop.container.enums.attributes.GenericGeneTrait;
import mintychochip.mintychochip.horsepoop.container.enums.attributes.specific.GeneticAttribute;
import mintychochip.mintychochip.horsepoop.container.enums.characteristics.GenericCharacteristicTrait;
import org.bukkit.Bukkit;
import org.bukkit.Particle;

public class ValueFactory {

  private Gson gson = new Gson();

  private Random random = new Random(System.currentTimeMillis());

  public ValueFactory() {

  }

  public String generateValue(Trait trait, TraitMeta traitMeta) {
    ValueType valueType = trait.getValueType();
    double maximum = traitMeta.maximum();
    double minimum = traitMeta.minimum();

    if (valueType == ValueType.MENDELIAN && traitMeta instanceof GeneTraitMeta geneTraitMeta) {
      return mendelianCase(geneTraitMeta);
    }

    return switch (valueType) {
      case NUMERIC -> numericCase(maximum, minimum);
      case INTEGER -> numericCase((int) maximum, (int) minimum);
      case ENUM -> enumCase(trait);
      default -> null;
    };
  }

  private String mendelianCase(GeneTraitMeta meta) {
    return gson.toJson(
        MendelianGene.createInstance(MendelianAllele.createAllele(meta.chance()),
            MendelianAllele.createAllele(meta.chance())));
  }

  private String numericCase(double maximum, double minimum) {
    double child = random.nextDouble(minimum, maximum);
    if (child > maximum) {
      child = maximum;
    }
    return gson.toJson(child);
  }

  private String enumCase(Trait trait) {
    if (trait == GeneticAttribute.PARTICLE) {
      return randomEnumVal(Particle.values(), random);
    } else if (trait == GenericGeneTrait.FEATHER_COLOR) {
      return randomEnumVal(FeatherColor.values(), random);
    } else if (trait == GenericCharacteristicTrait.RARITY) {
      Bukkit.broadcastMessage("here");
      return randomEnumVal(Rarity.values(), random);
    } else if (trait == GenericCharacteristicTrait.GENDER) {
      return randomEnumVal(Gender.values(), random);
    }
    return null;
  }

  private static <T extends Enum<?>> String randomEnumVal(T[] values, Random random) {
    int i = random.nextInt(values.length);
    return values[i].toString();
  }
}
