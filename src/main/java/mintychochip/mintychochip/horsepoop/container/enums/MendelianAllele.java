package mintychochip.mintychochip.horsepoop.container.enums;

public enum MendelianAllele {
    RECESSIVE,
    DOMINANT;
    @Override
    public String toString() {
        return this == RECESSIVE ? "REC" : "DOM";
    }
}
