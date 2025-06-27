-- brand table
CREATE TABLE brand
(
    id          bigint AUTO_INCREMENT
        PRIMARY KEY,
    name        varchar(50)                          NOT NULL,
    image_path  varchar(255) NULL COMMENT '이미지 경로',
    used        tinyint(1) DEFAULT 1                   NOT NULL,
    created_at  datetime DEFAULT CURRENT_TIMESTAMP() NOT NULL,
    updated_at  datetime DEFAULT CURRENT_TIMESTAMP() NOT NULL ON UPDATE CURRENT_TIMESTAMP() COMMENT '수정일시',
    deleted_at  datetime NULL COMMENT '삭제일시',
    v_activated tinyint(1) AS (IF(deleted_at IS NULL, 1, 0)) VIRTUAL,
    CONSTRAINT uk_name_vactivated UNIQUE (name, v_activated)
) COMMENT '브랜드' CHARSET = utf8mb4;


-- manage_category table
CREATE TABLE manage_category
(
    id                 bigint AUTO_INCREMENT COMMENT '관리 카테고리 ID'
        PRIMARY KEY,
    root_category_id   bigint                                   NOT NULL COMMENT '루트 카테고리 ID',
    parent_category_id bigint                                   NULL COMMENT '상위카테고리 ID',
    name               varchar(100)                             NOT NULL COMMENT '카테고리 명',
    depth              tinyint                                  NOT NULL COMMENT '현재 단계',
    used               tinyint(1)   DEFAULT 1                   NOT NULL COMMENT '사용여부',
    is_leaf            tinyint(1)   DEFAULT 0                   NULL COMMENT '리프 카테고리 여부',
    created_at         datetime     DEFAULT CURRENT_TIMESTAMP() NOT NULL COMMENT '등록일시',
    created_by         varchar(100) DEFAULT ''                  NOT NULL COMMENT '등록자',
    updated_at         datetime     DEFAULT CURRENT_TIMESTAMP() NOT NULL ON UPDATE CURRENT_TIMESTAMP() COMMENT '수정일시',
    updated_by         varchar(100) DEFAULT ''                  NOT NULL COMMENT '수정자',
    deleted_at  datetime NULL COMMENT '삭제일시'
)
    COMMENT '관리 카테고리' CHARSET = utf8mb4;

CREATE INDEX idx_parentcategoryid
    ON manage_category (parent_category_id);




-- product table
CREATE TABLE product
(
    id              bigint AUTO_INCREMENT
        PRIMARY KEY,
    brand_id        bigint                                   NOT NULL COMMENT '브랜드ID',
    category_id     bigint                                   NOT NULL COMMENT '카테고리ID',
    name            varchar(100)                             NOT NULL COMMENT '상품명',
    description     text NULL COMMENT '상품설명',
    model_no        varchar(100)                             NOT NULL COMMENT '모델 번호',
    used            tinyint                                  NOT NULL COMMENT '사용여부',
    main_image_path varchar(255) NULL COMMENT '메인 이미지 URL',
    created_at      datetime     DEFAULT CURRENT_TIMESTAMP() NOT NULL COMMENT '등록일시',
    created_by      varchar(100) DEFAULT ''                  NOT NULL COMMENT '등록자',
    updated_at      datetime     DEFAULT CURRENT_TIMESTAMP() NOT NULL ON UPDATE CURRENT_TIMESTAMP() COMMENT '수정일시',
    updated_by      varchar(100) DEFAULT ''                  NOT NULL COMMENT '수정자',
    deleted_at      datetime NULL COMMENT '삭제일시',
    v_activated     tinyint(1) AS (IF(deleted_at IS NULL, 1, 0)) VIRTUAL,
    CONSTRAINT uk_brandid_modelno_vactivated UNIQUE (brand_id, model_no, v_activated)
) COMMENT '상품' CHARSET = utf8mb4;


-- item table
CREATE TABLE item
(
    id              bigint AUTO_INCREMENT
        PRIMARY KEY,
    product_id      bigint                                   NOT NULL COMMENT '상품 ID',
    name            varchar(255) NULL COMMENT '옵션명',
    sku_code        varchar(100)                             NOT NULL COMMENT '판매 단위 코드',
    option_color    varchar(50)                              NOT NULL COMMENT '색상 옵션',
    option_size     varchar(50)                              NOT NULL COMMENT '사이즈 옵션',
    description     text NULL COMMENT '상세설명',
    price           int                                      NOT NULL COMMENT '가격',
    used            tinyint      DEFAULT 1                   NOT NULL COMMENT '사용여부',
    main_image_path varchar(255) NULL COMMENT '대표이미지path',
    created_at      datetime     DEFAULT CURRENT_TIMESTAMP() NOT NULL COMMENT '등록일시',
    created_by      varchar(100) DEFAULT ''                  NOT NULL COMMENT '등록자',
    updated_at      datetime     DEFAULT CURRENT_TIMESTAMP() NOT NULL ON UPDATE CURRENT_TIMESTAMP() COMMENT '수정일시',
    updated_by      varchar(100) DEFAULT ''                  NOT NULL COMMENT '수정자',
    deleted_at      datetime NULL COMMENT '삭제일시',
    v_activated     tinyint(1) AS (IF(deleted_at IS NULL, 1, 0)) VIRTUAL,
    CONSTRAINT uk_productid_skucode_vactivated UNIQUE (product_id, sku_code, v_activated)
) COMMENT '아이템' CHARSET = utf8mb4;


-- item_inventory table
CREATE TABLE item_inventory
(
    id         bigint AUTO_INCREMENT
        PRIMARY KEY,
    item_id bigint NOT NULL COMMENT '상품 아이템 ID',
    stock_quantity int NOT NULL COMMENT '현재 재고',
    reserved_quantity int NOT NULL COMMENT '예약 재고',
    created_at      datetime     DEFAULT CURRENT_TIMESTAMP() NOT NULL COMMENT '등록일시',
    created_by      varchar(100) DEFAULT ''                  NOT NULL COMMENT '등록자',
    updated_at      datetime     DEFAULT CURRENT_TIMESTAMP() NOT NULL ON UPDATE CURRENT_TIMESTAMP() COMMENT '수정일시',
    updated_by      varchar(100) DEFAULT ''                  NOT NULL COMMENT '수정자',
    deleted_at      datetime NULL COMMENT '삭제일시',
    v_activated     tinyint(1) AS (IF(deleted_at IS NULL, 1, 0)) VIRTUAL,
    CONSTRAINT uk_itemid_vactivated UNIQUE (item_id, v_activated)
) COMMENT '아이템 재고 관리' CHARSET = utf8mb4;