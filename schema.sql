-- Database DDL Generated from MyBatis Mapper XMLs

-- Table mapper: TranslateMapper.xml
CREATE TABLE `t_translate`
(
    `id`               INT NOT NULL,
    `canonical_name`   VARCHAR(255),
    `translate`        VARCHAR(255),
    `full_name`        VARCHAR(255),
    `language`         VARCHAR(255),
    `lid`              VARCHAR(255),
    `tree_id`          VARCHAR(255),
    `fetched`          TINYINT(1),
    `create_time`      BIGINT,
    `last_modify_time` BIGINT,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci;

-- Table mapper: SourceDatabaseMapper.xml
CREATE TABLE `t_source_database`
(
    `id`               INT NOT NULL,
    `short_name`       VARCHAR(255),
    `full_name`        VARCHAR(255),
    `create_time`      BIGINT,
    `last_modify_time` BIGINT,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci;

-- Table mapper: LifeCategorySourceDatabaseMapper.xml
CREATE TABLE `t_life_category_source_database`
(
    `id`               INT NOT NULL,
    `life_id`          INT,
    `database_id`      INT,
    `create_time`      BIGINT,
    `last_modify_time` BIGINT,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci;

-- Table mapper: LifeCategoryMapper.xml
CREATE TABLE `t_life_category`
(
    `id`               INT NOT NULL,
    `snId`             VARCHAR(255),
    `name`             VARCHAR(255),
    `type`             VARCHAR(255),
    `parentId`         INT,
    `numChildren`      INT,
    `estimation`       INT,
    `total`            INT,
    `nr_fossil`        INT,
    `nr_extant`        INT,
    `estimate_source`  VARCHAR(255),
    `is_extinct`       TINYINT(1),
    `has_preholocene`  TINYINT(1),
    `has_modern`       TINYINT(1),
    `image`            VARCHAR(255),
    `url`              VARCHAR(255),
    `fetched`          TINYINT(1),
    `create_time`      BIGINT,
    `last_modify_time` BIGINT,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci;

-- Table mapper: SpeciesRecordScrutinyMapper.xml
CREATE TABLE `t_species_record_scrutiny`
(
    `id`               INT NOT NULL,
    `species_id`       VARCHAR(255),
    `scrutiny`         VARCHAR(255),
    `create_time`      BIGINT,
    `last_modify_time` BIGINT,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci;

-- Table mapper: SpeciesCommonNameMapper.xml
CREATE TABLE `t_species_common_name`
(
    `id`               INT NOT NULL,
    `species_id`       VARCHAR(255),
    `name`             VARCHAR(255),
    `language`         VARCHAR(255),
    `country`          VARCHAR(255),
    `create_time`      BIGINT,
    `last_modify_time` BIGINT,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci;

-- Table mapper: SpeciesCommonNameReferencesMapper.xml
CREATE TABLE `t_species_common_names_references`
(
    `id`               INT NOT NULL,
    `common_name_id`   INT,
    `author`           VARCHAR(255),
    `year`             INT,
    `source`           VARCHAR(255),
    `create_time`      BIGINT,
    `last_modify_time` BIGINT,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci;

-- Table mapper: SpeciesSynonymsMapper.xml
CREATE TABLE `t_species_synonyms`
(
    `id`               INT NOT NULL,
    `species_id`       VARCHAR(255),
    `synonyms_id`      VARCHAR(255),
    `create_time`      BIGINT,
    `last_modify_time` BIGINT,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci;

-- Table mapper: SpeciesReferencesMapper.xml
CREATE TABLE `t_species_references`
(
    `id`               INT NOT NULL,
    `species_id`       VARCHAR(255),
    `author`           VARCHAR(255),
    `year`             INT,
    `source`           VARCHAR(255),
    `create_time`      BIGINT,
    `last_modify_time` BIGINT,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci;

-- Table mapper: SpeciesMapper.xml
CREATE TABLE `t_species`
(
    `id`                     VARCHAR(255) NOT NULL,
    `name`                   VARCHAR(255),
    `name_accepted`          TINYINT(1),
    `genus`                  VARCHAR(255),
    `subgenus`               VARCHAR(255),
    `species`                VARCHAR(255),
    `infraspecies_marker`    VARCHAR(255),
    `infraspecies`           VARCHAR(255),
    `author`                 VARCHAR(255),
    `online_resource`        VARCHAR(255),
    `is_extinct`             TINYINT(1),
    `source_database`        VARCHAR(255),
    `source_database_url`    VARCHAR(255),
    `bibliographic_citation` TEXT,
    `url`                    VARCHAR(255),
    `distribution`           TEXT,
    `create_time`            BIGINT,
    `last_modify_time`       BIGINT,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci;

