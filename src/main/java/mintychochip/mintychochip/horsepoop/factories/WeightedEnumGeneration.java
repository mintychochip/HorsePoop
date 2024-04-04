package mintychochip.mintychochip.horsepoop.factories;

import mintychochip.mintychochip.horsepoop.container.MetaValueGeneration;
import mintychochip.mintychochip.horsepoop.container.Trait;
import mintychochip.mintychochip.horsepoop.metas.Meta;
import mintychochip.mintychochip.horsepoop.metas.WeightedEnumMeta;

import java.util.List;

public class WeightedEnumGeneration<U extends Trait> implements MetaValueGeneration<U> {
    @Override
    public String generateValue(Meta<U> meta) {
        String value = null;
        if(meta instanceof WeightedEnumMeta<U> wem) {
            List<Double> weights = wem.getWeights();
            String enumClass = wem.getEnumClass();
            try {
                Class<?> aClass = Class.forName(enumClass);
                if(aClass inst)
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
