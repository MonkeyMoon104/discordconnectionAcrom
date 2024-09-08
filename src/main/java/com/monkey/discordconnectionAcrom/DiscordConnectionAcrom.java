package com.monkey.discordconnectionAcrom;

import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DiscordConnectionAcrom extends JavaPlugin {

    private final String webhookUrl = "";
    private final String dbUrl = "";
    private final String dbUser = "";
    private final String dbPassword = "";

    @Override
    public void onEnable() {
        getLogger().info("DiscordConnection plugin abilitato!");
        getCommand("discord-connection").setExecutor(new DiscordConnectionCommand());

        new BukkitRunnable() {
            @Override
            public void run() {
                checkForNewConnections();
            }
        }.runTaskTimer(this, 0L, 20L);
    }

    private Connection connectToDatabase() {
        try {
            return DriverManager.getConnection(dbUrl, dbUser, dbPassword);
        } catch (SQLException e) {
            getLogger().severe("Errore nella connessione al database: " + e.getMessage());
            return null;
        }
    }

    private void checkForNewConnections() {
        try (Connection conn = connectToDatabase();
             PreparedStatement stmt = conn.prepareStatement("SELECT minecraft_name FROM mcds_connection WHERE prize = 0")) {
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    String minecraftName = rs.getString("minecraft_name");
                    Player player = getServer().getPlayer(minecraftName);
                    if (player != null) {
                        rewardPlayer(player);
                        startFireworkEffectHypePartyForAllPlayers();
                        try (PreparedStatement updateStmt = conn.prepareStatement("UPDATE mcds_connection SET prize = 1 WHERE minecraft_name = ?")) {
                            updateStmt.setString(1, minecraftName);
                            updateStmt.executeUpdate();
                        }
                    }
                }
            }
        } catch (SQLException e) {
            getLogger().severe("Errore durante il controllo dei nuovi collegamenti: " + e.getMessage());
        }
    }

    private void rewardPlayer(Player player) {
        String command = String.format("crates key give %s protocal 1", player.getName());
        getServer().dispatchCommand(getServer().getConsoleSender(), command);
        getServer().dispatchCommand(getServer().getConsoleSender(), "bc " + player.getName() + " Ha collegato l'account Minecraft con discord e ottenuto una ricompensa, collega anche tu il tuo account per ottenere una ricompensa!");
        player.sendMessage(ChatColor.GREEN + "Congratulazioni! Hai ricevuto una chiave nexus come premio per aver completato il collegamento!");
    }

    private void startFireworkEffectHypePartyForAllPlayers() {
        new BukkitRunnable() {
            @Override
            public void run() {
                for (Player player : Bukkit.getOnlinePlayers()) {
                    Location location = player.getLocation().add(0, 10, 0);
                    Firework firework = (Firework) location.getWorld().spawnEntity(location, EntityType.FIREWORK);
                    FireworkMeta fireworkMeta = (FireworkMeta) firework.getFireworkMeta();
                    fireworkMeta.addEffect(getCircularFireworkEffectHype());
                    firework.setFireworkMeta(fireworkMeta);
                    firework.setVelocity(location.getDirection().multiply(0.2));
                    firework.setShotAtAngle(true);
                    firework.detonate();
                }
            }
        }.runTaskLater(this, 20L);
    }

    private FireworkEffect getCircularFireworkEffectHype() {
        Random random = new Random();

        List<Color> colors = new ArrayList<>();
        for (int i = 0; i < 500; i++) {
            colors.add(Color.fromRGB(random.nextInt(256), random.nextInt(256), random.nextInt(256)));
        }

        FireworkEffect.Builder builder = FireworkEffect.builder()
                .flicker(true)
                .withColor(colors)
                .with(FireworkEffect.Type.BALL_LARGE)
                .withColor(Color.fromRGB(random.nextInt(256), random.nextInt(256), random.nextInt(256)))
                .withFade(Color.fromRGB(random.nextInt(256), random.nextInt(256), random.nextInt(256)))
                .withFade(Color.fromRGB(random.nextInt(256), random.nextInt(256), random.nextInt(256)))
                .withFade(Color.fromRGB(random.nextInt(256), random.nextInt(256), random.nextInt(256)))
                .withFade(Color.fromRGB(random.nextInt(256), random.nextInt(256), random.nextInt(256)))
                .withFade(Color.fromRGB(random.nextInt(256), random.nextInt(256), random.nextInt(256)))
                .withFade(Color.fromRGB(random.nextInt(256), random.nextInt(256), random.nextInt(256)))
                .withFade(Color.fromRGB(random.nextInt(256), random.nextInt(256), random.nextInt(256)))
                .withFade(Color.fromRGB(random.nextInt(256), random.nextInt(256), random.nextInt(256)))
                .withFade(Color.fromRGB(random.nextInt(256), random.nextInt(256), random.nextInt(256)))
                .withFade(Color.fromRGB(random.nextInt(256), random.nextInt(256), random.nextInt(256)))
                .withFade(Color.fromRGB(random.nextInt(256), random.nextInt(256), random.nextInt(256)))
                .withFade(Color.fromRGB(random.nextInt(256), random.nextInt(256), random.nextInt(256)))
                .withFade(Color.fromRGB(random.nextInt(256), random.nextInt(256), random.nextInt(256)))
                .withFade(Color.fromRGB(random.nextInt(256), random.nextInt(256), random.nextInt(256)))
                .withFade(Color.fromRGB(random.nextInt(256), random.nextInt(256), random.nextInt(256)))
                .withFade(Color.fromRGB(random.nextInt(256), random.nextInt(256), random.nextInt(256)))
                .withFade(Color.fromRGB(random.nextInt(256), random.nextInt(256), random.nextInt(256)))
                .withFade(Color.fromRGB(random.nextInt(256), random.nextInt(256), random.nextInt(256)))
                .withFade(Color.fromRGB(random.nextInt(256), random.nextInt(256), random.nextInt(256)))
                .withFade(Color.fromRGB(random.nextInt(256), random.nextInt(256), random.nextInt(256)))
                .withFade(Color.fromRGB(random.nextInt(256), random.nextInt(256), random.nextInt(256)))
                .withFade(Color.fromRGB(random.nextInt(256), random.nextInt(256), random.nextInt(256)))
                .withFade(Color.fromRGB(random.nextInt(256), random.nextInt(256), random.nextInt(256)))
                .withFade(Color.fromRGB(random.nextInt(256), random.nextInt(256), random.nextInt(256)))
                .withFade(Color.fromRGB(random.nextInt(256), random.nextInt(256), random.nextInt(256)))
                .withFade(Color.fromRGB(random.nextInt(256), random.nextInt(256), random.nextInt(256)))
                .withFade(Color.fromRGB(random.nextInt(256), random.nextInt(256), random.nextInt(256)))
                .withFade(Color.fromRGB(random.nextInt(256), random.nextInt(256), random.nextInt(256)))
                .withFade(Color.fromRGB(random.nextInt(256), random.nextInt(256), random.nextInt(256)))
                .withFade(Color.fromRGB(random.nextInt(256), random.nextInt(256), random.nextInt(256)))
                .withFade(Color.fromRGB(random.nextInt(256), random.nextInt(256), random.nextInt(256)))
                .withFade(Color.fromRGB(random.nextInt(256), random.nextInt(256), random.nextInt(256)))
                .withFade(Color.fromRGB(random.nextInt(256), random.nextInt(256), random.nextInt(256)))
                .withFade(Color.fromRGB(random.nextInt(256), random.nextInt(256), random.nextInt(256)))
                .withFade(Color.fromRGB(random.nextInt(256), random.nextInt(256), random.nextInt(256)))
                .withFade(Color.fromRGB(random.nextInt(256), random.nextInt(256), random.nextInt(256)))
                .withFade(Color.fromRGB(random.nextInt(256), random.nextInt(256), random.nextInt(256)))
                .withFade(Color.fromRGB(random.nextInt(256), random.nextInt(256), random.nextInt(256)))
                .withFade(Color.fromRGB(random.nextInt(256), random.nextInt(256), random.nextInt(256)))
                .withFade(Color.fromRGB(random.nextInt(256), random.nextInt(256), random.nextInt(256)))
                .withFade(Color.fromRGB(random.nextInt(256), random.nextInt(256), random.nextInt(256)))
                .withFade(Color.fromRGB(random.nextInt(256), random.nextInt(256), random.nextInt(256)))
                .withFade(Color.fromRGB(random.nextInt(256), random.nextInt(256), random.nextInt(256)))
                .withFade(Color.fromRGB(random.nextInt(256), random.nextInt(256), random.nextInt(256)))
                .withFade(Color.fromRGB(random.nextInt(256), random.nextInt(256), random.nextInt(256)))
                .withFade(Color.fromRGB(random.nextInt(256), random.nextInt(256), random.nextInt(256)))
                .withFade(Color.fromRGB(random.nextInt(256), random.nextInt(256), random.nextInt(256)))
                .withFade(Color.fromRGB(random.nextInt(256), random.nextInt(256), random.nextInt(256)))
                .withFade(Color.fromRGB(random.nextInt(256), random.nextInt(256), random.nextInt(256)))
                .withFade(Color.fromRGB(random.nextInt(256), random.nextInt(256), random.nextInt(256)))
                .withFade(Color.fromRGB(random.nextInt(256), random.nextInt(256), random.nextInt(256)))
                .withFade(Color.fromRGB(random.nextInt(256), random.nextInt(256), random.nextInt(256)))
                .withFade(Color.fromRGB(random.nextInt(256), random.nextInt(256), random.nextInt(256)))
                .withFade(Color.fromRGB(random.nextInt(256), random.nextInt(256), random.nextInt(256)))
                .withFade(Color.fromRGB(random.nextInt(256), random.nextInt(256), random.nextInt(256)))
                .withFade(Color.fromRGB(random.nextInt(256), random.nextInt(256), random.nextInt(256)))
                .withFade(Color.fromRGB(random.nextInt(256), random.nextInt(256), random.nextInt(256)))
                .withFade(Color.fromRGB(random.nextInt(256), random.nextInt(256), random.nextInt(256)))
                .withFade(Color.fromRGB(random.nextInt(256), random.nextInt(256), random.nextInt(256)))
                .withFade(Color.fromRGB(random.nextInt(256), random.nextInt(256), random.nextInt(256)))
                .withFade(Color.fromRGB(random.nextInt(256), random.nextInt(256), random.nextInt(256)))
                .withFade(Color.fromRGB(random.nextInt(256), random.nextInt(256), random.nextInt(256)))
                .withFade(Color.fromRGB(random.nextInt(256), random.nextInt(256), random.nextInt(256)))
                .withFade(Color.fromRGB(random.nextInt(256), random.nextInt(256), random.nextInt(256)))
                .withFade(Color.fromRGB(random.nextInt(256), random.nextInt(256), random.nextInt(256)))
                .withFade(Color.fromRGB(random.nextInt(256), random.nextInt(256), random.nextInt(256)))
                .withFade(Color.fromRGB(random.nextInt(256), random.nextInt(256), random.nextInt(256)))
                .withFade(Color.fromRGB(random.nextInt(256), random.nextInt(256), random.nextInt(256)))
                .withFade(Color.fromRGB(random.nextInt(256), random.nextInt(256), random.nextInt(256)))
                .withFade(Color.fromRGB(random.nextInt(256), random.nextInt(256), random.nextInt(256)))
                .withFade(Color.fromRGB(random.nextInt(256), random.nextInt(256), random.nextInt(256)))
                .withFade(Color.fromRGB(random.nextInt(256), random.nextInt(256), random.nextInt(256)))
                .withFade(Color.fromRGB(random.nextInt(256), random.nextInt(256), random.nextInt(256)))
                .withFade(Color.fromRGB(random.nextInt(256), random.nextInt(256), random.nextInt(256)))
                .withFade(Color.fromRGB(random.nextInt(256), random.nextInt(256), random.nextInt(256)))
                .withFade(Color.fromRGB(random.nextInt(256), random.nextInt(256), random.nextInt(256)))
                .withFade(Color.fromRGB(random.nextInt(256), random.nextInt(256), random.nextInt(256)))
                .withFade(Color.fromRGB(random.nextInt(256), random.nextInt(256), random.nextInt(256)))
                .withFade(Color.fromRGB(random.nextInt(256), random.nextInt(256), random.nextInt(256)))
                .withFade(Color.fromRGB(random.nextInt(256), random.nextInt(256), random.nextInt(256)))
                .withFade(Color.fromRGB(random.nextInt(256), random.nextInt(256), random.nextInt(256)))
                .withFade(Color.fromRGB(random.nextInt(256), random.nextInt(256), random.nextInt(256)))
                .withFade(Color.fromRGB(random.nextInt(256), random.nextInt(256), random.nextInt(256)))
                .withFade(Color.fromRGB(random.nextInt(256), random.nextInt(256), random.nextInt(256)))
                .withFade(Color.fromRGB(random.nextInt(256), random.nextInt(256), random.nextInt(256)))
                .withFade(Color.fromRGB(random.nextInt(256), random.nextInt(256), random.nextInt(256)))
                .withFade(Color.fromRGB(random.nextInt(256), random.nextInt(256), random.nextInt(256)))
                .withFade(Color.fromRGB(random.nextInt(256), random.nextInt(256), random.nextInt(256)))
                .withFade(Color.fromRGB(random.nextInt(256), random.nextInt(256), random.nextInt(256)))
                .withFade(Color.fromRGB(random.nextInt(256), random.nextInt(256), random.nextInt(256)))
                .withFade(Color.fromRGB(random.nextInt(256), random.nextInt(256), random.nextInt(256)))
                .withFade(Color.fromRGB(random.nextInt(256), random.nextInt(256), random.nextInt(256)))
                .withFade(Color.fromRGB(random.nextInt(256), random.nextInt(256), random.nextInt(256)))
                .withFade(Color.fromRGB(random.nextInt(256), random.nextInt(256), random.nextInt(256)))
                .withFade(Color.fromRGB(random.nextInt(256), random.nextInt(256), random.nextInt(256)))
                .withFade(Color.fromRGB(random.nextInt(256), random.nextInt(256), random.nextInt(256)))
                .withFade(Color.fromRGB(random.nextInt(256), random.nextInt(256), random.nextInt(256)))
                .withFade(Color.fromRGB(random.nextInt(256), random.nextInt(256), random.nextInt(256)))
                .withFade(Color.fromRGB(random.nextInt(256), random.nextInt(256), random.nextInt(256)))
                .withFade(Color.fromRGB(random.nextInt(256), random.nextInt(256), random.nextInt(256)))
                .withFade(Color.fromRGB(random.nextInt(256), random.nextInt(256), random.nextInt(256)))
                .withFade(Color.fromRGB(random.nextInt(256), random.nextInt(256), random.nextInt(256)))
                .trail(true);

        return builder.build();
    }

    public class DiscordConnectionCommand implements CommandExecutor {

        @Override
        public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
            if (!(sender instanceof Player)) {
                sender.sendMessage("Questo comando può essere eseguito solo da un giocatore");
                return true;
            }

            Player player = (Player) sender;
            String playerName = player.getName();

            try (Connection conn = connectToDatabase();
                 PreparedStatement stmt = conn.prepareStatement("SELECT discord_name FROM mcds_connection WHERE minecraft_name = ?")) {
                stmt.setString(1, playerName);
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        String discordName = rs.getString("discord_name");
                        player.sendMessage(ChatColor.RED + "Il tuo account Minecraft è già collegato all'account Discord (" + discordName + ")");
                        return true;
                    }
                }
            } catch (SQLException e) {
                getLogger().severe("Errore nella verifica dell'account: " + e.getMessage());
            }

            String code = generateCode();
            Instant expiration = Instant.now().plusSeconds(5 * 60);

            player.sendMessage(ChatColor.GREEN + "Ecco il tuo codice, (" + ChatColor.YELLOW + code + ChatColor.GREEN + "), utilizzalo su discord eseguendo il seguente comando: " + ChatColor.AQUA + "/discord-connection (" + code + ") (" + playerName + ")\n" + ChatColor.YELLOW + "Hai 5 minuti di tempo!");

            sendCodeToDiscordWebhook(playerName, code, expiration);

            return true;
        }

        private String generateCode() {
            Random random = new Random();
            int code = 100000 + random.nextInt(900000);
            return String.valueOf(code);
        }

        private void sendCodeToDiscordWebhook(String playerName, String code, Instant expiration) {
            String jsonPayload = String.format(
                    "{\n" +
                            "  \"embeds\": [{\n" +
                            "    \"title\": \"Discord Connection Code\",\n" +
                            "    \"color\": 5814783,\n" +
                            "    \"fields\": [\n" +
                            "      {\"name\": \"Player Name\", \"value\": \"%s\", \"inline\": true},\n" +
                            "      {\"name\": \"Code\", \"value\": \"%s\", \"inline\": true},\n" +
                            "      {\"name\": \"Expires At\", \"value\": \"%s\"}\n" +
                            "    ]\n" +
                            "  }]\n" +
                            "}", playerName, code, expiration.toString()
            );

            try {
                URL url = new URL(webhookUrl);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("POST");
                connection.setRequestProperty("Content-Type", "application/json");
                connection.setDoOutput(true);

                try (OutputStream os = connection.getOutputStream()) {
                    byte[] input = jsonPayload.getBytes("utf-8");
                    os.write(input, 0, input.length);
                }

                int responseCode = connection.getResponseCode();
                if (responseCode != HttpURLConnection.HTTP_NO_CONTENT) {
                    getLogger().warning("Errore nell'invio del webhook Discord: " + responseCode);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
