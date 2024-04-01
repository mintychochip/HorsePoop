package mintychochip.mintychochip.horsepoop.commands;

import mintychochip.genesis.commands.abstraction.GenericCommandObject;
import mintychochip.genesis.commands.abstraction.SubCommand;
import mintychochip.mintychochip.horsepoop.config.ConfigManager;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

public class Reload extends GenericCommandObject implements SubCommand {

  private final ConfigManager configManager;
  public Reload(String executor, String description, ConfigManager configManager) {
    super(executor, description);
    this.configManager = configManager;
  }

  @Override
  public boolean execute(String[] strings, Player player) {
    configManager.reload();
    player.getLocation().getWorld().playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP,1,1);
    Bukkit.broadcastMessage("Finished");
    return true;
  }
}
