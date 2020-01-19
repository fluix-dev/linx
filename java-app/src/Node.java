import java.io.IOException;
import java.util.Scanner;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

public class Node {
	
	public static void main(String[] args) throws IOException {
		Node node = new Node();
	}
	
	Scanner in = new Scanner(System.in);
	
	public Node() throws IOException {
	    Client client = new Client();
	    client.start();
	    client.connect(5000, "localhost", 33334);
	    
	    client.addListener(new Listener() {
		       public void received (Connection connection, Object object) {
		          if (object instanceof TextResponse) {
		             TextResponse response = (TextResponse)object;
		             System.out.println(response.text);
		          }
		       }
		       
		    });
	    
	    Kryo kryo = client.getKryo();
	    kryo.register(TextRequest.class);
	    kryo.register(TextResponse.class);
	    
	    while (true) {
		    TextRequest request = new TextRequest();
		    request.text = in.nextLine();
		    client.sendTCP(request);
	    }
	}
	
}
