package mintychochip.mintychochip.horsepoop.listener.perks;

import mintychochip.genesis.items.container.AbstractItem;
import mintychochip.mintychochip.horsepoop.HorsePoop;
import mintychochip.mintychochip.horsepoop.api.Fetcher;
import mintychochip.mintychochip.horsepoop.api.events.finalevents.FrogDartEvent;
import mintychochip.mintychochip.horsepoop.api.markers.Gene;
import mintychochip.mintychochip.horsepoop.container.AnimalGenome;
import mintychochip.mintychochip.horsepoop.container.BaseTrait;
import mintychochip.mintychochip.horsepoop.container.MendelianGene;
import mintychochip.mintychochip.horsepoop.container.ValueFetcher;
import mintychochip.mintychochip.horsepoop.container.enums.MendelianType;
import mintychochip.mintychochip.horsepoop.container.enums.traits.GenericGene;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.potion.PotionType;

public class FrogListener implements Listener {

  private final Fetcher<Gene> geneFetcher = new ValueFetcher<>();
  @EventHandler
  private void onFrogDart(final FrogDartEvent event) {
    AnimalGenome animalGenome = event.getAnimalGenome();
    BaseTrait<Gene> toxic = geneFetcher.getTraitFromList(animalGenome.getGenes(),
        GenericGene.TOXIC);
    if(toxic == null) {
      return;
    }
    MendelianGene toxicGene = geneFetcher.getMendelian(toxic);
    if(toxicGene.getPhenotype() != MendelianType.MENDELIAN_DOMINANT) {
      return;
    }
    Player player = event.getPlayer();
    PlayerInventory inventory = player.getInventory();
    inventory.addItem(new AbstractItem.PotionBuilder(HorsePoop.getInstance(), Material.TIPPED_ARROW,
        false).setBasePotionType(PotionType.LONG_POISON).build().getItemStack());
    Location location = player.getLocation();
    World world = location.getWorld();
    if(world == null) {
      return;
    }
    world.playSound(event.getLivingEntity(), Sound.ENTITY_SLIME_SQUISH,1,1);
  }
}
