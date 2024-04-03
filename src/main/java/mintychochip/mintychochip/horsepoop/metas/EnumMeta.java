package mintychochip.mintychochip.horsepoop.metas;

import mintychochip.mintychochip.horsepoop.container.Trait;
public class EnumMeta<U extends Trait> extends AbstractMeta<U> {

    String enumClass; //stores the string of the enum class

    protected EnumMeta(U trait, String enumClass) {
        super(trait);
        this.enumClass = enumClass;
    }
}
