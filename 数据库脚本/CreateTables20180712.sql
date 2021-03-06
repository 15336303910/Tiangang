
DROP TABLE IF EXISTS company_info cascade;
CREATE TABLE company_info (
    `company_id` varchar(32) NOT NULL  COMMENT '公司ＩＤ：５位定长，从００００１开始，最大９９９９９',
    `rowno` varchar(32) NOT NULL  COMMENT '公司ＩＤ２：同ｃｏｍｐａｎｙ＿ｉｄ',
    `company_name` varchar(32) NOT NULL  COMMENT '公司名称',
    `super_company_id` varchar(16)  COMMENT '上级公司ＩＤ',
    `address` varchar(256)  COMMENT '详细地址',
    `company_nature` varchar(64)  COMMENT '公司性质：国营、私营、公私合营、股份制、事业',
    `company_summary` varchar(256)  COMMENT '公司简介',
    `tel` varchar(16)  COMMENT '业务电话',
    `service_tel` varchar(16)  COMMENT '服务热线',
    `email` varchar(128)  COMMENT '邮箱',
    `url` varchar(128)  COMMENT '网址',
    `company_type` varchar(8)  COMMENT '公司类型：总公司，一级子公司，二级子公司',
    `longitude` varchar(32)  COMMENT '经度',
    `latitude` varchar(32)  COMMENT '纬度',
    `notes` varchar(256)  COMMENT '备注',
    `memo1` varchar(32)  COMMENT '保留１',
    `memo2` varchar(32)  COMMENT '保留２',
    `create_date` datetime  COMMENT '创建时间',
    `create_user` varchar(32)  COMMENT '创建者',
    `update_date` datetime  COMMENT '更新时间',
    `update_user` varchar(32)  COMMENT '更新者',
    `industry` varchar(32)  COMMENT '所属行业：热力ＲＬ、水务ＳＷ、热力和水务ＲＳ',
    `proviance` varchar(32)  COMMENT '省',
    `city` varchar(32)  COMMENT '市',
    `area` varchar(32)  COMMENT '区',
    PRIMARY KEY (`company_id`)
);

DROP TABLE IF EXISTS producer_info cascade;
CREATE TABLE producer_info (
    `producer_id` varchar(32) NOT NULL  COMMENT '源ＩＤ：从００００１开始，公司下采番',
    `producer_name` varchar(128) NOT NULL  COMMENT '源名称',
    `super_producer_id` varchar(32)  COMMENT '隶属源ＩＤ',
    `company_id` varchar(32) NOT NULL  COMMENT '公司ＩＤ',
    `total_load` decimal(18,2)  COMMENT '供应能力',
    `producer_info` varchar(256)  COMMENT '设备配置',
    `area` decimal(18,2)  COMMENT '规划面积',
    `length` decimal(18,2)  COMMENT '管网长度',
    `longitude` varchar(32)  COMMENT '经度',
    `latitude` varchar(32)  COMMENT '纬度',
    `height` decimal(18,2)  COMMENT '标高',
    `department_id` varchar(32)  COMMENT '负责部门ＩＤ',
    `charge_person` varchar(50)  COMMENT '负责人名',
    `notes` varchar(256)  COMMENT '备注',
    `memo1` varchar(32)  COMMENT '保留１',
    `memo2` varchar(32)  COMMENT '保留２',
    `create_date` datetime  COMMENT '创建时间',
    `create_user` varchar(32)  COMMENT '创建者',
    `update_date` datetime  COMMENT '更新时间',
    `update_user` varchar(32)  COMMENT '更新者',
    `producer_type_id` varchar(32)  COMMENT '源类型：水厂、买水，区域高温锅炉、区域高温锅炉、热电联产首站、空气源热泵、燃气锅炉、地源热泵、海水热泵、混合热源等',
    `business_type` varchar(32)  COMMENT '类型标志：水源、热源',
    PRIMARY KEY (`producer_id`)
);

DROP TABLE IF EXISTS pipe_info cascade;
CREATE TABLE pipe_info (
    `pipe_type_id` varchar(32) NOT NULL  COMMENT '管线类别ＩＤ',
    `pipe_type_name` varchar(32) NOT NULL  COMMENT '管线类别名称：北线、西线、东南线、１号线等',
    `company_id` varchar(32) NOT NULL  COMMENT '公司ＩＤ',
    `pipe_source_id` varchar(32)  COMMENT '管线归属ＩＤ：热源ＩＤ、热力站ＩＤ',
    `pipe_length` int  COMMENT '管线长度',
    `notes` varchar(256)  COMMENT '备注',
    `memo1` varchar(32)  COMMENT '保留１',
    `memo2` varchar(32)  COMMENT '保留２',
    `create_date` DateTime  COMMENT '创建时间',
    `create_user` varchar(32)  COMMENT '创建者',
    `update_date` DateTime  COMMENT '更新时间',
    `update_user` varchar(32)  COMMENT '更新者',
    PRIMARY KEY (`pipe_type_id`)
);

DROP TABLE IF EXISTS station_info cascade;
CREATE TABLE station_info (
    `station_id` varchar(32) NOT NULL  COMMENT '站ＩＤ',
    `company_id` varchar(32) NOT NULL  COMMENT '公司ＩＤ',
    `station_name` varchar(64) NOT NULL  COMMENT '站名称',
    `producer_id` varchar(32) NOT NULL  COMMENT '源ＩＤ',
    `pipe_type_id` varchar(32)  COMMENT '管线类别ＩＤ：取自管线类别中的ＩＤ信息',
    `station_type_id` varchar(32) NOT NULL  COMMENT '站类型ＩＤ',
    `system_num` int  COMMENT '系统数',
    `plan_area` decimal(18,2)  COMMENT '规划面积',
    `address` varchar(256)  COMMENT '详细地址',
    `department_id` varchar(32)  COMMENT '负责部门ＩＤ',
    `charge_person` varchar(50)  COMMENT '负责人名',
    `primary_in_temperature` decimal(8, 3)  COMMENT '一次网设计供温',
    `primary_out_temperature` decimal(8, 3)  COMMENT '一次网设计回温',
    `outdoor_set_temperature` decimal(8, 3)  COMMENT '室外设计温度',
    `room_set_temperature` decimal(8, 3)  COMMENT '室内设计温度',
    `heating_index` decimal(8,3)  COMMENT '设计热指标',
    `producer_distance` decimal(18,2)  COMMENT '距源距离（ｍ）',
    `height` decimal(8,2)  COMMENT '标高',
    `longitude` varchar(32)  COMMENT '经度',
    `latitude` varchar(32)  COMMENT '纬度',
    `notes` varchar(256)  COMMENT '备注',
    `memo1` varchar(32)  COMMENT '保留１',
    `memo2` varchar(32)  COMMENT '保留２',
    `create_date` DateTime  COMMENT '创建时间',
    `create_user` varchar(32)  COMMENT '创建者',
    `update_date` DateTime  COMMENT '更新时间',
    `update_user` varchar(32)  COMMENT '更新者',
    `business_type` varchar(32)  COMMENT '类型标志：水站、热力站',
    `water_plan` decimal(8, 3)  COMMENT '规划供水量',
    `water_act` decimal(8, 3)  COMMENT '实际供水量',
    `water_design` decimal(8, 3)  COMMENT '设计供水指标',
    `pipe_info` varchar(32)  COMMENT '对应的管线信息：济南',
    `city` varchar(64)  COMMENT '所在城市：获取天气使用',
    `index` int  COMMENT '显示排序：默认９９９９',
    `hpump_num` int  COMMENT '热泵数量',
    PRIMARY KEY (`station_id`)
);

DROP TABLE IF EXISTS heating_area_change cascade;
CREATE TABLE heating_area_change (
    `primary_id` varchar(32) NOT NULL  COMMENT '主键',
    `station_id` varchar(32) NOT NULL  COMMENT '热力站ＩＤ',
    `system_id` varchar(32) NOT NULL  COMMENT '系统ＩＤ',
    `year` varchar(8) NOT NULL  COMMENT '采暖年度',
    `heat_area` decimal(18,2)  COMMENT '供热面积',
    `start_time` datetime  COMMENT '起始时间',
    `end_time` datetime  COMMENT '结束时间',
    `company_id` varchar(32) NOT NULL  COMMENT '公司ＩＤ',
    `notes` varchar(256)  COMMENT '备注',
    `memo1` varchar(32)  COMMENT '保留１',
    `memo2` varchar(32)  COMMENT '保留２',
    `create_date` datetime  COMMENT '创建时间',
    `create_user` varchar(32)  COMMENT '创建者',
    `update_date` datetime  COMMENT '更新时间',
    `update_user` varchar(32)  COMMENT '更新者',
    PRIMARY KEY (`primary_id`)
);

DROP TABLE IF EXISTS system_info cascade;
CREATE TABLE system_info (
    `system_id` varchar(32) NOT NULL  COMMENT '分系统ＩＤ：６位定长，从０００００１开始',
    `system_name` varchar(64) NOT NULL  COMMENT '分系统名称',
    `plan_area` decimal(18, 2)  COMMENT '规划面积',
    `second_pipe_farthest` decimal(18, 2)  COMMENT '二次管线最远距离（ｍ）',
    `second_in_temperature` decimal(8, 3)  COMMENT '二次网设计供温',
    `second_out_temperature` decimal(8, 3)  COMMENT '二次网设计回温',
    `outdoor_set_temperature` decimal(8, 3)  COMMENT '室外设计温度',
    `room_set_temperature` decimal(8, 3)  COMMENT '室内设计温度',
    `heating_index` decimal(8, 3)  COMMENT '设计热指标',
    `company_id` varchar(32) NOT NULL  COMMENT '公司ＩＤ',
    `station_id` varchar(32) NOT NULL  COMMENT '换热站ＩＤ',
    `notes` varchar(256)  COMMENT '备注',
    `memo1` varchar(32)  COMMENT '保留１',
    `memo2` varchar(32)  COMMENT '保留２',
    `create_date` datetime  COMMENT '创建时间',
    `create_user` varchar(32)  COMMENT '创建者',
    `update_date` datetime  COMMENT '更新时间',
    `update_user` varchar(32)  COMMENT '更新者',
    `business_type` varchar(32)  COMMENT '类型标志：水站、换热站',
    `water_plan` decimal(8, 3)  COMMENT '规划供水量',
    `water_act` decimal(8, 3)  COMMENT '实际供水量',
    `water_design` decimal(8, 3)  COMMENT '设计供水指标',
    `cpump_num` decimal(8, 3)  COMMENT '循环泵数量',
    `rpump_num` decimal(8, 3)  COMMENT '补水泵数量',
    `cconv_num` decimal(8, 3)  COMMENT '循环泵变频器数量',
    `rconv_num` decimal(8, 3)  COMMENT '补水泵变频器数量',
    PRIMARY KEY (`system_id`)
);

DROP TABLE IF EXISTS sewage_station_info cascade;
CREATE TABLE sewage_station_info (
    `company_id` varchar(32) NOT NULL  COMMENT '公司ＩＤ',
    `sewage_station_id` varchar(32) NOT NULL  COMMENT '污水站ＩＤ',
    `sewage_station_name` varchar(50) NOT NULL  COMMENT '污水站名称',
    `sewage_station_size` varchar(2)  COMMENT '污水站类型',
    `address` varchar(256)  COMMENT '详细地址',
    `tel` varchar(16)  COMMENT '业务联系电话',
    `plan_poll_dis` decimal(8, 2)  COMMENT '规划排污能力',
    `actual_poll_dis` decimal(8, 2)  COMMENT '实际排污能力',
    `design_poll_dis` decimal(8, 2)  COMMENT '设计排污能力',
    `longitude` varchar(32)  COMMENT '经度',
    `latitude` varchar(32)  COMMENT '纬度',
    `notes` varchar(255)  COMMENT '备注',
    `memo1` varchar(32)  COMMENT '保留１',
    `memo2` varchar(32)  COMMENT '保留２',
    `create_date` datetime  COMMENT '创建时间',
    `create_user` varchar(32)  COMMENT '创建者',
    `update_date` datetime  COMMENT '更新时间',
    `update_user` varchar(32)  COMMENT '更新者',
    `index` int  COMMENT '显示排序：默认９９９９',
    PRIMARY KEY (`sewage_station_id`)
);

DROP TABLE IF EXISTS commuity_info cascade;
CREATE TABLE commuity_info (
    `commuity_id` varchar(32) NOT NULL  COMMENT '地址ＩＤ：１０位定长，热力公司＋地址名称，地址名称从００００１开始',
    `rowno` varchar(32) NOT NULL  COMMENT '地址ＩＤ２：ＵＵＩＤ',
    `company_id` varchar(32) NOT NULL  COMMENT '公司ＩＤ',
    `station_id` varchar(32) NOT NULL  COMMENT '站ＩＤ',
    `commuity_name` varchar(64)  COMMENT '地址名称：小区和道路名称',
    `property` varchar(32)  COMMENT '物业公司',
    `tel` varchar(32)  COMMENT '物业电话',
    `charge_person` varchar(32)  COMMENT '物业联系人',
    `commuity_area` varchar(64)  COMMENT '占地面积',
    `build_num` int  COMMENT '楼宇数量',
    `unit_num` int  COMMENT '单元数量',
    `address` varchar(256)  COMMENT '坐落位置',
    `build_year` varchar(16)  COMMENT '建设时间',
    `longitude` varchar(32)  COMMENT '经度',
    `latitude` varchar(32)  COMMENT '纬度',
    `notes` varchar(256)  COMMENT '备注',
    `memo1` varchar(32)  COMMENT '保留１',
    `memo2` varchar(32)  COMMENT '保留２',
    `create_date` DateTime  COMMENT '创建时间',
    `create_user` varchar(32)  COMMENT '创建者',
    `update_date` DateTime  COMMENT '更新时间',
    `update_user` varchar(32)  COMMENT '更新者',
    `has_build_meter` varchar(32)  COMMENT '水－小区表安装：无，水表，流量计',
    `cbzq` int  COMMENT '抄表周期（月）',
    `xqzt` varchar(8)  COMMENT '小区状态（０１：有效，０２：无效）',
    `index` int  COMMENT '显示排序：默认９９９９',
    PRIMARY KEY (`commuity_id`)
);

DROP TABLE IF EXISTS build_info cascade;
CREATE TABLE build_info (
    `building_no` varchar(32) NOT NULL  COMMENT '建筑物ＩＤ：３位在小区（地址名称）下采番',
    `rowno` varchar(32) NOT NULL  COMMENT '建筑物ＩＤ２：３位在小区（地址名称）下采番',
    `building_name` varchar(32)  COMMENT '楼号',
    `unit_num` varchar(32)  COMMENT '单元数量',
    `floor_num` varchar(32)  COMMENT '楼层数量',
    `floor_height` int  COMMENT '层高',
    `building_area` decimal(18, 2)  COMMENT '建筑面积：本楼各单元面积之和',
    `energy_saving` varchar(8)  COMMENT '节能建筑：是／否',
    `preserve_heat` varchar(16)  COMMENT '保温情况：保温／非保温',
    `begin_pipe_id` varchar(32)  COMMENT '管线类别ＩＤ：取自管线类别中的ＩＤ信息',
    `pipe_id` varchar(128)  COMMENT '管线号ＩＤ：如果是多系统，允许多个ＩＤ，之间用逗号隔开',
    `main_diameter` int  COMMENT '热－总管管径',
    `pipe_position` varchar(128)  COMMENT '热－管网位置',
    `pipe_length` int  COMMENT '热－管线长度：近端、中端、远端',
    `heating_index` decimal(8, 2)  COMMENT '设计热指标',
    `heating_form` varchar(32)  COMMENT '热－楼内系统形式：上供下回串联、上供下回并联、下供下回并联等',
    `build_year` varchar(8)  COMMENT '建成时间',
    `builder` varchar(64)  COMMENT '建筑商',
    `net_in_time` datetime  COMMENT '热－入网时间',
    `net_in_contract` varchar(32)  COMMENT '热－入网合同号',
    `has_build_meter` varchar(32)  COMMENT '栋表安装：无，热量表，流量计',
    `has_balance_valve` varchar(32)  COMMENT '平衡阀安装：无、静态平衡阀、自力式流量平衡阀、自力式压差平衡阀、智能平衡阀等',
    `charge_person` varchar(50)  COMMENT '负责人名',
    `attributes` varchar(8)  COMMENT '使用属性：公建、居民、混建等',
    `building_right` varchar(64)  COMMENT '房权单位',
    `using_right` varchar(64)  COMMENT '使用单位',
    `system_num` int  COMMENT '系统数',
    `rest_time` varchar(32)  COMMENT '供暖作息时间：下班后无需供热、或全天供热等（主要对公建建筑标注）',
    `longitude` varchar(32)  COMMENT '经度',
    `latitude` varchar(32)  COMMENT '纬度',
    `company_id` varchar(32) NOT NULL  COMMENT '公司ＩＤ',
    `address` varchar(256)  COMMENT '地址全称：小区名＋楼名',
    `notes` varchar(256)  COMMENT '备注',
    `memo1` varchar(32)  COMMENT '保留１',
    `memo2` varchar(32)  COMMENT '保留２',
    `create_date` datetime  COMMENT '创建时间',
    `create_user` varchar(32)  COMMENT '创建者',
    `update_date` datetime  COMMENT '更新时间',
    `update_user` varchar(32)  COMMENT '更新者',
    `control_type` varchar(32)  COMMENT '控制方式：单元控制、整楼控制、分户控制、分层控制、混合控制等（设备用，收费用）',
    `water_preserve_heat` varchar(16)  COMMENT '水－保温情况：水管是否保温／非保温',
    `water_pipe_id` varchar(128)  COMMENT '水－管线号ＩＤ：如果是多系统，允许多个ＩＤ，之间用逗号隔开',
    `water_main_diameter` int  COMMENT '水－总管管径',
    `water_pipe_position` varchar(128)  COMMENT '水－管网位置',
    `water_pipe_length` int  COMMENT '水－管线长度',
    `water_index` decimal(8, 2)  COMMENT '水－设计供水指标',
    `water_form` varchar(32)  COMMENT '水－楼内系统形式',
    `water_net_in_time` datetime  COMMENT '水－入网时间',
    `water_net_in_contract` varchar(32)  COMMENT '水－入网合同号',
    `water_has_build_meter` varchar(32)  COMMENT '水－栋表安装：无，水表，流量计',
    `index` int  COMMENT '显示排序：默认９９９９',
    `isvalid` int  COMMENT '有效标志，０表明删除',
    PRIMARY KEY (`building_no`)
);

DROP TABLE IF EXISTS unit_info cascade;
CREATE TABLE unit_info (
    `unit_id` varchar(32) NOT NULL  COMMENT '单元ＩＤ　　唯一识别号：建筑物信息＋单元，单元信息从０１开始，１５位ＩＤ',
    `rowno` varchar(32) NOT NULL  COMMENT '单元ＩＤ２：建筑物信息＋单元，单元信息从０１开始，１５位ＩＤ',
    `building_id` varchar(32) NOT NULL  COMMENT '建筑物ＩＤ',
    `unit_name` varchar(32)  COMMENT '单元名称：原则为东１单元、东２单元、南１单元',
    `building_area` decimal(18, 2)  COMMENT '建筑面积：本单元面积之和，可以人工修改。',
    `diameter` int  COMMENT '单元管径',
    `control_type` varchar(16)  COMMENT '控制方式：楼总阀、单元总阀、分户',
    `floor_num` varchar(128)  COMMENT '楼层数',
    `floor_house` decimal(8, 2)  COMMENT '每层户数',
    `heating_form` varchar(128)  COMMENT '采暖形式：地暖、挂暖等',
    `has_build_meter` decimal(8, 2)  COMMENT '栋表安装：无，热量表，流量计',
    `has_balance_valve` decimal(8, 2)  COMMENT '平衡阀安装：无、静态平衡阀、自力式流量平衡阀、自力式压差平衡阀、智能平衡阀等',
    `company_id` varchar(32) NOT NULL  COMMENT '公司ＩＤ',
    `address` varchar(256)  COMMENT '地址全称：小区名＋楼名＋单元名＋楼名＋单元名',
    `notes` varchar(256)  COMMENT '备注',
    `memo1` varchar(32)  COMMENT '保留１',
    `memo2` varchar(32)  COMMENT '保留２',
    `create_date` datetime  COMMENT '创建时间',
    `create_user` varchar(32)  COMMENT '创建者',
    `update_date` datetime  COMMENT '更新时间',
    `update_user` varchar(32)  COMMENT '更新者',
    `water_control_type` varchar(32)  COMMENT '控制方式：单元控制、分户控制',
    `water_has_build_meter` decimal(8, 2)  COMMENT '栋表安装：无，水表，流量计',
    `index` int  COMMENT '显示排序：默认９９９９',
    `isvalid` int  COMMENT '有效标志，０表明删除',
    PRIMARY KEY (`unit_id`)
);

DROP TABLE IF EXISTS houses_info cascade;
CREATE TABLE houses_info (
    `consumer_id` varchar(32) NOT NULL  COMMENT '用户ＩＤ　唯一识别号：单元信息＋用户信息，用户信息从０００１开始　１９位定长',
    `rowno` varchar(32) NOT NULL  COMMENT '用户ＩＤ２：单元信息＋用户信息，用户信息从０００１开始　１９位定长',
    `room_name` varchar(32)  COMMENT '室名',
    `system_id` varchar(32)  COMMENT '热力站分系统ＩＤ',
    `building_area` decimal(8, 2)  COMMENT '建筑面积',
    `in_build_area` decimal(8, 2)  COMMENT '套内建筑面积',
    `user_build_area` decimal(8, 2)  COMMENT '实用面积',
    `contract_heat_id` varchar(32)  COMMENT '供热合同ＩＤ',
    `heating_area` decimal(8, 2) NOT NULL  COMMENT '供热面积：可配置等于用户信息表中的建筑面积、套内建筑面积、使用面积及修正公式，自动生成',
    `charge_type` varchar(32)  COMMENT '供热收费类型ＩＤ：面积收费、二部制收费、热计量收费',
    `unit_price_type` varchar(32)  COMMENT '热费单价类型ＩＤ',
    `heat_user_type` varchar(32)  COMMENT '供热用户类型ＩＤ：低保、事业、政府、工厂、门市、写字楼、学校、居民等',
    `charge_area` decimal(8, 2)  COMMENT '收费面积：可配置等于用户信息表中的建筑面积、套内建筑面积、使用面积及修正公式，自动生成',
    `name` varchar(32)  COMMENT '用户姓名',
    `email` varchar(64)  COMMENT '邮箱',
    `user_company` varchar(128)  COMMENT '单位名称',
    `tel` varchar(32)  COMMENT '联系电话',
    `heating_form` varchar(32)  COMMENT '室内暖气：地暖、片暖、风机盘管等',
    `net_status` varchar(32)  COMMENT '入网状态：未入网／入网',
    `heat_exchange_status` varchar(8)  COMMENT '换热器安装：有、没有',
    `issample` varchar(8)  COMMENT '流量样本',
    `house_type` varchar(8)  COMMENT '采暖户型：边户、顶（底）户、边顶（底）户、中间户等',
    `diameter` varchar(32)  COMMENT '用户管径',
    `has_heat_meter` decimal(10, 2)  COMMENT '热量表安装',
    `has_valve` decimal(10, 3)  COMMENT '温控阀安装',
    `house_structure_id` varchar(128)  COMMENT '房屋结构ＩＤ：可链接扫描图片',
    `charge_id` varchar(64)  COMMENT '收费ＩＤ',
    `company_id` varchar(32) NOT NULL  COMMENT '公司ＩＤ',
    `sec_pipe_id` varchar(32)  COMMENT '二次管线ＩＤ',
    `address` varchar(256)  COMMENT '地址全称：小区名＋楼名＋单元名＋楼名＋单元名＋室名',
    `design_heat_target` decimal(10, 2)  COMMENT '设计热指标',
    `notes` varchar(256)  COMMENT '备注',
    `memo1` varchar(32)  COMMENT '保留１',
    `memo2` varchar(32)  COMMENT '保留２',
    `memo3` varchar(32)  COMMENT '保留３',
    `memo4` varchar(32)  COMMENT '保留４',
    `memo5` varchar(32)  COMMENT '保留５',
    `create_date` datetime  COMMENT '创建时间',
    `create_user` varchar(32)  COMMENT '创建者',
    `update_date` datetime  COMMENT '更新时间',
    `update_user` varchar(32)  COMMENT '更新者',
    `floor_high` varchar(32)  COMMENT '层高',
    `idcard` varchar(32)  COMMENT '身份证信息',
    `passwod` varchar(32)  COMMENT '登录密码',
    `person_sum` decimal(8, 2)  COMMENT '人口数',
    `person_type` varchar(32)  COMMENT '客户类型',
    `color_no` varchar(32)  COMMENT '集收号',
    `personcard_id` varchar(32)  COMMENT '统一社会信用代码',
    `lowinsure_name` varchar(32)  COMMENT '低保姓名',
    `lowinsure_no` varchar(32)  COMMENT '低保证号',
    `water_type` varchar(32)  COMMENT '用水性质：居民、非居民',
    `open_acc_type` varchar(32)  COMMENT '开账分类',
    `tot_meter_clientno` varchar(32)  COMMENT '总表客户号',
    `step_begin` datetime  COMMENT '阶梯开始日期',
    `step_end` datetime  COMMENT '阶梯结束日期',
    `step_adj_type` varchar(32)  COMMENT '阶梯日期调整方式（０１：自动，０２：手动）',
    `contry_factor_begin` datetime  COMMENT '缴费系数开始日期',
    `contry_factor_end` datetime  COMMENT '缴费系数结束日期',
    `contry_factor` varchar(32)  COMMENT '缴费系数',
    `contry_type` varchar(32)  COMMENT '催缴方式',
    `contry_person` varchar(32)  COMMENT '催缴员',
    `contry_stat` varchar(8)  COMMENT '状态：（０１：正常，０２：抄表不收费，０３：注销，０４：不抄表，０５：销户）',
    `destroy_date` datetime  COMMENT '销户日期',
    `approve_state` varchar(32)  COMMENT '审批状态',
    `water_state` varchar(32)  COMMENT '供水状态',
    `contry_id` varchar(32)  COMMENT '供水收费ｉｄ',
    `house_direct` varchar(32)  COMMENT '房屋方向：阳面、阴面　　济南',
    `house_key` varchar(32)  COMMENT '用户密码：收费登录用',
    `serviceman` varchar(50)  COMMENT '维修负责人',
    `isvalid` int  COMMENT '有效标志，０表明删除',
    PRIMARY KEY (`consumer_id`)
);

DROP TABLE IF EXISTS heating_time cascade;
CREATE TABLE heating_time (
    `primary_id` varchar(32) NOT NULL  COMMENT '主键',
    `station_id` varchar(32) NOT NULL  COMMENT '供热主体ＩＤ：记录的是热力公司ＩＤ和热力站ＩＤ，可混合记录。',
    `year` varchar(32) NOT NULL  COMMENT '采暖年度',
    `start_time` datetime NOT NULL  COMMENT '供暖起始时间：放在用户年度采暖信息表，再讨论，年度信息变更的时候，需要在这里取',
    `end_time` datetime NOT NULL  COMMENT '供暖结束时间：放在用户年度采暖信息表，再讨论，年度信息变更的时候，需要在这里取',
    `company_id` varchar(32) NOT NULL  COMMENT '公司ＩＤ',
    `rowno` varchar(32) NOT NULL  COMMENT '唯一识别号：与框架对应的主键',
    `notes` varchar(256)  COMMENT '备注',
    `memo1` varchar(32)  COMMENT '保留１',
    `memo2` varchar(32)  COMMENT '保留２',
    `create_date` datetime  COMMENT '创建时间',
    `create_user` varchar(32)  COMMENT '创建者',
    `update_date` datetime  COMMENT '更新时间',
    `update_user` varchar(32)  COMMENT '更新者',
    PRIMARY KEY (`primary_id`)
);

DROP TABLE IF EXISTS department_info cascade;
CREATE TABLE department_info (
    `department_id` varchar(32) NOT NULL  COMMENT '公司部门ＩＤ',
    `department_name` varchar(4) NOT NULL  COMMENT '部门名称',
    `company_id` varchar(32) NOT NULL  COMMENT '公司ＩＤ',
    `super_department_id` varchar(32)  COMMENT '隶属部门ＩＤ',
    `department_charge` varchar(256)  COMMENT '部门职责',
    `charge_person` varchar(32)  COMMENT '部门负责人',
    `notes` varchar(256)  COMMENT '备注',
    `memo1` varchar(32)  COMMENT '保留１',
    `memo2` varchar(32)  COMMENT '保留２',
    `create_date` datetime  COMMENT '创建时间',
    `create_user` varchar(32)  COMMENT '创建者',
    `update_date` datetime  COMMENT '更新时间',
    `update_user` varchar(32)  COMMENT '更新者',
    PRIMARY KEY (`department_id`)
);

DROP TABLE IF EXISTS staff_info cascade;
CREATE TABLE staff_info (
    `staff_id` varchar(32) NOT NULL  COMMENT '员工ＩＤ',
    `staff_name` varchar(32) NOT NULL  COMMENT '员工姓名',
    `department_id` varchar(32) NOT NULL  COMMENT '公司部门ＩＤ',
    `tel1` varchar(32)  COMMENT '联系电话１',
    `tel2` varchar(32)  COMMENT '联系电话２',
    `duty` varchar(256)  COMMENT '职务',
    `home_address` varchar(128)  COMMENT '家庭住址',
    `tel3` varchar(32)  COMMENT '应急电话',
    `notes` varchar(256)  COMMENT '备注',
    `memo1` varchar(32)  COMMENT '保留１',
    `memo2` varchar(32)  COMMENT '保留２',
    `create_date` datetime  COMMENT '创建时间',
    `create_user` varchar(32)  COMMENT '创建者',
    `update_date` datetime  COMMENT '更新时间',
    `update_user` varchar(32)  COMMENT '更新者',
    PRIMARY KEY (`staff_id`)
);

DROP TABLE IF EXISTS weather_info cascade;
CREATE TABLE weather_info (
    `weather_id` varchar(32) NOT NULL  COMMENT '天气代码ＩＤ',
    `city` varchar(32) NOT NULL  COMMENT '行政地址',
    `notes` varchar(256)  COMMENT '备注',
    `memo1` varchar(32)  COMMENT '保留１',
    `memo2` varchar(32)  COMMENT '保留２',
    `create_date` datetime  COMMENT '创建时间',
    `create_user` varchar(32)  COMMENT '创建者',
    `update_date` datetime  COMMENT '更新时间',
    `update_user` varchar(32)  COMMENT '更新者',
    PRIMARY KEY (`weather_id`)
);

DROP TABLE IF EXISTS weather_data_now cascade;
CREATE TABLE weather_data_now (
    `city` varchar(32) NOT NULL  COMMENT '城市',
    `read_time` decimal(18,2)  COMMENT '时间',
    `temperature` decimal(8,3)  COMMENT '温度',
    `weather` DateTime  COMMENT '天气',
    `spead` varchar(32)  COMMENT '风速',
    `light` varchar(256)  COMMENT '光照',
    `humidity` varchar(32)  COMMENT '湿度',
    `notes` varchar(32)  COMMENT '备注',
    `create_date` DateTime  COMMENT '创建时间',
    `create_user` varchar(32)  COMMENT '创建者',
    `update_date` DateTime  COMMENT '更新时间',
    `update_user` varchar(32)  COMMENT '更新者',
    PRIMARY KEY (`city`)
);

DROP TABLE IF EXISTS weather_data cascade;
CREATE TABLE weather_data (
    `primary_id` varchar(32) NOT NULL  COMMENT '记录序号：采番',
    `city` varchar(32) NOT NULL  COMMENT '城市',
    `read_time` datetime  COMMENT '时间',
    `temperature` decimal(8,2)  COMMENT '温度',
    `weather` decimal(8,2)  COMMENT '天气',
    `spead` decimal(8,2)  COMMENT '风速',
    `light` decimal(8,2)  COMMENT '光照',
    `humidity` decimal(8,2)  COMMENT '湿度',
    `notes` varchar(32)  COMMENT '备注',
    `create_date` DateTime  COMMENT '创建时间',
    `create_user` varchar(32)  COMMENT '创建者',
    `update_date` DateTime  COMMENT '更新时间',
    `update_user` varchar(32)  COMMENT '更新者',
    PRIMARY KEY (`primary_id`)
);

DROP TABLE IF EXISTS weather_data2 cascade;
CREATE TABLE weather_data2 (
    `primary_id` varchar(32) NOT NULL  COMMENT '记录序号：采番',
    `city` varchar(32) NOT NULL  COMMENT '城市',
    `read_time` datetime  COMMENT '时间',
    `temperature_highest` decimal(8,2)  COMMENT '实际最高温度',
    `temperature_lowest` decimal(8,2)  COMMENT '实际最低温度',
    `temperature_avg` decimal(8,2)  COMMENT '平均温度',
    `notes` varchar(32)  COMMENT '备注',
    `create_date` DateTime  COMMENT '创建时间',
    `create_user` varchar(32)  COMMENT '创建者',
    `update_date` DateTime  COMMENT '更新时间',
    `update_user` varchar(32)  COMMENT '更新者',
    PRIMARY KEY (`primary_id`)
);

DROP TABLE IF EXISTS weather_fur_data cascade;
CREATE TABLE weather_fur_data (
    `primary_id` varchar(32) NOT NULL  COMMENT '记录序号',
    `city` varchar(32) NOT NULL  COMMENT '城市',
    `read_time` decimal(18,2)  COMMENT '时间',
    `temperature` varchar(255)  COMMENT '温度：未来三天的值按照ｊｓｏｎ方式存储（７２个）',
    `weather` varchar(255)  COMMENT '天气：未来三天的值按照ｊｓｏｎ方式存储（７２个）',
    `spead` varchar(255)  COMMENT '风速：未来三天的值按照ｊｓｏｎ方式存储（７２个）',
    `light` varchar(255)  COMMENT '光照：未来三天的值按照ｊｓｏｎ方式存储（７２个）',
    `humidity` varchar(255)  COMMENT '湿度：未来三天的值按照ｊｓｏｎ方式存储（７２个）',
    `notes` varchar(32)  COMMENT '备注',
    `create_date` DateTime  COMMENT '创建时间',
    `create_user` varchar(32)  COMMENT '创建者',
    `update_date` DateTime  COMMENT '更新时间',
    `update_user` varchar(32)  COMMENT '更新者',
    PRIMARY KEY (`primary_id`)
);

DROP TABLE IF EXISTS type_mst cascade;
CREATE TABLE type_mst (
    `id` varchar(32) NOT NULL  COMMENT 'ｉｄ',
    `type_kbn` varchar(32) NOT NULL  COMMENT '类型区分',
    `type_kbn_name` varchar(64) NOT NULL  COMMENT '类型名',
    `type_id` varchar(32) NOT NULL  COMMENT '类型ＩＤ',
    `type_name` varchar(128) NOT NULL  COMMENT '类型名',
    `parent_type_id` varchar(32)  COMMENT '父类型ＩＤ',
    `order_by` varchar(32)  COMMENT '排序',
    `show_flg` varchar(32)  COMMENT '是否显示',
    `notes` varchar(128)  COMMENT '备注',
    `memo1` varchar(32)  COMMENT '保留１',
    `memo2` varchar(32)  COMMENT '保留２',
    `memo3` varchar(32)  COMMENT '保留３',
    `memo4` varchar(32)  COMMENT '保留４',
    `memo5` varchar(32)  COMMENT '保留５',
    `create_date` DateTime  COMMENT '创建时间',
    `create_user` varchar(32)  COMMENT '创建者',
    `update_date` DateTime  COMMENT '更新时间',
    `update_user` varchar(32)  COMMENT '更新者',
    PRIMARY KEY (`id`)
);

DROP TABLE IF EXISTS mbus_info cascade;
CREATE TABLE mbus_info (
    `mbus_id` varchar(32) NOT NULL  COMMENT '通讯设备ＩＤ：采番',
    `consumer_id` varchar(32) NOT NULL  COMMENT '用途ＩＤ',
    `install_time` DateTime  COMMENT '安装时间',
    `install_address` varchar(160)  COMMENT '安装地址',
    `mbus_code` varchar(16)  COMMENT '集中器号',
    `factory` varchar(32)  COMMENT '厂商',
    `equipment_no` varchar(32)  COMMENT '型号：有心跳的用数字开头，没心跳的用字母开头',
    `mbus_position` varchar(32)  COMMENT '集中器位置类型',
    `running_state` varchar(32)  COMMENT '运行状态',
    `busy_status` varchar(32)  COMMENT '线路状态',
    `up_comm_mode` varchar(8)  COMMENT '上传方式',
    `trans_mode` varchar(8)  COMMENT '传输方式',
    `channl_mode` varchar(8)  COMMENT '有无虚拟',
    `online_time` DateTime  COMMENT '上线时间',
    `offline_time` DateTime  COMMENT '下线时间',
    `down_pro` varchar(32)  COMMENT '下挂协议',
    `mbus_pro` varchar(32)  COMMENT '集中器自身封包协议',
    `protocol` varchar(8)  COMMENT '使用协议',
    `ip` varchar(32)  COMMENT 'ＩＰ',
    `port` varchar(6)  COMMENT 'ＰＯＲＴ',
    `sim_card` varchar(64)  COMMENT '通讯卡号',
    `sim_provider` varchar(64)  COMMENT '通讯服务商ＩＤ',
    `server_port` varchar(64)  COMMENT '通讯服务器地址与端口',
    `use_start_time` datetime  COMMENT '启用日期',
    `supper_device_address` varchar(32)  COMMENT '上级设备地址',
    `longitude` varchar(32)  COMMENT '经度',
    `latitude` varchar(32)  COMMENT '纬度',
    `company_id` varchar(32) NOT NULL  COMMENT '公司ＩＤ',
    `address` varchar(256)  COMMENT '地址全称：小区名＋楼名＋单元名＋室名',
    `notes` varchar(256)  COMMENT '备注',
    `memo1` varchar(32)  COMMENT '保留１',
    `memo2` varchar(32)  COMMENT '保留２',
    `create_date` datetime  COMMENT '创建时间',
    `create_user` varchar(32)  COMMENT '创建者',
    `update_date` datetime  COMMENT '更新时间',
    `update_user` varchar(32)  COMMENT '更新者',
    `is_first` varchar(32)  COMMENT '是否是第一次搜索',
    `card_flow` varchar(32)  COMMENT '卡实时流量',
    `send_interval` int  COMMENT '指令发送间隔',
    `isvalid` int  COMMENT '有效标志，０表明删除',
    PRIMARY KEY (`mbus_id`)
);

DROP TABLE IF EXISTS mbus_readmodel_info cascade;
CREATE TABLE mbus_readmodel_info (
    `mbus_readmodel_id` varchar(32) NOT NULL  COMMENT '采集器ｉｄ',
    `mbus_id` varchar(32) NOT NULL  COMMENT '集中器ｉｄ',
    `mbus_readmodel_code` varchar(16)  COMMENT '采集器号',
    `install_time` DateTime  COMMENT '安装时间',
    `factory` varchar(32)  COMMENT '厂商',
    `equipment_no` varchar(32)  COMMENT '型号',
    `position` varchar(128)  COMMENT '位置',
    `company_id` varchar(32) NOT NULL  COMMENT '公司ＩＤ',
    `notes` varchar(256)  COMMENT '备注',
    `memo1` varchar(32)  COMMENT '保留１',
    `memo2` varchar(32)  COMMENT '保留２',
    `create_date` DateTime  COMMENT '创建时间',
    `create_user` varchar(32)  COMMENT '创建者',
    `update_date` DateTime  COMMENT '更新时间',
    `update_user` varchar(32)  COMMENT '更新者',
    `isvirtual` varchar(32)  COMMENT '有无虚拟',
    `channelstate` varchar(32)  COMMENT '通道状态：１开，０　关',
    `isvalid` int  COMMENT '有效标志，０表明删除',
    PRIMARY KEY (`mbus_readmodel_id`)
);

DROP TABLE IF EXISTS meter_info cascade;
CREATE TABLE meter_info (
    `meter_id` varchar(32) NOT NULL  COMMENT '仪表ＩＤ',
    `rowno` varchar(32) NOT NULL  COMMENT '仪表ＩＤ２',
    `consumer_id` varchar(32) NOT NULL  COMMENT '用途ＩＤ',
    `address_1st` varchar(32)  COMMENT '一级地址',
    `address_2nd` varchar(32) NOT NULL  COMMENT '二级地址',
    `protocol` varchar(32) NOT NULL  COMMENT '协议',
    `mbus_id` varchar(32)  COMMENT '集中器　ＩＤ',
    `mbus_pro` varchar(32)  COMMENT '集中器封包协议',
    `mbus_readmodel_id` varchar(32)  COMMENT '采集器　ＩＤ',
    `repeater_id` varchar(32)  COMMENT '通道号：采集器下的多通道采集使用',
    `meter_type` varchar(32) NOT NULL  COMMENT '仪表类型：热表、户用温控阀、户用平衡温控阀、楼宇平衡阀、温控面板、管网监测终端、室温采集器',
    `sim_card` varchar(32)  COMMENT '通讯卡号',
    `running_state` varchar(32)  COMMENT '运行状态',
    `install_address` varchar(128)  COMMENT '安装地址',
    `meter_error_time` datetime  COMMENT '最近故障发生时间',
    `meter_position` varchar(32)  COMMENT '位置类型',
    `super_meter_id` varchar(32)  COMMENT '上级表号',
    `valve_meter_id` varchar(32)  COMMENT '阀门对应表号',
    `main_meter_id` varchar(32)  COMMENT '分体对应主体ＩＤ',
    `use_start_date` datetime  COMMENT '启用日期',
    `next_check_time` datetime  COMMENT '下次校表时间',
    `install_height` decimal(8,2)  COMMENT '海拔标高（安装高度',
    `longitude` varchar(32)  COMMENT '经度',
    `latitude` varchar(32)  COMMENT '纬度',
    `company_id` varchar(32) NOT NULL  COMMENT '公司ＩＤ',
    `address` varchar(256)  COMMENT '地址全称：小区名＋楼名＋单元名＋室名',
    `notes` varchar(256)  COMMENT '备注',
    `memo1` varchar(32)  COMMENT '保留１',
    `memo2` varchar(32)  COMMENT '保留２',
    `memo3` varchar(32)  COMMENT '保留３',
    `memo4` varchar(32)  COMMENT '保留４',
    `create_date` datetime  COMMENT '创建时间',
    `create_user` varchar(32)  COMMENT '创建者',
    `update_date` datetime  COMMENT '更新时间',
    `update_user` varchar(32)  COMMENT '更新者',
    `basecode` decimal(8,2)  COMMENT '新表底码',
    `meter_subtype` varchar(32)  COMMENT '仪表子类型',
    `steel` varchar(32)  COMMENT '水表钢印号',
    `water_address` varchar(32)  COMMENT '水表地址',
    `meter_state` varchar(32)  COMMENT '表状态设定：１是启用　０是删除　２是暂停',
    `factory` varchar(32)  COMMENT '厂商',
    `diameter` varchar(32)  COMMENT '口径',
    `precisiona` decimal(8,4)  COMMENT '计量精度',
    `soft_ver` varchar(32)  COMMENT '软件版本',
    `hard_ver` varchar(32)  COMMENT '硬件版本',
    `common_flow` decimal(8,3)  COMMENT '常用流量',
    `minimum_flow` decimal(8,3)  COMMENT '最小流量',
    `temperature_differ` decimal(8,3)  COMMENT '温差范围',
    `temperature_range` decimal(8,3)  COMMENT '温度范围',
    `baud_rate` varchar(32)  COMMENT '波特率',
    `max_bound` varchar(32)  COMMENT '量程',
    `limtspeed` decimal(8,2)  COMMENT '设定流量',
    `ctrlflag` int  COMMENT '终止标志',
    `isvalid` int  COMMENT '有效标志，０表明删除',
    PRIMARY KEY (`meter_id`)
);

DROP TABLE IF EXISTS meter_key cascade;
CREATE TABLE meter_key (
    `id` varchar(32) NOT NULL  COMMENT 'ＩＤ',
    `address_2nd` varchar(32) NOT NULL  COMMENT '表号',
    `meter_key` varchar(100)  COMMENT '秘钥',
    PRIMARY KEY (`id`)
);

DROP TABLE IF EXISTS meter_timing_define cascade;
CREATE TABLE meter_timing_define (
    `primary_id` varchar(32) NOT NULL  COMMENT 'ＩＤ',
    `mbus_code` varchar(32)  COMMENT '集中器ＩＤ',
    `up_comm_mode` int  COMMENT '时间类型：０－－定时　　１－－间隔',
    `order_type` varchar(16)  COMMENT '指令：ｒｅａｄ－－读表　ｕｓｅ－－用水习惯　ｒｅａｄＳｅｔｔｌｅＤａｔａ－－读取结算日数据',
    `exec_day` varchar(16)  COMMENT '执行日　　读取结算日数据用',
    `timing` varchar(20)  COMMENT '定时时间',
    `intervals` int  COMMENT '间隔时间',
    `next_send_time` varchar(20)  COMMENT '间隔时的下一次执行时间',
    `re_read` varchar(8)  COMMENT '读取结算日时，因有数据未读取上来，第二天还需接着读取',
    `company_id` varchar(32) NOT NULL  COMMENT '公司ＩＤ',
    `send_times` int  COMMENT '终端下发次数',
    `time_index` int  COMMENT '终端指令ｉｎｄｅｘ',
    `equipment_no` varchar(32)  COMMENT '集中器型号',
    PRIMARY KEY (`primary_id`)
);

DROP TABLE IF EXISTS dev_accept_info cascade;
CREATE TABLE dev_accept_info (
    `meter_id` varchar(32) NOT NULL  COMMENT '仪表ＩＤ：自动采番',
    `isinstall` varchar(32)  COMMENT '是否安装',
    `ishouse` varchar(32)  COMMENT '是否维护房屋',
    `isonline` varchar(32)  COMMENT '是否远传',
    `isinstall_accept` varchar(32)  COMMENT '是否安装验收',
    `isengin_accept` varchar(32)  COMMENT '是否工程验收',
    `ishouse_link` varchar(32)  COMMENT '表房贯通',
    `rate_online` decimal(8,3)  COMMENT '表计在线率',
    `rate_param` decimal(8,3)  COMMENT '参数完整率',
    `rate_acc` decimal(8,3)  COMMENT '数据准确率',
    `isvitro` varchar(32)  COMMENT '是否维保',
    `ischarge` varchar(32)  COMMENT '缴费居民信息到位',
    `save_time` datetime  COMMENT '保存时间',
    `applyer` varchar(32)  COMMENT '申请人',
    `apply_time` datetime  COMMENT '申请时间',
    `verty_state` varchar(32)  COMMENT '审核状态',
    `meter_type` varchar(32) NOT NULL  COMMENT '表类型：济南四类表计　１，２，３，４',
    `verty_person` varchar(8)  COMMENT '审核人',
    `verty_time` datetime NOT NULL  COMMENT '审核时间',
    PRIMARY KEY (`meter_id`)
);

DROP TABLE IF EXISTS meter_modify_data cascade;
CREATE TABLE meter_modify_data (
    `primary_id` varchar(32) NOT NULL  COMMENT '唯一识别码：采番',
    `meter_id` varchar(32) NOT NULL  COMMENT '设备ＩＤ',
    `meter_type` varchar(8)  COMMENT '仪表类型',
    `consumer_id` varchar(32)  COMMENT '用途ＩＤ',
    `address_2nd` varchar(32)  COMMENT '仪表号',
    `flag` varchar(10)  COMMENT '标志：１更换　　２　维修',
    `meter_modify_time` datetime NOT NULL  COMMENT '维修时间',
    `modify_reason` varchar(255)  COMMENT '维修原因',
    `old_metet_begin` varchar(32)  COMMENT '原表起码',
    `old_metet_end` varchar(32)  COMMENT '原表止码',
    `new_metet_address_2nd` varchar(32)  COMMENT '新表表号',
    `new_metet_begin` varchar(32)  COMMENT '新表起码',
    `company_id` varchar(32) NOT NULL  COMMENT '公司ＩＤ',
    `notes` varchar(256)  COMMENT '备注',
    `memo1` varchar(32)  COMMENT '保留１',
    `memo2` varchar(32)  COMMENT '保留２',
    `create_date` datetime  COMMENT '创建时间',
    `create_user` varchar(32)  COMMENT '创建者',
    `update_date` datetime  COMMENT '更新时间',
    `update_user` varchar(32)  COMMENT '更新者',
    `factory` varchar(32)  COMMENT '厂家',
    PRIMARY KEY (`primary_id`)
);

DROP TABLE IF EXISTS pipe_device_info cascade;
CREATE TABLE pipe_device_info (
    `pipe_device_id` varchar(32) NOT NULL  COMMENT '管线及设备ＩＤ',
    `pipe_device_name` varchar(64) NOT NULL  COMMENT '管线及设备名称：管道、各种用途阀门、弯头、变径、三通等',
    `ascription_id` varchar(32)  COMMENT '区域归属ＩＤ：指的是热源、换热站等区域',
    `father_no` varchar(32)  COMMENT '父节点编号',
    `pipe_device_no` varchar(32)  COMMENT '本节点编号',
    `hight` decimal(8,2)  COMMENT '本节点标高',
    `equipment_no` varchar(32)  COMMENT '规格',
    `out_diam` decimal(8,2)  COMMENT '外径',
    `pipe_wall` decimal(8,2)  COMMENT '管壁',
    `technique_param` varchar(64)  COMMENT '技术参数：其他详细参数',
    `factory` varchar(32)  COMMENT '厂家',
    `device_classify` varchar(32)  COMMENT '设备归类：一次网、二次网、热源、热力站',
    `charge_person` varchar(32)  COMMENT '负责人ＩＤ',
    `lenth` decimal(8,2)  COMMENT '管道（当量）长度（ｍ）',
    `length_adjust_factor` decimal(8,2)  COMMENT '当量长度调整系数',
    `start_time` datetime  COMMENT '启用时间',
    `longitude` varchar(32)  COMMENT '本节点经度',
    `latitude` varchar(32)  COMMENT '本节点纬度',
    `company_id` varchar(32) NOT NULL  COMMENT '公司ＩＤ',
    `notes` varchar(256)  COMMENT '备注',
    `memo1` varchar(32)  COMMENT '保留１',
    `memo2` varchar(32)  COMMENT '保留２',
    `create_date` datetime  COMMENT '创建时间',
    `create_user` varchar(32)  COMMENT '创建者',
    `update_date` datetime  COMMENT '更新时间',
    `update_user` varchar(32)  COMMENT '更新者',
    `in_diam` decimal(8,2)  COMMENT '内径：默认等于外径－２＊管壁，可修改',
    `pipe_type_id` varchar(32)  COMMENT '所属管线类别ＩＤ',
    `pipe_id` varchar(32)  COMMENT '所属管线ＩＤ',
    PRIMARY KEY (`pipe_device_id`)
);

DROP TABLE IF EXISTS station_show_dev_type cascade;
CREATE TABLE station_show_dev_type (
    `dev_type` varchar(32) NOT NULL  COMMENT '设备类型：水泵，阀门，板换等等',
    `dev_name` varchar(32) NOT NULL  COMMENT '设备名称',
    `isshow` int  COMMENT '是否显示',
    `notes` varchar(32)  COMMENT '备注',
    `memo1` varchar(32)  COMMENT '保留１',
    `memo2` varchar(32)  COMMENT '保留２' 

);

DROP TABLE IF EXISTS station_show_dev cascade;
CREATE TABLE station_show_dev (
    `dev_type` varchar(32) NOT NULL  COMMENT '设备类型ＩＤ：水泵，阀门，板换，基本参数，计量参数，运行参数',
    `dev_id` varchar(32) NOT NULL  COMMENT '设备ＩＤ：ＩＤ为设备类型ＩＤ＋编号（１，２）自动生成，热泵在站设备关联表里面已经有了，自动同步',
    `dev_name` varchar(100) NOT NULL  COMMENT '设备名：添加记录时自动生成',
    `source_id` varchar(32) NOT NULL  COMMENT '源ＩＤ：源ＩＤ＋ｄｅｖ＿ｉｄ是主键，显示的时候，有几个设备，显示对应的列下的数据',
    `notes` varchar(32)  COMMENT '备注',
    `memo1` varchar(32)  COMMENT '保留１',
    `memo2` varchar(32)  COMMENT '保留２',
    PRIMARY KEY (`dev_id`,`source_id`)
);

DROP TABLE IF EXISTS system_point_dict cascade;
CREATE TABLE system_point_dict (
    `id` varchar(32) NOT NULL  COMMENT 'ＩＤ',
    `pointid` varchar(40)  COMMENT '点位名：与运行数据表中字段一致',
    `showdevtype` varchar(32)  COMMENT '对应显示设备类型',
    `tablename` varchar(50)  COMMENT '保存在哪里：存不存，存在哪里',
    `unit` varchar(11)  COMMENT '单位',
    `kind` varchar(32)  COMMENT '字段类型说明：一次侧，子系统，热泵',
    `warnkind` int  COMMENT '报警类型：０没报警，１是开关量报警，２模拟量报警',
    `iswarn` int  COMMENT '默认开启：１开启，０关闭',
    `lwarn` decimal(8,2)  COMMENT '低值报警设定',
    `llwarn` decimal(8,2)  COMMENT '低低值报警设定',
    `hwarn` decimal(8,2)  COMMENT '高值报警设定',
    `hhwarn` decimal(8,2)  COMMENT '高高值报警设定',
    `swarn` decimal(8,2)  COMMENT '比率报警设定，当两次的值差大于多少时报警',
    `warndes` varchar(50)  COMMENT '报警描述',
    PRIMARY KEY (`id`)
);

DROP TABLE IF EXISTS system_point_tab cascade;
CREATE TABLE system_point_tab (
    `id` varchar(32) NOT NULL  COMMENT 'ＩＤ',
    `pointid` varchar(40)  COMMENT '点位名：与运行数据表中字段一致',
    `sourceid` varchar(32)  COMMENT '源ＩＤ：对应',
    `showdevtype` varchar(32)  COMMENT '对应显示设备类型',
    `tablename` varchar(50)  COMMENT '保存在哪里：空不存，不空则为表名＿ｈｉｓ，表名＋',
    `unit` varchar(11)  COMMENT '单位',
    `kind` varchar(32)  COMMENT '字段类型说明：一次侧，子系统，热泵',
    `warnkind` int  COMMENT '是否包含报警：０没报警，１是开关量报警，２模拟量报警',
    `lwarn` decimal(8,2)  COMMENT '低值报警设定',
    `llwarn` decimal(8,2)  COMMENT '低低值报警设定',
    `hwarn` decimal(8,2)  COMMENT '高值报警设定',
    `hhwarn` decimal(8,2)  COMMENT '高高值报警设定',
    `swarn` decimal(8,2)  COMMENT '比率报警设定，当两次的值差大于多少时报警',
    `warndes` varchar(50)  COMMENT '报警描述',
    `iswarn` int  COMMENT '默认开启：１开启，０关闭',
    `valided` int  COMMENT '是否显示：０无效，１有效，用于字段无效时可以置为０',
    PRIMARY KEY (`id`)
);

DROP TABLE IF EXISTS station_col_dev cascade;
CREATE TABLE station_col_dev (
    `mbus_id` varchar(32)  COMMENT '通讯设备ＩＤ',
    `dev_id` varchar(32) NOT NULL  COMMENT '设备ＩＤ',
    `dev_name` varchar(32) NOT NULL  COMMENT '设备名称：如果是ｓｃａｄａ，则配置为服务名，热泵则热泵名按照规则生成，并自动添加到显示设备表中。',
    `dev_type` varchar(32) NOT NULL  COMMENT '设备类型：ＰＬＣ，ＳＣＡＤＡ，热泵，其它',
    `mode_id` varchar(32) NOT NULL  COMMENT '设备模板',
    `addr` varchar(32)  COMMENT '设备地址',
    `running_state` varchar(32)  COMMENT '运行状态：标识运行状态及故障类型，暂时只有断线',
    `col_state` varchar(32)  COMMENT '采集类型：１　是运行，２是暂停',
    `install_addr` varchar(32)  COMMENT '安装地址',
    `notes` varchar(32)  COMMENT '备注',
    `memo1` varchar(32)  COMMENT '保留１',
    `memo2` varchar(32)  COMMENT '保留２',
    `isvalid` int  COMMENT '有效标志，０表明删除',
    PRIMARY KEY (`dev_id`)
);

DROP TABLE IF EXISTS station_point_desc cascade;
CREATE TABLE station_point_desc (
    `id` varchar(32) NOT NULL  COMMENT 'ＩＤ',
    `pointid` varchar(40)  COMMENT '点位名：与运行数据表中字段一致',
    `func` int  COMMENT '功能码：０３，０１',
    `offset` int  COMMENT '偏移地址',
    `oper` int  COMMENT '读数是否需要处理',
    `ratio` decimal(8,3)  COMMENT 'ｏｐｅｒ＝＝１时，读数＝读数×ｒａｔｉｏ＋ｏｆｆ',
    `off` decimal(8,3)  COMMENT 'ｏｐｅｒ＝＝１时，读数＝读数×ｒａｔｉｏ＋ｏｆｆ',
    `type` int  COMMENT '数据类型　１为四字节浮点数，２为两字节整数，３为开关量',
    `offset2` int  COMMENT 'ｔｙｐｅ为３时，标记在寄存器内的偏移',
    `unit` varchar(50)  COMMENT '单位',
    `timespan` int  COMMENT '采集间隔',
    `name` varchar(80)  COMMENT '名称描述',
    `dtype` varchar(32) NOT NULL  COMMENT '所属模板，一般为ＰＬＣ　和热泵：所属模板，一般为ＰＬＣ，ＳＣＡＤＡ和热泵',
    `opcgroup` varchar(32)  COMMENT 'ＯＰＣ组名',
    `opcpoint` varchar(32)  COMMENT '点位名',
    `opckey` varchar(32)  COMMENT '组名点位名分隔符',
    `index` int  COMMENT '所属系统的索引：当设备模板中包含复数个系统时，在此标记１，２作为与站或分系统关联的依据',
    `warnkind` int  COMMENT '是否包含报警：０没报警，１是开关量报警，２模拟量报警',
    `lwarn` decimal(8,2)  COMMENT '低值报警设定',
    `llwarn` decimal(8,2)  COMMENT '低低值报警设定',
    `hwarn` decimal(8,2)  COMMENT '高值报警设定',
    `hhwarn` decimal(8,2)  COMMENT '高高值报警设定',
    `swarn` decimal(8,2)  COMMENT '比率报警设定，当两次的值差大于多少时报警',
    `warndes` varchar(50)  COMMENT '报警描述',
    `iswarn` int  COMMENT '默认开启：１开启，０关闭',
    PRIMARY KEY (`id`)
);

DROP TABLE IF EXISTS station_dev_point cascade;
CREATE TABLE station_dev_point (
    `id` varchar(32) NOT NULL  COMMENT 'ＩＤ',
    `devid` varchar(32)  COMMENT '设备ＩＤ',
    `pointid` varchar(40)  COMMENT '点位名：与运行数据表中字段一致',
    `func` int  COMMENT '功能码：０３，０１',
    `offsets` int  COMMENT '偏移地址',
    `oper` int  COMMENT '读数是否需要处理',
    `ratio` decimal(8,3)  COMMENT 'ｏｐｅｒ＝＝１时，读数＝读数×ｒａｔｉｏ＋ｏｆｆ',
    `off` decimal(8,3)  COMMENT 'ｏｐｅｒ＝＝１时，读数＝读数×ｒａｔｉｏ＋ｏｆｆ',
    `type` int  COMMENT '数据类型　１为四字节浮点数，２为两字节整数，３为开关量',
    `offset2` int  COMMENT 'ｔｙｐｅ为３时，标记在寄存器内的偏移',
    `unit` varchar(50)  COMMENT '单位',
    `timespan` int  COMMENT '采集间隔',
    `names` varchar(80)  COMMENT '名称描述',
    `dtype` varchar(32) NOT NULL  COMMENT '所属模板，一般为ＰＬＣ　和热泵：所属模板，一般为ＰＬＣ，ＳＣＡＤＡ和热泵',
    `opcgroup` varchar(32)  COMMENT 'ＯＰＣ组名',
    `opcpoint` varchar(32)  COMMENT '点位名',
    `opckey` varchar(32)  COMMENT '组名点位名分隔符',
    `indexs` varchar(32)  DEFAULT '1' COMMENT '所属系统的索引：当设备模板中包含复数个系统时，在此标记１，２作为与站或分系统关联的依据',
    `warnkind` int  COMMENT '报警类型：０没报警，１是开关量报警，２模拟量报警',
    `iswarn` int  COMMENT '默认开启：１开启，０关闭',
    `lwarn` decimal(8,2)  COMMENT '低值报警设定',
    `llwarn` decimal(8,2)  COMMENT '低低值报警设定',
    `hwarn` decimal(8,2)  COMMENT '高值报警设定',
    `hhwarn` decimal(8,2)  COMMENT '高高值报警设定',
    `swarn` decimal(8,2)  COMMENT '比率报警设定，当两次的值差大于多少时报警',
    `warndes` varchar(50)  COMMENT '报警描述',
    `sourceid` varchar(32)  COMMENT '源ＩＤ：对应',
    `isvalid` int  COMMENT '是否有效',
    `tablename` varchar(32)  COMMENT '保存在哪里：空不存，不空则为表名＿ｈｉｓ，表名＋',
    PRIMARY KEY (`id`)
);

DROP TABLE IF EXISTS station_commond cascade;
CREATE TABLE station_commond (
    `commond_id` varchar(32) NOT NULL  COMMENT '指令ＩＤ',
    `model_id` varchar(32) NOT NULL  COMMENT '模板ＩＤ',
    `span` varchar(8)  COMMENT '采集间隔（ｓ）',
    `commond` varchar(255)  COMMENT '指令',
    `notes` varchar(255)  COMMENT '备注：情况说明',
    `create_date` datetime  COMMENT '创建者',
    `create_user` varchar(32)  COMMENT '创建时间',
    `update_date` datetime  COMMENT '更新者',
    `update_user` varchar(32)  COMMENT '更新时间',
    PRIMARY KEY (`commond_id`,`model_id`)
);

DROP TABLE IF EXISTS station_dev_relative cascade;
CREATE TABLE station_dev_relative (
    `id` varchar(32) NOT NULL  COMMENT '设备ＩＤ',
    `sourceid` varchar(32) NOT NULL  COMMENT '站ＩＤ／系统ＩＤ',
    `index` varchar(32)  COMMENT '设备的索引，用于一个设备对应多个站的情况',
    `notes` varchar(255)  COMMENT '备注：情况说明',
    `create_date` datetime  COMMENT '创建者',
    `create_user` varchar(32)  COMMENT '创建时间',
    `update_date` datetime  COMMENT '更新者',
    `update_user` varchar(32)  COMMENT '更新时间',
    PRIMARY KEY (`id`,`sourceid`)
);

DROP TABLE IF EXISTS mbus_work_history cascade;
CREATE TABLE mbus_work_history (
    `primary_id` varchar(32) NOT NULL  COMMENT '记录序号：采番',
    `mbus_id` varchar(32) NOT NULL  COMMENT '通讯设备ＩＤ',
    `mbus_code` varchar(16) NOT NULL  COMMENT '集中器号',
    `running_state` varchar(32)  COMMENT '运行状态',
    `busy_status` varchar(32)  COMMENT '线路状态',
    `online_time` DateTime  COMMENT '上线时间',
    `offline_time` DateTime  COMMENT '下线时间',
    `ip` varchar(32)  COMMENT 'ＩＰ',
    `port` varchar(6)  COMMENT 'ＰＯＲＴ',
    `crsq` varchar(32)  COMMENT '信号质量',
    `connection_times` varchar(32)  COMMENT '连接次数',
    `success_times` varchar(32)  COMMENT '成功次数',
    `company_id` varchar(32) NOT NULL  COMMENT '公司ＩＤ',
    `memo1` varchar(32)  COMMENT '保留１',
    `memo2` varchar(32)  COMMENT '保留２',
    `create_date` datetime  COMMENT '创建时间',
    `create_user` varchar(32)  COMMENT '创建者',
    `update_date` datetime  COMMENT '更新时间',
    `update_user` varchar(32)  COMMENT '更新者',
    `card_flow` varchar(32)  COMMENT '流量',
    PRIMARY KEY (`primary_id`)
);

DROP TABLE IF EXISTS meter_read_percent cascade;
CREATE TABLE meter_read_percent (
    `primary_id` varchar(32) NOT NULL  COMMENT 'ＩＤ',
    `commuity_id` varchar(10)  COMMENT '小区ＩＤ',
    `mbus_code` varchar(16) NOT NULL  COMMENT '集中器ＩＤ',
    `sys_read_time` datetime NOT NULL  COMMENT '读取时间',
    `meter_type` varchar(8) NOT NULL  COMMENT '仪表类型',
    `factory_id` varchar(8) NOT NULL  COMMENT '厂商ＩＤ',
    `meter_all` int  COMMENT '仪表总数',
    `read_success` int  COMMENT '读取成功的仪表数',
    `read_fail` int  COMMENT '读取失败的仪表数',
    `meter_err` int  COMMENT '仪表故障数',
    `meter_data` int  COMMENT '数据分析故障数',
    `read_success_per` decimal  COMMENT '读取成功百分比',
    `read_fail_per` decimal  COMMENT '读取失败百分比',
    `meter_err_per` decimal  COMMENT '仪表故障百分比',
    `meter_data_per` decimal  COMMENT '数据故障百分比',
    `notes` varchar(255)  COMMENT '备注',
    `company_id` varchar(32) NOT NULL  COMMENT '公司ＩＤ',
    PRIMARY KEY (`primary_id`,`mbus_code`,`sys_read_time`,`meter_type`,`factory_id`)
);

DROP TABLE IF EXISTS mbus_test cascade;
CREATE TABLE mbus_test (
    `primary_id` varchar(32) NOT NULL  COMMENT 'ＩＤ',
    `mbus_code` varchar(32) NOT NULL  COMMENT '集中器ＩＤ',
    `send_data` varchar(32)  COMMENT '发送数据',
    `rec_data` varchar(320)  COMMENT '接收数据',
    `send_date` varchar(320) NOT NULL  COMMENT '发送时间',
    `rec_date` varchar(32)  COMMENT '接收时间：有数据表明成功',
    `rec_flag` varchar(8)  COMMENT '接收标志：０－－默认　１－－程序正常',
    `company_id` varchar(32) NOT NULL  COMMENT '公司ＩＤ',
    PRIMARY KEY (`primary_id`)
);

DROP TABLE IF EXISTS heat_meter_data_now cascade;
CREATE TABLE heat_meter_data_now (
    `meter_id` varchar(32) NOT NULL  COMMENT '仪表ｉｄ',
    `consumer_id` varchar(32) NOT NULL  COMMENT '用途ＩＤ',
    `heat` decimal(18,2)  COMMENT '热量',
    `cool` decimal(18,2)  COMMENT '冷量',
    `in_flow` decimal(18,2)  COMMENT '正向流量',
    `out_flow` decimal(18,2)  COMMENT '反向流量',
    `flow_speed` decimal(8,3)  COMMENT '瞬时流量',
    `in_temperature` decimal(5,2)  COMMENT '进水温度',
    `out_temperature` decimal(5,2)  COMMENT '回水温度',
    `power` decimal(8,3)  COMMENT '功率',
    `all_work_time` varchar(32)  COMMENT '工作时间',
    `all_time` varchar(32)  COMMENT '线路板加电时间',
    `sys_read_time` DateTime NOT NULL  COMMENT '读取时间',
    `running_state` varchar(8)  COMMENT '设备状态',
    `meter_time` datetime  COMMENT '热表时间',
    `heat_last` decimal(18,2)  COMMENT '上次热量',
    `cool_last` decimal(18,2)  COMMENT '上次冷量',
    `in_flow_last` decimal(18,2)  COMMENT '上次正向流量',
    `out_flow_last` decimal(18,2)  COMMENT '上次反向流量',
    `flow_speed_last` decimal(8,3)  COMMENT '上次瞬时流量',
    `in_temperature_last` decimal(5,2)  COMMENT '上次进水温度',
    `out_temperature_last` decimal(5,2)  COMMENT '上次回水温度',
    `power_last` decimal(8,3)  COMMENT '上次功率',
    `all_work_time_last` varchar(32)  COMMENT '上次工作时间',
    `all_time_last` varchar(32)  COMMENT '上次线路板加电时间',
    `sys_read_time_last` DateTime  COMMENT '上次读取时间',
    `running_state_last` varchar(8)  COMMENT '上次设备状态',
    `meter_time_last` datetime  COMMENT '上次热表时间',
    `avg_flow_speed` decimal(8,3)  COMMENT '瞬时流量',
    `avg_power` decimal(8,3)  COMMENT '功率',
    `company_id` varchar(32) NOT NULL  COMMENT '公司ＩＤ',
    `notes` varchar(256)  COMMENT '备注',
    `memo1` varchar(32)  COMMENT '保留１',
    `memo2` varchar(32)  COMMENT '保留２',
    `memo3` varchar(32)  COMMENT '保留３',
    `memo4` varchar(32)  COMMENT '保留４',
    `memo5` varchar(32)  COMMENT '保留５',
    `memo6` varchar(32)  COMMENT '保留６',
    `create_date` DateTime  COMMENT '创建时间',
    `create_user` varchar(32)  COMMENT '创建者',
    `update_date` DateTime  COMMENT '更新时间',
    `update_user` varchar(32)  COMMENT '更新者',
    PRIMARY KEY (`meter_id`)
);

DROP TABLE IF EXISTS heat_meter_randrom_read_temp cascade;
CREATE TABLE heat_meter_randrom_read_temp (
    `id` varchar(32)  COMMENT '自增列',
    `commuity_id` varchar(32)  COMMENT '小区ｉｄ：未用',
    `building_no` varchar(32)  COMMENT '楼ｉｄ：未用',
    `unit_no` varchar(32)  COMMENT '单元ｉｄ：未用',
    `room_no` varchar(32)  COMMENT '房间ｉｄ：未用',
    `reat_time` varchar(32) NOT NULL  COMMENT '读取时间',
    `meter_position` varchar(32)  COMMENT '位置信息',
    `dtu_state_flag` varchar(32)  COMMENT '集中器和后台程序状态',
    `read_over_flag` varchar(32)  COMMENT '是否读取完成',
    `more_flag` varchar(32)  COMMENT '是否再次读取',
    `more_time` varchar(32)  COMMENT '再次读取时间',
    `node_select` varchar(4000)  COMMENT '选择的节点',
    `user_name` varchar(32)  COMMENT '操作者',
    `company_id` varchar(32) NOT NULL  COMMENT '公司ＩＤ',
    PRIMARY KEY (`reat_time`)
);

DROP TABLE IF EXISTS heat_meter_data_normal_now cascade;
CREATE TABLE heat_meter_data_normal_now (
    `meter_id` varchar(32) NOT NULL  COMMENT '仪表ｉｄ',
    `consumer_id` varchar(32) NOT NULL  COMMENT '用途ＩＤ',
    `heat` decimal(18,2)  COMMENT '热量',
    `cool` decimal(18,2)  COMMENT '冷量',
    `in_flow` decimal(18,2)  COMMENT '正向流量',
    `out_flow` decimal(18,2)  COMMENT '反向流量',
    `flow_speed` decimal(8,3)  COMMENT '瞬时流量',
    `in_temperature` decimal(5,2)  COMMENT '进水温度',
    `out_temperature` decimal(5,2)  COMMENT '回水温度',
    `power` decimal(8,3)  COMMENT '功率',
    `all_work_time` varchar(32)  COMMENT '工作时间',
    `all_time` varchar(32)  COMMENT '线路板加电时间',
    `sys_read_time` DateTime  COMMENT '读取时间',
    `running_state` varchar(8)  COMMENT '设备状态',
    `meter_time` datetime  COMMENT '热表时间',
    `heat_last` decimal(18,2)  COMMENT '上次热量',
    `cool_last` decimal(18,2)  COMMENT '上次冷量',
    `in_flow_last` decimal(18,2)  COMMENT '上次正向流量',
    `out_flow_last` decimal(18,2)  COMMENT '上次反向流量',
    `flow_speed_last` decimal(8,3)  COMMENT '上次瞬时流量',
    `in_temperature_last` decimal(5,2)  COMMENT '上次进水温度',
    `out_temperature_last` decimal(5,2)  COMMENT '上次回水温度',
    `power_last` decimal(8,3)  COMMENT '上次功率',
    `all_work_time_last` varchar(32)  COMMENT '上次工作时间',
    `all_time_last` varchar(32)  COMMENT '上次线路板加电时间',
    `sys_read_time_last` DateTime  COMMENT '上次读取时间',
    `running_state_last` varchar(8)  COMMENT '上次设备状态',
    `meter_time_last` datetime  COMMENT '上次热表时间',
    `avg_flow_speed` decimal(8,3)  COMMENT '瞬时流量',
    `avg_power` decimal(8,3)  COMMENT '功率',
    `company_id` varchar(32) NOT NULL  COMMENT '公司ＩＤ',
    `notes` varchar(256)  COMMENT '备注',
    `memo1` varchar(32)  COMMENT '保留１',
    `memo2` varchar(32)  COMMENT '保留２',
    `memo3` varchar(32)  COMMENT '保留３',
    `memo4` varchar(32)  COMMENT '保留４',
    `memo5` varchar(32)  COMMENT '保留５',
    `memo6` varchar(32)  COMMENT '保留６',
    `create_date` DateTime  COMMENT '创建时间',
    `create_user` varchar(32)  COMMENT '创建者',
    `update_date` DateTime  COMMENT '更新时间',
    `update_user` varchar(32)  COMMENT '更新者',
    PRIMARY KEY (`meter_id`)
);

DROP TABLE IF EXISTS heat_meter_data cascade;
CREATE TABLE heat_meter_data (
    `primary_id` varchar(32) NOT NULL  COMMENT '记录序号',
    `meter_id` varchar(32) NOT NULL  COMMENT '仪表ｉｄ',
    `heat` decimal(18,2)  COMMENT '热量',
    `cool` decimal(18,2)  COMMENT '冷量',
    `in_flow` decimal(18,2)  COMMENT '正向流量',
    `out_flow` decimal(18,2)  COMMENT '反向流量',
    `flow_speed` decimal(8,3)  COMMENT '瞬时流量',
    `in_temperature` decimal(5,2)  COMMENT '进水温度',
    `out_temperature` decimal(5,2)  COMMENT '回水温度',
    `power` decimal(8,3)  COMMENT '功率',
    `all_work_time` varchar(32)  COMMENT '工作时间',
    `all_time` varchar(32)  COMMENT '线路板加电时间',
    `sys_read_time` DateTime NOT NULL  COMMENT '读取时间',
    `running_state` varchar(8)  COMMENT '设备状态',
    `meter_time` datetime  COMMENT '热表时间',
    `company_id` varchar(32) NOT NULL  COMMENT '公司ＩＤ',
    `notes` varchar(256)  COMMENT '备注',
    `memo1` varchar(32)  COMMENT '保留１',
    `memo2` varchar(32)  COMMENT '保留２',
    `memo3` varchar(32)  COMMENT '保留３',
    `memo4` varchar(32)  COMMENT '保留４',
    `memo5` varchar(32)  COMMENT '保留５',
    `memo6` varchar(32)  COMMENT '保留６',
    `create_date` DateTime  COMMENT '创建时间',
    `create_user` varchar(32)  COMMENT '创建者',
    `update_date` DateTime  COMMENT '更新时间',
    `update_user` varchar(32)  COMMENT '更新者',
    `isfreeze` varchar(32)  COMMENT '是否冻结',
    `using_id` varchar(32)  COMMENT '用途ＩＤ',
    PRIMARY KEY (`meter_id`,`sys_read_time`)
);

DROP TABLE IF EXISTS valve_data_now cascade;
CREATE TABLE valve_data_now (
    `meter_id` varchar(32) NOT NULL  COMMENT '仪表ｉｄ',
    `consumer_id` varchar(32) NOT NULL  COMMENT '用途ＩＤ',
    `control_role` varchar(32)  COMMENT '控制权限：上位控制／就地控制',
    `regulation_mode` varchar(32)  COMMENT '调节方式：动态调节／通断调节',
    `openness` decimal(5,2)  COMMENT '开度',
    `running_state` varchar(32)  COMMENT '阀门状态：全开、中间、全关',
    `action_limit` varchar(32)  COMMENT '动作限制：无限制／平衡限制',
    `limit_status` varchar(32)  COMMENT '限制状态：正常动作、禁止开阀、禁止关阀、点动关阀、点动开阀',
    `execution_step` decimal(5,2)  COMMENT '执行步长：固定步长＊修正系数１＊修正系数２',
    `fixed_step` decimal(5,2)  COMMENT '固定步长：取自中间表',
    `currnt_work` varchar(32)  COMMENT '当前工作状态：开、关、保持',
    `upper_control_command` varchar(8)  COMMENT '上位控制指令：全开、全关、点开、点关、锁定等',
    `flow_speed` decimal(8,3)  COMMENT '瞬时流量',
    `upper_limit_flow` decimal(8,2)  COMMENT '流量上限值（ｌ／ｈ）：取自控制参数表',
    `max_flow_threshold` decimal(8,2)  COMMENT '最大流量阈值（ｌ／ｈ）：取自控制参数表',
    `power_protection` varchar(32)  COMMENT '停电保护：阀门保持原位、阀门全开、阀门全关',
    `correction_factor1` decimal(8,2)  COMMENT '修正系数１：取自中间表',
    `correction_factor2` decimal(8,2)  COMMENT '修正系数２：取自中间表',
    `set_temperature` decimal(8,2)  COMMENT '设定温度',
    `room_temperature` decimal(8,2)  COMMENT '室内温度',
    `sys_read_time` DateTime NOT NULL  COMMENT '读取时间',
    `company_id` varchar(32) NOT NULL  COMMENT '公司ＩＤ',
    `notes` varchar(256)  COMMENT '备注',
    `memo1` varchar(32)  COMMENT '保留１',
    `memo2` varchar(32)  COMMENT '保留２',
    `create_date` DateTime  COMMENT '创建时间',
    `create_user` varchar(32)  COMMENT '创建者',
    `update_date` DateTime  COMMENT '更新时间',
    `update_user` varchar(32)  COMMENT '更新者',
    `meter_type` varchar(32)  COMMENT '仪表类型：室内采集器，温控阀',
    `step_total` decimal(8,2)  COMMENT '步长累计值：正值表示把阀全关初始为０，阀累计开了多少步；负值表示把阀全开初始为０，阀累计关了多少步，应为我们全关和全开一共需要多少步，是一个不确定数。',
    `base_step` decimal(8,2)  COMMENT '基本步长（阀门每动一步的长度，可设置）',
    `fur_step` decimal(8,2)  COMMENT '预开步数（阀门在通断方式时，由全关状态进行开阀时的动作步数，可设置）',
    `open_time` decimal(8,2)  COMMENT '开阀时长：阀门处于开或者半开状态的总时长。',
    `interval` decimal(8,2)  COMMENT '动作间隔：阀门处于动态调节时两次调节之间的间隔，可设置。',
    `cal` decimal(8,2)  COMMENT '阀门口径',
    `point_step` decimal(8,2)  COMMENT '点动步数：温控器控制处于调节状态的阀门时，不同的温差对应不同的动作步数，分为３级。可设置。',
    `wireless` decimal(8,2)  COMMENT '有线／无线产品：是指与温控器之间是有线连接还是无线连接。',
    `current_times` DateTime  COMMENT '阀当前时间',
    `lock` decimal(8,2)  COMMENT '锁定／解锁状态',
    `indoor_col_time` DateTime  COMMENT '室内温度采集时间',
    PRIMARY KEY (`meter_id`)
);

DROP TABLE IF EXISTS valve_data cascade;
CREATE TABLE valve_data (
    `primary_id` varchar(32) NOT NULL  COMMENT '记录序号',
    `meter_id` varchar(32) NOT NULL  COMMENT '仪表ｉｄ',
    `consumer_id` varchar(32) NOT NULL  COMMENT '用途ＩＤ',
    `control_role` varchar(32)  COMMENT '控制权限：上位控制／就地控制',
    `regulation_mode` varchar(32)  COMMENT '调节方式：动态调节／通断调节',
    `openness` decimal(5,2)  COMMENT '开度',
    `running_state` varchar(32)  COMMENT '阀门状态：全开、中间、全关',
    `action_limit` varchar(32)  COMMENT '动作限制：无限制／平衡限制',
    `limit_status` varchar(32)  COMMENT '限制状态：正常动作、禁止开阀、禁止关阀、点动关阀、点动开阀',
    `execution_step` decimal(5,2)  COMMENT '执行步长：固定步长＊修正系数１＊修正系数２',
    `fixed_step` decimal(5,2)  COMMENT '固定步长：取自中间表',
    `currnt_work` varchar(32)  COMMENT '当前工作状态：开、关、保持',
    `upper_control_command` varchar(8)  COMMENT '上位控制指令：全开、全关、点开、点关、锁定等',
    `flow_speed` decimal(8,3)  COMMENT '瞬时流量',
    `upper_limit_flow` decimal(8,2)  COMMENT '流量上限值（ｌ／ｈ）：取自控制参数表',
    `max_flow_threshold` decimal(8,2)  COMMENT '最大流量阈值（ｌ／ｈ）：取自控制参数表',
    `power_protection` varchar(32)  COMMENT '停电保护：阀门保持原位、阀门全开、阀门全关',
    `correction_factor1` decimal(8,2)  COMMENT '修正系数１：取自中间表',
    `correction_factor2` decimal(8,2)  COMMENT '修正系数２：取自中间表',
    `set_temperature` decimal(8,2)  COMMENT '设定温度',
    `room_temperature` decimal(8,2)  COMMENT '室内温度',
    `sys_read_time` DateTime NOT NULL  COMMENT '读取时间',
    `company_id` varchar(32) NOT NULL  COMMENT '公司ＩＤ',
    `notes` varchar(256)  COMMENT '备注',
    `memo1` varchar(32)  COMMENT '保留１',
    `memo2` varchar(32)  COMMENT '保留２',
    `create_date` DateTime  COMMENT '创建时间',
    `create_user` varchar(32)  COMMENT '创建者',
    `update_date` DateTime  COMMENT '更新时间',
    `update_user` varchar(32)  COMMENT '更新者',
    `meter_type` varchar(32)  COMMENT '仪表类型：室内采集器，温控阀',
    `step_total` decimal(8,2)  COMMENT '步长累计值：正值表示把阀全关初始为０，阀累计开了多少步；负值表示把阀全开初始为０，阀累计关了多少步，应为我们全关和全开一共需要多少步，是一个不确定数。',
    `base_step` decimal(8,2)  COMMENT '基本步长（阀门每动一步的长度，可设置）',
    `fur_step` decimal(8,2)  COMMENT '预开步数（阀门在通断方式时，由全关状态进行开阀时的动作步数，可设置）',
    `open_time` decimal(8,2)  COMMENT '开阀时长：阀门处于开或者半开状态的总时长。',
    `interval` decimal(8,2)  COMMENT '动作间隔：阀门处于动态调节时两次调节之间的间隔，可设置。',
    `cal` decimal(8,2)  COMMENT '阀门口径',
    `point_step` decimal(8,2)  COMMENT '点动步数：温控器控制处于调节状态的阀门时，不同的温差对应不同的动作步数，分为３级。可设置。',
    `wireless` decimal(8,2)  COMMENT '有线／无线产品：是指与温控器之间是有线连接还是无线连接。',
    `current_times` DateTime  COMMENT '阀当前时间',
    `locka` varchar(32)  COMMENT '锁定／解锁状态',
    `indoor_col_time` DateTime  COMMENT '室内温度采集时间',
    PRIMARY KEY (`primary_id`,`sys_read_time`)
);

DROP TABLE IF EXISTS balance_valve_data_now cascade;
CREATE TABLE balance_valve_data_now (
    `meter_id` varchar(32) NOT NULL  COMMENT '仪表ｉｄ',
    `consumer_id` varchar(32) NOT NULL  COMMENT '用途ＩＤ',
    `control_role` varchar(32)  COMMENT '控制权限：上位控制／就地控制',
    `control_mode` varchar(32)  COMMENT '控制模式：压差控制、流量限制、回温限制、手动控制等',
    `openness` decimal(5,2)  COMMENT '开度',
    `in_pressure` decimal(5,2)  COMMENT '进水压力',
    `out_pressure` decimal(5,2)  COMMENT '回水压力',
    `pressure_diff` decimal(5,2)  COMMENT '供回压差',
    `set_pressure_diff` decimal(5,2)  COMMENT '压差设定值',
    `pressure_diff__threshold` decimal(5,2)  COMMENT '压差阈值',
    `in_temperature` decimal(5,2)  COMMENT '进水温度',
    `out_temperature` decimal(5,2)  COMMENT '回水温度',
    `set_temperature` decimal(5,2)  COMMENT '设定温度',
    `temperature_threshold` decimal(5,2)  COMMENT '温度阈值',
    `sample_flow_speed` decimal(8,3)  COMMENT '样本瞬时流量：取自样本用户的热表数据',
    `unit_flow_speed` decimal(8,3)  COMMENT '单元瞬时流量：通过样本流量换算为单元流量而来',
    `upper_limit_flow` decimal(8,3)  COMMENT '流量上限值（ｌ／ｈ）：取自控制参数表',
    `max_flow_threshold` decimal(8,3)  COMMENT '流量阈值（ｌ／ｈ）：取自控制参数表',
    `sys_read_time` DateTime NOT NULL  COMMENT '读取时间',
    `company_id` varchar(32) NOT NULL  COMMENT '公司ＩＤ',
    `notes` varchar(256)  COMMENT '备注',
    `memo1` varchar(32)  COMMENT '保留１',
    `memo2` varchar(32)  COMMENT '保留２',
    `create_date` DateTime  COMMENT '创建时间',
    `create_user` varchar(32)  COMMENT '创建者',
    `update_date` DateTime  COMMENT '更新时间',
    `update_user` varchar(32)  COMMENT '更新者',
    `env_time` decimal(8,3)  COMMENT '环境温度',
    `open_set` decimal(8,3)  COMMENT '设定开度：固定开度模式的设定开度。',
    `open_level` decimal(8,3)  COMMENT '开度阈值',
    `base_open` varchar(32)  COMMENT '基本开度：允许的最大／最小开度',
    `low_temp` decimal(8,3)  COMMENT '最低温度：允许的最低温度',
    `com_interval` decimal(8,3)  COMMENT '温控间隔：温度控制模式下，两次调整的间隔时间',
    `exchange_temp` varchar(32)  COMMENT '交换进回水温度传感器',
    `exchange_press` varchar(32)  COMMENT '交换进回水压力变送器',
    `exchange_frame` varchar(32)  COMMENT '切换压力帧',
    `lock` varchar(32)  COMMENT '锁定／解锁状态',
    `time_param` varchar(32)  COMMENT '时间段参数：每天分为３个时间段，每个时间段记录时／分２个字节',
    `temp_param` varchar(32)  COMMENT '温度曲线参数：需要记录４个室外温度点对应的设定回水温度',
    `time_open_param` varchar(32)  COMMENT '时间段开度参数：记录３个时间段对应的３个设定开度。',
    `time_temp_param` varchar(32)  COMMENT '时间段温度参数：记录３个时间段对应的３个设定回水温度。',
    `time_temp_cure_param` varchar(32)  COMMENT '时间段温度曲线参数：需要记录３个时间段对应的３个偏置温度',
    `press_adj` varchar(32)  COMMENT '压力修正值：进回水压力各自修正多少。',
    `cal` decimal(8,3)  COMMENT '阀门口径',
    `open_time` decimal(8,3)  COMMENT '开阀时长：阀门处于开或者半开状态的总时长。',
    `current_time` DateTime  COMMENT '阀当前时间',
    PRIMARY KEY (`meter_id`)
);

DROP TABLE IF EXISTS balance_valve_data cascade;
CREATE TABLE balance_valve_data (
    `primary_id` varchar(32) NOT NULL  COMMENT '记录序号',
    `meter_id` varchar(32) NOT NULL  COMMENT '仪表ｉｄ',
    `consumer_id` varchar(32) NOT NULL  COMMENT '用途ＩＤ',
    `control_role` varchar(32)  COMMENT '控制权限：上位控制／就地控制',
    `control_mode` varchar(32)  COMMENT '控制模式：压差控制、流量限制、回温限制、平均温度控制、手动控制等',
    `openness` decimal(5,2)  COMMENT '开度',
    `in_pressure` decimal(5,2)  COMMENT '进水压力',
    `out_pressure` decimal(5,2)  COMMENT '回水压力',
    `pressure_diff` decimal(5,2)  COMMENT '供回压差',
    `set_pressure_diff` decimal(5,2)  COMMENT '压差设定值',
    `pressure_diff__threshold` decimal(5,2)  COMMENT '压差阈值',
    `in_temperature` decimal(5,2)  COMMENT '进水温度',
    `out_temperature` decimal(5,2)  COMMENT '回水温度',
    `set_temperature` decimal(5,2)  COMMENT '设定温度',
    `temperature_threshold` decimal(5,2)  COMMENT '温度阈值',
    `sample_flow_speed` decimal(8,3)  COMMENT '样本瞬时流量：取自样本用户的热表数据',
    `unit_flow_speed` decimal(8,3)  COMMENT '单元瞬时流量：通过样本流量换算为单元流量而来',
    `upper_limit_flow` decimal(8,3)  COMMENT '流量上限值（ｌ／ｈ）：取自控制参数表',
    `max_flow_threshold` decimal(8,3)  COMMENT '流量阈值（ｌ／ｈ）：取自控制参数表',
    `sys_read_time` DateTime NOT NULL  COMMENT '读取时间',
    `company_id` varchar(32) NOT NULL  COMMENT '公司ＩＤ',
    `notes` varchar(256)  COMMENT '备注',
    `memo1` varchar(32)  COMMENT '保留１',
    `memo2` varchar(32)  COMMENT '保留２',
    `create_date` DateTime  COMMENT '创建时间',
    `create_user` varchar(32)  COMMENT '创建者',
    `update_date` DateTime  COMMENT '更新时间',
    `update_user` varchar(32)  COMMENT '更新者',
    `env_time` decimal(8,3)  COMMENT '环境温度',
    `open_set` decimal(8,3)  COMMENT '设定开度：固定开度模式的设定开度。',
    `open_level` decimal(8,3)  COMMENT '开度阈值',
    `base_open` varchar(32)  COMMENT '基本开度：允许的最大／最小开度',
    `low_temp` decimal(8,3)  COMMENT '最低温度：允许的最低温度',
    `com_interval` decimal(8,3)  COMMENT '温控间隔：温度控制模式下，两次调整的间隔时间',
    `exchange_temp` varchar(32)  COMMENT '交换进回水温度传感器',
    `exchange_press` varchar(32)  COMMENT '交换进回水压力变送器',
    `exchange_frame` varchar(32)  COMMENT '切换压力帧',
    `lock` varchar(32)  COMMENT '锁定／解锁状态',
    `time_param` varchar(32)  COMMENT '时间段参数：每天分为３个时间段，每个时间段记录时／分２个字节',
    `temp_param` varchar(32)  COMMENT '温度曲线参数：需要记录４个室外温度点对应的设定回水温度',
    `time_open_param` varchar(32)  COMMENT '时间段开度参数：记录３个时间段对应的３个设定开度。',
    `time_temp_param` varchar(32)  COMMENT '时间段温度参数：记录３个时间段对应的３个设定回水温度。',
    `time_temp_cure_param` varchar(32)  COMMENT '时间段温度曲线参数：需要记录３个时间段对应的３个偏置温度',
    `press_adj` varchar(32)  COMMENT '压力修正值：进回水压力各自修正多少。',
    `cal` decimal(8,3)  COMMENT '阀门口径',
    `open_time` decimal(8,3)  COMMENT '开阀时长：阀门处于开或者半开状态的总时长。',
    `current_time` DateTime  COMMENT '阀当前时间',
    PRIMARY KEY (`primary_id`)
);

DROP TABLE IF EXISTS balance_valve_control_temp_h cascade;
CREATE TABLE balance_valve_control_temp_h (
    `primary_id` varchar(32) NOT NULL  COMMENT 'ＩＤ',
    `read_time` varchar(32)  COMMENT '读取时间',
    `control_flag` varchar(32)  COMMENT '控制类型：ｍｓｔ的平衡阀控制',
    `control_type` varchar(32)  COMMENT '控制方式：０－－单独　１－－广播',
    `dtu_state_flag` varchar(32)  COMMENT '集中器和后台程序状态：初始０，正常为１',
    `read_over_flag` varchar(32)  COMMENT '读取完成标志：初始０，完成１',
    `more_flag` varchar(32)  COMMENT '是否再次读取的标志：初始０，再次读取１',
    `more_time` varchar(32)  COMMENT '再次读取的时间',
    `node_select` varchar(400)  COMMENT '公用树选择的：用于广播操作的条件',
    `id_select` varchar(4000)  COMMENT '选择的控制阀：单独操作的条件',
    `user_name` varchar(32)  COMMENT '操作者',
    `para1` varchar(400)  COMMENT '参数１',
    `para2` varchar(32)  COMMENT '参数２',
    `para3` varchar(32)  COMMENT '参数３',
    `para4` varchar(32)  COMMENT '参数４',
    `para5` varchar(32)  COMMENT '参数５',
    `company_id` varchar(32) NOT NULL  COMMENT '公司ＩＤ',
    PRIMARY KEY (`primary_id`)
);

DROP TABLE IF EXISTS balance_valve_control_temp_d cascade;
CREATE TABLE balance_valve_control_temp_d (
    `primary_id` varchar(32) NOT NULL  COMMENT 'ＩＤ',
    `hid` bigint  COMMENT '头表ＩＤ',
    `read_time` varchar(32) NOT NULL  COMMENT '读取时间',
    `meter_id` varchar(32) NOT NULL  COMMENT '仪表ＩＤ',
    `success_flag` varchar(10)  COMMENT '成功标志：１－－执行成功，其余执行失败',
    `company_id` varchar(32) NOT NULL  COMMENT '公司ＩＤ',
    PRIMARY KEY (`primary_id`,`read_time`,`meter_id`)
);

DROP TABLE IF EXISTS balance_commuity_state cascade;
CREATE TABLE balance_commuity_state (
    `primary_id` varchar(32) NOT NULL  COMMENT 'ＩＤ',
    `commuity_id` varchar(4)  COMMENT '小区ＩＤ',
    `commuity_balance_state` varchar(4)  COMMENT '平衡阀分析状态：小区平衡阀状态',
    `sys_read_time` datetime  COMMENT '读取时间',
    `company_id` varchar(32) NOT NULL  COMMENT '公司ＩＤ',
    PRIMARY KEY (`primary_id`)
);

DROP TABLE IF EXISTS pipe_monitoring_terminal_data_now cascade;
CREATE TABLE pipe_monitoring_terminal_data_now (
    `meter_id` varchar(32) NOT NULL  COMMENT '仪表ｉｄ',
    `in_pressure` decimal(5,2)  COMMENT '进水压力',
    `out_pressure` decimal(5,2)  COMMENT '回水压力',
    `pressure_diff` decimal(5,2)  COMMENT '供回压差',
    `in_temperature` decimal(5,2)  COMMENT '进水温度',
    `out_temperature` decimal(5,2)  COMMENT '回水温度',
    `temperature_diff` decimal(5,2)  COMMENT '温差',
    `sys_read_time` DateTime NOT NULL  COMMENT '读取时间',
    `company_id` varchar(32) NOT NULL  COMMENT '公司ＩＤ',
    `notes` varchar(256)  COMMENT '备注',
    `memo1` varchar(32)  COMMENT '保留１',
    `memo2` varchar(32)  COMMENT '保留２',
    `create_date` DateTime  COMMENT '创建时间',
    `create_user` varchar(32)  COMMENT '创建者',
    `update_date` DateTime  COMMENT '更新时间',
    `update_user` varchar(32)  COMMENT '更新者',
    PRIMARY KEY (`meter_id`)
);

DROP TABLE IF EXISTS pipe_monitoring_terminal_data cascade;
CREATE TABLE pipe_monitoring_terminal_data (
    `primary_id` varchar(32) NOT NULL  COMMENT '记录序号',
    `meter_id` varchar(32) NOT NULL  COMMENT '仪表ｉｄ',
    `in_pressure` decimal(5,2)  COMMENT '进水压力',
    `out_pressure` decimal(5,2)  COMMENT '回水压力',
    `pressure_diff` decimal(5,2)  COMMENT '供回压差',
    `in_temperature` decimal(5,2)  COMMENT '进水温度',
    `out_temperature` decimal(5,2)  COMMENT '回水温度',
    `temperature_diff` decimal(5,2)  COMMENT '温差',
    `sys_read_time` DateTime NOT NULL  COMMENT '读取时间',
    `company_id` varchar(32) NOT NULL  COMMENT '公司ＩＤ',
    `notes` varchar(256)  COMMENT '备注',
    `memo1` varchar(32)  COMMENT '保留１',
    `memo2` varchar(32)  COMMENT '保留２',
    `create_date` DateTime  COMMENT '创建时间',
    `create_user` varchar(32)  COMMENT '创建者',
    `update_date` DateTime  COMMENT '更新时间',
    `update_user` varchar(32)  COMMENT '更新者',
    `source_id` varchar(32)  COMMENT '用途ＩＤ',
    PRIMARY KEY (`meter_id`,`sys_read_time`)
);

DROP TABLE IF EXISTS room_temperature_data cascade;
CREATE TABLE room_temperature_data (
    `primary_id` varchar(32) NOT NULL  COMMENT '记录序号',
    `meter_id` varchar(32) NOT NULL  COMMENT '仪表ｉｄ',
    `consumer_id` varchar(32) NOT NULL  COMMENT '用途ＩＤ',
    `room_temperature` decimal(5,2)  COMMENT '室温',
    `signal` int  COMMENT '信号强度',
    `elec` decimal(5,2)  COMMENT '电量',
    `sys_read_time` DateTime  COMMENT '读取时间',
    `company_id` varchar(32) NOT NULL  COMMENT '公司ＩＤ',
    `position_key` varchar(32) NOT NULL  COMMENT '定位键值',
    `notes` varchar(256)  COMMENT '备注',
    `memo1` varchar(32)  COMMENT '保留１',
    `memo2` varchar(32)  COMMENT '保留２',
    `create_date` DateTime  COMMENT '创建时间',
    `create_user` varchar(32)  COMMENT '创建者',
    `update_date` DateTime  COMMENT '更新时间',
    `update_user` varchar(32)  COMMENT '更新者',
    PRIMARY KEY (`meter_id`,`company_id`)
);

DROP TABLE IF EXISTS room_temperature_data_now cascade;
CREATE TABLE room_temperature_data_now (
    `meter_id` varchar(32) NOT NULL  COMMENT '仪表ｉｄ',
    `consumer_id` varchar(32) NOT NULL  COMMENT '用途ＩＤ',
    `room_temperature` decimal(5,2)  COMMENT '室温',
    `signal` int  COMMENT '信号强度',
    `elec` decimal(5,2)  COMMENT '电量',
    `sys_read_time` DateTime  COMMENT '读取时间',
    `company_id` varchar(32) NOT NULL  COMMENT '公司ＩＤ',
    `position_key` varchar(32) NOT NULL  COMMENT '定位键值',
    `notes` varchar(256)  COMMENT '备注',
    `memo1` varchar(32)  COMMENT '保留１',
    `memo2` varchar(32)  COMMENT '保留２',
    `create_date` DateTime  COMMENT '创建时间',
    `create_user` varchar(32)  COMMENT '创建者',
    `update_date` DateTime  COMMENT '更新时间',
    `update_user` varchar(32)  COMMENT '更新者',
    PRIMARY KEY (`meter_id`,`company_id`)
);

DROP TABLE IF EXISTS valve_control_temp_h cascade;
CREATE TABLE valve_control_temp_h (
    `primary_id` varchar(32) NOT NULL  COMMENT 'ＩＤ',
    `read_time` varchar(32)  COMMENT '读取时间',
    `control_flag` varchar(32)  COMMENT '控制类型：ｍｓｔ的平衡阀控制',
    `control_type` varchar(32)  COMMENT '控制方式：０－－单独　１－－广播',
    `dtu_state_flag` varchar(32)  COMMENT '集中器和后台程序状态：初始０，正常为１',
    `read_over_flag` varchar(32)  COMMENT '读取完成标志：初始０，完成１',
    `more_flag` varchar(32)  COMMENT '是否再次读取的标志：初始０，再次读取１',
    `more_time` varchar(32)  COMMENT '再次读取的时间',
    `node_select` varchar(400)  COMMENT '公用树选择的：用于广播操作的条件',
    `id_select` varchar(4000)  COMMENT '选择的控制阀：单独操作的条件',
    `user_name` varchar(32)  COMMENT '操作者',
    `para1` varchar(32)  COMMENT '参数１',
    `para2` varchar(32)  COMMENT '参数２',
    `para3` varchar(32)  COMMENT '参数３',
    `para4` varchar(32)  COMMENT '参数４',
    `para5` varchar(32)  COMMENT '参数５',
    PRIMARY KEY (`primary_id`)
);

DROP TABLE IF EXISTS valve_control_temp_d cascade;
CREATE TABLE valve_control_temp_d (
    `primary_id` varchar(32) NOT NULL  COMMENT 'ＩＤ',
    `hid` varchar(32)  COMMENT '头表ＩＤ',
    `read_time` varchar(32)  COMMENT '读取时间',
    `meter_id` varchar(32)  COMMENT '仪表ＩＤ',
    `success_flag` varchar(10)  COMMENT '成功标志：１－－执行成功，其余执行失败',
    PRIMARY KEY (`primary_id`)
);

DROP TABLE IF EXISTS temp_control_param cascade;
CREATE TABLE temp_control_param (
    `meter_id` varchar(32) NOT NULL  COMMENT '设备ＩＤ',
    `consumer_id` varchar(32) NOT NULL  COMMENT '用途ＩＤ',
    `first_start_time` datetime  COMMENT '第一时段开始时间',
    `first_end_time` datetime  COMMENT '第一时段结束时间',
    `first_set_temperature` decimal(5,2)  COMMENT '第一时段设置室温',
    `sencond_start_time` datetime  COMMENT '第二时段开始时间：１更换　　２　维修',
    `sencond_end_time` datetime NOT NULL  COMMENT '第二时段结束时间',
    `sencond_set_temperature` decimal(5,2)  COMMENT '第二时段设置室温',
    `third_start_time` datetime  COMMENT '第三时段开始时间',
    `third_end_time` datetime  COMMENT '第三时段结束时间',
    `third_set_temperature` decimal(5,2)  COMMENT '第三时段设置室温',
    `fouth_start_time` datetime  COMMENT '第四时段开始时间',
    `fouth_end_time` datetime  COMMENT '第四时段结束时间',
    `fouth_set_temperature` decimal(5,2)  COMMENT '第四时段设置室温',
    `room_temperature_read` decimal(5,2)  COMMENT '室温测量值',
    `room_temperature_set` decimal(5,2)  COMMENT '室温设置高限',
    `set_role` varchar(32)  COMMENT '室温设定权限：上位／用户',
    `set_time` datetime  COMMENT '设置时间',
    `company_id` varchar(32) NOT NULL  COMMENT '公司ＩＤ',
    `notes` varchar(256)  COMMENT '备注',
    `memo1` varchar(32)  COMMENT '保留１',
    `memo2` varchar(32)  COMMENT '保留２',
    `create_date` datetime  COMMENT '创建时间',
    `create_user` varchar(32)  COMMENT '创建者',
    `update_date` datetime  COMMENT '更新时间',
    `update_user` varchar(32)  COMMENT '更新者',
    `wireless` varchar(32)  COMMENT '无线地址：只针对无线温控器有效',
    `temp_bound` varchar(32)  COMMENT '控温范围：允许温控器设置的最高／最低温度',
    `temp_mode` varchar(32)  COMMENT '控温模式：智能控温和手动控温。智能控温是指分时段控温',
    `time_step` varchar(32)  COMMENT '说明：时间段实际是分为工作日和休息日，每天分为３个时间段',
    `iswire` varchar(32)  COMMENT '有线／无线温控器',
    PRIMARY KEY (`meter_id`)
);

DROP TABLE IF EXISTS valve_control_param1 cascade;
CREATE TABLE valve_control_param1 (
    `meter_id` varchar(32) NOT NULL  COMMENT '设备ＩＤ',
    `consumer_id` varchar(32) NOT NULL  COMMENT '用途ＩＤ',
    `heating_area` decimal(5,2)  COMMENT '采暖面积（㎡）',
    `flowing_index` decimal(5,2)  COMMENT '流量指标（Ｌ／㎡）',
    `flow_value` decimal(5,2)  COMMENT '流量值（Ｌ／Ｈ）',
    `flow_upper_ratio` decimal(5,2)  COMMENT '流量上限比（％）',
    `flow_upper_value` decimal(5,2)  COMMENT '流量上限值（Ｌ／Ｈ）',
    `max_flow_threshold` decimal(5,2)  COMMENT '最大流量阈值（Ｌ／Ｈ）',
    `flow_give_mode` varchar(32)  COMMENT '流量给定方式：（系统计算、人工给定',
    `fixed_step` decimal(5,2)  COMMENT '固定步长',
    `company_id` varchar(32) NOT NULL  COMMENT '公司ＩＤ',
    `notes` varchar(256)  COMMENT '备注',
    `memo1` varchar(32)  COMMENT '保留１',
    `memo2` varchar(32)  COMMENT '保留２',
    `create_date` datetime  COMMENT '创建时间',
    `create_user` varchar(32)  COMMENT '创建者',
    `update_date` datetime  COMMENT '更新时间',
    `update_user` varchar(32)  COMMENT '更新者',
    PRIMARY KEY (`meter_id`)
);

DROP TABLE IF EXISTS valve_control_param2 cascade;
CREATE TABLE valve_control_param2 (
    `rowno` varchar(32) NOT NULL  COMMENT 'ｉｄ',
    `compare_algorithm_id` varchar(8) NOT NULL  COMMENT '比较算法ＩＤ',
    `compare_algorithm_name` varchar(64) NOT NULL  COMMENT '比较算法',
    `correct_coefficient` decimal(18,2) NOT NULL  COMMENT '修正系数',
    `type` varchar(8) NOT NULL  COMMENT '系数区分：系数１，系数２',
    PRIMARY KEY (`rowno`)
);

DROP TABLE IF EXISTS station_data_point cascade;
CREATE TABLE station_data_point (
    `id` varchar(32) NOT NULL  COMMENT 'ＩＤ',
    `pointid` varchar(40)  COMMENT '点位名：与运行数据表中字段一致',
    `sourceid` varchar(32)  COMMENT '数据源ＩＤ',
    `val` decimal(8,4)  COMMENT '值',
    `time` datetime  COMMENT '时间',
    PRIMARY KEY (`id`)
);

DROP TABLE IF EXISTS station_data_elecont cascade;
CREATE TABLE station_data_elecont (
    `id` varchar(32) NOT NULL  COMMENT 'ＩＤ',
    `pointid` varchar(40)  COMMENT '点位名：与运行数据表中字段一致',
    `sourceid` varchar(32)  COMMENT '数据源ＩＤ',
    `val` decimal(8,4)  COMMENT '值',
    `time` datetime  COMMENT '时间',
    PRIMARY KEY (`id`)
);

DROP TABLE IF EXISTS station_data_heatcont cascade;
CREATE TABLE station_data_heatcont (
    `pointid` varchar(40) NOT NULL  COMMENT '点位名：与运行数据表中字段一致',
    `sourceid` varchar(32) NOT NULL  COMMENT '数据源ＩＤ',
    `val` decimal(8,4)  COMMENT '值',
    `time` datetime  COMMENT '时间',
    PRIMARY KEY (`pointid`,`sourceid`)
);

DROP TABLE IF EXISTS station_data_rapwater cascade;
CREATE TABLE station_data_rapwater (
    `id` varchar(32) NOT NULL  COMMENT 'ＩＤ',
    `pointid` varchar(40)  COMMENT '点位名：与运行数据表中字段一致',
    `sourceid` varchar(32)  COMMENT '数据源ＩＤ',
    `val` decimal(8,4)  COMMENT '值',
    `time` datetime  COMMENT '时间',
    PRIMARY KEY (`id`)
);

DROP TABLE IF EXISTS station_data_hpump cascade;
CREATE TABLE station_data_hpump (
    `id` varchar(32) NOT NULL  COMMENT 'ＩＤ',
    `pointid` varchar(40)  COMMENT '点位名：与运行数据表中字段一致',
    `devid` varchar(40)  COMMENT '设备名',
    `sourceid` varchar(32)  COMMENT '数据源ＩＤ',
    `val` decimal(8,4)  COMMENT '值',
    `time` datetime  COMMENT '时间',
    PRIMARY KEY (`id`)
);

DROP TABLE IF EXISTS station_data_two cascade;
CREATE TABLE station_data_two (
    `bas_temsupwat` decimal(8,4)  COMMENT '供水温度',
    `bas_temretwat` decimal(8,4)  COMMENT '回水温度',
    `bas_presretwat` decimal(8,4)  COMMENT '回水压力（除前）',
    `bas_prespumpfont` decimal(8,4)  COMMENT '回水压力（除后）',
    `bas_prespumpback` decimal(8,4)  COMMENT '泵后压力',
    `bas_pressupwat` decimal(8,4)  COMMENT '供水压力',
    `bas_flowinst` decimal(8,4)  COMMENT '瞬时流量',
    `bas_flowpow` decimal(8,4)  COMMENT '瞬时功率',
    `bas_weathertemp` decimal(8,4)  COMMENT '室外温度',
    `bas_repwatinst` decimal(8,4)  COMMENT '补水瞬时流量',
    `sourceid` varchar(32)  COMMENT '数据源ＩＤ',
    `time` datetime  COMMENT '时间' 

);

DROP TABLE IF EXISTS station_data_one cascade;
CREATE TABLE station_data_one (
    `one_temsupwat` decimal(8,4)  COMMENT '供水温度',
    `one_temretwat` decimal(8,4)  COMMENT '回水温度',
    `one_presretwat` decimal(8,4)  COMMENT '回水压力（除前）',
    `one_prespumpfont` decimal(8,4)  COMMENT '回水压力（除后）',
    `one_prespumpback` decimal(8,4)  COMMENT '泵后压力',
    `one_pressupwat` decimal(8,4)  COMMENT '供水压力',
    `one_flowinst` decimal(8,4)  COMMENT '瞬时流量',
    `one_flowpow` decimal(8,4)  COMMENT '瞬时功率',
    `one_weathertemp` decimal(8,4)  COMMENT '室外温度',
    `one_repwatinst` decimal(8,4)  COMMENT '补水瞬时流量',
    `sourceid` varchar(32)  COMMENT '数据源ＩＤ',
    `time` datetime  COMMENT '时间' 

);

DROP TABLE IF EXISTS water_meter_data cascade;
CREATE TABLE water_meter_data (
    `id` varchar(32) NOT NULL  COMMENT 'ＩＤ',
    `meter_id` varchar(32) NOT NULL  COMMENT '仪表ｉｄ',
    `in_flow` decimal(18,2)  COMMENT '正向流量',
    `out_flow` decimal(18,2)  COMMENT '反正流量',
    `flow_speed` decimal(18,3)  COMMENT '进水温度',
    `in_temperature` decimal(18,4)  COMMENT '瞬时流量',
    `all_work_time` varchar(32)  COMMENT '工作时间',
    `all_time` varchar(32)  COMMENT '线路板加电时间',
    `sys_read_time` DateTime  COMMENT '读取时间',
    `running_state` VARCHAR(8)  COMMENT '设备状态',
    `pressure_meter_id` varchar(32)  COMMENT '压力模块ＩＤ',
    `pressure1` decimal(18,3)  COMMENT '左侧压力',
    `pressure2` decimal(18,3)  COMMENT '右侧压力',
    `max_history_flow_speed` decimal(18,4)  COMMENT '历史最大流速',
    `max_lastday_flow_speed` decimal(18,4)  COMMENT '昨日最大流速',
    `settle_day_flow` decimal(18,2)  COMMENT '结算日流量',
    `valve_status` VARCHAR(32)  COMMENT '阀状态',
    `max_flow_speed` decimal(18,4)  COMMENT '今日最大流速',
    `mbus_offline` int  COMMENT '集中器在线状态',
    `residual_flow` decimal(18,3)  COMMENT '剩余流量',
    `residual_price` decimal(18,3)  COMMENT '剩余金额',
    `owe_flow` decimal(18,3)  COMMENT '欠流量',
    `owe_price` decimal(18,3)  COMMENT '欠金额',
    `notes` varchar(256)  COMMENT '备注',
    `memo1` varchar(32)  COMMENT '保留１：压力变送器的错误',
    `memo2` varchar(32)  COMMENT '保留２：表的真实时间',
    `memo3` varchar(32)  COMMENT '保留３：返回数据不全错误',
    `memo4` varchar(32)  COMMENT '保留４',
    `memo5` varchar(32)  COMMENT '保留５：表的状态字',
    `memo6` varchar(32)  COMMENT '保留６：表号',
    `create_date` DateTime  COMMENT '创建时间',
    `create_user` varchar(32)  COMMENT '创建者',
    `update_date` DateTime  COMMENT '更新时间',
    `update_user` varchar(32)  COMMENT '更新者',
    `rssi` varchar(32)  COMMENT '无线水表信号',
    PRIMARY KEY (`id`)
);

DROP TABLE IF EXISTS sewage_station_data cascade;
CREATE TABLE sewage_station_data (
    `primary_id` varchar(32) NOT NULL  COMMENT 'ＩＤ',
    `sewage_station_id` varchar(32) NOT NULL  COMMENT '水站ＩＤ',
    `elec_total` decimal(18,2)  COMMENT '累计电量',
    `in_pressure` decimal(18,3)  COMMENT '出口压力',
    `out_pressure` decimal(18,3)  COMMENT '入口压力',
    `pump1_total_time` decimal(18,2)  COMMENT '泵１总运行时间',
    `pump2_total_time` decimal(18,2)  COMMENT '泵２总运行时间',
    `pump3_total_time` decimal(18,2)  COMMENT '泵３总运行时间',
    `pump4_total_time` decimal(18,2)  COMMENT '泵４总运行时间',
    `pump5_total_time` decimal(18,2)  COMMENT '泵５总运行时间',
    `pump6_total_time` decimal(18,2)  COMMENT '泵６总运行时间',
    `pump7_total_time` decimal(18,2)  COMMENT '泵７总运行时间',
    `pump8_total_time` decimal(18,2)  COMMENT '泵８总运行时间',
    `pump1_today_time` decimal(18,2)  COMMENT '泵１当天运行时间',
    `pump2_today_time` decimal(18,2)  COMMENT '泵２当天运行时间',
    `pump3_today_time` decimal(18,2)  COMMENT '泵３当天运行时间',
    `pump4_today_time` decimal(18,2)  COMMENT '泵４当天运行时间',
    `pump5_today_time` decimal(18,2)  COMMENT '泵５当天运行时间',
    `pump6_today_time` decimal(18,2)  COMMENT '泵６当天运行时间',
    `pump7_today_time` decimal(18,2)  COMMENT '泵７当天运行时间',
    `pump8_today_time` decimal(18,2)  COMMENT '泵８当天运行时间',
    `sys_read_time` DateTime  COMMENT '读取时间',
    `notes` varchar(255)  COMMENT '备注',
    `memo1` varchar(32)  COMMENT '保留１',
    `memo2` varchar(32)  COMMENT '保留２',
    `create_date` datetime  COMMENT '创建时间',
    `create_user` varchar(32)  COMMENT '创建者',
    `update_date` datetime  COMMENT '更新时间',
    `update_user` varchar(32)  COMMENT '更新者',
    PRIMARY KEY (`sewage_station_id`)
);

DROP TABLE IF EXISTS pump_station_data cascade;
CREATE TABLE pump_station_data (
    `primary_id` varchar(32) NOT NULL  COMMENT 'ＩＤ',
    `pump_station_id` varchar(32) NOT NULL  COMMENT '水站ＩＤ',
    `flow_speed` decimal(18,3)  COMMENT '瞬时流量',
    `in_flow` decimal(18,2)  COMMENT '累计流量',
    `elec_total` decimal(18,2)  COMMENT '累计电量',
    `in_pressure` decimal(18,3)  COMMENT '出口压力',
    `out_pressure` decimal(18,3)  COMMENT '入口压力',
    `pump1_total_time` decimal(18,2)  COMMENT '泵１总运行时间',
    `pump2_total_time` decimal(18,2)  COMMENT '泵２总运行时间',
    `pump3_total_time` decimal(18,2)  COMMENT '泵３总运行时间',
    `pump4_total_time` decimal(18,2)  COMMENT '泵４总运行时间',
    `pump5_total_time` decimal(18,2)  COMMENT '泵５总运行时间',
    `pump6_total_time` decimal(18,2)  COMMENT '泵６总运行时间',
    `pump7_total_time` decimal(18,2)  COMMENT '泵７总运行时间',
    `pump8_total_time` decimal(18,2)  COMMENT '泵８总运行时间',
    `pump1_today_time` decimal(18,2)  COMMENT '泵１当天运行时间',
    `pump2_today_time` decimal(18,2)  COMMENT '泵２当天运行时间',
    `pump3_today_time` decimal(18,2)  COMMENT '泵３当天运行时间',
    `pump4_today_time` decimal(18,2)  COMMENT '泵４当天运行时间',
    `pump5_today_time` decimal(18,2)  COMMENT '泵５当天运行时间',
    `pump6_today_time` decimal(18,2)  COMMENT '泵６当天运行时间',
    `pump7_today_time` decimal(18,2)  COMMENT '泵７当天运行时间',
    `pump8_today_time` decimal(18,2)  COMMENT '泵８当天运行时间',
    `sys_read_time` DateTime  COMMENT '读取时间',
    `notes` varchar(255)  COMMENT '备注',
    `memo1` varchar(32)  COMMENT '保留１',
    `memo2` varchar(32)  COMMENT '保留２',
    `create_date` datetime  COMMENT '创建时间',
    `create_user` varchar(32)  COMMENT '创建者',
    `update_date` datetime  COMMENT '更新时间',
    `update_user` varchar(32)  COMMENT '更新者',
    PRIMARY KEY (`primary_id`,`pump_station_id`)
);

DROP TABLE IF EXISTS source_water_data cascade;
CREATE TABLE source_water_data (
    `primary_id` varchar(32) NOT NULL  COMMENT 'ＩＤ',
    `source_water_id` varchar(32) NOT NULL  COMMENT '水源ＩＤ',
    `flow_speed` decimal(18,3)  COMMENT '瞬时流量',
    `in_flow` decimal(18,2)  COMMENT '累计流量',
    `elec_total` decimal(18,2)  COMMENT '累计电量',
    `pump1_total_time` decimal(18,2)  COMMENT '泵１总运行时间',
    `pump2_total_time` decimal(18,2)  COMMENT '泵２总运行时间',
    `pump3_total_time` decimal(18,2)  COMMENT '泵３总运行时间',
    `pump4_total_time` decimal(18,2)  COMMENT '泵４总运行时间',
    `pump5_total_time` decimal(18,2)  COMMENT '泵５总运行时间',
    `pump6_total_time` decimal(18,2)  COMMENT '泵６总运行时间',
    `pump7_total_time` decimal(18,2)  COMMENT '泵７总运行时间',
    `pump8_total_time` decimal(18,2)  COMMENT '泵８总运行时间',
    `pump1_today_time` decimal(18,2)  COMMENT '泵１当天运行时间',
    `pump2_today_time` decimal(18,2)  COMMENT '泵２当天运行时间',
    `pump3_today_time` decimal(18,2)  COMMENT '泵３当天运行时间',
    `pump4_today_time` decimal(18,2)  COMMENT '泵４当天运行时间',
    `pump5_today_time` decimal(18,2)  COMMENT '泵５当天运行时间',
    `pump6_today_time` decimal(18,2)  COMMENT '泵６当天运行时间',
    `pump7_today_time` decimal(18,2)  COMMENT '泵７当天运行时间',
    `pump8_today_time` decimal(18,2)  COMMENT '泵８当天运行时间',
    `sys_read_time` DateTime  COMMENT '读取时间',
    `running_state` VARCHAR(8)  COMMENT '设备状态',
    `notes` varchar(255)  COMMENT '备注',
    `memo1` varchar(32)  COMMENT '保留１',
    `memo2` varchar(32)  COMMENT '保留２',
    `create_date` datetime  COMMENT '创建时间',
    `create_user` varchar(32)  COMMENT '创建者',
    `update_date` datetime  COMMENT '更新时间',
    `update_user` varchar(32)  COMMENT '更新者',
    PRIMARY KEY (`primary_id`)
);

DROP TABLE IF EXISTS water_meter_use cascade;
CREATE TABLE water_meter_use (
    `primary_id` varchar(32) NOT NULL  COMMENT 'ＩＤ',
    `meter_id` varchar(32) NOT NULL  COMMENT '仪表ＩＤ',
    `sys_read_time` datetime  COMMENT '读取时间',
    `in_flow_begin` decimal  COMMENT '初始流量',
    `begin_time` datetime NOT NULL  COMMENT '初始时间',
    `in_flow_end` decimal  COMMENT '结束流量',
    `end_time` datetime  COMMENT '结束时间',
    `in_flow_use` decimal  COMMENT '使用流量',
    `use_time` int  COMMENT '使用时间',
    `speed_hight` decimal  COMMENT '最高流速',
    `speed_average` decimal  COMMENT '平均流速',
    `toilet_use_flg` int  COMMENT '马桶标志',
    `washer_use_flg` int  COMMENT '洗衣机标志',
    `notes` varchar(256)  COMMENT '备注',
    `create_date` DateTime  COMMENT '创建时间',
    `create_user` varchar(32)  COMMENT '创建者',
    `update_date` DateTime  COMMENT '更新时间',
    `update_user` varchar(32)  COMMENT '更新者',
    PRIMARY KEY (`primary_id`)
);

DROP TABLE IF EXISTS pressure_module_data cascade;
CREATE TABLE pressure_module_data (
    `primary_id` varchar(32) NOT NULL  COMMENT 'ｉｄ',
    `meter_id` varchar(32) NOT NULL  COMMENT '仪表ｉｄ',
    `left_pressure` decimal(18,2)  COMMENT '左侧压力',
    `right_pressure` decimal(18,2)  COMMENT '右侧压力',
    `sys_read_time` DateTime  COMMENT '读取时间',
    `running_status` varchar(8)  COMMENT '设备状态',
    `mbus_offline` int  COMMENT '集中器掉线状态',
    `notes` varchar(256)  COMMENT '备注',
    `memo1` varchar(32)  COMMENT '保留１',
    `memo2` varchar(32)  COMMENT '保留２',
    `create_date` DateTime  COMMENT '创建时间',
    `create_user` varchar(32)  COMMENT '创建者',
    `update_date` DateTime  COMMENT '更新时间',
    `update_user` varchar(32)  COMMENT '更新者',
    PRIMARY KEY (`primary_id`)
);

DROP TABLE IF EXISTS elec_meter_data cascade;
CREATE TABLE elec_meter_data (
    `primary_id` varchar(32) NOT NULL  COMMENT 'ｉｄ',
    `meter_id` varchar(32) NOT NULL  COMMENT '仪表ｉｄ',
    `elec_power` decimal(18,2)  COMMENT '电量',
    `remain_power` decimal(18,2)  COMMENT '剩余电量',
    `buy_times` varchar(8)  COMMENT '购买时间',
    `running_state` varchar(8)  COMMENT '状态',
    `sys_read_time` datetime  COMMENT '读取时间',
    `mbus_offline` int  COMMENT '集中器在线状态',
    `notes` VARCHAR(32)  COMMENT '备注',
    `memo1` VARCHAR(32)  COMMENT '保留１',
    `memo2` VARCHAR(32)  COMMENT '保留２',
    `memo3` VARCHAR(32)  COMMENT '保留３',
    `create_date` datetime  COMMENT '创建时间',
    `create_user` varchar(32)  COMMENT '创建者',
    `update_date` datetime  COMMENT '更新时间',
    `update_user` varchar(32)  COMMENT '更新者',
    PRIMARY KEY (`primary_id`)
);

DROP TABLE IF EXISTS elec3_meter_data cascade;
CREATE TABLE elec3_meter_data (
    `primary_id` varchar(32) NOT NULL  COMMENT 'ｉｄ',
    `meter_id` varchar(32) NOT NULL  COMMENT '仪表ｉｄ',
    `elec_total` decimal(18,4)  COMMENT '电量',
    `voltagea` decimal(18,4)  COMMENT '电压Ａ',
    `voltageb` decimal(18,4)  COMMENT '电压Ｂ',
    `voltagec` decimal(18,4)  COMMENT '电压Ｃ',
    `currenta` decimal(18,4)  COMMENT '电流Ａ',
    `currentb` decimal(18,4)  COMMENT '电流Ｂ',
    `currentc` decimal(18,4)  COMMENT '电流Ｃ',
    `powera` decimal(18,4)  COMMENT '功率Ａ',
    `powerb` decimal(18,4)  COMMENT '功率Ｂ',
    `powerc` decimal(18,4)  COMMENT '功率Ｃ',
    `work_time` int  COMMENT '工作时间',
    `running_state` varchar(8)  COMMENT '状态',
    `sys_read_time` datetime  COMMENT '读取时间',
    `notes` varchar(32)  COMMENT '备注',
    `memo1` varchar(32)  COMMENT '保留１',
    `memo2` varchar(32)  COMMENT '保留２',
    `memo3` varchar(32)  COMMENT '保留３',
    `create_date` datetime  COMMENT '创建时间',
    `create_user` varchar(32)  COMMENT '创建者',
    `update_date` datetime  COMMENT '更新时间',
    `update_user` varchar(32)  COMMENT '更新者',
    PRIMARY KEY (`primary_id`)
);

DROP TABLE IF EXISTS event_info cascade;
CREATE TABLE event_info (
    `primary_id` varchar(32) NOT NULL  COMMENT 'ＩＤ',
    `company_id` varchar(32)  COMMENT '公司ＩＤ',
    `commuity_id` varchar(50)  COMMENT '小区ＩＤ',
    `station_id` varchar(50)  COMMENT '站ＩＤ',
    `dev_id` varchar(50) NOT NULL  COMMENT '所属设备',
    `type` varchar(256) NOT NULL  COMMENT '事件类型',
    `begin_time` datetime NOT NULL  COMMENT '事件时间',
    `des` varchar(32) NOT NULL  COMMENT '事件描述',
    `result` varchar(32)  COMMENT '结果',
    `result_person` varchar(32)  COMMENT '报告人',
    PRIMARY KEY (`primary_id`)
);

DROP TABLE IF EXISTS alarm_info cascade;
CREATE TABLE alarm_info (
    `primary_id` varchar(32) NOT NULL  COMMENT 'ＩＤ',
    `company_id` varchar(32)  COMMENT '公司ＩＤ',
    `commuity_id` varchar(50)  COMMENT '小区ＩＤ',
    `station_id` varchar(50)  COMMENT '站ＩＤ',
    `sys_id` varchar(50) NOT NULL  COMMENT '子系统标识',
    `dev_id` varchar(50) NOT NULL  COMMENT '所属设备',
    `type` varchar(50) NOT NULL  COMMENT '报警类型',
    `begin_time` datetime NOT NULL  COMMENT '报警时间',
    `des` varchar(100)  COMMENT '报警描述',
    `result` int  COMMENT '是否处理',
    `result_time` datetime  COMMENT '处理时间',
    `result_des` varchar(100)  COMMENT '备注',
    `result_person` varchar(50)  COMMENT '处理人',
    `level` varchar(50)  COMMENT '报警级别',
    `val` varchar(50)  COMMENT '报警值',
    `valset` varchar(50)  COMMENT '报警设定',
    PRIMARY KEY (`primary_id`)
);

DROP TABLE IF EXISTS user_running_data_total cascade;
CREATE TABLE user_running_data_total (
    `consumer_id` varchar(32) NOT NULL  COMMENT '用户ＩＤ',
    `heat` varchar(32) NOT NULL  COMMENT '累计热量',
    `power` decimal(8,3)  COMMENT '功率',
    `flow_speed` decimal(8,3)  COMMENT '瞬时流量',
    `flow_upper_limit` decimal(18,2)  COMMENT '流量上限',
    `in_temperature` decimal(5,2)  COMMENT '进水温度',
    `out_temperature` decimal(5,2)  COMMENT '回水温度',
    `room_temperature_read` decimal(5,2)  COMMENT '室温测量值',
    `room_temperature_set` decimal(5,2)  COMMENT '室温设置值',
    `outdoor_temperature` decimal(8,3)  COMMENT '室外温度值',
    `openness` decimal(8,2)  COMMENT '开度',
    `open_status` varchar(32)  COMMENT '开关状态：全开、中间、全关',
    `heating_area` decimal(8,2)  COMMENT '供暖面积',
    `heating_index` decimal(8,3)  COMMENT '耗热指标',
    `flowing_index` decimal(8,3)  COMMENT '流量指标',
    `heat_status` varchar(8)  COMMENT '供暖状态：供暖、停暖',
    `control_role` varchar(32)  COMMENT '控制权限：上位控制／就地控制',
    `regulation_mode` varchar(32)  COMMENT '调节方式：动态调节／通断调节',
    `room_temperature` varchar(32)  COMMENT '动作限制：无限制／平衡限制',
    `system_read_time` DateTime  COMMENT '时间',
    `company_id` varchar(32) NOT NULL  COMMENT '公司ＩＤ',
    `address` varchar(256)  COMMENT '地址全称：小区名＋楼名＋单元名＋楼名＋单元名＋室名',
    `notes` varchar(256)  COMMENT '备注',
    `memo1` varchar(32)  COMMENT '保留１',
    `memo2` varchar(32)  COMMENT '保留２',
    `memo3` varchar(32)  COMMENT '保留３',
    `memo4` varchar(32)  COMMENT '保留４',
    `memo5` varchar(32)  COMMENT '保留５',
    `memo6` varchar(32)  COMMENT '保留６',
    `create_date` DateTime  COMMENT '创建时间',
    `create_user` varchar(32)  COMMENT '创建者',
    `update_date` DateTime  COMMENT '更新时间',
    `update_user` varchar(32)  COMMENT '更新者',
    PRIMARY KEY (`consumer_id`)
);

DROP TABLE IF EXISTS user_running_data_histroy cascade;
CREATE TABLE user_running_data_histroy (
    `primary_id` varchar(32) NOT NULL  COMMENT 'ＩＤ',
    `consumer_id` varchar(32) NOT NULL  COMMENT '用户ＩＤ',
    `heat` varchar(32) NOT NULL  COMMENT '累计热量',
    `power` decimal(8,3)  COMMENT '功率',
    `flow_speed` decimal(8,3)  COMMENT '瞬时流量',
    `flow_upper_limit` decimal(18,2)  COMMENT '流量上限',
    `in_temperature` decimal(5,2)  COMMENT '进水温度',
    `out_temperature` decimal(5,2)  COMMENT '回水温度',
    `room_temperature_read` decimal(5,2)  COMMENT '室温测量值',
    `room_temperature_set` decimal(5,2)  COMMENT '室温设置值',
    `outdoor_temperature` decimal(8,3)  COMMENT '室外温度值',
    `openness` decimal(8,2)  COMMENT '开度',
    `open_status` varchar(32)  COMMENT '开关状态：全开、中间、全关',
    `heating_area` decimal(8,2)  COMMENT '供暖面积',
    `heating_index` decimal(8,3)  COMMENT '耗热指标',
    `flowing_index` decimal(8,3)  COMMENT '流量指标',
    `heat_status` varchar(8)  COMMENT '供暖状态：供暖、停暖',
    `control_role` varchar(32)  COMMENT '控制权限：上位控制／就地控制',
    `regulation_mode` varchar(32)  COMMENT '调节方式：动态调节／通断调节',
    `room_temperature` varchar(32)  COMMENT '动作限制：无限制／平衡限制',
    `system_read_time` DateTime  COMMENT '时间',
    `company_id` varchar(32) NOT NULL  COMMENT '公司ＩＤ',
    `address` varchar(256)  COMMENT '地址全称：小区名＋楼名＋单元名＋楼名＋单元名＋室名',
    `notes` varchar(256)  COMMENT '备注',
    `memo1` varchar(32)  COMMENT '保留１',
    `memo2` varchar(32)  COMMENT '保留２',
    `memo3` varchar(32)  COMMENT '保留３',
    `memo4` varchar(32)  COMMENT '保留４',
    `memo5` varchar(32)  COMMENT '保留５',
    `memo6` varchar(32)  COMMENT '保留６',
    `create_date` DateTime  COMMENT '创建时间',
    `create_user` varchar(32)  COMMENT '创建者',
    `update_date` DateTime  COMMENT '更新时间',
    `update_user` varchar(32)  COMMENT '更新者',
    PRIMARY KEY (`primary_id`)
);

DROP TABLE IF EXISTS house_heat_detail cascade;
CREATE TABLE house_heat_detail (
    `primary_id` varchar(32) NOT NULL  COMMENT '记录号ＩＤ：ＵＵＩＤ',
    `house_id` varchar(32) NOT NULL  COMMENT '用户ＩＤ：低保、事业、政府、工厂、门市、写字楼、学校、居民等',
    `meter_id` varchar(32)  COMMENT '仪表ＩＤ：水源、热源',
    `company_id` varchar(32) NOT NULL  COMMENT '公司ＩＤ',
    `year` varchar(256)  COMMENT '采暖年度',
    `meter_start` decimal(8, 2)  COMMENT '表起始码（读取）：触发读取时，读取比设置的时间延后且最近时间的读表记录的数值',
    `start_time` datetime  COMMENT '读取起始时间：可以选择，默认供暖开始时间，触发读取时，记录比设置的时间延后且有读表记录的最近时间',
    `meter_start_conf` decimal(8, 2)  COMMENT '起始确认表码：当没有数，自动等于表起始码（读取），当有数值时，保持不变，可以人工修改，并注明原因',
    `meter_end` decimal(8, 2)  COMMENT '表终止码（读取）：触发读取时，读取比设置的时间提前且最近时间的读表记录的数值',
    `end_time` datetime  COMMENT '读取终止时间：可以选择，默认供暖开始时间，触发读取时，记录比设置的时间提前且有读表记录的最近时间',
    `meter_end_conf` decimal(8, 2)  COMMENT '确认终止表码：当没有数，自动等于表终止码（读取），当有数值时，保持不变，可以人工修改，并注明原因',
    `adj_desc` varchar(256)  COMMENT '表码调整说明：注明表码调整的原因',
    `meter_read` decimal(10, 2)  COMMENT '热表码数：等于起始终止表码－起始确认表码',
    `heat_adj` decimal(10, 2)  COMMENT '热量调整：填写计量数因各种原因引起的调整值',
    `heat_adj_desc` varchar(256)  COMMENT '热量调整原因',
    `meter_adjust_sum` decimal(10, 2)  COMMENT '热量小计：等于热表码数＋热量调整',
    `memo1` varchar(32)  COMMENT '保留１',
    `memo2` varchar(32)  COMMENT '保留２',
    `create_date` datetime  COMMENT '创建时间',
    `create_user` varchar(32)  COMMENT '创建者',
    `update_date` datetime  COMMENT '更新时间',
    `update_user` varchar(32)  COMMENT '更新者',
    PRIMARY KEY (`primary_id`)
);

DROP TABLE IF EXISTS user_year_receivable_detail cascade;
CREATE TABLE user_year_receivable_detail (
    `primary_id` varchar(32) NOT NULL  COMMENT '记录号ＩＤ',
    `consumer_id` varchar(32) NOT NULL  COMMENT '用户ＩＤ',
    `year` varchar(32) NOT NULL  COMMENT '采暖年度',
    `charging_item` varchar(32) NOT NULL  COMMENT '费用项目：面积费用、热量费用、换热器费、费用调整、温度退费、少供退费、滞纳金、上年余额、余额结转等，过了收费截止时间。系统每天一计算，可晚上１２点触发，避开业务高峰期。过了收费截止时间。系统每天一计算，可晚上１２点触发，避开业务高峰期。
本表年度结转时，自动按一定规则生成，面积费用、热量费用、换热器费用、往年欠费按上年直接生成记录，滞纳金也固定产生一条记录。
',
    `sum` decimal(8, 2)  COMMENT '数量：收费面积或热量累计数',
    `unit_price` decimal(8, 2)  COMMENT '单价',
    `discount` decimal(8, 2)  COMMENT '折扣：默认为１',
    `total` decimal(8, 2)  COMMENT '总价：＝数量＊单价＊折扣',
    `certufucate` varchar(32) NOT NULL  COMMENT '原始凭证：链接文件',
    `approve_res` varchar(32) NOT NULL  COMMENT '审批结果：通过、未通过',
    `notes` varchar(256)  COMMENT '备注',
    `create_date` datetime  COMMENT '创建时间',
    `create_user` varchar(32)  COMMENT '创建者',
    `update_date` datetime  COMMENT '更新时间',
    `update_user` varchar(32)  COMMENT '更新者',
    `approve_serial` varchar(32)  COMMENT '审批流水号：同审批过程记录表',
    PRIMARY KEY (`primary_id`)
);

DROP TABLE IF EXISTS user_year_account_detail cascade;
CREATE TABLE user_year_account_detail (
    `consumer_id` varchar(32) NOT NULL  COMMENT '缴费流水号',
    `third_consumer_id` varchar(32)  COMMENT '平台流水号',
    `user_id` varchar(32) NOT NULL  COMMENT '用户ＩＤ',
    `year` varchar(32) NOT NULL  COMMENT '采暖年度',
    `account_cost` decimal(8, 2)  COMMENT '缴费金额：退费为负',
    `account_time` datetime  COMMENT '缴费日期',
    `account_type` varchar(32)  COMMENT '缴费方式：现金、刷卡、银行、微信、支付宝、网银等',
    `account_channel` varchar(32)  COMMENT '缴费通道',
    `isbill` varchar(8)  COMMENT '发票开具：是、否',
    `billno` varchar(32)  COMMENT '发票号',
    `bank_name` varchar(100)  COMMENT '银行名称',
    `bank_dept` varchar(100)  COMMENT '营业部名称',
    `teller` varchar(100)  COMMENT '柜员号',
    `account_user` varchar(100)  COMMENT '缴费账号',
    `notes` varchar(255)  COMMENT '备注',
    `create_date` datetime  COMMENT '创建者',
    `create_user` varchar(32)  COMMENT '创建时间',
    `update_date` datetime  COMMENT '更新者',
    `update_user` varchar(32)  COMMENT '更新时间',
    PRIMARY KEY (`consumer_id`)
);

DROP TABLE IF EXISTS price_define cascade;
CREATE TABLE price_define (
    `id` varchar(32) NOT NULL  COMMENT '热费单价ＩＤ',
    `price_id` varchar(32)  COMMENT '热费单价类型ＩＤ',
    `annual` varchar(100) NOT NULL  COMMENT '采暖季',
    `company_id` varchar(32) NOT NULL  COMMENT '公司ＩＤ',
    `pre_price` decimal(10,3)  COMMENT '预收单价',
    `area_price` decimal(10,3)  COMMENT '面积价格',
    `heat_price` decimal(10,3)  COMMENT '热量价格',
    `yeat_flag` int  COMMENT '是否当前采暖季：１当前采暖季',
    `notes` varchar(255)  COMMENT '备注',
    `overdue_begin_time` datetime  COMMENT '滞纳金计算日期',
    `overdue_rate` decimal(10,3)  COMMENT '滞纳金率',
    `instruct` varchar(255)  COMMENT '单价说明',
    PRIMARY KEY (`id`)
);

DROP TABLE IF EXISTS approve_proj_config cascade;
CREATE TABLE approve_proj_config (
    `type_id` varchar(32) NOT NULL  COMMENT '审批项目ＩＤ',
    `type` varchar(32) NOT NULL  COMMENT '公司ＩＤ',
    `approve_proj` varchar(32) NOT NULL  COMMENT '审批项目：费用调整、温度退费、少供退费、滞纳金等',
    `approve_require` varchar(32) NOT NULL  COMMENT '审批需求：是、否',
    `notes` varchar(255)  COMMENT '备注：情况说明',
    `create_date` datetime  COMMENT '创建者',
    `create_user` varchar(32)  COMMENT '创建时间',
    `update_date` datetime  COMMENT '更新者',
    `update_user` varchar(32)  COMMENT '更新时间',
    PRIMARY KEY (`type_id`)
);

DROP TABLE IF EXISTS approve_flow_config cascade;
CREATE TABLE approve_flow_config (
    `type_id` varchar(32) NOT NULL  COMMENT '审批项目ＩＤ',
    `type` varchar(32) NOT NULL  COMMENT '公司ＩＤ',
    `approve_proj` varchar(32) NOT NULL  COMMENT '审批项目：费用调整、温度退费、少供退费、滞纳金等',
    `flow_num` decimal(8, 2) NOT NULL  COMMENT '流程数：比如５',
    `flow_step` varchar(32) NOT NULL  COMMENT '流程阶段：比如１',
    `submit_dept` varchar(32)  COMMENT '提交部门ＩＤ',
    `submit_person` varchar(32)  COMMENT '提交员工ＩＤ',
    `overtime` decimal(8, 2)  COMMENT '审批时限：小时',
    `overtime_deal` varchar(32)  COMMENT '超时动作',
    `notes` varchar(255)  COMMENT '备注：情况说明',
    `create_date` datetime  COMMENT '创建者',
    `create_user` varchar(32)  COMMENT '创建时间',
    `update_date` datetime  COMMENT '更新者',
    `update_user` varchar(32)  COMMENT '更新时间',
    PRIMARY KEY (`type_id`)
);

DROP TABLE IF EXISTS approve_detail cascade;
CREATE TABLE approve_detail (
    `approve_serial` varchar(32)  COMMENT '审批流水号',
    `detail_approve_serial` varchar(32)  COMMENT '审批流水分号',
    `type_id` varchar(32) NOT NULL  COMMENT '审批项目ＩＤ',
    `type` varchar(32) NOT NULL  COMMENT '公司ＩＤ',
    `approve_proj` varchar(32) NOT NULL  COMMENT '审批项目：费用调整、温度退费、少供退费、滞纳金等',
    `flow_step` varchar(32) NOT NULL  COMMENT '流程阶段：比如１',
    `approve_dept` varchar(32)  COMMENT '审批部门ＩＤ',
    `approve_person` varchar(32)  COMMENT '审批员工ＩＤ',
    `approve_result` varchar(32)  COMMENT '审批结果：通过、未通过、退回',
    `last_step` varchar(32)  COMMENT '退回阶段',
    `opinion` varchar(32)  COMMENT '审批意见',
    `notes` varchar(255)  COMMENT '备注：情况说明',
    `create_date` datetime  COMMENT '创建者',
    `create_user` varchar(32)  COMMENT '创建时间',
    `update_date` datetime  COMMENT '更新者',
    `update_user` varchar(32)  COMMENT '更新时间',
    PRIMARY KEY (`type_id`)
);

DROP TABLE IF EXISTS user_year_heat cascade;
CREATE TABLE user_year_heat (
    `primary_id` varchar(32) NOT NULL  COMMENT '记录号ＩＤ',
    `consumer_id` varchar(32) NOT NULL  COMMENT '用户ＩＤ',
    `year` varchar(8) NOT NULL  COMMENT '采暖年度',
    `heating_area` decimal(8, 2) NOT NULL  COMMENT '供热面积：等于用户信息表中的供热面积，自动生成',
    `pay_area` decimal(10, 2)  COMMENT '收费面积：等于用户信息表中的收费面积，自动生成，当采暖状态为停暖时，为０',
    `seal_area` decimal(10, 2)  COMMENT '封堵面积',
    `area_price_type` varchar(32)  COMMENT '热费单价类型ＩＤ：不同的面积单价类型对应不同的面积单价，系统单独有配置表',
    `pay_type` varchar(32)  COMMENT '供热收费类型ＩＤ',
    `pre_price` decimal(10, 2)  COMMENT '预收单价：自动从收费单价配置表中取过来',
    `adv_heat_cost` decimal(10, 2)  COMMENT '预收热费金额：自动计算＝收费面积＊面积单价',
    `sum_receivable` decimal(10, 2)  COMMENT '热费应收合计：自动从《用户年度采暖费用明细表》中取合计数',
    `sum_account` decimal(10, 2)  COMMENT '热费实收合计：自动从《用户年度采暖缴费明细表》中取合计数',
    `margin_now` decimal(10, 2)  COMMENT '热费余额：自动计算等于热费实收合计－热费应收合计',
    `heating_status` decimal(10, 2)  COMMENT '供暖状态：供暖、停暖',
    `sysauto_heating_status` decimal(10, 2)  COMMENT '系统判断供暖状态',
    `around_heating` varchar(32)  COMMENT '周边采暖：正常，１侧停暖，２侧停暖，３侧停暖，４侧停暖',
    `total_value` decimal(8, 2)  COMMENT '总用热量：自动计算，等于用户年度用热明细表中的以每户为基础统计的热量消极的加和',
    `company_id` varchar(32) NOT NULL  COMMENT '公司ＩＤ',
    `actbegin_time` datetime  COMMENT '采暖实供时间：默认等于公司采暖开始时间，可根据用户实际情况修改',
    `actend_time` datetime  COMMENT '采暖实停时间：默认等于公司采暖停止时间，可根据用户实际情况修改',
    `heat_target` decimal(8, 2)  COMMENT '年度优化热指标',
    `rowno` varchar(32) NOT NULL  COMMENT '唯一识别号：与框架对应的主键',
    `notes` varchar(256)  COMMENT '备注',
    `memo1` varchar(32)  COMMENT '保留１',
    `memo2` varchar(32)  COMMENT '保留２',
    `create_date` datetime  COMMENT '创建时间',
    `create_user` varchar(32)  COMMENT '创建者',
    `update_date` datetime  COMMENT '更新时间',
    `update_user` varchar(32)  COMMENT '更新者',
    PRIMARY KEY (`primary_id`)
);

DROP TABLE IF EXISTS house_year_heatstate_detail cascade;
CREATE TABLE house_year_heatstate_detail (
    `house_id` varchar(32) NOT NULL  COMMENT '用户ＩＤ',
    `flow_id` varchar(32) NOT NULL  COMMENT '任务流水号：流程管理应尽可能和收费的审批使用一个模式',
    `year` varchar(32) NOT NULL  COMMENT '采暖年度',
    `company_id` varchar(32)  COMMENT '公司ＩＤ',
    `appr_person` varchar(32)  COMMENT '申请人',
    `appr_time` datetime  COMMENT '申请时间',
    `task_type` varchar(32)  COMMENT '任务类型：申请供暖、申请停暖',
    `task_contect` varchar(32)  COMMENT '任务内容',
    `handler` varchar(32)  COMMENT '受理人',
    `pay_state` varchar(32)  COMMENT '缴费状态',
    `emerge` varchar(32) NOT NULL  COMMENT '紧急程度',
    `overtime` varchar(32)  COMMENT '处理时限',
    `end_flag` varchar(32)  COMMENT '任务结束标识',
    `execute_person` varchar(32)  COMMENT '执行人',
    `execute_time` datetime  COMMENT '执行日期',
    `feedback_contect` varchar(32)  COMMENT '反馈内容',
    `end_state` varchar(32)  COMMENT '完成情况',
    `heat_state` varchar(32)  COMMENT '状态标志：正常采暖、关栓未完成、停暖、开栓未完成、供暖欠费等',
    `house_view` varchar(32)  COMMENT '用户意见',
    `house_sign` varchar(32)  COMMENT '用户签名',
    `visit_person` varchar(32)  COMMENT '回访人',
    `visit_time` datetime  COMMENT '回访时间',
    `visit_contect` varchar(32)  COMMENT '回访内容',
    `satisfy` varchar(32)  COMMENT '满意程度',
    `notes` varchar(255)  COMMENT '备注：情况说明',
    `create_date` datetime  COMMENT '创建者',
    `create_user` varchar(32)  COMMENT '创建时间',
    `update_date` datetime  COMMENT '更新者',
    `update_user` varchar(32)  COMMENT '更新时间',
    PRIMARY KEY (`flow_id`)
);

DROP TABLE IF EXISTS hrdp_f_accountinfo cascade;
CREATE TABLE hrdp_f_accountinfo (
    `zhbh` VARCHAR(32) NOT NULL  COMMENT '账户编号：ｔｒｕｎｃａｔｅ会将自增值变为１，需要重新从８００００００００开始',
    `zkhh` VARCHAR(32)  COMMENT '主客户号：主客户销户或注销则变为一个有效的客户',
    `jsh` VARCHAR(10)  COMMENT '集收号',
    `zhye` DECIMAL  COMMENT '账户余额',
    `sffs` VARCHAR(20)  COMMENT '收费方式',
    `khyh` VARCHAR(60)  COMMENT '开户银行',
    `yhkhhm` VARCHAR(60)  COMMENT '银行开户户名',
    `yhzh` VARCHAR(20)  COMMENT '银行账号',
    `sfzzsyh` CHAR  COMMENT '是否增值税用户',
    `sbh` VARCHAR(20)  COMMENT '识别号',
    `zcdz` VARCHAR(120)  COMMENT '注册地址',
    `zddz` VARCHAR(120)  COMMENT '账单地址',
    `spzt` CHAR  COMMENT '审批状态',
    `bz` VARCHAR(300)  COMMENT '备注',
    `whr` VARCHAR(20)  COMMENT '维护人',
    `whsj` VARCHAR(40)  COMMENT '维护时间',
    PRIMARY KEY (`zhbh`)
);

DROP TABLE IF EXISTS hrdp_f_classprice cascade;
CREATE TABLE hrdp_f_classprice (
    `ysxz` VARCHAR(20) NOT NULL  COMMENT '用水性质',
    `jtxh` INT NOT NULL  COMMENT '阶梯序号',
    `fylx` VARCHAR(2) NOT NULL  COMMENT '费用类型',
    `dj` decimal(5, 2)  COMMENT '单价',
    `bz` VARCHAR(300)  COMMENT '备注',
    `whr` VARCHAR(20)  COMMENT '维护人',
    `whsj` VARCHAR(40)  COMMENT '维护时间',
    PRIMARY KEY (`ysxz`,`jtxh`,`fylx`)
);

DROP TABLE IF EXISTS hrdp_f_classprice_his cascade;
CREATE TABLE hrdp_f_classprice_his (
    `primary_id` varchar(32) NOT NULL  COMMENT '记录号ＩＤ',
    `ysxz` VARCHAR(20) NOT NULL  COMMENT '用水性质',
    `jtxh` INT NOT NULL  COMMENT '阶梯序号',
    `fylx` VARCHAR(2) NOT NULL  COMMENT '费用类型',
    `kssj` VARCHAR(40) NOT NULL  COMMENT '开始时间',
    `dj` DECIMAL  COMMENT '单价',
    `bz` VARCHAR(300)  COMMENT '备注',
    `jssj` VARCHAR(40)  COMMENT '结束时间',
    `whr` VARCHAR(20)  COMMENT '维护人',
    `whsj` VARCHAR(40)  COMMENT '维护时间',
    PRIMARY KEY (`primary_id`)
);

DROP TABLE IF EXISTS hrdp_f_class cascade;
CREATE TABLE hrdp_f_class (
    `primary_id` varchar(32) NOT NULL  COMMENT '记录号ＩＤ',
    `jth` VARCHAR(10) NOT NULL  COMMENT '阶梯号',
    `jtxh` INT NOT NULL  COMMENT '阶梯序号',
    `ysxz` VARCHAR(20)  COMMENT '用水性质',
    `zxysrk` INT  COMMENT '最小用水人口',
    `zdysrk` INT  COMMENT '最大用水人口',
    `jffs` VARCHAR(2)  COMMENT '计费方式（０１：阶梯，０２：固定）',
    `kssl` INT  COMMENT '开始水量',
    `jssl` INT  COMMENT '结束水量',
    `jtzt` VARCHAR(2)  COMMENT '阶梯状态（０１：有效，０２：无效）',
    `bz` VARCHAR(300)  COMMENT '备注',
    `whr` VARCHAR(20)  COMMENT '维护人',
    `whsj` VARCHAR(40)  COMMENT '维护时间',
    PRIMARY KEY (`primary_id`)
);

DROP TABLE IF EXISTS hrdp_f_class_his cascade;
CREATE TABLE hrdp_f_class_his (
    `primary_id` varchar(32) NOT NULL  COMMENT '记录号ＩＤ',
    `jth` VARCHAR(10) NOT NULL  COMMENT '阶梯号',
    `jtxh` INT NOT NULL  COMMENT '阶梯序号',
    `kssj` VARCHAR(40) NOT NULL  COMMENT '开始时间',
    `ysxz` VARCHAR(20)  COMMENT '用水性质',
    `zxysrk` INT  COMMENT '最小用水人口',
    `zdysrk` INT  COMMENT '最大用水人口',
    `jffs` VARCHAR(2)  COMMENT '计费方式（０１：阶梯，０２：固定）',
    `kssl` INT  COMMENT '开始水量',
    `jssl` INT  COMMENT '结束水量',
    `jtzt` VARCHAR(2)  COMMENT '阶梯状态（０１：有效，０２：无效）',
    `bz` VARCHAR(300)  COMMENT '备注',
    `jssj` VARCHAR(40)  COMMENT '结束时间',
    `whr` VARCHAR(20)  COMMENT '维护人',
    `whsj` VARCHAR(40)  COMMENT '维护时间',
    PRIMARY KEY (`primary_id`)
);

DROP TABLE IF EXISTS hrdp_f_waterproperty_info cascade;
CREATE TABLE hrdp_f_waterproperty_info (
    `ysxz` VARCHAR(20) NOT NULL  COMMENT '用水性质',
    `jffs` VARCHAR(2)  COMMENT '计费方式（０１：阶梯，０２：固定）',
    `sfazrnxh` VARCHAR(2)  COMMENT '阶梯是否按自然年循环',
    `xhzq` INT  COMMENT '循环周期（Ｍ）',
    `zqzhkzyf` VARCHAR(2)  COMMENT '周期最后开账月份（０１：当月，０２：下一月）',
    `jfxsksyf` VARCHAR(2)  COMMENT '缴费系数开始月份（０１：当月，０２：下一月）',
    `jfxsjsyf` VARCHAR(2)  COMMENT '缴费系数结束月份（０１：当月，０２：下一月）',
    `sfqyznj` VARCHAR(2)  COMMENT '是否启用滞纳金',
    `djysqznj` INT  COMMENT '开账后第几月收取滞纳金',
    `znjxs` DECIMAL  COMMENT '滞纳金系数（按天）',
    `bz` VARCHAR(300)  COMMENT '备注',
    `whr` VARCHAR(20)  COMMENT '维护人',
    `whsj` VARCHAR(40)  COMMENT '维护时间',
    PRIMARY KEY (`ysxz`)
);

DROP TABLE IF EXISTS hrdp_f_waterproperty_info_his cascade;
CREATE TABLE hrdp_f_waterproperty_info_his (
    `primary_id` varchar(32) NOT NULL  COMMENT '记录号ＩＤ',
    `ysxz` VARCHAR(20) NOT NULL  COMMENT '用水性质',
    `kssj` VARCHAR(40) NOT NULL  COMMENT '开始时间',
    `jffs` VARCHAR(2)  COMMENT '计费方式（０１：阶梯，０２：固定）',
    `sfazrnxh` VARCHAR(2)  COMMENT '阶梯是否按自然年循环',
    `xhzq` INT  COMMENT '循环周期（Ｍ）',
    `zqzhkzyf` VARCHAR(2)  COMMENT '周期最后开账月份（０１：当月，０２：下一月）',
    `jfxsksyf` VARCHAR(2)  COMMENT '缴费系数开始月份（０１：当月，０２：下一月）',
    `jfxsjsyf` VARCHAR(2)  COMMENT '缴费系数结束月份（０１：当月，０２：下一月）',
    `sfqyznj` VARCHAR(2)  COMMENT '是否启用滞纳金',
    `djysqznj` INT  COMMENT '开账后第几月收取滞纳金',
    `znjxs` DECIMAL  COMMENT '滞纳金系数（按天）',
    `bz` VARCHAR(300)  COMMENT '备注',
    `jssj` VARCHAR(40)  COMMENT '结束时间',
    `whr` VARCHAR(20)  COMMENT '维护人',
    `whsj` VARCHAR(40)  COMMENT '维护时间',
    PRIMARY KEY (`primary_id`,`ysxz`,`kssj`)
);

DROP TABLE IF EXISTS meter_error_cancel cascade;
CREATE TABLE meter_error_cancel (
    `id` varchar(32) NOT NULL  COMMENT 'ｉｄ',
    `meter_id` varchar(32) NOT NULL  COMMENT '仪表ｉｄ',
    `err_code` varchar(32)  COMMENT '故障ＩＤ',
    `create_date` DateTime  COMMENT '创建时间',
    `create_user` varchar(32)  COMMENT '创建者',
    `update_date` DateTime  COMMENT '更新时间',
    `update_user` varchar(32)  COMMENT '更新者',
    PRIMARY KEY (`id`)
);

DROP TABLE IF EXISTS event_type_disc cascade;
CREATE TABLE event_type_disc (
    `id` varchar(32) NOT NULL  COMMENT '列',
    `type` varchar(16)  COMMENT '报警类型',
    `company` varchar(32)  COMMENT '公司',
    `point` varchar(32)  COMMENT '点位',
    `action` varchar(32)  COMMENT '行为',
    `event_set` decimal(18,4)  COMMENT '设定值',
    `eventdes` varchar(255)  COMMENT '描述',
    PRIMARY KEY (`id`)
);

DROP TABLE IF EXISTS menu_define cascade;
CREATE TABLE menu_define (
    `f_m_id` varchar(32) NOT NULL  COMMENT '父菜单编号',
    `m_id` varchar(32) NOT NULL  COMMENT '编号',
    `m_name` varchar(32)  COMMENT '名',
    `m_url` varchar(500)  COMMENT '链接地址：ｓｙｓ／ｍｅｎｕ／',
    `page_name` varchar(100)  COMMENT '页面名称',
    `icon` varchar(100)  COMMENT '菜单图标',
    `pagesort` int  COMMENT '链接排序',
    `ismenu` int  COMMENT '类型：０目录，１　菜单　２按钮',
    `perms` varchar(100)  COMMENT '权限标识：ｓｙｓ：ｍｅｎｕ：ｍｅｎｕ',
    PRIMARY KEY (`m_id`)
);

DROP TABLE IF EXISTS role_define cascade;
CREATE TABLE role_define (
    `role_id` varchar(32) NOT NULL  COMMENT '角色ＩＤ',
    `role_name` varchar(32)  COMMENT '角色名称',
    `role_state` int  COMMENT '角色状态：１有效　　０无效',
    `role_desc` varchar(128)  COMMENT '备注',
    PRIMARY KEY (`role_id`)
);

DROP TABLE IF EXISTS rl_menu_role cascade;
CREATE TABLE rl_menu_role (
    `id` varchar(32) NOT NULL  COMMENT 'ＩＤ',
    `role_id` varchar(32) NOT NULL  COMMENT '角色ＩＤ',
    `page_name` varchar(100) NOT NULL  COMMENT '菜单ＩＤ',
    PRIMARY KEY (`id`)
);

DROP TABLE IF EXISTS rl_user_role cascade;
CREATE TABLE rl_user_role (
    `id` varchar(32) NOT NULL  COMMENT 'ＩＤ',
    `userid` int  COMMENT '用户ＩＤ',
    `role_id` varchar(32)  COMMENT '角色ＩＤ',
    PRIMARY KEY (`id`)
);

DROP TABLE IF EXISTS rl_role_data cascade;
CREATE TABLE rl_role_data (
    `id` varchar(32) NOT NULL  COMMENT 'ＩＤ',
    `role_id` varchar(32)  COMMENT '角色ＩＤ',
    `column_value` varchar(32)  COMMENT '数据ＩＤ',
    `column_type` varchar(5)  COMMENT '数据类型：Ａ－－热力公司　　Ｅ－－换热站　Ｆ－－小区，Ｈ－－平衡阀，Ｇ－智能温控阀',
    `control_type` varchar(5)  COMMENT '控制类型：１不能控，２能控',
    PRIMARY KEY (`id`)
);

DROP TABLE IF EXISTS rl_role_valve cascade;
CREATE TABLE rl_role_valve (
    `id` varchar(32) NOT NULL  COMMENT 'ＩＤ',
    `role_id` varchar(32)  COMMENT '角色ＩＤ',
    `valve_id` varchar(32)  COMMENT '阀权限ＩＤ',
    `valve_type` varchar(32)  COMMENT '阀类型',
    PRIMARY KEY (`id`)
);

DROP TABLE IF EXISTS user_page_history cascade;
CREATE TABLE user_page_history (
    `primary_id` varchar(32) NOT NULL  COMMENT 'ＩＤ',
    `user_code` varchar(32)  COMMENT '用户ＩＤ',
    `username` varchar(32)  COMMENT '用户名',
    `intime` datetime  COMMENT '操作时间',
    `col` varchar(60)  COMMENT '操作页面',
    `act` varchar(60)  COMMENT '操作功能',
    PRIMARY KEY (`primary_id`)
);

DROP TABLE IF EXISTS user_login_history cascade;
CREATE TABLE user_login_history (
    `primary_id` varchar(32) NOT NULL  COMMENT 'ＩＤ',
    `user_code` varchar(32)  COMMENT '用户ＩＤ',
    `username` varchar(32)  COMMENT '用户名字',
    `intime` datetime  COMMENT '访问时间',
    `ip` varchar(32)  COMMENT '访问ｉｐ',
    `ipcity` varchar(50)  COMMENT '访问城市',
    PRIMARY KEY (`primary_id`)
);

DROP TABLE IF EXISTS user_login cascade;
CREATE TABLE user_login (
    `user_code` varchar(32) NOT NULL  COMMENT '用户登录ＩＤ',
    `user_psw` varchar(32)  COMMENT '用户密码',
    `username` varchar(32)  COMMENT '用户名',
    `identity_card` varchar(18)  COMMENT '身份证',
    `email` varchar(128)  COMMENT '邮箱',
    `phone` varchar(11)  COMMENT '手机',
    `tel` varchar(12)  COMMENT '固话',
    `notes` varchar(255)  COMMENT '备注',
    `lasttime` datetime  COMMENT '最后登录时间',
    `devide_id` varchar(255)  COMMENT '手机ＩＭＥＩ码',
    PRIMARY KEY (`user_code`)
);

DROP TABLE IF EXISTS map_point cascade;
CREATE TABLE map_point (
    `id` varchar(32) NOT NULL  COMMENT 'ＩＤ',
    `meter_type` varchar(32)  COMMENT '仪表类型',
    `map_point` varchar(32)  COMMENT '点坐标',
    `point_name` varchar(100)  COMMENT '点名称',
    `color` varchar(32)  COMMENT '点颜色',
    `point_type` varchar(32)  COMMENT '点类型：０－－楼　　１－－地图标记点',
    `notes` varchar(100)  COMMENT '备注',
    `company_id` varchar(32) NOT NULL  COMMENT '公司ＩＤ',
    `station_id` varchar(32)  COMMENT '换热站ＩＤ',
    `area_id` varchar(32)  COMMENT '地址ＩＤ',
    PRIMARY KEY (`id`)
);

DROP TABLE IF EXISTS map_line cascade;
CREATE TABLE map_line (
    `id` varchar(50) NOT NULL  COMMENT 'ＩＤ',
    `company_id` varchar(50)  COMMENT '公司ＩＤ',
    `meter_type` varchar(8)  COMMENT '仪表类型：０表示全部',
    `line_name` varchar(50)  COMMENT '线的名字',
    `points` varchar(2000)  COMMENT '坐标点',
    `stype` varchar(50)  COMMENT '类型－－实线，虚线',
    `weight` int  COMMENT '线宽',
    `color` varchar(50)  COMMENT '颜色',
    `opacity` varchar(50)  COMMENT '透明度',
    `notes` varchar(100)  COMMENT '备注',
    `station_id` varchar(32)  COMMENT '换热站ＩＤ',
    `area_id` varchar(32)  COMMENT '地址ＩＤ',
    PRIMARY KEY (`id`)
);

DROP TABLE IF EXISTS zone_province_info cascade;
CREATE TABLE zone_province_info (
    `z_p_id` varchar(32) NOT NULL  COMMENT 'ＩＤ',
    `z_p_provinceid` varchar(20)  COMMENT '省ＩＤ',
    `z_p_province` varchar(50)  COMMENT '省',
    PRIMARY KEY (`z_p_id`)
);

DROP TABLE IF EXISTS zone_city_info cascade;
CREATE TABLE zone_city_info (
    `z_c_id` varchar(32) NOT NULL  COMMENT 'ＩＤ',
    `z_c_cityid` varchar(20)  COMMENT '市ＩＤ',
    `z_c_city` varchar(50)  COMMENT '市',
    `z_c_provinceid` varchar(20)  COMMENT '省ＩＤ',
    PRIMARY KEY (`z_c_id`)
);

DROP TABLE IF EXISTS zone_area_info cascade;
CREATE TABLE zone_area_info (
    `z_a_id` varchar(32) NOT NULL  COMMENT 'ＩＤ',
    `z_a_areaid` varchar(20)  COMMENT '地区ＩＤ',
    `z_a_area` varchar(50)  COMMENT '地区',
    `z_a_cityid` varchar(20)  COMMENT '市ＩＤ',
    PRIMARY KEY (`z_a_id`)
);

DROP TABLE IF EXISTS grid_col cascade;
CREATE TABLE grid_col (
    `id` varchar(32) NOT NULL  COMMENT 'ＩＤ',
    `page_no` varchar(100) NOT NULL  COMMENT '页面名称',
    `grid_name` varchar(100) NOT NULL  COMMENT '页面数据名称',
    `display` varchar(100)  COMMENT '显示列名',
    `name` varchar(100) NOT NULL  COMMENT '绑定的数据名',
    `colid` varchar(100)  COMMENT '列ＩＤ',
    `align` varchar(100)  COMMENT '显示位置',
    `width` varchar(100)  COMMENT '列宽',
    `issort` varchar(100)  COMMENT '是否允许排序',
    `hide` varchar(100)  COMMENT '是否隐藏',
    `colsort` int  COMMENT '列顺序',
    `format` varchar(100)  COMMENT '列的自定义式样',
    `frozen` varchar(100)  COMMENT '是否固定',
    `site_type` varchar(16)  COMMENT '类型：ｈｅａｔ　－－热　　ｗａｔｅｒ－水　　　ａｌｌ－－全部',
    `others` varchar(1000)  COMMENT '其他',
    PRIMARY KEY (`id`)
);

DROP TABLE IF EXISTS grid_col_user cascade;
CREATE TABLE grid_col_user (
    `id` varchar(32) NOT NULL  COMMENT '自增列',
    `page_no` varchar(100)  COMMENT '页面名称',
    `grid_name` varchar(100)  COMMENT '页面数据名称',
    `name` varchar(100)  COMMENT '绑定的数据名',
    `hide` varchar(100)  COMMENT '是否隐藏',
    `colsort` int  COMMENT '列顺序',
    `user_id` varchar(32)  COMMENT '用户ＩＤ',
    PRIMARY KEY (`id`)
);

DROP TABLE IF EXISTS ticket_info cascade;
CREATE TABLE ticket_info (
    `id` varchar(32) NOT NULL  COMMENT 'ＩＤ：ＵＵＩＤ',
    `dept_id` varchar(32) NOT NULL  COMMENT '公司ＩＤ',
    `bill_code` varchar(32) NOT NULL  COMMENT '发票代码',
    `bill_no` varchar(32) NOT NULL  COMMENT '发票号码',
    `bill_type` datetime  COMMENT '发票类型',
    `in_user` varchar(32)  COMMENT '入库人',
    `in_time` datetime  COMMENT '入库日期',
    `rece_user` varchar(32)  COMMENT '领取人',
    `rece_time` datetime  COMMENT '领取日期',
    `return_user` varchar(32)  COMMENT '返还人',
    `return_time` datetime  COMMENT '返还日期',
    `del_user` varchar(32)  COMMENT '作废人',
    `del_time` datetime  COMMENT '作废日期',
    `use_flag` varchar(32)  COMMENT '使用标记：使用，未用，作废',
    `stock_flag` varchar(32)  COMMENT '库存状态：在库，出库',
    `source_id` varchar(32)  COMMENT '用途ＩＤ',
    `user` varchar(32)  COMMENT '使用人：收费员',
    `use_time` datetime  COMMENT '使用日期',
    `amount` decimal(18,2)  COMMENT '票据金额',
    `note` varchar(256)  COMMENT '备注',
    PRIMARY KEY (`id`)
);

DROP TABLE IF EXISTS station_warning_set cascade;
CREATE TABLE station_warning_set (
    `id` varchar(32) NOT NULL  COMMENT 'ＩＤ',
    `station_id` varchar(32)  COMMENT '站标识',
    `source_id` varchar(32)  COMMENT '源标识',
    `valset` decimal(18,2)  COMMENT '设定值',
    `warn_kind` varchar(32)  COMMENT '报警种类',
    PRIMARY KEY (`id`)
);

DROP TABLE IF EXISTS notice cascade;
CREATE TABLE notice (
    `id` varchar(32) NOT NULL  COMMENT '通告记录号',
    `company_id` varchar(32) NOT NULL  COMMENT '公司ＩＤ',
    `year` varchar(32)  COMMENT '采暖年度',
    `title` varchar(256)  COMMENT '通告标题',
    `context` varchar(256)  COMMENT '通告内容',
    `level` varchar(16)  COMMENT '通告级别：正常、较急、紧急',
    `theme_obj` varchar(256)  COMMENT '主题对象ＩＤ：公司ＩＤ、热源ＩＤ、换热站ＩＤ',
    `send_person` varchar(32)  COMMENT '发布人',
    `send_time` datetime  COMMENT '发布日期',
    `approve_person` varchar(128)  COMMENT '审批人',
    `approve_time` datetime  COMMENT '审批日期',
    `isapprove` varchar(64)  COMMENT '审批标识：同意、不同意',
    `aim1` varchar(8)  COMMENT '接受对象１（热网调度）：１：是；０：否',
    `aim2` varchar(8)  COMMENT '接受对象２（收费）：１：是；０：否',
    `aim3` varchar(8)  COMMENT '接受对象３（客服）：１：是；０：否',
    `filepath` varchar(256)  COMMENT '附件文件',
    `notes` varchar(256)  COMMENT '备注',
    `memo1` varchar(32)  COMMENT '保留１',
    `memo2` varchar(32)  COMMENT '保留２',
    `create_date` datetime  COMMENT '创建时间',
    `create_user` varchar(32)  COMMENT '创建者',
    `update_date` datetime  COMMENT '更新时间',
    `update_user` varchar(32)  COMMENT '更新者',
    PRIMARY KEY (`id`)
);

DROP TABLE IF EXISTS contract_heat_info cascade;
CREATE TABLE contract_heat_info (
    `id` varchar(32) NOT NULL  COMMENT '合同记录号',
    `contract_code` varchar(32) NOT NULL  COMMENT '合同编号',
    `contract_name` varchar(32)  COMMENT '合同名称',
    `a_name` varchar(256)  COMMENT '甲方名称',
    `b_name` varchar(256)  COMMENT '乙方名称',
    `scope` varchar(16)  COMMENT '入网范围',
    `area` decimal(8, 2)  COMMENT '入网面积',
    `unit_price` decimal(8, 2)  COMMENT '入网单价',
    `total` decimal(8, 2)  COMMENT '入网总价',
    `price_adj` varchar(128)  COMMENT '费用调整：正数，增加；负数，减免',
    `pay_type` varchar(50)  COMMENT '缴费方式：现金，刷卡，支付宝，微信，支票',
    `contact_name` varchar(64)  COMMENT '联系人',
    `contact_phone` varchar(8)  COMMENT '联系电话',
    `file_path` varchar(100)  COMMENT '文件名',
    `notes` varchar(256)  COMMENT '备注',
    `sign_date` datetime  COMMENT '签订日期',
    `memo1` varchar(32)  COMMENT '保留１',
    `memo2` varchar(32)  COMMENT '保留２',
    `create_date` datetime  COMMENT '创建时间',
    `create_user` varchar(32)  COMMENT '创建者',
    `update_date` datetime  COMMENT '更新时间',
    `update_user` varchar(32)  COMMENT '更新者',
    `approve_serial` varchar(32)  COMMENT '审批流水号',
    PRIMARY KEY (`id`)
);

DROP TABLE IF EXISTS customer_service_order cascade;
CREATE TABLE customer_service_order (
    `id` varchar(32) NOT NULL  COMMENT '工单流水号',
    `category_type` varchar(32)  COMMENT '工单类别：用户报修、公共报修、供热咨询、测温申请、暖费查询、表扬投诉',
    `category_c_type` varchar(256)  COMMENT '工单类型：用户（暖气漏水、暖气不热，室温不足、重复报修、其他），公共报修（管网漏水、管道破坏、保温破损、井盖丢失、施工质量、火灾报警、财产偷盗、非法施工、其他）',
    `user_id` varchar(32)  COMMENT '用户ＩＤ',
    `category_addr` varchar(256)  COMMENT '报修地址：用户报修时，选择用户的地址；公共报修时，手工填写',
    `year` varchar(32) NOT NULL  COMMENT '采暖年度',
    `company_id` varchar(32)  COMMENT '公司ＩＤ',
    `incoming_tel` varchar(32)  COMMENT '来电电话',
    `incoming_person` varchar(32)  COMMENT '来电人',
    `processor` varchar(32)  COMMENT '受理人',
    `process_time` datetime  COMMENT '受理时间',
    `stage` varchar(32)  COMMENT '工单阶段：处理，审核，回访',
    `status` varchar(32)  COMMENT '工单状态：未确认，已确认，已分派，已接收，已解决，已退回',
    `priority` varchar(16)  COMMENT '紧急程度：紧急，正常',
    `limit_time` decimal(8, 2)  COMMENT '处理期限：ｘ小时',
    `source` varchar(32)  COMMENT '工单来源：来电，前台、网络',
    `contact_name` varchar(64)  COMMENT '联系人',
    `contact_phone` varchar(32)  COMMENT '联系电话',
    `desc` varchar(256)  COMMENT '工单描述',
    `file_path` varchar(100)  COMMENT '工单附件：附件',
    `claim_times` int  COMMENT '催办次数',
    `order_time` datetime  COMMENT '预约时间',
    `distribution_time` datetime  COMMENT '工单派发日期',
    `accept_time` datetime  COMMENT '工单接受日期',
    `arrival_time` datetime  COMMENT '现场达到时间',
    `execut_dept` varchar(32)  COMMENT '执行部门',
    `execut_person` varchar(32)  COMMENT '执行人',
    `execut_record` varchar(100)  COMMENT '执行情况',
    `execut_time` datetime  COMMENT '执行日期',
    `over_flag` varchar(32)  COMMENT '工单结束标识：结束、未结束',
    `temp_result` varchar(100)  COMMENT '测温结果',
    `temp_serial` varchar(32)  COMMENT '测温流水号',
    `visit_person` varchar(32)  COMMENT '回访人',
    `visit_contect` varchar(100)  COMMENT '回访内容',
    `visit_time` datetime  COMMENT '回访时间',
    `satisfy` varchar(100)  COMMENT '服务评价：非常满意、满意、一般、不满意、投诉、情况不符',
    `user_sign` varchar(32)  COMMENT '用户签名',
    `user_opinion` varchar(256)  COMMENT '用户意见',
    `memo1` varchar(32)  COMMENT '保留１',
    `memo2` varchar(32)  COMMENT '保留２',
    `create_date` datetime  COMMENT '创建时间',
    `create_user` varchar(32)  COMMENT '创建者',
    `update_date` datetime  COMMENT '更新时间',
    `update_user` varchar(32)  COMMENT '更新者',
    PRIMARY KEY (`id`)
);

DROP TABLE IF EXISTS customer_service_order_detail cascade;
CREATE TABLE customer_service_order_detail (
    `id` varchar(32) NOT NULL  COMMENT '工单明细ｉｄ',
    `order_id` varchar(32) NOT NULL  COMMENT '工单ｉｄ',
    `stage` varchar(32)  COMMENT '工单阶段：处理，审核，回访',
    `status` varchar(32)  COMMENT '工单状态：未确认，已确认，已分派，已接收，已解决，已退回',
    `desc` varchar(256)  COMMENT '描述',
    `file_path` varchar(100)  COMMENT '文件名：附件',
    `dept_id` varchar(10)  COMMENT '部门ｉｄ',
    `user_id` varchar(10)  COMMENT '操作人员',
    `memo1` varchar(32)  COMMENT '保留１',
    `memo2` varchar(32)  COMMENT '保留２',
    `create_date` datetime  COMMENT '创建时间',
    `create_user` varchar(32)  COMMENT '创建者',
    `update_date` datetime  COMMENT '更新时间',
    `update_user` varchar(32)  COMMENT '更新者',
    PRIMARY KEY (`id`)
);

DROP TABLE IF EXISTS sms_template cascade;
CREATE TABLE sms_template (
    `id` varchar(32) NOT NULL  COMMENT '短信模板ｉｄ',
    `dept_id` varchar(32) NOT NULL  COMMENT '公司ｉｄ',
    `title` varchar(50) NOT NULL  COMMENT '短信模板主题',
    `contents` varchar(256)  COMMENT '短信模板内容',
    `memo1` varchar(32)  COMMENT '保留１',
    `memo2` varchar(32)  COMMENT '保留２',
    `create_date` datetime  COMMENT '创建时间',
    `create_user` varchar(32)  COMMENT '创建者',
    `update_date` datetime  COMMENT '更新时间',
    `update_user` varchar(32)  COMMENT '更新者',
    PRIMARY KEY (`id`)
);

DROP TABLE IF EXISTS sms_info cascade;
CREATE TABLE sms_info (
    `id` varchar(32) NOT NULL  COMMENT '短信ｉｄ',
    `year_id` varchar(32) NOT NULL  COMMENT '年度ｉｄ',
    `dept_id` varchar(32) NOT NULL  COMMENT '公司ｉｄ',
    `title` varchar(50) NOT NULL  COMMENT '短信主题',
    `contents` varchar(256)  COMMENT '短信内容',
    `receiver` varchar(32)  COMMENT '接收人',
    `receiver_phone` varchar(32)  COMMENT '手机号',
    `status` varchar(10)  COMMENT '发送状态：成功，失败',
    `memo1` varchar(32)  COMMENT '保留１',
    `memo2` varchar(32)  COMMENT '保留２',
    `create_date` datetime  COMMENT '创建时间',
    `create_user` varchar(32)  COMMENT '创建者',
    `update_date` datetime  COMMENT '更新时间',
    `update_user` varchar(32)  COMMENT '更新者',
    PRIMARY KEY (`id`)
);

DROP TABLE IF EXISTS mbus_noid cascade;
CREATE TABLE mbus_noid (
    `id` varchar(32) NOT NULL  COMMENT 'ＩＤ',
    `mbus_code` varchar(16) NOT NULL  COMMENT '集中器号',
    `address_1st` varchar(8)  COMMENT '一级地址',
    `address_2nd` varchar(32)  COMMENT '二级地址',
    `date_begin` datetime  COMMENT '开始时间',
    `date_now` datetime  COMMENT '当前时间',
    `meter_type` varchar(8)  COMMENT '仪表类型',
    `notes` varchar(10)  COMMENT '备注',
    `memo1` varchar(32)  COMMENT '保留１',
    `memo2` varchar(32)  COMMENT '保留２',
    `create_date` datetime  COMMENT '创建时间',
    `create_user` varchar(32)  COMMENT '创建者',
    `update_date` datetime  COMMENT '更新时间',
    `update_user` varchar(32)  COMMENT '更新者',
    PRIMARY KEY (`id`)
);

DROP TABLE IF EXISTS customer_temp_detail cascade;
CREATE TABLE customer_temp_detail (
    `temp_serial` varchar(32) NOT NULL  COMMENT '测温流水号',
    `category_serial` varchar(32)  COMMENT '工单流水号',
    `user_id` varchar(32) NOT NULL  COMMENT '用户ＩＤ',
    `year` varchar(32)  COMMENT '采暖年度',
    `company_id` varchar(32)  COMMENT '公司ＩＤ',
    `room_loc1` varchar(32)  COMMENT '１房间位置',
    `room_temp1` decimal(18,2)  COMMENT '１房间温度',
    `room_loc2` varchar(32)  COMMENT '２房间位置',
    `room_temp2` decimal(18,2)  COMMENT '２房间温度',
    `room_loc3` varchar(32)  COMMENT '３房间位置',
    `room_temp3` decimal(18,2)  COMMENT '３房间温度',
    `room_loc4` varchar(32)  COMMENT '４房间位置',
    `room_temp4` decimal(18,2)  COMMENT '４房间温度',
    `room_loc5` varchar(32)  COMMENT '５房间位置',
    `room_temp5` decimal(18,2)  COMMENT '５房间温度',
    `room_loc6` varchar(32)  COMMENT '６房间位置',
    `room_temp6` decimal(18,2)  COMMENT '６房间温度',
    `room_loc7` varchar(32)  COMMENT '７房间位置',
    `room_temp7` decimal(18,2)  COMMENT '７房间温度',
    `room_loc8` varchar(32)  COMMENT '８房间位置',
    `room_temp8` decimal(18,2)  COMMENT '８房间温度',
    `avg_temp` decimal(18,2)  COMMENT '平均室温',
    `desc` varchar(256)  COMMENT '测温说明',
    `approval_flag` varchar(32)  COMMENT '审批标识',
    `access` varchar(256)  COMMENT '测温附件',
    `notes` varchar(256)  COMMENT '备注',
    `memo1` varchar(32)  COMMENT '保留１',
    `memo2` varchar(32)  COMMENT '保留２',
    `create_date` datetime  COMMENT '创建时间',
    `create_user` varchar(32)  COMMENT '创建者',
    `update_date` datetime  COMMENT '更新时间',
    `update_user` varchar(32)  COMMENT '更新者',
    PRIMARY KEY (`temp_serial`)
);

DROP TABLE IF EXISTS mbus_crsq_history cascade;
CREATE TABLE mbus_crsq_history (
    `id` varchar(32) NOT NULL  COMMENT 'ＩＤ',
    `mbus_code` varchar(32) NOT NULL  COMMENT '集中器号',
    `sys_read_time` datetime NOT NULL  COMMENT '系统读取时间',
    `crsq` varchar(16)  COMMENT '信号质量',
    `send_times` varchar(16)  COMMENT '发送次数',
    `send_times_success` varchar(16)  COMMENT '发送成功次数',
    PRIMARY KEY (`id`)
);
