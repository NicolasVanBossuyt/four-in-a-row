package master;

import models.*;
import network.*;

import java.io.IOException;
import java.util.HashMap;

public class MasterGame extends Game implements ConnectionListener, PendingGame {
    private Server server;
    private HashMap<Connection, ConnectedSlave> slaves;

    public MasterGame(Deck deck, int port) {
        super(deck);

        slaves = new HashMap<>();

        server = new Server();
        server.setListener(this);
        server.start(port);
    }

    @Override
    public void kickPlayer(int i) {
        for (ConnectedSlave slave : slaves.values()) {
            if (slave.getPlayer() != null && slave.getPlayer().getId() == i) {
                removePlayer(slave.getPlayer());
            }
        }
    }

    @Override
    public void attachListener(PendingGameListener listener) {
        setPlayerListener(listener);
    }

    public void shutdown() {
        System.out.println("Shutting down server...");
        server.stop();
    }

    @Override
    public void onConnect(Connection connection) {
        System.out.println("Connecting player " + connection.toString() + "...");
        this.slaves.put(connection, new ConnectedSlave());
    }

    @Override
    public void onDisconnect(Connection connection) {
        System.out.println("Disconnecting player " + connection.toString() + "...");

        ConnectedSlave slave = this.slaves.get(connection);
        this.removePlayer(slave.getPlayer());
        this.slaves.remove(connection);
    }

    @Override
    public void onReceive(Packet packet, Connection connection) {
        switch (packet.getPacketType()) {
            case LOGIN:
                System.out.println("Player login" + connection.toString() + "...");

                ConnectedSlave slave = this.slaves.get(connection);
                try {
                    Player newPlayer = this.joinPlayer(new PacketReader(packet).readString());

                    if (newPlayer != null) {
                        slave.setPlayer(newPlayer);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    connection.close();
                }

                break;
            default:
                System.out.println("Unexpected packet " + packet.toString());
        }
    }
}
