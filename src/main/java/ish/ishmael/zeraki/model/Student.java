package ish.ishmael.zeraki.model;


import jakarta.persistence.*;
import lombok.*;

@Entity(name ="Student")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "students")
public class Student {
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
    @JoinColumn(name = "course_id")
    private Course course;

}
