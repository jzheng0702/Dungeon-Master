package jzheng;

import dnd.models.ChamberContents;
import dnd.exceptions.UnusualShapeException;
import dnd.models.ChamberShape;
import dnd.models.Monster;
import dnd.models.Exit;
import dnd.models.Treasure;
import dnd.die.D20;
import java.util.ArrayList;

/**
* @author     jzheng06@uoguelph.ca
* @version    1.3
*/

public class Chamber extends Space implements java.io.Serializable {

  /**
  * determine the contents of the chamber.
  */
  private ChamberContents myContents;

  /**
  * determine the size of the chamberContents.
  */
  private ChamberShape mySize;

  /**
  * created door list.
  */
  private ArrayList<Door> myDoors = new ArrayList<Door>();

  /**
  * created treasure list.
  */
  private ArrayList<Treasure> myTreasures = new ArrayList<Treasure>();

  /**
  * created monster list.
  */
  private ArrayList<Monster> myMonsters = new ArrayList<Monster>();

  /**
  * created exit LIST.
  */
  private ArrayList<Exit> myExits;

  /**
  * my die.
  */
  private D20 myDie = new D20();


  /**
  * constructor with no param.
  */
  public Chamber() {
    this.myContents = new ChamberContents();
    this.myContents.chooseContents(myDie.roll());
    this.mySize = ChamberShape.selectChamberShape(myDie.roll());
    while (mySize.getNumExits() == 0) {
      mySize.setNumExits();
    }


  }

  /**
  * constructor with two param.
  * @param theShape the shape of the chamber
  * @param theContents the content of the chamber
  */
  public Chamber(ChamberShape theShape, ChamberContents theContents) {
    this.myContents = theContents;
    this.setShape(theShape);
    while (mySize.getNumExits() == 0) {
      mySize.setNumExits();
    }
  }

  /**
  * set the shape of the chamber.
  * @param theShape the shape of the chamber
  */
  public void setShape(ChamberShape theShape) {
    this.mySize = theShape;
  }

  /**
  * return all the doors.
  * @return the list of the doors
  */
  public ArrayList<Door> getDoors() {
    return myDoors;
  }

  /**
  * add a monster to the current LIST.
  * @param theMonster the new Monster
  */
  public void addMonster(Monster theMonster) {
    myMonsters.add(theMonster);
  }

  /**
  * delete a monster to the current LIST.
  * @param num the num
  */
  public void deleteMonster(int num) {
    myMonsters.remove(num);
  }

  /**
  * get the monster array list.
  * @return the list of Monsters
  */
  public ArrayList<Monster> getMonsters() {
    return myMonsters;
  }


  /**
  * add a treasure to the list.
  * @param theTreasure the new treasure
  */
  public void addTreasure(Treasure theTreasure) {
    myTreasures.add(theTreasure);

  }

  /**
  * delete a treasure to the current LIST.
  * @param num the num
  */
  public void deleteTreasure(int num) {
    myTreasures.remove(num);
  }

  /**
  * return the chamber shape.
  * @return the chamber shape
  */
  public ChamberShape getChamberShape() {
    return this.mySize;

  }

  /**
  * get the treasure list.
  * @return the array list of the treasure
  */
  public ArrayList<Treasure> getTreasureList() {
    return myTreasures;
  }


  /**
  * get the description of the entire chamber.
  * @return the description String
  */
  @Override
  public String getDescription() {
    int i;
    int area;
    String temp = "The chamber's shape is " + this.getChamberShape().getShape() + ". The chamber content is " + myContents.getDescription() + ". \n";
    try {
      temp = temp.concat("The chamber's size is " + this.getChamberShape().getLength() + " * " + this.getChamberShape().getWidth() + "\n");
    } catch (UnusualShapeException e) {
      area = this.getChamberShape().getArea();
      temp = temp.concat("The chamber is an unusual shape, the area is " + area);
    }

    if (myMonsters.size() != 0) {
      temp = temp.concat(" The monsters are ");
      for (i = 0; i < myMonsters.size(); i++) {
        temp = temp.concat("(" + (i + 1) + ")" + myMonsters.get(i).getDescription() + ".");
      }
      temp = temp.concat("\n");
    }




    if (myTreasures.size() != 0) {
      temp = temp.concat(" The treasures are ");
      for (i = 0; i < myTreasures.size(); i++) {
        temp = temp.concat("(" + (i + 1) + ")" + myTreasures.get(i).getDescription() + ".");
      }
      temp = temp.concat("\n");
    }

    if (myDoors.size() != 0) {
      temp = temp.concat(" This chamber connects to " + myDoors.size() + " doors. ");
    }

    return temp;

  }


  /**
  * set the door.
  * @param newDoor the new door
  */
  @Override
  public void setDoor(Door newDoor) {
    this.myDoors.add(newDoor);

  }


}
