    版本:V22.04.20_01_released
    后端运行环境
        MySQL 5.7
        java 1.8

   1.新建数据库  
   2.执行初始化sql--sparkera_nft.sql      
   3.修改src\main\resources下application.yml    
      spring.profiles.active指定要运行的配置文件    
   4.修改配置文件    
    1.修改数据库链接，账号密码    
    2.修改bsc.node.url 节点url    
    3.  修改contract地址:    
    去掉0x 防止自动转义 fireworkcard_address卡片合约地址，NFTtransfer_address 交易合约地址    
    fireworkcard_address: 8b1222d248c598074544c0e4507ae3d1d788008e    
    NFTtransfer_address: 18A014B0A7f63C962030Dd4f3397Ac74c7c111Ab    

5.修改数据库配置表 ts_sys_config    
    1.现有数据有描述，根据描述修改相应参数即可

        二期
        1.打包最新代码，执行theSecondPhase.sql即可


