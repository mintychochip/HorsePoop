package mintychochip.mintychochip.horsepoop.listener;

import mintychochip.genesis.Genesis;
import mintychochip.genesis.config.abstraction.GenesisConfigurationSection;
import mintychochip.genesis.container.AppraisableItemData;
import mintychochip.genesis.container.items.AbstractItem;
import mintychochip.genesis.container.items.actions.ActionPacket;
import mintychochip.genesis.container.items.actions.EventAction;
import mintychochip.mintychochip.horsepoop.ConfigManager;
import mintychochip.mintychochip.horsepoop.HorsePoop;
import mintychochip.mintychochip.horsepoop.container.AnimalGenome;
import mintychochip.mintychochip.horsepoop.container.Gene;
import mintychochip.mintychochip.horsepoop.container.enums.MendelianType;
import mintychochip.mintychochip.horsepoop.container.enums.attributes.GenericTrait;
import mintychochip.mintychochip.horsepoop.container.enums.attributes.specific.CowTrait;
import mintychochip.mintychochip.horsepoop.container.enums.attributes.specific.SheepTrait;
import mintychochip.mintychochip.horsepoop.util.DataExtractor;
import org.bukkit.*;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Sheep;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockShearEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.event.player.PlayerShearEntityEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

public class AnimalDeathListener implements Listener {

    private final ConfigManager configManager;
    public AnimalDeathListener(ConfigManager configManager) {
        this.configManager = configManager;
    }
    @EventHandler
    private void onFawnyAnimalDeath(final EntityDeathEvent event) {
        LivingEntity entity = event.getEntity();
        if(!configManager.getHorseConfig().getEnabledEntityTypes().contains(entity.getType().toString())) {
            return;
        }
        PersistentDataContainer persistentDataContainer = entity.getPersistentDataContainer();
        if(!persistentDataContainer.has(HorsePoop.GENOME_KEY)) {
            return;
        }
        String s = persistentDataContainer.get(HorsePoop.GENOME_KEY, PersistentDataType.STRING);
        AnimalGenome animalGenome = Genesis.GSON.fromJson(s, AnimalGenome.class);
        Gene yield = animalGenome.getGeneFromTrait(GenericTrait.YIELD);
        if(yield == null) {
            return;
        }
        switch (entity.getType()) {
            case SHEEP -> {

            }
        }
    }

    @EventHandler (priority = EventPriority.MONITOR)
    private void onFawnyAnimalSheared(final PlayerShearEntityEvent event) { //need blockShear too
        if(event.isCancelled()) {
            return;
        }
        Entity entity = event.getEntity();
        if(!(entity instanceof LivingEntity livingEntity)) {
            return;
        }
        Location entityLocation = entity.getLocation();
        AnimalGenome animalGenome = DataExtractor.extractGenomicData(livingEntity);
        if(animalGenome == null) {
            return;
        }
        Gene yield = animalGenome.getGeneFromTrait(GenericTrait.YIELD);
        if(yield == null) {
            return;
        }
        int rand = calculateRandomYield(Genesis.GSON.fromJson(yield.getValue(), int.class),event.getItem());
        switch (livingEntity.getType()) {
            case SHEEP -> {
                event.setCancelled(true);
                Sheep sheep = (Sheep) livingEntity;
                sheep.setSheared(true);
                DyeColor dyeColor = SheepTrait.calculateDyeColor(animalGenome, livingEntity);
                if(dyeColor == null) {
                    return;
                }
                World world = entityLocation.getWorld();
                if(world == null) {
                    return;
                }
                ItemStack wool = new ItemStack(Material.valueOf(dyeColor + "_WOOL"));
                Bukkit.broadcastMessage(rand + "");
                wool.setAmount(rand);
                world.playSound(entityLocation,Sound.ENTITY_SHEEP_SHEAR,1,1);
                world.dropItem(entityLocation,wool);
            }
        }
    }
    @EventHandler
    private void onFawnyAnimalBlockShearEvent(final BlockShearEntityEvent event) {
        if(event.isCancelled()) {
            return;
        }
        Entity entity = event.getEntity();
        if(!(entity instanceof LivingEntity livingEntity)) {
            return;
        }
        Location entityLocation = entity.getLocation();
        AnimalGenome animalGenome = DataExtractor.extractGenomicData(livingEntity);
        if(animalGenome == null) {
            return;
        }
        Gene yield = animalGenome.getGeneFromTrait(GenericTrait.YIELD);
        if(yield == null) {
            return;
        }
        int rand = calculateRandomYield(Genesis.GSON.fromJson(yield.getValue(), int.class),event.getTool());
        switch (livingEntity.getType()) {
            case SHEEP -> {
                event.setCancelled(true);
                Sheep sheep = (Sheep) livingEntity;
                sheep.setSheared(true);
                DyeColor dyeColor = SheepTrait.calculateDyeColor(animalGenome, livingEntity);
                if(dyeColor == null) {
                    return;
                }
                World world = entityLocation.getWorld();
                if(world == null) {
                    return;
                }
                ItemStack wool = new ItemStack(Material.valueOf(dyeColor + "_WOOL"));
                wool.setAmount(rand);
                world.playSound(entityLocation,Sound.ENTITY_SHEEP_SHEAR,1,1);
                world.dropItem(entityLocation,wool);
            }
        }
    }
    @EventHandler(priority = EventPriority.MONITOR)
    private void fawnyCowHasMilkGene(final PlayerInteractEntityEvent event) {
        if(event.isCancelled()) {
            return;
        }
        Entity rightClicked = event.getRightClicked();
        if(!configManager.getHorseConfig().isTraitEnabled(event.getRightClicked().getType(),CowTrait.MILK)) {
            return;
        }
        if(!(rightClicked instanceof LivingEntity livingEntity)) {
            return;
        }
        AnimalGenome animalGenome = DataExtractor.extractGenomicData(livingEntity);
        if(animalGenome == null) {
            return;
        }
        Gene milkTrait = animalGenome.getGeneFromTrait(CowTrait.MILK);
        if(milkTrait == null) {
            return;
        }
        ItemStack item;
        Player player = event.getPlayer();
        PlayerInventory inventory = player.getInventory();
        if(event.getHand() == EquipmentSlot.HAND) {
            item = inventory.getItemInMainHand();
        } else {
            item = inventory.getItemInOffHand();
        }
        if(item.getType() != Material.BUCKET) {
            return;
        }
        event.setCancelled(true);
        int i = item.getAmount(); //count of buckets
        item.setAmount(--i);
        if(milkTrait.getPhenotype() == MendelianType.MENDELIAN_RECESSIVE) {
            GenesisConfigurationSection mainConfigurationSection = configManager.getItemConfig().getMainConfigurationSection("spider-milk");
            AbstractItem.EmbeddedDataBuilder classic = new AbstractItem.EmbeddedDataBuilder(HorsePoop.getInstance(), mainConfigurationSection, false, "CLASSIC", new AppraisableItemData())
                    .addConsumeEvent("monch",new ActionPacket(EventAction.EXECUTE_COMMAND,"playsound minecraft:dreamy.buss voice @a"));
            inventory.addItem(classic.defaultBuild().getItemStack());
        } else {
            inventory.addItem(new ItemStack(Material.MILK_BUCKET));
        }
    }
    private int calculateRandomYield(int max, ItemStack shear) {
        int min = 1;
        Integer fortuneLevel = shear.getEnchantments().get(Enchantment.LOOT_BONUS_BLOCKS);
        if(fortuneLevel != null) {
            min = calculateMaxYield(fortuneLevel,min);
            max = calculateMaxYield(fortuneLevel,max);
            if(min < 1) {
                min = 1;
            }
        }
        if(min >= max) {
            max = min + 1;
        }
        return Genesis.RANDOM.nextInt(min,max);
    }
    private int calculateMaxYield(int fortuneLevel, int i) {
        return (int) (i * (((double) 1 / (fortuneLevel + 2)) + ((double) (fortuneLevel + 1) / 2)));
    }
}
