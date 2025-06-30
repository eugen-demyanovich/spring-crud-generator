package com.springcrudgenerator;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;


import freemarker.cache.ClassTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;

public class TemplateLoader {
    private final Configuration cfg;

    public TemplateLoader() {
        cfg = new Configuration(Configuration.VERSION_2_3_31);
        cfg.setTemplateLoader(
            new ClassTemplateLoader(
                Thread.currentThread().getContextClassLoader(),
                "/templates"
            )
        );
        cfg.setDefaultEncoding("UTF-8");
        cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
    }

    // в TemplateLoader.java
    public void renderAndWrite(String tplName,
                               Map<String,Object> model,
                               Path outputDir,
                               String outputFile) throws IOException {
        try (Writer out = new StringWriter()) {
            Template tpl = cfg.getTemplate(tplName);
            tpl.process(model, out);
            Files.createDirectories(outputDir);
            Files.writeString(outputDir.resolve(outputFile), out.toString());
        } catch (TemplateException e) {
            throw new IOException("Template error: " + tplName, e);
        }
    }

}
