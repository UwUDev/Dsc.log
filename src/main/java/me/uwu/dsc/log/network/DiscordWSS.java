package me.uwu.dsc.log.network;

import com.google.gson.JsonSyntaxException;
import me.uwu.dsc.log.Main;
import me.uwu.dsc.log.event.EventManager;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.net.URISyntaxException;

public class DiscordWSS {
    private final String token;
    private int heartbeatInterval;
    private Thread heartbeatThread;

    public DiscordWSS(String token) {
        this.token = token;
    }

    public void connect() throws URISyntaxException {
        WebSocketClient socket = new WebSocketClient(new URI("wss://gateway.discord.gg/?v=9&encoding=json"))
        {
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
                send("{\"op\":2,\"d\":{\"token\":\"" + token + "\",\"capabilities\":4608,\"properties\":{\"os\":\"Windows\",\"browser\":\"Chrome\",\"device\":\"\",\"system_locale\":\"fr-FR\",\"browser_user_agent\":\"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/96.0.4647.0 Safari/537.36\",\"browser_version\":\"96.0.4647.0\",\"os_version\":\"10\",\"referrer\":\"\",\"referring_domain\":\"\",\"referrer_current\":\"\",\"referring_domain_current\":\"\",\"release_channel\":\"stable\",\"client_build_number\":97662,\"client_event_source\":null},\"presence\":{\"status\":\"online\",\"since\":0,\"activities\":[],\"afk\":false},\"compress\":false,\"client_state\":{\"guild_hashes\":{},\"highest_last_message_id\":\"0\",\"read_state_version\":0,\"user_guild_settings_version\":-1}}}");
                heartbeatThread = new Thread(() ->{
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
            }

            @Override
            public void onClose(int code, String reason, boolean remote) {
                //System.out.println("closed connection");
                try { heartbeatThread.interrupt();
                } catch (Exception ignored){}
                Main.newConnection(token);
            }

            @Override
            public void onError(Exception ex) {
                ex.printStackTrace();
            }
        };
        socket.connect();
    }
}
