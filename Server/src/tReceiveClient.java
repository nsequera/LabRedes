import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.HashSet;
import java.util.Set;

public class tReceiveClient implements Runnable{
	String dirServer = "192.168.173.1";    
	Set<String> mRecibidos;
	
	
	public tReceiveClient() {
		super();
		mRecibidos = new HashSet<String>();
	}

	public Message listenNewClients(){
	        Message m = null;
	        try{
	        	
	        	DatagramSocket socket =new DatagramSocket(8889);
	            socket.setBroadcast(true);
	            byte[] mensajeRecibido = new byte[15000];
	            DatagramPacket paqueteRecibido=new DatagramPacket(mensajeRecibido, mensajeRecibido.length);

	            socket.receive(paqueteRecibido);
	            ByteArrayInputStream bs = new ByteArrayInputStream(paqueteRecibido.getData());
	            ObjectInputStream is = new ObjectInputStream (bs);
	            Object o = is.readObject();
	            is.close();
                socket.close();   
	            if (o instanceof Message){
	            	m = (Message)o;
	            }else{
	            	return null;
	            }                  
	        }catch (Exception e){
	        
	        }
	    return m;
	}
	
    public void run() {
    	System.out.println("---->Escuchando clientes.");   
      while(true){
    	  Message message = listenNewClients();
    	  if(message != null && !mRecibidos.contains(message.getID())){
    		  System.out.println("--->Id Mensaje: "+message.getID());
    		  mRecibidos.add(message.getID());
    		  SendMessage sendProductsToClient = new SendMessage(message.getIP(),message.getID());	  
    		  sendProductsToClient.run();
    	  }
      }
    
    }

	
}