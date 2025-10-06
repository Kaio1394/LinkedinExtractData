package com.kaiosantiago.linkedin.scraper.service;

import com.kaiosantiago.linkedin.scraper.excel.ExcelHelper;
import com.kaiosantiago.linkedin.scraper.helpers.selenium.SeleniumHelper;
import org.openqa.selenium.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LinkedinService {
    private static final Logger logger = LoggerFactory.getLogger(LinkedinService.class);
    private final SeleniumHelper helper;
    private final ExcelHelper excelHelper;

    private static final String XPATH = "xpath";

    private static final String URL_LINKEDIN_JOG = "https://www.linkedin.com/jobs/search?trk=guest_homepage-basic_guest_nav_menu_jobs";
    private static final String BUTTON_CLOSE_LOGIN_GOOGLE = "//button[contains(@class,'contextual-sign-in-modal__modal-dismiss')]";
    private static final String TEXT_FIELD_JOB_SEARCH = "//input[@aria-controls='job-search-bar-keywords-typeahead-list']";
    private static final String TEXT_FIELD_CUNTRY = "//input[@aria-controls='job-search-bar-location-typeahead-list']";
    private static final String BUTTON_SEARCH = "/html/body/div[1]/header/nav/section/section[2]/form/button";
    private static final String CARD_TITLE = "//ul[@class='jobs-search__results-list']/li[INDEX_CARD]//h3";
    private static final String BUTTON_DATE_PUBLISH_JOB = "//button[contains(@class,'filter-button')][contains(@aria-label,'Filtro Data do anúncio')]";
    private static final String BUTTON_DATE_PUBLISH_JOB_LAST_24_HOURS = "//label[contains(normalize-space(.), 'TEXT_DATE')]/preceding-sibling::input";

    public LinkedinService(SeleniumHelper helper, ExcelHelper excelHelper) {
        this.helper = helper;
        this.excelHelper = excelHelper;
    }
    public void selectDatePublishJob(String text) throws InterruptedException {
        String xpath = BUTTON_DATE_PUBLISH_JOB_LAST_24_HOURS;
        xpath = xpath.replace("TEXT_DATE", text);
        helper.clickElement(XPATH, BUTTON_DATE_PUBLISH_JOB);
        Thread.sleep(2000);
        helper.clickElement(XPATH, xpath);
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
    public void setTextJob(String job){
        helper.setText(XPATH, TEXT_FIELD_JOB_SEARCH, job, false, -1);
    }
    public void setTextCountry(String country){
        helper.setText(XPATH, TEXT_FIELD_CUNTRY, country, false, -1);
    }
    public void clickSearch(){
//        helper.clickElement(XPATH, BUTTON_SEARCH);
        helper.sendKeystrokes(XPATH, TEXT_FIELD_CUNTRY, Keys.ENTER);
    }
}
