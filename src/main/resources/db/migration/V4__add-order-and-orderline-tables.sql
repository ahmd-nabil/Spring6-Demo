-- MySQL Workbench Synchronization
-- Generated: 2023-05-08 18:39
-- Model: New Model
-- Version: 1.0
-- Project: Name of the project
-- Author: AhmedNabil

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

CREATE TABLE IF NOT EXISTS `restdb`.`beer_order` (
     `id` VARCHAR(36) NOT NULL,
     `created_date` DATETIME NOT NULL,
     `updated_date` DATETIME NOT NULL,
     `version` INT(11) NOT NULL,
     `customer_id` VARCHAR(36) NOT NULL,
     PRIMARY KEY (`id`),
     INDEX `fk_beer_order_customer_idx` (`customer_id` ASC) VISIBLE,
     CONSTRAINT `fk_beer_order_customer`
         FOREIGN KEY (`customer_id`)
             REFERENCES `restdb`.`customer` (`id`)
             ON DELETE NO ACTION
             ON UPDATE CASCADE)
    ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8mb4
    COLLATE = utf8mb4_unicode_ci;

CREATE TABLE IF NOT EXISTS `restdb`.`beer_order_line` (
`beer_order_id` VARCHAR(36) NOT NULL,
`beer_id` VARCHAR(36) NOT NULL,
`created_date` DATETIME NOT NULL,
`updated_date` DATETIME NOT NULL,
`order_quantity` INT(11) NULL DEFAULT NULL,
`quantity_allocated` INT(11) NULL DEFAULT NULL,
`version` INT(11) NULL DEFAULT NULL,
PRIMARY KEY (`beer_order_id`, `beer_id`),
INDEX `fk_beer_order_line_beer1_idx` (`beer_id` ASC) VISIBLE,
CONSTRAINT `fk_beer_order_line_beer_order1`
  FOREIGN KEY (`beer_order_id`)
      REFERENCES `restdb`.`beer_order` (`id`)
      ON DELETE CASCADE
      ON UPDATE CASCADE,
CONSTRAINT `fk_beer_order_line_beer1`
  FOREIGN KEY (`beer_id`)
      REFERENCES `restdb`.`beer` (`id`)
      ON DELETE NO ACTION
      ON UPDATE NO ACTION
)   ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8mb4
    COLLATE = utf8mb4_unicode_ci;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;