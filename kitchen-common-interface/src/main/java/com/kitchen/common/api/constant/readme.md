# constant包下可放：enum、static等确定的常量数据结构

可通过enum定义在一定值范围内的任何数据结构  
可通过enum定义状态（state）  
可通过enum定义范围（range）  

合适的情况下可以使用中文定义枚举值：  
定义
```
public enum Car {
    
    奥迪("AUDI", 1),
    宝马("BMW", 2);

    private String _desc;
    private Integer _code;

    OrderShipState(String desc, Integer code) {
        this._desc = desc;
        this._code = code;
    }

    public String getDesc() {
        return this._desc;
    }

    public Integer getCode() {
        return _code;
    }
}
```

使用
```
System.out.println(Car.奥迪);
System.out.println(Car.奥迪.getDesc());
```