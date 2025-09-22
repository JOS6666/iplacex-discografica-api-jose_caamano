package cl.iplacex.discografia.discos;

import cl.iplacex.discografia.artistas.IArtistaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class DiscoController {

    private final IDiscoRepository repo;
    private final IArtistaRepository artistaRepo;

    public DiscoController(IDiscoRepository repo, IArtistaRepository artistaRepo) {
        this.repo = repo;
        this.artistaRepo = artistaRepo;
    }

    // POST /api/disco
    @PostMapping(
        value = "/disco",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> HandlePostDiscoRequest(@RequestBody Disco body) {
        if (body.idArtista == null || !artistaRepo.existsById(body.idArtista)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("idArtista inválido o artista no existe");
        }
        Disco saved = repo.save(body);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    // GET /api/discos
    @GetMapping(
        value = "/discos",
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<List<Disco>> HandleGetDiscosRequest() {
        return ResponseEntity.ok(repo.findAll());
    }

    // GET /api/disco/{id}
    @GetMapping(
        value = "/disco/{id}",
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> HandleGetDiscoRequest(@PathVariable String id) {
        return repo.findById(id)
                .<ResponseEntity<?>>map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
                                               .body("Disco no encontrado"));
    }

    // GET /api/artista/{id}/discos
    @GetMapping(
        value = "/artista/{id}/discos",
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<List<Disco>> HandleGetDiscosByArtistaRequest(@PathVariable String id) {
        return ResponseEntity.ok(repo.findDiscosByIdArtista(id));
    }
}
