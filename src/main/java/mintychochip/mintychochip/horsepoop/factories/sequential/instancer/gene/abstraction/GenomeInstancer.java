package mintychochip.mintychochip.horsepoop.factories.sequential.instancer.gene.abstraction;

import mintychochip.mintychochip.horsepoop.config.CharacteristicTraitMeta;
import mintychochip.mintychochip.horsepoop.config.GeneTraitMeta;
import mintychochip.mintychochip.horsepoop.config.TraitMeta;
import mintychochip.mintychochip.horsepoop.container.AnimalGenome;
import org.bukkit.entity.EntityType;

public interface GenomeInstancer {
  AnimalGenome instanceGenome(EntityType entityType);
  TraitInstancer<CharacteristicTraitMeta> getCharInstancer();
  TraitInstancer<GeneTraitMeta> getGeneInstancer();
}
