# 这是数据保存的案例
# 这是一个ruby脚本，因为hbase底层使用的ruby
# 命名空间namespace,是根据业务来创建命名空间的，不同的业务可以使用不同的命名空间来保存业务中的表
# HBase会有两个默认的命名空间1、default命名空间主要用于保存默认创建的表，也就是说创建时没有指定命名空间
#                         2、hbase命名空间主要用于保存系统表()
# 创建命名空间
create_namespace 'MOMO_CHAT'

# 查看命名空间
list_namespace

# 删除命名空间
drop_namespace 'MOMO_CHAT'

# 查看指定的命名空间
describe_namespace 'MOMO_CHAT'

# 同时创建命名空间和表以及列族
create 'MOMO_CHAT:MSG','C1'

# 同时创建命名空间和表以及列族并指定列簇的压缩算法
create 'MOMO_CHAT:MSG',{NAME=>'C1',COMPRESSION=>'GZ'}
# 命名空间和表以及列族已存在修改或者添加列簇的压缩算法
alter 'MOMO_CHAT:MSG',{NAME=>'C1',COMPRESSION=>'GZ'}


# 列簇设计
# 一般一个表只会设计一个列簇，因为一个列簇对应MemStore中，HBase中的数据首先保存到MemStore，当数据达到默认的128M就会将
# MemStore中的数据flush到HFile中，所以当如果有多个列簇，那么有可能就会出现多个flush操作，因为每个列簇对应的MemStore内存
# 有坑同时达到128M导致性能问题

# 数据压缩
# 1、GZIP 压缩效率最高
# 2、LZO
# 3、ZIPPY



# 避免热点数据
# 1、预分区：在创建表时指定region个数，让一个Table有多个Region,并且分布在不通的RegionServer中
#           如果只有一个region，那么Hbase会进行自动对table进行划分为多个Region，Region根据startKey和EndKey判断
#           数据是否保存在该region中
# 2、RowKey设计：不能实用递增ID，时间戳等有序的rowkey，因为这样违法将数据均衡的分布在个个Region中，
#               但是由于数据是分散从而会导致实用scan命令扫描数据有可能会扫描多个region导致性能问题，scan命令扫描数据是有序的

# Region的分区策略
# 1、指定startKey,Region,这里就会把startKey和EndKey分为0-10,10-20，20-30，30-40,40-n进行保存数据
create 'MOMO_CHAT:MSG','C1',SPLITS=>['10','20','30','40']
# 2、指定分区数量
create 'MOMO_CHAT:MSG','C1',{NUMREGIONS => 6,SPLITALGO=>'HexStringSplit'}

# 创建表，并指定分区
create 'MOMO_CHAT:MSG',{NAME=>'C1',COMPRESSION=>'GZ'},{NUMREGIONS => 6,SPLITALGO=>'HexStringSplit'}


