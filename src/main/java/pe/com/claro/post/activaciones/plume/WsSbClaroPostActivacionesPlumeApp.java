package pe.com.claro.post.activaciones.plume;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan({"pe.com.claro.post.activaciones.plume"})
public class WsSbClaroPostActivacionesPlumeApp {

  public static void main(String[] args) {
    SpringApplication.run(WsSbClaroPostActivacionesPlumeApp.class, args);
  }

}
