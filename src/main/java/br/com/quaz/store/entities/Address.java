package br.com.quaz.store.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "TB_ADDRESS")
public class Address {
    @Id
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @GeneratedValue(generator = "uuid2")
    @Type(type = "uuid-char")
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
