package org.islandmanager.islandManager;

import com.bgsoftware.superiorskyblock.api.SuperiorSkyblockAPI;
import com.bgsoftware.superiorskyblock.api.events.IslandCreateEvent;
import com.bgsoftware.superiorskyblock.api.island.Island;
import com.bgsoftware.superiorskyblock.api.key.Key;
import com.bgsoftware.superiorskyblock.api.wrappers.SuperiorPlayer;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.CommandExecutor;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Map;

import static org.islandmanager.islandManager.UserInterface.*;

public final class IslandManager extends JavaPlugin implements Listener, CommandExecutor {

    public static String prefix = "&3[Island Manager]";

    @Override
    public void onEnable() {

        RegisterAllEvents();

        consoleLog(sendText(prefix+" &aBy Graymontt &2v1.0"));

    }

    public static IslandManager getMainPlugin(){
        return IslandManager.getPlugin(IslandManager.class);
    }

    @Override
    public void onDisable() {

    }

    void RegisterAllEvents(){
        getServer().getPluginManager().registerEvents(this, this);

        consoleLog(prefix+" &aAll Events has been intialized!");
    }

    @EventHandler
    public void OnPlayerJoin(PlayerJoinEvent event){
        Player player = event.getPlayer();

        executeConsoleCommand("is admin recalculate "+player.getName());
        consoleLog(sendText(prefix+" &aRecalculating island count for &2"+player.getName()));
    }

    @EventHandler
    public void OnTeleport(PlayerTeleportEvent event) {
        if (event.getCause() == PlayerTeleportEvent.TeleportCause.PLUGIN){
            SetIslandUpgrades(event.getPlayer());
        }
    }

    @EventHandler
    public void OnIslandCreate(IslandCreateEvent event){
        Player player = Bukkit.getPlayer(event.getPlayer().getUniqueId());
        SuperiorPlayer superiorPlayer = SuperiorSkyblockAPI.getPlayer(player.getUniqueId());

        Island island = event.getIsland();
        SetIslandDefaultUpgrades(island);
    }

    public static int defaultSize = 25;
    public static int defaultMaxHopper = 10;
    public static int defaultMaxMinecart = 5;
    public static int defaultMaxSpawner = 15;

    public static int defaultMaxObserver = 150;
    public static int defaultMaxPiston = 250;
    public static int defaultMaxStickyPiston = 250;

    public static void SetIslandDefaultUpgrades(Island island){
        island.setIslandSize(defaultSize);

        island.setBlockLimit(Key.of(Material.SPAWNER), defaultMaxSpawner);
        island.setBlockLimit(Key.of(Material.OBSERVER), defaultMaxObserver);
        island.setBlockLimit(Key.of(Material.PISTON), defaultMaxPiston);
        island.setBlockLimit(Key.of(Material.STICKY_PISTON), defaultMaxStickyPiston);

        int defaultEntityMax = 20;
        for (EntityType entityType : EntityType.values()){
            island.setEntityLimit(Key.of(entityType), defaultEntityMax);
        }

        island.setTeamLimit(3);
        island.setCoopLimit(10);

        consoleLog("&aGenerating default island upgrades for &2"+island.getOwner()+"'s &aisland");
    }

    public static void SetIslandUpgrades(Player player) {
        SuperiorPlayer superiorPlayer = SuperiorSkyblockAPI.getPlayer(player.getUniqueId());
        Island island = superiorPlayer.getIsland();

        if (island == null) {
            player.sendMessage("You don't have an island.");
            return;
        }

        SetIslandDefaultUpgrades(island);

        Map<String, Integer> upgrades = island.getUpgrades();

        for (Map.Entry<String, Integer> entry : upgrades.entrySet()) {
            String upgradeId = entry.getKey();
            int level = entry.getValue();

            if (upgradeId.equals("members-limit")){
                if (level == 2){
                    island.setTeamLimit(4);
                    consoleLog(sendText("&aTeam Limit of "+player.getName()+" &ahas been set to 4"));
                }
                else if (level == 3){
                    island.setTeamLimit(7);
                    consoleLog(sendText("&aTeam Limit of "+player.getName()+" &ahas been set to 7"));
                }
                else if (level == 4){
                    island.setTeamLimit(10);
                    consoleLog(sendText("&aTeam Limit of "+player.getName()+" &ahas been set to 10"));
                }
            }

            else if (upgradeId.equals("hoppers-limit")){
                if (level == 2){
                    island.setBlockLimit(Key.of(Material.HOPPER), 25);
                    consoleLog(sendText("&aHopper Limit of "+player.getName()+" &ahas been set to 25"));
                }
                else if (level == 3){
                    island.setBlockLimit(Key.of(Material.HOPPER), 45);
                    consoleLog(sendText("&aHopper Limit of "+player.getName()+" &ahas been set to 45"));
                }
                else if (level == 4){
                    island.setBlockLimit(Key.of(Material.HOPPER), 64);
                    consoleLog(sendText("&aHopper Limit of "+player.getName()+" &ahas been set to 64"));
                }
            }

            else if (upgradeId.equals("minecarts-limit")){
                if (level == 2){
                    island.setEntityLimit(Key.of(EntityType.MINECART), 10);
                    island.setEntityLimit(Key.of(EntityType.HOPPER_MINECART), 10);
                    island.setEntityLimit(Key.of(EntityType.CHEST_MINECART), 10);
                    island.setEntityLimit(Key.of(EntityType.TNT_MINECART), 10);
                    island.setEntityLimit(Key.of(EntityType.FURNACE_MINECART), 10);
                    consoleLog(sendText("&aMinecart Limit of "+player.getName()+" &ahas been set to 10"));
                }
                else if (level == 3){
                    island.setEntityLimit(Key.of(EntityType.MINECART), 15);
                    island.setEntityLimit(Key.of(EntityType.HOPPER_MINECART), 15);
                    island.setEntityLimit(Key.of(EntityType.CHEST_MINECART), 15);
                    island.setEntityLimit(Key.of(EntityType.TNT_MINECART), 15);
                    island.setEntityLimit(Key.of(EntityType.FURNACE_MINECART), 15);
                    consoleLog(sendText("&aMinecart Limit of "+player.getName()+" &ahas been set to 15"));
                }
                else if (level == 4){
                    island.setEntityLimit(Key.of(EntityType.MINECART), 20);
                    island.setEntityLimit(Key.of(EntityType.HOPPER_MINECART), 20);
                    island.setEntityLimit(Key.of(EntityType.CHEST_MINECART), 20);
                    island.setEntityLimit(Key.of(EntityType.TNT_MINECART), 20);
                    island.setEntityLimit(Key.of(EntityType.FURNACE_MINECART), 20);
                    consoleLog(sendText("&aMinecart Limit of "+player.getName()+" &ahas been set to 20"));
                }
            }

            else if (upgradeId.equals("border-size")){
                if (level == 2){
                    island.setIslandSize(75);
                    consoleLog(sendText("&aBorder Size of "+player.getName()+" &ahas been set to 75"));
                }
                else if (level == 3){
                    island.setIslandSize(100);
                    consoleLog(sendText("&aBorder Size of "+player.getName()+" &ahas been set to 100"));
                }
                else if (level == 4){
                    island.setIslandSize(200);
                    consoleLog(sendText("&aBorder Size of "+player.getName()+" &ahas been set to 200"));
                }else{
                    island.setIslandSize(25);
                }
            }

            player.sendMessage("Upgrade: " + upgradeId + ", Level: " + level);
        }
    }



}
