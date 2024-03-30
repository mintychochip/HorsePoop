package mintychochip.mintychochip.horsepoop.factories;

import mintychochip.mintychochip.horsepoop.config.ConfigManager;
import mintychochip.mintychochip.horsepoop.container.AnimalGenome;
import org.bukkit.entity.EntityType;

public class GenomeFactory {
  private final GeneFactory geneFactory;
  private GenomeFactory(GeneFactory geneFactory) {
    this.geneFactory = geneFactory;
  }

  public static GenomeFactory createInstance(GeneFactory geneFactory) {
    return new GenomeFactory(geneFactory);
  }
  public AnimalGenome createInstance(EntityType entityType,
      GeneFactory geneFactory,
      GenomeFactory genomeFactory) {
    return AnimalGenome.createInstance(entityType, geneFactory);
  }
}
