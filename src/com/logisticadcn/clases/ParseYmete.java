package com.logisticadcn.clases;

import java.sql.*;

public class ParseYmete {

	private String mensaje;
	private String horaReporte;
	
	
	
	
	//metodos constructores
	public ParseYmete(String mensaje, String horaReporte) {
		this.mensaje = mensaje;
		this.horaReporte = horaReporte;
	}

public ParseYmete() {
}



	public  void parseymete() {
		// Instanciamos la clase para leer el archivo de config.	
		//LeeConfig config = new LeeConfig();
		
		// Instamnciamos la clase para grabar el archivo.
		EscribeTXT txt = new EscribeTXT();
		
		
		//String mensaje = ">RGP220321184457-3443892-058712780000543005E5800;ID=I309;#42E3;*21<";
		String latitud="",longitud="",entradasok="",velocidad="",curso="",fechaGPS="",estadoGPS="",segundosUltimaPosicion="",entradas="",evento="",hdop="",xps="",alprincipal="",albackup="",analogica0="",analogica1="",analogica2="",id="",nromensaje="";
		//para saber a que destino tiene que meter el mensaje 1= buffer, 2 = bitacoras, 3 = dorsaclogs
		int tipoInsert=10;
			//Si es GP y el evento está por debajo del 99
		if (mensaje.length() == 67 && mensaje.contains("RGP")) {
			tipoInsert= 1;
			fechaGPS = "20"+mensaje.substring(8,(8+2))+mensaje.substring(6,(6+2))+mensaje.substring(4,(4+2))+" "+mensaje.substring(10,(10+2))+":"+mensaje.substring(12,(12+2))+":"+mensaje.substring(14,(14+2));
			latitud=Float.toString((float) (Integer.parseInt(mensaje.substring(16,(16+8))))/100000);
			longitud= Float.toString((float) (Integer.parseInt(mensaje.substring(24,(24+9))))/100000);
			velocidad = mensaje.substring(33,(33+3));
			curso = mensaje.substring(36,(36+3));
			estadoGPS = mensaje.substring(39,40);
			segundosUltimaPosicion= Integer.toString(Integer.parseInt(mensaje.substring(40,(40+2)),16));
			entradas = Integer.toBinaryString(Integer.parseInt(mensaje.substring(42,(42+2)),16));
			evento= mensaje.substring(44,(44+2));
			hdop= mensaje.substring(46,(46+2));
			id= mensaje.substring(52,(52+4));
			nromensaje= mensaje.substring(58,(58+4));
			
		} 	//Si es GP y el evento está por encima del 99
			else if(mensaje.length() == 68 && mensaje.contains("RGP")) {
			tipoInsert= 1;
			fechaGPS ="20"+mensaje.substring(8,(8+2))+mensaje.substring(6,(6+2))+mensaje.substring(4,(4+2))+" "+mensaje.substring(10,(10+2))+":"+mensaje.substring(12,(12+2))+":"+mensaje.substring(14,(14+2));
			latitud=Float.toString((float) (Integer.parseInt(mensaje.substring(16,(16+8))))/100000);
			longitud= Float.toString((float) (Integer.parseInt(mensaje.substring(24,(24+9))))/100000);
			velocidad = mensaje.substring(33,(33+3));
			curso = mensaje.substring(36,(36+3));
			estadoGPS = mensaje.substring(39,40);
			segundosUltimaPosicion= Integer.toString(Integer.parseInt(mensaje.substring(40,(40+2)),16));
			entradas = Integer.toBinaryString(Integer.parseInt(mensaje.substring(42,(42+2)),16));
			evento= mensaje.substring(44,(44+3));
			hdop= mensaje.substring(47,(47+2));
			id= mensaje.substring(53,(53+4));
			nromensaje= mensaje.substring(58,(58+4));
			
		} 	//Si es RUS 
			else if(mensaje.length() == 84 && mensaje.contains("RUS")) {
			tipoInsert= 1;
			fechaGPS = mensaje.substring(11,(11+6))+" "+mensaje.substring(17,(17+2))+":"+mensaje.substring(19,(19+2))+":"+mensaje.substring(21,(21+2));
			latitud=Float.toString((float) (Integer.parseInt(mensaje.substring(23,(23+8))))/100000);
			longitud= Float.toString((float)(Integer.parseInt(mensaje.substring(31,(31+9))))/100000);
			velocidad = mensaje.substring(40,(40+3));
			curso = mensaje.substring(43,(43+3));
			estadoGPS = mensaje.substring(46,47);
			segundosUltimaPosicion= Integer.toString(Integer.parseInt(mensaje.substring(47,(47+2)),16));
			entradas = Integer.toBinaryString(Integer.parseInt(mensaje.substring(49,(49+2)),16));
			evento= mensaje.substring(7,(7+3));
			hdop= mensaje.substring(53,(53+2));
			id= mensaje.substring(69,(69+4));
			nromensaje= mensaje.substring(75,(75+4));
		}
			//Si es TT o TF y el evento está por encima del 99
			else if (mensaje.contains("RTT") || mensaje.contains("RTF") && mensaje.length()==102 ) {
			tipoInsert= 1;
			fechaGPS ="20"+mensaje.substring(8,(8+2))+mensaje.substring(6,(6+2))+mensaje.substring(4,(4+2))+" "+mensaje.substring(10,(10+2))+":"+mensaje.substring(12,(12+2))+":"+mensaje.substring(14,(14+2));
			latitud=Float.toString((float) (Integer.parseInt(mensaje.substring(16,(16+8))))/100000);
			longitud= Float.toString((float) (Integer.parseInt(mensaje.substring(24,(24+9))))/100000);
			velocidad = mensaje.substring(33,(33+3));
			curso = mensaje.substring(36,(36+3));
			estadoGPS = mensaje.substring(39,40);
			segundosUltimaPosicion= Integer.toString(Integer.parseInt(mensaje.substring(40,(40+2)),16));
			entradas = Integer.toBinaryString(Integer.parseInt(mensaje.substring(42,(42+2)),16));
			evento= mensaje.substring(44,(44+3));
			hdop= mensaje.substring(46,(46+2));
			xps= mensaje.substring(59,(59+3));
			alprincipal=mensaje.substring(79, (79+4));
			albackup = mensaje.substring(75,(75+4));
			analogica0=mensaje.substring(63,(63+4));
			analogica1=mensaje.substring(67,(67+4));
			analogica2=mensaje.substring(71,(71+4));
			id= mensaje.substring(87,(87+4));
			nromensaje= mensaje.substring(93,(93+4));
		} 	//Si es TT o TF y el evento está por debajo del 99
			else if (mensaje.contains("RTT") || mensaje.contains("RTF") && mensaje.length()==101) {
			tipoInsert= 1;
			fechaGPS ="20"+mensaje.substring(8,(8+2))+mensaje.substring(6,(6+2))+mensaje.substring(4,(4+2))+" "+mensaje.substring(10,(10+2))+":"+mensaje.substring(12,(12+2))+":"+mensaje.substring(14,(14+2));
			latitud=Float.toString((float) (Integer.parseInt(mensaje.substring(16,(16+8))))/100000);
			longitud= Float.toString((float) (Integer.parseInt(mensaje.substring(24,(24+9))))/100000);
			velocidad = mensaje.substring(33,(33+3));
			curso = mensaje.substring(36,(36+3));
			estadoGPS = mensaje.substring(39,40);
			segundosUltimaPosicion= Integer.toString(Integer.parseInt(mensaje.substring(40,(40+2)),16));
			entradas = Integer.toBinaryString(Integer.parseInt(mensaje.substring(42,(42+2)),16));
			evento= mensaje.substring(44,(44+2));
			hdop= mensaje.substring(46,(46+2));
			xps= mensaje.substring(58,(58+3));
			alprincipal=mensaje.substring(78, (78+4));
			albackup = mensaje.substring(74,(74+4));
			analogica0=mensaje.substring(62,(62+4));
			analogica1=mensaje.substring(66,(66+4));
			analogica2=mensaje.substring(70,(70+4));
			id= mensaje.substring(86,(86+4));
			nromensaje= mensaje.substring(92,(92+4));
			System.out.println("Entro al 101");
		}  	//Si es TT o TF y el evento está por debajo del 99
			else if (mensaje.contains("RTT")){
			tipoInsert= 2;
				
		}
		System.out.println(entradas.length());
		for(int i =entradas.length()-1;i>=0;i--) {
			entradasok += entradas.charAt(i);
		}
		System.out.println(fechaGPS+" "+latitud+" "+longitud+" "+velocidad+" "+curso+" "+estadoGPS+" "+segundosUltimaPosicion+" in: "+entradas+" IN OK:"+entradasok+"EV:"+evento+" hdop"+hdop+" xp:"+xps+" alp:"+alprincipal+" alb:"+albackup+" ad0:"+analogica0+" ad1:"+analogica1+" ad2:"+analogica2+" id:"+id+" nrmensaje:"+nromensaje);
		System.out.println("largo del mensaje "+mensaje.length());
		
//		//preparo para meter en la base de datos el mensaje parseado
		ConexionBase bd = new ConexionBase();
		
	
//		
//		String connectionUrl = "jdbc:sqlserver://" +config.getServerSql() + ":1433;" + "database=DSCS;" + "user=" + config.getUsuarioSql() + ";"
//				+ "password=" + config.getContraseniaSql() + ";"
//				// + "encrypt=true;"
//				// + "trustServerCertificate=false;"
//				+ "loginTimeout=30;";
		
//		ResultSet resultSet = null;
		
//		String insertSql = "INSERT INTO BUFFER (EquipoId,horareporte,horagps,latitude,longitude,speed,course,posageflag,GPSFixSrc,EventoId,InputMask,OutputMask,taip,NroPaquete,CommType) VALUES " + "('" + id + "','" + horaReporte  + "','" + fechaGPS + "','" + latitud + "','" + longitud + "','"+ velocidad +"','"+ curso +"','"+ segundosUltimaPosicion +"','"+ estadoGPS +"','" + evento +"','"+ entradasok +"','"+ xps +"','" + mensaje+"','"+ nromensaje +"','7');";
		
		String insertSql= "";
		
		switch(tipoInsert) {
		case 1:
			insertSql = "INSERT INTO BUFFER (EquipoId,horareporte,horagps,latitude,longitude,speed,course,posageflag,GPSFixSrc,EventoId,InputMask,OutputMask,taip,NroPaquete,CommType) VALUES " + "('" + id + "','" + horaReporte  + "','" + fechaGPS + "','" + latitud + "','" + longitud + "','"+ velocidad +"','"+ curso +"','"+ segundosUltimaPosicion +"','"+ estadoGPS +"','" + evento +"','"+ entradasok +"','"+ xps +"','" + mensaje+"','"+ nromensaje +"','7');";
		case 2:
			insertSql = " insert into BitacorasDatos (BitacoraId,Data) values ( '1','"+mensaje+"')";
		case 3:
			insertSql = "INSERT INTO BUFFcdos (";
		}
		
			System.out.println(insertSql);
		
//		try (Connection connection = DriverManager.getConnection(connectionUrl);
//				PreparedStatement prepsInsertProduct = connection.prepareStatement(insertSql,
//						Statement.RETURN_GENERATED_KEYS);) 
		try{
			PreparedStatement insertaReporte = bd.getConnection().prepareStatement(insertSql);
			insertaReporte.executeUpdate();
			//prepsInsertProduct.execute();
			// Retrieve the generated key from the insert.
			//resultSet = prepsInsertProduct.getGeneratedKeys();

			// Print the ID of the inserted row.
			//while (resultSet.next()) {
			//	System.out.println("Generated: " + resultSet.getString(1));
			//}
		}
		// Handle any errors that may have occurred.
		catch (Exception e) {
			txt.setMensaje(e.getMessage()+" error al insertar en buffer");
			txt.grabaError();
			
			//e.printStackTrace();
		}
		bd.desconectar();
		// System.out.println(LocalDate.parse(LocalDate.now(),BASIC_ISO_DATE)
		// LocalDate.now()+" "+LocalTime.now());
	}

	

}	
	
	
	
