package noventagrados.control;

import noventagrados.modelo.Celda;
import noventagrados.modelo.Jugada;
import noventagrados.modelo.Pieza;
import noventagrados.modelo.Tablero;
import noventagrados.util.Color;
import noventagrados.util.Coordenada;
import noventagrados.util.TipoPieza;

public class Arbitro {
    
    private Tablero tablero;
    private Caja cajaBlanca = new Caja(Color.BLANCO);
    private Caja cajaNegra = new Caja(Color.NEGRO);

    private Color turnoActual;
    private int contadorJugada;

    public Arbitro(Tablero tablero) {
        this.tablero = tablero;
        this.contadorJugada = 0;
        this.turnoActual = Color.BLANCO;  // Empieza siempre el jugador con blancas
    }

    public void cambiarTurno() {
        turnoActual = (turnoActual == Color.BLANCO) ? Color.NEGRO : Color.BLANCO;
    }

    public void colocarPiezas(Pieza[] piezas, Coordenada[] coordenadas, Color turnoInicial) {
        for (int i = 0; i < piezas.length; i++) {
            tablero.colocar(piezas[i], coordenadas[i]);
        }
        turnoActual = turnoInicial;
    }

    public void colocarPiezasConfiguracionInicial() {
        Pieza[] piezasIniciales = {
            new Pieza(TipoPieza.REINA, Color.BLANCO), new Pieza(TipoPieza.PEON, Color.BLANCO),
            new Pieza(TipoPieza.PEON, Color.BLANCO), new Pieza(TipoPieza.PEON, Color.BLANCO),
            new Pieza(TipoPieza.PEON, Color.BLANCO), new Pieza(TipoPieza.PEON, Color.BLANCO),
            new Pieza(TipoPieza.PEON, Color.BLANCO), new Pieza(TipoPieza.REINA, Color.NEGRO),
            new Pieza(TipoPieza.PEON, Color.NEGRO), new Pieza(TipoPieza.PEON, Color.NEGRO),
            new Pieza(TipoPieza.PEON, Color.NEGRO), new Pieza(TipoPieza.PEON, Color.NEGRO),
            new Pieza(TipoPieza.PEON, Color.NEGRO), new Pieza(TipoPieza.PEON, Color.NEGRO)
        };

        Coordenada[] posicionesIniciales = {
            new Coordenada(0, 0), new Coordenada(0, 1), new Coordenada(0, 2), new Coordenada(0, 3),
            new Coordenada(1, 0), new Coordenada(2, 0), new Coordenada(3, 0), new Coordenada(6, 6),
            new Coordenada(6, 5), new Coordenada(6, 4), new Coordenada(6, 3), new Coordenada(5, 6),
            new Coordenada(4, 6), new Coordenada(3, 6)
        };

        for (int i = 0; i < piezasIniciales.length; i++) {
            tablero.colocar(piezasIniciales[i], posicionesIniciales[i]);
        }
        turnoActual = Color.BLANCO;
    }

    public Caja consultarCaja(Color color) {
        return color == Color.BLANCO ? cajaBlanca : cajaNegra;
    }

    public int consultarNumeroJugada() {
        return contadorJugada;
    }

    public Tablero consultarTablero() {
        return tablero;  // Devolver el tablero actual en lugar de uno nuevo
    }

    public Color consultarTurno() {
        return turnoActual;
    }

    public Color consultarTurnoGanador() {
        TableroConsultor consultor = new TableroConsultor(tablero);

        // Caso 1: Verificar si una reina está en el centro
        if (consultor.estaReinaEnElCentro(Color.BLANCO)) {
            return Color.BLANCO;
        } else if (consultor.estaReinaEnElCentro(Color.NEGRO)) {
            return Color.NEGRO;
        }

        // Caso 2: Verificar si alguna reina ha sido expulsada
        boolean hayReinaBlanca = consultor.hayReina(Color.BLANCO);
        boolean hayReinaNegra = consultor.hayReina(Color.NEGRO);

        if (!hayReinaBlanca) {
            return Color.NEGRO;
        } else if (!hayReinaNegra) {
            return Color.BLANCO;
        }

        return null;
    }

    

    public void empujar(Jugada jugada) {
        Coordenada origenCoord = jugada.origen().consultarCoordenada();
        Coordenada destinoCoord = jugada.destino().consultarCoordenada();

        Celda origen = tablero.consultarCelda(origenCoord);

        System.out.println("Intentando empujar pieza desde " + origenCoord + " a " + destinoCoord);

        // Comprobar si el destino está fuera de los límites del tablero
        if (destinoCoord.fila() < 0 || destinoCoord.fila() >= tablero.consultarNumeroFilas() ||
            destinoCoord.columna() < 0 || destinoCoord.columna() >= tablero.consultarNumeroColumnas()) {
            
            // Si el destino está fuera del tablero, expulsamos la pieza de origen
            if (origen != null && !origen.estaVacia()) {
                Pieza piezaExpulsada = origen.consultarPieza();
                if (piezaExpulsada.consultarColor() == Color.BLANCO) {
                    cajaBlanca.añadir(piezaExpulsada);
                } else {
                    cajaNegra.añadir(piezaExpulsada);
                }
                origen.eliminarPieza();
                System.out.println("Pieza " + piezaExpulsada + " expulsada a la caja correspondiente.");
            }
        } else {
            // Dirección de empuje
            int dirFila = Integer.signum(destinoCoord.fila() - origenCoord.fila());
            int dirColumna = Integer.signum(destinoCoord.columna() - origenCoord.columna());

            // Empuje de la pieza dentro del tablero
            Celda actual = origen;
            while (actual != null && !actual.estaVacia()) {
                Pieza piezaMovida = actual.consultarPieza();
                actual.eliminarPieza();

                Coordenada siguienteCoord = new Coordenada(
                    actual.consultarCoordenada().fila() + dirFila,
                    actual.consultarCoordenada().columna() + dirColumna
                );

                Celda siguienteCelda = tablero.obtenerCelda(siguienteCoord);

                if (siguienteCelda == null) {  // Si la siguiente celda es nula, expulsamos la pieza actual
                    if (piezaMovida.consultarColor() == Color.BLANCO) {
                        cajaBlanca.añadir(piezaMovida);
                    } else {
                        cajaNegra.añadir(piezaMovida);
                    }
                    System.out.println("Pieza " + piezaMovida + " expulsada fuera del tablero.");
                    break;
                } else if (siguienteCelda.estaVacia()) {  // Si la siguiente celda está vacía, colocar pieza y detener el empuje
                    siguienteCelda.colocar(piezaMovida);
                    System.out.println("Pieza movida a " + siguienteCoord);
                    break;
                } else {  // Si la siguiente celda tiene una pieza, empujarla en la misma dirección
                    siguienteCelda.colocar(piezaMovida);
                    actual = siguienteCelda;
                    System.out.println("Pieza movida de " + actual.consultarCoordenada() + " a " + siguienteCoord);
                }
            }
        }

        // Incrementar el contador de jugadas después de un movimiento
        contadorJugada++;
        System.out.println("Movimiento realizado. Estado del tablero:");
        System.out.println(tablero.aTexto());

        // Verificar el contenido de las cajas después del movimiento
        System.out.println("Contenido de la caja blanca: " + cajaBlanca.consultarPiezas().length + " piezas.");
        System.out.println("Contenido de la caja negra: " + cajaNegra.consultarPiezas().length + " piezas.");
    }
    public boolean esMovimientoLegal(Jugada jugada) {
        Coordenada origenCoord = jugada.origen().consultarCoordenada();
        Coordenada destinoCoord = jugada.destino().consultarCoordenada();

        int filaOrigen = origenCoord.fila();
        int columnaOrigen = origenCoord.columna();
        int filaDestino = destinoCoord.fila();
        int columnaDestino = destinoCoord.columna();

        if (filaOrigen < 0 || filaOrigen >= 7 || columnaOrigen < 0 || columnaOrigen >= 7 ||
            filaDestino < 0 || filaDestino >= 7 || columnaDestino < 0 || columnaDestino >= 7) {
            return false;
        }

        Celda origen = tablero.consultarCelda(origenCoord);
        Celda destino = tablero.consultarCelda(destinoCoord);

        if (origen.estaVacia()) {
            return false;
        }

        Pieza piezaOrigen = origen.consultarPieza();
        if (piezaOrigen.consultarColor() != consultarTurno()) {
            return false;
        }

        if (!destino.estaVacia() && destino.consultarPieza().consultarColor() == piezaOrigen.consultarColor()) {
            return false;
        }

        int diffFila = Math.abs(filaOrigen - filaDestino);
        int diffColumna = Math.abs(columnaOrigen - columnaDestino);

        return (diffFila == 1 && diffColumna == 0) || (diffFila == 0 && diffColumna == 1);
    }

    public boolean estaFinalizadaPartida() {
        boolean reinaBlancaEnTablero = new TableroConsultor(tablero).hayReina(Color.BLANCO);
        boolean reinaNegraEnTablero = new TableroConsultor(tablero).hayReina(Color.NEGRO);

        return !(reinaBlancaEnTablero && reinaNegraEnTablero); // Finaliza si alguna reina está fuera del tablero
    }

    @Override
    public String toString() {
        return "Arbitro [tablero=" + tablero + ", cajaBlanca=" + cajaBlanca + ", cajaNegra=" + cajaNegra + ", turnoActual=" + turnoActual + ", contadorJugada=" + contadorJugada + "]";
    }
}