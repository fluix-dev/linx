import java.io.IOException;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;

public class Hub {
	int level;
	Hub parent;
	Hub[] child_hubs;
	Node[] child_nodes;
	
	public static void main(String[] args) throws IOException {
		Hub hub = new Hub();
	}
	
	public Hub() throws IOException {
		Server server = new Server();
	    server.start();
		server.bind(33334);
		
	    Kryo kryo = server.getKryo();
	    kryo.register(TextRequest.class);
	    kryo.register(TextResponse.class);
	    
		
	    server.addListener(new Listener() {
	        public void received (Connection connection, Object object) {
	           if (object instanceof TextRequest) {
	              TextRequest request = (TextRequest)object;
	              System.out.println(request.text);
	     
	              TextResponse response = new TextResponse();
	              response.text = connection.getID() + ": " + request.text;
	              server.sendToAllTCP(response);
	           }
	        }
	     });
	}
}
