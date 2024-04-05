package mintychochip.mintychochip.horsepoop.api;

import mintychochip.mintychochip.horsepoop.metas.Meta;

public interface MetaValueGeneration<U extends TraitEnum> {

    String generateValue (Meta<U> meta);
}
