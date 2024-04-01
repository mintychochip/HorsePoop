package mintychochip.mintychochip.horsepoop;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import mintychochip.genesis.Genesis;
import mintychochip.genesis.commands.abstraction.GenericMainCommandManager;
import mintychochip.mintychochip.horsepoop.commands.EnabledEntitiesCommand;
import mintychochip.mintychochip.horsepoop.commands.EnchantCommand;
import mintychochip.mintychochip.horsepoop.commands.Reload;
import mintychochip.mintychochip.horsepoop.config.ConfigManager;
import mintychochip.mintychochip.horsepoop.config.GeneTraitMeta;
import mintychochip.mintychochip.horsepoop.container.*;
import mintychochip.mintychochip.horsepoop.container.GenomeComparer;
import mintychochip.mintychochip.horsepoop.container.TypeAdapters.TraitTypeAdapter;
import mintychochip.mintychochip.horsepoop.factories.sequential.crosser.SequentialGenomeCrosser;
import mintychochip.mintychochip.horsepoop.factories.sequential.crosser.abstraction.GenomeCrosser;
import mintychochip.mintychochip.horsepoop.factories.sequential.crosser.abstraction.GenomeCrossingStep;
import mintychochip.mintychochip.horsepoop.factories.sequential.crosser.steps.ConservationStep;
import mintychochip.mintychochip.horsepoop.factories.sequential.crosser.steps.MutationStep;
import mintychochip.mintychochip.horsepoop.factories.sequential.crosser.steps.NonConservedStep;
import mintychochip.mintychochip.horsepoop.factories.sequential.instancer.SequentialGenomeInstancer;
import mintychochip.mintychochip.horsepoop.factories.sequential.instancer.SequentialTraitInstancer;
import mintychochip.mintychochip.horsepoop.factories.sequential.instancer.gene.abstraction.GenomeInstancer;
import mintychochip.mintychochip.horsepoop.factories.sequential.instancer.gene.abstraction.InstancingStep;
import mintychochip.mintychochip.horsepoop.factories.sequential.instancer.gene.abstraction.TraitInstancer;
import mintychochip.mintychochip.horsepoop.factories.sequential.instancer.gene.steps.InstanceGeneStep;
import mintychochip.mintychochip.horsepoop.factories.sequential.instancer.gene.steps.MutationInstancingStep;
import mintychochip.mintychochip.horsepoop.listener.AnimalCreationListener;
import mintychochip.mintychochip.horsepoop.listener.AnimalPerkListener;
import mintychochip.mintychochip.horsepoop.listener.AnimalPlayerListener;
import mintychochip.mintychochip.horsepoop.listener.HorseCreationListener;
import mintychochip.mintychochip.horsepoop.listener.HorsePerkListener;
import mintychochip.mintychochip.horsepoop.listener.MilkListener;
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

  private GenomeCrosser genomeCrosser;

  private GenomeInstancer genomeInstancer;

  @Override
  public void onEnable() {
    TraitFetcher traitFetcher = new TraitFetcher(
        new GsonBuilder().registerTypeHierarchyAdapter(Trait.class,
            new TraitTypeAdapter()).create());
    Random random = new Random(System.currentTimeMillis());
    INSTANCE = this;
    // Plugin startup logic
    GENOME_KEY = Genesis.getKey("genome");
    ConfigManager configManager = ConfigManager.instanceConfigManager(this);
    GeneFactory geneFactory = GeneFactory.createInstance(configManager);
    this.adventure = BukkitAudiences.create(this);
    List<InstancingStep<GeneTraitMeta>> geneInstancingSteps = new ArrayList<>();
    geneInstancingSteps.add(new InstanceGeneStep<>());
    geneInstancingSteps.add(new MutationInstancingStep<>());
    TraitInstancer<GeneTraitMeta> geneInstancer = new SequentialTraitInstancer<GeneTraitMeta,GeneTrait>();
    List<InstancingStep> charInstancingSteps = new ArrayList<>();
    charInstancingSteps.add(new InstanceCharStep(geneFactory, random));
    List<Listener> listeners = new ArrayList<>();
    List<GenomeCrossingStep> steps = new ArrayList<>();
    steps.add(new ConservationStep(geneFactory));
    steps.add(new NonConservedStep(geneFactory));
    steps.add(new MutationStep(geneFactory, new GenomeComparer(traitFetcher)));
    TraitInstancer geneInstancer = new SequentialTraitInstancer(geneInstancingSteps);
    TraitInstancer charInstancer = new SequentialTraitInstancer(charInstancingSteps);
    this.genomeInstancer = new SequentialGenomeInstancer(geneInstancer,charInstancer);
    this.genomeCrosser = new SequentialGenomeCrosser(steps);
    listeners.add(
        new HorseCreationListener(configManager, geneFactory, genomeCrosser, genomeInstancer));
//    listeners.add(new AnimalPlayerListener(configManager, adventure));
    listeners.add(new AnimalCreationListener(adventure, configManager, traitFetcher));
    listeners.add(new HorsePerkListener(traitFetcher));
    listeners.add(new AnimalPlayerListener(configManager, adventure));
    listeners.add(new AnimalPerkListener(configManager, traitFetcher));
    listeners.add(new MilkListener(configManager, traitFetcher));
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

  public static HorsePoop getInstance() {
    return INSTANCE;
  }

  public @NonNull BukkitAudiences adventure() {
    if (this.adventure == null) {
      throw new IllegalStateException("Tried to access Adventure when the plugin was disabled!");
    }
    return this.adventure;
  }

  public GenomeCrosser getGenomeCrosser() {
    return genomeCrosser;
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
