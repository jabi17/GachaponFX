package modelo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Personaje {
    private String nombre;
    private int rareza;
    private int precio;
    private int id;
    private String icono;
}