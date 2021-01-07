package com.huaxin.webchat.unit;

import java.io.InputStream;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;

/**
 * @author sunny
 */
public class PropUtil {

    private Properties props;

    /**
     * @param propUri 资源文件路径
     */
    public PropUtil(String propUri) {
        super();
        init(propUri);
    }

//    public PropUtil() {
//        init(WebKey.SYSTEM_CONFIG_PATH);
//    }

    private void init(String propUri) {
        InputStream is = getClass().getResourceAsStream(propUri);
        props = new Properties();
        try {
            props.load(is);
        } catch (Exception e) {
            props.clear();
        }
    }

    /**
     * @param key 资源名称
     * @return 资源内容
     */
    public String getProperty(String key) {
        return props.getProperty(key, "");
    }

    /**
     * @param key          资源名称
     * @param defaultValue 如果找不到该键匹配的资源，则返回该值
     * @return 资源内容
     */
    public String getProperty(String key, String defaultValue) {
        return props.getProperty(key, defaultValue);
    }

    public void putProperty(String key, String value) {
        props.put(key, value);
    }

    /**
     * @param key  资源名称
     * @param pops 资源参数
     * @return 资源内容
     */
    public String getProperty(String key, String[] pops) {
        String value = this.getProperty(key, "null");
        if (!value.equals("null")) {
            value = MessageFormat.format(value, pops);
            return value;
        } else {
            return "null";
        }
    }

    public Properties getProperties() {
        return this.props;
    }

    public List<String> getKeys() {
        Enumeration keys = props.propertyNames();
        ArrayList result = new ArrayList();
        for (; keys.hasMoreElements();) {
            String key = keys.nextElement().toString();
            result.add(key);
        }
        return result;
    }
}
