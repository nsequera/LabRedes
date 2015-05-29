import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;



public class SendMessage extends Thread {
	private String clientIP;
	private String messageID;
	private MessageACK mACK;
	public SendMessage(String ipOrigen, String messageACK) {
		super();
		this.clientIP = ipOrigen;
		this.messageID = messageACK;
		mACK = new MessageACK(ipOrigen, messageACK);
	}
	
	public SendMessage() {
		// TODO Auto-generated constructor stub
	}

	public void SendProductss(){
		try {
			DatagramSocket socket =new DatagramSocket();
			
			ByteArrayOutputStream bs = new ByteArrayOutputStream();
			ObjectOutputStream os = new ObjectOutputStream(bs);
			os.writeObject(mACK);
			byte[] mensajeEnviar = bs.toByteArray();
            
			
			
			DatagramPacket paqueteEnviar= new DatagramPacket(mensajeEnviar, mensajeEnviar.length,InetAddress.getByName("192.168.173.255"),8887);
            System.out.println("-->Enviando confirmación.");
            socket.send(paqueteEnviar);
            System.out.println("->Confirmación enviada.");
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
	
	@Override
	public void run() {
		SendProductss();
	}
}
