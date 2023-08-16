package com.locydragon.rli.util.old;

import com.locydragon.rli.RevivedLocyItem;
import com.locydragon.rli.util.Colors;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

public class VersionHelper implements Listener {
    public static int nowAVersion
            = splitVersion(RevivedLocyItem.instance.getDescription().getVersion());
    public static int latestVersion
            = splitVersion(RevivedLocyItem.instance.getDescription().getVersion());
    public static String latestVersionString = RevivedLocyItem.instance.getDescription().getVersion();
    public static boolean hasChecked = false;

    private static final String latestURL = "https://byserver.github.io/";

    @EventHandler
    public void onJoinMessage(PlayerJoinEvent e) {
        if (e.getPlayer().isOp()) {
            if (!hasChecked) {
                e.getPlayer().sendMessage(Colors.color("&7[&b&lRli云网络&7] &k[]&c 无法连接到云服务器检测最新版本，请保持网络畅通."));
            } else {
                if (nowAVersion < latestVersion) {
                    e.getPlayer().sendMessage(Colors.color("&7[&b&lRli云网络&7] &k[]&5 检测到有新版本：&b" + latestVersionString + "&5，你的版本：&b" + RevivedLocyItem.instance.getDescription().getVersion()));
                    TextComponent message = new TextComponent(Colors.color("&f[&6&l点我跳转更新网页&f]"));
                    message.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://www.mcbbs.net/forum.php?mod=viewthread&tid=908852"));
                    message.setHoverEvent(new HoverEvent( HoverEvent.Action.SHOW_TEXT,
                            new ComponentBuilder("点击跳转至MCBBS链接").create()));
                    e.getPlayer().spigot().sendMessage(message);
                    e.getPlayer().sendMessage(Colors.color("&7[&b&lRli云网络&7] &5Rli云服务提醒您请尽快更新，以防止错过漏洞或新功能！"));
                } else {
                    e.getPlayer().sendMessage(Colors.color("&7[&b&lRli云网络&7] &k[]&a 太好了，您正在使用最新版本： &b" + latestVersionString + "&a，请继续保持！"));
                }
            }
        }
    }

    public static int splitVersion(String versionData) {
        return Integer.valueOf(versionData
                .replace(".", "").trim());
    }

    public static void getLatestVersion() {
        Thread asyncNetCheck = new Thread(() -> {
            String latestVersion = openFile(VersionHelper.latestURL);
            if (latestVersion.trim().equalsIgnoreCase("")) {
                return;
            }
            hasChecked = true;
            int verionNum = splitVersion(latestVersion);
            VersionHelper.latestVersion = verionNum;
            latestVersionString = latestVersion;
            RevivedLocyItem.instance.getLogger().info("[Rli云网络] Rli在云上的最新版本：" + latestVersion);
            if (verionNum > nowAVersion) {
                RevivedLocyItem.instance.getLogger().info("[Rli云网络] 检测到您不是使用最新版本，建议您立即去贴内更新最新版！");
            } else {
                RevivedLocyItem.instance.getLogger().info("[Rli云网络] 检测到您使用最新版本，请继续保持哦！");
            }
        });
        asyncNetCheck.setDaemon(true);
        asyncNetCheck.start();
    }

    private static String openFile(String filePath) {
        int HttpResult;
        String ee = new String();
        try
        {
            URL url =new URL(filePath);
            URLConnection urlconn = url.openConnection();
            urlconn.connect();
            HttpURLConnection httpconn =(HttpURLConnection)urlconn;
            HttpResult = httpconn.getResponseCode();
            if(HttpResult != HttpURLConnection.HTTP_OK) {
                System.out.print("无法连接到");
            } else {
                int filesize = urlconn.getContentLength();
                InputStreamReader isReader = new InputStreamReader(urlconn.getInputStream(),"UTF-8");
                BufferedReader reader = new BufferedReader(isReader);
                StringBuffer buffer = new StringBuffer();
                String line;
                line = reader.readLine();
                while (line != null) {
                    buffer.append(line);
                    buffer.append(" ");
                    line = reader.readLine();
                }
                ee = buffer.toString();
            }
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return  ee;
    }
}
