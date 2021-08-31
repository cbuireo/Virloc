package com.logisticadcn.clases;

public class Ack {

	private String mensaje;
	private String ack;
	
	//Metodos constructores
	public Ack(String mensaje) {
		
		this.mensaje = mensaje;
	}
	
	
	public Ack() {
		
	}

	//Metodos

	public void ackvir() {
		// TODO Auto-generated method stub
		
			
		//Instanciamos la clase para obtener el ID
		ObtieneEquipo id = new ObtieneEquipo(mensaje);
		
		String  ackvir = "";
		
		//ejecuto el metodo para obtener el id y el numero de mensaje, los capturo con los getters
		id.ObtieneSoloEquipo();
		id.nromensaje();
		
		// Armo el mensaje para calcular el checksum.
		ackvir = ">ACK;" + id.getEquipo() + ";" + id.getNroMnesaje() + ";*";
		
		// Instanciamos la clase para calcular el checksum
		Checksum chk = new Checksum(ackvir);
		//calculamos el Checksum
		chk.chkvl();
		
		// arma el mensaje para envair el ACK.
		ackvir += chk.getChksum()+"<"+(char) 0x0D+(char) 0x0A;
		
		this.ack= ackvir;
	}
	//gets y Sets


	public String getAck() {
		return ack;
	}
	
	
	
}
