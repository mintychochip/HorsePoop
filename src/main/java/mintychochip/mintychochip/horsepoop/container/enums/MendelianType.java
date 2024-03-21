package mintychochip.mintychochip.horsepoop.container.enums;

public enum MendelianType {
    MENDELIAN_RECESSIVE(0),
    MENDELIAN_DOMINANT(1),
    MENDELIAN_HETEROZYGOUS(2);

    private final int code;

    MendelianType(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
