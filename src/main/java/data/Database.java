package data;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import config.Configuracion;
import jakarta.inject.Inject;
import lombok.extern.log4j.Log4j2;
import modelo.Banner;
import modelo.Personaje;
import modelo.Usuario;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

@Log4j2
public class Database {

    private final Gson gson;
    private final Configuracion configuracion;

    @Inject
    public Database(Gson gson, Configuracion configuracion) {
        this.gson = gson;
        this.configuracion = configuracion;
    }

    public List<Usuario> loadUsuarios() {
        Type userListType = new TypeToken<ArrayList<Usuario>>() {
        }.getType();

        List<Usuario> usuarios = null;
        try {
            usuarios = gson.fromJson(
                    new FileReader(configuracion.getPathUsuarios()),
                    userListType);
        } catch (FileNotFoundException e) {
            log.error(e.getMessage(), e);
        }
        return usuarios;
    }

    public boolean saveUsuarios(List<Usuario> usuarios) {
        try (FileWriter fw = new FileWriter("data/usuario.json")) {
            gson.toJson(usuarios, fw);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            return false;
        }
        return true;
    }

    public List<Banner> loadBanners() {
        Type userListType = new TypeToken<ArrayList<Banner>>() {
        }.getType();

        List<Banner> banners = null;
        try {
            banners = gson.fromJson(
                    new FileReader(configuracion.getPathBanners()),
                    userListType);
        } catch (FileNotFoundException e) {
            log.error(e.getMessage(), e);
        }
        return banners;
    }

    public boolean saveBanners(List<Banner> banners) {
        try (FileWriter fw = new FileWriter("data/banners.json")) {
            gson.toJson(banners, fw);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            return false;
        }
        return true;
    }

    public List<Personaje> loadPersonajes() {
        Type userListType = new TypeToken<ArrayList<Personaje>>() {
        }.getType();

        List<Personaje> personajes = null;
        try {
            personajes = gson.fromJson(
                    new FileReader(configuracion.getPathPersonajes()),
                    userListType);
        } catch (FileNotFoundException e) {
            log.error(e.getMessage(), e);
        }
        return personajes;
    }

    public boolean savePersonajes(List<Personaje> personajes) {
        try (FileWriter fw = new FileWriter("data/personajes.json")) {
            gson.toJson(personajes, fw);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            return false;
        }
        return true;
    }
}