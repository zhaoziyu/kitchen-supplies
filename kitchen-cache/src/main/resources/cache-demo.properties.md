# 缓存配置

## 本地缓存配置
### 是否开启本地缓存服务
kitchen.cache.local.open=true

## Redis缓存配置
### 是否开启Redis缓存服务
kitchen.cache.redis.open=false
### Redis服务的IP地址
kitchen.cache.redis.ip=127.0.0.1
### Redis服务的端口号
kitchen.cache.redis.port=6379
### 连接实例的最大数目，默认值为8；如果为-1，则表示不限制；如果pool已经分配了maxTotal个jedis实例，则此时pool的状态为exhausted(耗尽)；
kitchen.cache.redis.max-total=1024
### 控制一个pool最多有多少个状态为idle(空闲的)的jedis实例，默认值8。
kitchen.cache.redis.max-idle=256
### 等待可用连接的最大时间，单位毫秒，默认值为-1，表示永不超时。如果超过等待时间，则直接抛出JedisConnectionException；
kitchen.cache.redis.max-wait-millis=5000
### Redis连接的超时时间
kitchen.cache.redis.connection-timeout=5000
### Redis服务的密码
kitchen.cache.redis.password=XsJYvyrytaOU52Kn2Vvy