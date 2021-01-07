package com.huaxin.webchat.webService;

import javax.jws.WebService;


@WebService(name = "LeakageService", // 暴露服务名称
        targetNamespace = "http://webService.webchat.huaxin.com"// 命名空间,一般是接口的包名倒序
)
public interface LeakageService {
    String doSave(String id,String receiveCode);
}
