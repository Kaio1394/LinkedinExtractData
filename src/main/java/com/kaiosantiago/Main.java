package com.kaiosantiago;

import com.kaiosantiago.linkedin.scraper.excel.ExcelHelper;
import com.kaiosantiago.linkedin.scraper.helpers.selenium.SeleniumHelper;
import com.kaiosantiago.linkedin.scraper.service.LinkedinService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {
    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        if(args.length < 4) throw new IllegalArgumentException("É obrigatório repassar os parâmetros <job> <country> <limit_search>.");

        String job = args[0];
        String country = args[1];
        int limitSearch = Integer.parseInt(args[2]);
        String pathExcel = args[3];

        SeleniumHelper helper = null;
        LinkedinService service = null;
        ExcelHelper excelHelper = null;
        try {

            helper = new SeleniumHelper("chrome", false, "");
            excelHelper = new ExcelHelper(pathExcel);
            service = new LinkedinService(helper, excelHelper);

            logger.info("Iniciando aplicação de extração de dados no Linkedin.");
            service.openUrlLinkedin();
            if(service.divLoginGoogleExists()) service.clickCloseLoginGoogleExists();
            Thread.sleep(2000);
            service.setTextJob(job);
            service.setTextCountry(country);
            service.clickSearch();
            Thread.sleep(5000);
            service.selectDatePublishJob("Últimas 24 horas");
            Thread.sleep(5000);

        } catch (Exception e) {
            logger.error("Erro durante a execução do scraper: " + e.getMessage());
            e.printStackTrace();
        }
        finally {
            if(service != null) service.closeLinkedinPage();
        }
    }
}