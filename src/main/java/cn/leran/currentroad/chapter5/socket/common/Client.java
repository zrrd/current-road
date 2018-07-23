package cn.leran.currentroad.chapter5.socket.common;

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

  public static void main(String[] args) throws IOException {
    Socket client = null;
    PrintWriter writer = null;
    BufferedReader reader = null;
    try {
      client = new Socket();
      client.connect(new InetSocketAddress("localhost", 2333));
      writer = new PrintWriter(client.getOutputStream(), true);
      writer.println("Hello!");
      writer.flush();
      reader = new BufferedReader(new InputStreamReader(client.getInputStream()));
      System.out.println("from server:" + reader.readLine());
    } catch (IOException e) {
      System.out.println(e);
    } finally {
      client.close();
      writer.close();
      reader.close();
    }
  }
}
