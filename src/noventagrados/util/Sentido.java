package noventagrados.util;

public enum Sentido {
	VERTICAL_N(-1,0),
	VERTICAL_S(+1,0),
	HORIZONTAL_E(0,+1),
	HORIZONTAL_O(0,-1);
	
	private final int desplazamientoEnFilas;
	private final int desplazamientoEnColumnas;
	
	Sentido(int desplazamientoEnFilas, int desplazamientoEnColumnas){
		this.desplazamientoEnFilas = desplazamientoEnFilas;
		this.desplazamientoEnColumnas = desplazamientoEnColumnas;
	}
	public int consultarDesplazamientoEnFilas() {
		return desplazamientoEnFilas;
	}
	public int consultarDesplazamientoEnColumnas() {
		return desplazamientoEnColumnas;
	}
}
