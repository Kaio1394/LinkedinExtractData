package com.kaiosantiago.linkedin.scraper.service;

import com.kaiosantiago.linkedin.scraper.helpers.SeleniumHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LinkedinService {
    private static final Logger logger = LoggerFactory.getLogger(LinkedinService.class);
    private final SeleniumHelper helper;

    private static final String XPATH = "xpath";

    private static final String URL_LINKEDIN_JOG = "https://www.linkedin.com/jobs/search?trk=guest_homepage-basic_guest_nav_menu_jobs";
    private static final String BUTTON_CLOSE_LOGIN_GOOGLE = "//button[contains(@class,'contextual-sign-in-modal__modal-dismiss')]";
    private static final String TEXT_FIELD_JOB_SEARCH = "//input[@aria-controls='job-search-bar-keywords-typeahead-list']";

    public LinkedinService(SeleniumHelper helper) {
        this.helper = helper;
    }

    public void openUrlLinkedin(){
        helper.openUrl(URL_LINKEDIN_JOG);
    }
    public void closeLinkedinPage(){
        helper.close();
    }
    public Boolean divLoginGoogleExists(){
        return helper.elementExists(XPATH, BUTTON_CLOSE_LOGIN_GOOGLE, 5);
    }
    public void clickCloseLoginGoogleExists(){
        helper.clickElement(XPATH, BUTTON_CLOSE_LOGIN_GOOGLE);
    }

    public void setTextjob(String text){
        helper.setText(XPATH, TEXT_FIELD_JOB_SEARCH, text, false, -1);
    }

}
