package mintychochip.mintychochip.horsepoop.config;

public record GeneTraitMeta(boolean conserved, boolean crossable, double chance, double maximum,
                            double minimum, String type) implements TraitMeta {

}
