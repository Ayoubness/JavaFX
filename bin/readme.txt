# to run project using maven

$ mvn verify
$ mvn javafx:run

Runtime: Java 11

Database client: mysql jdbc driver

JavaFx version: openjfx 15

# to create simple project managed by maven

mvn archetype:generate \
        -DarchetypeGroupId=org.openjfx \
        -DarchetypeArtifactId=javafx-archetype-simple \
        -DarchetypeVersion=0.0.3 \
        -DgroupId=com.ezzariy \
        -DartifactId=gestion-magasin-module1 \
        -Dversion=1.0.0 \
        -Djavafx-version=15.0.1


# Creating database and initializing schema

drop database javafxtps;
create database javafxtps;

use javafxtps;

CREATE TABLE `javafxtps`.`products` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `designation` VARCHAR(100) NOT NULL,
  `prix` DOUBLE NULL,
  `qte` INT NULL,
  `date` DATE NULL,
  PRIMARY KEY (`id`));

CREATE TABLE `javafxtps`.`clients` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `nom` VARCHAR(100) NOT NULL,
  `prenom` VARCHAR(100) NOT NULL,
  `telephone` VARCHAR(100) NULL,
  `email` VARCHAR(100) NULL,
  `adresse` VARCHAR(100) NULL,
  PRIMARY KEY (`id`));


CREATE TABLE `javafxtps`.`ventes` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `date` DATE NULL,
  `client_id` int(11),
  PRIMARY KEY (`id`),
  constraint `vente_client_id_fk`
     foreign key (`client_id`)
     references `clients` (`id`)
);

CREATE TABLE `javafxtps`.`ligne_commandes` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `total` DOUBLE NULL,
  `qte` INT NULL,
  `product_id` int(11),
  PRIMARY KEY (`id`),
  constraint `commande_product_id_fk`
     foreign key (`product_id`)
     references `products` (`id`)
);

CREATE TABLE `javafxtps`.`vente_commandes` (
  `vente_id` int(11),
  `commandes_id` int(11),
  primary key (`vente_id`,`commandes_id`),
  constraint `vente_commande_id_fk`
     foreign key (`vente_id`)
     references `ventes` (`id`),
  constraint `commandes_vente_id_fk`
     foreign key (`commandes_id`)
     references `ligne_commandes` (`id`)
);