package mintychochip.mintychochip.horsepoop.listener;

import mintychochip.mintychochip.horsepoop.HorsePoop;
import mintychochip.mintychochip.horsepoop.api.EventCreator;
import mintychochip.mintychochip.horsepoop.api.Fetcher;
import mintychochip.mintychochip.horsepoop.api.events.AbstractInstrumentEvent;
import mintychochip.mintychochip.horsepoop.api.events.FawnyInteractEntityEvent;
import mintychochip.mintychochip.horsepoop.api.events.finalevents.FawnyShearEvent;
import mintychochip.mintychochip.horsepoop.api.events.finalevents.FrogDartEvent;
import mintychochip.mintychochip.horsepoop.api.markers.Gene;
import mintychochip.mintychochip.horsepoop.api.markers.Intrinsic;
import mintychochip.mintychochip.horsepoop.config.ConfigManager;
import mintychochip.mintychochip.horsepoop.container.AnimalGenome;
import mintychochip.mintychochip.horsepoop.container.BaseTrait;
import mintychochip.mintychochip.horsepoop.container.CooldownContainer;
import mintychochip.mintychochip.horsepoop.container.CooldownManager;
import mintychochip.mintychochip.horsepoop.container.enums.Gender;
import mintychochip.mintychochip.horsepoop.container.enums.traits.CowGene;
import mintychochip.mintychochip.horsepoop.container.enums.traits.IntrinsicTraitEnum;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Ageable;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Tameable;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;

public class IntermittentListener implements
    Listener { //listeners that plan to call other fawny events

  private final EventCreator eventCreator;

  private final Fetcher<Gene> geneFetcher;

  private final Fetcher<Intrinsic> intrinsicFetcher;

  private final ConfigManager configManager;

  private final CooldownManager cooldownManager = new CooldownManager();

  public IntermittentListener(ConfigManager configManager,
      Fetcher<Gene> geneFetcher, Fetcher<Intrinsic> intrinsicFetcher, EventCreator eventCreator) {
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
    if (intrinsicFetcher.getEnumValue(animalGenome.getIntrinsics(), IntrinsicTraitEnum.GENDER,
        Gender.class) == Gender.MALE && !configManager.getSettingsConfig()
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
//    Bukkit.getPluginManager().callEvent(eventCreator.instanceMilkEvent(animalGenome,
//        event.getEntityType(), player, instrument));
  }
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
    CooldownContainer container = new CooldownContainer(
        new NamespacedKey(HorsePoop.getInstance(), "frogdart"), 20);
    cooldownManager.trigerEventOffCooldown(livingEntity,container, new FrogDartEvent(animalGenome, livingEntity, player,
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
  private void onShearBird(final FawnyInteractEntityEvent event) {
    AnimalGenome animalGenome = event.getAnimalGenome();
    Player player = event.getPlayer();
    if (player.isSneaking()) {
      return;
    }
    LivingEntity livingEntity = event.getLivingEntity();
    if (livingEntity instanceof Tameable tameable) {
      if (tameable.getOwner() != player) {
        return;
      }
    }
    if (livingEntity.isDead()) {
      return;
    }
    ItemStack instrument = event.getInstrument();
    if (instrument == null) {
      return;
    }
    if (instrument.getType() != Material.SHEARS) {
      return;
    }
    CooldownContainer cooldownContainer = new CooldownContainer(
        new NamespacedKey(HorsePoop.getInstance(), "birdcooldown"), 10);
    cooldownManager.trigerEventOffCooldown(livingEntity, cooldownContainer,
        new FawnyShearEvent(animalGenome, event.getLivingEntity(), player, instrument));
  }
}
