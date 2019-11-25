package jzheng;

import dnd.models.Trap;
import java.util.ArrayList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.junit.Assert.assertTrue;
import org.junit.Test;


/**
*
* @author Jingjing Zheng
*/
public class DoorTest {

    public DoorTest() {


    }

    /**
     * Test of Get description method, of class Door.
     */
    @Test
    public void testGetDescription() {
      System.out.println("Get description Test");

      Door myDoor = new Door();
      Passage myPassage = new Passage();
      Chamber myChamber = new Chamber();

      myDoor.setSpaces(myPassage,myChamber);
      String result = myDoor.getDescription();
      assertTrue(result.contains(myPassage.getDescription()) && result.contains(myChamber.getDescription()));
    }

    /**
     * Test of Set trapped method, of class Door.
     */
    @Test
    public void testSetTrapped() {
      System.out.println("Set Trapped Test");

      Door myDoor = new Door();
      myDoor.setTrapped(true,3);

      String result = myDoor.getDescription();
      Trap myTrap = new Trap();
      myTrap.setDescription(3);
      String expected = myTrap.getDescription();

      assertTrue(result.contains(expected));
    }

    /**
     * Test of Set trap information method, of class Door.
     */
    @Test
    public void testSetTrapInfo() {
      System.out.println("Set Trap Inforamtion Test");

      Door myDoor = new Door();
      myDoor.setTrapped(true);
      myDoor.setTrapInfo(3);

      String result = myDoor.getDescription();
      Trap myTrap = new Trap();
      myTrap.setDescription(3);
      String expected = myTrap.getDescription();

      assertTrue(result.contains(expected));
    }

    /**
     * Test of Set Open method, of class Door.
     */
    @Test
    public void testSetOpen() {
      System.out.println("Set Open Test");

      Door myDoor = new Door();
      myDoor.setOpen(false);

      boolean result = myDoor.isOpen();
      boolean expected = false;
      assertEquals(expected,result);
    }

    /**
     * Test of Set archway method, of class Door.
     */
    @Test
    public void testSetArchway() {
      System.out.println("Set Archway Test");

      Door myDoor = new Door();
      myDoor.setArchway(true);
      myDoor.setOpen(false);

      boolean result = myDoor.isOpen();
      boolean expected = true;
      assertEquals(expected,result);
    }

    /**
     * Test of isTrapped method, of class Door.
     */
    @Test
    public void testIsTrapped() {
      System.out.println("Is Trapped Test");

      Door myDoor = new Door();
      myDoor.setTrapped(false,1);

      String result = myDoor.getDescription();
      Trap myTrap = new Trap();
      myTrap.setDescription(1);
      String notExpected = myTrap.getDescription();

      assertTrue(!(result.contains(notExpected)));
    }

    /**
     * Test of isOpen method, of class Door.
     */
    @Test
    public void testIsOpen() {
      System.out.println("Is Open Test");

      Door myDoor = new Door();
      myDoor.setOpen(false);
      myDoor.setArchway(true);

      boolean expected = true;
      boolean result = myDoor.isOpen();
      assertEquals(expected,result);
    }

    /**
     * Test of isArchway method, of class Door.
     */
    @Test
    public void testIsArchway() {
      System.out.println("Is Archway Test");

      Door myDoor = new Door();
      myDoor.setArchway(true);

      boolean expected = true;
      boolean result = myDoor.isArchway();
      assertEquals(expected,result);
    }

    /**
     * Test of Get trap description method, of class Door.
     */
    @Test
    public void testGetTrapDescription() {
      System.out.println("Get Trap description Test");

      Door myDoor = new Door();
      myDoor.setTrapped(true,5);

      String result = myDoor.getTrapDescription();
      Trap myTrap = new Trap();
      myTrap.setDescription(5);
      String expected = myTrap.getDescription();
      assertEquals(expected,result);
    }

    /**
     * Test of Get Spaces method, of class Door.
     */
    @Test
    public void testGetSpaces() {
      System.out.println("Get Space Test");

      Door myDoor = new Door();
      Chamber myChamber = new Chamber();
      Passage myPassage = new Passage();
      myDoor.setSpaces(myChamber,myPassage);

      String result = myDoor.getSpaces().get(0).getDescription();
      String expected = myChamber.getDescription();
      assertEquals(expected,result);
    }

    /**
     * Test of Set Spaces method, of class Door.
     */
    @Test
    public void testSetSpace() {
      System.out.println("Set Space Test");

      Door myDoor = new Door();
      Chamber myChamber = new Chamber();
      Passage myPassage = new Passage();
      myDoor.setSpaces(myPassage,myChamber);

      String result = myDoor.getDescription();

      assertTrue(result.contains(myChamber.getDescription()) && result.contains(myPassage.getDescription()));
    }

}
