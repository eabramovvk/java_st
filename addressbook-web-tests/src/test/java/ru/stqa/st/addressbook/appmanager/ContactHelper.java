package ru.stqa.st.addressbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import ru.stqa.st.addressbook.model.ContactData;
import ru.stqa.st.addressbook.model.Contacts;
import java.util.List;


public class ContactHelper extends HelperBase {

    public ContactHelper(WebDriver wd) {
        super(wd);
    }


    public void submitContactCreation() {
        wd.findElement(By.name("submit")).click();
    }

    public void fillContactForm(ContactData contactData, boolean creation) {
        type(By.name("firstname"),contactData.getFirstname());
        type(By.name("lastname"),contactData.getLastname());
        type(By.name("address"),contactData.getAddress());
        type(By.name("home"),contactData.getHomePhone());
        type(By.name("email"),contactData.getEmail());
        attach(By.name("photo"),contactData.getPhoto());
        if (creation) {
            new Select(wd.findElement(By.name("new_group"))).selectByVisibleText(contactData.getGroup());
        } else {
            Assert.assertFalse(isElementPresent(By.name("new_group")));
        }

    }

    public void goToHomePage() {
        if (isElementPresent(By.id("maintable"))) {
            return;
        }
        wd.findElement(By.linkText("home page")).click();
    }
    public void selectContactById(int id) {
        wd.findElement((By.cssSelector("a[href*='edit.php?id=" + id + "']"))).click();
    }

    public void deleteSelectedContacts() {
        click(By.xpath("//input[@value='Delete']"));
    }
    public void acceptAlert() {

        wd.switchTo().alert().accept();
        wd.findElement(By.cssSelector("div.msgbox"));
    }

    public void initContactModification(int index) {
        wd.findElements(By.xpath("//img[@alt='Edit']")).get(index).click();
    }

    public void submitContactModifications() {
        click(By.xpath("(//input[@name='update'])[2]"));
    }

    public boolean isThereAContact() {
        return isElementPresent(By.xpath("//img[@alt='Edit']"));
    }

    public void create(ContactData contactData, boolean creation) {
        fillContactForm (contactData, creation);
        submitContactCreation();
        contactCache = null;
        goToHomePage();
    }

    public void modify (ContactData contact){
        selectContactById(contact.getId());
        fillContactForm(contact,false);
        submitContactModifications();
        contactCache = null;
        goToHomePage();
    }

    public void delete (ContactData contact){
        selectContactById(contact.getId());
        deleteSelectedContacts();
        contactCache = null;
        goToHomePage();
    }
    public int count() {
        return wd.findElements(By.name("selected[]")).size();
    }

    private Contacts contactCache = null;

    public Contacts all() {
        if (contactCache != null) {
            return new Contacts(contactCache);
        }
        contactCache = new Contacts();
        Contacts contacts = new Contacts();
        List<WebElement> elements = wd.findElements(By.name("entry"));
        for (WebElement element : elements) {
            List<WebElement> cells = element.findElements(By.tagName("td"));
            int id = Integer.parseInt(element.findElement(By.tagName("input")).getAttribute("value"));
            String lastname = cells.get(1).getText();
            String firstname = cells.get(2).getText();
            String allPhones = cells.get(5).getText();
            String allEmails = cells.get(4).getText();
            String allAddress = cells.get(3).getText();
            contactCache.add(new ContactData().withId(id).withFirstName(firstname).withLastName(lastname)
                    .withAllPhones(allPhones).withEmail(allEmails).withAddress(allAddress));
        }
        return new Contacts(contactCache);
    }
    public ContactData infoFromEditForm(ContactData contact) {
        selectContactById(contact.getId());
        String firstname = wd.findElement(By.name("firstname")).getAttribute("value");
        String lastname = wd.findElement(By.name("lastname")).getAttribute("value");
        String home = wd.findElement(By.name("home")).getAttribute("value");
        String mobile = wd.findElement(By.name("mobile")).getAttribute("value");
        String work = wd.findElement(By.name("work")).getAttribute("value");
        String email = wd.findElement(By.name("email")).getAttribute("value");
        String email2 = wd.findElement(By.name("email2")).getAttribute("value");
        String email3 = wd.findElement(By.name("email3")).getAttribute("value");
        String address = wd.findElement(By.name("address")).getText();
        wd.navigate().back();
        return new ContactData().withId(contact.getId()).withFirstName(firstname).withLastName(lastname)
                .withHomePhone(home).withMobilePhone(mobile).withWorkPhone(work).withEmail(email).withEmail2(email2).withEmail3(email3).withAddress(address);

    }
}


