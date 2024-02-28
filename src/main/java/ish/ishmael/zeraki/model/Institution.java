package ish.ishmael.zeraki.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity(name="Institution")
@Table(
        name="institutions",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "institution_name_unique",
                        columnNames = "name"
                )
        }
)
@NoArgsConstructor
@AllArgsConstructor
public class Institution {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(
            updatable = false
    )
    private Long id;
    @Column(
            nullable = false
    )
    private String name;
}
