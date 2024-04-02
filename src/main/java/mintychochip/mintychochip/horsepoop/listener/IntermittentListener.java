package mintychochip.mintychochip.horsepoop.listener;

public class IntermittentListener  { //listeners that plan to call other fawny events

//  private final EventCreator eventCreator;
//  public IntermittentListener(ConfigManager configManager,
//      TraitFetcher traitFetcher, EventCreator eventCreator) {
//    super(configManager, traitFetcher);
//    this.eventCreator = eventCreator;
//  }
//
//
//  @EventHandler
//  private void onFawnyInteractEvent(final FawnyInteractEntityEvent event) {
//    AnimalGenome animalGenome = event.getAnimalGenome();
//    Player player = event.getPlayer();
//    if (player.isSneaking()) {
//      return;
//    }
//    LivingEntity livingEntity = event.getLivingEntity();
//    if (livingEntity instanceof Ageable ageable) {
//      if (!ageable.isAdult()) {
//        return;
//      }
//    }
//    Gene milkTrait = traitFetcher.getGeneFromList(animalGenome, CowGeneTrait.STRAWBERRY_MILK);
//    if (milkTrait == null) {
//      return;
//    }
////    if (animalGenome.getGender() == Gender.MALE && !configManager.getSettingsConfig()
////        .getMaleMilked()) {
////      player.sendMessage(ChatColor.RED + "You can't milk a male!");
////      event.setCancelled(true);
////      return;
////    }
//    ItemStack instrument = event.getInstrument();
//    if (instrument == null) {
//      return;
//    }
//    if (instrument.getType() != Material.BUCKET) {
//      return;
//    }
//    int i = instrument.getAmount(); //count of buckets
//    if (i < 1) {
//      return;
//    }
//    this.callEvent(eventCreator.instanceMilkEvent(animalGenome,
//        event.getEntityType(), player, instrument));
//  }
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
