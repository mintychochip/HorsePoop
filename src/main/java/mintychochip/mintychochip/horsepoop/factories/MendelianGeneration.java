package mintychochip.mintychochip.horsepoop.factories;

import com.google.gson.Gson;
import mintychochip.mintychochip.horsepoop.container.MendelianGene;
import mintychochip.mintychochip.horsepoop.container.MetaValueGeneration;
import mintychochip.mintychochip.horsepoop.container.Trait;
import mintychochip.mintychochip.horsepoop.container.enums.MendelianAllele;
import mintychochip.mintychochip.horsepoop.metas.MendelianMeta;
import mintychochip.mintychochip.horsepoop.metas.Meta;

import java.util.Random;

public class MendelianGeneration<U extends Trait> implements MetaValueGeneration<U> {

    @Override
    public String generateValue(Meta<U> meta) {
        String gene = null;
        if(meta instanceof MendelianMeta<U> mm) {
            double chance = mm.getChance();
            gene = new Gson().toJson(new MendelianGene(this.createAllele(chance),this.createAllele(chance)));
        }
        return new Gson().toJson(gene);
    }

    private MendelianAllele createAllele(double chance) { //higher chance means higher for r
        Random random = new Random();
        return random.nextDouble() <= chance ? MendelianAllele.DOMINANT : MendelianAllele.RECESSIVE;
    }
}
