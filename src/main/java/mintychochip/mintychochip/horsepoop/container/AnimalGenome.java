package mintychochip.mintychochip.horsepoop.container;

import com.google.gson.annotations.SerializedName;
import java.util.List;
import javax.annotation.Nullable;

import mintychochip.mintychochip.horsepoop.container.TypeAdapters.Genome;
import mintychochip.mintychochip.horsepoop.factories.sequential.crosser.abstraction.GenomeCrosser;
import mintychochip.mintychochip.horsepoop.factories.sequential.instancer.gene.abstraction.GenomeGenerator;

public class AnimalGenome implements Genome {

  @SerializedName("genes")
  private List<BaseTrait<GeneTrait>> genes;
  @SerializedName("chars")
  private List<BaseTrait<CharacteristicTrait>> chars;
  @SerializedName("intrinsics")
  private List<BaseTrait<Intrinsic>> intrinsics;
  @SerializedName("time-alive")
  private long birthTime;
  @SerializedName("name")
  @Nullable
  private String name = null;

  private AnimalGenome(List<BaseTrait<GeneTrait>> genes, List<BaseTrait<CharacteristicTrait>> chars, long birthTime) {
    this.genes = genes;
    this.chars = chars;
    this.birthTime = birthTime;
  }

  public static AnimalGenome createInstance(List<BaseTrait<GeneTrait>> genes, List<BaseTrait<CharacteristicTrait>> chars, GenomeGenerator generator) {
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
  public List<BaseTrait<GeneTrait>> getGenes() {
    return genes;
  }

  @Override
  public List<BaseTrait<CharacteristicTrait>> getChars() {
    return chars;
  }

  public long getBirthTime() {
    return birthTime;
  }
}