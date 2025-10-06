package com.kaiosantiago;

import com.kaiosantiago.linkedin.scraper.helpers.SeleniumHelper;
import com.kaiosantiago.linkedin.scraper.service.LinkedinService;

public class Main {
    public static void main(String[] args) {
        SeleniumHelper helper = null;
        LinkedinService service = null;

        try {
            helper = new SeleniumHelper("chrome", false, "");
            service = new LinkedinService(helper);
            service.openUrlLinkedin();
        } catch (Exception e) {
            System.err.println("Erro durante a execução do scraper: " + e.getMessage());
            e.printStackTrace();
        }
        finally {
            service.closeLinkedinPage();
        }
    }
}