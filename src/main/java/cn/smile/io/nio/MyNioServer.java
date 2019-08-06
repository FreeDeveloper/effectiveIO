package cn.smile.io.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

public class MyNioServer {

    public static void main(String [] args){
        NIOBlockerModel();
//        NIONBlockerModel();
    }


    //阻塞模式实现
    public static void NIOBlockerModel(){
        ServerSocketChannel serverSocketChannel = null;
        try {
            //创建serverSocketChannel对象
            serverSocketChannel = ServerSocketChannel.open();

            //绑定端口
            serverSocketChannel.bind(new InetSocketAddress(5555));

            //获取socketChannel对象
            SocketChannel socketChannel = serverSocketChannel.accept();
            System.out.println("socketChannel: " + socketChannel);
            System.out.println("有客户端链接进来了");

        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                serverSocketChannel.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    //非阻塞模式
    public static void NIONBlockerModel(){

        ServerSocketChannel serverSocketChannel = null;
        try {

            serverSocketChannel = ServerSocketChannel.open();

            //服务端非阻塞模式开启
            serverSocketChannel.configureBlocking(false);

            //设置绑定的端口
            serverSocketChannel.bind(new InetSocketAddress(6666));

            SocketChannel socketChannel = serverSocketChannel.accept();

            System.out.println("socketChannel: " + socketChannel);
            System.out.println("有客户端接入了。");

        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                serverSocketChannel.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
