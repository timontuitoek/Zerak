package ish.ishmael.zeraki.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "Course")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(
        name = "courses",
       uniqueConstraints = {
               @UniqueConstraint(
                       name = "course_name_unique",
                       columnNames = "name"
               )
       }

)
public class Course {
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "institution_id")
    private Institution institution;
}
