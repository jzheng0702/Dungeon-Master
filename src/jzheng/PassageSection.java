package jzheng;

import dnd.models.Monster;
import dnd.models.Treasure;

/**
* @author     jzheng06@uoguelph.ca
* @version    1.3
*/

public class PassageSection implements java.io.Serializable{
  /**
  * the description of the passage section.
  */
  private String myDescription;

  /**
  * the door instance variable.
  */
  private Door myDoor;

  /**
  * the monster instance variable.
  */
  private Monster myMonster;

  /**
  * the treasure instance variable.
  */
  private Treasure myTreasure;


  /**
  * constructor with zero param.
  */
  public PassageSection() {
    //sets up the 10 foot section with default settings
    this.setDescription("10 foot section");

  }

  /**
  * constructor with one param.
  * @param description the description of the passage section
  */
  public PassageSection(String description) {
    //sets up a specific passage based on the values sent in from
    //modified table 1
    this.setDescription(description);
  }

  /**
  * return door.
  * @return door if there is one
  */
  public Door getDoor() {
    if (myDoor != null) {
      return myDoor;
    }
    return null;
  }

  /**
  * set a new door.
  * @param theDoor the new door
  */
  public void setDoor(Door theDoor) {
    this.myDoor = theDoor;
  }

  /**
  * return monster.
  * @return monster if there is one
  */
  public Monster getMonster() {
    //returns the monster that is in the passage section, if there is one
    if (myMonster != null) {
      return myMonster;
    }
    return null;
  }

  /**
  * return treasure.
  * @return treasure if there is one
  */
  public Treasure getTreasure() {
    if (myTreasure != null) {
      return myTreasure;
    }
    return null;
  }

  /**
  * add a new monster.
  * @param theMonster the new monster
  */
  public void addMonster(Monster theMonster) {
    // adds a monster
    this.myMonster = theMonster;
  }

  /**
  * remove a monster.
  */
  public void removeMonster() {
    this.myMonster = null;
  }

  /**
  * add a new treasures.
  * @param theTreasure the new treasure
  */
  public void addTreasure(Treasure theTreasure) {
    this.myTreasure = theTreasure;
  }

  /**
  * remove a treasure.
  */
  public void removeTreasure() {
    this.myTreasure = null;
  }

  /**
  * return description.
  * @return description
  */
  public String getDescription() {
    String temp;
    temp = myDescription;
    if (myMonster != null) {
      temp = temp.concat(",the monster is " + myMonster.getDescription());
    }
    if (myTreasure != null) {
      temp = temp.concat(",the treasure is " + myTreasure.getDescription());
    }
    if (myDoor != null) {
      temp = temp.concat(", this passage is connect to the door and here is the " + myDoor.getDescription());
    }
    return temp;
  }

  /**
  * set a new description.
  * @param description the description
  */
  public void setDescription(String description) {
    this.myDescription = description;
  }



}
