package ru.stqa.st.addressbook.tests;

import org.testng.annotations.Test;
import ru.stqa.st.addressbook.model.ContactData;

public class ContactCreationTests extends TestBase {

  @Test
  public void testContactCreationTests() throws Exception {

    app.getNavigationHelper().goToContactCreation();
    app.getContactHelper().fillContactForm(new ContactData("TestName", "TestLastName", "TestAddress", "+79217777777", "testmail@test.test","test1"), true);
    app.getContactHelper().submitContactCreation();
    app.getNavigationHelper().goToHomePage();
  }

}
