package com.bebo.pages;

import com.bebo.config.BaseClass;
import com.bebo.utils.DriverFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.lang.ref.SoftReference;
import java.util.List;

public class AllFilesPage {

    WebDriverWait wait;
    int startNumber;
    int finalNumber;

    @FindBy(xpath = "//*[@id=\"mod-action-bar-1\"]/div[2]/div[2]/div[1]/a/span[1]")
    WebElement ddlNew;

    @FindBy(xpath = "//button[@data-type='new-folder']")
    WebElement eleFolder;

    @FindBy(className = "new-folder-popup")
    WebElement popNewFolder;

    @FindBy(name = "folderName")
    WebElement txtFolderName;

    @FindBy(xpath = "(//input[@type='text'])[3]")
    WebElement txtInvitePeople;

    @FindBy(name = "permission")
    WebElement ddlPermission;

    @FindBy(xpath = "//button[@data-resin-target='createfolder']")
    WebElement btnCreate;

    @FindBy(xpath = "(//*[starts-with(@id,'menu-file-list-')]/li[8]/button)[1]")
    WebElement lstFirstMoreActions;

    @FindBy(xpath = "//input[@class='popup-prompt-input']")
    WebElement txtRenameFolder;

    @FindBy(xpath = "//button[@data-event-type='ok']")
    WebElement btnRenameOk;

    public AllFilesPage(){
        PageFactory.initElements(DriverFactory.getInstance().getDriver(),this);
    }

    public void clickNew(){
        ddlNew.click();
    }

    public void selectFolder(){
        eleFolder.click();
    }

    public void setFolderName(String name){
        txtFolderName.sendKeys(name);
    }

    public void setInvitePeople(String inviteEmail){
        txtInvitePeople.sendKeys(inviteEmail);
    }

    public void selectPermission(String permission){
        new Select(ddlPermission).selectByVisibleText(permission);
    }

    public void clickCreate(){
        btnCreate.click();
    }

    public void clickFirsMoreActions(){
        lstFirstMoreActions.click();
    }

    public void setRenameFolder(String folderRaname){
        txtRenameFolder.sendKeys(folderRaname);
    }

    public void clickRenameOkay(){
        btnRenameOk.click();
    }

    public int getFoldersNumber(){
        wait = new WebDriverWait(DriverFactory.getInstance().getDriver(), 15);
        wait.until(ExpectedConditions.visibilityOfAllElements(DriverFactory.getInstance().getDriver().findElements(By.xpath("//ol[contains(@class,'file-list-body')]/li"))));
        return DriverFactory.getInstance().getDriver().findElements(By.xpath("//ol[contains(@class,'file-list-body')]/li")).size();
    }

    public List<WebElement> getListFolders(){
        wait = new WebDriverWait(DriverFactory.getInstance().getDriver(), 15);
        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath("//ol[contains(@class,'file-list-body')]/li")));
        List<WebElement> folders = DriverFactory.getInstance().getDriver().findElements(By.xpath("//ol[contains(@class,'file-list-body')]/li"));
        return folders;
    }

    public void clickThreeDots(){
        BaseClass.waitTime(5);
        List<WebElement> options = DriverFactory.getInstance().getDriver().findElements(By.xpath("//button[contains(@class,'file-list-item-context-menu')]"));
        WebElement btnDots = options.get(0);
        Actions builder = new Actions(DriverFactory.getInstance().getDriver());
        Action mouseOver = builder
                .moveToElement(btnDots)
                .build();
        mouseOver.perform();
        btnDots.click();
    }


    public void createNewFolder(String folderName, String emailInvite, String permission){
        this.clickNew();
        this.selectFolder();

        wait = new WebDriverWait(DriverFactory.getInstance().getDriver(), 15);
        wait.until(ExpectedConditions.visibilityOf(popNewFolder));

        this.setFolderName(folderName);
        this.setInvitePeople(emailInvite);
        this.selectPermission(permission);
        this.clickCreate();
    }

    public boolean renameFolder(String newFolderName){
        this.clickThreeDots();
        this.clickFirsMoreActions();

        WebElement lstRename = DriverFactory.getInstance().getDriver().findElement(By.xpath("(//*[starts-with(@id,'moreactions-submenu-item')]/li[3]/button)[1]"));
        Actions builder = new Actions(DriverFactory.getInstance().getDriver());
        Action mouseOver = builder
                .moveToElement(lstRename)
                .click(lstRename)
                .build();
        mouseOver.perform();
//        lstRename.click();
        this.setRenameFolder(newFolderName);
        this.clickRenameOkay();
        return true;
    }

    public boolean renameFolderDirect(String newFolderName){

        BaseClass.waitTime(5);

        WebElement firstFolder = DriverFactory.getInstance().getDriver().findElement(By.xpath("(//a[@class='item-name-link'])[1]"));

        WebElement renameFolder = DriverFactory.getInstance().getDriver().findElement(By.xpath("(//button[@class='btn-plain rename-btn'])[1]"));

        Actions builder = new Actions(DriverFactory.getInstance().getDriver());
        Action mouseOver = builder
                .moveToElement(firstFolder)
                .build();
        mouseOver.perform();

        Action mouseOver2 = builder
                .moveToElement(renameFolder)
                .click(renameFolder)
                .build();
        mouseOver2.perform();

        this.setRenameFolder(newFolderName);
        this.clickRenameOkay();
        return true;
    }
}
