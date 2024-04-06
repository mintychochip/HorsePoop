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
import org.bukkit.Bukkit;

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

    private AnimalGenome(List<BaseTrait<Gene>> genes, List<BaseTrait<Phenotypic>> chars, List<BaseTrait<Intrinsic>> intrinsics, long birthTime) {
        this.genes = genes;
        this.chars = chars;
        this.intrinsics = intrinsics;
        this.birthTime = birthTime;
    }


    public static AnimalGenome createInstance(List<BaseTrait<Gene>> genes, List<BaseTrait<Phenotypic>> chars, List<BaseTrait<Intrinsic>> intrinsics, GenomeCrosser crosser) {
        return new AnimalGenome(genes, chars, intrinsics,System.currentTimeMillis());
    }

    public static AnimalGenome createInstance(List<BaseTrait<Gene>> genes, List<BaseTrait<Phenotypic>> chars,List<BaseTrait<Intrinsic>> intrinsics, GenomeGenerator generator) {
        return new AnimalGenome(genes, chars, intrinsics, System.currentTimeMillis());
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
    public List<BaseTrait<Phenotypic>> getPhenotypics() {
        return chars;
    }

    public long getBirthTime() {
        return birthTime;
    }
}