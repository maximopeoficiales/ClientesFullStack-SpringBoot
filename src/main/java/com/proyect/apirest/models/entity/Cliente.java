package com.proyect.apirest.models.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Data;

@Data
@Entity
@Table(name = "clientes")
public class Cliente implements Serializable {

     private static final long serialVersionUID = 1L;

     @Id
     @GeneratedValue(strategy = GenerationType.IDENTITY)
     private Long id;
     private String nombre;
     private String apellido;
     private String email;

     @Column(name = "created_at")
     @Temporal(TemporalType.DATE) // se le especifica que tipo de fecha
     private Date createdAt;

     /* ciclo de vida de persistance */
     @PrePersist
     void prePersist() {
          // antes de guardar se hara esto
          createdAt = new Date();
     }
}
