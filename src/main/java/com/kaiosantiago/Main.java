package com.kaiosantiago;

import com.kaiosantiago.linkedin.scraper.helpers.SeleniumHelper;
import com.kaiosantiago.linkedin.scraper.service.LinkedinService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {
    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        if(args.length < 1) throw new IllegalArgumentException("É obrigatório repassar o parâmetro [job]. Exemplo: java Main '<job>'");

        String job = args[0];

        SeleniumHelper helper = null;
        LinkedinService service = null;
        try {
            helper = new SeleniumHelper("chrome", false, "");
            service = new LinkedinService(helper);
            logger.info("Iniciando aplicação de extração de dados no Linkedin.");
            service.openUrlLinkedin();
            if(service.divLoginGoogleExists()) service.clickCloseLoginGoogleExists();
            Thread.sleep(2000);
            service.setTextjob(job);
            Thread.sleep(2000);
        } catch (Exception e) {
            logger.error("Erro durante a execução do scraper: " + e.getMessage());
            e.printStackTrace();
        }
        finally {
            if(service != null) service.closeLinkedinPage();
        }
    }
}