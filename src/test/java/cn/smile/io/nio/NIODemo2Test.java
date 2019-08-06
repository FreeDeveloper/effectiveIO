package cn.smile.io.nio;

import org.junit.Test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

public class NIODemo2Test {


    @Test
    public void testAccept(){
        try {
            ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.configureBlocking(false);
            serverSocketChannel.bind(new InetSocketAddress(5555));

            SocketChannel socketChannel = null;
            while(socketChannel == null){
                socketChannel = serverSocketChannel.accept();
            }

            System.out.println("socketChannel: " + socketChannel);
            System.out.println("有客户端接入了");

            ByteBuffer byteBuffer = ByteBuffer.allocate(20);

            socketChannel.read(byteBuffer);

            System.out.println("读取到的数据："+new String(byteBuffer.array()));

            ByteBuffer byteBuffer1 = ByteBuffer.wrap("永不言败".getBytes("utf-8"));
            socketChannel.write(byteBuffer1);

            System.out.println("服务端回写数据");

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * 问题：服务器端读取了客户端写入的数据“HelloWorld”，
     * 但是客户端却没有读取到服务器端写入的数据“永不言败”，为什么？
     * */
    @Test
    public void testConnect(){
        try {
            SocketChannel socketChannel = SocketChannel.open();
            socketChannel.configureBlocking(false);
            socketChannel.connect(new InetSocketAddress("127.0.0.1",5555));

            while(!socketChannel.isConnected()){
                socketChannel.finishConnect();
            }

            // 提示用户
            System.out.println("socketChannel: " + socketChannel);
            System.out.println("连接服务端成功！");

            // 定义字节缓冲区
            ByteBuffer buffer = ByteBuffer.wrap("HelloWorld".getBytes());
            // 将字节缓冲数据写入通道
            socketChannel.write(buffer);
            // 提示用户
            System.out.println("客户端写入数据");

            // 定义字节缓冲区
            ByteBuffer buffer1 = ByteBuffer.allocate(12);
            // 读取通道数据存入字节缓冲区
            socketChannel.read(buffer1);
            // 输出从通道里读取的数据
            System.out.println("读取的数据：" + new String(buffer1.array(), "utf-8"));


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 让客户端sleep一会儿
     * */
    @Test
    public void testConnectSleep(){
        try {
            SocketChannel socketChannel = SocketChannel.open();
            socketChannel.configureBlocking(false);
            socketChannel.connect(new InetSocketAddress("127.0.0.1",5555));

            while(!socketChannel.isConnected()){
                socketChannel.finishConnect();
            }

            // 提示用户
            System.out.println("socketChannel: " + socketChannel);
            System.out.println("连接服务端成功！");

            // 定义字节缓冲区
            ByteBuffer buffer = ByteBuffer.wrap("HelloWorld".getBytes());
            // 将字节缓冲数据写入通道
            socketChannel.write(buffer);
            // 提示用户
            System.out.println("客户端写入数据");

            Thread.sleep(100);

            // 定义字节缓冲区
            ByteBuffer buffer1 = ByteBuffer.allocate(12);
            // 读取通道数据存入字节缓冲区
            socketChannel.read(buffer1);
            // 输出从通道里读取的数据
            System.out.println("读取的数据：" + new String(buffer1.array(), "utf-8"));


        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
