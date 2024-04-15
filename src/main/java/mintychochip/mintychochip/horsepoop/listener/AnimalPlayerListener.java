package mintychochip.mintychochip.horsepoop.listener;

import mintychochip.mintychochip.horsepoop.api.events.FawnyInteractEntityEvent;
import mintychochip.mintychochip.horsepoop.config.ConfigManager;
import mintychochip.mintychochip.horsepoop.container.AnimalGenome;
import mintychochip.mintychochip.horsepoop.listener.display.DisplayBook;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class AnimalPlayerListener implements Listener {
  private final ConfigManager configManager;

  private final BukkitAudiences audiences;

  public AnimalPlayerListener(ConfigManager configManager, BukkitAudiences bukkitAudiences) {
    this.configManager = configManager;
    this.audiences = bukkitAudiences;
  }

  @EventHandler
  private void onPlayerRightClick(final FawnyInteractEntityEvent event) {
    Player player = event.getPlayer();
    if (player.isSneaking()) {
      AnimalGenome genome = event.getAnimalGenome();
      DisplayBook displayBook = new DisplayBook.PageBuilder().addTitlePage(event.getLivingEntity().getType())
          .addTableOfContentsPage()
          .addTraitPages("[Genes]",genome.getGenes())
          .addTraitPages("[Phenotypics]",genome.getPhenotypics())
          .addTraitPages("[Intrinsics]",genome.getIntrinsics())
          .create("pp","pp",false);
      Audience player1 = audiences.player(player);
      player1.openBook(displayBook.getBook());
    }
  }
}
