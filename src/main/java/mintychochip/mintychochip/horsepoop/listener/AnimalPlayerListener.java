package mintychochip.mintychochip.horsepoop.listener;

import com.google.gson.Gson;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import mintychochip.genesis.util.StringUtil;
import mintychochip.mintychochip.horsepoop.config.ConfigManager;
import mintychochip.mintychochip.horsepoop.HorsePoop;
import mintychochip.mintychochip.horsepoop.config.SettingsConfig;
import mintychochip.mintychochip.horsepoop.container.AnimalGenome;
import mintychochip.mintychochip.horsepoop.container.Gene;
import mintychochip.mintychochip.horsepoop.container.Gene.GeneType;
import mintychochip.mintychochip.horsepoop.container.Trait;
import mintychochip.mintychochip.horsepoop.container.enums.attributes.GenericTrait;
import org.bukkit.ChatColor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

public class AnimalPlayerListener implements Listener {

  private static final String DEFAULT_HEADER = "GENES";

  private static final String DEFAULT_BODY_FORMAT = "%key : %value";

  private static final String DEFAULT_CHARACTER = "-";
  private final ConfigManager configManager;

  public AnimalPlayerListener(ConfigManager configManager) {
    this.configManager = configManager;
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
      SettingsConfig settingsConfig = configManager.getSettingsConfig();
      String bodyFormat = settingsConfig.getBody();
      if (bodyFormat == null) {
        bodyFormat = DEFAULT_BODY_FORMAT;
      }
      final String finalBodyFormat = bodyFormat;
      List<String> body = genome.getGenes().stream()
          .map(gene -> StringUtil.addColorToString(this.processBody(finalBodyFormat, gene)))
          .collect(Collectors.toList());

      int max = this.getBodyMaxSize(body);

      String header = settingsConfig.getHeader();
      if (header == null) {
        header = DEFAULT_HEADER;
      }
      String character = settingsConfig.getCharacter();
      if(character == null) {
        character = DEFAULT_CHARACTER;
      }
      header = this.addAdditionalCharToString(max, header, character);
      player.sendMessage(ChatColor.GOLD + "Rarity: " + ChatColor.WHITE + genome.getRarity());
      player.sendMessage(StringUtil.addColorToString(header));
      Collections.sort(body);
      body.forEach(player::sendMessage);
      player.sendMessage(StringUtil.addColorToString(character.repeat(header.length())));
    }
  }
  private String processBody(String bodyFormat, Gene gene) {
    Trait trait = gene.getTrait();
    String s = bodyFormat.replaceAll("%key", trait.getKey())
        .replaceAll("%value", gene.toString())
        .replaceAll("%namespaced-key", trait.getNamespacedKey())
        .replaceAll("%upper", trait.toString());
    return StringUtil.capitalizeFirstLetter(s);
  }

  private String addAdditionalCharToString(int max, String headerTitle, String character) {
    int length = headerTitle.length();
    String s = headerTitle;
    if (length < max) {
      int maxHalf = (max - length) / 4;
      for (int i = 0; i < maxHalf; i++) {
        s = character + s + character;
      }
    }
    return s;
  }

  private int getBodyMaxSize(List<String> body) {
    int max = -1;
    for (String x : body) {
      max = Math.max(max, x.length());
    }
    return max;
  }
}
