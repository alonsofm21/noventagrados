package noventagrados.util;

public record Coordenada(int fila, int columna) {
	
	public String aTexto() {
		return  ""+ fila + columna +"";
	}
}
