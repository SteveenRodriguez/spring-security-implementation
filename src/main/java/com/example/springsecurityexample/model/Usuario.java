package com.example.springsecurityexample.model;

import jakarta.persistence.*;
import lombok.Data;

//import javax.persistence.*;

@Entity
@Data
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario")
    private Integer idUsuario;

    private String nombre;
    private String email;
    private String password;




}
