package pe.edu.i202220224.cl2_web_backoffice_incacutipa_ninfa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.edu.i202220224.cl2_web_backoffice_incacutipa_ninfa.entity.Rental;
import pe.edu.i202220224.cl2_web_backoffice_incacutipa_ninfa.entity.Inventory;


public interface RentalRepository extends JpaRepository<Rental, Integer> {
    void deleteAllByInventory(Inventory inventory);
}
