import java.io.IOException;
import java.util.ArrayList;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;

public class Hub {
	Server server;
	
	int level;

	String parent_addr;
	int parent_port;

	ArrayList<String> child_hub_addrs;
	ArrayList<Integer> child_hub_ports;

	ArrayList<String> child_node_addrs;
	ArrayList<Integer> child_node_ports;

	public static void main(String[] args) throws IOException {
		// Hub hub = new Hub(Integer.parseInt(args[0]), Integer.parseInt(args[1]));
		Hub hub = new Hub(Main.AVAILABLE_PORTS[Main.MAX_LEVEL][0], 2);
	}

	public Hub(int port, int level) throws IOException {
		this.level = level;
		
		child_hub_addrs = new ArrayList<String>();
		child_hub_ports = new ArrayList<Integer>();
		child_node_addrs = new ArrayList<String>();
		child_node_ports = new ArrayList<Integer>();

		connect(port);	
		addListener();

		Kryo kryo = server.getKryo();
		Main.registerKyro(kryo);
	}
	
	public void connect(int port) throws IOException {
		server = new Server();
		server.start();
		server.bind(port);
	}
	
	private void sendToParent(TextRequest obj) throws IOException {
		Client client = new Client();
		client.start();
		Main.registerKyro(client.getKryo());
		client.addListener(new Listener() {
			public void received(Connection connection, Object object) {
				if (object instanceof TextResponse) {
					client.close();
				}
			}});
		client.connect(5000, "localhost", 20001);
		System.out.println(" - Sending socket");
		client.sendTCP(obj);
	}
	
	public void addListener() {
		server.addListener(new Listener() {
			public void received(Connection connection, Object object) {
				if (object instanceof TextRequest) {
					TextRequest request = (TextRequest) object;
					System.out.println(request.text);

					TextResponse response = new TextResponse();
					response.text = connection.getID() + ": " + request.text;
					if (level != Main.MAX_LEVEL) {
						server.sendToAllExceptTCP(connection.getID(), response);
						try {
							TextRequest tr = new TextRequest();
							tr.text = request.text;
							sendToParent(tr);
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}

				if (object instanceof GetHubListRequest) {
					GetHubListResponse response = new GetHubListResponse();
					response.addrs = child_hub_addrs;
					response.ports = child_hub_ports;
					connection.sendTCP(response);
				}
				
				if (object instanceof AddToHubListRequest) {
					child_hub_addrs.add(connection.getRemoteAddressTCP().getHostName());
					child_hub_ports.add(connection.getRemoteAddressTCP().getPort());
				}
			}
		});
	}
}
