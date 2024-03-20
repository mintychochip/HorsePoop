package mintychochip.mintychochip.horsepoop;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import mintychochip.mintychochip.horsepoop.container.Trait;
import mintychochip.mintychochip.horsepoop.container.TraitTypeAdapter;
import mintychochip.mintychochip.horsepoop.factories.GeneFactory;
import mintychochip.mintychochip.horsepoop.factories.GenomeFactory;
import mintychochip.mintychochip.horsepoop.horse.HorseLifeTimeManager;
import mintychochip.mintychochip.horsepoop.listener.*;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public final class HorsePoop extends JavaPlugin {
    private final Random random = new Random(System.currentTimeMillis());

    public static final Gson GSON = new GsonBuilder().registerTypeHierarchyAdapter(Trait.class, new TraitTypeAdapter()).create();

    private static HorsePoop INSTANCE;

    @Override
    public void onEnable() {
        INSTANCE = this;
        // Plugin startup logic
        ConfigManager configManager = ConfigManager.instanceConfigManager(this);
        GeneFactory geneFactory = GeneFactory.createInstance(configManager.getHorseConfig());
        HorseLifeTimeManager horseLifeTimeManager = new HorseLifeTimeManager(this);
        List<Listener> listeners = new ArrayList<>();
        listeners.add(new HorseCreationListener(geneFactory, GenomeFactory.createInstance(geneFactory)));
        listeners.add(new AnimalPlayerListener());
        listeners.add(new AnimalCreationListener(horseLifeTimeManager));
        listeners.add(new HorsePerkListener());
        listeners.add(ParticleListener.createParticleListener(this, new Gson()));
        listeners.forEach(x -> {
            Bukkit.getPluginManager().registerEvents(x, this);
        });
    }

    public static HorsePoop getInstance() {
        return INSTANCE;
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
