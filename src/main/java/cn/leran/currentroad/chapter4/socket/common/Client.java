package cn.leran.currentroad.chapter4.socket.common;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * 通信客户端.
 *
 * @author shaoyijiong
 * @date 2018/7/23
 */
public class Client {

  private static final String HOST = "127.0.0.1";
  private static final Integer PORT = 2333;

  /**
   * 启动客户端.
   */
  public static void main(String[] args) throws IOException {
    //这里使用了try-with-resource
    try (Socket client = new Socket();
        //字符流输出
        PrintWriter writer = new PrintWriter(client.getOutputStream(), true);
        //来自客户端的字符流
        BufferedReader reader = new BufferedReader(
            new InputStreamReader(client.getInputStream()))) {
      client.connect(new InetSocketAddress(HOST, PORT));
      writer.println("Hello!");
      writer.flush();
      System.out.println("from server:" + reader.readLine());
    } catch (IOException e) {
      System.out.println(e);
    }
  }
}
