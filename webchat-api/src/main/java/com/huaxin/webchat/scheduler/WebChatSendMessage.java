package com.huaxin.webchat.scheduler;

import com.github.pagehelper.util.StringUtil;
import com.huaxin.webchat.mapper.EtlWarnHistoryMapper;
import com.huaxin.webchat.mapper.HxConfigMapper;
import com.huaxin.webchat.mapper.RealDataDoubleValueMapper;
import com.huaxin.webchat.mapper.WarnConfigMapper;
import com.huaxin.webchat.unit.ConnectDB;
import com.huaxin.webchat.unit.HttpUtils;
import com.huaxin.webchat.unit.PropUtil;
import net.sf.json.JSONObject;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;

@Configuration
@EnableScheduling
public class WebChatSendMessage {

    private static final Log log = LogFactory.getLog(ExceedStandardFilter.class);

    private static PropUtil propUtil = new PropUtil("/DataProject.properties");

    @Autowired
    private HxConfigMapper hxConfigMapper;

    @Autowired
    private RealDataDoubleValueMapper realDataDoubleValueMapper;

    @Autowired
    private WarnConfigMapper warnConfigMapper;

    @Autowired
    private EtlWarnHistoryMapper etlWarnHistroyMapper;

    private static Map<String, String> BlueB = new HashMap<String, String>();  //保存蓝鸟库上次的超标过滤

    //@Scheduled(cron="0/10 * *  * * ?")
    public void sendMessage(){
        //遍历出RT_realDataDoubleValue，得到系统最近时间段数据， 判断是否为异常信息
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date collDateTime = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(collDateTime);
        calendar.set(Calendar.SECOND,0);
        collDateTime = calendar.getTime();

        /*boolean flag = (collDateTime.getMinutes() * 60 + collDateTime.getSeconds()) % 300 ==0;
        String time = "";
        if(flag){

        }else{
            calendar.set(Calendar.SECOND,-((collDateTime.getMinutes() * 60 + collDateTime.getSeconds())% 300));
            collDateTime = calendar.getTime();
        }*/
        Date endDateTime = new Date();
        Calendar calendars = Calendar.getInstance();
        calendars.setTime(collDateTime);
        calendars.set(Calendar.SECOND,59);
        endDateTime = calendars.getTime();

        ExceedStandardFilter exceedStandardFilter = new ExceedStandardFilter();

        String startTime = sdf.format(collDateTime);
        String endTime = sdf.format(endDateTime);

        //List<Map<String,Object>> list = realDataDoubleValueMapper.findValueByTime("2020-12-10 10:15:00");
        //获取蓝鸟，三高数据，来进行处理 判断 数据是否超标


        List<Map<String,Object>> list = blueBirdieETL( startTime, endTime);
        for(Map<String,Object> map:list){

            String orgId = map.get("ORGANIZATIONID").toString();
            String dataId = map.get("dataId").toString();
            String dataValue = map.get("dataValue").toString();

            String result = exceedStandardFilter.isExceedOrFilter(orgId, dataId, dataValue, sdf.format(collDateTime));  //数据超标过滤判断
            String dataStateId = result.split("@")[0];
            String exceed = result.split("@")[1];
            dataValue = result.split("@")[2];
            String extendExpression = result.split("@")[3];
            //判断时候异常，异常则发微信消息
            try {
                String mapKey = orgId + "@" + dataId;
                String dataStateMap = BlueB.get(mapKey);

                String countKey = orgId + "@" + dataId + "@count"; //统计次数
                String countValue = BlueB.get(countKey);
                int count = 0;
                if (!StringUtil.isEmpty(countValue)) {
                    count = Integer.valueOf(countValue);
                }
                if (dataStateMap != null) {
                    if (!"0".equals(exceed) && isMsgExceed(dataStateId, extendExpression, dataValue)) {//超标状态并且超标 加一
                        count++;
                    } else { //一旦不超标则计数恢复0
                        count = 0;
                    }
                    if (count >= 3) {

                        weChart(orgId, dataId, dataStateId, exceed, sdf.format(collDateTime), dataValue, extendExpression);
                    }
                }
                BlueB.put(mapKey, dataStateId);
                BlueB.put(countKey, count + "");
            } catch (Exception e) {
                log.error("数据异常警告，发送微信消息产生失败，" + e.getMessage());
            }
        }


    }





    //蓝鸟数据采集
    public List<Map<String,Object>> blueBirdieETL(String startTime,String endTime) {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        try {
            SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Connection conn = null;
            try {

                //连接数据库
                ConnectDB db = new ConnectDB("0", "runtime", "10.10.30.246", "1433", "sa", "eddc6aecd52b1613e1d05ce39050585e");
                conn = db.getConnection();
                if (conn == null) {
                    //数据库连接失败
                    return null;
                }

                log.error("开始时间:" + startTime + "----结束时间:" + endTime);
                //获取查询的tagName 需要采集数据的点位
                String tagNameAll = propUtil.getProperty("Heda_Project");

                Map<String, Object> tagMap = new HashMap<String, Object>(); //所有要查询的数据
                for (String tag : tagNameAll.split(",")) {
                    tagMap.put(tag.replace("'", "").toUpperCase(), "");
                }
                //List<String> tagList = new ArrayList<String>(); //本次查到数据的
                //region 抽取程序
                PreparedStatement preparedStatement = null;
                ResultSet resultSet = null;
                try {
                    String sql = "SELECT  * FROM (\n" +
                            "SELECT History.TagName, DateTime = convert(nvarchar, DateTime, 20), Value,  StartDateTime\n" +
                            " FROM History\n" +
                            " WHERE History.TagName IN (" + tagNameAll + ")\n" +
                            " AND wwRetrievalMode = 'Cyclic'\n" +
                            " AND wwResolution = 300000\n" +
                            " AND wwQualityRule = 'Optimistic'\n" +
                            " AND wwVersion = 'Latest'\n" +
                            " AND DateTime >= convert(dateTime, '#startTime')\n" +
                            " AND DateTime <= convert(dateTime, '#endTime')) temp WHERE temp.StartDateTime >=  convert(dateTime, '#startTime')";
                    sql = sql.replace("#startTime", startTime).replace("#endTime", endTime);
                    preparedStatement = conn.prepareStatement(sql);
                    resultSet = preparedStatement.executeQuery();
                    while (resultSet.next()) {
                        String dataDate = resultSet.getString("DateTime");
                        String dataValue = resultSet.getString("Value");
                        String dataId = resultSet.getString("TagName").toUpperCase();

                        //dataDate = sf.format(dataDate);  //数据时间

                        Map<String, Object> map = new HashMap<String, Object>();
                        map.put("dataId", dataId);
                        map.put("dataDate", dataDate);
                        map.put("dataValue", dataValue);
                        list.add(map);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    log.error("蓝鸟抽取出错:", e);
                } catch (Throwable t) {
                    t.printStackTrace();
                    log.error("蓝鸟抽取出错:", t);
                } finally {
                    if (resultSet != null) {
                        resultSet.close();
                    }
                    if (preparedStatement != null) {
                        preparedStatement.close();
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
                log.error("蓝鸟抽取出错", e);
            }
        }catch (Exception e) {
            e.printStackTrace();
            log.error("蓝鸟抽取出错", e);
        }
        return list;
    }





    public boolean isMsgExceed(String dataStateId, String extendExpression, String dataValue) {
        double warnValue = 0.0;
        boolean warnFlag = false;
        String limitValue = "";
        if ("2".equals(dataStateId) || "4".equals(dataStateId)) {//超最大值
            String leftValue = extendExpression.split("\\,")[0];
            limitValue = leftValue.substring(1);
            warnValue = Double.valueOf(limitValue) * 1;//报警时上限
            if (warnValue < Double.valueOf(dataValue)) {
                warnFlag = true;
            }
        } else if ("6".equals(dataStateId) || "8".equals(dataStateId)) {//超最小值
            String rightValue = extendExpression.split("\\,")[1];
            limitValue = rightValue.substring(0, rightValue.length() - 1);
            warnValue = Double.valueOf(limitValue) * 1;//报警时下限
            if (warnValue > Double.valueOf(dataValue)) {
                warnFlag = true;
            }
        }
        return warnFlag;//如果在正负30%以内则报警
    }

    //微信推送
    public void weChart(String organizationId, String dataId, String dataStateId, String exceed, String dataDate, String dataValue, String extendExpression) {
        try {
            log.error("---------微信推送开始---------");
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            List<Map<String,Object>> configList = hxConfigMapper.findByConfigId("577");

            String configValue = configList.get(0).get("CONFIGVALUE").toString();

            String limitValue;

            if ("1".equals(configValue)) {
                Map<String,Object> params = new HashMap<>();
                params.put("organizationId",organizationId);
                params.put("dataId",dataId);

                // 查询需要推送的人员
                List<Map<String, Object>> warnConfigList = warnConfigMapper.findByOrgIdAndDataId(params);

                limitValue = "";
                if (("2".equals(dataStateId)) || ("4".equals(dataStateId))) {
                    String leftValue = extendExpression.split("\\,")[0];
                    limitValue = leftValue.substring(1);
                } else if (("6".equals(dataStateId)) || ("8".equals(dataStateId))) {
                    String rightValue = extendExpression.split("\\,")[1];
                    limitValue = rightValue.substring(0, rightValue.length() - 1);
                }

                /*if (!"".equals(limitValue)) {
                    limitValue = limitValue + dataUnit;
                }*/
                Date now = new Date();
                boolean isOverdue = false;
                if (warnConfigList.size() > 0) {
                    if (!StringUtil.isEmpty(dataDate)) {
                        String format = "yyyy-MM-dd HH:mm";
                        if (dataDate.length() > 16) {
                            format = "yyyy-MM-dd HH:mm:ss";
                        }
                        Date tmp = new SimpleDateFormat(format).parse(dataDate);
                        if (now.getTime() - tmp.getTime() > 3600000L) {
                            return;
                        }
                    }
                    for (Map<String, Object> map : warnConfigList) {
                        Integer configId = Integer.parseInt(map.get("config_id").toString());
                        String orgId = map.get("ORGANIZATION_ID") == null ? "" : map.get("ORGANIZATION_ID").toString();
                        String dataIds = map.get("DATA_ID") == null ? "" : map.get("DATA_ID").toString();
                        String warnType = map.get("WARN_TYPE") == null ? "" : map.get("WARN_TYPE").toString();
                        String objectId = map.get("OBJECT_ID") == null ? "" : map.get("OBJECT_ID").toString();
                        String msgContent = map.get("MSG_CONTENT") == null ? "" : map.get("MSG_CONTENT").toString();
                        String organizationName = map.get("ORGANIZATION_NAME") == null ? "" : map.get("ORGANIZATION_NAME").toString();
                        String dataName = map.get("DATA_NAME") == null ? "" : map.get("DATA_NAME").toString();
                        params.put("dataStateId",dataStateId);
                        List<Map<String,Object>> realList = warnConfigMapper.findRealDataColorStateName(params);
                        String dataStateName = realList.get(0).get("DATASTATENAME").toString();
                        /*dataValue = dataValue + dataUnit;*/

                        dataStateName = dataStateName + limitValue;
                        String message = msgContent.replace("organizationName", organizationName).replace("dataName", dataName).replace("dataValue", dataValue).replace("dataTime", dataDate).replace("dataStateName", dataStateName);

                        String[] msgTypes = warnType.split(",");
                        String ids = "";
                        String response;
                        for (String msgType : msgTypes) {
                            String[] objectIds = objectId.split(";");
                            for (int i = 0; i < objectIds.length; i++) {
                                if (objectIds[i].contains("u")) {
                                    ids = ids + objectIds[i].substring(3, objectIds[i].length() - 1);
                                }
                            }

                            String[] id = ids.split(",");
                            //Set userNameSet = new HashSet();
                            //Set webChatSet = new HashSet();
                            String webChatNum ="";
                            // 根据用户ID 查询 得到用户名 微信名
                            for (int i = 0; i < id.length; i++) {

                                List<Map<String,Object>> usersList = warnConfigMapper.findWebChatOfUserId(id[i]);
                                /*String userName = usersList.get(0).get("user_name").toString();
                                userNameSet.add(userName);*/

                                String webChat = usersList.get(0).get("webchat_num").toString();
                                webChatNum +=webChat +"|";

                            }

                            webChatNum = webChatNum.substring(0,webChatNum.length()-1);


                            Map<String,Object> weChatMap = new HashMap<>();
                            weChatMap.put("orgId",orgId);
                            weChatMap.put("dataId",dataIds);
                            weChatMap.put("dataStateId",dataStateId);
                            weChatMap.put("exceed",exceed);
                            weChatMap.put("dataDate",sdf.parse(dataDate));
                            weChatMap.put("dataValue",dataValue);
                            weChatMap.put("extendExpression",extendExpression);
                            weChatMap.put("configId",configId);
                            etlWarnHistroyMapper.saveWeChart(weChatMap);


                            //微信推送
                            String corpId = propUtil.getProperty("corpId");
                            String corpSecret = propUtil.getProperty("corpSecret");
                            String AgentId = propUtil.getProperty("AgentId");
                            String access_token =getAccessToken(corpId,corpSecret);

                            JSONObject jsonObject = new JSONObject();
                            jsonObject.put("touser", webChatNum);//需要推送的用户 UserID1|UserID2|UserID3
                            jsonObject.put("agentid", AgentId);
                            JSONObject mytext = new JSONObject();
                            mytext.put("content",message);//推送消息内容
                            jsonObject.put("msgtype", "text");
                            jsonObject.put("text",mytext);

                            try {
                                 response= HttpUtils.httpPostMethod("https://qyapi.weixin.qq.com/cgi-bin/message/send?access_token=" + access_token, params);
                                log.error("微信推送http："+response);
                                /*com.alibaba.fastjson.JSONObject obj = JSON.parseObject( response);
                                String errmsg = obj.getString("errmsg");
                                 //获取返回code==200访问成功 ==500失败
                                 if(errmsg.equals("ok")){
                                    //获取返回页面数据
                                    HttpEntity httpEntity = response.getEntity();
                                    String html = EntityUtils.toString(httpEntity, "utf-8");
                                    log.error("微信推送返回："+html);
                                }
*/
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }
            log.error("sendMsg2");
        } catch (Exception e) {
            log.error("",e);
        }catch (Throwable t){
            log.error("",t);
        }
    }



    public void weChartOfOld(String organizationId, String dataId, String dataStateId, String exceed, String dataDate, String dataValue, String extendExpression) {
        /*try {
            log.error("sendMsg1");
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            List<Map<String,Object>> configList = hxConfigMapper.findByConfigId("577");

            String configValue = configList.get(0).get("CONFIGVALUE").toString();
            String urlParam;
            String limitValue;
            String dataName;
            String dataUnit;
            if ("1".equals(configValue)) {
                Map<String,Object> params = new HashMap<>();
                params.put("organizationId",organizationId);
                params.put("dataId",dataId);

                List<Map<String, Object>> warnConfigList = warnConfigMapper.findByOrgIdAndDataId(params);
                List list = warnConfigMapper.findPointInfo(params);
                Integer nodeId = 0;
                Integer _orgId = 0;
                if (list.size() > 0) {
                    nodeId = Integer.valueOf(String.valueOf(((Map) list.get(0)).get("mapInfoid")));
                    _orgId = Integer.valueOf(String.valueOf(((Map) list.get(0)).get("organizationid")));
                }

                urlParam = "orgid=" + _orgId + "&dataid=" + dataId + "&nodeid=" + nodeId + "&organizationid=" + organizationId + "&datatype=7";
                log.error(urlParam);

                List<Map<String,Object>> dataNameList = warnConfigMapper.findDataName(params);

                String dataNameAndUnit="";
                if(dataNameList.size()>0){
                    dataNameAndUnit= dataNameList.get(0).get("dataName").toString()+","+dataNameList.get(0).get("dataUnit").toString();
                }else{
                    dataNameAndUnit=" , ";
                }
                limitValue = "";
                if (("2".equals(dataStateId)) || ("4".equals(dataStateId))) {
                    String leftValue = extendExpression.split("\\,")[0];
                    limitValue = leftValue.substring(1);
                } else if (("6".equals(dataStateId)) || ("8".equals(dataStateId))) {
                    String rightValue = extendExpression.split("\\,")[1];
                    limitValue = rightValue.substring(0, rightValue.length() - 1);
                }
                dataName = dataNameAndUnit.split("\\,")[0];
                dataUnit = dataNameAndUnit.split("\\,")[1];
                if (!"".equals(limitValue)) {
                    limitValue = limitValue + dataUnit;
                }

                Date now = new Date();
                boolean isOverdue = false;
                if (warnConfigList.size() > 0) {
                    if (!StringUtil.isEmpty(dataDate)) {
                        String format = "yyyy-MM-dd HH:mm";
                        if (dataDate.length() > 16) {
                            format = "yyyy-MM-dd HH:mm:ss";
                        }
                        Date tmp = new SimpleDateFormat(format).parse(dataDate);
                        if (now.getTime() - tmp.getTime() > 3600000L) {
                            return;
                        }
                    }
                    for (Map<String, Object> map : warnConfigList) {
                        Integer configId = Integer.parseInt(map.get("config_id").toString());
                        String orgId = map.get("organization_id") == null ? "" : map.get("organization_id").toString();
                        String dataIds = map.get("data_Id") == null ? "" : map.get("data_Id").toString();
                        String warnType = map.get("warn_type") == null ? "" : map.get("warn_type").toString();
                        String objectId = map.get("object_id") == null ? "" : map.get("object_id").toString();
                        String msgContent = map.get("msg_content") == null ? "" : map.get("msg_content").toString();

                        List<Map<String,Object>> orgList = warnConfigMapper.findOrganizationName(params);

                        String organizationName = orgList.get(0).get("ORGANIZATIONNAME").toString();

                        params.put("dataStateId",dataStateId);
                        List<Map<String,Object>> realList = warnConfigMapper.findRealDataColorStateName(params);
                        String dataStateName = realList.get(0).get("DATASTATENAME").toString();
                        dataValue = dataValue + dataUnit;

                        dataStateName = dataStateName + limitValue;
                        String message = msgContent.replace("organizationName", organizationName).replace("dataName", dataName).replace("dataValue", dataValue).replace("dataTime", dataDate).replace("dataStateName", dataStateName);

                        String[] msgTypes = warnType.split(",");
                        String ids = "";
                        HttpResponse response;
                        for (String msgType : msgTypes) {
                            String[] objectIds = objectId.split(";");
                            for (int i = 0; i < objectIds.length; i++) {
                                if (objectIds[i].contains("u")) {
                                    ids = ids + objectIds[i].substring(3, objectIds[i].length() - 1);
                                } else if (objectIds[i].contains("o")) {
                                    String orgIds = objectIds[i].substring(3, objectIds[i].length() - 1);
                                    List<Integer> userIdList = warnConfigMapper.findUserIdByOrgId(orgIds);
                                    for (Integer userId : userIdList) {
                                        ids = ids + userId + ",";
                                    }
                                    ids = ids.substring(0, ids.length() - 1);
                                } else if (objectIds[i].contains("r")) {
                                    String[] roles = objectIds[i].substring(3, objectIds[i].length() - 1).split(",");
                                    for (String role : roles) {

                                        List<Map<String,Object>> userList = warnConfigMapper.findUsersByRoleId(Integer.parseInt(role));
                                        for(Map<String,Object> userMap:userList){
                                            ids = ids + userMap.get("USERID").toString() + ",";
                                        }
                                    }
                                    ids = ids.substring(0, ids.length() - 1);
                                }
                            }

                            String[] id = ids.split(",");
                            Set userNameSet = new HashSet();
                            for (int i = 0; i < id.length; i++) {

                                List<Map<String,Object>> usersList = warnConfigMapper.findUserId(id[i]);
                                String userName = usersList.get(0).get("LOGINNAME").toString();
                                userNameSet.add(userName);
                            }
                            String Token = "CD32EB042E118EC1B122870C1F9F953C";

                            String Title = organizationName;
                            String Keyword1 = dataName;
                            String Keyword2 = dataDate;
                            String Keyword3 = message;
                            String Remark = "";

                            Map<String,Object> weChatMap = new HashMap<>();
                            weChatMap.put("orgId",orgId);
                            weChatMap.put("dataId",dataId);
                            weChatMap.put("dataStateId",dataStateId);
                            weChatMap.put("exceed",exceed);
                            weChatMap.put("dataDate",sdf.parse(dataDate));
                            weChatMap.put("dataValue",dataValue);
                            weChatMap.put("extendExpression",extendExpression);
                            weChatMap.put("configId",configId);
                            warnConfigMapper.saveWeChart(weChatMap);
                            //warnConfigBiz.saveWeChart(orgId,dataId,dataStateId,exceed,dataDate,dataValue,extendExpression,configId);
                            log.error("保存告警信息!!");
                            String url = "https://115.29.174.41/duliang/wechat/push";
                            DefaultHttpClient client = new DefaultHttpClient();
                            enableSSL(client);
                            String jr = generateJsonRequest(userNameSet, Token, Title, Keyword1, Keyword2, Keyword3, Remark, urlParam);
                            StringEntity entity = new StringEntity(jr, "UTF-8");
                            HttpPost httPost = new HttpPost(url);
                            httPost.setEntity(entity);
                            response = client.execute(httPost);
                            log.error("微信推送http："+response.getStatusLine().getStatusCode());
                            //获取返回code==200访问成功 ==500失败

                            if(response.getStatusLine().getStatusCode()==200){
                                //获取返回页面数据
                                HttpEntity httpEntity = response.getEntity();
                                String html = EntityUtils.toString(httpEntity, "utf-8");
                                log.error("微信推送返回："+html);
                            }
                        }
                    }
                }
            }
            log.error("sendMsg2");
        } catch (Exception e) {
            log.error("",e);
        }catch (Throwable t){
            log.error("",t);
        }*/
    }


    /*public static String generateJsonRequest(Set<String> userNameSet, String Token, String Title, String Keyword1, String Keyword2, String Keyword3, String Remark, String url) {
        JSONStringer js = new JSONStringer();
        try {
            js.object();
            js.key("Token").value(Token);
            js.key("Title").value(Title);
            js.key("Keyword1").value(Keyword1);
            js.key("Keyword2").value(Keyword2);
            js.key("Keyword3").value(Keyword3);
            js.key("Remark").value(Remark);
            js.key("Url").value(url);

            JSONArray ct = new JSONArray();
            for (String UserName : userNameSet) {
                ct.add(UserName);
            }
            js.key("To").value(ct);
            js.endObject();
            log.error(ct);
        } catch (Exception e) {
        }
        return js.toString();
    }


    private static void enableSSL(DefaultHttpClient httpclient) {
        try {
            SSLContext sslcontext = SSLContext.getInstance("TLS");
            sslcontext.init(null, new TrustManager[]{truseAllManager}, null);
            SSLSocketFactory sf = new SSLSocketFactory(sslcontext);
            sf.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
            Scheme https = new Scheme("https", sf, 443);
            httpclient.getConnectionManager().getSchemeRegistry().register(https);
        } catch (Exception e) {
        }
    }

    private static TrustManager truseAllManager = new X509TrustManager() {
        @Override
        public void checkClientTrusted(X509Certificate[] arg0, String arg1)
                throws CertificateException {
        }

        @Override
        public void checkServerTrusted(X509Certificate[] arg0, String arg1)
                throws CertificateException {
        }

        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return null;
        }
    };*/


    public static void main(String[] args) {

    }


    private  String getAccessToken(String qywxId, String secret2) {
        String url="https://qyapi.weixin.qq.com/cgi-bin/gettoken?corpid="+qywxId+"&corpsecret="+secret2+"";
        String token=null;
        try {
            token = HttpUtils.httpGetMethod(url);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return token;
    }





}
