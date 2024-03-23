package mintychochip.mintychochip.horsepoop.commands;

import mintychochip.genesis.commands.abstraction.GenericCommandObject;
import mintychochip.genesis.commands.abstraction.SubCommand;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class EnchantCommand extends GenericCommandObject implements SubCommand {
    public EnchantCommand(String executor, String description) {
        super(executor, description);
    }

    @Override
    public boolean execute(String[] strings, Player player) {
        ItemStack itemInMainHand = player.getInventory().getItemInMainHand();
        ItemMeta itemMeta = itemInMainHand.getItemMeta();
        itemMeta.addEnchant(Enchantment.LOOT_BONUS_BLOCKS,3, true);
        itemInMainHand.setItemMeta(itemMeta);
        return true;
    }
}
