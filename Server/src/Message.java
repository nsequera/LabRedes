import java.io.Serializable;


public class Message implements Serializable{
	private String idEstacion;
	private String idMensaje;
	private static int id;
	private int saltos;
	Sensor []sensor;
	private int bateria;
	
	Message (String idEstacion_){
		idEstacion = idEstacion_;
		idMensaje = idEstacion_ + ":"+ id;
		id++;
		saltos = 100;
		bateria = 100;
		sensor = new Sensor [8];
	}

	public String getIP (){
		return idEstacion;
	}
	
	public String getID (){
		return idMensaje;
	}
	
	protected class Sensor{
		private int id;
		String message;
		
		Sensor (){
			id = 0;
			message = "hola";
		}
		
	}
	
	public String toString (){
		String s = null;
		return s;
		
	}
}
