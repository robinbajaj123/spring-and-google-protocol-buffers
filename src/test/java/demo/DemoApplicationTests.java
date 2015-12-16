package demo;

import java.util.Arrays;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.protobuf.ProtobufHttpMessageConverter;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class DemoApplicationTests {

    @Configuration
    public static class RestClientConfiguration {

        @Bean
        RestTemplate restTemplate(ProtobufHttpMessageConverter hmc) {
            return new RestTemplate(Arrays.asList(hmc));
        }

        @Bean
        ProtobufHttpMessageConverter protobufHttpMessageConverter() {
            return new ProtobufHttpMessageConverter();
        }
    }

    @Autowired
    private RestTemplate restTemplate;

    @Test
    public void contextLoaded() {
    	
        ResponseEntity<CustomerProtos.Customer> customer = restTemplate.getForEntity(
                "http://localhost:7001/demo-0.0.1-SNAPSHOT/ws/customers/2", CustomerProtos.Customer.class);

        System.out.println("customer retrieved: " + customer.toString());

    }

}
