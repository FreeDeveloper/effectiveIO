package cn.smile.io.nio.selector;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * 使用selector来进行多路复用
 * */
public class MySelectorServer {
    public static void main(String [] args){
        ServerSocketChannel serverSocketChannel = null;
        try {
            // 创建ServerSocketChannel对象
            serverSocketChannel = ServerSocketChannel.open();
            // 设置服务器通道非阻塞模式
            serverSocketChannel.configureBlocking(false);
            // 绑定监听端口
            serverSocketChannel.bind(new InetSocketAddress(6666));

            System.out.println("服务端已启动，等待客户端连接。。。");

            // 创建多路复用选择器
            Selector selector = Selector.open();
            // 在ssc上注册accept事件，并指定由selector来负责处理事件
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

            //让服务端线程持续工作
            while(true){

                //select方法产生阻塞，直到有连接事件发生
                selector.select();
                //获取监听事件集合（连接事件）
                Set<SelectionKey> keys = selector.selectedKeys();
                //迭代器
                Iterator<SelectionKey> iterator = keys.iterator();
                while(iterator.hasNext()){
                    //迭代selectionKey
                    SelectionKey selectionKey = iterator.next();
                    //判断当前事件是否是连接事件
                    if(selectionKey.isAcceptable()){
                        // 从sk中获取它封装的ServerSocketChannel对象
                        ServerSocketChannel serverSocketChannel1 = (ServerSocketChannel)selectionKey.channel();
                        //设置成非阻塞模式
                        serverSocketChannel1.configureBlocking(false);
                        // 获取SocketChannel对象
                        SocketChannel socketChannel = serverSocketChannel1.accept();
                        // 设置客户端通道非阻塞模式
                        socketChannel.configureBlocking(false);

                        System.out.println("有客户端接入，负责处理该请求的线程id：" + Thread.currentThread().getId());

                        /**
                         * OP_READ 1<<0    0000 0001
                         * OP_ACCEPT 1<<4  0001 0000
                         * OP_WRITE 1<<2   0000 0100
                         * OP_CONNECT 1<<3 0000 1000
                         */
                        // 将该通道上注册的accept事件去掉，注册read和write事件
                        socketChannel.register(selector, SelectionKey.OP_READ | SelectionKey.OP_WRITE);


                    }
                    //判断当前事件是否为read事件
                    if(selectionKey.isReadable()){
                        // 获取客户端通道
                        SocketChannel socketChannel = (SocketChannel)selectionKey.channel();
                        //设置非阻塞通道
                        socketChannel.configureBlocking(false);
                        //创建字节缓冲
                        ByteBuffer byteBuffer = ByteBuffer.allocate(20);
                        // 读取通道数据存在字节缓冲区
                        socketChannel.read(byteBuffer);
                        // 输出服务器端读取的数据
                        System.out.println("服务器端读到了数据：" + new String(byteBuffer.array()));
                        // 去掉read事件
                        // sc.register(selector, SelectionKey.OP_WRITE);
                        socketChannel.register(selector, selectionKey.interestOps() & ~SelectionKey.OP_READ);
                    }
                    //判断当前事件是否为write时间
                    if(selectionKey.isWritable()){
                        // 获取客户端通道
                        SocketChannel socketChannel = (SocketChannel) selectionKey.channel();
                        // 设置客户端通道非阻塞模式
                        socketChannel.configureBlocking(false);
                        // 定义字节缓冲区存放了数据
                        ByteBuffer buffer = ByteBuffer.wrap("天长地久".getBytes("utf-8"));
                        // 将字节缓冲区数据写入通道
                        socketChannel.write(buffer);
                        // 提示用户
                        System.out.println("服务器端写入了数据。");
                        // 去掉write事件
                        socketChannel.register(selector, selectionKey.interestOps() & ~SelectionKey.OP_WRITE);
                    }

                    // 勿忘我：将该事件迭代器对象移除
                    iterator.remove();
                }
            }

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
