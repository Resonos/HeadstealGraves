package studio.resonos.grave.core;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.*;
import org.bukkit.inventory.meta.ItemMeta;
import studio.resonos.grave.Grave;

public class RecipieManager {

    public static Recipe createSoulFragment() {

        // Our custom variable which we will be changing around.
        ItemStack item = new ItemStack(Material.SKELETON_SKULL);

        // The meta of the diamond sword where we can change the name, and properties of the item.
        ItemMeta meta = item.getItemMeta();

        // We will initialise the next variable after changing the properties of the sword

        // This sets the name of the item.
        meta.setDisplayName(ChatColor.RED + "Soul Fragment");

        // Set the meta of the sword to the edited meta.
        item.setItemMeta(meta);
        // create a NamespacedKey for your recipe
        NamespacedKey key = new NamespacedKey(Grave.getPlugin(Grave.class), "soul_fragment");

        // Create our custom recipe variable
        ShapedRecipe recipe = new ShapedRecipe(key, item);

        // Here we will set the places. E and S can represent anything, and the letters can be anything. Beware; this is case sensitive.
        recipe.shape("DRD",
                "RDR",
                "DRD");

        // Set what the letters represent.
        // E = Emerald, S = Stick
        recipe.setIngredient('D', Material.DIAMOND_BLOCK);
        recipe.setIngredient('R', Material.REDSTONE_BLOCK);

       return recipe;
    }

    public static Recipe createBeacon() {
        // Our custom variable which we will be changing around.
        ItemStack item = new ItemStack(Material.BEACON);

        // The meta of the diamond sword where we can change the name, and properties of the item.
        ItemMeta meta = item.getItemMeta();

        // We will initialise the next variable after changing the properties of the sword

        // This sets the name of the item.
        meta.setDisplayName(ChatColor.GREEN + "Revive Beacon");

        // Set the meta of the sword to the edited meta.
        item.setItemMeta(meta);

        // create a NamespacedKey for your recipe
        NamespacedKey key = new NamespacedKey(Grave.getPlugin(Grave.class), "revive_beacon");

        // Create our custom recipe variable
        ShapedRecipe recipe = new ShapedRecipe(key, item);

        // Here we will set the places. E and S can represent anything, and the letters can be anything. Beware; this is case sensitive.
        recipe.shape("FCF", "ISI", "FCF");

        // Set what the letters represent.
        // E = Emerald, S = Stick
        recipe.setIngredient('F', new RecipeChoice.ExactChoice(RecipieManager.createSoulFragment().getResult()));
        recipe.setIngredient('C', Material.CRYING_OBSIDIAN);
        recipe.setIngredient('S', Material.NETHER_STAR);
        recipe.setIngredient('I', Material.NETHERITE_INGOT);
        return recipe;
    }

    /*private void IronIngot() {
        ItemStack ironIngot = RecipieManager.createSoulFragment().getResult();
        RecipeChoice ironFragment = new RecipeChoice.ExactChoice(RecipieManager.createSoulFragment().getResult());
        NamespacedKey key = new NamespacedKey(plugin, "ironingot"); // MAKE SURE NAMESPACE KEY DOESN'T HAVE UPPERCASE BTW SCYYE
        ShapelessRecipe recipe = new ShapelessRecipe(key, ironIngot);
        recipe.addIngredient(ironFragment);
        Bukkit.addRecipe(recipe);
    }*/
}
