package cn.smile.io.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SocketChannel;

public class MyNioClient {
    public static void main(String [] args){
//        NIOBlockerClient();
        NIONBlockerClient();
    }

    public static void NIOBlockerClient(){
        //创建SocketChannel对象
        SocketChannel socketChannel = null;
        try {
            socketChannel = SocketChannel.open();
            //建立链接
            socketChannel.connect(new InetSocketAddress("127.0.0.1",5555));

            System.out.println("socketChannel: " + socketChannel);
            System.out.println("链接到服务端");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void NIONBlockerClient(){
        //创建SocketChannel对象
        SocketChannel socketChannel = null;
        try {
            socketChannel = SocketChannel.open();

            socketChannel.configureBlocking(false);
            //建立链接
            socketChannel.connect(new InetSocketAddress("127.0.0.1",5555));

            System.out.println("socketChannel: " + socketChannel);
            System.out.println("链接到服务端");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
