package com.springcrudgenerator.configs;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;


import org.yaml.snakeyaml.Yaml;

public class GeneratorConfig {
    private String basePackage;
    private String dbProvider;
    private boolean withFacade;
    private boolean withConverter;

    public String getBasePackage()    { return basePackage; }
    public String getDbProvider()     { return dbProvider; }
    public boolean isWithFacade()     { return withFacade; }
    public boolean isWithConverter()  { return withConverter; }
    public boolean isSpringData()     { return "spring-data".equals(dbProvider); }

    @SuppressWarnings("unchecked")
    public static GeneratorConfig load(String path) {
        try (InputStream in = Files.newInputStream(Paths.get(path))) {
            Map<String,Object> m = new Yaml().load(in);
            GeneratorConfig cfg = new GeneratorConfig();
            cfg.basePackage   = (String) m.get("basePackage");
            cfg.dbProvider    = (String) m.getOrDefault("dbProvider", "spring-data");
            cfg.withFacade    = Boolean.TRUE.equals(m.get("withFacade"));
            cfg.withConverter = Boolean.TRUE.equals(m.get("withConverter"));
            return cfg;
        } catch (Exception e) {
            throw new RuntimeException("Cannot load config: " + path, e);
        }
    }
}
