package noventagrados.control;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import noventagrados.modelo.Pieza;
import noventagrados.util.Color;
import noventagrados.util.TipoPieza;

public class Caja {
	private Color color;
	private List<Pieza> piezas;
	
	public Caja(Color color) {
		this.color = color;
		this.piezas = new ArrayList<>();
	}
	public void añadir(Pieza pieza) {
		if(pieza != null && pieza.consultarColor() == this.color && piezas.size() < 7) {
			piezas.add(pieza);
		}
	}
	public Caja clonar() {
		Caja clon = new Caja(this.color);
		for (Pieza pieza : this.piezas) {
			clon.añadir(pieza.clonar());
		}
		return clon;
	}
	public Color consultarColor() {
		return this.color;
	}
	public Pieza[] consultarPiezas(){
		return piezas.toArray(new Pieza[0]);
	}
	public int contarPiezas() {
		return piezas.size();
	}
	public int contarPiezas(TipoPieza tipopieza) {
		int contador = 0;
		for (Pieza pieza : piezas) {
			if (pieza.consultarTipoPieza() == tipopieza) {
				contador++;
			}
		}
		return contador;
	}
	@Override
	public int hashCode() {
		return Objects.hash(color, piezas);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Caja other = (Caja) obj;
		return color == other.color && Objects.equals(piezas, other.piezas);
	}
	@Override
	public String toString() {
		return "Caja [color=" + color + ", piezas=" + piezas + "]";
	}

}
