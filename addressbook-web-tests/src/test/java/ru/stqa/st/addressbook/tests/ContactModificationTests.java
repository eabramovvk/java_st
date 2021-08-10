package ru.stqa.st.addressbook.tests;

import org.testng.annotations.Test;
import ru.stqa.st.addressbook.model.ContactData;

public class ContactModificationTests extends TestBase {

    @Test
    public void testContactModification(){
        app.getContactHelper().selectContact();
        app.getContactHelper().initContactModification();
        app.getContactHelper().fillContactForm(new ContactData("TestName", "TestLastName", "TestAddress", "+79217777777", "testmail@test.test", null), false);
        app.getContactHelper().submitContactModifications();
        app.getNavigationHelper().goToHomePage();
    }
}
