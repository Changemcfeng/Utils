### Utils
#### DateUtils 时间工具类：
*功能*：
 - localDateTime localDate转化成date，string <br>
 - 统计本周内的日期，本月的日期，前n天到今天的日期，后n天的到今天的日期 <br>
 - 日期的增加n天或者减少n天 <br>
 - 时间比较（data，LocalDate，LocalDateTime）<br>
 -  判断当前时间是否在时间段里（data，LocalDate，LocalDateTime）<br>

#### DateUtils 数组交集工具类：
*功能*：
 - 求数组交集（interSection）
 - 求数组并集（unionSection）
 - 求数组差集（differenceSet）
 - 数组转化成set（arrayIntoSet）
 - set转化成数组（setIntoArray）

#### SnowflakeIdWorker 雪花算法：
*功能*：
 - 产生一个不重复的id，整体上按照时间自增排序，
 - 整个分布式系统内不会产生ID碰撞(由数据中心ID和机器ID作区分)，并且效率较高，经测试，SnowFlake每秒能够产生26万ID左右
