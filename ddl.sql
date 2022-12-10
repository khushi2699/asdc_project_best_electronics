SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema CSCI5308_4_DEVINT
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema CSCI5308_4_DEVINT
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `CSCI5308_4_DEVINT` DEFAULT CHARACTER SET utf8 ;
USE `CSCI5308_4_DEVINT` ;

-- -----------------------------------------------------
-- Table `CSCI5308_4_DEVINT`.`User`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `CSCI5308_4_DEVINT`.`User` (
  `userId` INT NOT NULL AUTO_INCREMENT,
  `firstName` VARCHAR(45) NOT NULL,
  `lastName` VARCHAR(45) NOT NULL,
  `emailAddress` VARCHAR(100) NOT NULL,
  `password` VARCHAR(45) NOT NULL,
  `dob` DATE NOT NULL,
  `gender` VARCHAR(45) NOT NULL,
  `address` LONGTEXT NOT NULL,
  `resetPasswordToken` INT NULL,
  PRIMARY KEY (`userId`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `CSCI5308_4_DEVINT`.`Admin`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `CSCI5308_4_DEVINT`.`Admin` (
  `adminId` INT NOT NULL AUTO_INCREMENT,
  `firstName` VARCHAR(45) NOT NULL,
  `lastName` VARCHAR(45) NOT NULL,
  `emailAddress` VARCHAR(45) NOT NULL,
  `password` VARCHAR(45) NOT NULL,
  `resetPasswordToken` INT NULL,
  PRIMARY KEY (`adminId`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `CSCI5308_4_DEVINT`.`ProductCategory`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `CSCI5308_4_DEVINT`.`ProductCategory` (
  `categoryId` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`categoryId`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `CSCI5308_4_DEVINT`.`Product`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `CSCI5308_4_DEVINT`.`Product` (
  `productId` INT NOT NULL AUTO_INCREMENT,
  `productCode` VARCHAR(45) NOT NULL,
  `productName` VARCHAR(45) NOT NULL,
  `productBrand` VARCHAR(45) NOT NULL,
  `productDescription` TEXT(200) BINARY NOT NULL,
  `productPrice` DOUBLE NOT NULL,
  `availableQuantity` INT NOT NULL,
  `categoryId` INT NOT NULL,
  PRIMARY KEY (`productId`),
  INDEX `fk_Product_ProductCategory1_idx` (`categoryId` ASC),
  CONSTRAINT `fk_Product_ProductCategory`
    FOREIGN KEY (`categoryId`)
    REFERENCES `CSCI5308_4_DEVINT`.`ProductCategory` (`categoryId`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `CSCI5308_4_DEVINT`.`CartItem`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `CSCI5308_4_DEVINT`.`CartItem` (
  `cartItemId` INT NOT NULL AUTO_INCREMENT,
  `quantity` INT NOT NULL,
  `productId` INT NOT NULL,
  `userId` INT NOT NULL,
  PRIMARY KEY (`cartItemId`),
  INDEX `fk_Cart_Product1_idx` (`productId` ASC),
  INDEX `fk_Cart_UserInfo1_idx` (`userId` ASC),
  CONSTRAINT `fk_Cart_Product`
    FOREIGN KEY (`productId`)
    REFERENCES `CSCI5308_4_DEVINT`.`Product` (`productId`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Cart_User`
    FOREIGN KEY (`userId`)
    REFERENCES `CSCI5308_4_DEVINT`.`User` (`userId`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `CSCI5308_4_DEVINT`.`WishListItem`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `CSCI5308_4_DEVINT`.`WishListItem` (
  `wishListItemId` INT NOT NULL AUTO_INCREMENT,
  `quantity` INT NOT NULL,
  `productId` INT NOT NULL,
  `userId` INT NOT NULL,
  PRIMARY KEY (`wishListItemId`),
  INDEX `fk_WishList_Product1_idx` (`productId` ASC),
  INDEX `fk_WishList_UserInfo1_idx` (`userId` ASC),
  CONSTRAINT `fk_WishList_Product`
    FOREIGN KEY (`productId`)
    REFERENCES `CSCI5308_4_DEVINT`.`Product` (`productId`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_WishList_User`
    FOREIGN KEY (`userId`)
    REFERENCES `CSCI5308_4_DEVINT`.`User` (`userId`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `CSCI5308_4_DEVINT`.`Order`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `CSCI5308_4_DEVINT`.`Order` (
  `orderId` INT NOT NULL AUTO_INCREMENT,
  `orderAmount` DOUBLE NOT NULL,
  `orderDate` DATETIME NOT NULL,
  `orderStatus` VARCHAR(45) NOT NULL,
  `paymentMethod` VARCHAR(45) NOT NULL,
  `userId` INT NOT NULL,
  PRIMARY KEY (`orderId`),
  INDEX `fk_Order_UserInfo1_idx` (`userId` ASC),
  CONSTRAINT `fk_Order_User`
    FOREIGN KEY (`userId`)
    REFERENCES `CSCI5308_4_DEVINT`.`User` (`userId`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `CSCI5308_4_DEVINT`.`OrderItem`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `CSCI5308_4_DEVINT`.`OrderItem` (
  `orderItemId` INT NOT NULL AUTO_INCREMENT,
  `quantity` INT NOT NULL,
  `orderId` INT NOT NULL,
  `productId` INT NOT NULL,
  PRIMARY KEY (`orderItemId`),
  INDEX `fk_OrderItem_Order1_idx` (`orderId` ASC),
  INDEX `fk_OrderItem_Product1_idx` (`productId` ASC),
  CONSTRAINT `fk_OrderItem_Order`
    FOREIGN KEY (`orderId`)
    REFERENCES `CSCI5308_4_DEVINT`.`Order` (`orderId`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_OrderItem_Product`
    FOREIGN KEY (`productId`)
    REFERENCES `CSCI5308_4_DEVINT`.`Product` (`productId`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
