import java.io.Serializable;


public class MessageACK implements Serializable {
	private String idEstacion;
	private String idMensajeACK;
	private String idMensaje;
	private static int id = 0;
	
	
	MessageACK (String idDestino_,String idMensajeACK_){
		idEstacion = idDestino_;
		idMensajeACK = idMensajeACK_;
		idMensaje = idMensajeACK_ +":"+id;
	}

	public String getIP (){
		return idEstacion;
	}
	
	public String getID (){
		return idMensaje;
	}
	
	public String getACK (){
		return idMensajeACK;
	}
}
