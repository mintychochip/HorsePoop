package mintychochip.mintychochip.horsepoop.listener.perks;

import mintychochip.genesis.items.container.AbstractItem;
import mintychochip.genesis.items.container.AbstractItem.PotionBuilder;
import mintychochip.mintychochip.horsepoop.HorsePoop;
import mintychochip.mintychochip.horsepoop.api.events.AbstractInstrumentEvent;
import mintychochip.mintychochip.horsepoop.api.events.FawnyInteractEntityEvent;
import mintychochip.mintychochip.horsepoop.api.events.finalevents.FawnyShearEvent;
import mintychochip.mintychochip.horsepoop.api.events.finalevents.FrogDartEvent;
import mintychochip.mintychochip.horsepoop.container.AnimalGenome;
import mintychochip.mintychochip.horsepoop.container.CooldownContainer;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Tameable;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionType;

public class FrogListener implements Listener {

  @EventHandler
  private void onClickFrog(final FawnyInteractEntityEvent event) {
    AnimalGenome animalGenome = event.getAnimalGenome();
    Player player = event.getPlayer();
    if (player.isSneaking()) {
      return;
    }
    LivingEntity livingEntity = event.getLivingEntity();
    if (livingEntity.isDead()) {
      return;
    }
    ItemStack instrument = event.getInstrument();
    if (instrument == null) {
      return;
    }
    if (instrument.getType() != Material.ARROW) {
      return;
    }
    Bukkit.getPluginManager().callEvent(
        new FrogDartEvent(animalGenome, livingEntity, player,
            instrument));
  }
  @EventHandler
  private void onFinalCall(final AbstractInstrumentEvent event) {
    Player player = event.getPlayer();
    if(player.getGameMode() == GameMode.SURVIVAL) {
      return;
    }
    ItemStack instrument = event.getInstrument();
    ItemMeta itemMeta = instrument.getItemMeta();
    if(itemMeta instanceof Damageable damageable) {
      damageable.setDamage(damageable.getDamage() - 1);
    } else {
      instrument.setAmount(instrument.getAmount() - 1);
    }
  }
  @EventHandler
  private void onFrogDart(final FrogDartEvent event) {
    Player player = event.getPlayer();
    PlayerInventory inventory = player.getInventory();
    inventory.addItem(new AbstractItem.PotionBuilder(HorsePoop.getInstance(), Material.TIPPED_ARROW,
        false).setBasePotionType(PotionType.LONG_POISON).build().getItemStack());
  }
}
