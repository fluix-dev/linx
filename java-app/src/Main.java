import java.io.IOException;
import java.util.ArrayList;

import com.esotericsoftware.kryo.Kryo;

public class Main {
	
	public static final int MAX_LEVEL = 2;
	public static final int[][] AVAILABLE_PORTS = {
			{},
			{10001, 10002},
			{20001}};
	
	public static void main(String[] args) throws IOException {
	    //Hub hub = new Hub();
	    //Node node1 = new Node();
	    //Node node2 = new Node();
	}
	
	
	public static void registerKyro(Kryo kryo) {
		kryo.register(TextRequest.class);
		kryo.register(TextResponse.class);
		kryo.register(String[].class);
		kryo.register(int[].class);
		kryo.register(ArrayList.class);
		kryo.register(GetHubListRequest.class);
		kryo.register(GetHubListResponse.class);
		kryo.register(AddToHubListRequest.class);
		kryo.register(GetNodeListRequest.class);
		kryo.register(GetNodeListResponse.class);
	}
}
