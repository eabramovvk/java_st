package ru.stqa.st.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.st.addressbook.model.ContactData;

import java.util.Comparator;
import java.util.List;

public class ContactCreationTests extends TestBase {

  @Test (enabled = false)
  public void testContactCreationTests() throws Exception {
    List<ContactData> before = app.getContactHelper().getContactList();
    app.goTo().goToContactCreation();
    ContactData contact = new ContactData("TestName", "TestLastName", "TestAddress", "+79217777777", "testmail@test.test","test1");
    app.getContactHelper().fillContactForm(contact,true);
    app.getContactHelper().submitContactCreation();
    app.goTo().goToHomePage();

    List<ContactData> after = app.getContactHelper().getContactList();
    Assert.assertEquals(after.size(),before.size()+1);

    before.add(contact);
    Comparator<? super ContactData> byId = (c1, c2) -> Integer.compare(c1.getId(), c2.getId());
    before.sort(byId);
    after.sort(byId);
    Assert.assertEquals(before, after);
  }

}
