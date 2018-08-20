package cn.leran.currentroad.chapter5.socket.common;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 通信服务器.
 *
 * @author shaoyijiong
 * @date 2018/7/23
 */
public class Server {

  private static final int PORT = 2333;
  private static ExecutorService executorService = Executors.newCachedThreadPool();

  static class HandleMsg implements Runnable {

    Socket clientSocket;

    HandleMsg(Socket clientSocket) {
      this.clientSocket = clientSocket;
    }

    @Override
    public void run() {
      //这里的is 和 os 类的都实现类AutoCloseable接口,不需要再finally里关闭连接了
      try (BufferedReader is = new BufferedReader(
          new InputStreamReader(clientSocket.getInputStream()));
          PrintWriter os = new PrintWriter(clientSocket.getOutputStream(), true)
      ) {
        String inputLine;
        long b = System.currentTimeMillis();
        while ((inputLine = is.readLine()) != null) {
          os.println(inputLine);
        }
        long e = System.currentTimeMillis();
        System.out.println("speed:" + (e - b) + "ms");
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

  /**
   * 启动服务器.
   */
  public static void main(String[] args) throws IOException {
    ServerSocket serverSocket;
    Socket clientSocket;
    serverSocket = new ServerSocket(PORT);
    while (true) {
      //侦听并接受到此套接字的连接.
      clientSocket = serverSocket.accept();
      System.out.println(clientSocket.getRemoteSocketAddress() + " connect!");
      executorService.execute(new HandleMsg(clientSocket));
    }
  }
}
