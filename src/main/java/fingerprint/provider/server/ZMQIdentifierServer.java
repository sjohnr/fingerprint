package fingerprint.provider.server;

import org.zeromq.ContextFactory;
import org.zeromq.api.Backgroundable;
import org.zeromq.api.Context;
import org.zeromq.api.PollAdapter;
import org.zeromq.api.Poller;
import org.zeromq.api.PollerType;
import org.zeromq.api.Socket;
import org.zeromq.api.SocketType;
import org.zeromq.api.exception.ContextTerminatedException;
import org.zeromq.api.exception.InvalidSocketException;

import fingerprint.IdentifierProvider;
import fingerprint.identifier.BigIntegerIdentifier;

public class ZMQIdentifierServer {
	private static final byte[] OK = "OK".getBytes();
	private IdentifierProvider provider;
	
	private Context context;
	
	public ZMQIdentifierServer(IdentifierProvider provider) {
		this.provider = provider;
	}
	
	public void start() {
		this.context = ContextFactory.createContext(1);
		
		// run agent in the background
		context.buildSocket(SocketType.REP)
			.withBackgroundable(new BackgroundAgent())
			.bind("tcp://*:7157");
	}
	
	public void destroy() {
		context.close();
	}
	
	private class BackgroundAgent extends PollAdapter implements Backgroundable {
		private volatile boolean running = true;
		
		@Override
		public void run(Context context, Socket socket, Object... args) {
			Poller poller = context.buildPoller()
				.withPollable(context.newPollable(socket, PollerType.POLL_IN), this)
				.create();
			while (running) {
				try {
					poller.poll();
				} catch (ContextTerminatedException | InvalidSocketException ignored) {
				}
			}
			
			socket.close();
		}
		
		@Override
		public void handleIn(Socket socket) {
			String request = new String(socket.receive());
			switch (request) {
				case "GET":
					socket.send(provider.getID().getBytes());
					break;
				case "SET":
					provider.setID(new BigIntegerIdentifier(socket.receive()));
					socket.send(OK);
					break;
			}
		}

		@Override
		public void onClose() {
			running = false;
		}
	}
}
