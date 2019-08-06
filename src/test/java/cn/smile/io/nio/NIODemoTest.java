package cn.smile.io.nio;

import org.junit.Test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

public class NIODemoTest {

    /**
     * 非阻塞编程的最大问题：不知道是否真正的有客户端接入，所以容易产生空指针，
     * 为了解决这个问题，需要人为设置阻塞。
     * */
    @Test
    public void testAcceptOnce(){
        try {
            ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();

            serverSocketChannel.configureBlocking(false);

            serverSocketChannel.bind(new InetSocketAddress(5555));

            SocketChannel socketChannel = serverSocketChannel.accept();

            System.out.println("socketChannel: " + socketChannel);
            System.out.println("有客户端接入了。");

            ByteBuffer byteBuffer = ByteBuffer.allocate(10);

            //读取通道数据存入字节缓冲区
            socketChannel.read(byteBuffer);
            System.out.println("有数据写入啦~");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * 结果分析：连接能够正常建立，但是“有数据读入了。”并没有输出，
     * 说明即使ssc服务通道设置了非阻塞，也没有改变得到的通道sc默认为阻塞模式，所以sc.read(buf)阻塞了。
     * 若不想让read()方法阻塞，需要在调用read()之前加sc.configureBlocking(false)；
     * 这样即使没有读到数据，“有数据读入了。”也能打印出来。
     *
     * */
    @Test
    public void testAcceptWhile(){
        try {
            ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();

            serverSocketChannel.configureBlocking(false);

            serverSocketChannel.bind(new InetSocketAddress(5555));

            SocketChannel socketChannel = null;
            while (socketChannel == null){
                socketChannel = serverSocketChannel.accept();
            }

            System.out.println("socketChannel: " + socketChannel);
            System.out.println("有客户端接入了。");

            ByteBuffer byteBuffer = ByteBuffer.allocate(10);

            //读取通道数据存入字节缓冲区
            socketChannel.read(byteBuffer);
            System.out.println("有数据写入啦~");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testAcceptWhileReadNBlocker(){
        try {
            ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();

            serverSocketChannel.configureBlocking(false);

            serverSocketChannel.bind(new InetSocketAddress(5555));

            SocketChannel socketChannel = null;
            while (socketChannel == null){
                socketChannel = serverSocketChannel.accept();
            }

            System.out.println("socketChannel: " + socketChannel);
            System.out.println("有客户端接入了。");

            socketChannel.configureBlocking(false);

            ByteBuffer byteBuffer = ByteBuffer.allocate(20);

            //读取通道数据存入字节缓冲区
            socketChannel.read(byteBuffer);
            System.out.println("客户端写入的数据："+new String(byteBuffer.array()));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 测试通道的write方法
     * 输出“连接成功”，但是抛出NotYetConnectedException异常，
     * 非阻塞模式很坑的地方在于不知道连接是否真正的建立。
     * */
    @Test
    public void testConnect(){
        try {

            // 创建SocketChannel对象
            SocketChannel socketChannel = SocketChannel.open();
            // 设置客户端通道非阻塞模式
            socketChannel.configureBlocking(false);
            // 建立连接
            socketChannel.connect(new InetSocketAddress("127.0.0.1",5555));

            System.out.println("socketChannel: " + socketChannel);
            System.out.println("连接成功！");
            // 定义字节缓冲区
            ByteBuffer byteBuffer = ByteBuffer.wrap("Hello World".getBytes());
            // 将字节缓冲区数据写入通道
            socketChannel.write(byteBuffer);

            System.out.println("客户端写入数据");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testConnectWhile() {
        try {

            // 创建SocketChannel对象
            SocketChannel socketChannel = SocketChannel.open();
            // 设置客户端通道非阻塞模式
            socketChannel.configureBlocking(false);
            // 建立连接
            socketChannel.connect(new InetSocketAddress("127.0.0.1", 5555));

            //判断客户端是否链接成功
            while (!socketChannel.isConnected()) {
                socketChannel.finishConnect();
            }

            System.out.println("socketChannel: " + socketChannel);
            System.out.println("连接成功！");
            // 定义字节缓冲区
            ByteBuffer byteBuffer = ByteBuffer.wrap("Hello World".getBytes());
            // 将字节缓冲区数据写入通道
            socketChannel.write(byteBuffer);

            System.out.println("客户端写入数据");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
