package mintychochip.mintychochip.horsepoop.factories;

import com.google.gson.Gson;
import java.util.Random;
import mintychochip.genesis.Genesis;
import mintychochip.genesis.config.abstraction.GenesisConfigurationSection;
import mintychochip.genesis.util.Rarity;
import mintychochip.mintychochip.horsepoop.HorseMarker;
import mintychochip.mintychochip.horsepoop.config.ConfigManager;
import mintychochip.mintychochip.horsepoop.config.EntityConfig;
import mintychochip.mintychochip.horsepoop.HorsePoop;
import mintychochip.mintychochip.horsepoop.container.Gene;
import mintychochip.mintychochip.horsepoop.container.Gene.GeneType;
import mintychochip.mintychochip.horsepoop.container.MendelianGene;
import mintychochip.mintychochip.horsepoop.container.Trait;
import mintychochip.mintychochip.horsepoop.container.enums.MendelianAllele;
import mintychochip.mintychochip.horsepoop.container.enums.attributes.GenericTrait;
import mintychochip.mintychochip.horsepoop.container.enums.attributes.specific.GeneticAttribute;
import org.bukkit.ChatColor;
import org.bukkit.Particle;
import org.bukkit.entity.EntityType;
import org.jetbrains.annotations.NotNull;

public class GeneFactory {

    private final ConfigManager configManager;

    private final Gson gson = HorsePoop.GSON;

    private GeneFactory(ConfigManager configManager) {
        this.configManager = configManager;
    }

    public static GeneFactory createInstance(ConfigManager configManager) {
        return new GeneFactory(configManager);
    }
    public Gene createInstance(@NotNull Trait trait, @NotNull String value, boolean conserved,
        boolean crossable, @NotNull GeneType geneType) {
        return Gene.createInstance(trait, value, conserved, crossable, geneType, this);
    }

    public Gene createInstance(@NotNull Trait trait, @NotNull EntityType entityType, @NotNull ConfigManager configManager) {
        return Gene.createInstance(trait,entityType,configManager,this);
    }

    public EntityConfig getHorseConfig() {
        return configManager.getEntityConfig();
    }


    public ConfigManager getConfigManager() {
        return configManager;
    }

    public static String generateValue(Trait trait, GenesisConfigurationSection meta,
        GeneType geneType) {
        Gson gson = new Gson();
        Random random = Genesis.RANDOM;
        String value = null;
        switch (geneType) {
            case NUMERIC -> {
                double minimum = meta.getDouble(HorseMarker.minimum);
                double maximum = meta.getDouble(HorseMarker.maximum);
                value = gson.toJson(random.nextDouble(minimum, maximum));
            }
            case MENDELIAN -> { //when spawning, dominant/recessive is random
                double chance = meta.getDouble(HorseMarker.chance);
                value = gson.toJson(
                    MendelianGene.createInstance(MendelianAllele.createAllele(chance),
                        MendelianAllele.createAllele(chance)));
            }
            case ENUM -> {
                if (trait == GeneticAttribute.PARTICLE) {
                    Particle[] values = Particle.values();
                    int i = random.nextInt(0, values.length);
                    value = values[i].toString();
                }
            }
            case INTEGER -> {
                int minimum = meta.getInt(HorseMarker.minimum);
                int maximum = meta.getInt(HorseMarker.maximum);
                value = gson.toJson(random.nextInt(minimum, maximum));
            }
        }
        return value;
    }
}