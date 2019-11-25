package jzheng;

import dnd.models.Exit;
import dnd.models.Monster;
import dnd.models.Stairs;
import java.util.ArrayList;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;

/**
*
* @author Jingjing Zheng
*/

public class PassageTest {
  //you don't have to use instance variables but it is easier
  // in many cases to have them and use them in each test
  private Passage testerOne;
  private Passage testerTwo;
  private PassageSection sectionOne;
  private PassageSection sectionTwo;

  public PassageTest() {
  }


  /**
   * The set up process
   */
  @Before
  public void setup(){
    //set up any instance variables here so that they have fresh values for every test
    testerOne = new Passage();
    testerTwo = new Passage();
    sectionOne = new PassageSection();
    sectionTwo = new PassageSection();
  }

  /**
   * Test of Get description method, of class Passage.
   * Test if it can output a description of an entire length of passage
   */
  @Test
  public void testGetDescription() {
    System.out.println("Get description Test");
    this.setup();
    testerOne.addPassageSection(sectionOne);
    testerOne.addPassageSection(sectionTwo);

    testerTwo.addPassageSection(sectionOne);

    String result = testerOne.getDescription();
    String result2 = testerTwo.getDescription();
    assertTrue(result.contains("2") && result2.contains("1"));
  }

  /**
   * Test of setDoors method, of class Passage.
   */
  @Test
  public void testSetDoor() {
    System.out.println("New Door Test");
    this.setup();

    Door expected = new Door();
    testerOne.setDoor(expected);
    Door result = testerOne.getDoor(0);

    assertEquals(expected,result);
  }

  /**
   * Test of getDoor method, of class Passage.
   */
  @Test
  public void testGetDoor() {
    System.out.println("Get Door Test");
    this.setup();

    Door newDoor = new Door();
    testerOne.setDoor(newDoor);

    testerOne.addPassageSection(sectionOne);
    Door doorTwo = new Door();
    testerOne.setDoor(doorTwo);

    testerOne.addPassageSection(sectionTwo);
    Door doorThree = new Door();
    testerOne.setDoor(doorThree);

    Door result = testerOne.getDoor(3);
    Door expected = null;

    assertEquals(expected,result);
  }

  /**
   * Test of getDoors method, of class Passage.
   */
  @Test
  public void testGetDoors() {
    System.out.println("Get Doors Test");
    this.setup();

    Door newDoor = new Door();
    testerOne.setDoor(newDoor);

    testerOne.addPassageSection(sectionOne);
    Door doorTwo = new Door();
    testerOne.setDoor(doorTwo);

    int result = testerOne.getDoors().size();
    int expected = 2;


    assertEquals(expected,result);
  }

  /**
   * Test of get passage section method, of class Passage.
   */
  @Test
  public void testGetPassageSections() {
    System.out.println("Get Passage Sections Test");
    this.setup();

    testerOne.addPassageSection(sectionOne);
    testerOne.addPassageSection(sectionTwo);

    int result = testerOne.getPassageSections().size();
    int expected = 2;


    assertEquals(expected,result);
  }

  /**
   * Test of add passage section method, of class Passage.
   */
  @Test
  public void testAddPassageSection() {
    System.out.println("Add Passage Sections Test");
    this.setup();

    testerOne.addPassageSection(sectionOne);
    testerOne.addPassageSection(sectionTwo);

    String result = testerOne.getDescription();

    assertTrue(result.contains(sectionOne.getDescription()) && result.contains(sectionTwo.getDescription()));
  }

  /**
   * Test of clear passage section method, of class Passage.
   */
  @Test
  public void testClearPassageSection() {
    System.out.println("Clear Passage Section Test");
    this.setup();

    testerOne.addPassageSection(sectionOne);
    testerOne.addPassageSection(sectionTwo);

    testerOne.clearPassageSection(0);

    int expected = 1;
    int result = testerOne.getPassageSections().size();


    assertEquals(expected,result);
  }

  /**
   * Test of add monster section method, of class Passage.
   */
  @Test
  public void testAddMonster() {
    System.out.println("Add Monster Test");
    this.setup();

    testerOne.addPassageSection(sectionOne);
    Monster monster = new Monster();
    testerOne.addMonster(monster,0);

    String result = testerOne.getDescription();
    String expected = monster.getDescription();


    assertTrue(result.contains(expected));
  }

  /**
   * Test of get monster section method, of class Passage.
   */
  @Test
  public void testGetMonster() {
    System.out.println("Get Monster Test");
    this.setup();

    testerOne.addPassageSection(sectionOne);
    Monster monster = new Monster();
    testerOne.addMonster(monster,0);

    testerOne.addPassageSection(sectionTwo);
    Monster monster2 = new Monster();
    testerOne.addMonster(monster2,1);

    Monster result = testerOne.getMonster(1);
    Monster expected = monster2;


    assertEquals(expected,result);
  }


}
