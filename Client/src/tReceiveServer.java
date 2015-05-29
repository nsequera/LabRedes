import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

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
	            String confirmation = new String(paqueteRecibido.getData()).trim();
	            if (ackMessages.containsKey(confirmation)){
	            	ackMessages.put(confirmation, true);
	            	System.out.println("El mensaje lleg� al servidor.");
	            	flag = false;
	            }
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