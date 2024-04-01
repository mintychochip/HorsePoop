package mintychochip.mintychochip.horsepoop.container;

import mintychochip.mintychochip.horsepoop.config.TraitMeta;

public interface TraitValueGenerator {

    String generateValue(Trait trait, TraitMeta traitMeta);
}
