package mintychochip.mintychochip.horsepoop;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import mintychochip.genesis.Genesis;
import mintychochip.mintychochip.horsepoop.api.EventCreator;
import mintychochip.mintychochip.horsepoop.api.Fetcher;
import mintychochip.mintychochip.horsepoop.api.markers.Gene;
import mintychochip.mintychochip.horsepoop.api.Generator;
import mintychochip.mintychochip.horsepoop.api.markers.Intrinsic;
import mintychochip.mintychochip.horsepoop.api.markers.Phenotypic;
import mintychochip.mintychochip.horsepoop.api.TraitEnum;
import mintychochip.mintychochip.horsepoop.config.ConfigManager;
import mintychochip.mintychochip.horsepoop.config.configs.EntityConfig;
import mintychochip.mintychochip.horsepoop.config.configs.TraitConfig;
import mintychochip.mintychochip.horsepoop.container.TypeAdapters.TraitMetaAdapter;
import mintychochip.mintychochip.horsepoop.container.ValueFetcher;
import mintychochip.mintychochip.horsepoop.container.grabber.GenomeGrasper;
import mintychochip.mintychochip.horsepoop.container.grabber.GenomeGrasperImpl;
import mintychochip.mintychochip.horsepoop.factories.DyeSelector;
import mintychochip.mintychochip.horsepoop.factories.EnumGeneration;
import mintychochip.mintychochip.horsepoop.factories.NumericGeneration;
import mintychochip.mintychochip.horsepoop.factories.MendelianGeneration;
import mintychochip.mintychochip.horsepoop.factories.TraitGeneratorImpl;
import mintychochip.mintychochip.horsepoop.factories.WeightedEnumGeneration;
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
import mintychochip.mintychochip.horsepoop.listener.IntermittentListener;
import mintychochip.mintychochip.horsepoop.listener.MilkListener;
import mintychochip.mintychochip.horsepoop.listener.NativeMethodListener;
import mintychochip.mintychochip.horsepoop.metas.Meta;
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


    private static HorsePoop INSTANCE;
    private GenomeGrasper genomeGrasper;

    private ConfigManager configManager;

    @Override
    public void onEnable() {
        this.configManager = ConfigManager.instanceConfigManager(this);
        Generator<Gene> geneGenerator = this.getGenerator();
        Generator<Phenotypic> phenotypicGenerator = this.getGenerator();
        Generator<Intrinsic> intrinsicGenerator = this.getGenerator();
        Fetcher<Gene> geneFetcher = new ValueFetcher<>();
        Fetcher<Intrinsic> intrinsicFetcher = new ValueFetcher<>();
        Fetcher<Phenotypic> phenotypicFetcher = new ValueFetcher<>();
        EventCreator eventCreator = new EventCreator();
        INSTANCE = this;
        // Plugin startup logic
        GENOME_KEY = Genesis.getKey("genome");
        this.adventure = BukkitAudiences.create(this);
        List<Listener> listeners = new ArrayList<>();
        this.genomeGrasper = new GenomeGrasperImpl(this.getGson(configManager), GENOME_KEY);
        GenomeGenerator genomeGenerator = this.getGenomeGenerator(geneGenerator, phenotypicGenerator,intrinsicGenerator);
        listeners.add(
                new HorseCreationListener(configManager, genomeGenerator, genomeGrasper));
        listeners.add(new AnimalCreationListener(configManager, genomeGrasper));
        listeners.add(new NativeMethodListener(configManager, genomeGrasper));
        listeners.add(new AnimalPlayerListener(configManager, adventure));
        listeners.add(new IntermittentListener(configManager,geneFetcher,intrinsicFetcher,new EventCreator()));
        listeners.add(new MilkListener(configManager,geneFetcher,new DyeSelector()));
        listeners.forEach(x -> {
            Bukkit.getPluginManager().registerEvents(x, this);
        });
    }

    private Gson getGson(ConfigManager configManager) {
        EntityConfig entityConfig = configManager.getEntityConfig();
        TraitConfig<Gene> geneConfig = entityConfig.getGeneConfig();
        TraitConfig<Phenotypic> characteristicConfig = entityConfig.getCharacteristicConfig();
        TraitConfig<Intrinsic> intrinsicConfig = entityConfig.getIntrinsicConfig();
        return new GsonBuilder().registerTypeAdapter(new TypeToken<Meta<Gene>>(){}.getType(), new TraitMetaAdapter<>(geneConfig))
            .registerTypeAdapter(new TypeToken<Meta<Phenotypic>>(){}.getType(),new TraitMetaAdapter<>(characteristicConfig))
            .registerTypeAdapter(new TypeToken<Meta<Intrinsic>>(){}.getType(), new TraitMetaAdapter<>(intrinsicConfig)).create();
    }

    private <U extends TraitEnum> Generator<U> getGenerator() {
        Generator<U> generator = new TraitGeneratorImpl<>();
        generator.addMetaGenerationType(MetaType.DOUBLE, new NumericGeneration<>());
        generator.addMetaGenerationType(MetaType.CROSSABLE_DOUBLE, new NumericGeneration<>());
        generator.addMetaGenerationType(MetaType.ENUM, new EnumGeneration<>());
        generator.addMetaGenerationType(MetaType.WEIGHTED_ENUM, new WeightedEnumGeneration<>());
        generator.addMetaGenerationType(MetaType.INTEGER, new NumericGeneration<>());
        generator.addMetaGenerationType(MetaType.CROSSABLE_INTEGER, new NumericGeneration<>());
        generator.addMetaGenerationType(MetaType.MENDELIAN, new MendelianGeneration<>());
        generator.addMetaGenerationType(MetaType.POLYGENIC_MENDELIAN, new MendelianGeneration<>());
        generator.addMetaGenerationType(MetaType.CROSSABLE_MENDELIAN, new MendelianGeneration<>());
        return generator;
    }
    private GenomeGenerator getGenomeGenerator(Generator<Gene> geneGenerator, Generator<Phenotypic> phenotypicGenerator, Generator<Intrinsic> intrinsicGenerator) {
        List<InstancingStep<Gene>> steps = new ArrayList<>();
        steps.add(new InstanceConserved<>());
        steps.add(new MutationInstancingStep<>());
        List<InstancingStep<Phenotypic>> charSteps = new ArrayList<>();
        charSteps.add(new InstanceConserved<>());
        List<InstancingStep<Intrinsic>> intrinsicSteps = new ArrayList<>();
        intrinsicSteps.add(new InstanceConserved<>());
        GeneratorHolder<Intrinsic> intrinsicGeneratorHolder = new SequentialTraitGenerator<>(intrinsicSteps,configManager.getEntityConfig().getIntrinsicConfig(),intrinsicGenerator);
        GeneratorHolder<Gene> sequentialTraitGenerator = new SequentialTraitGenerator<>(steps, configManager.getEntityConfig().getGeneConfig(), geneGenerator);
        GeneratorHolder<Phenotypic> sequentialTraitGenerator1 = new SequentialTraitGenerator<>(charSteps, configManager.getEntityConfig().getCharacteristicConfig(), phenotypicGenerator);
        return new SequentialGenomeGenerator(sequentialTraitGenerator, sequentialTraitGenerator1,intrinsicGeneratorHolder);
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
