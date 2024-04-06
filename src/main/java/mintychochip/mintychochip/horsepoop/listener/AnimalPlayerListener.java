package mintychochip.mintychochip.horsepoop.listener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import mintychochip.mintychochip.horsepoop.api.TraitEnum;
import mintychochip.mintychochip.horsepoop.api.events.FawnyInteractEntityEvent;
import mintychochip.mintychochip.horsepoop.config.ConfigManager;
import mintychochip.mintychochip.horsepoop.container.AnimalGenome;
import mintychochip.mintychochip.horsepoop.container.BaseTrait;
import mintychochip.mintychochip.horsepoop.listener.display.DisplayBook;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.inventory.Book;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Display;
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
    Player player = event.getPlayer();
    Bukkit.broadcastMessage("here");

    if (player.isSneaking()) {
      LivingEntity livingEntity = event.getLivingEntity();
      AnimalGenome genome = event.getAnimalGenome();
      DisplayBook displayBook = new DisplayBook(event.getEntityType(), genome);
      Audience player1 = audiences.player(player);
      this.displayBook(player1,displayBook);
    }
  }
  private final Map<String, Integer> pageNumbers = new HashMap<>();


  public void displayBook(@NonNull Audience target, DisplayBook animalDisplayInfo) {
    Component bookTitle = Component.text("Encyclopedia of cats");
    Component bookAuthor = Component.text("kashike");
    Collection<Component> bookPages = new ArrayList<>();
    AnimalGenome animalGenome = animalDisplayInfo.getAnimalGenome();

    bookPages.add(animalDisplayInfo.createBookPage("Genes",animalGenome.getGenes()));
    bookPages.add(animalDisplayInfo.createBookPage("Phenotypics",animalGenome.getPhenotypics()));
    bookPages.add(animalDisplayInfo.createBookPage("Intrinsics",animalGenome.getIntrinsics()));
    Collection<Component> bookPage = new ArrayList<>();
    bookPage.add(animalDisplayInfo.getEntityTypeComponentImage(3)
        .append(Component.newline())
        .append(animalDisplayInfo.pageButtonComponent("Genes"))
        .append(Component.newline())
        .append(animalDisplayInfo.pageButtonComponent("Phenotypics"))
        .append(Component.newline())
        .append(animalDisplayInfo.pageButtonComponent("Intrinsics")));
    bookPage.addAll(bookPages);
    Book book = Book.book(bookTitle, bookAuthor, bookPage);
    target.openBook(book);
  }
}
