package servicios;

import data.DaoUsuario;
import jakarta.inject.Inject;
import modelo.PersonajeInventario;

import java.util.List;

public class ServiciosUsuario {

    private final DaoUsuario dao;

    @Inject
    public ServiciosUsuario(DaoUsuario dao) {
        this.dao = dao;
    }

    public List<PersonajeInventario> verInventario() {
        return dao.verInventario();
    }

    public int getValorInventario() {
        return dao.getValorInventario();
    }
}