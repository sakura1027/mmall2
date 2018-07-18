**业务基本流程**
新用户注册，校验用户名是否存在 
用户名、密码、确认密码、电话、邮箱、密码提示问题和密码提示答案
然后注册成功，进行登录（这里也可以点击忘记密码进行密码找回）
登录成功后，在我的mmall里面可以修改个人信息

首页可以动态的搜索 i关键字搜索，海尔、美的、华为和iPhone
也会有分类的id查询，看到手机分类点进去，可以按价格动态排序 
加入购物车，查看购物车 
全选/单选，购物车的价格会实时的计算，并且可以增加和减少数量 
去结算，新增收货地址，保存收货地址，地址添加成功，可以对其编辑和删除 
提交订单，生成订单号，扫码支付二维码 
您的订单支付成功！ 
查看订单，订单信息，商品清单，点击我的订单，分页

后台 
登录，订单管理，查看，立即发货，按订单号查询，分页 
品类管理，查看子品类，新增品类，修改名称 
商品管理，按商品id查询，修改上下架

**数据表结构设计**
**1. 用户表**
**主键：id，唯一索引：username**

    DROP TABLE IF EXISTS `mmall_user`;
    CREATE TABLE `mmall_user` (
      `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '用户表id',
      `username` varchar(50) NOT NULL COMMENT '用户名',
      `password` varchar(50) NOT NULL COMMENT '用户密码，MD5加密',
      `email` varchar(50) DEFAULT NULL,
      `phone` varchar(20) DEFAULT NULL,
      `question` varchar(100) DEFAULT NULL COMMENT '找回密码问题',
      `answer` varchar(100) DEFAULT NULL COMMENT '找回密码答案',
      `role` int(4) NOT NULL COMMENT '角色1-管理员,0-普通用户',
      `create_time` datetime NOT NULL COMMENT '创建时间',
      `update_time` datetime NOT NULL COMMENT '最后一次更新时间',
      PRIMARY KEY (`id`),
      UNIQUE KEY `user_name_unique` (`username`) USING BTREE
    ) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8;

**2. 分类表**
**主键：id**

    DROP TABLE IF EXISTS `mmall_category`;
    CREATE TABLE `mmall_category` (
      `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '类别Id',
      `parent_id` int(11) DEFAULT NULL COMMENT '父类别id当id=0时说明是根节点,一级类别',
      `name` varchar(50) DEFAULT NULL COMMENT '类别名称',
      `status` tinyint(1) DEFAULT '1' COMMENT '类别状态1-正常,2-已废弃',
      `sort_order` int(4) DEFAULT NULL COMMENT '排序编号,同类展示顺序,数值相等则自然排序',
      `create_time` datetime DEFAULT NULL COMMENT '创建时间',
      `update_time` datetime DEFAULT NULL COMMENT '更新时间',
      PRIMARY KEY (`id`)
    ) ENGINE=InnoDB AUTO_INCREMENT=100032 DEFAULT CHARSET=utf8;
    
**3. 产品表**
**主键：id**

    DROP TABLE IF EXISTS `mmall_product`;
    CREATE TABLE `mmall_product` (
      `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '商品id',
      `category_id` int(11) NOT NULL COMMENT '分类id,对应mmall_category表的主键',
      `name` varchar(100) NOT NULL COMMENT '商品名称',
      `subtitle` varchar(200) DEFAULT NULL COMMENT '商品副标题',
      `main_image` varchar(500) DEFAULT NULL COMMENT '产品主图,url相对地址',
      `sub_images` text COMMENT '图片地址,json格式,扩展用',
      `detail` text COMMENT '商品详情',
      `price` decimal(20,2) NOT NULL COMMENT '价格,单位-元保留两位小数',
      `stock` int(11) NOT NULL COMMENT '库存数量',
      `status` int(6) DEFAULT '1' COMMENT '商品状态.1-在售 2-下架 3-删除',
      `create_time` datetime DEFAULT NULL COMMENT '创建时间',
      `update_time` datetime DEFAULT NULL COMMENT '更新时间',
      PRIMARY KEY (`id`)
    ) ENGINE=InnoDB AUTO_INCREMENT=30 DEFAULT CHARSET=utf8;
    
**4. 购物车表**
**主键：id，单索引：user_id**

    DROP TABLE IF EXISTS `mmall_cart`;
    CREATE TABLE `mmall_cart` (
      `id` int(11) NOT NULL AUTO_INCREMENT,
      `user_id` int(11) NOT NULL,
      `product_id` int(11) DEFAULT NULL COMMENT '商品id',
      `quantity` int(11) DEFAULT NULL COMMENT '数量',
      `checked` int(11) DEFAULT NULL COMMENT '是否选择,1=已勾选,0=未勾选',
      `create_time` datetime DEFAULT NULL COMMENT '创建时间',
      `update_time` datetime DEFAULT NULL COMMENT '更新时间',
      PRIMARY KEY (`id`),
      KEY `user_id_index` (`user_id`) USING BTREE
    ) ENGINE=InnoDB AUTO_INCREMENT=146 DEFAULT CHARSET=utf8;
    
**5. 支付信息表**
**主键：id**

    DROP TABLE IF EXISTS `mmall_pay_info`;
    CREATE TABLE `mmall_pay_info` (
      `id` int(11) NOT NULL AUTO_INCREMENT,
      `user_id` int(11) DEFAULT NULL COMMENT '用户id',
      `order_no` bigint(20) DEFAULT NULL COMMENT '订单号',
      `pay_platform` int(10) DEFAULT NULL COMMENT '支付平台:1-支付宝,2-微信',
      `platform_number` varchar(200) DEFAULT NULL COMMENT '支付宝支付流水号',
      `platform_status` varchar(20) DEFAULT NULL COMMENT '支付宝支付状态',
      `create_time` datetime DEFAULT NULL COMMENT '创建时间',
      `update_time` datetime DEFAULT NULL COMMENT '更新时间',
      PRIMARY KEY (`id`)
    ) ENGINE=InnoDB AUTO_INCREMENT=61 DEFAULT CHARSET=utf8;

**6. 订单表**
**主键：id，唯一索引：order_no**

    DROP TABLE IF EXISTS `mmall_order`;
    CREATE TABLE `mmall_order` (
      `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '订单id',
      `order_no` bigint(20) DEFAULT NULL COMMENT '订单号',
      `user_id` int(11) DEFAULT NULL COMMENT '用户id',
      `shipping_id` int(11) DEFAULT NULL,
      `payment` decimal(20,2) DEFAULT NULL COMMENT '实际付款金额,单位是元,保留两位小数',
      `payment_type` int(4) DEFAULT NULL COMMENT '支付类型,1-在线支付',
      `postage` int(10) DEFAULT NULL COMMENT '运费,单位是元',
      `status` int(10) DEFAULT NULL COMMENT '订单状态:0-已取消-10-未付款，20-已付款，40-已发货，50-交易成功，60-交易关闭',
      `payment_time` datetime DEFAULT NULL COMMENT '支付时间',
      `send_time` datetime DEFAULT NULL COMMENT '发货时间',
      `end_time` datetime DEFAULT NULL COMMENT '交易完成时间',
      `close_time` datetime DEFAULT NULL COMMENT '交易关闭时间',
      `create_time` datetime DEFAULT NULL COMMENT '创建时间',
      `update_time` datetime DEFAULT NULL COMMENT '更新时间',
      PRIMARY KEY (`id`),
      UNIQUE KEY `order_no_index` (`order_no`) USING BTREE
    ) ENGINE=InnoDB AUTO_INCREMENT=118 DEFAULT CHARSET=utf8;

**7. 订单明细表**
**主键：id，单索引：order_no，组合索引：user_id order_no**

    DROP TABLE IF EXISTS `mmall_order_item`;
    CREATE TABLE `mmall_order_item` (
      `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '订单子表id',
      `user_id` int(11) DEFAULT NULL,
      `order_no` bigint(20) DEFAULT NULL,
      `product_id` int(11) DEFAULT NULL COMMENT '商品id',
      `product_name` varchar(100) DEFAULT NULL COMMENT '商品名称',
      `product_image` varchar(500) DEFAULT NULL COMMENT '商品图片地址',
      `current_unit_price` decimal(20,2) DEFAULT NULL COMMENT '生成订单时的商品单价，单位是元,保留两位小数',
      `quantity` int(10) DEFAULT NULL COMMENT '商品数量',
      `total_price` decimal(20,2) DEFAULT NULL COMMENT '商品总价,单位是元,保留两位小数',
      `create_time` datetime DEFAULT NULL,
      `update_time` datetime DEFAULT NULL,
      PRIMARY KEY (`id`),
      KEY `order_no_index` (`order_no`) USING BTREE,
      KEY `order_no_user_id_index` (`user_id`,`order_no`) USING BTREE
    ) ENGINE=InnoDB AUTO_INCREMENT=135 DEFAULT CHARSET=utf8;
    
**8. 收货地址表**
**主键：id**

    DROP TABLE IF EXISTS `mmall_shipping`;
    CREATE TABLE `mmall_shipping` (
      `id` int(11) NOT NULL AUTO_INCREMENT,
      `user_id` int(11) DEFAULT NULL COMMENT '用户id',
      `receiver_name` varchar(20) DEFAULT NULL COMMENT '收货姓名',
      `receiver_phone` varchar(20) DEFAULT NULL COMMENT '收货固定电话',
      `receiver_mobile` varchar(20) DEFAULT NULL COMMENT '收货移动电话',
      `receiver_province` varchar(20) DEFAULT NULL COMMENT '省份',
      `receiver_city` varchar(20) DEFAULT NULL COMMENT '城市',
      `receiver_district` varchar(20) DEFAULT NULL COMMENT '区/县',
      `receiver_address` varchar(200) DEFAULT NULL COMMENT '详细地址',
      `receiver_zip` varchar(6) DEFAULT NULL COMMENT '邮编',
      `create_time` datetime DEFAULT NULL,
      `update_time` datetime DEFAULT NULL,
      PRIMARY KEY (`id`)
    ) ENGINE=InnoDB AUTO_INCREMENT=33 DEFAULT CHARSET=utf8;

**各模块功能接口**
1.用户模块：登录、用户名验证、注册、忘记密码、提交问题答案、重置密码、获取用户信息、更新用户信息和退出登录
2.分类管理模块：获取节点、增加节点、修改名字、获取分类id和递归子节点id
3.商品模块：前台包括产品搜索、动态排序列表和商品详情，后台包括商品列表、商品搜索、图片上传、富文本上传、商品详情、商品上下架、增加商品和更新商品
4.购物车模块：加入商品、更新商品数、查询商品数、移除商品、单选/取消、全选/取消和购物车列表
5.收货地址模块：添加地址、删除地址、更新地址、地址列表、地址分页和地址详情
6.支付模块：支付宝沙箱、支付宝集成、支付回调和查询支付状态
7.订单模块：前台包括创建订单、商品信息、订单列表、订单详情和取消订单，后台包括订单列表、订单搜索、订单详情和订单发货
