package org.islandmanager.islandManager;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.*;
import org.bukkit.Color;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.awt.*;
import java.nio.ByteBuffer;
import java.text.DecimalFormat;
import java.util.Base64;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.bukkit.Bukkit.getServer;


public class UserInterface {

    public static String checkSymbol = "✔";
    public static String xSymbol = "✘";

    public static String usageArrowSymbol = "➤";

    // color

    public static String color_darkGreenAcid = "#538E1F";
    public static String color_brightGreenAcid = "#6EDA10";

    public static String color_darkRed = "#A4162D";

    //

    public static String sendText(String text) {
        return text.replaceAll("&", "§");
    }

    public static void executeConsoleCommand(String command) {
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command);
    }

    public static void PlaySound(Sound s, Entity e, float volume, float pitch) {
        e.getWorld().playSound(e.getLocation(), s, volume, pitch);
    }

    public static void PlaySoundAt(Sound sound, Location location, float volume, float pitch) {
        if (location.getWorld() != null) {
            location.getWorld().playSound(location, sound, volume, pitch);
        }
    }

    public static String uncolouredText(String text) {
        return text.replaceAll("§.|[^\\x00-\\x7F]|\\d+|[^a-zA-Z_ ]", "").trim();
    }

    public static String numberInText(String text) {
        String cleaned = text.replaceAll("§.|[^\\x00-\\x7F]", "").trim();
        // Replace text
        String pattern = "\\d+";
        Pattern compiledPattern = Pattern.compile(pattern);
        Matcher matcher = compiledPattern.matcher(cleaned);
        StringBuilder result = new StringBuilder();
        while (matcher.find()) {
            // Append matched substring to result
            result.append(matcher.group());
        }
        return result.toString().trim();
    }

    public static String intToRoman(int number) {
        if (number <= 0 || number > 3999) {
            throw new IllegalArgumentException("Number must be between 1 and 3999");
        }

        String[] romans = {
                "M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I"
        };
        int[] values = {
                1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1
        };

        StringBuilder result = new StringBuilder();
        for (int i = 0; i < values.length; i++) {
            while (number >= values[i]) {
                number -= values[i];
                result.append(romans[i]);
            }
        }
        return result.toString();
    }

    public static void consoleLog(String message) {
        ConsoleCommandSender console = getServer().getConsoleSender();
        console.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
    }

    private static final String defaultFormat = "#,###.##";

    public static String FormatDouble(double d){
        DecimalFormat formatter = new DecimalFormat(defaultFormat);
        return formatter.format(d);

    }


    public static Inventory OpenChest(Player p, int size, String name) {
        Inventory gui = Bukkit.createInventory(p, size*9, sendText("&n"+name));

        return gui;
    }






    public static String formatItemName(String itemName) {
        String[] words = itemName.split("_"); // Split by underscore
        StringBuilder formattedName = new StringBuilder();

        for (String word : words) {
            formattedName.append(word.substring(0, 1).toUpperCase()) // Capitalize first letter
                    .append(word.substring(1).toLowerCase()) // Lowercase rest
                    .append(" "); // Add space
        }

        return formattedName.toString().trim(); // Trim trailing space
    }




    public static String sendRgbText(String text, String hex) {
        return net.md_5.bungee.api.ChatColor.of(hex)+sendText(text);
    }

    public static void Broadcast(String text){
        for (Player player : Bukkit.getOnlinePlayers()){
            player.sendMessage(sendText(text));
        }
    }

    public static String getFormattedTime(long seconds) {
        if (seconds <= 0) {
            return "0s";
        }

        long days = seconds / 86400;
        long hours = (seconds % 86400) / 3600;
        long minutes = (seconds % 3600) / 60;
        long secs = seconds % 60;

        StringBuilder formattedTime = new StringBuilder();

        if (days > 0) {
            formattedTime.append(days).append(" ").append(days == 1 ? "day " : "days ");
        }
        if (hours > 0) {
            formattedTime.append(hours).append(" ").append(hours == 1 ? "hour " : "hours ");
        }
        if (minutes > 0) {
            formattedTime.append(minutes).append(" ").append(minutes == 1 ? "minute " : "minutes ");
        }
        if (secs > 0 || formattedTime.length() == 0) {
            formattedTime.append(secs).append(" ").append(secs == 1 ? "second" : "seconds");
        }

        return formattedTime.toString().trim();
    }

    public static void AnnouncePayment(Player player, String text, String price){

        Broadcast("&8[&6!&8] &e"+player.getName()+" &fhas bought &6x1 "+text+" &ffrom &6&lCredit Shop &ffor &e"+price+" credits!");

    }

    public static void PlayParticleAtBlock(Block block, Particle particle) {
        Location loc = block.getLocation().add(0, 0.5, 0);
        block.getWorld().spawnParticle(particle, loc, 10);
    }

    public static void SpawnBlockCrackParticle(Block block) {
        Location loc = block.getLocation().add(0.5, 1.0, 0.5); // top surface

        BlockData data = block.getBlockData(); // block appearance
        block.getWorld().spawnParticle(
                Particle.BLOCK,
                loc,
                20,       // count
                0.3, 0, 0.3, // offset x/y/z
                data      // material to simulate breaking
        );
    }


    public static void SpawnBlockRedstoneParticle(Block block, Color color) {
        Location loc = block.getLocation().add(0.5, 1.0, 0.5); // top surface

        Particle.DustOptions dust = new Particle.DustOptions(color, 1.0F); // Color and size
        block.getWorld().spawnParticle(
                Particle.DUST,
                loc,
                20,       // count
                0.3, 0, 0.3, // offset x/y/z
                0,
                dust
        );
    }

    public static void SendTitle(Player player, String title, String subtitle, int fadeIn, int stay, int fadeOut){
        player.sendTitle(sendText(title), sendText(subtitle), fadeIn*20, stay*20, fadeOut*20);
    }


}
