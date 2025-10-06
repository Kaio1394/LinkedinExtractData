package com.kaiosantiago.linkedin.scraper.service;

import com.kaiosantiago.linkedin.scraper.helpers.SeleniumHelper;

public class LinkedinService {
    private SeleniumHelper helper;

    public LinkedinService(SeleniumHelper helper) {
        this.helper = helper;
    }

    public void openUrlLinkedin(){
        helper.openUrl("https://www.linkedin.com/jobs/search?trk=guest_homepage-basic_guest_nav_menu_jobs");
    }
    public void closeLinkedinPage(){
        helper.close();
    }
}
