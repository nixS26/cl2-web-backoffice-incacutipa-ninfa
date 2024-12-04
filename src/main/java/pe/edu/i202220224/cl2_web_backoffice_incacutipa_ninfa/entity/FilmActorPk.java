package pe.edu.i202220224.cl2_web_backoffice_incacutipa_ninfa.entity;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FilmActorPk implements Serializable {
    private Integer actorId;
    private Integer filmId;

}
