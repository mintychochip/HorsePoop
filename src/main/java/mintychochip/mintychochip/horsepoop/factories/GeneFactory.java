package mintychochip.mintychochip.horsepoop.factories;

import com.google.gson.Gson;
import mintychochip.genesis.Genesis;
import mintychochip.mintychochip.horsepoop.HorseConfig;
import mintychochip.mintychochip.horsepoop.HorsePoop;
import mintychochip.mintychochip.horsepoop.container.Gene;
import mintychochip.mintychochip.horsepoop.container.MendelianGene;
import mintychochip.mintychochip.horsepoop.container.Trait;
import org.bukkit.entity.EntityType;

public class GeneFactory {
    private final HorseConfig horseConfig;

    private final Gson gson = HorsePoop.GSON;

    private GeneFactory(HorseConfig horseConfig) {
        this.horseConfig = horseConfig;
    }

    public static GeneFactory createInstance(HorseConfig horseConfig) {
        return new GeneFactory(horseConfig);
    }

    public Gene createInstance(Trait trait, EntityType entityType) {
        return Gene.createInstance(trait, entityType, horseConfig, this);
    }

    public HorseConfig getHorseConfig() {
        return horseConfig;
    }

    public Gene crossGene(Gene father, Gene mother, EntityType entityType) {
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
                    } else {
                        child = rollNumberVal(fatherVal,motherVal);
                    }
                    return Gene.createInstance(father.getTrait(), entityType, horseConfig, gson.toJson(child),this);
                }
                case ENUM -> {
                    //not gonna write it no enum classes yet
                }
                case MENDELIAN -> {
                    MendelianGene fatherGene = gson.fromJson(father.getValue(), MendelianGene.class);
                    MendelianGene motherGene = gson.fromJson(mother.getValue(), MendelianGene.class);
                    return Gene.createInstance(father.getTrait(), entityType, horseConfig, gson.toJson(fatherGene.crossGenes(motherGene)), this);
                }
                case INTEGER -> {
                    Integer fatherVal = gson.fromJson(father.getValue(), int.class);
                    Integer motherVal = gson.fromJson(mother.getValue(), int.class);
                    int child;
                    if(fatherVal.equals(motherVal)) {
                        child = fatherVal;
                    }
                    else {
                        child = rollNumberVal(fatherVal,motherVal);
                    }
                    return Gene.createInstance(father.getTrait(), entityType, horseConfig, gson.toJson(child),this);
                }
            }
        }
        return null;
    }
    @SuppressWarnings("unchecked")

    private <T extends Number> T rollNumberVal(T a, T b) { //this name and function sucks
        T min;
        T max;
        if(a instanceof Double && b instanceof Double) {
            min = (T) (Double) Math.min(a.doubleValue(),b.doubleValue());
            max = (T) (Double) Math.max(a.doubleValue(),b.doubleValue());
            return (T) (Double) Genesis.RANDOM.nextDouble(min.doubleValue(),max.doubleValue());
        } else if (a instanceof Integer || b instanceof Integer) {
            min = (T) (Integer) Math.min(a.intValue(), b.intValue());
            max = (T) (Integer) Math.max(a.intValue(),b.intValue());
            return (T) (Integer) Genesis.RANDOM.nextInt(min.intValue(),max.intValue());
        } else {
            throw new IllegalArgumentException("Unsupported type. Only double and int are supported.");
        }
    }
}