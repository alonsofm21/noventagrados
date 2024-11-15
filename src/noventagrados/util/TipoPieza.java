package noventagrados.util;

public enum TipoPieza {
	
	PEON('P'),
	REINA('R');
	
	private final char caracter;
	
	TipoPieza(char caracter){
		this.caracter = caracter;
	}
	public char toChar() {
		return caracter;
	}
}
