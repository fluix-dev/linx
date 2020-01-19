import java.io.IOException;
import java.io.ObjectInputStream.GetField;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;

public class Hub {
	int level;

	String parent_addr;
	int parent_port;

	String[] child_hub_addrs;
	int[] child_hub_ports;

	String[] child_node_addrs;
	int[] child_node_ports;

	public static void main(String[] args) throws IOException {
		// Hub hub = new Hub(Integer.parseInt(args[0]), Integer.parseInt(args[1]));
		Hub hub = new Hub(Main.AVAILABLE_PORTS[Main.MAX_LEVEL][0], 2);
	}

	public Hub(int port, int level) throws IOException {
		this.level = level;
		
		child_hub_addrs = new String[0];
		child_hub_ports = new int[0];
		child_node_addrs = new String[0];
		child_node_ports = new int[0];

		Server server = new Server();
		server.start();
		server.bind(port);

		Kryo kryo = server.getKryo();
		kryo.register(TextRequest.class);
		kryo.register(TextResponse.class);
		kryo.register(String[].class);
		kryo.register(int[].class);
		kryo.register(GetHubListRequest.class);
		kryo.register(GetHubListResponse.class);

		server.addListener(new Listener() {
			public void received(Connection connection, Object object) {
				if (object instanceof TextRequest) {
					TextRequest request = (TextRequest) object;
					System.out.println(request.text);

					TextResponse response = new TextResponse();
					response.text = connection.getID() + ": " + request.text;
					server.sendToAllTCP(response);
				}

				if (object instanceof GetHubListRequest) {
					GetHubListResponse response = new GetHubListResponse();
					response.addrs = child_hub_addrs;
					response.ports = child_hub_ports;
					connection.sendTCP(response);
				}
			}
		});
	}
}
