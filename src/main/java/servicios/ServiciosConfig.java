package servicios;

import data.DaoConfig;
import jakarta.inject.Inject;
import modelo.Usuario;

public class ServiciosConfig {

    private final DaoConfig dao;

    @Inject
    public ServiciosConfig(DaoConfig dao) {
        this.dao = dao;
    }

    public boolean saveNombre(String nombre) {
        return dao.saveNombre(nombre);
    }

    public String getNombre() {
        return dao.getNombre();
    }

    public Usuario getUsuario() {
        return dao.getUsuario();
    }

    public void saveUsuario(Usuario usuario) {
        dao.saveUsuario(usuario);
    }

    public void reiniciarCuenta() {
        dao.reiniciarCuenta();
    }
}