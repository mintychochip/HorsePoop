package mintychochip.mintychochip.horsepoop.metas;

import mintychochip.mintychochip.horsepoop.container.Trait;
public class EnumMeta<U extends Trait> extends AbstractMeta<U> {

    protected String enumClass; //stores the string of the enum class

    public EnumMeta(U trait, String enumClass) {
        super(trait);
        this.enumClass = enumClass;
    }

    public String getEnumClass() {
        return enumClass;
    }
}
