package mintychochip.mintychochip.horsepoop.factories;

import mintychochip.genesis.Genesis;
import mintychochip.mintychochip.horsepoop.ConfigManager;
import mintychochip.mintychochip.horsepoop.container.AnimalGenome;
import mintychochip.mintychochip.horsepoop.container.Gene;
import mintychochip.mintychochip.horsepoop.container.Trait;
import org.bukkit.entity.EntityType;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class GenomeFactory {

    private final double defaultRecombinanceChance = 0.5; //if the config is less than 0

    private final int defaultMaxCount = 3; //if the config is less than 0
    private final GeneFactory geneFactory;

    private final ConfigManager configManager;

    private GenomeFactory(GeneFactory geneFactory, ConfigManager configManager) {
        this.geneFactory = geneFactory;
        this.configManager = configManager;
    }

    public static GenomeFactory createInstance(GeneFactory geneFactory, ConfigManager configManager) {
        return new GenomeFactory(geneFactory, configManager);
    }

    public AnimalGenome createGenome(EntityType entityType, int mutations) {
        return AnimalGenome.createInstance(geneFactory, entityType, mutations);
    }

    public AnimalGenome crossGenome(AnimalGenome father, AnimalGenome mother, EntityType entityType) {

        //this first step crosses all 'conserved locus'
        List<Gene> genes = father.getGenes().stream()
                .filter(Gene::isConserved)
                .map(gene -> this.generalizedCross(gene, mother.getGeneFromTrait(gene.getTrait()), entityType))
                .collect(Collectors.toList());

        //second step crosses all 'nonconserved'
        List<Gene> matchingNonConservedList = father.getGenes().stream().filter(gene ->
                !isTraitInList(genes, gene.getTrait())).filter(gene -> mother.getGeneFromTrait(gene.getTrait()) != null).toList();

        matchingNonConservedList.forEach(gene -> genes.add(this.generalizedCross(gene, mother.getGeneFromTrait(gene.getTrait()), entityType)));

        double recombinanceChance = configManager.getSettingsConfig().getRecombinanceChance();
        if(recombinanceChance < 0) {
            recombinanceChance = defaultRecombinanceChance;
        }
        int maxCount = configManager.getSettingsConfig().getRecombinanceMaxCount();
        if(maxCount < 0) {
            maxCount = defaultMaxCount;
        }
        //third step for all unique traits, we are given a chance to roll for recombinance
        int recombinanceCount = 0;

        for (Gene gene : uniqueGenes(father, mother)) {
            if (Genesis.RANDOM.nextDouble() <= recombinanceChance && recombinanceCount <= maxCount) {
                genes.add(geneFactory.crossGene(gene, geneFactory.createInstance(gene.getTrait(), entityType), entityType));
                recombinanceCount++;
            }
        }
        //fourth step accoutn for deletions

        return AnimalGenome.createInstance(geneFactory, entityType, genes, 3);
    }

    private Gene generalizedCross(Gene father, Gene mother, EntityType entityType) {
        return geneFactory.crossGene(father, mother, entityType);
    }

    private boolean isTraitInList(List<Gene> genes, Trait trait) {
        return genes.stream().anyMatch(x -> x.getTrait() == trait);
    }

    private Set<Gene> uniqueGenes(AnimalGenome father, AnimalGenome mother) {
        Map<Trait, Gene> combinedAttributes = new HashMap<>(father.getAttributes());
        combinedAttributes.putAll(mother.getAttributes());

        return combinedAttributes.entrySet().stream()
                .filter(entry -> {
                    Trait trait = entry.getKey();
                    Gene fatherGene = father.getGeneFromTrait(trait);
                    Gene motherGene = mother.getGeneFromTrait(trait);
                    return (fatherGene == null && motherGene != null) || (fatherGene != null && motherGene == null);
                })
                .map(Map.Entry::getValue)
                .collect(Collectors.toSet());
    }

}
