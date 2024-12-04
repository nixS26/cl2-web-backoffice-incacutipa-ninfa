package pe.edu.i202220224.cl2_web_backoffice_incacutipa_ninfa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.edu.i202220224.cl2_web_backoffice_incacutipa_ninfa.entity.Inventory;
import java.util.List;

public interface InventoryRepository extends JpaRepository<Inventory, Integer> {
    List<Inventory> findAllByFilm_FilmId(int filmId);
}
