package mintychochip.mintychochip.horsepoop.listener.perks.parrot;

import mintychochip.genesis.config.abstraction.GenesisConfigurationSection;
import mintychochip.genesis.items.container.AbstractItem;
import mintychochip.genesis.items.container.AppraisableItemData;
import mintychochip.mintychochip.horsepoop.HorsePoop;
import mintychochip.mintychochip.horsepoop.api.Fetcher;
import mintychochip.mintychochip.horsepoop.api.events.finalevents.FawnyShearEvent;
import mintychochip.mintychochip.horsepoop.api.markers.Gene;
import mintychochip.mintychochip.horsepoop.api.markers.Phenotypic;
import mintychochip.mintychochip.horsepoop.config.ConfigManager;
import mintychochip.mintychochip.horsepoop.container.AnimalGenome;
import mintychochip.mintychochip.horsepoop.container.ValueFetcher;
import mintychochip.mintychochip.horsepoop.container.enums.FeatherColor;
import mintychochip.mintychochip.horsepoop.container.enums.traits.GenericGene;
import mintychochip.mintychochip.horsepoop.container.enums.traits.PhenotypicTraitEnum;
import mintychochip.mintychochip.horsepoop.util.math.RandomYield;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;

public class ShearListener implements Listener {

  private final Fetcher<Phenotypic> phenotypicFetcher = new ValueFetcher<>();
  private final Fetcher<Gene> geneFetcher = new ValueFetcher<>();

  public ShearListener(ConfigManager configManager) {
    this.configManager = configManager;
  }

  private final ConfigManager configManager;

  @EventHandler
  private void onShearParrotEvent(final FawnyShearEvent event) {
    if (event.isCancelled()) {
      return;
    }
    AnimalGenome animalGenome = event.getAnimalGenome();
    FeatherColor color = phenotypicFetcher.getEnumValue(animalGenome.getPhenotypics(),
        PhenotypicTraitEnum.PARROT_FEATHER_COLOR,
        FeatherColor.class);
    if (color == null) {
      return;
    }
    ItemStack shears = event.getInstrument();
    event.getLivingEntity().damage(1, event.getPlayer());
    GenesisConfigurationSection featherSection = configManager.getItemConfig()
        .getMainConfigurationSection(color.getFeatherKey()
        );
    if (featherSection.isNull()) {
      throw new RuntimeException("Feather section: " + color.getFeatherKey() + " is null.");
    }
    ItemStack itemStack = new AbstractItem.EmbeddedDataBuilder(HorsePoop.getInstance(), featherSection, false,
        new AppraisableItemData()).defaultBuild().getItemStack();
    double yield = geneFetcher.getNumeric(animalGenome.getGenes(), GenericGene.YIELD);
    int max = yield != 0 ? (int) yield : 1;
    itemStack.setAmount(RandomYield.calculateRandomYield(max, shears));

    Location location = event.getLivingEntity().getLocation();
    World world = location.getWorld();
    if (world == null) {
      return;
    }
    world.dropItem(location, itemStack);
  }
}
