package mintychochip.mintychochip.horsepoop.container.attributes;

import mintychochip.mintychochip.horsepoop.container.Trait;

public enum GeneticAttribute implements Trait {
    CONSTITUTION("constitution"),
    SPEED("speed"),
    PARTICLE("particle"),
    STRENGTH("strength"),
    JUMP_STRENGTH("jumping-strength"),
    FIRE_RESISTANT("fire"), //lava and fire walking
    GLOW("glow"), //enum
    REGEN("regeneration"),
    ICE_WALKER("ice-walker"),
    SLOWFALL("slowfall"),
    INVISIBLE("unseen"),
    ATTRACTIVE("attractive"),
    EGG_LAYER("egg"),
    DESERTWALKING("desert-haste"),
    SILENT("silence"),
    ARMOR("base-armor");

    private final String key;

    GeneticAttribute(String key) {
        this.key = key;
    }

    @Override
    public String getNamespacedKey() {
        return "genetic:"+ key;
    }

    @Override
    public String getKey() {
        return key;
    }

}
