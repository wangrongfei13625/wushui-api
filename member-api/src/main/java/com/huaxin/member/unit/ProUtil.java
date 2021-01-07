package com.huaxin.member.unit;

import java.io.InputStream;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;

public class ProUtil {
    private Properties props;

    public ProUtil(String propUri) {
        this.init(propUri);
    }

    public ProUtil() {
        this.init("/redis.properties");
    }

    private void init(String propUri) {
        InputStream is = this.getClass().getResourceAsStream(propUri);
        this.props = new Properties();

        try {
            this.props.load(is);
        } catch (Exception var4) {
            this.props.clear();
        }

    }

    public String getProperty(String key) {
        return this.props.getProperty(key, "");
    }

    public String getProperty(String key, String defaultValue) {
        return this.props.getProperty(key, defaultValue);
    }

    public void putProperty(String key, String value) {
        this.props.put(key, value);
    }

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
        Enumeration keys = this.props.propertyNames();
        ArrayList result = new ArrayList();

        while(keys.hasMoreElements()) {
            String key = keys.nextElement().toString();
            result.add(key);
        }

        return result;
    }

}
