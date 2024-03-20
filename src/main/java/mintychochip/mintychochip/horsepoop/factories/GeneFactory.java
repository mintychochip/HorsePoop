package mintychochip.mintychochip.horsepoop.factories;

import com.google.gson.Gson;
import mintychochip.genesis.Genesis;
import mintychochip.mintychochip.horsepoop.HorseConfig;
import mintychochip.mintychochip.horsepoop.HorsePoop;
import mintychochip.mintychochip.horsepoop.container.Gene;
import mintychochip.mintychochip.horsepoop.container.MendelianGene;
import mintychochip.mintychochip.horsepoop.container.Trait;
import mintychochip.mintychochip.horsepoop.container.attributes.GeneticAttribute;

public class GeneFactory {
    private final HorseConfig horseConfig;

    private final Gson gson = HorsePoop.GSON;

    private GeneFactory(HorseConfig horseConfig) {
        this.horseConfig = horseConfig;
    }

    public static GeneFactory createInstance(HorseConfig horseConfig) {
        return new GeneFactory(horseConfig);
    }

    public Gene createInstance(Trait trait) {
        return Gene.createInstance(trait, horseConfig, this);
    }

    public HorseConfig getHorseConfig() {
        return horseConfig;
    }

    public Gene crossGene(Gene father, Gene mother) {
        Gene.GeneType motherGeneType = mother.getGeneType();
        if (father.getGeneType() != motherGeneType) {
            return null;
        }
        if (father.getTrait() != mother.getTrait()) {
            return null;
        }
        if(father.isCrossable() && mother.isCrossable()) {
            switch (motherGeneType) {
                case NUMERIC -> {
                    Double fatherVal = gson.fromJson(father.getValue(), double.class);
                    Double motherVal = gson.fromJson(mother.getValue(), double.class);
                    double child;
                    if (fatherVal.equals(motherVal)) {
                        child = fatherVal;
                    } else if (fatherVal > motherVal) {
                        child = Genesis.RANDOM.nextDouble(motherVal,fatherVal);
                    } else {
                        child = Genesis.RANDOM.nextDouble(fatherVal,motherVal);
                    }
                    return Gene.createInstance(father.getTrait(), horseConfig, gson.toJson(child),this);
                }
                case ENUM -> {
                    //not gonna write it no enum classes yet
                }
                case MENDELIAN -> {
                    MendelianGene fatherGene = gson.fromJson(father.getValue(), MendelianGene.class);
                    MendelianGene motherGene = gson.fromJson(mother.getValue(), MendelianGene.class);
                    return Gene.createInstance(father.getTrait(), horseConfig, gson.toJson(fatherGene.crossGenes(motherGene)), this);
                }
            }
        }
        return null;
    }
}