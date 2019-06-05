package me.yonatan.yWarps.cmds;

import me.yonatan.yWarps.yWarps;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import java.io.IOException;
import java.util.ArrayList;

public class GoCmd implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (!(commandSender instanceof Player)) {
            commandSender.sendMessage(ChatColor.RED + "Must be a player inorder to use this command.");
            return true;
        }

        Player player = (Player) commandSender;
        String uuid = player.getUniqueId().toString();

        if (strings.length == 1) {
            String warpName = strings[0];

            if (warpName.equalsIgnoreCase("list")) {
                ArrayList<String> list = new ArrayList<>();
                ConfigurationSection inventorySection = yWarps.get().getWarpData().getConfigurationSection(uuid);
                if (inventorySection == null){
                    player.sendMessage(ChatColor.GRAY + "No warps are set.");
                    return true;
                }
                for (String key : inventorySection.getKeys(false)) {
                    list.add(key);
                }
                player.sendMessage(list.toString());
            } else {
                if (yWarps.get().getWarpData().getString(uuid + "." + warpName + ".WORLD") == null) {
                    player.sendMessage(ChatColor.RED + "Warp not found.");
                    return true;
                }
                String world = yWarps.get().getWarpData().getString(uuid + "." + warpName + ".WORLD");
                double x = yWarps.get().getWarpData().getDouble(uuid + "." + warpName + ".X");
                double y = yWarps.get().getWarpData().getDouble(uuid + "." + warpName + ".Y");
                double z = yWarps.get().getWarpData().getDouble(uuid + "." + warpName + ".Z");
                float yaw = (float) (yWarps.get().getWarpData().getDouble(uuid + "." + warpName + ".YAW"));
                float pitch = (float) (yWarps.get().getWarpData().getDouble(uuid + "." + warpName + ".PITCH"));
                Location location = new Location(Bukkit.getWorld(world), x, y, z, yaw, pitch);
                player.teleport(location);
            }
            return true;
        } else if (strings.length == 2) {
            if (strings[0].equalsIgnoreCase("set")) {
                String warpName = strings[1];
                Location location = player.getLocation();
                yWarps.get().getWarpData().set(uuid + "." + warpName + ".WORLD", location.getWorld().getName());
                yWarps.get().getWarpData().set(uuid + "." + warpName + ".X", location.getX());
                yWarps.get().getWarpData().set(uuid + "." + warpName + ".Y", location.getY());
                yWarps.get().getWarpData().set(uuid + "." + warpName + ".Z", location.getZ());
                yWarps.get().getWarpData().set(uuid + "." + warpName + ".YAW", location.getYaw());
                yWarps.get().getWarpData().set(uuid + "." + warpName + ".PITCH", location.getPitch());
                try {
                    yWarps.get().saveWarps();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                player.sendMessage(ChatColor.GREEN + "Warp " + warpName + " set.");
                return true;
            } else if (strings[0].equalsIgnoreCase("del") || strings[0].equalsIgnoreCase("delete") ||
                    strings[0].equalsIgnoreCase("remove")) {
                if (yWarps.get().getWarpData().get(uuid + "." + strings[1]) == null) {
                    player.sendMessage(ChatColor.RED + "Warp not found.");
                    return true;
                }
                yWarps.get().getWarpData().set(uuid + "." + strings[1], null);
                player.sendMessage(ChatColor.GREEN + "Warp removed.");
                return true;
            } else {
                commandSender.sendMessage(ChatColor.RED + "/warp <set,delete> <name>");
                return true;
            }
        } else {
            commandSender.sendMessage(ChatColor.RED + "/warp <warp name>");
            return true;
        }
    }
}
