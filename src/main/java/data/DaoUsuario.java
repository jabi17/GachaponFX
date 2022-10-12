package data;

import jakarta.inject.Inject;
import modelo.Personaje;
import modelo.PersonajeInventario;
import modelo.Usuario;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class DaoUsuario {
    private final Database db;

    @Inject
    public DaoUsuario(Database db) {
        this.db = db;
    }

    public List<PersonajeInventario> verInventario() {
        List<Usuario> usuarios = db.loadUsuarios();
        List<Personaje> inventario = usuarios.get(0).getInventario();
        List<Personaje> personajes = db.loadPersonajes();
        List<PersonajeInventario> inventarioPersonajes = new ArrayList<>();
        int contador = 0;
        for (int i = 0; i < personajes.size(); i++) {
            for (int j = 0; j < inventario.size(); j++) {
                if (personajes.get(i).getNombre().equals(inventario.get(j).getNombre())) {
                    contador++;
                }
            }
            if (contador > 0) {
                inventarioPersonajes.add(new PersonajeInventario(personajes.get(i), contador));
                contador = 0;
            }
        }
        return inventarioPersonajes;
    }

    public int getValorInventario() {
        List<Usuario> usuarios = db.loadUsuarios();
        List<Personaje> inventario = usuarios.stream().findFirst().map(Usuario::getInventario).orElse(new ArrayList<>());
        AtomicInteger monedas = new AtomicInteger();
        inventario.stream().filter(personaje -> personaje.getRareza() == 4).forEach(personaje -> monedas.addAndGet(personaje.getPrecio()));
        inventario.stream().filter(personaje -> personaje.getRareza() == 5).forEach(personaje -> monedas.addAndGet(10000));

        return monedas.get();
    }
}
