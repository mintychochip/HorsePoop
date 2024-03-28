package mintychochip.mintychochip.horsepoop.container.enums.attributes;

import mintychochip.mintychochip.horsepoop.container.Trait;

public enum GenericTrait implements Trait {
    YIELD("yield"),
    TEMPERANCE("temperance");
    private final String key;

    GenericTrait(String key) {
        this.key = key;
    }
    @Override
    public String getNamespacedKey() {
        return TraitType.GENERIC.getKey() + ":" + key;
    }

    @Override
    public String getKey() {
        return key;
    }
}
