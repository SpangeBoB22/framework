package io.model;


import javax.persistence.Entity;
import javax.persistence.*;
import javax.persistence.Table;
import java.io.Serial;
import java.io.Serializable;
import lombok.*;

/*
-- framework_test.product definition

CREATE TABLE `product` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `price` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `product_UN` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
 */

@EqualsAndHashCode
@Getter
@Setter
@ToString
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@NoArgsConstructor
@Entity
@Table(name = "product")
public class Product implements Serializable {
    @Serial
    private static final long serialVersionUID = -2L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "name", unique = true, nullable = false)
    private String name;

    @Column(name = "price", nullable = false)
    private int price;
}
