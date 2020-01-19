import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.util.Arrays;
import java.util.Scanner;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

public class Node {

	Client client;
	Scanner in = new Scanner(System.in);

	public static void main(String[] args) throws IOException {
		Node node = new Node();
	}

	public Node() throws IOException {
		connect();
		addListener();
		registerKyro();

		GetHubListRequest ghl_request = new GetHubListRequest();
		client.sendTCP(ghl_request);

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

	private void addListener() {
		client.addListener(new Listener() {
			public void received(Connection connection, Object object) {
				if (object instanceof TextResponse) {
					TextResponse response = (TextResponse) object;
					System.out.println(response.text);
				}

				if (object instanceof GetHubListResponse) {
					GetHubListResponse response = (GetHubListResponse) object;
					if (response.addrs.length == 0) {
						System.out.println("Transforming into [L1] hub.");
						try {
							Hub hub = new Hub(Main.AVAILABLE_PORTS[1][0], 1);
						} catch (IOException e) {
							e.printStackTrace();
						}
						client.close();
					}
				}
			}
		});
	}

	private void registerKyro() {
		Kryo kryo = client.getKryo();
		kryo.register(TextRequest.class);
		kryo.register(TextResponse.class);
		kryo.register(String[].class);
		kryo.register(int[].class);
		kryo.register(GetHubListRequest.class);
		kryo.register(GetHubListResponse.class);
	}

}
