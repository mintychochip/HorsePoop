package mintychochip.mintychochip.horsepoop.factories;

import mintychochip.mintychochip.horsepoop.container.AnimalGenome;
import mintychochip.mintychochip.horsepoop.container.Gene;
import mintychochip.mintychochip.horsepoop.container.Trait;
import mintychochip.mintychochip.horsepoop.container.attributes.GeneticAttribute;
import org.bukkit.Bukkit;

import java.util.HashMap;
import java.util.Map;

public class GenomeFactory {

    private final GeneFactory geneFactory;
    private GenomeFactory(GeneFactory geneFactory) {
        this.geneFactory = geneFactory;
    }

    public static GenomeFactory createInstance(GeneFactory geneFactory) {
        return new GenomeFactory(geneFactory);
    }
    public AnimalGenome createGenome(int mutations) {
        return AnimalGenome.createInstance(geneFactory,mutations);
    }

    public AnimalGenome crossGenome(AnimalGenome father, AnimalGenome mother) {
        Map<Trait, Gene> attributes = new HashMap<>();

//        for (Gene value : fatherAttributes.values()) { //first set of gene crossing, we cross the conserved genes
//            if(value.isConserved()) {
//                GeneticAttribute geneticAttribute = value.getGeneticAttribute();
//                Gene genericAttribute = mother.getGenericAttribute(geneticAttribute);
//                attributes.put(geneticAttribute,geneFactory.crossGene(value,genericAttribute));
//            }
//        }
//        Bukkit.broadcastMessage(attributes.toString());
//        for (Gene value : fatherAttributes.values()) {
//            if(!attributes.containsKey(value.getGeneticAttribute())) {
//                if(mother.getGenericAttribute(value.getGeneticAttribute()) != null) {
//                    Gene genericAttribute = mother.getGenericAttribute(value.getGeneticAttribute());
//                    Gene gene = geneFactory.crossGene(value, genericAttribute);
//                    attributes.put(value.getGeneticAttribute(),gene);
//                }
//            }
//        }
        return null;
    }
}
