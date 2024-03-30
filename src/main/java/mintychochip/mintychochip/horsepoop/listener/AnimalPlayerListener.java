package mintychochip.mintychochip.horsepoop.listener;

import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.Collection;
import mintychochip.mintychochip.horsepoop.config.ConfigManager;
import mintychochip.mintychochip.horsepoop.HorsePoop;
import mintychochip.mintychochip.horsepoop.container.AnimalGenome;
import mintychochip.mintychochip.horsepoop.listener.display.AnimalDisplayInfo;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.inventory.Book;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
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
  private void onPlayerRightClick(final PlayerInteractEntityEvent event) {
    Entity rightClicked = event.getRightClicked();
    Player player = event.getPlayer();
    if (!player.isSneaking()) {
      return;
    }
    if (event.getHand() != EquipmentSlot.HAND) {
      return;
    }
    PersistentDataContainer persistentDataContainer = rightClicked.getPersistentDataContainer();
    if (persistentDataContainer.has(HorsePoop.GENOME_KEY)) {
      AnimalGenome genome = new Gson().fromJson(
          persistentDataContainer.get(HorsePoop.GENOME_KEY, PersistentDataType.STRING),
          AnimalGenome.class);
      AnimalDisplayInfo animalDisplayInfo = new AnimalDisplayInfo(rightClicked.getType(), genome,
          rightClicked.getName());
      Audience player1 = audiences.player(player);
      this.displayBook(player1, animalDisplayInfo);
    }

  }

  public void displayBook(@NonNull Audience target, AnimalDisplayInfo animalDisplayInfo) {
    Component bookTitle = Component.text("Encyclopedia of cats");
    Component bookAuthor = Component.text("kashike");
    Collection<Component> bookPages = new ArrayList<>();
    bookPages.add(animalDisplayInfo.getEntityTypeComponentImage(5)
        .append(Component.newline())
        .append(Component.newline())
        .append(animalDisplayInfo.entityTypeComponent())
        .append(Component.newline())
        .append(animalDisplayInfo.genderComponent())
        .append(Component.newline())
        .append(animalDisplayInfo.rarityComponent()));
    bookPages.add(Component.text("Genes: ")
        .append(animalDisplayInfo.genesComponent()));
    Book book = Book.book(bookTitle, bookAuthor, bookPages);
    target.openBook(book);
  }


}
