package com.huaxin.webchat.webService.webServiceConfig;


import com.huaxin.webchat.webService.LeakageService;
import com.huaxin.webchat.webService.impl.LeakageServiceImpl;
import org.apache.cxf.Bus;
import org.apache.cxf.bus.spring.SpringBus;

import org.apache.cxf.jaxws.EndpointImpl;
import org.apache.cxf.transport.servlet.CXFServlet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.xml.ws.Endpoint;

@Configuration
public class CxfConfig {

    @Bean
    public ServletRegistrationBean dispatcherServlet() {
        return new ServletRegistrationBean(new CXFServlet(), "/services/*");
    }

    @Bean(name = Bus.DEFAULT_BUS_ID)
    public SpringBus springBus() {
        return new SpringBus();
    }

    @Bean
    public LeakageService leakageService() {
        return new LeakageServiceImpl();
    }

    @Bean
    public Endpoint endpoint() {
        EndpointImpl endpoint = new EndpointImpl(springBus(), leakageService());
        endpoint.publish("/LeakageService");
        return endpoint;

    }
}