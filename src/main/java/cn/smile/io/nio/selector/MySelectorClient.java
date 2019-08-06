package cn.smile.io.nio.selector;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class MySelectorClient {
    public static void main(String args[]){
        // 创建SocketChannel对象
        SocketChannel sc = null;
        try {
            sc = SocketChannel.open();
            // 设置客户端通道非阻塞模式
            sc.configureBlocking(false);
            // 请求连接服务器
            sc.connect(new InetSocketAddress("127.0.0.1", 6666));

            // 确保客户端连接成功
            if (!sc.isConnected()) {
                sc.finishConnect();
            }
            // 提示用户
            System.out.println("连接成功！");

            // 定义字节缓冲区
            ByteBuffer buffer1 = ByteBuffer.wrap("HelloWorld".getBytes());
            // 将字节缓冲区数据写入通道
            sc.write(buffer1);
            // 提示用户
            System.out.println("客户端写入了数据。");

            // 定义字节缓冲区
            ByteBuffer buffer2 = ByteBuffer.allocate(100);
            // 读取通道数据存入字节缓冲区
            sc.read(buffer2);
            // 调用缓冲区反转方法
            buffer2.flip();
            // 输出字节缓冲区实际字节数
            System.out.println("缓冲区字节数：" + buffer2.limit());
            // 输出客户端读取的内容
            System.out.println("客户端读取的内容：" + new String(buffer2.array(), "utf-8"));

            // 让客户端持续工作
            while (true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
