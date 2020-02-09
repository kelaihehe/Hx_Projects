package com.scu.controller;

import com.iotplatform.utils.JsonUtil;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@RestController
public class TestController {

    private Logger logger = LoggerFactory.getLogger(getClass());
    private String posturl = "http://3e6b3310.ngrok.io/local/postLocalTest";
    private String geturl = "http://3e6b3310.ngrok.io/local/getLocalTest";

    @PostMapping("/postTestPost")
    public String postTest(@RequestBody String body) {
        logger.info("postTestPost");
        System.out.println(body);
//        Map<String,Object> device = new HashMap<String,Object>();
//        device.put("deviceManage",body);
//        RestTemplate restTemplate = new RestTemplate();
//        return restTemplate.postForObject(posturl, device, String.class);
        return "abc";
    }

    @GetMapping("/getTest")
    public String getTest() {
        logger.info("getTest");
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(geturl,String.class);
    }

    @GetMapping("/getTestPost")
    public String getTestPost() {
        logger.info("getTestPost");
        RestTemplate restTemplate = new RestTemplate();
        JSONObject postData = new JSONObject();
        postData.put("descp", "request for post");
        return restTemplate.postForObject(posturl, postData, String.class);
    }
}
