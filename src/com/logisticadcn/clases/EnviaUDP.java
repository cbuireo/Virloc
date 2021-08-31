package com.logisticadcn.clases;

import java.net.*;

public class EnviaUDP {
	
	//Atributos de la clase
	private String mensaje;
	private String ip;
	private int puerto;
	
	//	Metodos Construcotres 
	
	public EnviaUDP(String mensaje, String ip, int puerto) {
		super();
		this.mensaje = mensaje;
		this.ip = ip;
		this.puerto = puerto;
	}
	
	public EnviaUDP() {
		
	}
	
	//Metodos
	
	public void envia() {
				
				// Instanciamos la clase Config para leer el puerto base
				LeeConfig config = new LeeConfig();
				
				// Definimos el sockets, número de bytes del buffer, y mensaje.
				DatagramSocket socketE;
				
				InetAddress address;
				byte[] mensaje_bytes = new byte[256];
				
				mensaje_bytes = mensaje.getBytes();

				// Paquete
				DatagramPacket paquete;


				try {
					socketE = new DatagramSocket(config.getPuertoEntrada());

					address = InetAddress.getByName(ip);
					
					mensaje_bytes = mensaje.getBytes();
					
					paquete = new DatagramPacket(mensaje_bytes, mensaje.length(), address, puerto);
					
					socketE.send(paquete);
					
					socketE.close();
					
				} catch (Exception e) {
					
					//txt.grabaError(e.getMessage() + "error al enviar por UDP " + mensaje);
					System.out.println("ip "+ip+" mensaje "+mensaje);
					System.out.println("Puerto ocupado");
					System.exit(1);
				}
			
		
		
	}
	
	//Gets y Sets
	
	
	

	
}
