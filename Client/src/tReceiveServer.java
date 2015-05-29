
import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Map;


public class tReceiveServer implements Runnable{

	private static Map<String, Boolean> ackMessages;
	public static void listenConfirmation(){
		  boolean flag = true;
		  int ite = 500000;
		  while(ite >= 0 && flag){
	        try{
	        	DatagramSocket socket =new DatagramSocket(8887);
	            socket.setBroadcast(true);
	            byte[] mensajeRecibido = new byte[15000];
	            DatagramPacket paqueteRecibido=new DatagramPacket(mensajeRecibido, mensajeRecibido.length);
	            
	            socket.receive(paqueteRecibido);

	            ByteArrayInputStream bs = new ByteArrayInputStream(paqueteRecibido.getData());
	            ObjectInputStream is = new ObjectInputStream (bs);
	            Object o = is.readObject();
	            
	            if (o instanceof MessageACK){
	            	MessageACK m = (MessageACK)o;
	            	if (InetAddress.getLocalHost().getHostAddress().equals(m.getIP())){
	            		 if (ackMessages.containsKey(m.getACK())){
	            			ackMessages.put(m.getACK(), true);
	     	            	System.out.println("El mensaje lleg� al servidor.");
	     	            	flag = false;
	            		 }
	            	}
	            }
	            
	            
	            is.close();
                socket.close();     
	            
	            socket.close();	            
	        }catch (Exception e){  
	        }
	        ite--;
		  }
		  
	}
	
	public tReceiveServer( Map<String, Boolean> ack_) {
		super();
		ackMessages = ack_;

	}
	
    public void run() {
         listenConfirmation();
    }

	
}