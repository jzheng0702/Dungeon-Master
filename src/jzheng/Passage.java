package jzheng;

import dnd.models.Monster;
import dnd.models.Treasure;
import java.util.ArrayList;
import java.util.HashMap;


/**
* @author     jzheng06@uoguelph.ca
* @version    1.3
*/

public class Passage extends Space {
  /**
  * my arraylist instance variable, record all the passage sections.
  */
  private ArrayList<PassageSection> thePassageSections = new ArrayList<PassageSection>(1);

  /**
  * my hashmap instance variable.
  */
  private HashMap<Door, PassageSection> doorMap = new HashMap<Door, PassageSection>(1);

  /**
  * my arraylist instance variable, record all the doors.
  */
  private ArrayList<Door> myDoors = new ArrayList<Door>(1);

  /**
  * my arraylist instance variable, record all the monsters.
  */
  private ArrayList<Monster> myMonsters = new ArrayList<Monster>(1);

  /**
  * number of monsters
  */
  private int numOfMonster;

  /**
  * number of treasures
  */
  private int numOfTreasure;

  /**
  * all the doors info.
  * @return ArrayList return all the doors that connect to the passage
  */
  public ArrayList getDoors() {
    for (Door temp : doorMap.keySet()) {
      myDoors.add(temp);
    }
    return myDoors;
  }

  /**
  * all the passgae section info.
  * @return ArrayList return all the passage sections that connect to the passage
  */
  public ArrayList getPassageSections() {
    return thePassageSections;
  }

  /**
  * a single door which is in section 'i'.
  * @return the door
  * @param i the index
  */
  public Door getDoor(int i) {
    //returns the door in section 'i'. If there is no door, returns null
    int count = 0;

    for (Door temp : doorMap.keySet()) {
      if (count == i) {
        return temp;
      }
      count++;
    }
    return null;
  }

  /**
  * add a monster to section 'i' of the passage.
  * @param theMonster the new monster
  * @param i the index
  */
  public void addMonster(Monster theMonster, int i) {
    thePassageSections.get(i).addMonster(theMonster);
    numOfMonster++;

  }

  /**
  * remove a monster in section 'i' of the passage.
  * @param i the index
  */
  public void deleteMonster(int i) {
    thePassageSections.get(i).removeMonster();
    numOfMonster--;
  }

  /**
  * add a treasure to section 'i' of the passage.
  * @param theTreasure the new monster
  * @param i the index
  */
  public void addTreasure(Treasure theTreasure, int i) {
    thePassageSections.get(i).addTreasure(theTreasure);
    numOfTreasure++;

  }

  /**
  * remove a treasure in section 'i' of the passage.
  * @param i the index
  */
  public void deleteTreasure(int i) {
    thePassageSections.get(i).removeTreasure();
    numOfTreasure--;
  }

  /**
  * returns Monster door in section 'i'. If there is no Monster, returns null.
  * @return Monster the monster
  * @param i the index
  */
  public Monster getMonster(int i) {
    if (thePassageSections.get(i).getMonster() != null) {
      return thePassageSections.get(i).getMonster();
    }

    return null;
  }

  /**
  * returns Monster array list's size
  * @return monster array size
  */
  public int getMonsterSize() {
    return numOfMonster;
  }

  /**
  * returns treasure array list's size
  * @return treasure array size
  */
  public int getTreasureSize() {
    return numOfTreasure;
  }


  /**
  * add a new passage section to the list.
  * @param toAdd the new passage section
  */
  public void addPassageSection(PassageSection toAdd) {
    this.thePassageSections.add(toAdd);
  }

  /**
  * remove the passage in section i.
  * @param i the index
  */
  public void clearPassageSection(int i) {
    this.thePassageSections.remove(i);
  }


  /**
  * set the door.
  * @param newDoor the new door
  */
  @Override
  public void setDoor(Door newDoor) {
    if (thePassageSections.size() == 0) {
      this.addPassageSection(new PassageSection());
    }
    doorMap.put(newDoor, thePassageSections.get(thePassageSections.size() - 1));
  }

  /**
  * return the description of the passage.
  * @return the description
  */
  @Override
  public String getDescription() {
    String temp;
    int i = 0;
    temp = "a passage with " + thePassageSections.size() + " passage sections.They are ";
    for (i = 0; i < thePassageSections.size(); i++) {
      temp = temp.concat((i + 1) + ". " + thePassageSections.get(i).getDescription() + ". ");
    }

    if (myDoors.size() != 0) {
      temp = temp.concat(" This passage connects to " + myDoors.size() + " doors. ");
    }

    return temp;
  }

}
