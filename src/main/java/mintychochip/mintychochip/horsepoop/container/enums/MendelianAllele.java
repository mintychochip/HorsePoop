package mintychochip.mintychochip.horsepoop.container.enums;

import mintychochip.genesis.Genesis;

public enum MendelianAllele {
    RECESSIVE,
    DOMINANT;
    public static MendelianAllele createAllele(double chance) {
        return Genesis.RANDOM.nextDouble() > chance ? MendelianAllele.DOMINANT : MendelianAllele.RECESSIVE;
    }
}
