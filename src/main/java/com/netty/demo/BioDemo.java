package com.netty.demo;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class BioDemo {

    /**
     * bio就是一个连接分配一个线程
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        final ExecutorService executorService = Executors.newCachedThreadPool();

        final ServerSocket serverSocket = new ServerSocket(8081);
        System.out.println("服务器启动");

        while (true) {
            final Socket accept = serverSocket.accept();

            System.out.println("客户端链接");

            executorService.execute(() -> {
                handler(accept);
            });
        }

    }

    /**
     * telnet 127.0.0.1 8081
     * ctrl + ]
     * send aa
     *
     * @param socket
     */
    public static void handler(Socket socket) {
        try {
            System.out.println("线程id: " + Thread.currentThread().getId() + ",线程名: " + Thread.currentThread().getName());

            byte[] bytes = new byte[1024];
            InputStream inputStream = socket.getInputStream();
            while (true) {
                //读取数据 阻塞
                int read = inputStream.read(bytes);
                if (read != -1) {
                    System.out.println(new String(bytes, 0, read));
                } else {
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                System.out.println("socket关闭");
                socket.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

}
