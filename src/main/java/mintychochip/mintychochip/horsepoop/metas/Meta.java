package mintychochip.mintychochip.horsepoop.metas;

import mintychochip.mintychochip.horsepoop.container.Trait;

public interface Meta<U extends Trait> {
    U getTrait();
}
