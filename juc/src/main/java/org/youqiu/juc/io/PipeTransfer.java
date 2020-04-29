package org.youqiu.juc.io;

import java.io.IOException;
import java.io.PipedReader;
import java.io.PipedWriter;

/**
 * Description
 * <br> Created by WangKun on 2020/04/29
 * <br> 管道输入输出流,适合线程间一对一的通信，适用范围较狭窄。
 * <br> pipedOutputStream/input 面向的字节
 * pipedReader/Writer 面向的是字符
 **/
public class PipeTransfer {


    public static void main(String[] args) throws IOException {

        PipedWriter out = new PipedWriter();
        PipedReader in = new PipedReader();

        out.connect(in);

        new Thread(() -> {
            try {
                int receive = 0;
                while ((receive = in.read()) != -1) {
                    System.out.println((char) receive);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();

        int receive = 0;
        try {
            while ((receive = System.in.read()) != -1) {
                out.write(receive);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            out.close();
        }
    }
}
