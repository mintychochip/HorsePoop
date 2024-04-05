package mintychochip.mintychochip.horsepoop;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import mintychochip.genesis.Genesis;
import mintychochip.genesis.commands.abstraction.GenericMainCommandManager;
import mintychochip.mintychochip.horsepoop.api.*;
import mintychochip.mintychochip.horsepoop.config.ConfigManager;
import mintychochip.mintychochip.horsepoop.container.TypeAdapters.TraitTypeAdapter;
import mintychochip.mintychochip.horsepoop.container.grabber.GenomeGrasper;
import mintychochip.mintychochip.horsepoop.container.grabber.GenomeGrasperImpl;
import mintychochip.mintychochip.horsepoop.factories.*;
import mintychochip.mintychochip.horsepoop.factories.sequential.instancer.SequentialGenomeGenerator;
import mintychochip.mintychochip.horsepoop.factories.sequential.instancer.SequentialTraitGenerator;
import mintychochip.mintychochip.horsepoop.factories.sequential.instancer.gene.abstraction.GeneratorHolder;
import mintychochip.mintychochip.horsepoop.factories.sequential.instancer.gene.abstraction.GenomeGenerator;
import mintychochip.mintychochip.horsepoop.factories.sequential.instancer.gene.abstraction.InstancingStep;
import mintychochip.mintychochip.horsepoop.factories.sequential.instancer.gene.steps.InstanceConserved;
import mintychochip.mintychochip.horsepoop.factories.sequential.instancer.gene.steps.MutationInstancingStep;
import mintychochip.mintychochip.horsepoop.listener.AnimalCreationListener;
import mintychochip.mintychochip.horsepoop.listener.AnimalPlayerListener;
import mintychochip.mintychochip.horsepoop.listener.HorseCreationListener;
import mintychochip.mintychochip.horsepoop.listener.NativeMethodListener;
import mintychochip.mintychochip.horsepoop.metas.MetaType;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.checkerframework.checker.nullness.qual.NonNull;

public final class HorsePoop extends JavaPlugin {

    private BukkitAudiences adventure;
    private final Random random = new Random(System.currentTimeMillis());

    public static NamespacedKey GENOME_KEY = null;

    public static final Gson GSON = new GsonBuilder().registerTypeHierarchyAdapter(TraitEnum.class,
            new TraitTypeAdapter()).create();

    private static HorsePoop INSTANCE;
    private GenomeGrasper genomeGrasper;

    private ConfigManager configManager;

    @Override
    public void onEnable() {
        this.configManager = ConfigManager.instanceConfigManager(this);

        Generator<Gene> geneGenerator = this.getGenerator();
        Generator<Phenotypic> phenotypicGenerator = this.getGenerator();
        Generator<Intrinsic> intrinsicGenerator = this.getGenerator();
        Random random = new Random(System.currentTimeMillis());
        EventCreator eventCreator = new EventCreator();
        INSTANCE = this;
        // Plugin startup logic
        GENOME_KEY = Genesis.getKey("genome");
        this.adventure = BukkitAudiences.create(this);
        List<Listener> listeners = new ArrayList<>();
        this.genomeGrasper = new GenomeGrasperImpl(GSON, GENOME_KEY);
        GenomeGenerator genomeGenerator = this.getGenomeGenerator(geneGenerator, phenotypicGenerator);
        listeners.add(
                new HorseCreationListener(configManager, genomeGenerator, genomeGrasper));
        listeners.add(new AnimalCreationListener(configManager, genomeGrasper));
        listeners.add(new NativeMethodListener(configManager, genomeGrasper));
        listeners.add(new AnimalPlayerListener(configManager, adventure));
        listeners.forEach(x -> {
            Bukkit.getPluginManager().registerEvents(x, this);
        });
    }

    private <U extends TraitEnum> Generator<U> getGenerator() {
        Generator<U> generator = new TraitGeneratorImpl<>();
        generator.addMetaGenerationType(MetaType.DOUBLE, new DoubleGeneration<>());
        generator.addMetaGenerationType(MetaType.CROSSABLE_DOUBLE, new DoubleGeneration<>());
        generator.addMetaGenerationType(MetaType.ENUM, new EnumGeneration<>());
        generator.addMetaGenerationType(MetaType.WEIGHTED_ENUM, new WeightedEnumGeneration<>());
        generator.addMetaGenerationType(MetaType.INTEGER, new IntegerGeneration<>());
        generator.addMetaGenerationType(MetaType.CROSSABLE_INTEGER, new IntegerGeneration<>());
        generator.addMetaGenerationType(MetaType.MENDELIAN, new MendelianGeneration<>());
        generator.addMetaGenerationType(MetaType.POLYGENIC_MENDELIAN, new MendelianGeneration<>());
        generator.addMetaGenerationType(MetaType.CROSSABLE_MENDELIAN, new MendelianGeneration<>());
        return generator;
    }

    private GenomeGenerator getGenomeGenerator(Generator<Gene> geneGenerator, Generator<Phenotypic> phenotypicGenerator) {
        List<InstancingStep<Gene>> steps = new ArrayList<>();
        steps.add(new InstanceConserved<>());
        steps.add(new MutationInstancingStep<>());
        List<InstancingStep<Phenotypic>> charSteps = new ArrayList<>();
        charSteps.add(new InstanceConserved<>());
        GeneratorHolder<Gene> sequentialTraitGenerator = new SequentialTraitGenerator<>(steps, configManager.getEntityConfig().geneConfig(), geneGenerator);
        GeneratorHolder<Phenotypic> sequentialTraitGenerator1 = new SequentialTraitGenerator<>(charSteps, configManager.getEntityConfig().characteristicConfig(), phenotypicGenerator);
        return new SequentialGenomeGenerator(sequentialTraitGenerator, sequentialTraitGenerator1);
    }

    public static HorsePoop getInstance() {
        return INSTANCE;
    }

    public @NonNull BukkitAudiences adventure() {
        if (this.adventure == null) {
            throw new IllegalStateException("Tried to access Adventure when the plugin was disabled!");
        }
        return this.adventure;
    }

    @Override
    public void onDisable() {
        if (this.adventure != null) {
            this.adventure.close();
            this.adventure = null;
        }
        // Plugin shutdown logic
    }
}
