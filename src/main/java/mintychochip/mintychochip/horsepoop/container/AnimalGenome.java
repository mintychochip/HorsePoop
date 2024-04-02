package mintychochip.mintychochip.horsepoop.container;

import com.google.gson.annotations.SerializedName;
import java.util.List;
import javax.annotation.Nullable;
import mintychochip.mintychochip.horsepoop.config.CharacteristicTraitMeta;
import mintychochip.mintychochip.horsepoop.config.GeneTraitMeta;
import mintychochip.mintychochip.horsepoop.container.TypeAdapters.Genome;
import mintychochip.mintychochip.horsepoop.factories.sequential.instancer.gene.abstraction.GenomeGenerator;

public class AnimalGenome implements Genome {

  @SerializedName("genes")
  private List<BaseTrait<GeneTraitMeta>> genes;
  @SerializedName("chars")
  private List<BaseTrait<CharacteristicTraitMeta>> chars;
  @SerializedName("time-alive")
  private long birthTime;
  @SerializedName("name")
  @Nullable
  private String name = null;

  private AnimalGenome(List<BaseTrait<GeneTraitMeta>> genes, List<BaseTrait<CharacteristicTraitMeta>> chars, long birthTime) {
    this.genes = genes;
    this.chars = chars;
    this.birthTime = birthTime;
  }

  public static AnimalGenome createInstance(List<BaseTrait<GeneTraitMeta>> genes, List<BaseTrait<CharacteristicTraitMeta>> chars, GenomeGenerator genomeGenerator) {
    if(genes == null || genes.isEmpty()) {
      return null;
    }
    if(chars == null || chars.isEmpty()) {
      return null;
    }
    return new AnimalGenome(genes,chars,System.currentTimeMillis());
  }
  public void setName(@Nullable String name) {
    this.name = name;
  }

  @Nullable
  public String getName() {
    return name;
  }

  @Override
  public List<BaseTrait<CharacteristicTraitMeta>> getChars() {
    return chars;
  }

  @Override
  public List<BaseTrait<GeneTraitMeta>> getGenes() {
    return genes;
  }

  public long getBirthTime() {
    return birthTime;
  }
}