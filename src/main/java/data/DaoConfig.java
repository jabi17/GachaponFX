package data;

import jakarta.inject.Inject;
import modelo.Banner;
import modelo.Personaje;
import modelo.Usuario;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class DaoConfig {

    private final Database database;

    @Inject
    public DaoConfig(Database database) {
        this.database = database;
    }

    public boolean saveNombre(String nombre) {
        List<Usuario> usuarios = database.loadUsuarios();
        int monedas = usuarios.stream().findFirst().map(Usuario::getCantidadMonedas).orElse(0);
        usuarios.clear();
        usuarios.add(new Usuario(nombre, monedas, new ArrayList<>(), new ArrayList<>(), LocalDate.now(), LocalDate.now().minusDays(1)));
        return database.saveUsuarios(usuarios);
    }

    public String getNombre() {
        List<Usuario> usuarios = database.loadUsuarios();
        return usuarios.stream().findFirst().map(Usuario::getNombreUsuario).orElse("");
    }

    public Usuario getUsuario() {
        List<Usuario> usuarios = database.loadUsuarios();
        return usuarios.stream().findFirst().orElse(new Usuario("", 0, new ArrayList<>(), new ArrayList<>(), LocalDate.now(), LocalDate.now().minusDays(1)));
    }

    public void saveUsuario(Usuario usuario) {
        List<Usuario> usuarios = database.loadUsuarios();
        usuarios.clear();
        usuarios.add(usuario);
        database.saveUsuarios(usuarios);
    }

    public void reiniciarCuenta() {
        List<Usuario> usuarios = database.loadUsuarios();
        List<Banner> banners = database.loadBanners();
        List<Personaje> inventario = usuarios.stream().findFirst().map(Usuario::getInventario).orElse(new ArrayList<>());
        AtomicInteger monedas = new AtomicInteger();
        inventario.stream().filter(personaje -> personaje.getRareza() == 4).forEach(personaje -> monedas.addAndGet(personaje.getPrecio()));
        inventario.stream().filter(personaje -> personaje.getRareza() == 5).forEach(personaje -> monedas.addAndGet(10000));
        usuarios.clear();
        usuarios.add(new Usuario("", monedas.get(), new ArrayList<>(), new ArrayList<>(), LocalDate.now(), LocalDate.now().minusDays(1)));
        banners.forEach(banner -> banner.setPity(0));
        database.saveBanners(banners);
        database.saveUsuarios(usuarios);
    }
}