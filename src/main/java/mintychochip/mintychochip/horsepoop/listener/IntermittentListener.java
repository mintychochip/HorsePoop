package mintychochip.mintychochip.horsepoop.listener;

import mintychochip.mintychochip.horsepoop.api.EventCreator;
import mintychochip.mintychochip.horsepoop.api.Fetcher;
import mintychochip.mintychochip.horsepoop.api.Gene;
import mintychochip.mintychochip.horsepoop.api.Intrinsic;
import mintychochip.mintychochip.horsepoop.api.events.FawnyInteractEntityEvent;
import mintychochip.mintychochip.horsepoop.config.ConfigManager;
import mintychochip.mintychochip.horsepoop.container.AnimalGenome;
import mintychochip.mintychochip.horsepoop.container.BaseTrait;
import mintychochip.mintychochip.horsepoop.container.enums.Gender;
import mintychochip.mintychochip.horsepoop.container.enums.traits.CowGene;
import mintychochip.mintychochip.horsepoop.container.enums.traits.IntrinsicTraitEnum;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Ageable;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

public class IntermittentListener implements Listener { //listeners that plan to call other fawny events

  private final EventCreator eventCreator;

  private final Fetcher<Gene> geneFetcher;

  private final Fetcher<Intrinsic> intrinsicFetcher;

  private final ConfigManager configManager;
  public IntermittentListener(ConfigManager configManager,
      Fetcher<Gene> geneFetcher,Fetcher<Intrinsic> intrinsicFetcher, EventCreator eventCreator) {
    this.geneFetcher = geneFetcher;
    this.intrinsicFetcher = intrinsicFetcher;
    this.configManager = configManager;
    this.eventCreator = eventCreator;
  }


  @EventHandler
  private void onFawnyInteractEvent(final FawnyInteractEntityEvent event) {
    AnimalGenome animalGenome = event.getAnimalGenome();
    Player player = event.getPlayer();
    if (player.isSneaking()) {
      return;
    }
    LivingEntity livingEntity = event.getLivingEntity();
    if (livingEntity instanceof Ageable ageable) {
      if (!ageable.isAdult()) {
        return;
      }
    }
    BaseTrait<Gene> milkTrait = geneFetcher.getTraitFromList(animalGenome.getGenes(),
        CowGene.STRAWBERRY_MILK);
    if (milkTrait == null) {
      return;
    }
    if (intrinsicFetcher.getEnumValue(animalGenome.getIntrinsics(), IntrinsicTraitEnum.GENDER,Gender.class) == Gender.MALE && !configManager.getSettingsConfig()
        .getMaleMilked()) {
      player.sendMessage(ChatColor.RED + "You can't milk a male!");
      event.setCancelled(true);
      return;
    }
    ItemStack instrument = event.getInstrument();
    if (instrument == null) {
      return;
    }
    if (instrument.getType() != Material.BUCKET) {
      return;
    }
    int i = instrument.getAmount(); //count of buckets
    if (i < 1) {
      return;
    }
    Bukkit.getPluginManager().callEvent(eventCreator.instanceMilkEvent(animalGenome,
        event.getEntityType(), player, instrument));
  }
//  @EventHandler
//  private void onShearBird(final FawnyInteractEntityEvent event) {
//    AnimalGenome animalGenome = event.getAnimalGenome();
//    Player player = event.getPlayer();
//    if(player.isSneaking()) {
//      return;
//    }
//    LivingEntity livingEntity = event.getLivingEntity();
//    if(livingEntity instanceof Tameable tameable) {
//      if(tameable.getOwner() != player) {
//        return;
//      }
//    }
//    ItemStack instrument = event.getInstrument();
//    if (instrument == null) {
//      return;
//    }
//    if (instrument.getType() != Material.SHEARS) {
//      return;
//    }
//    ItemMeta itemMeta = instrument.getItemMeta();
//    if(itemMeta instanceof Damageable damageable && event.getPlayer().getGameMode() == GameMode.SURVIVAL) {
//      damageable.setDamage(damageable.getDamage() + 1);
//      instrument.setItemMeta(damageable);
//    }
//    this.callEvent(eventCreator.instanceShearEvent(animalGenome, event.getLivingEntity(), player,
//        instrument));
//  }
}
