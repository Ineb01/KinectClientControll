
import java.io.*;
import java.net.*;

public class RPIClient {
	private Socket socket;
	public PrintStream stream;
	private BufferedReader reader;
	private int port;
	private String host;

	public RPIClient(int port, String hostname) throws UnknownHostException, IOException {
		this.port = port;
		host = hostname;
		socket = new Socket(hostname, port);
		stream = new PrintStream(socket.getOutputStream());
		reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
	}

	public void close() {
		try {
			socket.close();
			stream.close();
			reader.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void println(String s) {
		stream.println(s);
	}

	public String readLine() {
		String s = null;
		try {
			s = reader.readLine();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return s;
	}

}
