package com.logisticadcn.clases;

public class ObtieneEquipo {

	//Atributos de la clase
	private String mensaje;
	private String equipo;
	private String nroMnesaje;
	private int soloNroMensaje;
	
	//Metodos constructores

	
	public ObtieneEquipo() {
		
	}
	
	public ObtieneEquipo(String mensaje) {
		this.mensaje = mensaje;
	}
	public ObtieneEquipo(String mensaje, String equipo, String nroMnesaje, int soloNroMensaje) {
		
		this.mensaje = mensaje;
		this.equipo = equipo;
		this.nroMnesaje = nroMnesaje;
		this.soloNroMensaje = soloNroMensaje;
	}
	//Metodos
	
	public String ObtieneSoloEquipo() {
		// TODO Auto-generated method stub
		String equipo="";
		
				
			for (int i = 0;i<mensaje.length();i++) {
				if(mensaje.charAt(i)=='=') {
					equipo= mensaje.substring((i+1),((i+1)+4));
				}
			}
		
		
		this.equipo = equipo;
		return this.equipo;
}
	
	public String ObtieneIDEquipo() {
		// TODO Auto-generated method stub
		String equipo="";
		
				
			for (int i = 0;i<mensaje.length();i++) {
				if(mensaje.charAt(i)=='=') {
					equipo= mensaje.substring((i-2),((i-2)+7));
				}
			}
		
		
		this.equipo = equipo;
		return this.equipo;
}

	
	public String nromensaje() {
		// TODO Auto-generated method stub
		// Armo el constructor para ChecksumVirloc
		
		String  nromen = "";
		for (int i = 0; i < mensaje.length(); i++) {

			 if (mensaje.charAt(i) == '#') {
				nromen = mensaje.substring(i, i + 5);
				
			}
		}
		this.nroMnesaje = nromen;
		return this.nroMnesaje;
	}
	
	public String solonromensaje() {
		
		int  nromen = 0;
		for (int i = 0; i < mensaje.length(); i++) {

			 if (mensaje.charAt(i) == '#') {
				nromen = Integer.parseInt(mensaje.substring(i+1, i + 5),16);
				
			}
		}
		this.soloNroMensaje= nromen;
		return this.nroMnesaje;
	}
	
	//gets Y Sets
	
	public String getEquipo() {
		return equipo;
	}
	public String getNroMnesaje() {
		return nroMnesaje;
	}
	public int getSoloNroMensaje() {
		return soloNroMensaje;
	}
	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	
	
	
}
