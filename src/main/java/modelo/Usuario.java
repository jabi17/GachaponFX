package modelo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Data
@AllArgsConstructor
@Getter
@Setter
public class Usuario {
    private String nombreUsuario;
    private int cantidadMonedas;
    private List<Personaje> inventario;
    private List<Personaje> tienda;
    private LocalDate ultimaConexion;
    private LocalDate tiendaRefresh;

    public String getNombreUsuario() {
        return Objects.requireNonNullElse(nombreUsuario, "");
    }
}