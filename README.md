# 说明文档

---
### script文件夹
+ 存放了安装软件需要的curl脚本和获取拓扑信息的python脚本
+ get_delay.py ： python脚本，分别提供了获取用于选路的拓扑：for_path() 和用于前端呈现的拓扑 for_show() 详情见文件注释
+ onos-app 用于调用北向接口安装app

---
### show 
里面的效果呈现.txt文件保存了利用python脚本获取的两种拓扑信息

---
### onos_app文件夹

##### 1. choice-best-path
+ 提供北向接口graph,用于获取逻辑拓扑，即以端口为节点的基本的拓扑信息。

##### 2. device-and-host
+ 提供北向接口devicehost,用于获取物理拓扑，即交换机信息和与它相连的主机信息

##### 3. get-link-delay

+ 提供北向接口GetDelay，用于获取交换机与交换机之间链路的时延信息

---
### app安装教程
##### 提前安装
maven，onos，onos-app脚本

##### 安装步骤
1. 在app文件夹目录下执行 mvn clean install，已有target目录也可以不执行这一步 
2. build成功后在对应的target目录下执行<br>
 ``path/onos-app <ip> install! xxxx.oar``<br>
比如安装编译好的get-link-delay，在target目录下执行:<br>
``~(你的路径)/onos/script/onos-app 192.168.89.130 install! get-link-delay-1.0.oar``
3. 返回一个json格式的文本信息即为安装成功

---
### 注意事项

1. 为保证精确度，获取的时延信息单位为毫微秒（10^-9s）
2. 主机和交换机之间的链路和交换机不同端口之间的路径的时延在选路时不产生影响或不存在，所以这里全部默认为100000.
3. 因获取时延的原理所致，会在同一时间对一条链路的时延获取两次，两次可能会有不同，这里将两次的结果进行取平均值。
4. 在生成前端获取的拓扑时，两个交换机之间的link有两个（两个交换机分别作为源和目的地），但交换机和主机间只有一个。