package mintychochip.mintychochip.horsepoop.container.enums;

import mintychochip.genesis.Genesis;

public enum MendelianAllele {
    RECESSIVE,
    DOMINANT;
    @Override
    public String toString() {
        return this == RECESSIVE ? "REC" : "DOM";
    }
}
