package studio.resonos.grave.core.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CC {

    public static String translate(String in) {
        Pattern pattern = Pattern.compile("#[a-fA-F0-9]{6}");
        Matcher matcher = pattern.matcher(in);
        while (matcher.find()) {

            String hexCode = in.substring(matcher.start(), matcher.end());
            String replaceSharp = hexCode.replace('#', 'x');

            char[] ch = replaceSharp.toCharArray();
            StringBuilder builder = new StringBuilder("");
            for (char c : ch) {
                builder.append("&" + c);
            }

            in = in.replace(hexCode, builder.toString());
            matcher = pattern.matcher(in);
        }
        return org.bukkit.ChatColor.translateAlternateColorCodes('&', in);
    }
}
