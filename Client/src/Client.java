import java.util.HashMap;
import java.util.Map;


public class Client {
	private static String dirServer;	
	private static Map<String, Boolean> ackMessage;
		

	public static void main(String[] args) {
		dirServer = "192.168.173.1";
		ackMessage = new HashMap<String, Boolean>();
		tSendServer tsendserver = new tSendServer(dirServer,ackMessage);
		Thread te = new Thread(new tRtxServer(8887));
		Thread te2 = new Thread(new tRtxServer(8889));
		te.start();
		te2.start();
		while (true){
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Thread t = new Thread(tsendserver);
			t.start();
			
		}

	}

}
