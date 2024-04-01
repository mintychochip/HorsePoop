package mintychochip.mintychochip.horsepoop.container;

import com.google.gson.Gson;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TraitFetcher {

  private final Gson gson;

  public TraitFetcher(Gson gson) {
    this.gson = gson;
  }

  public GeneTrait getGeneTrait(String trait) {
    return gson.fromJson(trait, GeneTrait.class);
  }

  public MendelianGene getMendelian(Gene gene) {
    return this.getGeneTrait(gene.getTrait()).getValueType() == ValueType.MENDELIAN
        ? new Gson().fromJson(gene.getValue(), MendelianGene.class) : null;
  }

  public String toJson(Trait trait) {
    return gson.toJson(trait);
  }

  public boolean geneTraitInList(List<Gene> genes, GeneTrait geneTrait) {
    return this.getGeneFromGeneList(genes, geneTrait) != null;
  }

  public Gene getGeneFromGeneList(List<Gene> genes, GeneTrait geneTrait) {
    for (Gene gene : genes) {
      if (geneTrait == this.getGeneTrait(gene.getTrait())) {
        return gene;
      }
    }
    return null;
  }

  public Gene getGeneFromGeneList(AnimalGenome genome, GeneTrait geneTrait) {
    return this.getGeneFromGeneList(genome.getGenes(),geneTrait);
  }

  public double getNumericAttribute(List<Gene> genes, GeneTrait geneTrait) {
    if (geneTrait.getValueType() == ValueType.ENUM ||
        geneTrait.getValueType() == ValueType.MENDELIAN ||
        geneTrait.getValueType() == ValueType.WEIGHTED_ENUM) {
      return -1;
    }
    return new Gson().fromJson(this.getGeneFromGeneList(genes,geneTrait).getValue(), double.class);
  }

  public double getNumericAttribute(AnimalGenome genome, GeneTrait geneTrait) {
    return this.getNumericAttribute(genome.getGenes(),geneTrait);
  }
  public Map<GeneTrait, Gene> getAttributes(List<Gene> genes) {
    Map<GeneTrait, Gene> attributes = new HashMap<>();
    for (Gene gene : genes) {
      attributes.put(this.getGeneTrait(gene.getTrait()), gene);
    }
    return attributes;
  }
}
