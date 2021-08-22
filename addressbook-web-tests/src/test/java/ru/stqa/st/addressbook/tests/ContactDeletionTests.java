package ru.stqa.st.addressbook.tests;

import org.testng.annotations.Test;
import ru.stqa.st.addressbook.model.ContactData;
import org.testng.Assert;
import java.util.List;

public class ContactDeletionTests extends TestBase{

    @Test
    public void testContactDeletion(){
        if (! app.getContactHelper().isThereAContact()){
            app.getNavigationHelper().goToContactCreation();
            app.getContactHelper().createContact(new ContactData("TestName", "TestLastName", "TestAddress", "+79217777777", "testmail@test.test","test1"), true);
            app.getNavigationHelper().goToHomePage();
        }
        List<ContactData> before = app.getContactHelper().getContactList();
        app.getContactHelper().selectContact(before.size()-1);
        app.getContactHelper().deleteSelectedContacts();
        app.getContactHelper().acceptAlert();
        app.getNavigationHelper().goToHomePage();
        List<ContactData> after = app.getContactHelper().getContactList();
        Assert.assertEquals(after.size(), before.size() - 1);
        before.remove(before.size() - 1);
        Assert.assertEquals(before, after);

    }
}
