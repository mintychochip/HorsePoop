package mintychochip.mintychochip.horsepoop.listener;

import mintychochip.genesis.Genesis;
import mintychochip.mintychochip.horsepoop.ConfigManager;
import mintychochip.mintychochip.horsepoop.HorsePoop;
import mintychochip.mintychochip.horsepoop.container.AnimalGenome;
import mintychochip.mintychochip.horsepoop.container.Gene;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Tameable;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

public class AnimalPlayerListener implements Listener {

    private final ConfigManager configManager;
    public AnimalPlayerListener(ConfigManager configManager) {
        this.configManager = configManager;
    }
    @EventHandler
    private void onPlayerRightClick(final PlayerInteractEntityEvent event) {
        Entity rightClicked = event.getRightClicked();
        if(!event.getPlayer().isSneaking()) {
            return;
        }
        if(event.getHand() != EquipmentSlot.HAND) {
            return;
        }
        PersistentDataContainer persistentDataContainer = rightClicked.getPersistentDataContainer();

        if (persistentDataContainer.has(HorsePoop.GENOME_KEY)) {
            AnimalGenome horse = Genesis.GSON.fromJson(persistentDataContainer.get(HorsePoop.GENOME_KEY, PersistentDataType.STRING), AnimalGenome.class);
            for(int i = 0; i < 1; i++) {
                event.getPlayer().sendMessage("");
            }
            event.getPlayer().sendMessage("-----GENES-----");
            for (Gene gene : horse.getGenes()) {
                Bukkit.broadcastMessage(gene.toString());
            }

        }
    }

    private boolean checkIfEntityIsTameable(LivingEntity entity) {
        return entity instanceof Tameable;
    }
}
