                                      -- MySQL Workbench Forward Engineering

                                      SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
                                      SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
                                      SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

                                      -- -----------------------------------------------------
                                      -- Schema mydb
                                      -- -----------------------------------------------------

                                      -- -----------------------------------------------------
                                      -- Schema mydb
                                      -- -----------------------------------------------------
                                      CREATE SCHEMA IF NOT EXISTS `mydb` DEFAULT CHARACTER SET utf8 ;
                                      USE `mydb` ;

                                      -- -----------------------------------------------------
                                      -- Table `mydb`.`Admin`
                                      -- -----------------------------------------------------
                                      CREATE TABLE IF NOT EXISTS `mydb`.`Admin` (
                                        `Admin_id` INT NOT NULL,
                                        `Name` VARCHAR(45) NULL,
                                        `Phone` VARCHAR(45) NULL,
                                        `Email` VARCHAR(45) NULL,
                                        `Store_id` VARCHAR(45) NULL,
                                        `Employee_id` VARCHAR(45) NULL,
                                        PRIMARY KEY (`Admin_id`),
                                        UNIQUE INDEX `Admin_id_UNIQUE` (`Admin_id` ASC) VISIBLE)
                                      ENGINE = InnoDB;


                                      -- -----------------------------------------------------
                                      -- Table `mydb`.`order_status_code`
                                      -- -----------------------------------------------------
                                      CREATE TABLE IF NOT EXISTS `mydb`.`order_status_code` (
                                        `id` INT NOT NULL,
                                        `status_code` INT NULL,
                                        `description` VARCHAR(45) NULL,
                                        PRIMARY KEY (`id`))
                                      ENGINE = InnoDB;


                                      -- -----------------------------------------------------
                                      -- Table `mydb`.`order_item`
                                      -- -----------------------------------------------------
                                      CREATE TABLE IF NOT EXISTS `mydb`.`order_item` (
                                        `id` INT NOT NULL,
                                        `order_id` INT NULL,
                                        `product_id` INT NULL,
                                        `quantity` INT NULL,
                                        `price` FLOAT NULL,
                                        `` VARCHAR(45) NULL,
                                        PRIMARY KEY (`id`))
                                      ENGINE = InnoDB;


                                      -- -----------------------------------------------------
                                      -- Table `mydb`.`order`
                                      -- -----------------------------------------------------
                                      CREATE TABLE IF NOT EXISTS `mydb`.`order` (
                                        `id` INT NOT NULL,
                                        `customer_id` INT NULL,
                                        `status_code_id` INT NULL,
                                        `customer_comments` VARCHAR(45) NULL,
                                        `order_status_code_id` INT NOT NULL,
                                        `order_item_id` INT NOT NULL,
                                        PRIMARY KEY (`id`, `order_status_code_id`, `order_item_id`),
                                        INDEX `fk_order_order_status_code1_idx` (`order_status_code_id` ASC) VISIBLE,
                                        INDEX `fk_order_order_item1_idx` (`order_item_id` ASC) VISIBLE,
                                        CONSTRAINT `fk_order_order_status_code1`
                                          FOREIGN KEY (`order_status_code_id`)
                                          REFERENCES `mydb`.`order_status_code` (`id`)
                                          ON DELETE NO ACTION
                                          ON UPDATE NO ACTION,
                                        CONSTRAINT `fk_order_order_item1`
                                          FOREIGN KEY (`order_item_id`)
                                          REFERENCES `mydb`.`order_item` (`id`)
                                          ON DELETE NO ACTION
                                          ON UPDATE NO ACTION)
                                      ENGINE = InnoDB;


                                      -- -----------------------------------------------------
                                      -- Table `mydb`.`Products`
                                      -- -----------------------------------------------------
                                      CREATE TABLE IF NOT EXISTS `mydb`.`Products` (
                                        `Product_id` INT NOT NULL,
                                        `Product_name` VARCHAR(45) NOT NULL,
                                        `price` FLOAT NOT NULL,
                                        `description` VARCHAR(45) NULL,
                                        `category_id` INT NOT NULL,
                                        `category` VARCHAR(10) NOT NULL,
                                        `order_item_id` INT NOT NULL,
                                        PRIMARY KEY (`Product_id`),
                                        UNIQUE INDEX `Product_id_UNIQUE` (`Product_id` ASC) VISIBLE,
                                        INDEX `fk_Products_order_item1_idx` (`order_item_id` ASC) VISIBLE,
                                        CONSTRAINT `fk_Products_order_item1`
                                          FOREIGN KEY (`order_item_id`)
                                          REFERENCES `mydb`.`order_item` (`id`)
                                          ON DELETE NO ACTION
                                          ON UPDATE NO ACTION)
                                      ENGINE = InnoDB;


                                      -- -----------------------------------------------------
                                      -- Table `mydb`.`shopping_cart`
                                      -- -----------------------------------------------------
                                      CREATE TABLE IF NOT EXISTS `mydb`.`shopping_cart` (
                                        `id` INT NOT NULL,
                                        `customer_id` INT NULL,
                                        `product_id` INT NULL,
                                        `quantity` INT NULL,
                                        `price` FLOAT NULL,
                                        `Products_Product_id` INT NOT NULL,
                                        PRIMARY KEY (`id`, `Products_Product_id`),
                                        INDEX `fk_shopping_cart_Products1_idx` (`Products_Product_id` ASC) VISIBLE,
                                        CONSTRAINT `fk_shopping_cart_Products1`
                                          FOREIGN KEY (`Products_Product_id`)
                                          REFERENCES `mydb`.`Products` (`Product_id`)
                                          ON DELETE NO ACTION
                                          ON UPDATE NO ACTION)
                                      ENGINE = InnoDB;


                                      -- -----------------------------------------------------
                                      -- Table `mydb`.`credentials`
                                      -- -----------------------------------------------------
                                      CREATE TABLE IF NOT EXISTS `mydb`.`credentials` (
                                        `customer_id` INT NOT NULL,
                                        `User_name` VARCHAR(45) NULL,
                                        `Password` VARCHAR(45) NULL,
                                        PRIMARY KEY (`customer_id`))
                                      ENGINE = InnoDB;


                                      -- -----------------------------------------------------
                                      -- Table `mydb`.`Customer`
                                      -- -----------------------------------------------------
                                      CREATE TABLE IF NOT EXISTS `mydb`.`Customer` (
                                        `id` INT NOT NULL,
                                        `F_name` VARCHAR(45) NULL,
                                        `M_name` VARCHAR(45) NULL,
                                        `L_name` VARCHAR(45) NULL,
                                        `Phone` VARCHAR(45) NULL,
                                        `email` VARCHAR(45) NULL,
                                        `order_id` INT NOT NULL,
                                        `shopping_cart_id` INT NOT NULL,
                                        `credentials_customer_id` INT NOT NULL,
                                        PRIMARY KEY (`id`, `order_id`, `shopping_cart_id`, `credentials_customer_id`),
                                        UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE,
                                        INDEX `fk_Customer_order_idx` (`order_id` ASC) VISIBLE,
                                        INDEX `fk_Customer_shopping_cart1_idx` (`shopping_cart_id` ASC) VISIBLE,
                                        INDEX `fk_Customer_credentials1_idx` (`credentials_customer_id` ASC) VISIBLE,
                                        CONSTRAINT `fk_Customer_order`
                                          FOREIGN KEY (`order_id`)
                                          REFERENCES `mydb`.`order` (`id`)
                                          ON DELETE NO ACTION
                                          ON UPDATE NO ACTION,
                                        CONSTRAINT `fk_Customer_shopping_cart1`
                                          FOREIGN KEY (`shopping_cart_id`)
                                          REFERENCES `mydb`.`shopping_cart` (`id`)
                                          ON DELETE NO ACTION
                                          ON UPDATE NO ACTION,
                                        CONSTRAINT `fk_Customer_credentials1`
                                          FOREIGN KEY (`credentials_customer_id`)
                                          REFERENCES `mydb`.`credentials` (`customer_id`)
                                          ON DELETE NO ACTION
                                          ON UPDATE NO ACTION)
                                      ENGINE = InnoDB;


                                      -- -----------------------------------------------------
                                      -- Table `mydb`.`product_category`
                                      -- -----------------------------------------------------
                                      CREATE TABLE IF NOT EXISTS `mydb`.`product_category` (
                                        `id` INT NOT NULL,
                                        `name` VARCHAR(45) NULL,
                                        `code` VARCHAR(45) NULL,
                                        `description` VARCHAR(45) NULL,
                                        `Product_id` INT NOT NULL,
                                        PRIMARY KEY (`id`, `Product_id`),
                                        INDEX `fk_product_category_Products1_idx` (`Product_id` ASC) VISIBLE,
                                        CONSTRAINT `fk_product_category_Products1`
                                          FOREIGN KEY (`Product_id`)
                                          REFERENCES `mydb`.`Products` (`Product_id`)
                                          ON DELETE NO ACTION
                                          ON UPDATE NO ACTION)
                                      ENGINE = InnoDB;

                                      
                                      SET SQL_MODE=@OLD_SQL_MODE;
                                      SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
                                      SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
