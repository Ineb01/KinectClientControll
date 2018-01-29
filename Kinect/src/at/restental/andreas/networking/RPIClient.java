package at.restental.andreas.networking;

import java.io.*;
import java.net.*;

public class RPIClient extends Socket {

	public PrintWriter out;
	public BufferedReader in;

	/**
	 * Creates a socket, a PrintWriter and a Buffered Reader to read and write to the stream 
	 * @param port port the socket should bind to
	 * @param hostname the hostname of the server
	 * @throws UnknownHostException if the host is unknown
	 * @throws IOException if creation of either in or out fails
	 */
	public RPIClient(int port, String hostname) throws UnknownHostException, IOException {
		super(hostname, port);
		out = new PrintWriter(this.getOutputStream());
		in = new BufferedReader(new InputStreamReader(this.getInputStream()));
	}

	/**
	 * closes in and out so no more can be read and written. does not close the socket
	 */
	public void cleanup() {
		out.close();
		try {
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
