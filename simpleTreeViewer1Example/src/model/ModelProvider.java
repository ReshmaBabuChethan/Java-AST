/**
 * @(#) ModelProvider.java
 */
package model;

import java.util.ArrayList;
import java.util.List;

/**
 * @since J2SE-1.8
 */
public enum ModelProvider {
   INSTANCE;

   private List<Person> persons;

   private ModelProvider() {
      persons = new ArrayList<Person>();
      // group 1
      Person p1 = new Person("Customer Group 1");
      Person p1a = new Person("Customer 1-a", p1);
      Person p1b = new Person("Customer 1-b", p1);
      p1.add(p1a);
      p1.add(p1b);

      // group 2
      Person p2 = new Person("Customer Group 2");
      Person p2a = new Person("Customer 2-a", p2);
      Person p2b = new Person("Customer 2-b", p2);
      p2.add(p2a);
      p2.add(p2b);

      // group 3
      Person p3 = new Person("Customer Group 3", p2);
      Person p3a = new Person("Customer 3-a", p2);
      Person p3b = new Person("Customer 3-b", p2);
      p3.add(p3a);
      p3.add(p3b);
      p2.add(p3); // p2 links to p3

      // group 4
      Person p4 = new Person("Customer Group 4", p2);
      Person p4a = new Person("Customer 4-a", p2);
      Person p4b = new Person("Customer 4-b", p2);
      p4.add(p4a);
      p4.add(p4b);
      p3.add(p4); // p3 links to p4

      persons.add(p1);
      persons.add(p2);
   }

   public List<Person> getPersons() {
      return persons;
   }
}
