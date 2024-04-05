package mintychochip.mintychochip.horsepoop.api;

import mintychochip.mintychochip.horsepoop.container.BaseTrait;

public interface TraitCrosser<U extends TraitEnum> {
    /**
     *
     * @param father A baseTrait of the father
     * @param mother A baseTrait of the mother
     * @return Crossed child baseTrait
     */
    BaseTrait<U> crossTraits(BaseTrait<U> father, BaseTrait<U> mother);
}
