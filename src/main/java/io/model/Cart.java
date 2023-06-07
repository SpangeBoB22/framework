package io.model;

import lombok.*;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;

/*
-- framework_test.cart definition

CREATE TABLE `cart` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `id_cart` varchar(100) DEFAULT NULL,
  `id_member` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
 */
@EqualsAndHashCode
@Getter
@Setter
@ToString
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@NoArgsConstructor
@Entity
@Table(name = "cart")
public class Cart  implements Serializable  {
    @Serial
    private static final long serialVersionUID = -2L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "id_cart")
    private String idCart;

    @Column(name = "id_member")
    private String idMember;
}
