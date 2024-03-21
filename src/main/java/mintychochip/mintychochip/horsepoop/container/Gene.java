package mintychochip.mintychochip.horsepoop.container;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import mintychochip.genesis.Genesis;
import mintychochip.genesis.config.abstraction.GenesisConfigurationSection;
import mintychochip.mintychochip.horsepoop.HorseConfig;
import mintychochip.mintychochip.horsepoop.HorseMarker;
import mintychochip.mintychochip.horsepoop.HorsePoop;
import mintychochip.mintychochip.horsepoop.container.enums.MendelianAllele;
import mintychochip.mintychochip.horsepoop.container.enums.MendelianType;
import mintychochip.mintychochip.horsepoop.factories.GeneFactory;
import org.bukkit.Particle;
import org.bukkit.entity.EntityType;

import java.util.Random;


public class Gene {
    public enum GeneType { //determines the instancing sequence they will take
        ENUM, //list of values the gene can take
        MENDELIAN, //classical mendelian
        NUMERIC, //range of values the gene can take, for example, speed can have different numerical value,
        INTEGER;
    }

    @SerializedName("trait")
    private final String trait;
    @SerializedName("value")
    private String value = null;
    @SerializedName("conserved")
    private final boolean conserved; //the animal retains this after breeding always
    @SerializedName("crossable")
    private final boolean crossable; //prevents animal crossing from retaining this gene
    @SerializedName("gene-type")
    private final GeneType geneType;
    @SerializedName("entity-type")
    private final EntityType entityType;

    private Gene(Trait trait, EntityType entityType, HorseConfig horseConfig) {

        Gson gson = HorsePoop.GSON;
        this.trait = gson.toJson(trait);
        Random random = Genesis.RANDOM;
        GenesisConfigurationSection attribute = horseConfig.getAttribute(trait, entityType);
        this.crossable = attribute.getBoolean(HorseMarker.crossable);
        this.conserved = attribute.getBoolean(HorseMarker.conserved);
        this.geneType = attribute.enumFromSection(GeneType.class, HorseMarker.gene_type);
        this.entityType = entityType;
        GenesisConfigurationSection meta = horseConfig.getMeta(trait, entityType);
        switch (this.geneType) {
            case NUMERIC -> {
                double minimum = meta.getDouble(HorseMarker.minimum);
                double maximum = meta.getDouble(HorseMarker.maximum);
                this.value = gson.toJson(random.nextDouble(minimum, maximum));
            }
            case MENDELIAN -> { //when spawning, dominant/recessive is random
                double chance = meta.getDouble(HorseMarker.chance);
                this.value = gson.toJson(MendelianGene.createInstance(MendelianAllele.createAllele(chance), MendelianAllele.createAllele(chance)));
            }
            case ENUM -> {
                Particle[] values = Particle.values();
                int i = random.nextInt(0, values.length);
                this.value = values[i].toString();
            }
            case INTEGER -> {
                int minimum = meta.getInt(HorseMarker.minimum);
                int maximum = meta.getInt(HorseMarker.maximum);
                this.value = gson.toJson(random.nextInt(minimum,maximum));
            }
        }
    }

    private Gene(Trait trait, EntityType entityType, HorseConfig horseConfig, String value) {
        this.trait = HorsePoop.GSON.toJson(trait);
        this.value = value;
        GenesisConfigurationSection attribute = horseConfig.getAttribute(trait, entityType);
        this.crossable = attribute.getBoolean(HorseMarker.crossable);
        this.conserved = attribute.getBoolean(HorseMarker.conserved);
        this.geneType = attribute.enumFromSection(GeneType.class, HorseMarker.gene_type);
        this.entityType = entityType;
    }

    public boolean isCrossable() {
        return crossable;
    }

    public MendelianType getPhenotype() {
        if (this.geneType != GeneType.MENDELIAN) {
            return null;
        }
        MendelianGene mendelianGene = HorsePoop.GSON.fromJson(value, MendelianGene.class);
        if (mendelianGene.getAlleleA() == MendelianAllele.RECESSIVE && mendelianGene.getAlleleB() == MendelianAllele.RECESSIVE) {
            return MendelianType.MENDELIAN_RECESSIVE;
        }
        return MendelianType.MENDELIAN_DOMINANT;
    }

    public EntityType getEntityType() {
        return entityType;
    }

    public MendelianType getGenotype() {
        if (this.geneType != GeneType.MENDELIAN) {
            return null;
        }
        if (this.getPhenotype() == MendelianType.MENDELIAN_RECESSIVE) {
            return MendelianType.MENDELIAN_RECESSIVE;
        }
        MendelianGene mendelianGene = HorsePoop.GSON.fromJson(value, MendelianGene.class);
        if (mendelianGene.getAlleleA() == MendelianAllele.DOMINANT && mendelianGene.getAlleleB() == MendelianAllele.DOMINANT) {
            return MendelianType.MENDELIAN_DOMINANT;
        }
        return MendelianType.MENDELIAN_HETEROZYGOUS;
    }

    public static Gene createInstance(Trait trait, EntityType entityType, HorseConfig horseConfig, GeneFactory instance) { //do something with factory instance after
        return new Gene(trait, entityType, horseConfig);
    }

    public static Gene createInstance(Trait trait, EntityType entityType, HorseConfig horseConfig, String value, GeneFactory instance) {
        return new Gene(trait, entityType, horseConfig, value);
    }

    public Trait getTrait() {
        return HorsePoop.GSON.fromJson(trait, Trait.class);
    }

    public String getValue() {
        return value;
    }

    public boolean isConserved() {
        return conserved;
    }

    public GeneType getGeneType() {
        return geneType;
    }

    @Override
    public String toString() {
        Gson gson = HorsePoop.GSON;
        StringBuilder stringBuilder = new StringBuilder(this.getTrait().getNamespacedKey() + ": ");
        switch (this.geneType) {
            case MENDELIAN -> {
                return stringBuilder.append(gson.fromJson(this.value, MendelianGene.class).toString()).toString();
            }
            case NUMERIC -> {
                return stringBuilder.append(gson.fromJson(this.value, double.class)).toString();
            }
            case ENUM -> {
                return stringBuilder.append(value).toString();
            }
            case INTEGER -> {
                return stringBuilder.append(gson.fromJson(this.value,int.class).toString()).toString();
            }
        }
        return "";
    }
}
