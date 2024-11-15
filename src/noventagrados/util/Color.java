package noventagrados.util;

public enum Color {
	
	BLANCO('B'),
	NEGRO('N');
	
	private final char letra;
	
	Color(char letra){
		this.letra = letra;
	}
	public Color consultarContrario() {
		return this == BLANCO ? NEGRO : BLANCO;
	}
	public char toChar() {
		return letra;
	}
}
