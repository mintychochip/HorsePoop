package mintychochip.mintychochip.horsepoop.container;

import mintychochip.mintychochip.horsepoop.metas.Meta;

public interface Crosser<T extends Meta> {
    BaseTrait<T> crossTraits(BaseTrait<T> father, BaseTrait<T> mother);
}
