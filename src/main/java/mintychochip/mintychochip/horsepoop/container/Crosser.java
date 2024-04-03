package mintychochip.mintychochip.horsepoop.container;

import mintychochip.mintychochip.horsepoop.metas.Meta;

public interface Crosser<U extends Trait> {
    BaseTrait<U> crossTraits(BaseTrait<U> father, BaseTrait<U> mother);
}
