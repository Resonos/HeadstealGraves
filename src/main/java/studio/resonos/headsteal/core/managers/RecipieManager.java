package studio.resonos.headsteal.core.managers;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import studio.resonos.headsteal.ResonosHeadsteal;

public class RecipieManager {

    public static Recipe createSoulFragment() {
        ItemStack item = new ItemStack(Material.SKELETON_SKULL);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.RED + "Soul Fragment");
        item.setItemMeta(meta);
        NamespacedKey key = new NamespacedKey(ResonosHeadsteal.getPlugin(ResonosHeadsteal.class), "soul_fragment");
        ShapedRecipe recipe = new ShapedRecipe(key, item);
        recipe.shape("DRD", "RDR", "DRD");
        recipe.setIngredient('D', Material.DIAMOND_BLOCK);
        recipe.setIngredient('R', Material.REDSTONE_BLOCK);

       return recipe;
    }

    public static Recipe createBeacon() {
        ItemStack item = new ItemStack(Material.BEACON);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.GREEN + "Revive Beacon");
        item.setItemMeta(meta);
        NamespacedKey key = new NamespacedKey(ResonosHeadsteal.getPlugin(ResonosHeadsteal.class), "revive_beacon");
        ShapedRecipe recipe = new ShapedRecipe(key, item);
        recipe.shape("FCF", "ISI", "FCF");
        recipe.setIngredient('F', new RecipeChoice.ExactChoice(RecipieManager.createSoulFragment().getResult()));
        recipe.setIngredient('C', Material.CRYING_OBSIDIAN);
        recipe.setIngredient('S', Material.NETHER_STAR);
        recipe.setIngredient('I', Material.NETHERITE_INGOT);
        return recipe;
    }

}
