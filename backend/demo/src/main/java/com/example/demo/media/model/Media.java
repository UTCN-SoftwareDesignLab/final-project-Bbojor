package com.example.demo.media.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;

@Entity
@Table(uniqueConstraints = {
    @UniqueConstraint(columnNames = "fileName")
})
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class Media {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String fileName;
}
