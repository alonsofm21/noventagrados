package noventagrados.modelo;

public record Jugada(Celda origen, Celda destino) {
	public String aTexto() {
		String textoOrigen = origen.consultarCoordenada().aTexto();
		String textoDestino = destino.consultarCoordenada().aTexto();
		return textoOrigen + "-" + textoDestino;
	}
}
