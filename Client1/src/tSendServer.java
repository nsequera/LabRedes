import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Map;


public class tSendServer implements Runnable{
	
	private static String dirServer;	
	private static Map<String, Boolean> ackMessage;
	
	
	public tSendServer(String dirServer_, Map<String, Boolean> ackMessage_) {
		dirServer = dirServer_;
		ackMessage = ackMessage_;
		new Thread(new tReceiveServer (ackMessage)).start();
	}

	public void run() {
		sendMessage();
	}
	
	public static void sendMessage (){
		try {
			DatagramSocket socket =new DatagramSocket();
						
			Message m = new Message(InetAddress.getLocalHost().getHostAddress());
			ByteArrayOutputStream bs = new ByteArrayOutputStream();
			ObjectOutputStream os = new ObjectOutputStream(bs);
			os.writeObject(m);
			byte[] mensajeEnviar = bs.toByteArray();
            
			DatagramPacket paqueteEnviar= new DatagramPacket(mensajeEnviar, mensajeEnviar.length,InetAddress.getByName("192.168.173.255"),8889);
            System.out.println("Enviando: "+m.getID());
            ackMessage.put(m.getID(), new Boolean(false));
            socket.send(paqueteEnviar);
            socket.close();
      
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
