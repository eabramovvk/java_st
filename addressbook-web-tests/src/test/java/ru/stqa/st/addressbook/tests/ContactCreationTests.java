package ru.stqa.st.addressbook.tests;

import org.testng.annotations.Test;
import ru.stqa.st.addressbook.model.ContactData;
import ru.stqa.st.addressbook.model.Contacts;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactCreationTests extends TestBase {

  @Test
  public void testContactCreationTests() throws Exception {
    Contacts before = app.contact().all();
    app.goTo().goToContactCreation();
    ContactData contact = new ContactData().withFirstName("TestName").withLastName("TestLastName").withAddress("TestAddress").withHomePhone("+79217777777").withEmail("testmail@test.test").withGroup("[none]");
    app.contact().create(contact,true);
    Contacts after = app.contact().all();
    assertThat(after.size(), equalTo(before.size()+1));
    assertThat(after,equalTo(before.withAdded(contact.withId(after.stream().mapToInt((c)-> c.getId()).max().getAsInt()))));
  }

}
