package noventagrados.modelo;
import java.util.Objects;

import noventagrados.util.Color;
import noventagrados.util.TipoPieza; 
public class Pieza {
	private TipoPieza tipo;
	private Color color;
	
	public Pieza(TipoPieza tipo, Color color) {
		this.tipo = tipo;
		this.color = color;
	}
	public String aTexto() {
		return tipo.toString().charAt(0) + "" + color.toString().charAt(0);
	}
	public Pieza clonar() {
		return new Pieza(this.tipo, this.color);
	}
	public TipoPieza consultarTipoPieza() {
		return this.tipo;
	}
	public Color consultarColor() {
		return this.color;
	}
	@Override
	public int hashCode() {
		return Objects.hash(color, tipo);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Pieza other = (Pieza) obj;
		return color == other.color && tipo == other.tipo;
	}
	@Override
	public String toString() {
		return "Pieza [tipo=" + tipo + ", color=" + color + "]";
	}
}
