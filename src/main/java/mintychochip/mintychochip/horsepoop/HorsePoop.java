package mintychochip.mintychochip.horsepoop;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import mintychochip.genesis.Genesis;
import mintychochip.genesis.commands.abstraction.GenericMainCommandManager;
import mintychochip.mintychochip.horsepoop.commands.EnabledEntitiesCommand;
import mintychochip.mintychochip.horsepoop.commands.EnchantCommand;
import mintychochip.mintychochip.horsepoop.commands.EntityTraitCommand;
import mintychochip.mintychochip.horsepoop.commands.Reload;
import mintychochip.mintychochip.horsepoop.config.ConfigManager;
import mintychochip.mintychochip.horsepoop.container.Trait;
import mintychochip.mintychochip.horsepoop.container.TraitTypeAdapter;
import mintychochip.mintychochip.horsepoop.factories.GeneFactory;
import mintychochip.mintychochip.horsepoop.factories.GenomeFactory;
import mintychochip.mintychochip.horsepoop.listener.*;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.checkerframework.checker.nullness.qual.NonNull;

public final class HorsePoop extends JavaPlugin {

  private BukkitAudiences adventure;
  private final Random random = new Random(System.currentTimeMillis());

  public static NamespacedKey GENOME_KEY = null;

  public static final Gson GSON = new GsonBuilder().registerTypeHierarchyAdapter(Trait.class,
      new TraitTypeAdapter()).create();

  private static HorsePoop INSTANCE;

  @Override
  public void onEnable() {
    INSTANCE = this;
    // Plugin startup logic
    GENOME_KEY = Genesis.getKey("genome");
    ConfigManager configManager = ConfigManager.instanceConfigManager(this);
    GeneFactory geneFactory = GeneFactory.createInstance(configManager);
    //HorseLifeTimeManager horseLifeTimeManager = new HorseLifeTimeManager(this);
    this.adventure = BukkitAudiences.create(this);

    List<Listener> listeners = new ArrayList<>();
    listeners.add(new HorseCreationListener(configManager, geneFactory,
        GenomeFactory.createInstance(geneFactory)));
    listeners.add(new AnimalPlayerListener(configManager, adventure));
    listeners.add(new AnimalCreationListener(adventure,configManager));
    listeners.add(new HorsePerkListener());
    listeners.add(new AnimalPerkListener(configManager));
    listeners.add(new ParticleListener(new Gson()));
    listeners.add(new MilkListener());
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
    if(this.adventure == null) {
      throw new IllegalStateException("Tried to access Adventure when the plugin was disabled!");
    }
    return this.adventure;
  }
  @Override
  public void onDisable() {
    if(this.adventure != null) {
      this.adventure.close();
      this.adventure = null;
    }
    // Plugin shutdown logic
  }
}
