package studio.resonos.headsteal.core.managers;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import org.bukkit.block.data.type.Wall;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.EulerAngle;
import studio.resonos.headsteal.ResonosHeadsteal;
import studio.resonos.headsteal.core.utils.LocationUtil;

public class GraveManager {

    public static void createGrave(Player p, int x , int y , int z) {
        FileConfiguration configuration = ResonosHeadsteal.getPlugin(ResonosHeadsteal.class).getPlayerconfig().getConfiguration();
        String path = "players." + p.getName();
        configuration.set(path + ".headsteal", LocationUtil.serialize(new Location(p.getWorld(), x, y, z)));
        int offsetx = x - 1;
        int offsety = y - 1;
        double offsetya = y - 1.5D;
        Location loc = new Location(p.getWorld(), x, y, z);
        Bukkit.getWorld(p.getWorld().getName()).strikeLightningEffect(loc);
        p.getPlayer().getWorld().getBlockAt(new Location(p.getWorld(), x, offsety, z)).setType(Material.PODZOL);
        p.getPlayer().getWorld().getBlockAt(new Location(p.getWorld(), offsetx, y,z)).setType(Material.COBBLESTONE_WALL);
        Block wallter = p.getPlayer().getWorld().getBlockAt(new Location(p.getWorld(), offsetx, y,z));
        BlockState state = wallter.getState();
        Wall wall = (Wall)state.getBlockData();
        wall.setHeight(BlockFace.NORTH, Wall.Height.LOW);
        wall.setHeight(BlockFace.SOUTH, Wall.Height.LOW);
        state.setBlockData(wall);
        state.update();
        ArmorStand stand = (ArmorStand) p.getWorld().spawnEntity(new Location(p.getWorld(), (x + 0.5D), (offsetya - 0.2D), (z + 0.5D)) , EntityType.ARMOR_STAND);
        stand.setInvulnerable(true);
        stand.setVisible(false);
        stand.setCustomName(p.getName());
        stand.setCustomNameVisible(true);
        stand.setHelmet(new ItemStack(Material.SKELETON_SKULL));
        stand.addEquipmentLock(EquipmentSlot.HEAD, ArmorStand.LockType.REMOVING_OR_CHANGING);
        stand.setRotation(33, 0);
        stand.setHeadPose(new EulerAngle(Math.toRadians(291),Math.toRadians(306),Math.toRadians(0)));
        stand.setGravity(false);
        stand.setCollidable(false);
        for (Player player: Bukkit.getOnlinePlayers()) {
            player.playSound(player.getLocation(), Sound.ENTITY_ENDER_DRAGON_DEATH, 1.0f, 1.0f);
        }
    }
}
