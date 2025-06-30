package com.springcrudgenerator;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;


import com.springcrudgenerator.configs.GeneratorConfig;
import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;

@Command(
    name = "crudgen",
    mixinStandardHelpOptions = true,
    version = "1.1",
    description = "Generates CRUD, facade and MapStruct layers"
)
public class CrudGenApp implements Runnable {

    @Option(names="--config", description="Path to config YAML", defaultValue=".crudgen.yml")
    private String configPath;

    private GeneratorConfig config;
    private TemplateLoader loader;

    @Command(
        name = "generate",
        description = "Create entity, CRUD classes and optional layers"
    )
    void generate(
        @Parameters(index="0", description="Entity name (Capitalized)") String entityName
    ) throws IOException {
        // 1. Load configuration and initialize components
        config = GeneratorConfig.load(configPath);
        loader = new TemplateLoader();

        // 2. Prepare the shared model for templates
        Path root = Paths.get("src/main/java", config.getBasePackage().split("\\."));
        Map<String, Object> model = new HashMap<>();
        model.put("config",      config);
        model.put("package",     config.getBasePackage());
        model.put("entityName",  entityName);
        model.put("entityVar",   Character.toLowerCase(entityName.charAt(0)) + entityName.substring(1));

        // 3. Invoke individual generator methods
        generateEntity(entityName, root, model);
        generateRepository(entityName, root, model);
        generateService(entityName, root, model);
        if (config.isWithConverter())   generateConverterAndDto(entityName, root, model);
        if (config.isWithFacade())      generateFacade(entityName, root, model);
        generateController(entityName, root, model);

        System.out.println("✅ Generation complete for entity " + entityName);
    }

    private void generateEntity(String name, Path root, Map<String,Object> model) throws IOException {
        loader.renderAndWrite(
            "entity.tpl",
            model,
            root.resolve("model"),
            name + ".java"
        );
    }

    private void generateRepository(String name, Path root, Map<String,Object> model) throws IOException {
        String tpl = config.isSpringData()
                     ? "repository-spring-data.tpl"
                     : "repository-hibernate.tpl";

        loader.renderAndWrite(
            tpl,
            model,
            root.resolve("repository"),
            name + "Repository.java"
        );
    }

    private void generateService(String name, Path root, Map<String,Object> model) throws IOException {
        loader.renderAndWrite(
            "service.tpl",
            model,
            root.resolve("service"),
            name + "Service.java"
        );
    }

    private void generateConverterAndDto(String name, Path root, Map<String,Object> model) throws IOException {
        // Generate DTO class
        loader.renderAndWrite(
            "dto.tpl",
            model,
            root.resolve("dto"),
            name + "Dto.java"
        );
        // Generate MapStruct mapper interface
        loader.renderAndWrite(
            "mapper.tpl",
            model,
            root.resolve("mapper"),
            name + "Mapper.java"
        );
    }

    private void generateFacade(String name, Path root, Map<String,Object> model) throws IOException {
        loader.renderAndWrite(
            "facade.tpl",
            model,
            root.resolve("facade"),
            name + "Facade.java"
        );
    }

    private void generateController(String name, Path root, Map<String,Object> model) throws IOException {
        loader.renderAndWrite(
            "controller.tpl",
            model,
            root.resolve("controller"),
            name + "Controller.java"
        );
    }

    @Override
    public void run() {
        System.out.println("Usage: crudgen generate <EntityName> [--config=path]");
    }

    public static void main(String[] args) {
        new CommandLine(new CrudGenApp()).execute(args);
    }
}
