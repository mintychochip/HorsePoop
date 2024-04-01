package mintychochip.mintychochip.horsepoop.container;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.annotation.Nullable;

import mintychochip.genesis.Genesis;
import mintychochip.mintychochip.horsepoop.config.CharacteristicTraitMeta;
import mintychochip.mintychochip.horsepoop.config.ConfigManager;
import mintychochip.mintychochip.horsepoop.config.GeneTraitMeta;
import mintychochip.mintychochip.horsepoop.config.configs.EntityConfig;
import mintychochip.mintychochip.horsepoop.factories.sequential.instancer.gene.abstraction.GenomeInstancer;
import org.bukkit.entity.EntityType;

public class AnimalGenome<T extends GeneTraitMeta, U extends CharacteristicTraitMeta> {


    @SerializedName("genes")
    private List<BaseTrait<T>> genes;
    @SerializedName("characteristics")
    private List<BaseTrait<U>> characteristics;
    @SerializedName("time-alive")
    private long birthTime;
    @SerializedName("name")
    @Nullable
    private String name = null;

    public void setName(@Nullable String name) {
        this.name = name;
    }

    @Nullable
    public String getName() {
        return name;
    }

    public static AnimalGenome createInstance(List<T> characteristics, List<Gene> genes, GenomeInstancer genomeInstancer) {
        if (genes == null) {
            return null;
        }
        return new AnimalGenome(characteristics, genes, System.currentTimeMillis());
    }

    public List<BaseTrait<U>> getCharacteristics() {
        return characteristics;
    }

    public List<BaseTrait<T>> getGenes() {
        return genes;
    }

    public long getBirthTime() {
        return birthTime;
    }
}