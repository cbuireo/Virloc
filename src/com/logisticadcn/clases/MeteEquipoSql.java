package com.logisticadcn.clases;

import java.sql.*;

public class MeteEquipoSql {

	//Atributos de la clase
	private String equipoTabla, equipo, hora, ip;
	private int puertoE, puertoM;
	
	//Metodos constructores
	public MeteEquipoSql(String equipoTabla, String equipo, String hora, String ip, int puertoE, int puertoM) {
		
		this.equipoTabla = equipoTabla;
		this.equipo = equipo;
		this.hora = hora;
		this.ip = ip;
		this.puertoE = puertoE;
		this.puertoM = puertoM;
	}
	
	public MeteEquipoSql() {
		
	}
	//Metodos
	
	public void Mete()
			 {
			// Instanciamos la clase para leer el archivo de config.	
			LeeConfig config = new LeeConfig();
			
			// Instamnciamos la clase para grabar el archivo.
			EscribeTXT txt = new EscribeTXT();
			
			String connectionUrl = "jdbc:sqlserver://" + config.getServerSql()+ ":1433;" + "database=" + config.getBase() + ";" + "user=" + config.getUsuarioSql() + ";"
					+ "password=" + config.getContraseniaSql() + ";"
					// + "encrypt=true;"
					// + "trustServerCertificate=false;"
					+ "loginTimeout=30;";
			
			ResultSet resultSet = null;
			
			//String equipoTabla ="";
			
			String insertSql = "INSERT INTO dscs.dbo.equiposModulos(Horareporte,ip,puertoEquipo,puertoModulo,equipo) VALUES " + "('" + hora + "','" + ip + "','" + puertoE + "','" + puertoM + "','"+equipo + "');";
			
			String updateSql = "update dscs.dbo.equiposModulos set Horareporte ='"+hora+"',ip='"+ip+"',puertoEquipo='"+puertoE+"',puertoModulo='"+puertoM+"' where equipo='"+equipo+"'";
				
			try (Connection connection = DriverManager.getConnection(connectionUrl);
				Statement statement = connection.createStatement();) {

				// Consulta a hacer.
				String selectSql = "select equipo from dscs.dbo.equiposModulos where equipo = '"+equipo+"'";
				
				resultSet = statement.executeQuery(selectSql);

					// Carga el equipo a buscar o dar de alta
					while (resultSet.next()) {
						equipoTabla = resultSet.getString(1);
						
					}
			} catch (SQLException e) {
				
				txt.setMensaje(e.getMessage()+" error al buscar el equipo");
				txt.grabaError();
				//e.printStackTrace();
			}
			
				if (equipoTabla =="") {
					try (
						Connection connection = DriverManager.getConnection(connectionUrl);
							PreparedStatement Insert = connection.prepareStatement(insertSql,
									Statement.RETURN_GENERATED_KEYS);){Insert.execute();} catch (SQLException e) {
										
										txt.setMensaje(e.getMessage()+" error al buscar el equipo");
										txt.grabaError();
										//e.printStackTrace();
									}
						} else {
							try (
							Connection connection = DriverManager.getConnection(connectionUrl);
							
							PreparedStatement update = connection.prepareStatement(updateSql,
									Statement.RETURN_GENERATED_KEYS);){update.execute();} catch (SQLException e) {
										
										txt.setMensaje(e.getMessage()+" error al hacer el update del equipo");
										txt.grabaError();
										//e.printStackTrace();
									}
				}
		
			
	}
}
