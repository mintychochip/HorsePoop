package mintychochip.mintychochip.horsepoop.factories;

import mintychochip.mintychochip.horsepoop.container.MetaValueGeneration;
import mintychochip.mintychochip.horsepoop.container.Trait;
import mintychochip.mintychochip.horsepoop.metas.DoubleMeta;
import mintychochip.mintychochip.horsepoop.metas.Meta;

import java.util.Random;

public class DoubleGeneration<U extends Trait> implements MetaValueGeneration<U> {
    @Override
    public String generateValue(Meta<U> meta) {
        String value = null;
        if(meta instanceof DoubleMeta<U> dm) {
            double child = new Random().nextDouble(dm.getMin(), dm.getMax());
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
