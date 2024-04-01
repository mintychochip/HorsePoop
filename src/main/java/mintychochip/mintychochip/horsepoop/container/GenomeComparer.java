//package mintychochip.mintychochip.horsepoop.container;
//
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.Set;
//import java.util.stream.Collectors;
//
//public class GenomeComparer {
//  private final TraitFetcher traitFetcher;
//  public GenomeComparer(TraitFetcher traitFetcher) {
//    this.traitFetcher = traitFetcher;
//  }
//  public Set<Gene> uniqueGenes(AnimalGenome father, AnimalGenome mother) {
//    List<Gene> fatherGenes = father.getGenes();
//    List<Gene> motherGenes = mother.getGenes();
//
//    Map<GeneTrait, Gene> combinedAttributes = new HashMap<>();
//
//    combinedAttributes.putAll(traitFetcher.getAttributes(fatherGenes));
//    combinedAttributes.putAll(traitFetcher.getAttributes(motherGenes));
//
//    return combinedAttributes.entrySet().stream()
//        .filter(entry -> {
//          GeneTrait geneTrait = entry.getKey();
//          Gene fatherGene = traitFetcher.getGeneFromGeneList(fatherGenes, geneTrait);
//          Gene motherGene = traitFetcher.getGeneFromGeneList(motherGenes, geneTrait);
//          return (fatherGene == null && motherGene != null) || (fatherGene != null && motherGene == null);
//        })
//        .map(Map.Entry::getValue)
//        .collect(Collectors.toSet());
//  }
//
//}
