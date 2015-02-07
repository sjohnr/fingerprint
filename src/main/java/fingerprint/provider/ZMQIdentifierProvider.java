package fingerprint.provider;

import org.zeromq.ContextFactory;
import org.zeromq.api.Context;
import org.zeromq.api.MessageFlag;
import org.zeromq.api.Socket;
import org.zeromq.api.SocketType;

import fingerprint.Identifier;
import fingerprint.IdentifierProvider;
import fingerprint.identifier.BigIntegerIdentifier;

public class ZMQIdentifierProvider implements IdentifierProvider {
	private static final byte[] GET = "GET".getBytes();
	private static final byte[] SET = "SET".getBytes();
	
	private Context context;
	private Socket socket;
	
	public ZMQIdentifierProvider(String host) {
		this.context = ContextFactory.createContext(1);
		this.socket = context.buildSocket(SocketType.REQ)
			.connect(String.format("tcp://%s:7157", host));
	}
	
	@Override
	public void setID(Identifier identifier) {
		socket.send(SET, MessageFlag.SEND_MORE);
		socket.send(identifier.getBytes());
		
		String response = new String(socket.receive());
		assert (response.equals("OK"));
	}
	
	@Override
	public Identifier getID() {
		socket.send(GET);
		
		byte[] bytes = socket.receive();
		return new BigIntegerIdentifier(bytes);
	}
	
	@Override
	protected void finalize() throws Throwable {
		socket.close();
		context.close();
	}
}
