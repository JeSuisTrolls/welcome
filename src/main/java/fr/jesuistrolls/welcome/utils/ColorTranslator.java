package fr.jesuistrolls.welcome.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TranslateColors {
    public static String apply(String color) {
        // Replaces hexadecimal color codes with Discord color code syntax
        Pattern hexPattern = Pattern.compile("§x(\\p{XDigit}{6})");
        Matcher hexMatcher = hexPattern.matcher(color);
        StringBuffer sbHex = new StringBuffer();
        while (hexMatcher.find()) {
            String hex = hexMatcher.group(1);
            String replacement = "<#" + hex.toUpperCase() + ">";
            hexMatcher.appendReplacement(sbHex, replacement);
        }
        hexMatcher.appendTail(sbHex);
        color = sbHex.toString();

        // Replaces Minecraft color codes with Discord color code syntax
        color = color.replaceAll("§0", "<black>");
        color = color.replaceAll("§1", "<dark_blue>");
        color = color.replaceAll("§2", "<dark_green>");
        color = color.replaceAll("§3", "<dark_aqua>");
        color = color.replaceAll("§4", "<dark_red>");
        color = color.replaceAll("§5", "<dark_purple>");
        color = color.replaceAll("§6", "<gold>");
        color = color.replaceAll("§7", "<gray>");
        color = color.replaceAll("§8", "<dark_gray>");
        color = color.replaceAll("§9", "<blue>");
        color = color.replaceAll("§a", "<green>");
        color = color.replaceAll("§b", "<aqua>");
        color = color.replaceAll("§c", "<red>");
        color = color.replaceAll("§d", "<light_purple>");
        color = color.replaceAll("§e", "<yellow>");
        color = color.replaceAll("§f", "<white>");
        color = color.replaceAll("§k", "<obfuscated>");
        color = color.replaceAll("§l", "<b>");
        color = color.replaceAll("§m", "<st>");
        color = color.replaceAll("§n", "<u>");
        color = color.replaceAll("§o", "<i>");
        color = color.replaceAll("§r", "<reset>");

        color = color.replaceAll("§A", "<green>");
        color = color.replaceAll("§B", "<aqua>");
        color = color.replaceAll("§C", "<red>");
        color = color.replaceAll("§D", "<light_purple>");
        color = color.replaceAll("§E", "<yellow>");
        color = color.replaceAll("§F", "<white>");
        color = color.replaceAll("§K", "<obfuscated>");
        color = color.replaceAll("§L", "<b>");
        color = color.replaceAll("§M", "<st>");
        color = color.replaceAll("§N", "<u>");
        color = color.replaceAll("§O", "<i>");
        color = color.replaceAll("§R", "<reset>");

        return color;
    }
}
