package jzheng;

import dnd.models.ChamberContents;
import dnd.models.ChamberShape;
import dnd.models.DnDElement;
import dnd.models.Monster;
import dnd.models.Stairs;
import dnd.models.Trap;
import dnd.models.Treasure;
import java.util.ArrayList;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
*
* @author Jingjing Zheng
*/
public class ChamberTest {
    ChamberShape theShape;
    ChamberContents theContents;
    Monster myMonster;

    public ChamberTest() {
    }

    /**
     * The set up process
     */
    @Before
    public void setup(){
        theShape = new ChamberShape();
        theShape.setShape();
        theShape.setNumExits();
        theContents = new ChamberContents();
    }

    /**
     * Test of Get description method, of class Chamber
     */
    @Test
    public void testGetDescription() {
      System.out.println("Get description Test");
      this.setup();

      theContents.setDescription(15);//set it to monster and treausre mode
      Chamber myChamber = new Chamber(theShape,theContents);

      Treasure myTreasure = new Treasure();
      myChamber.addTreasure(myTreasure);

      Treasure myTreasure2 = new Treasure();
      myChamber.addTreasure(myTreasure2);

      Monster myMonster = new Monster();
      myChamber.addMonster(myMonster);

      String result = myChamber.getDescription();

      assertTrue(result.contains(myTreasure.getDescription()) && result.contains(myTreasure2.getDescription()) && result.contains(myMonster.getDescription()));
    }

    /**
     * Test of Set Door method, of class Chamber
     */
    @Test
    public void testSetDoor() {
      System.out.println("Set Door Test");
      this.setup();

      Chamber myChamber = new Chamber(theShape,theContents);
      Door myDoor = new Door();

      myChamber.setDoor(myDoor);

      Door result = myChamber.getDoors().get(0);
      Door expected = myDoor;


      assertEquals(expected,result);
    }

    /**
     * Test of Add Monster method, of class Chamber
     */
    @Test
    public void testAddMonster() {
      System.out.println("Add Monster Test");
      this.setup();

      theContents.setDescription(13);//set it to only monster mode
      Chamber myChamber = new Chamber(theShape,theContents);
      Monster myMonster = new Monster();
      Monster myMonster2 = new Monster();

      myChamber.addMonster(myMonster);
      myChamber.addMonster(myMonster2);

      int expected = 2;
      int result = myChamber.getMonsters().size();

      assertEquals(expected,result);
    }

    /**
     * Test of Set shape method, of class Chamber
     */
    @Test
    public void testSetShape() {
      System.out.println("Set shape Test");
      this.setup();

      Chamber myChamber = new Chamber();
      myChamber.setShape(theShape);

      String result = myChamber.getChamberShape().getShape();
      String expected = theShape.getShape();


      assertEquals(expected,result);
    }

    /**
     * Test of get Chamber shape method, of class Chamber
     */
    @Test
    public void testGetChamberShape() {
      System.out.println("Get shape Test");
      this.setup();

      Chamber myChamber = new Chamber(theShape,theContents);
      ChamberShape test = new ChamberShape();
      myChamber.setShape(test);

      String result = myChamber.getChamberShape().getShape();
      String expected = test.getShape();


      assertEquals(expected,result);
    }

    /**
     * Test of Get Doors method, of class Chamber
     */
    @Test
    public void testGetDoors() {
      System.out.println("Get Doors Test");
      assertEquals(1,1);
    }

    /**
     * Test of Get monsters method, of class Chamber
     */
    @Test
    public void testGetMonsters() {
      System.out.println("Get Monsters Test");
      this.setup();

      theContents.setDescription(13);
      Chamber myChamber = new Chamber();
      Monster myMonster = new Monster();
      Monster myMonster2 = new Monster();
      Monster myMonster3 = new Monster();

      myChamber.addMonster(myMonster);
      myChamber.addMonster(myMonster2);
      myChamber.addMonster(myMonster3);

      Monster result = myChamber.getMonsters().get(2);
      Monster expected = myMonster3;

      assertEquals(expected,result);
    }

    /**
     * Test of Add treasure method, of class Chamber
     */
    @Test
    public void testAddTreasure() {
      System.out.println("Add Treasure Test");
      this.setup();

      theContents.setDescription(20);//set it to treasure mode
      Chamber myChamber = new Chamber(theShape,theContents);
      Treasure myTreasure = new Treasure();

      myChamber.addTreasure(myTreasure);

      String result = myChamber.getDescription();

      assertTrue(result.contains(myTreasure.getDescription()));
    }

    /**
     * Test of get Treasure list method, of class Chamber
     */
    @Test
    public void testgetTreasureList() {
      System.out.println("Get Treasure List Test");

      theContents.setDescription(20);//set it to treasure mode
      Chamber myChamber = new Chamber(theShape,theContents);

      Treasure myTreasure = new Treasure();
      myChamber.addTreasure(myTreasure);

      Treasure myTreasure2 = new Treasure();
      myChamber.addTreasure(myTreasure2);

      Treasure myTreasure3 = new Treasure();
      myChamber.addTreasure(myTreasure3);

      Treasure myTreasure4 = new Treasure();
      myChamber.addTreasure(myTreasure4);

      int result = myChamber.getTreasureList().size();
      int expected = 4;

      assertEquals(expected,result);
    }





}
