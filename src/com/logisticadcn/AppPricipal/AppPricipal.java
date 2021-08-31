package com.logisticadcn.AppPricipal;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
//import java.sql.SQLException;
import java.sql.*;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;

import com.logisticadcn.clases.Ack;
import com.logisticadcn.clases.Checksum;
import com.logisticadcn.clases.ConexionBase;
//import com.logisticadcn.clases.Checksum;
import com.logisticadcn.clases.EnviaUDP;
import com.logisticadcn.clases.EscribeTXT;
import com.logisticadcn.clases.FechaHora;
import com.logisticadcn.clases.LeeConfig;
import com.logisticadcn.clases.MeteEquipoSql;
import com.logisticadcn.clases.ObtieneEquipo;
import com.logisticadcn.clases.ParseYmete;

public class AppPricipal {

	public static void main(String[] args) {
		
		int cantidadEntrante =0;

		// Instanciamos la clase para leer el archivo de config.	
		LeeConfig config = new LeeConfig();
		
		//INSTANCIAMOS LA CLASE FECHA
		FechaHora fecha = new FechaHora();
		
		//Instanciamos la clase que graba el TXT
		EscribeTXT txt = new EscribeTXT();
		
		// instanciamos la ventana
		miMacro macro = new miMacro();
		
		//Instanciamos la Etiqueta de mensajes recibidos
		Etiqueta eti = new Etiqueta();
		
		//Instanciamos el titulo de Etiqueta de cantidad de mensajes recibidos
		Etiqueta etiCantidad = new Etiqueta();
		
		//Instanciamos la Etiqueta de cantidad de mensajes recibidos
		Etiqueta etiCantidadMensajes = new Etiqueta();
		
		//Prendo la ventana
		macro.setVisible(true);
		
		//Título de la ventana
		macro.setTitle("Arrancado: "+fecha.ahoraHumano()+" Modo trabajo: Virloc IP Base:"+config.getBaseIp()+" Puerto:"+config.getPuertoEntrada());
		
		//Boton cerrar de la ventana cierra todo
		macro.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//Armo todas las etiquetas
		eti.setBounds(0,1, 1000, 10);
		etiCantidad.setBounds(15,11, 2000, 10);
		etiCantidadMensajes.setBounds(1,11, 100,10);
		macro.add(etiCantidadMensajes);
		macro.add(eti);
		//macro.add(grillaEntrada);
		
		
		
		// Creamos un objeto de tipo DatagrmaPacket para recibir un paquete
		DatagramPacket paquete;
		
		// Declaramos una variable de tipo string donde guardaremos el mensaje
		String mensaje = "";
		
		DatagramSocket socket;
		
		boolean fin = false;
		
		
		try {
		
			// Nos preparamos para recibir una mensaje de 1mb(esta expresado en bytes)
			byte[] mensaje_bytes = new byte[4194304];
			
			mensaje_bytes = new byte[4194304];
			
			
			// Contenedor de datagrama, el buffer será el array mensaje_bytes
			paquete = new DatagramPacket(mensaje_bytes, 4194304);
			
					
			//Loop para recibir los mensajes
			do {
				System.out.println("vuelta");
				
				// Abrimos un socket en el puerto establecido en config.ini

				socket = new DatagramSocket(config.getPuertoEntrada(),InetAddress.getByName(config.getBaseIp()));
				
				mensaje_bytes = new byte[4194304];

				// Creamos un contenedor de datagrama, el buffer será el array mensaje_bytes
				paquete = new DatagramPacket(mensaje_bytes, 4194304);

				// Esperamos a recibir un paquete
				socket.receive(paquete);

				// Convertimos el mensaje recibido en un string
				mensaje = new String(mensaje_bytes).trim();
				
				// capturo la ip del paquete
				InetAddress address = paquete.getAddress();
				
				String ip = address.toString();
				
				// capturo el Nº de puerto escucha remoto
				int puertoE = paquete.getPort();
				
				// Cierre del puerto para liberar memoria.
				socket.close();
				
				// Guardamos en log TXT si está seteado en el config
				txt.setMensaje(mensaje);
				txt.graba();
				
				//Instanciamos la clase para armar el ack
				Ack ack = new Ack(mensaje);
				
				//Arma el ACK
				ack.ackvir();
				
				//instanciamos la clase que envía el ack del mensaje que llegó
				EnviaUDP envia = new EnviaUDP(ack.getAck(),ip.substring(1),puertoE);
				
				//Envia el ACK
				envia.envia();
				
				//Instanciamos la clase que obtiene el equipo
				ObtieneEquipo equipo = new ObtieneEquipo(mensaje);
				
				//instanciamos la clase que mete el Equipo en el SQL
				MeteEquipoSql entra = new MeteEquipoSql("",equipo.ObtieneSoloEquipo(),fecha.ahora(),ip.substring(1),puertoE,config.getPuertoEntrada() );
				
				//Inserta en la base de datos
				entra.Mete();
				
				//instancamos la clase que mete los reportes en la BD
				ParseYmete parse = new ParseYmete(mensaje,fecha.ahora());
				
				//Mete en la BD
				parse.parseymete();
				
				//Cargo contador de paquetes recibidos
				cantidadEntrante++;
				
				//Actualizo etiqueta de cantidad de mensajes recibidos
				etiCantidadMensajes.setText("Recibidos: "+Integer.toString(cantidadEntrante));
				
				//Muestro en la ventana el mensaje recibido
				eti.setText(mensaje);
							
				//Macros

				String macroE="",ipM="",nromensajecontrol="",sessionid="",idM="",fechaM="",comando="",tipopedido="", nromensajecontrolprograma="",nromensajesaliente="";
				
				int nromensaje=32768, puertoEquipo;
				 sessionid ="null";
				//Constructor para trabajar con la fecha
				FechaHora fh = new FechaHora();
				
				// Consulta a hacer.
				String selectnromensajeSql = "select right(nromensajecontrol,4) from DSCS.dbo.Macros where id = (select max(id) from DSCS.dbo.Macros)";
				
				//preparo para meter en la base de datos el mensaje parseado
				ConexionBase bd = new ConexionBase();
				
				//Devolución de la consulta
				ResultSet nroMnesaje = null;
				
				
				try{
					PreparedStatement selNromensaje = bd.getConnection().prepareStatement(selectnromensajeSql);
					
					nroMnesaje = selNromensaje.executeQuery(selectnromensajeSql);
					
				// voy manejando lo que devuelve e SQL
				while (nroMnesaje.next()) {
					
					nromensaje = Integer.parseInt(nroMnesaje.getString(1),16)+1;
					if(nromensaje == 36864) {
						nromensaje=32768;
					}
					nromensajecontrol= Integer.toHexString(nromensaje);
					for (int i = 0; i<6;i++) {
						switch (i) {
						case 0:
							nromensajecontrol=nromensajecontrol.replace("a", "A");
							break;
						case 1:
							nromensajecontrol=nromensajecontrol.replace("b", "B");
							break;
						case 2:
							nromensajecontrol=nromensajecontrol.replace("c", "C");
							break;
						case 3:
							nromensajecontrol=nromensajecontrol.replace("d", "D");
							break;
						case 4:
							nromensajecontrol=nromensajecontrol.replace("e", "E");
							break;
						case 5:
							nromensajecontrol=nromensajecontrol.replace("f", "F");
							break;
							
						}
						
					}
					nromensajecontrol="#"+nromensajecontrol;
				}
			}
				
			// Handle any errors that may have occurred.
			catch (SQLException e) {
				txt.setMensaje(e.getMessage()+" error cuando trae nromensaje de macros");
				txt.grabaError();
			}
							
				try {
					PreparedStatement selectSqlmacro = bd.getConnection().prepareStatement("SELECT left(replace(replace(TAIPComando,CHAR(13),''),CHAR(10),''),len(replace(replace(TAIPComando,CHAR(13),''),CHAR(10),''))-1), EquipoId,equipos.ip,equipos.puertoEquipo,sessionid,tipopedido,trapaquete FROM DSCS.dbo.Trafico t inner join (select equipo,ip,puertoEquipo from dscs.dbo.equiposModulos where puertomodulo = ? and DATEDIFF(MINUTE,horareporte,getdate()) <=10) as equipos on equipos.equipo = t.EquipoId and traestado = 0 and t.traEstado not in (3,4) and tipopedido not in (3);");
					selectSqlmacro.setInt(1, config.getPuertoEntrada());
					ResultSet res2 = selectSqlmacro.executeQuery();
					
					//String selectSqlmacro = "SELECT left(replace(replace(TAIPComando,CHAR(13),''),CHAR(10),''),len(replace(replace(TAIPComando,CHAR(13),''),CHAR(10),''))-1), EquipoId,equipos.ip,equipos.puertoEquipo,sessionid, tipopedido FROM DSCS.dbo.Trafico t inner join (select equipo,ip,puertoEquipo from dscs.dbo.equiposModulos where puertomodulo = " + puertoModulo +" and DATEDIFF(MINUTE,horareporte,getdate()) <=10) as equipos on equipos.equipo = t.EquipoId and traestado = 0 and t.traEstado not in (3,4) and tipopedido not in (3);";
					//resultSet = statement1.executeQuery(selectSqlmacro);

					// Trabajo con los datos de la consulta y enviar así los comandos
					while (res2.next()) {
																		
												tipopedido = res2.getString(6);
												
												ipM= res2.getString(3);
												
												puertoEquipo =  Integer.parseInt(res2.getString(4));
											
												idM=res2.getString(2);
												
												ipM=res2.getString(3);
												
												sessionid= res2.getString(5);
												
												fechaM=fh.ahora();
												
												if(Integer.parseInt(tipopedido)==10) {
													
													nromensajesaliente = res2.getString(7);
													
													nromensajesaliente = "#"+nromensajesaliente;
													
												} else {
													nromensajesaliente = nromensajecontrol;
												}
												macroE= res2.getString(1)+";"+nromensajesaliente+";ID="+res2.getString(2)+";*";
												
												// Instanciamos la clase para calcular el checksum
												Checksum chkMacro = new Checksum(macroE);
												
												//calculamos el Checksum
												chkMacro.chkvl();
																				
												macroE+=chkMacro.getChksum()+"<";
													
												if(Integer.parseInt(tipopedido)==10) {
													//instanciamos la clase que envía el comando de programación
													EnviaUDP enviaPrograma = new EnviaUDP(macroE, ipM, puertoEquipo);
													
													//Envia programa
													enviaPrograma.envia();
												
													System.out.println(macroE+" programa");
													
													String insertSqlPrograma= "insert into dscs.dbo.programa (fechaenvio, equipoid, nromensajecontrol, sessionid,comando,procesado) values ('" + fh.ahora() + "','" + idM + "','" + nromensajesaliente + "','" + sessionid + "','"+macroE +"','0'); ";
													
													try {
														PreparedStatement insert = bd.getConnection().prepareStatement(insertSqlPrograma);
											
														insert.executeUpdate();
														
													}	
												// Handle any errors that may have occurred.
												catch (SQLException e) {
													txt.setMensaje(e.getMessage()+" error inserta Progarma");
													txt.grabaError();
													
												}
												
												}else {
													
													//instanciamos la clase que envía la macro
													EnviaUDP enviaMacro = new EnviaUDP(macroE, ipM, puertoEquipo);
													
													//Envia macro
													enviaMacro.envia();
													
													
													System.out.println(macroE + " Comando");
													
													String insertSql= "insert into dscs.dbo.Macros (fechaenvio, equipoid, nromensajecontrol, sessionid,macro,procesado) values ('" + fh.ahora() + "','" + idM + "','" + nromensajesaliente + "','" + sessionid + "','"+macroE +"','0'); ";
													
												try {
													PreparedStatement insert = bd.getConnection().prepareStatement(insertSql);
												insert.executeUpdate();
												
												}	
											// Handle any errors that may have occurred.
											catch (SQLException e) {
												txt.setMensaje(e.getMessage()+" error inserta macros");
												txt.grabaError();
												
											}
									}		
								}
					
										if(sessionid != ""&&sessionid!="null") {
										//String updateSql= "update dscs.dbo.trafico set traestado = 1 where sessionid = ?;";
										System.out.println("actualiza trafico");
										//System.out.println(updateSql);
										
										try {
											String update = "update dscs.dbo.trafico set traestado = 1 where sessionid = ?;";  
											System.out.println(update+sessionid);
											PreparedStatement updateSql = bd.getConnection().prepareStatement(update);
											updateSql.setString(1, sessionid);
											
											 
											updateSql.executeUpdate();
											
											
										}	
													// Handle any errors that may have occurred.
														catch (SQLException e) {
															txt.setMensaje(e.getMessage()+" error update tráfico sessioid = "+sessionid);
															txt.grabaError();
															}
										}
					
					
			
										
										
						res2.close();
					//conex.desconectar();
				}	
							// Handle any errors that may have occurred.
								catch (SQLException e) {
									txt.setMensaje(e.getMessage()+" Error al intentar enviar una macro");
									txt.grabaError();

								}
				
				bd.desconectar();
				
			} while (!fin);
		} catch  (Exception e) {
			txt.setMensaje(e.getMessage()+ "error al recibir, puerto ocupado");
			txt.grabaError();
			
		}
		
		
		
	}
	
	//Armo la ventana
		 static class miMacro extends JFrame {
			  public miMacro() {
				  Toolkit miPantalla = Toolkit.getDefaultToolkit();
				  Dimension tamanoPantalla = miPantalla.getScreenSize();
				  int alturaPantalla=tamanoPantalla.height;
				  int anchoPantalla=tamanoPantalla.width;
				  setSize (800,100);
				  setLocation(anchoPantalla/4,alturaPantalla/4);
				  Image icono = miPantalla.getImage("iot.png");
				  setIconImage(icono);
				  
			  }
		 }
		 
		 static class Lamina extends JPanel {
			  public void paintComponent(Graphics g) {
				  super.paintComponent(g);
							  
			  }
		 }
		 
		 static class Etiqueta extends JLabel {
			  public void etiqueta() {
				  JLabel et = new JLabel();
				 
				   }
		 }
		 

		 static class Grilla extends JTable {
			  public void grilla() {
				  JTable et = new JTable();
				  et.setBounds(1, 1, 10, 10);
				  
			  }
		 }
	
}
