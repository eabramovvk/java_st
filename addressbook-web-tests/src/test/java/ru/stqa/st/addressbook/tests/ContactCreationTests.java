package ru.stqa.st.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.st.addressbook.model.ContactData;

import java.util.Comparator;
import java.util.List;
import java.util.Set;

public class ContactCreationTests extends TestBase {

  @Test
  public void testContactCreationTests() throws Exception {
    Set<ContactData> before = app.contact().all();
    app.goTo().goToContactCreation();
    ContactData contact = new ContactData().withFirstName("TestName").withLastName("TestLastName").withAddress("TestAddress").withHomePhone("+79217777777").withEmail("testmail@test.test").withGroup("[none]");
    app.contact().create(contact,true);

    Set<ContactData> after = app.contact().all();
    Assert.assertEquals(after.size(),before.size()+1);
    contact.withId(after.stream().mapToInt((c) -> c.getId()).max().getAsInt());
    before.add(contact);
    Assert.assertEquals(before, after);
  }

}
