package jzheng;

import dnd.models.Exit;
import dnd.models.Monster;
import org.junit.Test;
import static org.junit.Assert.*;

/**
*
* @author Jingjing Zheng
*/
public class PassageSectionTest {

  public PassageSectionTest() {

  }

  /**
   * Test of Get description method, of class PassageSection
   */
  @Test
  public void testGetDescription() {
    System.out.println("Get description Test");
    PassageSection myPassageSection = new PassageSection("Winnie Zheng");

    String result = myPassageSection.getDescription();
    String expected = "Winnie Zheng";

    assertEquals(expected,result);
  }

  /**
   * Test of Get Door method, of class PassageSection
   */
  @Test
  public void testGetDoor() {
    System.out.println("Get Door Test");

    PassageSection myPassageSection = new PassageSection();
    Door myDoor = new Door();
    myPassageSection.setDoor(myDoor);

    String result = myPassageSection.getDescription();
    //Testing: System.out.println(result);
    String expected = myDoor.getDescription();
    assertTrue(result.contains(expected));
  }

  /**
   * Test of Set Door method, of class PassageSection
   */
  @Test
  public void testSetDoor() {
    System.out.println("Set Door Test");

    PassageSection myPassageSection = new PassageSection();
    Door myDoor = new Door();
    myPassageSection.setDoor(myDoor);

    Door result = myPassageSection.getDoor();
    Door expected = myDoor;
    assertEquals(expected,result);
  }

  /**
   * Test of Get Monster method, of class PassageSection
   */
  @Test
  public void testGetMonster() {
    System.out.println("Get Monster Test");

    PassageSection myPassageSection = new PassageSection();
    Monster myMonster = new Monster();
    myPassageSection.addMonster(myMonster);

    String result = myPassageSection.getDescription();
    String expected = myMonster.getDescription();
    assertTrue(result.contains(expected));
  }

  /**
   * Test of Add Monster method, of class PassageSection
   */
  @Test
  public void testAddMonster() {
    System.out.println("Add Monster Test");

    PassageSection myPassageSection = new PassageSection();
    Monster myMonster = new Monster();
    myPassageSection.addMonster(myMonster);

    Monster result = myPassageSection.getMonster();
    Monster expected = myMonster;
    assertEquals(expected,result);
  }

  /**
   * Test of Set description method, of class PassageSection
   */
  @Test
  public void testSetDescription() {
    System.out.println("Set description Test");

    PassageSection myPassageSection = new PassageSection("Winnie Zheng");
    myPassageSection.setDescription("Here I am");

    String notExpected = "Winnie Zheng";
    String result = myPassageSection.getDescription();


    assertTrue(!(result.contains(notExpected)));
  }



}
