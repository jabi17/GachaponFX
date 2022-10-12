package data;

import jakarta.inject.Inject;
import modelo.Usuario;

import java.util.List;

public class DaoFarmeo {

    private final Database database;

    @Inject
    public DaoFarmeo(Database database) {
        this.database = database;
    }

    public void addMonedas(int cantidad) {
        List<Usuario> usuarios = database.loadUsuarios();
        usuarios.get(0).setCantidadMonedas(usuarios.get(0).getCantidadMonedas() + cantidad);
        database.saveUsuarios(usuarios);
    }
}