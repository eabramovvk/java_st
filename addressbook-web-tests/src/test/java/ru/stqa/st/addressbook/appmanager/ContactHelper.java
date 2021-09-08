package ru.stqa.st.addressbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import ru.stqa.st.addressbook.model.ContactData;
import ru.stqa.st.addressbook.model.Contacts;
import ru.stqa.st.addressbook.model.GroupData;
import ru.stqa.st.addressbook.model.Groups;

import java.util.List;

import static javafx.beans.binding.Bindings.select;


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

        if (creation) {
            if (contactData.getGroups().size() > 0) {
                Assert.assertTrue(contactData.getGroups().size() == 1 );
                new Select(wd.findElement(By.name("new_group"))).selectByVisibleText(contactData.getGroups().iterator().next().getName());
            }        } else {
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
        click(By.cssSelector("input[value='" + id + "']"));
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
        fillContactForm (contactData, true);
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

    public void selectContactInCheckbox(int id)
    {
        wd.findElement(By.cssSelector("input[value='" + id + "']")).click();
    }

    public void clickAddToGroup()
    {
        wd.findElement(By.name("add")).click();
    }
    public void clickRemoveContactFromGroup()
    {
        wd.findElement(By.name("remove")).click();
    }

    public void addContactToGroup(int id, ContactData contact, GroupData group) {
        selectContactInCheckbox(id);
        dropDownClick(String.format("//div[@id='content']/form[2]/div[4]/select/option[@value='%s']",group.getId()));
        clickAddToGroup();
        goToHomePage();
    }

    public boolean isContactInGroup(ContactData contact, GroupData group){
        if(contact.getGroups().size() == 0){
            return false;
        }
        Groups contactGroups = contact.getGroups();
        for (GroupData contactGroup:contactGroups){
            if (contactGroup.equals(group)){
                return true;
            }
        }
        return false;
    }

    public void clickOnGroupForDeletion()    {
        wd.findElement(By.name("group")).click();
    }
    public void selectGroupFromFilterForDeletion(GroupData group) {
        String groupId = String.valueOf(group.getId());
        new Select(wd.findElement(By.name("group"))).selectByValue(groupId);    }

    public void deleteContactFromGroup(ContactData contact, GroupData groupUnassigned) {
        goToHomePage();
        clickOnGroupForDeletion();
        //selectGroupFromFilterForDeletion(groupSelect);
        selectContactInCheckbox(contact.getId());
        clickRemoveContactFromGroup();
        goToHomePage();
    }

    public void allGroupsOnUserPage() {
        new Select(wd.findElement(By.name("group"))).selectByVisibleText("[all]");
    }

    public void gotoCreateContactPage() {
        wd.findElement(By.linkText("add new")).click();
    }

    public void choiceGroup(String nameGroup) {
        select(By.name("to_group"), nameGroup);
    }

    public void selectGroupFilterByName(GroupData group) {
        String groupId = String.valueOf(group.getId());
        new Select(wd.findElement(By.name("to_group"))).selectByValue(groupId);
    }
    public void selectedGroup(ContactData user, GroupData group){
        selectContactInCheckbox(user.getId());
        String groupId = String.valueOf(group.getId());
        new Select(wd.findElement(By.name("to_group"))).selectByValue(groupId);
        clickAddToGroup();
        contactCache = null;
    }
}


