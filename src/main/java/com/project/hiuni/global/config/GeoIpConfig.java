package com.project.hiuni.global.config;

import com.maxmind.geoip2.DatabaseReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

@Configuration
public class GeoIpConfig {

  @Value("${geo.db.path}")
  String geoDBPath;

  @Bean
  public DatabaseReader databaseReader(ResourceLoader resourceLoader) throws IOException {

    if(geoDBPath.startsWith("classpath")) {
      Resource resource = resourceLoader.getResource(geoDBPath);
      InputStream inputStream = resource.getInputStream();

      return new DatabaseReader.Builder(inputStream).build();
    }

      File geoDB = new File(geoDBPath);
      return new DatabaseReader.Builder(geoDB).build();

  }
}
