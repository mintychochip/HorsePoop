package mintychochip.mintychochip.horsepoop.factories;

import com.google.gson.Gson;
import java.util.Random;
import mintychochip.mintychochip.horsepoop.config.ConfigManager;
import mintychochip.mintychochip.horsepoop.config.TraitMeta;
import mintychochip.mintychochip.horsepoop.config.configs.EntityConfig;
import mintychochip.mintychochip.horsepoop.HorsePoop;
import mintychochip.mintychochip.horsepoop.container.BaseTrait;
import mintychochip.mintychochip.horsepoop.container.GeneCrosser;
import mintychochip.mintychochip.horsepoop.container.GeneTrait;
import mintychochip.mintychochip.horsepoop.container.Gene;
import mintychochip.mintychochip.horsepoop.container.Trait;
import mintychochip.mintychochip.horsepoop.container.TraitFetcher;
import org.bukkit.entity.EntityType;

public class GeneFactory {

  private final ConfigManager configManager;
  private final ValueFactory valueFactory = new ValueFactory();
  private final TraitFetcher traitFetcher;
  private final GeneCrosser geneCrosser;

  private GeneFactory(ConfigManager configManager) {
    this.configManager = configManager;
    this.traitFetcher = new TraitFetcher(HorsePoop.GSON);
    this.geneCrosser = new GeneCrosser(new Random(System.currentTimeMillis()), new Gson(), traitFetcher);
  }

  public static GeneFactory createInstance(ConfigManager configManager) {
    return new GeneFactory(configManager);
  }
  public BaseTrait createInstance(Trait trait, EntityType entityType) {
    TraitMeta traitMeta = configManager.getEntityConfig().getTraitMeta(trait, entityType);
    if(traitMeta == null) {
      return null;
    }
    if(traitMeta.type().equals("gene") && trait instanceof GeneTrait geneTrait) {
      return Gene.createInstance(geneTrait,entityType,this);
    }
    return BaseTrait.createInstance(trait,traitMeta,this);
  }
  public Gene crossAndCreateGene(Gene father, Gene mother) {
    String value = geneCrosser.crossGeneForValue(father,mother);
    boolean conserved = false;
    if(father.isConserved() && mother.isConserved()) {
      conserved = true;
    }
    boolean crossable = true;
    if(father.isCrossable() && mother.isCrossable()) {
      crossable = true;
    }
    String trait = father.getTrait();
    if(traitFetcher.getGeneTrait(trait) == null) {
      return null;
    }
    return Gene.createInstance(value,trait,conserved,crossable,this);
  }

  public TraitFetcher getTraitFetcher() {
    return traitFetcher;
  }

  public EntityConfig getHorseConfig() {
    return configManager.getEntityConfig();
  }
  public ConfigManager getConfigManager() {
    return configManager;
  }

  public ValueFactory getValueFactory() {
    return valueFactory;
  }
}