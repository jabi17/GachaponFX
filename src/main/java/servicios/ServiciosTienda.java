package servicios;

import data.DaoTienda;
import jakarta.inject.Inject;
import modelo.Personaje;

import java.util.List;

public class ServiciosTienda {

    private final DaoTienda daoTienda;

    @Inject
    public ServiciosTienda(DaoTienda daoTienda) {
        this.daoTienda = daoTienda;
    }

    public List<Personaje> getPersonajes() {
        return daoTienda.getPersonajes();
    }

    public List<Personaje> generarTienda() {
        return daoTienda.generarTienda();
    }
}
