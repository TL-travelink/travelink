-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema travelink
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema travelink
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `travelink` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci ;
USE `travelink` ;

-- -----------------------------------------------------
-- Table `travelink`.`member`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `travelink`.`member` (
  `id` VARCHAR(45) NOT NULL,
  `pwd` VARCHAR(45) NOT NULL,
  `name` VARCHAR(45) NOT NULL,
  `address` VARCHAR(45) NOT NULL,
  `point` INT NULL DEFAULT '0',
  `grade` INT NULL DEFAULT '0',
  `roll` VARCHAR(45) NULL DEFAULT NULL,
  `token` VARCHAR(300) NULL DEFAULT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `travelink`.`travel_board`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `travelink`.`travel_board` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `start_at` TIMESTAMP NOT NULL,
  `end_at` TIMESTAMP NOT NULL,
  `title` VARCHAR(45) NOT NULL,
  `member_id` VARCHAR(45) NOT NULL,
  `visibility` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `member_fk_into_travel_board_idx` (`member_id` ASC) VISIBLE,
  CONSTRAINT `member_fk_into_travel_board`
    FOREIGN KEY (`member_id`)
    REFERENCES `travelink`.`member` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
AUTO_INCREMENT = 6
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `travelink`.`matching_board`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `travelink`.`matching_board` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `category_id` VARCHAR(45) NOT NULL,
  `board_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `matching_board_to_board_board_id_idx` (`board_id` ASC) VISIBLE,
  CONSTRAINT `matching_board_to_board_board_id`
    FOREIGN KEY (`board_id`)
    REFERENCES `travelink`.`travel_board` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
AUTO_INCREMENT = 14
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `travelink`.`apply`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `travelink`.`apply` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `matching_board_id` INT NOT NULL,
  `member_id` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `apply_to_matching_board_matching_board_id_idx` (`matching_board_id` ASC) VISIBLE,
  INDEX `apply_to_member_member_id_idx` (`member_id` ASC) VISIBLE,
  CONSTRAINT `apply_to_matching_board_matching_board_id`
    FOREIGN KEY (`matching_board_id`)
    REFERENCES `travelink`.`matching_board` (`id`),
  CONSTRAINT `apply_to_member_member_id`
    FOREIGN KEY (`member_id`)
    REFERENCES `travelink`.`member` (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `travelink`.`sido`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `travelink`.`sido` (
  `sido_code` INT NOT NULL,
  `sido_name` VARCHAR(30) NULL DEFAULT NULL,
  PRIMARY KEY (`sido_code`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `travelink`.`gugun`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `travelink`.`gugun` (
  `gugun_code` INT NOT NULL,
  `gugun_name` VARCHAR(30) NULL DEFAULT NULL,
  `sido_code` INT NOT NULL,
  PRIMARY KEY (`gugun_code`, `sido_code`),
  INDEX `gugun_to_sido_sido_code_fk_idx` (`sido_code` ASC) VISIBLE,
  CONSTRAINT `gugun_to_sido_sido_code_fk`
    FOREIGN KEY (`sido_code`)
    REFERENCES `travelink`.`sido` (`sido_code`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `travelink`.`attraction_info`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `travelink`.`attraction_info` (
  `content_id` INT NOT NULL,
  `content_type_id` INT NULL DEFAULT NULL,
  `title` VARCHAR(100) NULL DEFAULT NULL,
  `addr1` VARCHAR(100) NULL DEFAULT NULL,
  `addr2` VARCHAR(50) NULL DEFAULT NULL,
  `zipcode` VARCHAR(50) NULL DEFAULT NULL,
  `tel` VARCHAR(50) NULL DEFAULT NULL,
  `first_image` VARCHAR(200) NULL DEFAULT NULL,
  `first_image2` VARCHAR(200) NULL DEFAULT NULL,
  `readcount` INT NULL DEFAULT NULL,
  `sido_code` INT NULL DEFAULT NULL,
  `gugun_code` INT NULL DEFAULT NULL,
  `latitude` DECIMAL(20,17) NULL DEFAULT NULL,
  `longitude` DECIMAL(20,17) NULL DEFAULT NULL,
  `mlevel` VARCHAR(2) NULL DEFAULT NULL,
  PRIMARY KEY (`content_id`),
  INDEX `attraction_to_content_type_id_fk_idx` (`content_type_id` ASC) VISIBLE,
  INDEX `attraction_to_sido_code_fk_idx` (`sido_code` ASC) VISIBLE,
  INDEX `attraction_to_gugun_code_fk_idx` (`gugun_code` ASC) VISIBLE,
  CONSTRAINT `attraction_to_content_type_id_fk`
    FOREIGN KEY (`content_type_id`)
    REFERENCES `travelink`.`content_type` (`content_type_id`),
  CONSTRAINT `attraction_to_gugun_code_fk`
    FOREIGN KEY (`gugun_code`)
    REFERENCES `travelink`.`gugun` (`gugun_code`),
  CONSTRAINT `attraction_to_sido_code_fk`
    FOREIGN KEY (`sido_code`)
    REFERENCES `travelink`.`sido` (`sido_code`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `travelink`.`attraction_description`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `travelink`.`attraction_description` (
  `content_id` INT NOT NULL,
  `homepage` VARCHAR(100) NULL DEFAULT NULL,
  `overview` VARCHAR(10000) NULL DEFAULT NULL,
  `telname` VARCHAR(45) NULL DEFAULT NULL,
  PRIMARY KEY (`content_id`),
  CONSTRAINT `attraction_detail_to_attraciton_id_fk`
    FOREIGN KEY (`content_id`)
    REFERENCES `travelink`.`attraction_info` (`content_id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `travelink`.`attraction_detail`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `travelink`.`attraction_detail` (
  `content_id` INT NOT NULL,
  `cat1` VARCHAR(3) NULL DEFAULT NULL,
  `cat2` VARCHAR(5) NULL DEFAULT NULL,
  `cat3` VARCHAR(9) NULL DEFAULT NULL,
  `created_time` VARCHAR(14) NULL DEFAULT NULL,
  `modified_time` VARCHAR(14) NULL DEFAULT NULL,
  `booktour` VARCHAR(5) NULL DEFAULT NULL,
  PRIMARY KEY (`content_id`),
  CONSTRAINT `attraction_detail_to_basic_content_id_fk`
    FOREIGN KEY (`content_id`)
    REFERENCES `travelink`.`attraction_info` (`content_id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `travelink`.`board`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `travelink`.`board` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `member_id` VARCHAR(45) NOT NULL,
  `title` VARCHAR(45) NOT NULL,
  `content` MEDIUMTEXT NOT NULL,
  `status` VARCHAR(45) NOT NULL,
  `hit` INT NULL DEFAULT '0',
  `like_count` INT NULL DEFAULT '0',
  `report_count` INT NULL DEFAULT '0',
  `create_at` TIMESTAMP NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  INDEX `board_to_member_member_id_idx` (`member_id` ASC) VISIBLE,
  CONSTRAINT `board_to_member_member_id`
    FOREIGN KEY (`member_id`)
    REFERENCES `travelink`.`member` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
AUTO_INCREMENT = 31
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `travelink`.`board_file_info`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `travelink`.`board_file_info` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `board_id` INT NOT NULL,
  `save_folder` VARCHAR(45) NULL DEFAULT NULL,
  `original_file` VARCHAR(45) NULL DEFAULT NULL,
  `save_file` VARCHAR(45) NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  INDEX `board_file_info_to_board_idx` (`board_id` ASC) VISIBLE,
  CONSTRAINT `board_file_info_to_board`
    FOREIGN KEY (`board_id`)
    REFERENCES `travelink`.`board` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
AUTO_INCREMENT = 20
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `travelink`.`comment`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `travelink`.`comment` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `board_id` INT NOT NULL,
  `member_id` VARCHAR(45) NOT NULL,
  `content` VARCHAR(45) NOT NULL,
  `create_at` TIMESTAMP NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  INDEX `board_to_member_member_id_idx` (`member_id` ASC) VISIBLE,
  INDEX `comment_to_board_board_id_idx` (`board_id` ASC) VISIBLE,
  CONSTRAINT `comment_to_board_board_id`
    FOREIGN KEY (`board_id`)
    REFERENCES `travelink`.`board` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `comment_to_member_member_id`
    FOREIGN KEY (`member_id`)
    REFERENCES `travelink`.`member` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
AUTO_INCREMENT = 65
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `travelink`.`like`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `travelink`.`like` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `member_id` VARCHAR(45) NOT NULL,
  `board_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `like_to_member_member_id_idx` (`member_id` ASC) VISIBLE,
  INDEX `like_to_board_board_id_idx` (`board_id` ASC) VISIBLE,
  CONSTRAINT `like_to_board_board_id`
    FOREIGN KEY (`board_id`)
    REFERENCES `travelink`.`board` (`id`),
  CONSTRAINT `like_to_member_member_id`
    FOREIGN KEY (`member_id`)
    REFERENCES `travelink`.`member` (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 14
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `travelink`.`member_info`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `travelink`.`member_info` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `member_id` VARCHAR(45) NOT NULL,
  `height` INT NULL DEFAULT NULL,
  `gender` VARCHAR(45) NULL DEFAULT NULL,
  `age` INT NULL DEFAULT NULL,
  `pro_img` VARCHAR(45) NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  INDEX `member_info_to_member_member_id_idx` (`member_id` ASC) VISIBLE,
  CONSTRAINT `member_info_to_member_member_id`
    FOREIGN KEY (`member_id`)
    REFERENCES `travelink`.`member` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
AUTO_INCREMENT = 7
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `travelink`.`message`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `travelink`.`message` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `sender_id` VARCHAR(45) NOT NULL,
  `receiver_id` VARCHAR(45) NOT NULL,
  `title` VARCHAR(45) NOT NULL,
  `content` VARCHAR(45) NOT NULL,
  `create_at` TIMESTAMP NULL DEFAULT NULL,
  `delete_by_sender` TINYINT NULL DEFAULT '0',
  `delete_by_receiver` TINYINT NULL DEFAULT '0',
  `read_by_sender` TINYINT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  INDEX `message_to_member_member_id_idx` (`sender_id` ASC) VISIBLE,
  INDEX `message_to_member_receiver_id_idx` (`receiver_id` ASC) VISIBLE,
  CONSTRAINT `message_to_member_receiver_id`
    FOREIGN KEY (`receiver_id`)
    REFERENCES `travelink`.`member` (`id`),
  CONSTRAINT `message_to_member_sender_id`
    FOREIGN KEY (`sender_id`)
    REFERENCES `travelink`.`member` (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `travelink`.`partner`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `travelink`.`partner` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `following_id` VARCHAR(45) NOT NULL,
  `follower_id` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `partner_to_member_member_id_idx` (`following_id` ASC) VISIBLE,
  INDEX `partner_to_member_follwer_id_fk_idx` (`follower_id` ASC) VISIBLE,
  CONSTRAINT `partner_to_member_follwer_id_fk`
    FOREIGN KEY (`follower_id`)
    REFERENCES `travelink`.`member` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `partner_to_member_member_id`
    FOREIGN KEY (`following_id`)
    REFERENCES `travelink`.`member` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
AUTO_INCREMENT = 12
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `travelink`.`review`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `travelink`.`review` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `content_id` INT NULL DEFAULT NULL,
  `rating` VARCHAR(45) NULL DEFAULT NULL,
  `comment` VARCHAR(45) NULL DEFAULT NULL,
  `member_id` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `review_to_member_member_id_idx` (`member_id` ASC) VISIBLE,
  INDEX `review_to_info_content_id_fk_idx` (`content_id` ASC) VISIBLE,
  CONSTRAINT `review_to_info_content_id_fk`
    FOREIGN KEY (`content_id`)
    REFERENCES `travelink`.`attraction_info` (`content_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `review_to_member_member_id`
    FOREIGN KEY (`member_id`)
    REFERENCES `travelink`.`member` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `travelink`.`travel_course`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `travelink`.`travel_course` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `travel_board_id` INT NOT NULL,
  `content_id` INT NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  INDEX `travel_course_to_travel_board_travel_board_id_idx` (`travel_board_id` ASC) VISIBLE,
  INDEX `travel_course_to_info_content_type_id_fk_idx` (`content_id` ASC) VISIBLE,
  CONSTRAINT `travel_course_to_info_content_id_fk`
    FOREIGN KEY (`content_id`)
    REFERENCES `travelink`.`attraction_info` (`content_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `travel_course_to_travel_board_travel_board_id`
    FOREIGN KEY (`travel_board_id`)
    REFERENCES `travelink`.`travel_board` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
