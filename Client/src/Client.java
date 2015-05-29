import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;


public class Client {
	private static String dirServer;	
	private static Map<String, Boolean> ackMessage;
		

	public static void main(String[] args) {
		dirServer = "192.168.173.1";
		ackMessage = new HashMap<String, Boolean>();
		try {
			DatagramSocket socket =new DatagramSocket();
						
			Message m = new Message(InetAddress.getLocalHost().getHostAddress());
			ByteArrayOutputStream bs = new ByteArrayOutputStream();
			ObjectOutputStream os = new ObjectOutputStream(bs);
			os.writeObject(m);
			byte[] mensajeEnviar = bs.toByteArray();
            
			DatagramPacket paqueteEnviar= new DatagramPacket(mensajeEnviar, mensajeEnviar.length,InetAddress.getByName(dirServer),8888);
            System.out.println("Enviando: "+m.getID());
            ackMessage.put(m.getID(), new Boolean(false));
            socket.send(paqueteEnviar);
            socket.close();
            
            
            new Thread(new tReceiveServer (ackMessage)).start();        
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

	}

}
