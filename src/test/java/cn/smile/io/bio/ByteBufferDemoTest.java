package cn.smile.io.bio;

import org.junit.Test;

import java.io.UnsupportedEncodingException;
import java.nio.Buffer;
import java.nio.ByteBuffer;

/**
 * 1）capacity：缓冲区容量，单位是字节
 * 2）limit：缓冲区限制（限定获取元素的最大下标）
 * 3）position：缓冲区位置（position<limit）
 *
 * 字节缓冲区的重要方法
 * 1）allocate(int capacity)：分配缓冲区容量
 * 2）limit()：获取缓冲区限制
 * 3）limit(int newLimit)：设置缓冲区限制
 * 4）position()：获取缓冲区位置
 * 5）position(int newPosition)：设置缓冲区位置
 * 6）get()：从缓冲区position指定的位置获取一个元素，然后position增加1。
 * 7）put(byte b)：往缓冲区写入数据（字节数据），导致position变化
 * 8）putXXX(XXX x)：往缓冲区写入数据（XXX类型数据），导致position变化
 * */
public class ByteBufferDemoTest {

    @Test
    public void allocateTest(){

        // 创建字节缓冲区对象，分配缓冲区容量(capacity=10)
        ByteBuffer byteBuffer = ByteBuffer.allocate(10);

        //显示字节缓冲的容量
        System.out.println(byteBuffer.capacity());

        //显示字节缓冲区的限制
        System.out.println(byteBuffer.limit());

        //显示字节缓冲区的位置
        System.out.println(byteBuffer.position());

    }

    @Test
    public void testPut(){
        ByteBuffer byteBuffer = ByteBuffer.allocate(10);

        byte b1 = 1;
        byte b2 = 5;

        byteBuffer.put(b1);
        byteBuffer.put(b2);

        //显示字节缓冲的容量
        System.out.println(byteBuffer.capacity());

        //显示字节缓冲区的限制
        System.out.println(byteBuffer.limit());

        //显示字节缓冲区的位置
        System.out.println(byteBuffer.position());
    }

    @Test
    /**
     * 分析结果：第一个位置是字节数据1（位置：0），
     * 第二个位置开始放整型数据10，Java里整型数据占4个字节（位置：1-2-3-4），
     * 于是position就从1变到5了，这样把第三个位置上的字节数据5给吃掉了。
     * 第五个位置没有数据，于是返回0。
     * */
    public void testGet(){
        ByteBuffer byteBuffer = ByteBuffer.allocate(10);

        byte b1 = 1;
        byte b2 = 5;

        byteBuffer.put(b1);
        byteBuffer.put(b2);

        byteBuffer.putInt(1,10);

        //缓冲区位置归0
        byteBuffer.position(0);
        System.out.println(byteBuffer.position()+":"+byteBuffer.get());
        System.out.println(byteBuffer.position()+":"+byteBuffer.getInt());
        System.out.println(byteBuffer.position()+":"+byteBuffer.get());
    }

    @Test
    public void testGet2(){
        ByteBuffer byteBuffer = ByteBuffer.allocate(10);

        byte b1 = 1;
        byte b2 = 10;

        byteBuffer.put(b1);
        byteBuffer.put(b2);

        byteBuffer.putInt(10);

        //缓冲区位置归0
        byteBuffer.position(0);
        System.out.println(byteBuffer.position()+":"+byteBuffer.get());
        System.out.println(byteBuffer.position()+":"+byteBuffer.get());
        System.out.println(byteBuffer.position()+":"+byteBuffer.getInt());
    }

    @Test
    /**
     * 我们可以通过limit来限定可以获取元素的个数。
     * */
    public void testGetLimit(){
        ByteBuffer byteBuffer = ByteBuffer.allocate(10);

        byte b1 = 1;
        byte b2 = 10;

        byteBuffer.put(b1);
        byteBuffer.put(b2);

        byteBuffer.putInt(10);

        //缓冲区位置归0
        byteBuffer.position(0);

        //缓冲区限制2
        byteBuffer.limit(2);

        System.out.println(byteBuffer.position()+":"+byteBuffer.get());
        System.out.println(byteBuffer.position()+":"+byteBuffer.get());
        System.out.println(byteBuffer.position()+":"+byteBuffer.getInt());
    }

    /**
     * 创建字节缓冲区对象，写入数据，然后反转，最后获取数据
     * */
    @Test
    public void testFlip(){
        ByteBuffer byteBuffer = ByteBuffer.allocate(10);
        byte b1 = 1;
        byte b2 = 5;
        byte b3 = 10;

        byteBuffer.put(b1);
        byteBuffer.put(b2);
        byteBuffer.put(b3);

        byteBuffer.flip();

        for(int i = 0 ; i < byteBuffer.limit() ;i ++){
            System.out.println(byteBuffer.position()+":"+byteBuffer.get());
        }
    }


    /**
     * flip源码也是通过设置limit= positon和 positon = 0来实现
     * */
    @Test
    public void testMyFlip(){
        // 创建字节缓冲区对象，分配缓冲区容量(capacity=10)
        ByteBuffer buffer = ByteBuffer.allocate(10);
        // 添加字节数据
        byte b1 = 1;
        byte b2 = 5;
        byte b3 = 10;


        buffer.put(b1);
        buffer.put(b2);
        buffer.put(b3);

        // 设置缓冲区限制和位置  这两句 = flip
        buffer.limit(buffer.position());
        buffer.position(0);

        // 获取全部元素并输出
        for (int i = 0; i < buffer.limit(); i++) {
            System.out.println(buffer.position() + ": " + buffer.get());
        }

        // 获取全部元素并输出
        for (int i = 0; i < buffer.limit(); i++) {
            System.out.println(i + ": " + buffer.get(i));
        }
    }

    /**
     * 判断是否还有数据
     * position < limit;
     * */
    @Test
    public void testHasRemaining(){
        // 创建字节缓冲区对象，分配缓冲区容量(capacity=10)
        ByteBuffer buffer = ByteBuffer.allocate(10);
        // 添加字节数据
        byte b1 = 1;
        byte b2 = 5;
        byte b3 = 10;


        buffer.put(b1);
        buffer.put(b2);
        buffer.put(b3);

        buffer.flip();

        while(buffer.hasRemaining()){
            System.out.println(buffer.position() + ": " + buffer.get());
        }
    }

    @Test
    /**
     * 英文需要经过强制类型转换
     * */
    public void testEnglishLetter(){
        String msg = "Hello World";
        ByteBuffer byteBuffer = ByteBuffer.wrap(msg.getBytes());

        while(byteBuffer.hasRemaining()){
            System.out.println(byteBuffer.position() + ": " + (char)byteBuffer.get());
        }

        byteBuffer.position(0);

        while(byteBuffer.hasRemaining()){
            System.out.print((char)byteBuffer.get());
        }
    }

    @Test
    public void testChineseLetter(){
        String msg = "天长地久";
        try {
            ByteBuffer byteBuffer = ByteBuffer.wrap(msg.getBytes("utf-8"));
            while(byteBuffer.hasRemaining()){
                System.out.print(byteBuffer.position() + ": " + (char)byteBuffer.get());
            }

            System.out.println("\n字节缓冲区长度：" + byteBuffer.capacity());
            System.out.println("字节缓冲区长度：" + byteBuffer.limit());
            System.out.println("字节缓冲区长度：" + byteBuffer.position());

            // 将字节缓冲区数据封装成utf-8字符串
            String content = new String(byteBuffer.array(), "utf-8");
            System.out.println(content);

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testChineseLetterGB2312(){
        String msg = "天长地久";
        try {
            ByteBuffer byteBuffer = ByteBuffer.wrap(msg.getBytes("gb2312"));
            while(byteBuffer.hasRemaining()){
                System.out.print(byteBuffer.position() + ": " + (char)byteBuffer.get());
            }

            System.out.println("\n字节缓冲区长度：" + byteBuffer.capacity());
            System.out.println("字节缓冲区长度：" + byteBuffer.limit());
            System.out.println("字节缓冲区长度：" + byteBuffer.position());

            // 将字节缓冲区数据封装成utf-8字符串
            String content = new String(byteBuffer.array(), "gb2312");
            System.out.println(content);

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }



}
