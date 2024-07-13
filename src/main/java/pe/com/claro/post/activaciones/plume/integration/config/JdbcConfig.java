package pe.com.claro.post.activaciones.plume.integration.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.lookup.JndiDataSourceLookup;

@Configuration
public class JdbcConfig {

  @Value("${db.sga.jndi}")
  private String sgaJndiName;

  @Value("${db.timeai.jndi}")
  private String timeaiJndiName;

  private final JndiDataSourceLookup lookup = new JndiDataSourceLookup();

  @Bean(name = "sgaJndiName", destroyMethod = "")
  DataSource dataSourceSga() {
    return lookup.getDataSource(sgaJndiName);
  }

  @Bean(name = "timeaiJndiName", destroyMethod = "")
  DataSource dataSourceTimeai() {
    return lookup.getDataSource(timeaiJndiName);
  }
}
