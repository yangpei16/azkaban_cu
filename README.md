# Azkaban CU



## V 2020.3.26



- 本版本基于官方Azkaban 3.81.0版本小修改

  原版本在开启**execute-as-user**后，所有用户都可以通过代理用户（**user.to.proxy**）使用任意用户的权限

  增加了**executor.proxy.enable**参数设置，用于开启/关闭代理用户（**user.to.proxy**）功能

  增加了**proxy_users**库表，用于管理某个用户可以代理哪些用户去执行作业

- 在executor-server的bin下增加了active-exe.sh脚本，方便激活exec节点服务

- 修改了azkaban-common/src/main/java/azkaban/jobExecutor/ProcessJob.java

- 新增了azkaban-common/src/main/java/azkaban/jobExecutor/JobExtend.java