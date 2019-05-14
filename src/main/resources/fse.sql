CREATE SCHEMA `anurag` ;

CREATE TABLE `anurag`.`users` (
  `user_id` bigint(8) NOT NULL AUTO_INCREMENT,
  `firstname` varchar(45) DEFAULT NULL,
  `lastname` varchar(45) DEFAULT NULL,
  `employee_id` bigint(8) DEFAULT NULL,
  `project_id` bigint(8) DEFAULT NULL,
  `task_id` bigint(8) DEFAULT NULL,
  PRIMARY KEY (`user_id`)
);
  
  
CREATE TABLE `anurag`.`project` (
  `project_id` BIGINT(8) NOT NULL AUTO_INCREMENT,
  `project` VARCHAR(200) NULL,
  `startdate` DATE NULL,
  `enddate` DATE NULL,
  `priority` INT NULL,
  PRIMARY KEY (`project_id`));

CREATE TABLE `anurag`.`parent_task` (
  `parent_id` BIGINT(8) NOT NULL AUTO_INCREMENT,
  `parent_task` VARCHAR(200) NULL,
  PRIMARY KEY (`parent_id`));

CREATE TABLE `anurag`.`task` (
  `task_id` BIGINT(8) NOT NULL AUTO_INCREMENT,
  `parent_id` BIGINT(8) NULL,
  `project_id` BIGINT(8) NULL,
  `task` VARCHAR(200) NULL,
  `startdate` DATE NULL,
  `enddate` DATE NULL,
  `priority` INT NULL,
  `status` TINYINT(1) NOT NULL DEFAULT 1,
  PRIMARY KEY (`task_id`),
  INDEX `fk_project_id_idx` (`project_id` ASC),
  CONSTRAINT `fk_project_id`
    FOREIGN KEY (`project_id`)
    REFERENCES `anurag`.`project` (`project_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);
