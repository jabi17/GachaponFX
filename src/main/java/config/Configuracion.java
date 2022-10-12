package config;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import jakarta.inject.Singleton;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;

import java.io.IOException;

@Getter
@Log4j2
@Singleton
public class Configuracion {

    public Configuracion() {
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        mapper.findAndRegisterModules();

        try {
            JsonNode node = mapper.readTree(
                    Configuracion.class.getClassLoader().getResourceAsStream("config.yaml"));
            this.pathUsuarios = node.get("pathUsuarios").asText();
            this.pathBanners = node.get("pathBanners").asText();
            this.pathPersonajes = node.get("pathPersonajes").asText();
        } catch (IOException e) {
            log.error(e.getMessage(), e);

        }
    }

    private String pathUsuarios;
    private String pathBanners;
    private String pathPersonajes;
}