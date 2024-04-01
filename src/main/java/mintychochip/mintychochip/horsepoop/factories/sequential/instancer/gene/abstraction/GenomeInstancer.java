package mintychochip.mintychochip.horsepoop.factories.sequential.instancer.gene.abstraction;

import mintychochip.mintychochip.horsepoop.container.AnimalGenome;
import org.bukkit.entity.EntityType;

public interface GenomeInstancer {
  AnimalGenome instanceGenome(EntityType entityType);
  TraitInstancer getCharInstancer();
  TraitInstancer getGeneInstancer();
}
