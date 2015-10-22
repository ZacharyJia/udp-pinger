package cit.bjtu.pinger;

import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;

import java.io.IOException;
import java.net.*;

/**
 * Created by Administrator on 2015/10/22.
 */
public class Client {
    public static void main(String[] args) throws IOException {
        if (args.length != 2) {
            System.out.println("usage: ping [target]");
            return;
        }

        int sent = 0;
        int received = 0;

        InetAddress addr = null;
        try {
            addr = InetAddress.getByName(args[1]);
        } catch (UnknownHostException e) {
            System.out.println("无法解析的地址");
            return;
        }
        int port = 5050;

        System.out.println("正在 Ping 192.168.100.200 具有 32 字节的数据:");
        for (int i = 0; i < 4; i++) {
            DatagramSocket client = new DatagramSocket();
            client.setSoTimeout(10000);
            byte[] data = new byte[32];
            DatagramPacket sendPacket = new DatagramPacket(data, data.length, addr, port);
            long start = System.currentTimeMillis();
            client.send(sendPacket);
            sent++;
            DatagramPacket rcvPacket = new DatagramPacket(data, data.length);
            try {
                client.receive(rcvPacket);
            } catch (SocketTimeoutException e) {
                System.out.println("请求超时");
                continue;
            }
            received++;
            long end = System.currentTimeMillis();
            System.out.println("来自" + rcvPacket.getAddress() + "的回复 时间:" + ((end - start) == 0? "<1":(end-start)) + "ms");
            client.close();
        }

        System.out.println(args[1] + " 的 Ping 统计信息:");
        System.out.println("数据包：已发送=" + sent + " ,已接收=" + received + " ，丢失=" + (sent - received) + "(" + (((double)sent - received) / (double)sent) * 100 + "%丢失)");

    }
}
