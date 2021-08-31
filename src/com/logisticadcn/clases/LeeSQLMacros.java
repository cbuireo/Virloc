package com.logisticadcn.clases;

import java.sql.*;

public class LeeSQLMacros {
//Atributos de la clase
	private String macro="",ip="",nromensajecontrol="",sessionid="",id="",fecha="";
	
	int nromensaje=32768;
//Metodos Constructores
	public LeeSQLMacros(String macro, String ip, String nromensajecontrol, String sessionid, String id, String fecha,
			int nromensaje) {
		
		this.macro = macro;
		this.ip = ip;
		this.nromensajecontrol = nromensajecontrol;
		this.sessionid = sessionid;
		this.id = id;
		this.fecha = fecha;
		this.nromensaje = nromensaje;
	}
	
	public LeeSQLMacros() {
		
	}
	//Metodos
	public void buscaYenvia()  {
		
		// Armo constructor para grabar txt
		EscribeTXT txt = new EscribeTXT();
		
		// Instanciamos la clase para leer el archivo de config.	
		LeeConfig config = new LeeConfig();
		
		//Creo constructor par el envío de comandos
		//Envia sale = new Envia();
		
		//String macro="",ip="",nromensajecontrol="",sessionid="",id="",fecha="";
		
		int nromensaje=32768;
		sessionid ="null";
		//Constructor para trabajar con la fecha
		FechaHora fh = new FechaHora();
		
		//String de conexión
		String connectionUrl = "jdbc:sqlserver://"+config.getBase()+":1433;database=dorsac;user="+config.getUsuarioSql()+";password="+config.getContraseniaSql()+";";
				// + "encrypt=true;"
				//"trustServerCertificate=false;"
				//"loginTimeout=30;";
		ResultSet resultSet = null;
		
		
		try (Connection connection = DriverManager.getConnection(connectionUrl);
				Statement statement = connection.createStatement();) {

			// Consulta a hacer.
			String selectnromensajeSql = "select right(nromensajecontrol,4) from DSCS.dbo.Macros where id = (select max(id) from DSCS.dbo.Macros)";
			resultSet = statement.executeQuery(selectnromensajeSql);
		
			// imprime en consola el resultado
			while (resultSet.next()) {
				
				nromensaje = Integer.parseInt(resultSet.getString(1),16)+1;
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
		
		try (Connection connection1 = DriverManager.getConnection(connectionUrl);
				Statement statement1 = connection1.createStatement();) {

			// Consulta a hacer.
			String selectSqlmacro = "SELECT left(replace(replace(TAIPComando,CHAR(13),''),CHAR(10),''),len(replace(replace(TAIPComando,CHAR(13),''),CHAR(10),''))-1), EquipoId,pepe.ip,sessionid FROM DSCS.dbo.Trafico t inner join (select equipo,ip from dscs.dbo.equiposModulos where puertomodulo = "+config.getPuertoEntrada()+" and DATEDIFF(MINUTE,horareporte,getdate()) <=10) as pepe on pepe.equipo = t.EquipoId and traestado = 0 and t.traEstado not in (3,4) and tipopedido not in (3)";
			resultSet = statement1.executeQuery(selectSqlmacro);

			// Trabajo con los datos de la consulta y enviar así los comandos
			while (resultSet.next()) {
																
										ip= resultSet.getString(3);
										
										macro= resultSet.getString(1)+";"+nromensajecontrol+";ID="+resultSet.getString(2)+";*";
										
										//Constructor para armar la calcular el checksum a enviar
										Checksum chk = new Checksum(macro);
										
										//metemos el resultdo del checksum al final del mensaje
										macro+=chk.getChksum()+"<";
										
										System.out.println(macro+" "+ip);
										
										//sale.envia(macro, ip, 4999);
										
										nromensajecontrol = "#"+Integer.toHexString(nromensaje);
										
										id=resultSet.getString(2);
										
										ip=resultSet.getString(3);
										
										sessionid= resultSet.getString(4);
										
										fecha=fh.ahora();
										
										String insertSql= "insert into dscs.dbo.Macros (fechaenvio, equipoid, nromensajecontrol, sessionid,macro,procesado) values ('" + fh.ahora() + "','" + id + "','" + nromensajecontrol + "','" + sessionid + "','"+macro +"','0'); ";
										/*
										try (
												Connection connection2 = DriverManager.getConnection(connectionUrl);
													PreparedStatement Insert = connection2.prepareStatement(insertSql,
															Statement.RETURN_GENERATED_KEYS);){Insert.execute();}
										catch(SQLException e) {
											txt.setMensaje(e.getMessage()+" error inserta macros");
											txt.grabaError();
										}*/
										System.out.println(insertSql);
									}
								}
								// Manejo de errores que ocurren
									catch (SQLException e) {
										txt.setMensaje(e.getMessage()+" error al buscar en la tabla tráfico");
										txt.grabaError();
									}
			
			
			if(sessionid != ""||sessionid!="null") {
								String updateSql= "update dscs.dbo.trafico set traestado = 1 where sessionid = "+ sessionid + ";";
								System.out.println("actualiza macro "+updateSql);
								
								/*
								try (Connection connection = DriverManager.getConnection(connectionUrl);
										
									PreparedStatement update = connection.prepareStatement(updateSql,
									
									Statement.RETURN_GENERATED_KEYS);){update.execute();}
							// Manejo de errores que ocurren
												catch (SQLException e) {
													txt.setMensaje(e.getMessage()+" error update tráfico sessioid = "+sessionid);
													txt.grabaError();
													
												}*/
								}
	
	
	}
}
