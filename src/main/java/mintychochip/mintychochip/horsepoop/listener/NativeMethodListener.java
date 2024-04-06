package mintychochip.mintychochip.horsepoop.listener;

import mintychochip.mintychochip.horsepoop.api.EventCreator;
import mintychochip.mintychochip.horsepoop.api.events.FawnyEvent;
import mintychochip.mintychochip.horsepoop.api.events.FawnyInteractEntityEvent;
import mintychochip.mintychochip.horsepoop.config.ConfigManager;
import mintychochip.mintychochip.horsepoop.container.AnimalGenome;
import mintychochip.mintychochip.horsepoop.container.TraitFetcher;
import mintychochip.mintychochip.horsepoop.container.grabber.GenomeGrasper;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.AbstractHorse;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.entity.Player;

public class NativeMethodListener implements
    FauwnyListener { //listens to only native spigot api methods

  private final EventCreator eventCreator = new EventCreator();

  private final GenomeGrasper grasper;
  public NativeMethodListener(ConfigManager configManager,
      GenomeGrasper grasper) {
    this.grasper = grasper;
  }

  //  @EventHandler
//  private void onPlayerRidingMoveEvent(final PlayerMoveEvent event) {
//    if (event.isCancelled()) {
//      return;
//    }
//    Player player = event.getPlayer();
//    if (!player.isInsideVehicle()) {
//      return;
//    }
//    Entity vehicle = player.getVehicle();
//    if (!(vehicle instanceof LivingEntity livingEntity)) {
//      return;
//    }
//    PersistentDataContainer pdc = livingEntity.getPersistentDataContainer();
//    if (!pdc.has(HorsePoop.GENOME_KEY, PersistentDataType.STRING)) {
//      return;
//    }
//    AnimalGenome animalGenome = genomeGrabber.grab(livingEntity);
//    FawnyRidingMoveEvent fawnyRidingMoveEvent = eventCreator.instanceRidingMoveEvent(animalGenome,
//        livingEntity.getType(), player);
//    if (fawnyRidingMoveEvent != null) {
//      Bukkit.getPluginManager().callEvent(fawnyRidingMoveEvent);
//    }
//  }
//
//  @EventHandler(priority = EventPriority.MONITOR)
//  private void onBlockShearEntityEvent(final BlockShearEntityEvent event) {
//    if (event.isCancelled()) {
//      return;
//    }
//    Entity entity = event.getEntity();
//    if (entity instanceof LivingEntity livingEntity) {
//      AnimalGenome genome = genomeGrabber.grab(livingEntity);
//      if (genome == null) {
//        return;
//      }
//      Gene yield = traitFetcher.getGeneFromList(genome, GenericGeneTrait.YIELD);
//      if (yield == null) {
//        return;
//      }
//      this.callEvent(eventCreator.instanceShearEvent(genome, livingEntity, null,
//          event.getTool()));
//    }
//  }
//  @EventHandler(priority = EventPriority.MONITOR)
//  private void onSheepDropNaturally(final EntityDropItemEvent event) {
//    if(event.isCancelled()) {
//      return;
//    }
//    Entity entity = event.getEntity();
//    if(entity instanceof LivingEntity livingEntity) {
//      AnimalGenome grab = genomeGrabber.grab(livingEntity);
//      if(grab == null) {
//        return;
//      }
//      event.setCancelled(true);
//    }
//  }
//
  @EventHandler(priority = EventPriority.MONITOR)
  private void onPlayerInteractEntityEvent(final PlayerInteractEntityEvent event) {
    if (event.isCancelled()) {
      return;
    }
    Entity rightClicked = event.getRightClicked();

    if (!(rightClicked instanceof LivingEntity livingEntity)) {
      return;
    }
    AnimalGenome animalGenome = grasper.grab(livingEntity);
    if (animalGenome == null) {
      return;
    }
    Player player = event.getPlayer();
    PlayerInventory inventory = player.getInventory();
    FawnyInteractEntityEvent fawnyInteractEntityEvent = eventCreator.instanceInteractionEvent(
        animalGenome, livingEntity, player);
    ItemStack instrument = event.getHand() == EquipmentSlot.HAND ? inventory.getItemInMainHand()
        : inventory.getItemInOffHand();
    fawnyInteractEntityEvent.setInstrument(instrument);
    if (instrument.getType() == Material.BUCKET || instrument.getType() == Material.SHEARS) {
      event.setCancelled(true);
    }
    if (rightClicked instanceof AbstractHorse abstractHorse && player.isSneaking()) { //ghetto fixc
      event.setCancelled(true);
    }
    Bukkit.broadcastMessage("here");
    this.callEvent(fawnyInteractEntityEvent);
  }

  @Override
  public <T extends FawnyEvent> void callEvent(T event) {
    if (event != null) {
      Bukkit.getPluginManager().callEvent(event);
    }
  }
}
