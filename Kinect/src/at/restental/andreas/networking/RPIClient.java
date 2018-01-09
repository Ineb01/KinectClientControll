package at.restental.andreas.networking;

import java.io.*;
import java.net.*;

public class RPIClient extends Socket {

	public PrintWriter out;
	public BufferedReader in;

	public RPIClient(int port, String hostname) throws UnknownHostException, IOException {
		super(hostname, port);
		out = new PrintWriter(this.getOutputStream());
		in = new BufferedReader(new InputStreamReader(this.getInputStream()));
	}

	public void cleanup() {
		out.close();
		try {
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
