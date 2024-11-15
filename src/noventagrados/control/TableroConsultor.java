package noventagrados.control;

import noventagrados.modelo.Celda;
import noventagrados.modelo.Pieza;
import noventagrados.modelo.Tablero;
import noventagrados.util.Color;
import noventagrados.util.Coordenada;
import noventagrados.util.Sentido;
import noventagrados.util.TipoPieza;

public class TableroConsultor {
	
	private Tablero tablero;
	
	public TableroConsultor(Tablero tablero) {
		this.tablero = tablero;
	}
	public Sentido calcularSentido(Coordenada origen, Coordenada destino) {
		  int deltaFila = destino.fila() - origen.fila();
	        int deltaColumna = destino.columna() - origen.columna();

	        if (deltaFila != 0 && deltaColumna == 0) {
	            return deltaFila > 0 ? Sentido.VERTICAL_S : Sentido.VERTICAL_N;
	        } else if (deltaFila == 0 && deltaColumna != 0) {
	            return deltaColumna > 0 ? Sentido.HORIZONTAL_E : Sentido.HORIZONTAL_O;
	        }
	        // Puedes manejar casos adicionales o ajustar según las reglas del juego
	        return null;
	    }
	
	public int consultarDistanciaEnHorizontal(Coordenada origen, Coordenada destino) {
		return Math.abs(destino.columna() - origen.columna());
	}
	public int consultarDistanciaEnVertical(Coordenada origen, Coordenada destino) {
		return Math.abs(destino.fila() - origen.fila());
	}
	public int consultarNumeroPiezas(TipoPieza tipopieza, Color color) {
	    int count = 0;
	    for (int fila = 0; fila < tablero.consultarNumeroFilas(); fila++) {
	        for (int columna = 0; columna < tablero.consultarNumeroColumnas(); columna++) {
	            Celda celda = tablero.obtenerCelda(new Coordenada(fila, columna));
	            if (celda != null && !celda.estaVacia()) {
	                Pieza pieza = celda.consultarPieza();
	                if (pieza.consultarTipoPieza() == tipopieza && pieza.consultarColor() == color) {
	                    count++;
	                }
	            }
	        }
	    }
	    return count;
	}
	public int consultarNumeroPiezasEnHorizontal(Coordenada coordenada) {
	    int count = 0;
	    int fila = coordenada.fila(); // Obtenemos la fila de la coordenada

	    // Recorremos todas las columnas de la fila indicada
	    for (int columna = 0; columna < Tablero.COLUMNAS; columna++) {
	        Celda celda = tablero.obtenerCelda(new Coordenada(fila, columna));
	        if (celda != null && !celda.estaVacia()) { // Verificamos que la celda no esté vacía
	            count++;
	        }
	    }
	    return count; // Devolvemos el total de piezas en esa fila
	}

	public int consultarNumeroPiezasEnVertical(Coordenada coordenada) {
	    int count = 0;
	    int columna = coordenada.columna(); // Obtenemos la columna de la coordenada

	    // Recorremos todas las filas de la columna indicada
	    for (int fila = 0; fila < Tablero.FILAS; fila++) {
	        Celda celda = tablero.obtenerCelda(new Coordenada(fila, columna));
	        if (celda != null && !celda.estaVacia()) { // Verificamos que la celda no esté vacía
	            count++;
	        }
	    }
	    return count; // Devolvemos el total de piezas en esa columna
	}
	public boolean estaReinaEnElCentro(Color color) {
		  Coordenada centro = new Coordenada(tablero.consultarNumeroFilas() / 2, tablero.consultarNumeroColumnas() / 2);
	        Celda celdaCentral = tablero.obtenerCelda(centro);
	        Pieza pieza = celdaCentral.consultarPieza();
	        return pieza != null && pieza.consultarTipoPieza() == TipoPieza.REINA && pieza.consultarColor() == color;
	}
	public boolean hayReina(Color color) {
		for (int fila = 0; fila < tablero.consultarNumeroFilas(); fila++) {
            for (int columna = 0; columna < tablero.consultarNumeroColumnas(); columna++) {
                Celda celda = tablero.obtenerCelda(new Coordenada(fila, columna));
                Pieza pieza = celda.consultarPieza();
                if (pieza != null && pieza.consultarTipoPieza() == TipoPieza.REINA && pieza.consultarColor() == color) {
                    return true;
                }
            }
        }
        return false;
	}
	
	@Override
	public String toString() {
		return "TableroConsultor [tablero=" + tablero + "]";
	}
	
}
