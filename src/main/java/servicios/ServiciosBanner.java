package servicios;

import data.DaoBanner;
import jakarta.inject.Inject;
import modelo.Banner;
import modelo.Personaje;

import java.util.List;

public class ServiciosBanner {

    private final DaoBanner dao;

    @Inject
    public ServiciosBanner(DaoBanner dao) {
        this.dao = dao;
    }

    public List<Banner> verBanners() {
        return dao.verBanners();
    }

    public Banner getBanner(Banner banner) {
        return dao.getBanner(banner);
    }

    public boolean existeBanner(Banner banner) {
        return dao.existeBanner(banner);
    }

    public Personaje tiradaSingle(String nombreBanner) {
        return dao.tiradaSingle(nombreBanner);
    }

    public List<Personaje> tiradaMulti(String nombreBanner) {
        return dao.tiradaMultiple(nombreBanner);
    }
}