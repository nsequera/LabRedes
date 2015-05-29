
public class StartServer {
	
	private tReceiveClient receiveClients;
	
	public StartServer()  {
    	
    	try {
    		//thread receive new Clients
			receiveClients = new tReceiveClient();
	    	new Thread(receiveClients).start();
	
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
    	    	
    }
	
	public static void main(String[] args) {
        new StartServer();
    }
}