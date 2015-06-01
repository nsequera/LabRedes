
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


public class tReceiveServer implements Runnable{

	private static Map<String, Boolean> ackMessages;
	private static Set<String> rMessage;
	public static void listenConfirmation(){
		  while(true){
	        try{
	        	DatagramSocket socket =new DatagramSocket(8889);
	            socket.setBroadcast(true);
	            byte[] mensajeRecibido = new byte[15000];
	            DatagramPacket paqueteRecibido=new DatagramPacket(mensajeRecibido, mensajeRecibido.length);
	            socket.receive(paqueteRecibido);
	            ByteArrayInputStream bs = new ByteArrayInputStream(paqueteRecibido.getData());
	            ObjectInputStream is = new ObjectInputStream (bs);
	            Object o = is.readObject();
	 	            if (o != null ){
	            	if (o instanceof MessageACK){
		            	MessageACK m = (MessageACK)o;
		            	if (InetAddress.getLocalHost().getHostAddress().equals(m.getIP())){
		            		 if (ackMessages.containsKey(m.getACK())){
		            			ackMessages.put(m.getACK(), true);
		     	            	System.out.println("El mensaje llegó al servidor. "+m.getID());
		     	            	
		            		 }
		            	}else{
		            		try {
		            			ByteArrayOutputStream bso = new ByteArrayOutputStream();
		            			ObjectOutputStream os = new ObjectOutputStream(bso);
		            			os.writeObject(m);
		            			byte[] mensajeEnviar = bso.toByteArray();      
		            			DatagramPacket paqueteEnviar= new DatagramPacket(mensajeEnviar, mensajeEnviar.length,InetAddress.getByName("192.168.173.255"),8889);
		                        if (!rMessage.contains(m.getID())){
		                        	rMessage.add(m.getID());
		                        	System.out.println("----->ReenviandoACK: "+m.getID());  
		                        }
		                        socket.send(paqueteEnviar);    
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
	            	}else if (o instanceof Message) {
		            	try {
		        			ByteArrayOutputStream bso = new ByteArrayOutputStream();
		        			ObjectOutputStream os = new ObjectOutputStream(bso);
		        			os.writeObject(o);
		        			byte[] mensajeEnviar = bso.toByteArray();
		                    
		        			DatagramPacket paqueteEnviar= new DatagramPacket(mensajeEnviar, mensajeEnviar.length,InetAddress.getByName("192.168.173.255"),8888);
		                    
		        			if (!rMessage.contains(((Message) o).getID())){
		        				rMessage.add(((Message) o).getID());
		                    	System.out.println("----->ReenviandoM: "+((Message) o).getID());
		        			}
		                    socket.send(paqueteEnviar);
		                          
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
	            }else
	            	System.out.println("NULLLL");
	            is.close(); 
	            socket.close();
		   }catch (Exception e){  
		   }
		  }  
	}
	
	public tReceiveServer( Map<String, Boolean> ack_) {
	
		super();
		ackMessages = ack_;
		rMessage = new HashSet<String>();	
		System.out.println("ini");

	}
	
    public void run() {
         listenConfirmation();
    }

	
}