package mintychochip.mintychochip.horsepoop.factories;

import mintychochip.mintychochip.horsepoop.config.ConfigManager;
import mintychochip.mintychochip.horsepoop.container.AnimalGenome;
import mintychochip.mintychochip.horsepoop.container.Gene;
import mintychochip.mintychochip.horsepoop.container.Trait;
import org.bukkit.entity.EntityType;

import java.util.List;

public class GenomeFactory {

  private final GeneFactory geneFactory;

  private final ConfigManager configManager;

  private GenomeFactory(GeneFactory geneFactory, ConfigManager configManager) {
    this.geneFactory = geneFactory;
    this.configManager = configManager;
  }

  public static GenomeFactory createInstance(GeneFactory geneFactory, ConfigManager configManager) {
    return new GenomeFactory(geneFactory, configManager);
  }
  public AnimalGenome createInstance(EntityType entityType, ConfigManager configManager,
      GeneFactory geneFactory,
      GenomeFactory genomeFactory) {
    return AnimalGenome.createInstance(entityType, configManager, geneFactory);
  }



}
