package com.huaxin.webchat.webService.impl;

import com.huaxin.webchat.mapper.LeakageMapper;
import com.huaxin.webchat.model.LeakageMessage;
import com.huaxin.webchat.unit.EscapeUnescape;
import com.huaxin.webchat.unit.PropUtil;
import com.huaxin.webchat.webService.LeakageService;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import net.sf.json.JSONObject;

import javax.jws.WebService;
import java.text.SimpleDateFormat;
import java.util.*;

@WebService(
        targetNamespace = "http://webService.webchat.huaxin.com", //wsdl命名空间
        serviceName = "LeakageService",                 //portType名称 客户端生成代码时 为接口名称
        endpointInterface = "com.huaxin.webchat.webService.LeakageService")//指定发布
public class LeakageServiceImpl implements LeakageService {

    private Logger logger = LogManager.getLogger(this.getClass());

    private String coding;
    private int saveNumber = 0;

    private static DefaultHttpClient httpClient = new DefaultHttpClient();
    @Autowired
    private LeakageMapper leakageMapper;


    public void init()
    {
        httpClient = new DefaultHttpClient();

        PropUtil propUtil = new PropUtil("/test.properties");
        setCoding(propUtil.getProperty("coding"));

        String LeakageLoginUrl = propUtil.getProperty("LeakageLoginUrl");
        String LeakageLoginParams = propUtil.getProperty("LeakageLoginParams");

        HttpPost httppost = new HttpPost(LeakageLoginUrl);
        List params = new ArrayList();
        String[] loginParamsArray = LeakageLoginParams.split(",");
        for (String param : loginParamsArray)
            params.add(new BasicNameValuePair(param.split("\\|")[0], param.split("\\|")[1]));
        try
        {
            httppost.setHeader("referer", "http://10.10.10.134:9782/ewidewx/loginController.do?login");

            httppost.setEntity(new UrlEncodedFormEntity(params));

            HttpResponse response = httpClient.execute(httppost);

            if (response.getStatusLine().getStatusCode() == 200) {
                HttpEntity httpEntity = response.getEntity();
                String html = EntityUtils.toString(httpEntity, "utf-8");
                System.out.println(html);
            }

            httppost.releaseConnection();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            logger.error("init:" + e);
        }
    }

    public String doSave(String id, String receiveCode)
    {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        HttpPost httppost = new HttpPost("http://10.10.10.134:9782/ewidewx/wxQielouController.do?doUpdate");

        String flag = "0";
        try {
           Map<String,Object> map = new HashMap<>();
           map.put("id",id);
           List<Map<String,Object>> list = leakageMapper.findById(map);

            if ((list == null) || (list.size() == 0)) {
                LeakageMessage leakageMessage = (LeakageMessage)list.get(0);
                List params = new ArrayList();
                params.add(new BasicNameValuePair("id", id));
                params.add(new BasicNameValuePair("createName", null));
                params.add(new BasicNameValuePair("createDate", format.format(leakageMessage.getCreateTime())));
                params.add(new BasicNameValuePair("updateName", "rx@ewidewater"));
                params.add(new BasicNameValuePair("updateDate", format.format(new Date())));
                params.add(new BasicNameValuePair("openid", leakageMessage.getOpenid()));
                params.add(new BasicNameValuePair("type", leakageMessage.getApplyType()));
                params.add(new BasicNameValuePair("bz", leakageMessage.getReportMemo()));
                params.add(new BasicNameValuePair("status", "01"));
                params.add(new BasicNameValuePair("reason", receiveCode));
                httppost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));

                HttpResponse httpResponse = httpClient.execute(httppost);
                if (httpResponse.getStatusLine().getStatusCode() == 200) {
                    HttpEntity httpEntity = httpResponse.getEntity();
                    String html = EntityUtils.toString(httpEntity, this.coding);
                    String result = EscapeUnescape.unescape(html);

                    JSONObject object = JSONObject.fromObject(result);
                    if ("true".equals(object.get("success"))) {
                        flag = "1";
                        map.put("receiveCode",receiveCode);
                        leakageMapper.updateById(map);
                        //String sql = "update LeakageMessage set receiveCode='" + receiveCode + "',status='01' where id in ('" + id + "')";
                        //getJdbcTemplate().update(sql);
                    }
                } else {
                    flag = "0";
                }
            }
        } catch (Exception e) {
            flag = "2";
            logger.error(e);
            e.printStackTrace();
        } catch (Throwable t) {
            flag = "2";
            logger.error(t);
            t.printStackTrace();
        }
        finally {
            httppost.releaseConnection();
        }
        return flag;
    }

    public String getCoding() {
        return coding;
    }

    public void setCoding(String coding) {
        this.coding = coding;
    }

    public int getSaveNumber() {
        return saveNumber;
    }

    public void setSaveNumber(int saveNumber) {
        this.saveNumber = saveNumber;
    }
}
