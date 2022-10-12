package data;

import jakarta.inject.Inject;
import modelo.Personaje;
import modelo.Usuario;

import java.util.ArrayList;
import java.util.List;

public class DaoTienda {

    private final Database database;

    @Inject
    public DaoTienda(Database database) {
        this.database = database;
    }

    public List<Personaje> getPersonajes() {
        return database.loadPersonajes();
    }

    public List<Personaje> generarTienda() {
        List<Personaje> personajes = database.loadPersonajes();
        List<Personaje> tienda = new ArrayList<>();
        List<Usuario> usuarios = database.loadUsuarios();
        for (int i = 0; i < 6; i++) {
            Personaje p = shufflePersonajes(personajes);
            if (!tienda.contains(p)) {
                tienda.add(p);
            } else {
                i--;
            }
        }
        usuarios.get(0).setTienda(tienda);
        database.saveUsuarios(usuarios);
        return tienda;
    }

    private Personaje shufflePersonajes(List<Personaje> personajes) {
        personajes.forEach(personaje -> {
            int randomIndex = (int) (Math.random() * personajes.size());
            Personaje temp = personajes.get(randomIndex);
            personajes.set(randomIndex, personaje);
            personajes.set(personajes.indexOf(personaje), temp);
        });
        return personajes.stream().filter(personaje -> personaje.getRareza() == 4).findFirst().get();
    }
}