package mintychochip.mintychochip.horsepoop.listener.perks.parrot;

import java.util.List;
import mintychochip.mintychochip.horsepoop.container.AnimalGenome;
import mintychochip.mintychochip.horsepoop.container.Genome;
import mintychochip.mintychochip.horsepoop.container.grabber.GenomeGrasper;
import mintychochip.mintychochip.horsepoop.factories.DyeSelector;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;

public class DeathListener implements Listener {

  private final GenomeGrasper grasper;

  private final DyeSelector selector = new DyeSelector();
  public DeathListener(GenomeGrasper grasper) {
    this.grasper = grasper;
  }
  @EventHandler
  private void onSheepDeath(final EntityDeathEvent event) {
    AnimalGenome grab = grasper.grab(event.getEntity());
    if(grab == null) {
      return;
    }
    ItemStack itemStack = event.getDrops().stream()
        .filter(item -> item.getType().toString().contains("WOOL")).findFirst().orElse(null);
    if(itemStack == null) {
      return;
    }
    int amount = itemStack.getAmount();
    event.getDrops().remove(itemStack);
    Material material = Material.valueOf(
        selector.calculateDyeColor(grab, event.getEntity()).toString() + "_WOOL");
    ItemStack itemStack1 = new ItemStack(material);
    itemStack1.setAmount(amount);
    event.getDrops().add(itemStack1);
  }
}
