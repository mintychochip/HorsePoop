package mintychochip.mintychochip.horsepoop;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import mintychochip.genesis.Genesis;
import mintychochip.genesis.commands.abstraction.GenericMainCommandManager;
import mintychochip.mintychochip.horsepoop.commands.EnabledEntitiesCommand;
import mintychochip.mintychochip.horsepoop.commands.EnchantCommand;
import mintychochip.mintychochip.horsepoop.commands.EntityTraitCommand;
import mintychochip.mintychochip.horsepoop.container.Trait;
import mintychochip.mintychochip.horsepoop.container.TraitTypeAdapter;
import mintychochip.mintychochip.horsepoop.factories.GeneFactory;
import mintychochip.mintychochip.horsepoop.factories.GenomeFactory;
import mintychochip.mintychochip.horsepoop.horse.HorseLifeTimeManager;
import mintychochip.mintychochip.horsepoop.listener.*;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public final class HorsePoop extends JavaPlugin {
    private final Random random = new Random(System.currentTimeMillis());

    public static NamespacedKey GENOME_KEY = null;

    public static final Gson GSON = new GsonBuilder().registerTypeHierarchyAdapter(Trait.class, new TraitTypeAdapter()).create();

    private static HorsePoop INSTANCE;

    @Override
    public void onEnable() {
        INSTANCE = this;
        // Plugin startup logic
        GENOME_KEY = Genesis.getKey("genome");
        ConfigManager configManager = ConfigManager.instanceConfigManager(this);
        GeneFactory geneFactory = GeneFactory.createInstance(configManager.getHorseConfig());
        HorseLifeTimeManager horseLifeTimeManager = new HorseLifeTimeManager(this);
        List<Listener> listeners = new ArrayList<>();
        listeners.add(new HorseCreationListener(geneFactory, GenomeFactory.createInstance(geneFactory, configManager)));
        listeners.add(new AnimalPlayerListener(configManager));
        listeners.add(new AnimalCreationListener(horseLifeTimeManager));
        listeners.add(new HorsePerkListener());
        listeners.add(new AnimalDeathListener(configManager));
        listeners.add(ParticleListener.createParticleListener(this, new Gson()));
        listeners.forEach(x -> {
            Bukkit.getPluginManager().registerEvents(x, this);
        });
        GenericMainCommandManager genericMainCommandManager = new GenericMainCommandManager("entity", "asdasd");
        genericMainCommandManager.instantiateSubCommandManager("list", "asdad")
                .addSubCommand(new EnabledEntitiesCommand("enabled", "aasdasd", configManager.getHorseConfig()))
                .addSubCommand(new EntityTraitCommand("trait", "lists traits", configManager.getHorseConfig().getEnabledEntityTypes(), configManager.getHorseConfig()))
                .addSubCommand(new EnchantCommand("enchant", "enchant"));
        getCommand("entity").setExecutor(genericMainCommandManager);
    }

    public static HorsePoop getInstance() {
        return INSTANCE;
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
