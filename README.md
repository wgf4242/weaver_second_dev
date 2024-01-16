Weaver Test

File - Project Structure - Sources , Add Content Root, 添加此目录即可

# Documentation

- [Api](https://e-cloudstore.com/ec/api/applist/#/)
- [后端API发布规范](https://e-cloudstore.com/e9/secondcommon_kfgf.html?type=4)
- [后端开发指南](https://e-cloudstore.com/e9/file/E9BackendDdevelopmentGuide.pdf)
- [流程API接口说明与示例/流程列表](https://e-cloudstore.com/doc.html?appId=031d370a0dfa4dcfb57228110bea3f41)

# 目录结构

- com.engine目录是核心业务逻辑类所在目录，不允许直接暴露对外服务接口
- com.api 对外服务接口，对外暴露（专门提供API服务的目录）。通过extends（继承）的方式暴露RESTful服务接口。

# Tools

## Soap UI Tools 查看 Webservice

WebService地址 /services
