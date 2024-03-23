package mintychochip.mintychochip.horsepoop.container;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import mintychochip.genesis.Genesis;
import mintychochip.mintychochip.horsepoop.EntityConfig;
import mintychochip.mintychochip.horsepoop.UnitConversions;
import mintychochip.mintychochip.horsepoop.container.enums.attributes.specific.GeneticAttribute;
import mintychochip.mintychochip.horsepoop.factories.GeneFactory;
import org.bukkit.Particle;
import org.bukkit.entity.EntityType;

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

    private AnimalGenome(GeneFactory geneFactory, EntityType entityType, int mutations) {
        Random random = Genesis.RANDOM;
        this.gender = random.nextBoolean() ? Gender.MALE : Gender.FEMALE;
        this.mutations = mutations;
        EntityConfig entityConfig = geneFactory.getHorseConfig();
        List<Trait> enabledAttributes = entityConfig.getEntityTypeTraitMap().get(entityType);
        for (Trait enabledAttribute : enabledAttributes) {
            if (entityConfig.getConserved(enabledAttribute, entityType)) {
                genes.add(geneFactory.createInstance(enabledAttribute, entityType));
            }
        }
        for (int i = 0; i < this.mutations; i++) {
            int index = random.nextInt(0, enabledAttributes.size());
            Trait value = enabledAttributes.get(index);
            if (!isInGenes(value)) {
                genes.add(geneFactory.createInstance(value, entityType));
            }
        }
    }
    public Map<Trait, Gene> getAttributes() {
        Map<Trait, Gene> attributes = new HashMap<>();
        for (Gene gene : genes) {
            attributes.put(gene.getTrait(),gene);
        }
        return attributes;
    }
    private boolean isInGenes(Trait trait) {
        for (Gene gene : genes) {
            if (trait == gene.getTrait()) {
                return true;
            }
        }
        return false;
    }

    private AnimalGenome(GeneFactory geneFactory, EntityType entityType, List<Gene> genes, int mutations) {
        Random random = Genesis.RANDOM;
        this.gender = random.nextBoolean() ? Gender.MALE : Gender.FEMALE;
        this.mutations = mutations;
        this.genes = genes;
    }

    public static AnimalGenome createInstance(GeneFactory geneFactory, EntityType entityType, int mutations) {
        return new AnimalGenome(geneFactory, entityType, mutations);
    }

    public static AnimalGenome createInstance(GeneFactory geneFactory, EntityType entityType, List<Gene> genes, int mutations) {
        return new AnimalGenome(geneFactory, entityType, genes, mutations);
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
            if (trait == gene.getTrait()) {
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