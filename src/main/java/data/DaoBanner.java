package data;

import jakarta.inject.Inject;
import modelo.Banner;
import modelo.Personaje;

import java.util.ArrayList;
import java.util.List;

public class DaoBanner {

    private final Database db;

    @Inject
    public DaoBanner(Database db) {
        this.db = db;
    }

    public List<Banner> verBanners() {
        return db.loadBanners();
    }

    public Banner getBanner(Banner banner) {
        List<Banner> banners = db.loadBanners();
        return banners.get(banners.indexOf(banner));
    }

    public boolean existeBanner(Banner banner) {
        List<Banner> banners = db.loadBanners();
        return banners.contains(banner);
    }

    public Personaje tiradaSingle(String nombreBanner) {
        Personaje personaje;
        List<Banner> banners = db.loadBanners();
        Banner b = banners.stream().filter(banner1 -> banner1.getNombre().equalsIgnoreCase(nombreBanner)).findFirst().get();
        List<Personaje> personajes = db.loadPersonajes();
        if (b.getPity() == 89) {
            personaje = b.getPersonajeDestacado();
            b.setPity(-1);
        } else if (b.getPity() > 72) {
            double probabilidad = b.getPity() - 72 + 0.6;
            double random = Math.random() * 100;
            if (random < probabilidad) {
                personaje = b.getPersonajeDestacado();
                b.setPity(-1);
            } else {
                personaje = shufflePersonajes(personajes);
            }
        } else {
            double probabilidad = 0.6;
            double random = Math.random() * 100;
            if (random < probabilidad) {
                personaje = b.getPersonajeDestacado();
                b.setPity(-1);
            } else {
                personaje = shufflePersonajes(personajes);
            }
        }
        b.setPity(b.getPity() + 1);
        db.saveBanners(banners);
        return personaje;
    }

    public List<Personaje> tiradaMultiple(String nombreBanner) {
        List<Personaje> personajesGanados = new ArrayList<>();

        List<Banner> banners = db.loadBanners();
        Banner b = banners.stream().filter(banner -> banner.getNombre().equalsIgnoreCase(nombreBanner)).findFirst().get();
        List<Personaje> personajes = db.loadPersonajes();
        for (int i = 0; i < 10; i++) {
            if (b.getPity() == 89) {
                personajesGanados.add(b.getPersonajeDestacado());
                b.setPity(0);
            } else if (b.getPity() > 72) {
                double probabilidad = b.getPity() - 72 + 0.6;
                double random = Math.random() * 100;
                if (random < probabilidad) {
                    personajesGanados.add(b.getPersonajeDestacado());
                    b.setPity(-1);
                } else {
                    personajesGanados.add(shufflePersonajes(personajes));
                }
                b.setPity(b.getPity() + 1);
            } else {
                double probabilidad = 0.6;
                double random = Math.random() * 100;
                if (random < probabilidad) {
                    personajesGanados.add(b.getPersonajeDestacado());
                    b.setPity(-1);
                } else {
                    personajesGanados.add(shufflePersonajes(personajes));
                }
                b.setPity(b.getPity() + 1);
            }
        }
        db.saveBanners(banners);
        return personajesGanados;
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