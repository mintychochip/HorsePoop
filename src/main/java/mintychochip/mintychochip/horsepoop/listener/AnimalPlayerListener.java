package mintychochip.mintychochip.horsepoop.listener;

import com.google.gson.Gson;
import mintychochip.genesis.Genesis;
import mintychochip.mintychochip.horsepoop.HorsePoop;
import mintychochip.mintychochip.horsepoop.container.AnimalGenome;
import mintychochip.mintychochip.horsepoop.container.Gene;
import mintychochip.mintychochip.horsepoop.container.Trait;
import mintychochip.mintychochip.horsepoop.container.attributes.GeneticAttribute;
import mintychochip.mintychochip.horsepoop.container.attributes.SheepAttribute;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Tameable;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.Map;

import static net.md_5.bungee.chat.ComponentSerializer.toJson;

public class AnimalPlayerListener implements Listener {

    @EventHandler
    private void onPlayerRightClick(final PlayerInteractEntityEvent event) {
        Entity rightClicked = event.getRightClicked();
        if(!event.getPlayer().isSneaking()) {
            return;
        }
        PersistentDataContainer persistentDataContainer = rightClicked.getPersistentDataContainer();
        if (persistentDataContainer.has(Genesis.getKey("horse"))) {
            AnimalGenome horse = Genesis.GSON.fromJson(persistentDataContainer.get(Genesis.getKey("horse"), PersistentDataType.STRING), AnimalGenome.class);
            for (Gene gene : horse.getGenes()) {
                Bukkit.broadcastMessage(gene.getTrait().getNamespacedKey());
            }

        }
    }

    private boolean checkIfEntityIsTameable(LivingEntity entity) {
        return entity instanceof Tameable;
    }
}
