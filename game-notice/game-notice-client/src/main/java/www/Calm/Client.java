package www.Calm;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

/**
 * 从服务器读取信息线程
 */
class ReadFormServerThread implements Runnable{
    private Socket client;
    Scanner in = null;
    public ReadFormServerThread(Socket cilent) {
        this.client = cilent;
    }
    @Override
    public void run() {
        try {
            in = new Scanner(client.getInputStream());
            in.useDelimiter("\n");
            while(true){
                if(in.hasNextLine()){
                    System.out.println("公告："+in.nextLine());
                }
                if(client.isClosed()){
                    //System.out.println("客户端已关闭");
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            in.close();
        }
    }
}

/**
 * 向服务器端写入信息线程
 */
class WriteToServerThread implements Runnable {
    private Socket client;
    Scanner scanner = null;
    PrintStream write = null;

    public WriteToServerThread(Socket cilent) {
        this.client = cilent;
    }

    @Override
    public void run() {
        scanner = new Scanner(System.in);
        scanner.useDelimiter("\n");
        try {
            write = new PrintStream(client.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        while(true){
            String str = null;
            System.out.println("请输入要发送的信息....");
            if(scanner.hasNextLine()){
                str = scanner.nextLine();
                write.println(str);
            }
            if(str.equals("bye")){
                try {
                    System.out.println("您下线了");
                    client.close();
                    scanner.close();
                    write.close();
                    break;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

public class Client {
    public static void main(String[] args) {
        try {
            Socket client = new Socket("127.0.0.1", 8888);
            ReadFormServerThread readFormServerThread = new ReadFormServerThread(client);
            WriteToServerThread writeToServerThread = new WriteToServerThread(client);
            Thread readThread = new Thread(readFormServerThread);
            Thread writeThread = new Thread(writeToServerThread);
            writeThread.start();
            Thread.sleep(1000);
            readThread.start();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
