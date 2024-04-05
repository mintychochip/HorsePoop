package mintychochip.mintychochip.horsepoop.container;

import com.google.gson.annotations.SerializedName;

import java.util.List;
import javax.annotation.Nullable;

import mintychochip.mintychochip.horsepoop.api.Gene;
import mintychochip.mintychochip.horsepoop.api.Intrinsic;
import mintychochip.mintychochip.horsepoop.api.Phenotypic;
import mintychochip.mintychochip.horsepoop.container.TypeAdapters.Genome;
import mintychochip.mintychochip.horsepoop.factories.sequential.crosser.abstraction.GenomeCrosser;
import mintychochip.mintychochip.horsepoop.factories.sequential.instancer.gene.abstraction.GenomeGenerator;

public class AnimalGenome implements Genome {

    @SerializedName("genes")
    private List<BaseTrait<Gene>> genes;
    @SerializedName("chars")
    private List<BaseTrait<Phenotypic>> chars;
    @SerializedName("intrinsics")
    private List<BaseTrait<Intrinsic>> intrinsics;
    @SerializedName("time-alive")
    private long birthTime;
    @SerializedName("name")
    @Nullable
    private String name = null;

    private AnimalGenome(List<BaseTrait<Gene>> genes, List<BaseTrait<Phenotypic>> chars, long birthTime) {
        this.genes = genes;
        this.chars = chars;
        this.birthTime = birthTime;
    }

    private static boolean instancingLogic(List<BaseTrait<Gene>> genes, List<BaseTrait<Phenotypic>> chars) {
        if (genes == null || genes.isEmpty()) {
            return true;
        }
        return chars == null || chars.isEmpty();
    }

    public static AnimalGenome createInstance(List<BaseTrait<Gene>> genes, List<BaseTrait<Phenotypic>> chars, GenomeCrosser crosser) {
        if (instancingLogic(genes, chars)) {
            return null;
        }
        return new AnimalGenome(genes, chars, System.currentTimeMillis());
    }

    public static AnimalGenome createInstance(List<BaseTrait<Gene>> genes, List<BaseTrait<Phenotypic>> chars, GenomeGenerator generator) {
        if (instancingLogic(genes, chars)) {
            return null;
        }
        return new AnimalGenome(genes, chars, System.currentTimeMillis());
    }

    public void setName(@Nullable String name) {
        this.name = name;
    }

    @Nullable
    public String getName() {
        return name;
    }

    public List<BaseTrait<Intrinsic>> getIntrinsics() {
        return intrinsics;
    }

    @Override
    public List<BaseTrait<Gene>> getGenes() {
        return genes;
    }

    @Override
    public List<BaseTrait<Phenotypic>> getChars() {
        return chars;
    }

    public long getBirthTime() {
        return birthTime;
    }
}