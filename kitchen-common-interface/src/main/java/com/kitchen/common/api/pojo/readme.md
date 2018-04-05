## 领域模型说明

##### 一、PO:persistant object  
持久对象。可以看成是与数据库中的表相映射的java对象，最简单的PO就是对应数据库中某个表中的一条记录，多个记录可以用PO的集合。PO中应该不包含任何对数据库的操作。   

在kitchen框架中，PO对象通常保存在core-interface模块的pojo.po包中；

##### 二、VO:value object
显示层对象。通常用于业务层之间的数据传递，和PO一样也是仅仅包含数据而已。但应是抽象出的业务对象,可以和表对应,也可以不,这根据业务的需要.个人觉得同DTO(数据传输对象),在web上传递。   

在kitchen框架中，VO对象通常保存在core-interface模块的pojo.vo包中；   
VO对象一般继承于PO对象，在PO对象的基础上扩展业务属性；   
对于特殊业务，也可存在于控制层（com.kitchen.common.api-portal或web-manage中）；

##### 三、DAO:data access object
数据访问对象。此对象用于访问数据库，通常和PO结合使用，DAO中包含了各种数据库的操作方法。通过它的方法,结合PO对数据库进行相关的操作。

在kitchen框架中，DAO层的代码由MyBatis自动生成，生成后的为\***Mapper接口和\***Mapper.xml，通常保存在core-business模块的dao包中；

##### 四、BO:business object
业务对象。封装业务逻辑的java对象，通过调用DAO方法,结合PO、VO进行业务操作。   

在kitchen框架中，BO的代码通常放在core-business模块的service包中；   

##### 五、POJO:plain ordinary java object
简单无规则java对象。包含了VO和PO。  

在kitchen框架中，POJO作为一个包名存在于core-interface模块下，用于存放VO、PO等模型；


##阿里巴巴开发规范
##### DO（Data Object）：与数据库表结构一一对应，通过 DAO 层向上传输数据源对象。
##### DTO（Data Transfer Object）：数据传输对象，Service 和 Manager 向外传输的对象。
##### BO（Business Object）：业务对象。可以由 Service 层输出的封装业务逻辑的对象。
##### QUERY：数据查询对象，各层接收上层的查询请求。注：超过 2 个参数的查询封装，禁止使用 Map 类来传输。
##### VO（View Object）：显示层对象，通常是 Web 向模板渲染引擎层传输的对象。 


VO:value object
显示层对象。通常用于业务层之间的数据传递，和PO一样也是仅仅包含数据而已。但应是抽象出的业务对象,可以和表对应,也可以不,这根据业务的需要.个人觉得同DTO(数据传输对象),在web上传递。   
VO对象可以继承自PO或BO对象

PO:persistant object  
持久对象。可以看成是与数据库中的表相映射的java对象，最简单的PO就是对应数据库中某个表中的一条记录，多个记录可以用PO的集合。PO中应该不包含任何对数据库的操作。   
同DO概念一致
DO（Data Object）：与数据库表结构一一对应，通过 DAO 层向上传输数据源对象。

BO（Business Object）：业务对象。可以由 Service 层输出的封装业务逻辑的对象。
BO对象可以继承自PO对象

RO（Request Object）：请求参数对象