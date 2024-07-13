package pe.com.claro.post.activaciones.plume;

import java.io.File;
import java.util.Properties;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

import pe.com.claro.post.activaciones.plume.common.constants.Constantes;

public class ServletInitializer extends SpringBootServletInitializer {

  @Override
  protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
    return application.sources(WsSbClaroPostActivacionesPlumeApp.class).properties(getProperties());
  }

  static Properties getProperties() {
    Properties props = new Properties();

    String pathClaroProp = System.getProperty(Constantes.ENV_CLARO_PROPERTIES) + Constantes.SERVICE_NAME
      + File.separator + Constantes.PROPERTIES_NAME;

    props.put("spring.config.location", "file:" + pathClaroProp);
    return props;
  }

}
