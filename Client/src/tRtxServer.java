import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;



public class tRtxServer implements Runnable{
	Map<String, Boolean> ackMessages;
	Set<String> messages;
	int p;
	public tRtxServer( int p_ ) {
		messages  = new HashSet<String>();
		p = p_;
	}
	public void run() {
		System.out.println("jjf");
		while (true){
			listen ();
		}
		
	}
	public void listen (){
		DatagramSocket socket;
		try {
			socket = new DatagramSocket(p);
			socket.setBroadcast(true);
	        byte[] mensajeRecibido = new byte[15000];
	        DatagramPacket paqueteRecibido=new DatagramPacket(mensajeRecibido, mensajeRecibido.length);

	        socket.receive(paqueteRecibido);
	        ByteArrayInputStream bs = new ByteArrayInputStream(paqueteRecibido.getData());
	        ObjectInputStream is = new ObjectInputStream (bs);
	        Object o = is.readObject();
	        is.close();
	        socket.close();
	        System.out.println(o.getClass());
	        if (o instanceof Message){
	        	Message m = (Message) o;
	        	if (!m.getIP().equals(InetAddress.getLocalHost().getHostAddress()) && !messages.contains(m.getID())){
	        		messages.add(m.getID());
	        		tx(m);
	        	}
	        }else if (o instanceof MessageACK){
	        	MessageACK mACK = (MessageACK) o;
	        	if (!mACK.getIP().equals(InetAddress.getLocalHost().getHostAddress()) && !messages.contains(mACK.getID())){
	        		messages.add(mACK.getID());
	        		tx(mACK);
	        	}
	        }
	        
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
	}
	private void tx(Message m) {
		try {
			DatagramSocket socket =new DatagramSocket();
						
			ByteArrayOutputStream bs = new ByteArrayOutputStream();
			ObjectOutputStream os = new ObjectOutputStream(bs);
			os.writeObject(m);
			byte[] mensajeEnviar = bs.toByteArray();
            
			DatagramPacket paqueteEnviar= new DatagramPacket(mensajeEnviar, mensajeEnviar.length,InetAddress.getByName("192.168.173.255"),8887);
            System.out.println("----->Reenviando: "+m.getID());
            
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
	
	private void tx(MessageACK m) {
		try {
			DatagramSocket socket =new DatagramSocket();
						
			ByteArrayOutputStream bs = new ByteArrayOutputStream();
			ObjectOutputStream os = new ObjectOutputStream(bs);
			os.writeObject(m);
			byte[] mensajeEnviar = bs.toByteArray();
            
			DatagramPacket paqueteEnviar= new DatagramPacket(mensajeEnviar, mensajeEnviar.length,InetAddress.getByName("192.168.173.255"),8889);
            System.out.println("----->Reenviando: "+m.getID());
            
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
