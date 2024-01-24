import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.awt.Color;

public class Server {

  private int port;
  private List<User> clients;
  private ServerSocket server;

  public static void main(String[] args) throws IOException {
    new Server(12345).run();
  }

  public Server(int port) {
    this.port = port;
    this.clients = new ArrayList<User>();
  }

  public void run() throws IOException {
    server = new ServerSocket(port);
    System.out.println("Port 12345 is now open.");

    try {
      while (!server.isClosed()) {
        // accepts a new client
        Socket client = server.accept();

        // get nickname of newUser
        String nickname = (new Scanner ( client.getInputStream() )).nextLine();
        nickname = nickname.replace(",", ""); //  ',' use for serialisation
        nickname = nickname.replace(" ", "_");
        System.out.println("New Client: \"" + nickname + "\"\n\t     Host:" + client.getInetAddress().getHostAddress());

        // create new User
        User newUser = new User(client, nickname);
