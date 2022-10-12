package modelo;

import lombok.Data;

@Data
public class PersonajeInventario extends Personaje {
    private int cantidad;

    public PersonajeInventario(Personaje personaje, int cantidad) {
        super(personaje.getNombre(), personaje.getRareza(), personaje.getPrecio(), personaje.getId(), personaje.getIcono());
        this.cantidad = cantidad;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
}