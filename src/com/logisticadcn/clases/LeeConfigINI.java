package com.logisticadcn.clases;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class LeeConfigINI {
	
	private int puertoEntrada;
	private int modeloEquipo;
	private String serverSql;
	private String usuarioSql;
	private String contraseniaSql;
	private String base;
	private int timerMacros;
	private int timerPrograma;
	private int timerPausaComandosProg;
	private boolean grabaErrores;
	private boolean grabaLog;
	
	
	//Métodos Constructores
	
		
	//public LeeConfig() {
		
	//}
	
	public LeeConfigINI(int puertoEntrada, int modeloEquipo, String serverSql, String usuarioSql, String contraseniaSql,
			String base, int timerMacros, int timerPrograma, int timerPausaComandosProg, boolean grabaErrores,
			boolean grabaLog) {
		
		this.puertoEntrada = puertoEntrada;
		this.modeloEquipo = modeloEquipo;
		this.serverSql = serverSql;
		this.usuarioSql = usuarioSql;
		this.contraseniaSql = contraseniaSql;
		this.base = base;
		this.timerMacros = timerMacros;
		this.timerPrograma = timerPrograma;
		this.timerPausaComandosProg = timerPausaComandosProg;
		this.grabaErrores = grabaErrores;
		this.grabaLog = grabaLog;
	}

	
	public  LeeConfigINI() {
		FileReader f = null;
		try {
			f = new FileReader("config.ini");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		BufferedReader b = new BufferedReader(f);
		String cadena;
		try {
			while ((cadena = b.readLine()) != null) {
				// System.out.println(cadena);
				if (cadena.contains("Puerto Entrada:")) {
					this.puertoEntrada = Integer.valueOf(cadena.replaceAll(" ", "").substring(14, 18));
				} else if (cadena.contains("Modelo Equipo:")) {
					this.modeloEquipo = Integer.valueOf(cadena.replaceAll(" ", "").substring(13, 14));
				} else if (cadena.contains("Servidor SQL")) {
					this.serverSql = cadena.replaceAll(" ", "").substring(12);
				} else if (cadena.contains("Usuario:")) {
					this.usuarioSql = cadena.replaceAll(" ", "").substring(8);
				} else if (cadena.contains("Contrase")) {
					this.contraseniaSql = cadena.replaceAll(" ", "").substring(12);
				} else if (cadena.contains("Base:")) {
					this.base = cadena.replaceAll(" ", "").substring(5);
				} else if (cadena.contains("Timer macros:")) {
					this.timerMacros =Integer.valueOf(cadena.replaceAll(" ", "").substring(12));
				} else if (cadena.contains("Timer Prog:")) {
					this.timerPrograma =Integer.valueOf(cadena.replaceAll(" ", "").substring(10));
				} else if (cadena.contains("Pausa entre comandos Prog:")) {
					this.timerPausaComandosProg =Integer.valueOf(cadena.replaceAll(" ", "").substring(23));
				} else if (cadena.contains("Graba errores:")) {
					this.grabaErrores = (Integer.valueOf(cadena.replaceAll(" ", "").substring(13))==1)?true:false;
				} else if (cadena.contains("Graba log:")) {
					this.grabaLog =(Integer.valueOf(cadena.replaceAll(" ", "").substring(9))==1)?true:false;
				}
				
							}
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			b.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	// gets & sets
	public int getPuertoEntrada() {
		return puertoEntrada;
	}

	public int getModeloEquipo() {
		return modeloEquipo;
	}


	public String getServerSql() {
		return serverSql;
	}


	public String getUsuarioSql() {
		return usuarioSql;
	}


	public String getContraseniaSql() {
		return contraseniaSql;
	}


	public String getBase() {
		return base;
	}


	public int getTimerMacros() {
		return timerMacros;
	}


	public int getTimerPrograma() {
		return timerPrograma;
	}


	public int getTimerPausaComandosProg() {
		return timerPausaComandosProg;
	}


	public boolean isGrabaErrores() {
		return grabaErrores;
	}


	public boolean isGrabaLog() {
		return grabaLog;
	}


	
}
