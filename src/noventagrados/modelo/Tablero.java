package noventagrados.modelo;
import java.util.Arrays;
import noventagrados.util.Coordenada;

public class Tablero {
    public static final int FILAS = 7;
    public static final int COLUMNAS = 7;
    private final Celda[][] matriz;

    public Tablero() {
        this.matriz = new Celda[FILAS][COLUMNAS];
        for (int fila = 0; fila < FILAS; fila++) {
            for (int columna = 0; columna < COLUMNAS; columna++) {
                Coordenada coordenada = new Coordenada(fila, columna);
                this.matriz[fila][columna] = new Celda(coordenada);
            }
        }
    }

    public String aTexto() {
        StringBuilder sb = new StringBuilder();
        for (int fila = 0; fila < FILAS; fila++) {
            sb.append(fila);
            for (int columna = 0; columna < COLUMNAS; columna++) {
                Celda celda = matriz[fila][columna];
                if (celda.estaVacia()) {
                    sb.append(" -- ");
                } else {
                    sb.append(" ").append(celda.consultarPieza().aTexto()).append(" ");
                }
            }
            sb.append("\n");
        }
        for (int columna = 0; columna < COLUMNAS; columna++) {
            sb.append("  ").append(columna).append(" ");
        }
        return sb.toString();
    }

    public Tablero clonar() {
        Tablero clon = new Tablero();
        for (int fila = 0; fila < FILAS; fila++) {
            for (int columna = 0; columna < COLUMNAS; columna++) {
                Celda celda = this.matriz[fila][columna];
                if (!celda.estaVacia()) {
                    clon.colocar(celda.consultarPieza().clonar(), new Coordenada(fila, columna));
                }
            }
        }
        return clon;
    }

    public void colocar(Pieza pieza, Coordenada coordenada) {
        if (coordenada != null && pieza != null) {
            int fila = coordenada.fila();
            int columna = coordenada.columna();
            if (fila >= 0 && fila < FILAS && columna >= 0 && columna < COLUMNAS) {
                matriz[fila][columna].colocar(pieza);
            }
        }
    }

    public Celda consultarCelda(Coordenada coordenada) {
        int fila = coordenada.fila();
        int columna = coordenada.columna();
        if (fila >= 0 && fila < FILAS && columna >= 0 && columna < COLUMNAS) {
            return matriz[fila][columna].clonar();
        }
        return null;
    }

    public int consultarNumeroColumnas() {
        return COLUMNAS;
    }

    public int consultarNumeroFilas() {
        return FILAS;
    }

    public Celda[] consultarCeldas() {
        Celda[] celdas = new Celda[COLUMNAS * FILAS];
        int index = 0;
        for (int fila = 0; fila < FILAS; fila++) {
            for (int columna = 0; columna < COLUMNAS; columna++) {
                celdas[index++] = matriz[fila][columna].clonar();
            }
        }
        return celdas;
    }

    public void eliminarPieza(Coordenada coordenada) {
        if (coordenada != null) {
            int fila = coordenada.fila();
            int columna = coordenada.columna();
            if (fila >= 0 && fila < FILAS && columna >= 0 && columna < COLUMNAS) {
                matriz[fila][columna].eliminarPieza();
            }
        }
    }

    public Celda obtenerCelda(Coordenada coordenada) {
        int fila = coordenada.fila();
        int columna = coordenada.columna();
        if (fila >= 0 && fila < FILAS && columna >= 0 && columna < COLUMNAS) {
            return matriz[fila][columna];
        }
        return null;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + Arrays.deepHashCode(matriz);
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Tablero other = (Tablero) obj;
        return Arrays.deepEquals(this.matriz, other.matriz);
    }

    @Override
    public String toString() {
        return "Tablero [matriz=" + Arrays.deepToString(matriz) + "]";
    }
}