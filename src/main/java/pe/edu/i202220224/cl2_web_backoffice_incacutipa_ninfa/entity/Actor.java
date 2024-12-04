package pe.edu.i202220224.cl2_web_backoffice_incacutipa_ninfa.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.util.Date;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Actor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer actorId;
    private String firstName;
    private String lastName;
    private Date lastUpdate;

    @OneToMany(mappedBy = "actor", cascade = CascadeType.REMOVE)
    private List<FilmActor> filmActors;

}
