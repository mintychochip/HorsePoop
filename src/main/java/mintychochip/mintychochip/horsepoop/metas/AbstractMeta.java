package mintychochip.mintychochip.horsepoop.metas;

import mintychochip.mintychochip.horsepoop.container.Trait;

abstract class AbstractMeta<U extends Trait> implements Meta<U> {
    protected U trait;

    protected AbstractMeta(U trait) {
        this.trait = trait;
    }

    @Override
    public U getTrait() {
        return trait;
    }
}
