package fr.jesuistrolls.welcome.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TranslateColors {
    public static String apply(String message) {
        Pattern pattern = Pattern.compile("\\p{XDigit})\\p{XDigit})\\p{XDigit})\\p{XDigit})\\p{XDigit})\\p{XDigit})");
        Matcher matcher = pattern.matcher(message);
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            String hex = matcher.group(1) + matcher.group(1) + matcher.group(2) + matcher.group(3) + matcher.group(4) + matcher.group(5);
            String replacement = "<#" + hex.toUpperCase() + ">";
            matcher.appendReplacement(sb, replacement);
        }
        matcher.appendTail(sb);
        message = sb.toString();
        message = message.replaceAll("§0", "<black>");
        message = message.replaceAll("§1", "<dark_blue>");
        message = message.replaceAll("§2", "<dark_green>");
        message = message.replaceAll("§3", "<dark_aqua>");
        message = message.replaceAll("§4", "<dark_red>");
        message = message.replaceAll("§5", "<dark_purple>");
        message = message.replaceAll("§6", "<gold>");
        message = message.replaceAll("§7", "<gray>");
        message = message.replaceAll("§8", "<dark_gray>");
        message = message.replaceAll("§9", "<blue>");
        message = message.replaceAll("§a", "<green>");
        message = message.replaceAll("§b", "<aqua>");
        message = message.replaceAll("§c", "<red>");
        message = message.replaceAll("§d", "<light_purple>");
        message = message.replaceAll("§e", "<yellow>");
        message = message.replaceAll("§f", "<white>");
        message = message.replaceAll("§k", "<obfuscated>");
        message = message.replaceAll("§l", "<b>");
        message = message.replaceAll("§m", "<st>");
        message = message.replaceAll("§n", "<u>");
        message = message.replaceAll("§o", "<i>");
        message = message.replaceAll("§r", "<reset>");
        message = message.replaceAll("§A", "<green>");
        message = message.replaceAll("§B", "<aqua>");
        message = message.replaceAll("§C", "<red>");
        message = message.replaceAll("§D", "<light_purple>");
        message = message.replaceAll("§E", "<yellow>");
        message = message.replaceAll("§F", "<white>");
        message = message.replaceAll("§K", "<obfuscated>");
        message = message.replaceAll("§L", "<b>");
        message = message.replaceAll("§M", "<st>");
        message = message.replaceAll("§N", "<u>");
        message = message.replaceAll("§O", "<i>");
        message = message.replaceAll("§R", "<reset>");
        return message;
    }
}
