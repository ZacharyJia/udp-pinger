package cit.bjtu.pinger;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

/**
 * Created by Administrator on 2015/10/22.
 */
public class Server {

    public static void main(String[] args) throws IOException {
        DatagramSocket server = new DatagramSocket(5050);

        byte[] data = new byte[32];

        DatagramPacket rcvPacket = new DatagramPacket(data, data.length);

        while (true) {
            server.receive(rcvPacket);
            server.send(rcvPacket);
            System.out.println("receive a ping request from: " + rcvPacket.getAddress());
        }

    }
}
