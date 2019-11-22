package jzheng;


import java.util.ArrayList;
import java.util.HashMap;

/**
* @author     jzheng06@uoguelph.ca
* @version    1.3
*/
public class Level {
  /**
  * my passages.
  */
  private ArrayList<Passage> myPassages = new ArrayList<Passage>();

  /**
  * my chambers.
  */
  private ArrayList<Chamber> myChambers = new ArrayList<Chamber>();

  /**
  * my chamber count.
  */
  private int chamberCount;

  /**
  * my hashmap for doors.
  */
  private HashMap<Door, ArrayList<Chamber>> doorMap = new HashMap<Door, ArrayList<Chamber>>(1);

  /**
  * constructor.
  */
  public Level() {

  }


  /**
  * Add Passage function.
  * @param myDoor change this door and return the new value of it
  * @return return the door
  */
  public Door addPassage(Door myDoor) {
    Passage newPassage = new Passage();

    for (int i = 0; i < doorMap.get(myDoor).size(); i++) {
      newPassage = new Passage();
      this.addPassageSections(newPassage);
      newPassage.setDoor(myDoor);
      myPassages.add(newPassage);

    }


    return myDoor;
  }

  /**
  * addPassageSection function.
  * @param passage the passage
  * @return the new passage
  */
  private Passage addPassageSections(Passage passage) {
    PassageSection mySection1 = new PassageSection();
    PassageSection mySection2 = new PassageSection();
    passage.addPassageSection(mySection1);
    passage.addPassageSection(mySection2);
    return passage;
  }

  /**
  * Add Chamber function.
  * @param myDoor change this door and return the new value of it
  * @return return the door
  */
  public Door addChamber(Door myDoor) {
    Chamber newChamber = new Chamber();
    Door newDoor;

    newChamber.setDoor(myDoor);
    this.addTarget(newChamber, myDoor);
    newDoor = myDoor;
    myChambers.add(newChamber);

    int numExits = getNumOfExits(newChamber);
    if (numExits <= 0) {
      return newDoor;
    } else {
      newDoor = assignDoors(myDoor, numExits, newChamber);
    }

    return newDoor;
  }

  /**
  * This function will return the total number of the Passages.
  * @return total number of the passages
  */
  public int numOfPassages() {
    return myPassages.size();
  }

  /**
  * This function will generate an entire level using A3's algorithm.
  */
  public void levelFactory() {
    int i = 0;
    int count = 0;
    Door myDoor = new Door();

    /*generate 5 stand alone chambers*/
    for (i = 0; i < 5; i++) {
      chamberCount = i + 1;
      myDoor = this.addChamber(myDoor);
    }

    this.checkNullTarget();
    this.createPassage();



  }

  /**
  * This function will check for null.
  */
  private void checkNullTarget() {
    ArrayList<Chamber> temp = new ArrayList<Chamber>(1);
    int i = 0;
    for (i = 0; i < doorMap.size(); i++) {
      if (doorMap.get(this.getDoor(i)) == null) {
        temp.add(myChambers.get(2));
        doorMap.replace(this.getDoor(i), doorMap.get(this.getDoor(i)), temp);
      }
    }
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
  * add one to the HashMap.
  * @param key thekey
  * @param value theValue
  */
  private void add(Door key, ArrayList<Chamber> value) {
    this.doorMap.put(key, value);
  }

  /**
  * get the number of exit of this chamber.
  * @param myChamber the chamber
  * @return number of Exit
  */
  private int getNumOfExits(Chamber myChamber) {
    return myChamber.getChamberShape().getNumExits() - myChamber.getDoors().size();
  }

  /**
  * add a target chamber to the door.
  * @param newChamber the new chamber
  * @param myDoor the door
  */
  private void addTarget(Chamber newChamber, Door myDoor) {
    ArrayList<Chamber> temp = new ArrayList<Chamber>();

    if (doorMap.containsKey(myDoor)) {
      temp = doorMap.get(myDoor);
      if (temp == null) {
        temp = new ArrayList<Chamber>(1);
      }
      temp.add(newChamber);
      doorMap.replace(myDoor, doorMap.get(myDoor), temp);
    } else {
      add(myDoor, null);
    }
  }

  /**
  * Create passage for each hashmap values.
  */
  private void createPassage() {
    Door myDoor;
    int i;

    for (i = 0; i < doorMap.size(); i++) {
      myDoor = this.addPassage(this.getDoor(i));
    }

  }

  /**
  * Assignment enough exits to each door.
  * @param myDoor myDoor
  * @param numExits the number that we need to assign doors
  * @param newChamber yea
  * @return return the new door
  */
  private Door assignDoors(Door myDoor, int numExits, Chamber newChamber) {
    Door newDoor = new Door();
    int count;
    int i;

    for (i = 0; i < numExits; i++) {
      count = 0;
      newDoor = new Door();
      while (doorMap.get(this.getDoor(count)) != null && count < doorMap.size() - 1) {
        count++;
      } //This loop will end when it find a door with no target chambers
      if (this.getDoor(count) == myDoor) {
        newDoor = new Door();
        myDoor = newDoor;
      } else {
        newDoor = this.getDoor(count);
        myDoor = newDoor;
      }
      newChamber.setDoor(newDoor);
      this.addTarget(newChamber, newDoor);
    }

    return newDoor;

  }

  /**
  * return the HashMap.
  * @return the HashMap
  */
  public HashMap getTargetMap() {
    return this.doorMap;
  }

  /**
  * return the Passage list.
  * @return the passages
  */
  public ArrayList getPassages() {
    return this.myPassages;
  }

  /**
  * return the Chamber list.
  * @return the chambers
  */
  public ArrayList getChambers() {
    return this.myChambers;
  }





}
