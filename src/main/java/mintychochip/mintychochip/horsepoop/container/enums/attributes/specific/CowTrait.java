package mintychochip.mintychochip.horsepoop.container.enums.attributes.specific;

import mintychochip.mintychochip.horsepoop.container.Trait;
import mintychochip.mintychochip.horsepoop.container.enums.attributes.TraitType;

public enum CowTrait implements Trait {

    MOOSHROOM_GENE("mooshroom"),
    STRAWBERRY_MILK("strawberry-milk"); //gives a different item if it has the gene
    private final String key;

    CowTrait(String key) {
        this.key = key;
    }
    @Override
    public String getNamespacedKey() {
        return TraitType.COW.getKey() + ":" + this.key;
    }

    @Override
    public String getKey() {
        return key;
    }
}
