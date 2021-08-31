package com.logisticadcn.clases;

public class Checksum {

	//Atributos de la clase
	private String chksum;
	private String mensaje;
	
	//Metodos constructores
	public Checksum(String chksum, String mensaje) {
		super();
		this.chksum = chksum;
		this.mensaje = mensaje;
	}
	
	public Checksum(String mensaje) {
		this.mensaje = mensaje;
	}
	
	//Metodos
	public void chkvl() {
		
		String chksum;
		int aux = 0, ascii = 0;
		for (int i = 0; i < mensaje.length(); i++) {
			if (mensaje.charAt(i) == '*') {
				break;
			} else {
				ascii = mensaje.charAt(i);
				aux ^= ascii;
			}
		}
		chksum = Integer.toHexString(aux);
		for (int i = 0; i<6;i++) {
			switch (i) {
			case 0:
				chksum=chksum.replace("a", "A");
				break;
			case 1:
				chksum=chksum.replace("b", "B");
				break;
			case 2:
				chksum=chksum.replace("c", "C");
				break;
			case 3:
				chksum=chksum.replace("d", "D");
				break;
			case 4:
				chksum=chksum.replace("e", "E");
				break;
			case 5:
				chksum=chksum.replace("f", "F");
				break;
				
			}
		}
		
		this.chksum= chksum;
	
		}
		
	
	
	
	
	//Gets y Sets
	public String getChksum() {
		return chksum;
	}

	public void setChksum(String chksum) {
		this.chksum = chksum;
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}
	
	
}
