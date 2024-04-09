package mintychochip.mintychochip.horsepoop.factories;

import java.util.Random;
import mintychochip.mintychochip.horsepoop.api.MetaValueGeneration;
import mintychochip.mintychochip.horsepoop.api.TraitEnum;
import mintychochip.mintychochip.horsepoop.metas.DoubleMeta;
import mintychochip.mintychochip.horsepoop.metas.Meta;

public class DoubleGeneration<U extends TraitEnum> implements MetaValueGeneration<U> {
    @Override
    public String generateValue(Meta<U> meta) {
        String value = null;
        if(meta instanceof DoubleMeta<U> dm) {
            double child = (dm.getMax() - dm.getMin()) * new Random().nextDouble() + dm.getMin();
            if(child > dm.getMax()) {
                child = dm.getMax();
            }
            if(child < dm.getMin()) {
                child = dm.getMin();
            }
            value = String.valueOf(child);
        }
        return value;
    }
}
