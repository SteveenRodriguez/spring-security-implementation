package com.example.springsecurityexample.controller;

import com.example.springsecurityexample.model.Contacto;
import com.example.springsecurityexample.repository.ContactoRepository;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/contactos")
@AllArgsConstructor
public class ContactoController {

    private final ContactoRepository contactoRepository;

    @GetMapping()
    public List<Contacto> listContacto() {
        return contactoRepository.findAll();
    }
}
