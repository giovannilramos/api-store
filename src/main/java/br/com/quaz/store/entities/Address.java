package br.com.quaz.store.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import java.util.UUID;

@Entity
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "TB_ADDRESS")
@Getter
public class Address {
    @Id
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @GeneratedValue(generator = "uuid2")
    private UUID uuid;

    @Column(nullable = false, length = 8)
    private String cep;
    @Column(nullable = false)
    private String number;
    @Column(nullable = false)
    private String street;
    @Column(nullable = false)
    private String district;
    @Column(length = 3, nullable = false)
    private String country;
    @Column(nullable = false)
    private String city;
    @Column(nullable = false, length = 2)
    private String state;

    private String complement;

    @ManyToOne
    private User user;
}
