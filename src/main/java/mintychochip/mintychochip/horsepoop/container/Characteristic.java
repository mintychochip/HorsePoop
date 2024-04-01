package mintychochip.mintychochip.horsepoop.container;

import mintychochip.mintychochip.horsepoop.config.TraitMeta;
import mintychochip.mintychochip.horsepoop.factories.GeneFactory;

public class Characteristic extends BaseTrait{
  private Characteristic(String value, String trait) {
    super(value, trait);
  }
  public static Characteristic createInstance(Trait trait, TraitMeta traitMeta, GeneFactory geneFactory) {
    if(traitMeta == null) {
      return null;
    }
    String traitString = geneFactory.getTraitFetcher().toJson(trait);
    String value = geneFactory.getValueFactory().generateValue(trait,traitMeta);

    if(traitString == null) {
      throw new RuntimeException("Trait String is null");
    }
    if(value == null) {
      throw new RuntimeException("Value is null");
    }
    return new Characteristic(value,traitString);
  }
}
