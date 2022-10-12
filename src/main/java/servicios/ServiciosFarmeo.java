package servicios;

import data.DaoFarmeo;
import jakarta.inject.Inject;

public class ServiciosFarmeo {

    private final DaoFarmeo daoFarmeo;

    @Inject
    public ServiciosFarmeo(DaoFarmeo daoFarmeo) {
        this.daoFarmeo = daoFarmeo;
    }

    public void addMonedas(int cantidad) {
        daoFarmeo.addMonedas(cantidad);
    }
}