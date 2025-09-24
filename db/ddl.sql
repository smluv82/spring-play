-- brand table
CREATE TABLE brand
(
    id          bigint AUTO_INCREMENT
        PRIMARY KEY,
    name        varchar(50)                              NOT NULL,
    image_path  varchar(255) NULL COMMENT '이미지 경로',
    used        tinyint(1) DEFAULT 1                   NOT NULL,
    created_at  datetime     DEFAULT CURRENT_TIMESTAMP() NOT NULL,
    created_by  varchar(100) DEFAULT ''                  NOT NULL COMMENT '등록자',
    updated_at  datetime     DEFAULT CURRENT_TIMESTAMP() NOT NULL ON UPDATE CURRENT_TIMESTAMP() COMMENT '수정일시',
    updated_by  varchar(100) DEFAULT ''                  NOT NULL COMMENT '수정자',
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
    parent_category_id bigint NULL COMMENT '상위카테고리 ID',
    name               varchar(100)                             NOT NULL COMMENT '카테고리 명',
    depth              tinyint                                  NOT NULL COMMENT '현재 단계',
    used               tinyint(1)   DEFAULT 1                   NOT NULL COMMENT '사용여부',
    is_leaf            tinyint(1)   DEFAULT 0                   NULL COMMENT '리프 카테고리 여부',
    created_at         datetime     DEFAULT CURRENT_TIMESTAMP() NOT NULL COMMENT '등록일시',
    created_by         varchar(100) DEFAULT ''                  NOT NULL COMMENT '등록자',
    updated_at         datetime     DEFAULT CURRENT_TIMESTAMP() NOT NULL ON UPDATE CURRENT_TIMESTAMP() COMMENT '수정일시',
    updated_by         varchar(100) DEFAULT ''                  NOT NULL COMMENT '수정자',
    deleted_at         datetime NULL COMMENT '삭제일시'
) COMMENT '관리 카테고리' CHARSET = utf8mb4;

CREATE INDEX idx_parentcategoryid
    ON manage_category (parent_category_id);


-- product table
CREATE TABLE product
(
    id                 bigint AUTO_INCREMENT
        PRIMARY KEY,
    brand_id           bigint                                   NOT NULL COMMENT '브랜드 ID',
    manage_category_id bigint                                   NOT NULL COMMENT '관리 카테고리 ID',
    name               varchar(100)                             NOT NULL COMMENT '상품명',
    description        text NULL COMMENT '상품설명',
    model_no           varchar(100)                             NOT NULL COMMENT '모델 번호',
    used               tinyint      DEFAULT 1                   NOT NULL COMMENT '사용여부',
    main_image_path    varchar(255) NULL COMMENT '메인 이미지 path',
    created_at         datetime     DEFAULT CURRENT_TIMESTAMP() NOT NULL COMMENT '등록일시',
    created_by         varchar(100) DEFAULT ''                  NOT NULL COMMENT '등록자',
    updated_at         datetime     DEFAULT CURRENT_TIMESTAMP() NOT NULL ON UPDATE CURRENT_TIMESTAMP() COMMENT '수정일시',
    updated_by         varchar(100) DEFAULT ''                  NOT NULL COMMENT '수정자',
    deleted_at         datetime NULL COMMENT '삭제일시',
    v_activated        tinyint(1) AS (IF(deleted_at IS NULL, 1, 0)) VIRTUAL,
    CONSTRAINT uk_brandid_modelno_vactivated UNIQUE (brand_id, model_no, v_activated)
) COMMENT '상품' CHARSET = utf8mb4;


-- item table
CREATE TABLE item
(
    id              bigint AUTO_INCREMENT
        PRIMARY KEY,
    product_id      bigint                                   NOT NULL COMMENT '상품 ID',
    name            varchar(255)                             NOT NULL COMMENT '옵션명',
    sku_code        varchar(100)                             NOT NULL COMMENT '판매 단위 코드',
    option_color    varchar(50)                              NOT NULL COMMENT '색상 옵션',
    option_size     varchar(50)                              NOT NULL COMMENT '사이즈 옵션',
    description     text NULL COMMENT '상세설명',
    price           int                                      NOT NULL COMMENT '가격',
    used            tinyint      DEFAULT 1                   NOT NULL COMMENT '사용여부',
    main_image_path varchar(255) NULL COMMENT '메인 이미지 path',
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
    id                bigint AUTO_INCREMENT
        PRIMARY KEY,
    item_id           bigint                                   NOT NULL COMMENT '상품 아이템 ID',
    stock_quantity    int                                      NOT NULL COMMENT '현재 재고',
    reserved_quantity int                                      NOT NULL COMMENT '예약 재고',
    created_at        datetime     DEFAULT CURRENT_TIMESTAMP() NOT NULL COMMENT '등록일시',
    created_by        varchar(100) DEFAULT ''                  NOT NULL COMMENT '등록자',
    updated_at        datetime     DEFAULT CURRENT_TIMESTAMP() NOT NULL ON UPDATE CURRENT_TIMESTAMP() COMMENT '수정일시',
    updated_by        varchar(100) DEFAULT ''                  NOT NULL COMMENT '수정자',
    deleted_at        datetime NULL COMMENT '삭제일시',
    v_activated       tinyint(1) AS (IF(deleted_at IS NULL, 1, 0)) VIRTUAL,
    CONSTRAINT uk_itemid_vactivated UNIQUE (item_id, v_activated)
) COMMENT '아이템 재고 관리' CHARSET = utf8mb4;


CREATE TABLE play_order
(
    id          bigint AUTO_INCREMENT PRIMARY KEY,
    product_id  bigint                                   NOT NULL COMMENT '상품 ID',
    user_id     bigint                                   NOT NULL COMMENT '사용자 ID',
    total_price int                                      NOT NULL COMMENT '전체 가격',
    status      varchar(50)                              NOT NULL COMMENT '주문 상태',
    created_at  datetime     DEFAULT CURRENT_TIMESTAMP() NOT NULL COMMENT '등록일시',
    created_by  varchar(100) DEFAULT ''                  NOT NULL COMMENT '등록자',
    updated_at  datetime     DEFAULT CURRENT_TIMESTAMP() NOT NULL ON UPDATE CURRENT_TIMESTAMP() COMMENT '수정일시',
    updated_by  varchar(100) DEFAULT ''                  NOT NULL COMMENT '수정자',
    deleted_at  datetime NULL COMMENT '삭제일시'
) COMMENT '주문 정보' CHARSET = utf8mb4;


CREATE TABLE play_order_detail
(
    id         bigint AUTO_INCREMENT PRIMARY KEY,
    order_id   bigint                                   NOT NULL COMMENT '주문 ID',
    item_id    bigint                                   NOT NULL COMMENT '아이템 ID',
    quantity   int                                      NOT NULL COMMENT '수량',
    price      int                                      NOT NULL COMMENT '개별 가격',
    created_at datetime     DEFAULT CURRENT_TIMESTAMP() NOT NULL COMMENT '등록일시',
    created_by varchar(100) DEFAULT ''                  NOT NULL COMMENT '등록자',
    updated_at datetime     DEFAULT CURRENT_TIMESTAMP() NOT NULL ON UPDATE CURRENT_TIMESTAMP() COMMENT '수정일시',
    updated_by varchar(100) DEFAULT ''                  NOT NULL COMMENT '수정자',
    deleted_at datetime NULL COMMENT '삭제일시'
) COMMENT '주문 상세 정보' CHARSET = utf8mb4;

CREATE TABLE play_reward_campaign
(
    id                 bigint AUTO_INCREMENT PRIMARY KEY,
    name               varchar(100)                             NOT NULL COMMENT '캠페인명',
    status             varchar(50)                              NOT NULL COMMENT '상태 (예정, 시작, 임시중단, 종료)',
    reward_type        varchar(50)                              NOT NULL COMMENT '리워드 타입',
    provide_type       varchar(50)                              NOT NULL COMMENT '지급 타입 (실시간, 배치)',
    provide_date       date         DEFAULT NULL COMMENT '지급 희망일 (배치인 경우에만)',
    unit_type          varchar(50)                              NOT NULL COMMENT '적립 유형 (A: 정액, P: 정률)',
    unit_value         int(11) NOT NULL COMMENT '정액 금액, 정률 퍼센트',
    percent_max_amount int(11) NULL COMMENT '정률일 경우 최대 적립 금액',
    expiry_type        varchar(50)                              NOT NULL COMMENT '유효기간 타입 (RELATIVE:유효기한(일), ABSOLUTE:유효기간 만료일)',
    expiry_days        int(11) DEFAULT NULL COMMENT '유효기한 (일)',
    expiry_date        date         DEFAULT NULL COMMENT '유효기간 만료일',
    start_at           datetime                                 NOT NULL COMMENT '시작일',
    end_at             datetime                                 NOT NULL COMMENT '종료일',
    duplicated         tinyint(1) NOT NULL COMMENT '중복 지급 가능 여부',
    created_at         datetime     DEFAULT CURRENT_TIMESTAMP() NOT NULL COMMENT '등록일시',
    created_by         varchar(100) DEFAULT ''                  NOT NULL COMMENT '등록자',
    updated_at         datetime     DEFAULT CURRENT_TIMESTAMP() NOT NULL ON UPDATE CURRENT_TIMESTAMP() COMMENT '수정일시',
    updated_by         varchar(100) DEFAULT ''                  NOT NULL COMMENT '수정자',
    deleted_at         datetime NULL COMMENT '삭제일시'
) COMMENT '리워드 캠페인' CHARSET = utf8mb4;


CREATE TABLE play_reward_user
(
    id       bigint AUTO_INCREMENT PRIMARY KEY,
    reward_campaign_id  bigint NOT NULL COMMENT '리워드 캠페인 ID',
    order_id bigint      NOT NULL COMMENT '주문 ID',
    amount   int(11) NOT NULL COMMENT '지급/차감 금액 (지급 + , 차감 -)',
    provide_status   varchar(50) NOT NULL COMMENT '상태 (WAIT, SUCCESS, FAIL, CANCEL)',
    provide_date  date NOT NULL COMMENT '지급 희망일',
    expiry_at     datetime NOT NULL COMMENT '유효기간',
    created_at         datetime     DEFAULT CURRENT_TIMESTAMP() NOT NULL COMMENT '등록일시',
    created_by         varchar(100) DEFAULT ''                  NOT NULL COMMENT '등록자',
    updated_at         datetime     DEFAULT CURRENT_TIMESTAMP() NOT NULL ON UPDATE CURRENT_TIMESTAMP() COMMENT '수정일시',
    updated_by         varchar(100) DEFAULT ''                  NOT NULL COMMENT '수정자',
    deleted_at         datetime NULL COMMENT '삭제일시'
) COMMENT '리워드 사용자' CHARSET = utf8mb4;