package mintychochip.mintychochip.horsepoop.container.attributes;

import mintychochip.mintychochip.horsepoop.container.Trait;

public enum SheepAttribute implements Trait {
    RED("red"),
    BLUE("blue"),
    GREEN("green"),
    WHITE_OVERRIDE("override"), //dominant phenotypes always exhibit white, allows for masking of colors
    BRIGHTNESS("brightness"),
    ; // numeric

    private final String key;

    SheepAttribute(String key) {
        this.key = key;
    }
    @Override
    public String getNamespacedKey() {
        return "sheep:" + key;
    }

    @Override
    public String getKey() {
        return key;
    }
}
