package mintychochip.mintychochip.horsepoop.factories;

import mintychochip.mintychochip.horsepoop.api.MetaValueGeneration;
import mintychochip.mintychochip.horsepoop.api.TraitEnum;
import mintychochip.mintychochip.horsepoop.metas.IntegerMeta;
import mintychochip.mintychochip.horsepoop.metas.Meta;

import java.util.Random;
import org.bukkit.Bukkit;

public class IntegerGeneration<U extends TraitEnum> implements MetaValueGeneration<U> {
    @Override
    public String generateValue(Meta<U> meta) {
        String value = null;
        if(meta instanceof IntegerMeta<U> im) {
            Random random = new Random();
            int child = random.nextInt(im.getMax() - im.getMin() + 1) + im.getMin();
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
