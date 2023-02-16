package com.example.springsecurityexample.model;

import jakarta.persistence.*;
import lombok.Data;

//import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Data
public class Contacto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_contacto")
    private Integer idContacto;

    private String nombre;

    @Column(name = "fecha_nacimiento")
    private LocalDate fechaNacimiento;

    private String celular;
    private String email;
}
