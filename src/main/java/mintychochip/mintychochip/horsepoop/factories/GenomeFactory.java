package mintychochip.mintychochip.horsepoop.factories;

import mintychochip.mintychochip.horsepoop.container.AnimalGenome;
import mintychochip.mintychochip.horsepoop.container.Gene;
import mintychochip.mintychochip.horsepoop.container.Trait;
import org.bukkit.Bukkit;
import org.bukkit.entity.EntityType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GenomeFactory {

    private final GeneFactory geneFactory;

    private GenomeFactory(GeneFactory geneFactory) {
        this.geneFactory = geneFactory;
    }

    public static GenomeFactory createInstance(GeneFactory geneFactory) {
        return new GenomeFactory(geneFactory);
    }

    public AnimalGenome createGenome(EntityType entityType, int mutations) {
        return AnimalGenome.createInstance(geneFactory, entityType, mutations);
    }

    public AnimalGenome crossGenome(AnimalGenome father, AnimalGenome mother, EntityType entityType) {
        List<Gene> genes = new ArrayList<>();
        Map<Trait, Gene> attributes = new HashMap<>();
        Map<Trait, Gene> fatherAttributes = father.getAttributes();
        for (Gene value : fatherAttributes.values()) { //first set of gene crossing, we cross the conserved genes
            if (value.isConserved()) {
                Trait trait = value.getTrait();
                Gene genericAttribute = mother.getGeneFromTrait(trait);
                Gene gene = geneFactory.crossGene(value, genericAttribute, entityType);
                attributes.put(trait,gene);
                genes.add(gene);
            }
        }
        for (Gene value : fatherAttributes.values()) {
            if (!attributes.containsKey(value.getTrait())) {
                if (mother.getGeneFromTrait(value.getTrait()) != null) {
                    Gene genericAttribute = mother.getGeneFromTrait(value.getTrait());
                    Gene gene = geneFactory.crossGene(value, genericAttribute, entityType);
                    attributes.put(value.getTrait(), gene);
                    genes.add(gene);
                }
            }
        }
        Bukkit.broadcastMessage(genes.toString());
        return AnimalGenome.createInstance(geneFactory,entityType, genes,3);
    }
}
