package mintychochip.mintychochip.horsepoop.factories;

import java.util.Random;
import mintychochip.mintychochip.horsepoop.api.MetaValueGeneration;
import mintychochip.mintychochip.horsepoop.api.TraitEnum;
import mintychochip.mintychochip.horsepoop.metas.DoubleMeta;
import mintychochip.mintychochip.horsepoop.metas.IntegerMeta;
import mintychochip.mintychochip.horsepoop.metas.Meta;

public final class NumericGeneration<U extends TraitEnum> implements MetaValueGeneration<U> {
    @Override
    public String generateValue(Meta<U> meta) {
        String value = null;
        if (meta instanceof DoubleMeta<U> dm) {
            value = this.generateDouble(dm.getMax(),dm.getMin());
        }
        if (meta instanceof IntegerMeta<U> im) {
            value = this.generateInt((int) im.getMax(),(int) im.getMin());
        }
        return value;
    }

    private String generateDouble(double max, double min) {
        Random random = new Random();

        double child = (max - min) * random.nextDouble() + min;
        if(child > max) {
            child = max;
        }
        if(child < min) {
            child = min;
        }
        return String.valueOf(child);
    }

    private String generateInt(int max, int min) {
        int child = new Random().nextInt(max - min + 1) + min;
        if(child > max) {
            child = max;
        }
        if(child < min) {
            child = min;
        }
        return String.valueOf(child);
    }
}
