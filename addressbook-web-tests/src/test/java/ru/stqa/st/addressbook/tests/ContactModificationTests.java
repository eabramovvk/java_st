package ru.stqa.st.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.st.addressbook.model.ContactData;
import java.util.Comparator;
import java.util.List;

public class ContactModificationTests extends TestBase {

    @Test (enabled = false)
    public void testContactModification(){
        if (! app.getContactHelper().isThereAContact()){
            app.goTo().goToContactCreation();
            app.getContactHelper().createContact(new ContactData("TestName", "TestLastName", "TestAddress", "+79217777777", "testmail@test.test","test1"), true);
            app.goTo().goToHomePage();
        }
        List<ContactData> before = app.getContactHelper().getContactList();
        app.getContactHelper().selectContact(before.size()-1);
        app.getContactHelper().initContactModification();
       ContactData contact = new ContactData(before.get(before.size()-1).getId(),"TestName", "TestLastName", "TestAddress", "+79217777777", "testmail@test.test", null);
        app.getContactHelper().submitContactModifications();
        app.goTo().goToHomePage();
        List<ContactData> after = app.getContactHelper().getContactList();
        Assert.assertEquals(after.size(), before.size());

        before.remove(before.size() - 1);
        before.add(contact);
        Comparator<? super ContactData> byId = (c1, c2) -> Integer.compare(c1.getId(), c2.getId());
        before.sort(byId);
        after.sort(byId);
        Assert.assertEquals(before, after);
    }
}
