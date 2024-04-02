package mintychochip.mintychochip.horsepoop.listener;

import java.util.ArrayList;
import java.util.Collection;
import mintychochip.mintychochip.horsepoop.api.events.FawnyInteractEntityEvent;
import mintychochip.mintychochip.horsepoop.config.ConfigManager;
import mintychochip.mintychochip.horsepoop.container.AnimalGenome;
import mintychochip.mintychochip.horsepoop.listener.display.AnimalDisplayInfo;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.inventory.Book;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.checkerframework.checker.nullness.qual.NonNull;

public class AnimalPlayerListener implements Listener {

  private static final String DEFAULT_HEADER = "GENES";

  private static final String DEFAULT_BODY_FORMAT = "%key : %value";

  private static final String DEFAULT_CHARACTER = "-";
  private final ConfigManager configManager;

  private final BukkitAudiences audiences;

  public AnimalPlayerListener(ConfigManager configManager, BukkitAudiences bukkitAudiences) {
    this.configManager = configManager;
    this.audiences = bukkitAudiences;
  }

  @EventHandler
  private void onPlayerRightClick(final FawnyInteractEntityEvent event) {
    Player player = event.getPlayer();    Bukkit.broadcastMessage("here");

    if (player.isSneaking()) {
      LivingEntity livingEntity = event.getLivingEntity();
      AnimalGenome genome = event.getAnimalGenome();
      AnimalDisplayInfo animalDisplayInfo = new AnimalDisplayInfo(event.getEntityType(), genome,
          "asd");
      Audience player1 = audiences.player(player);
      this.displayBook(player1, animalDisplayInfo);
    }
  }


  public void displayBook(@NonNull Audience target, AnimalDisplayInfo animalDisplayInfo) {
    Component bookTitle = Component.text("Encyclopedia of cats");
    Component bookAuthor = Component.text("kashike");
    Collection<Component> bookPages = new ArrayList<>();
    bookPages.add(animalDisplayInfo.getEntityTypeComponentImage(3)
        .append(Component.newline())
        .append(Component.newline())
        .append(Component.newline()));
//    bookPages.add(Component.text("Characteristics: ")
//        .append(animalDisplayInfo.characteristicComponent()));
//    bookPages.add(Component.text("Genes: ")
//        .append(animalDisplayInfo.genesComponent()));
    Book book = Book.book(bookTitle, bookAuthor, bookPages);
    target.openBook(book);
  }


}
