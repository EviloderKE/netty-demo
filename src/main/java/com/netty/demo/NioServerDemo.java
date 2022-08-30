package com.netty.demo;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Set;

public class NioServerDemo {

    public static void main(String[] args) throws IOException {

        final ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();

        serverSocketChannel.socket().bind(new InetSocketAddress(8081));

        serverSocketChannel.configureBlocking(false);

        final Selector selector = Selector.open();
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

        while (true){

            // 等待1s 没有事件发生就继续循环
            if (selector.select(1000) == 0){
                System.out.println("无连接");
                continue;
            }

            final Set<SelectionKey> selectionKeys = selector.selectedKeys();

            selectionKeys.forEach(e -> {
                if(e.isAcceptable()){
                    try {
                        final SocketChannel socketChannel = serverSocketChannel.accept();
                        socketChannel.register(selector, SelectionKey.OP_READ, ByteBuffer.allocate(1024));
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }

                if(e.isReadable()){
                    final SocketChannel channel = (SocketChannel) e.channel();
                    final ByteBuffer buffer = (ByteBuffer) e.attachment();
                    try {
                        channel.read(buffer);
                        System.out.println("输出：" + new String(buffer.array()));
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }

            });
        }
    }
    
}
