-- -----------------------------------------------------
-- Table `restdb`.`category`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `restdb`.`category` (
    `id` VARCHAR(36) NOT NULL,
    `description` VARCHAR(50) NOT NULL,
    `created_date` DATETIME(6) NULL,
    `updated_date` DATETIME(6) NULL,
    `version` INT NULL,
    PRIMARY KEY (`id`))
    ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `restdb`.`beer_category`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `restdb`.`beer_category` (
    `beer_id` VARCHAR(36) NOT NULL,
    `category_id` VARCHAR(36) NOT NULL,
    PRIMARY KEY (`beer_id`, `category_id`),
    INDEX `fk_category_has_beer_beer1_idx` (`beer_id` ASC) VISIBLE,
    INDEX `fk_category_has_beer_category1_idx` (`category_id` ASC) VISIBLE,
    CONSTRAINT `fk_category_has_beer_category1`
    FOREIGN KEY (`category_id`)
    REFERENCES `restdb`.`category` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
    CONSTRAINT `fk_category_has_beer_beer1`
    FOREIGN KEY (`beer_id`)
    REFERENCES `restdb`.`beer` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
    ENGINE = InnoDB;
