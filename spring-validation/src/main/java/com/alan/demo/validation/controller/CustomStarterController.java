package com.alan.demo.validation.controller;

import com.ctrip.framework.apollo.openapi.client.ApolloOpenApiClient;
import com.ctrip.framework.apollo.openapi.dto.OpenEnvClusterDTO;
import com.ctrip.framework.apollo.openapi.dto.OpenItemDTO;
import com.ctrip.framework.apollo.openapi.dto.OpenNamespaceDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import starter.service.MsgService;

import java.util.List;

/**
 * @author Alan Yin
 * @date 2021/1/18
 */
@RestController
@Slf4j
public class CustomStarterController {

    @Autowired
    private MsgService msgService;

    @RequestMapping("/sendMsg")
    public String sendMsg() {
        msgService.sendMsg("测试消息");
        return "";
    }

    private static final String APPID = "app_openapi";

    private static final String ENV = "DEV";

    private static final String CLUSTER = "default";

    @GetMapping("/t1")
    public String getApollo() {
        String portalUrl = "http://localhost:8070"; // portal url
        String token = "4164e3716f86ea951aec967d8768ed1245927f9e"; // 申请的token
        ApolloOpenApiClient client = ApolloOpenApiClient.newBuilder()
                .withPortalUrl(portalUrl)
                .withToken(token)
                .build();


        // 3.2.1 获取App的环境，集群信息
        List<OpenEnvClusterDTO> openEnvClusterDTOS = client.getEnvClusterInfo(APPID);
        log.info("openEnvClusterDTOS:{}", openEnvClusterDTOS);

        // 3.2.5 获取集群下所有Namespace信息接口
        OpenNamespaceDTO openNamespaceDTO1 = client.getNamespace(APPID, ENV, CLUSTER, "application");
        log.info("openNamespaceDTO1:{}", openNamespaceDTO1);

        // 3.2.7 创建Namespace
//        OpenAppNamespaceDTO namespaceDTO = new OpenAppNamespaceDTO();
//        namespaceDTO.setName("FX.public-0420-11");
//        namespaceDTO.setAppId(APPID);
//        namespaceDTO.setFormat("properties");
//        namespaceDTO.setPublic(false);
//        namespaceDTO.setComment("Namespace说明");
//        namespaceDTO.setDataChangeCreatedBy("yx");
//        OpenAppNamespaceDTO namespaceDTO2 = client.createAppNamespace(namespaceDTO);
//        log.info("namespaceDTO2:{}", namespaceDTO2);

        // 更新配置
        OpenItemDTO openItemDTO = new OpenItemDTO();
        openItemDTO.setKey("k1");
        openItemDTO.setValue("v111");
        openItemDTO.setComment("v1 comment");
        openItemDTO.setDataChangeLastModifiedBy("apollo");
        client.updateItem(APPID, ENV, CLUSTER, "application", openItemDTO);

        return "";
    }

}
