package com.netty.demo;

import java.nio.IntBuffer;

public class BufferDemo {

    public static void main(String[] args) {

        final IntBuffer allocate = IntBuffer.allocate(5);

        allocate.put(1);
        allocate.put(2);
        // 从写入变成读取
        allocate.flip();

       while (allocate.hasRemaining()){
           System.out.println(allocate.get());
       }
    }

}
