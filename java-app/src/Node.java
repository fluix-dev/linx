import java.io.IOException;
import java.util.Scanner;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

public class Node {

	Client client;
	Scanner in = new Scanner(System.in);

	public static void main(String[] args) throws IOException {
		Node node;
		if (args.length == 0)
			node = new Node(-1);
		else
			node = new Node(Integer.parseInt(args[0]));
	}

	public Node(int port) throws IOException {
		if (port == -1)
			connect();
		else
			connect(port);
		addListener();
		
		Kryo kryo = client.getKryo();
		Main.registerKyro(kryo);
		
		if (port == -1) {
			GetHubListRequest ghl_request = new GetHubListRequest();
			client.sendTCP(ghl_request);
		}

		while (true) {
			TextRequest request = new TextRequest();
			request.text = in.nextLine();
			client.sendTCP(request);
		}
	}

	private void connect() throws IOException {
		client = new Client();
		client.start();
		for (int i = 0; i < Main.AVAILABLE_PORTS[Main.MAX_LEVEL].length; i++) {
			try {
				client.connect(5000, "localhost", Main.AVAILABLE_PORTS[Main.MAX_LEVEL][i]);
				break;
			} catch (IOException e) {
				continue;
			}
		}
		if (!client.isConnected()) {
			System.out.println("Transforming into [L2] hub.");
			Hub hub = new Hub(Main.AVAILABLE_PORTS[Main.MAX_LEVEL][0], 2);
		}
	}
	
	private void connect(int port) throws IOException {
		client = new Client();
		client.start();
		client.connect(5000, "localhost", port);
	}

	private void addListener() {
		client.addListener(new Listener() {
			public void received(Connection connection, Object object) {
				if (object instanceof TextResponse) {
					TextResponse response = (TextResponse) object;
					System.out.println(response.text);
				}

				if (object instanceof GetHubListResponse) {
					GetHubListResponse response = (GetHubListResponse) object;
					if (response.addrs.size() < 2) {
						System.out.println("Transforming into [L1] hub.");
						AddToHubListRequest request = new AddToHubListRequest();
						client.sendTCP(request);
						try {
							Hub hub = new Hub(Main.AVAILABLE_PORTS[1][response.addrs.size()], 1);
						} catch (IOException e) {
							e.printStackTrace();
						}
						client.close();
					}/* else {
						int[] clients = new int[response.addrs.size()];
						for (int i = 0; i < response.addrs.size(); i ++) {
							try {
								connect(response.ports.get(i));
								GetNodeListRequest request = new GetNodeListRequest();
								client.sendTCP(request);
							} catch (IOException e) {
								e.printStackTrace();
							}
							
						}
					}*/
				}
			}
		});
	}
}
