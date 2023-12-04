package me.uwu.dsc.log.network;

import com.google.gson.JsonSyntaxException;
import lombok.SneakyThrows;
import me.uwu.dsc.log.Main;
import me.uwu.dsc.log.event.EventManager;
import me.uwu.dsc.log.utils.DiscordUtils;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.UUID;

public class DiscordWSS {
    private final String token;
    private int heartbeatInterval;
    private Thread heartbeatThread;
    private WebSocketClient socket;

    public DiscordWSS(String token) {
        this.token = token;
    }

    public void connect() throws URISyntaxException {
        socket = new WebSocketClient(new URI("wss://gateway.discord.gg/?v=9&encoding=json")) {
            @Override
            public void onMessage(String message) {
                try {
                    EventManager.onMessageReceived(message);
                } catch (Exception ex) {
                    if (!(ex instanceof JsonSyntaxException))
                        ex.printStackTrace();
                }
                if (message.contains("heartbeat_interval")) {
                    heartbeatInterval = Integer.parseInt(message.split("\"heartbeat_interval\":")[1].split(",")[0]) - 2500;
                    heartbeatThread.start();
                }
            }

            @Override
            public void onOpen(ServerHandshake handshake) {
                //System.out.println("opened connection");
                send("{\"op\":2,\"d\":{\"token\":\"" + token + "\",\"properties\":{\"os\":\"Android\",\"browser\":\"Discord Android\",\"device\":\"Nyaboom\",\"system_locale\":\"fr-FR\",\"client_version\":\"206.16 - rn\",\"release_channel\":\"googleRelease\",\"device_vendor_id\":\"" + UUID.randomUUID() + "\",\"browser_user_agent\":\"\",\"browser_version\":\"\",\"os_version\":\"33\",\"client_build_number\":20601600138104,\"client_event_source\":null},\"capabilities\":16383}}");
                heartbeatThread = new Thread(() -> {
                    while (true) {
                        try {
                            //noinspection BusyWait
                            Thread.sleep(heartbeatInterval);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        send("{\"op\":1,\"d\":null}");
                    }
                });
                new Thread(() -> refreshGuilds()).start();
            }

            @Override
            public void onClose(int code, String reason, boolean remote) {
                //System.out.println("closed connection");
                try {
                    heartbeatThread.interrupt();
                } catch (Exception ignored) {
                }
                Main.newConnection(token);
            }

            @Override
            public void onError(Exception ex) {
                ex.printStackTrace();
                onClose(0, "", false);
            }
        };
        socket.connect();
    }

    private void snipeGuild(long id) {
        if (socket == null || !socket.isOpen()) {
            System.err.println("Socket is not connected");
            return;
        }

        socket.send("{\"op\":14,\"d\":{\"guild_id\":\"" + id + "\",\"typing\":true,\"activities\":false,\"threads\":false}}");
        System.out.println("Subscribed to guild " + id);
    }

    @SneakyThrows
    private void refreshGuilds() {
        if (socket == null || !socket.isOpen()) {
            System.err.println("Socket is not connected");
            return;
        }

        List<Long> guilds = DiscordUtils.getGuilds(token);
        for (long guild : guilds) {
            snipeGuild(guild);
            Thread.sleep(1000/3);
        }
    }
}
