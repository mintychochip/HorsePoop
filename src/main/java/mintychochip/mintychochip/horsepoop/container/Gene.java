package mintychochip.mintychochip.horsepoop.container;

import com.google.gson.annotations.SerializedName;
import mintychochip.mintychochip.horsepoop.config.AnimalTraitWrapper;
import mintychochip.mintychochip.horsepoop.config.GeneTraitMeta;
import mintychochip.mintychochip.horsepoop.factories.GeneFactory;
import org.bukkit.entity.EntityType;

public class Gene extends BaseTrait {
  @SerializedName("conserved")
  private final boolean conserved;
  @SerializedName("crossable")
  private final boolean crossable;
  private Gene(String value, String trait, boolean conserved, boolean crossable) {
    super(value, trait);
    this.conserved = conserved;
    this.crossable = crossable;
  }
  public static Gene createInstance(String value, String trait, boolean conserved, boolean crossable, GeneFactory geneFactory) {
    return new Gene(value,trait,conserved,crossable);
  }
  public static Gene createInstance(GeneTrait geneTrait, EntityType entityType, GeneFactory geneFactory) { //move dis
    AnimalTraitWrapper geneticTrait = geneFactory.getConfigManager().getEntityConfig()
        .getGeneticTrait(geneTrait, entityType);
    String trait = geneFactory.getTraitFetcher().toJson(geneTrait);
    boolean crossable = false;
    boolean conserved = false;
    if (geneticTrait.meta() instanceof GeneTraitMeta gtm) {
      crossable = gtm.crossable();
      conserved = gtm.conserved();
    }
    String value = geneFactory.getValueFactory().generateValue(geneTrait, geneticTrait.meta());
    if(value == null) {
      return null;
    }
    return new Gene(value,trait,conserved,crossable);
  }
  public boolean isCrossable() {
    return crossable;
  }

  public boolean isConserved() {
    return conserved;
  }
}
