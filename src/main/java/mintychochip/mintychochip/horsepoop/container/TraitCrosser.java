package mintychochip.mintychochip.horsepoop.container;

import mintychochip.mintychochip.horsepoop.config.TraitMeta;

public interface TraitCrosser<T extends TraitMeta> {

    String crossTraitForValue(BaseTrait<T> one, BaseTrait<T> two);
}
