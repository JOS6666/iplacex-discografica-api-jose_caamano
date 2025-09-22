package cl.iplacex.discografia.artistas;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.List;

@Document(collection = "artistas")
public class Artista {
    @Id
    public String _id;

    public String nombre;
    public List<String> estilos;
    public int anioFundacion;
    public boolean estaActivo;
}
