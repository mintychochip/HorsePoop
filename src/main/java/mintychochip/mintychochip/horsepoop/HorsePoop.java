package mintychochip.mintychochip.horsepoop;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import mintychochip.genesis.Genesis;
import mintychochip.genesis.commands.abstraction.GenericMainCommandManager;
import mintychochip.mintychochip.horsepoop.api.EventCreator;
import mintychochip.mintychochip.horsepoop.commands.EnabledEntitiesCommand;
import mintychochip.mintychochip.horsepoop.commands.EnchantCommand;
import mintychochip.mintychochip.horsepoop.commands.Reload;
import mintychochip.mintychochip.horsepoop.config.CharacteristicTraitMeta;
import mintychochip.mintychochip.horsepoop.config.ConfigManager;
import mintychochip.mintychochip.horsepoop.config.GeneTraitMeta;
import mintychochip.mintychochip.horsepoop.config.configs.EntityConfig;
import mintychochip.mintychochip.horsepoop.container.Trait;
import mintychochip.mintychochip.horsepoop.container.TypeAdapters.TraitTypeAdapter;
import mintychochip.mintychochip.horsepoop.container.grabber.GenomeGrasper;
import mintychochip.mintychochip.horsepoop.container.grabber.GenomeGrasperImpl;
import mintychochip.mintychochip.horsepoop.factories.TraitGeneratorImpl;
import mintychochip.mintychochip.horsepoop.factories.sequential.crosser.abstraction.TraitCrosserHolder;
import mintychochip.mintychochip.horsepoop.factories.sequential.instancer.SequentialGenomeGenerator;
import mintychochip.mintychochip.horsepoop.factories.sequential.instancer.SequentialTraitGenerator;
import mintychochip.mintychochip.horsepoop.factories.sequential.instancer.gene.abstraction.GenomeGenerator;
import mintychochip.mintychochip.horsepoop.factories.sequential.instancer.gene.abstraction.InstancingStep;
import mintychochip.mintychochip.horsepoop.factories.sequential.instancer.gene.steps.InstanceGeneStep;
import mintychochip.mintychochip.horsepoop.factories.sequential.instancer.gene.steps.MutationInstancingStep;
import mintychochip.mintychochip.horsepoop.listener.AnimalCreationListener;
import mintychochip.mintychochip.horsepoop.listener.AnimalPlayerListener;
import mintychochip.mintychochip.horsepoop.listener.HorseCreationListener;
import mintychochip.mintychochip.horsepoop.listener.NativeMethodListener;
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

  public static final Gson GSON = new GsonBuilder().registerTypeHierarchyAdapter(Trait.class,
      new TraitTypeAdapter()).create();

  private static HorsePoop INSTANCE;

  private TraitCrosserHolder traitCrosserHolder;

  private GenomeGenerator genomeGenerator;

  private GenomeGrasper genomeGrasper;

  private ConfigManager configManager;

  @Override
  public void onEnable() {
    //  TraitFetcher<> traitFetcher = new TraitFetcher(
    //    new GsonBuilder().registerTypeHierarchyAdapter(Trait.class,
    //        new TraitTypeAdapter()).create());
    Random random = new Random(System.currentTimeMillis());
    EventCreator eventCreator = new EventCreator();
    INSTANCE = this;
    // Plugin startup logic
    GENOME_KEY = Genesis.getKey("genome");
    this.configManager = ConfigManager.instanceConfigManager(this);
    // GeneFactory geneFactory = GeneFactory.createInstance(configManager);
    this.adventure = BukkitAudiences.create(this);
    List<Listener> listeners = new ArrayList<>();
    this.genomeGrasper = new GenomeGrasperImpl(GSON, GENOME_KEY);
    this.genomeGenerator = createInstancer();
    listeners.add(
        new HorseCreationListener(configManager, traitCrosserHolder, genomeGenerator, genomeGrasper));
    listeners.add(new AnimalCreationListener(configManager, genomeGrasper));
    listeners.add(new NativeMethodListener(configManager,genomeGrasper));
    listeners.add(new AnimalPlayerListener(configManager,adventure));
//    listeners.add(new AnimalPlayerListener(configManager, adventure));
    listeners.forEach(x -> {
      Bukkit.getPluginManager().registerEvents(x, this);
    });

    GenericMainCommandManager genericMainCommandManager = new GenericMainCommandManager("entity",
        "asdasd");
    genericMainCommandManager.addSubCommand(new Reload("reload", "reloads", configManager));
    genericMainCommandManager.instantiateSubCommandManager("list", "asdad")
        .addSubCommand(
            new EnabledEntitiesCommand("enabled", "aasdasd", configManager.getEntityConfig()))
        .addSubCommand(new EnchantCommand("enchant", "enchant"));
    getCommand("entity").setExecutor(genericMainCommandManager);
  }

  private SequentialGenomeGenerator createInstancer() {
    List<InstancingStep<GeneTraitMeta>> steps = new ArrayList<>();
    steps.add(new InstanceGeneStep<>());
    steps.add(new MutationInstancingStep<>());
    List<InstancingStep<CharacteristicTraitMeta>> charSteps = new ArrayList<>();
    charSteps.add(new InstanceGeneStep<>());
    EntityConfig entityConfig = configManager.getEntityConfig();
    return new SequentialGenomeGenerator(
        new SequentialTraitGenerator<>(steps, entityConfig.geneConfig(),
            new TraitGeneratorImpl<>()),
        new SequentialTraitGenerator<>(charSteps, entityConfig.characteristicConfig(),
            new TraitGeneratorImpl<>()));
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

  public TraitCrosserHolder getGenomeCrosser() {
    return traitCrosserHolder;
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
