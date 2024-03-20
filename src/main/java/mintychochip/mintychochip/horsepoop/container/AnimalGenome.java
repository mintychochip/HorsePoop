package mintychochip.mintychochip.horsepoop.container;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import mintychochip.genesis.Genesis;
import mintychochip.mintychochip.horsepoop.HorseConfig;
import mintychochip.mintychochip.horsepoop.UnitConversions;
import mintychochip.mintychochip.horsepoop.container.attributes.GeneticAttribute;
import mintychochip.mintychochip.horsepoop.factories.GeneFactory;
import org.bukkit.Bukkit;
import org.bukkit.Particle;

import java.util.*;

public class AnimalGenome {
    private enum Gender {
        MALE,
        FEMALE
    }
    @SerializedName("genes")
    private List<Gene> genes = new ArrayList<>();
    @SerializedName("gender")
    private Gender gender;
    @SerializedName("time-alive")
    private long timeInstanced = System.currentTimeMillis();
    @SerializedName("mutations")
    private int mutations;

    private AnimalGenome(GeneFactory geneFactory, int mutations) {
        Random random = Genesis.RANDOM;
        this.gender = random.nextBoolean() ? Gender.MALE : Gender.FEMALE;
        this.mutations = mutations;
        HorseConfig horseConfig = geneFactory.getHorseConfig();
        List<Trait> enabledAttributes = horseConfig.getEnabledAttributes();
        for (Trait enabledAttribute : enabledAttributes) {
             if (horseConfig.getConserved(enabledAttribute)) {
                genes.add(geneFactory.createInstance(enabledAttribute));
            }
        }
        for (int i = 0; i < this.mutations; i++) {
            int index = random.nextInt(0, enabledAttributes.size());
            Trait value = enabledAttributes.get(index);
            if(!isInGenes(value)) {
                genes.add(geneFactory.createInstance(value));
            }
        }
        Bukkit.broadcastMessage(genes.toString());
    }
    private boolean isInGenes(Trait trait) {
        for (Gene gene : genes) {
            if(trait == gene.getTrait()) {
                return true;
            }
        }
        return false;
    }

    private AnimalGenome(GeneFactory geneFactory, List<Gene> genes, int mutations) {
        Random random = Genesis.RANDOM;
        this.gender = random.nextBoolean() ? Gender.MALE : Gender.FEMALE;
        this.mutations = mutations;
        this.genes = genes;
    }

    public static AnimalGenome createInstance(GeneFactory geneFactory, int mutations) {
        return new AnimalGenome(geneFactory, mutations);
    }

    public static AnimalGenome createInstance(GeneFactory geneFactory, List<Gene> genes, int mutations) {
        return new AnimalGenome(geneFactory, genes, mutations);
    }

    public List<Gene> getGenes() {
        return genes;
    }

    public int getMutations() {
        return mutations;
    }

    public double calculateRemainingHours() {
        double numericAttribute = getNumericAttribute(GeneticAttribute.CONSTITUTION); //this is in millis
        double v = numericAttribute / 1000; //seconds
        return v;
    }

    public double calculateRemainingDays() {
        double millis = getNumericAttribute(GeneticAttribute.CONSTITUTION);
        return millis * UnitConversions.SECONDS_FROM_MILLIS * UnitConversions.MIN_FROM_SECONDS * UnitConversions.HOURS_FROM_MIN;
    }

    public double getNumericAttribute(GeneticAttribute geneticAttribute) {
        if (geneticAttribute == GeneticAttribute.PARTICLE) {
            return -1;
        }
        return new Gson().fromJson(this.getGeneFromTrait(geneticAttribute).getValue(), double.class);
    }
    public Gene getGeneFromTrait(Trait trait) {
        for (Gene gene : genes) {
            if(trait == gene.getTrait()) {
                return gene;
            }
        }
        return null;
    }

    public Gender getGender() {
        return gender;
    }

    public Particle getParticle() {
        if (!isInGenes(GeneticAttribute.PARTICLE)) {
            return null;
        }
        return Particle.valueOf(this.getGeneFromTrait(GeneticAttribute.PARTICLE).getValue());
    }

    public double getSpeed() {
        String value = this.getGeneFromTrait(GeneticAttribute.SPEED).getValue();
        Gson gson = new Gson();
        return gson.fromJson(value, double.class);
    }

    public double getConstitution() {
        String value = this.getGeneFromTrait(GeneticAttribute.CONSTITUTION).getValue();
        Gson gson = new Gson();
        return gson.fromJson(value, double.class);
    }

    public long getTimeInstanced() {
        return timeInstanced;
    }

    public double getTimeRemaining() {
        return this.getConstitution() * 50 - getTimeElapsed();
    }

    public double getTimeElapsed() {
        return System.currentTimeMillis() - this.timeInstanced;
    }
}