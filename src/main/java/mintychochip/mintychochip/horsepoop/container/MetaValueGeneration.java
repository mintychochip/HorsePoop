package mintychochip.mintychochip.horsepoop.container;

import mintychochip.mintychochip.horsepoop.metas.Meta;

public interface MetaValueGeneration<U extends Trait> {

    String generateValue (Meta<U> meta);
}
