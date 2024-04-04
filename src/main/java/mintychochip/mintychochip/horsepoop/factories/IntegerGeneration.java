package mintychochip.mintychochip.horsepoop.factories;

import mintychochip.mintychochip.horsepoop.container.MetaValueGeneration;
import mintychochip.mintychochip.horsepoop.container.Trait;
import mintychochip.mintychochip.horsepoop.metas.IntegerMeta;
import mintychochip.mintychochip.horsepoop.metas.Meta;

import java.util.Random;

public class IntegerGeneration<U extends Trait> implements MetaValueGeneration<U> {
    @Override
    public String generateValue(Meta<U> meta) {
        String value = null;
        if(meta instanceof IntegerMeta<U> im) {
            int child = new Random(System.currentTimeMillis()).nextInt(im.getMin(), im.getMax());
            if(child > im.getMax()) {
                child = im.getMax();
            }
            if(child < im.getMin()) {
                child = im.getMin();
            }
            value = String.valueOf(child);
        }
        return value;
    }
}