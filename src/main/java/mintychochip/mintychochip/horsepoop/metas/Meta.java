package mintychochip.mintychochip.horsepoop.metas;

import mintychochip.mintychochip.horsepoop.api.TraitEnum;

public interface Meta<U extends TraitEnum> {
    U getTrait();

    boolean isConserved();

    void setConserved(boolean conserved);
}
