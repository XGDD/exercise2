package www.Calm;


import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Serve {
    //存储所有登陆的客户端
    private static Map<String, Socket> clientMap = new ConcurrentHashMap<>();
    //具体处理与每个客户端通信的内部类
    private static class ExecuteClient implements Runnable{

        private Socket client;

        public ExecuteClient(Socket client) {
            this.client = client;
        }

        @Override
        public void run() {
            try {
                //获取客户端输入流
                Scanner scanner = new Scanner(client.getInputStream());
                String strFromClient;
                while(true){
                    if(scanner.hasNextLine()){
                        strFromClient = scanner.nextLine();
                        //windows下将默认换行\r\n中的\r替换为空字符串
                        Pattern pattern = Pattern.compile("\r");
                        Matcher matcher = pattern.matcher(strFromClient);
                        strFromClient = matcher.replaceAll("");
                        //登陆流程
                        if(strFromClient.startsWith("userName")){
                            String userName = strFromClient.split(":")[1];
                            registerUser(userName,client);
                            continue;
                        }
                        //群聊流程
                        if(strFromClient.startsWith("G")){
                            String msg = strFromClient.split(":")[1];
                            groupChat(msg);
                            continue;
                        }
                        //私聊流程
                        if(strFromClient.startsWith("P")){
                            String userName = strFromClient.split(":")[1];
                            String msg = strFromClient.split(":")[2];
                            privateChat(userName, msg);
                        }
                        //退出流程
                        if(strFromClient.contains("bye")){
                            String byeName = null;
                            for(String keyName : clientMap.keySet()){
                                if(clientMap.get(keyName).equals(client)){
                                    byeName = keyName;
                                }
                            }
                            System.out.println(byeName + "下线了");
                            //clientMap.remove(byeName);
                            System.out.println("现在还有" + (clientMap.size()-1) + "人在线");
                            //公告告诉每个在线用户xx下线了
                            for(Map.Entry<String, Socket> entrySet : clientMap.entrySet()){
                                Socket socket = entrySet.getValue();
                                PrintStream printStream = new PrintStream(socket.getOutputStream());
                                printStream.println(byeName + "下线了");
                                printStream.println("现在还有" + (clientMap.size()-1) + "人");
                                continue;
                            }
                            clientMap.remove(byeName);
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        //登陆流程
        private void registerUser(String userName, Socket client) throws IOException {
            System.out.println("用户名为：" + userName + "，端口号为：" + client.getPort() + "的用户上线了");
           // System.out.println(userName + "上线了");
            System.out.println("在线人数为：" + (clientMap.size() + 1));
            //将新登陆的客户端存入Map中
            clientMap.put(userName, client);
            Set<Map.Entry<String, Socket>> entrySet = clientMap.entrySet();
            //公告，给每个在线的用户通知当前的在线人数和谁上线了
            for(Map.Entry<String, Socket> map: entrySet){
                PrintStream printStream = new PrintStream(map.getValue().getOutputStream());
                printStream.println(userName + "上线了");
                printStream.println("当前在线人数为:" + clientMap.size());
            }
        }
        //群聊流程
        private void groupChat(String msg){
            //遍历clientMap，给每位在线的人发群聊信息
            Set<Map.Entry<String, Socket>> clientSet = clientMap.entrySet();
            for(Map.Entry<String, Socket> entry : clientSet){

                Socket socket = entry.getValue();
                //取得每个客户端的输出流
                try {
                    PrintStream printStream = new PrintStream(socket.getOutputStream());
                    printStream.println("端口号为" + socket.getPort() +"的用户发来群聊消息为：" + msg);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        //私聊流程
        private void privateChat(String userName, String msg){
            //找到要私聊的对象
            Socket socket = clientMap.get(userName);
            try {
                PrintStream printStream = new PrintStream(socket.getOutputStream());
                printStream.println("端口号为：" + socket.getPort() +"的用户发来私聊消息为：" + msg);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        //用线程池创建10个线程
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        try {
            ServerSocket serverSocket = new ServerSocket(8888);
            for(int i = 0; i < 10; i++){
                System.out.println("等待连接....");
                Socket socket = serverSocket.accept();
                System.out.println("有端口号为" + socket.getPort() + "的新用户连接");
                //将每个连接的客户端放进线程池中
                executorService.submit(new ExecuteClient(socket));
            }
            executorService.shutdown();
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
