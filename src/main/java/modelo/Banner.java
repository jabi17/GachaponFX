package modelo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@AllArgsConstructor
@Getter
@Setter
public class Banner {
    private String nombre;
    private int precio;
    private Personaje personajeDestacado;
    private int pity;
    private String imagen;
}