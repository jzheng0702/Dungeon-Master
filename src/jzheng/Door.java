package jzheng;
import dnd.models.Exit;
import dnd.models.Trap;
import java.util.ArrayList;
import dnd.die.D20;


/**
* @author     jzheng06@uoguelph.ca
* @version    1.3
*/

public class Door {

  /**
  * my exit LIST.
  */
  private ArrayList<Exit> myExits;

  /**
  * my exit instance variable.
  */
  private Exit myExit;

  /**
  * my trap instance variable.
  */
  private Trap myTrap;

  /**
  * my boolean instance variable.
  */
  private boolean isArchway = false;

  /**
  * my boolean instance variable.
  */
  private boolean isOpen = false;

  /**
  * my boolean instance variable.
  */
  private boolean isTrapped = false;

  /**
  * my boolean instance variable.
  */
  private ArrayList<Space> mySpaces = new ArrayList<Space>();

  /**
  * constructor with zero param.
  */
  public Door() {
    this.myExit = new Exit();
  }

  /**
  * constructor with one param.
  * @param theExit the exit variable
  */
  public Door(Exit theExit) {
    this.myExit = theExit;
  }

  /**
  * set if the door is trapped.
  * @param flag boolean variable
  * @param roll the random number
  */
  public void setTrapped(boolean flag, int... roll) {
    this.isTrapped = flag;
    if (this.isTrapped) {
      this.setTrapInfo(roll);
    }

    // true == trapped.  Trap must be rolled if no integer is given

  }

  /**
  * set the door to open.
  * @param flag boolean variable
  */
  public void setOpen(boolean flag) {
    if (this.isArchway()) {
      this.isOpen = true;
    } else {
      this.isOpen = flag;
    }

  }

  /**
  * generate the trap's information.
  * @param roll the random number
  */
  public void setTrapInfo(int... roll) {
    D20 die = new D20();
    this.myTrap = new Trap();
    if (roll == null) {
      myTrap.setDescription(die.roll());
    } else {
      myTrap.setDescription(roll);
    }
  }

  /**
  * set the door to archway.
  * @param flag boolean variable
  */
  public void setArchway(boolean flag) {
    this.isArchway = flag;
    if (this.isArchway) {
      this.isOpen = true;
    }
  }

  /**
  * check if the door is trapped or not.
  * @return boolean value
  */
  public boolean isTrapped() {
    return this.isTrapped;
  }

  /**
  * check if the door is open or not.
  * @return boolean value
  */
  public boolean isOpen() {
    return this.isOpen;
  }

  /**
  * check if the door is archway or not.
  * @return boolean value
  */
  public boolean isArchway() {
    return this.isArchway;
  }

  /**
  * Get the trap description.
  * @return the description
  */
  public String getTrapDescription() {
    if (this.isTrapped()) {
      return myTrap.getDescription();
    } else {
      return "The door is not trapped";
    }
  }

  /**
  * return the space list.
  * @return the array list of space
  */
  public ArrayList<Space> getSpaces() {
    //returns the two spaces that are connected by the door
    return mySpaces;
  }

  /**
  * identifies the two spaces with the door.
  * @param spaceOne check it out
  * @param spaceTwo check it out
  */
  public void setSpaces(Space spaceOne, Space spaceTwo) {
    //identifies the two spaces with the door
    // this method should also call the addDoor method from Space

    spaceOne.setDoor(this);
    spaceTwo.setDoor(this);

    mySpaces.add(spaceOne);
    mySpaces.add(spaceTwo);



  }

  /**
  * return the door's information.
  * @return the door's description
  */
  public String getDescription() {
    String temp;
    temp = "door description : ";
    if (mySpaces.size() != 0) {
      temp = temp.concat("this door is connected to [");
      for (int i = 0; i < mySpaces.size(); i++) {
        if (mySpaces.get(i) instanceof Passage) {
          temp = temp.concat(((Passage) mySpaces.get(i)).getDescription());
        } else {
          temp = temp.concat(((Chamber) mySpaces.get(i)).getDescription());
        }
      }
      temp = temp.concat("] ");
    }
    temp = temp.concat("The direction of the door is " + myExit.getDirection());
    temp = temp.concat(", it is located at the " + myExit.getLocation() + ". ");
    if (this.isArchway()) {
      temp = temp.concat("This door is a archway, it's open");
      if (this.isTrapped()) {
        temp = temp.concat(", and it is trapped. The trap is" + myTrap.getDescription() + ". ");
      } else {
        temp = temp.concat(", and it is not trapped.");
      }
    } else {
      if (isOpen()) {
        temp = temp.concat("It's open");
      } else {
        temp = temp.concat("It's not open");
      }

      if (this.isTrapped()) {
        temp = temp.concat(", and it is trapped. The trap is " + myTrap.getDescription() + ". ");
      } else {
        temp = temp.concat(", and it is not trapped. ");
      }
    }
    return temp;
  }

}
