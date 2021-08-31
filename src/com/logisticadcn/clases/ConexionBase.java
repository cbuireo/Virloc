package com.logisticadcn.clases;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionBase {
	static // Instanciamos la clase para leer el archivo de config.	
LeeConfig config = new LeeConfig();
	static String db = "dorsac"; // nombre de mi base de datos relacional
	static String login = "VBDB600";// root es el user por default de mySql
	static String password = "DataServer";
	static String url = "jdbc:sqlserver://"+config.getServerSql()+":1433;database=dorsac;user="+config.getUsuarioSql()+";password="+config.getContraseniaSql()+";";
			//"jdbc:mysql://localhost/" + db;

	Connection conn = null;

	public ConexionBase() {
		try {
			// Obtenemos el driver para Sql
			//Class.forName("com.microsoft.sqlserver.jdbc");
			// Obtenemos la connection a la base
			conn = DriverManager.getConnection(url, login, password);

			if (conn != null) {
				System.out.println("logramos conectar con la base correctamente");
			}

		} catch (SQLException e) {
			System.out.println(e.getMessage()+" error2");

		} //catch (ClassNotFoundException e) {

			//System.out.println(e.getMessage()+" Error 3");
		//} 
	catch (Exception e) {

			System.out.println(e.getMessage()+ "error 4");
		}
	}


	// metodo que nos retorna la conexion
	public Connection getConnection() {

		if (conn != null) {
			return conn;
		} else {
			return null;
		}

	}

	//metodo que me permite desconectar de la base de datos 
	public void desconectar() {
		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				System.out.println(e.getMessage());
				e.printStackTrace();
			}
			conn = null;
			System.out.println("Se logro cerrar correctamente la conexion a la DB " + db);
		}
	}

	
//	public static void main(String[] args) {
//		Conexion conex = new Conexion();
//	}

}
