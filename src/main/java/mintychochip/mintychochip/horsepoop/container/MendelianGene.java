package mintychochip.mintychochip.horsepoop.container;

import mintychochip.genesis.Genesis;
import mintychochip.mintychochip.horsepoop.container.enums.MendelianAllele;

import java.util.Random;

public class MendelianGene { //could just do diploids
    private final MendelianAllele alleleA;

    private final MendelianAllele alleleB;
    private MendelianGene(MendelianAllele alleleA, MendelianAllele alleleB) {
        this.alleleA = alleleA;
        this.alleleB = alleleB;
    }
    public static MendelianGene createInstance(MendelianAllele alleleA, MendelianAllele alleleB) {
        return new MendelianGene(alleleA,alleleB);
    }

    public MendelianGene crossGenes(MendelianGene mendelianGene) { //can add some form of recombinance
        Random random = Genesis.RANDOM;
        MendelianAllele alleleA = random.nextBoolean() ? mendelianGene.alleleA : this.alleleA;
        MendelianAllele alleleB = random.nextBoolean() ? mendelianGene.alleleA : this.alleleB;
        return new MendelianGene(alleleA,alleleB);
    }
    public MendelianAllele getAlleleA() {
        return alleleA;
    }

    public MendelianAllele getAlleleB() {
        return alleleB;
    }

    @Override
    public String toString() {
        return alleleA + " | " + alleleB;
    }

}