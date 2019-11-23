package gui;

import jzheng.Level;
import jzheng.Chamber;
import jzheng.Passage;
import jzheng.Space;
import jzheng.Door;
import jzheng.PassageSection;
import java.util.ArrayList;
import dnd.models.Monster;
import dnd.models.Treasure;
import dnd.die.D20;
import dnd.die.Percentile;

public class Controller {
  private GuiDemo myGui;
  private Level myLevel;
  private Treasure tempTreasure;
  private Monster tempMonster;

  public Controller(GuiDemo theGui){
    myGui = theGui;
    myLevel = new Level();
    myLevel.levelFactory();
  }


  public ArrayList<String> getNameList(Space mySpace){
    int i;
    ArrayList<String> nameList = new ArrayList<>();
    ArrayList<Door> doors = new ArrayList<Door>();



    if (mySpace instanceof Chamber) {
      Chamber temp = (Chamber)mySpace;
      doors = temp.getDoors();
    } else {
      Passage buffer = (Passage)mySpace;
      doors = buffer.getDoors();
    }



    for (i = 0; i < doors.size(); i++) {
      nameList.add("Door" + (i + 1));
    }
    return nameList;
  }

  public String getDoorDescription(Chamber myChamber, int index) {
    String temp = myChamber.getDoors().get(index).getDescription();
    temp = temp.concat("\nLinked to Chamber and Passage, need to fix");
    return temp;
  }

  public String getDoorDescriptionPassage(Passage myPassage, int index) {
    return myPassage.getDoor(index).getDescription();
  }

  public String addTempTreasure(int num) {
    Percentile myDie = new Percentile();
    tempTreasure = new Treasure();
    tempTreasure.chooseTreasure(myDie.roll());
    return tempTreasure.getDescription();
  }

  public ArrayList treasureList() {
    ArrayList<String> treasureList = new ArrayList<>();
    int i;
    int start = 1,end;
    Treasure temp = new Treasure();
    Treasure after = new Treasure();

    for (i = 1; i <= 98; i++) {
      temp.chooseTreasure(i);
      if (i != 98) {
        after.chooseTreasure(i + 1);
        if (!(temp.getDescription().equals(after.getDescription()))) {
          end = i;
          treasureList.add(start + "-" + end + ": " + temp.getDescription());
          start = i + 1;
        }
      } else {
        treasureList.add("98-00: " + temp.getDescription());
      }
    }

    return treasureList;
  }

  public ArrayList monsterList() {
    ArrayList<String> monsterList = new ArrayList<>();
    int i;
    int start = 1,end;
    Monster temp = new Monster();
    Monster after = new Monster();

    for (i = 1; i <= 20; i++) {
      temp.setType(i);
      if (i != 20) {
        after.setType(i + 1);
        if (!(temp.getDescription().equals(after.getDescription()))) {
          end = i;
          if (start == end) {
            monsterList.add(end + ": " + temp.getDescription());
          } else {
            monsterList.add(start + "-" + end + ": " + temp.getDescription());
          }
          start = i + 1;
        }
      } else {
        monsterList.add("20: " + temp.getDescription());
      }
    }

    return monsterList;
  }

  public void addTreasure(int num) {
    this.getChamber(num).addTreasure(tempTreasure);
  }

  public void addMonster(int num) {
    D20 myDie = new D20();
    Monster newMonster = new Monster();
    newMonster.setType(myDie.roll());
    this.getChamber(num).addMonster(newMonster);
  }

  public void addMonsterPassage(int num) {
    D20 myDie = new D20();
    Monster newMonster = new Monster();
    int size;
    newMonster.setType(myDie.roll());
    size = this.getPassage(num).getMonsterSize();
    if (size != 0) {
      this.getPassage(num).addMonster(newMonster,1);
    } else {
      this.getPassage(num).addMonster(newMonster,0);
    }
  }

  public String DeleteMonster(int num) {
    if (this.getChamber(num).getMonsters().size() == 0) {
      return "No Monster";
    } else {
      this.getChamber(num).deleteMonster();
      return "Delete";
    }

  }

  public String DeleteMonsterPassage(int num) {
    int size = this.getPassage(num).getMonsterSize();
    if (size == 0) {
      return "No Monster";
    } else if (size == 1){
      this.getPassage(num).deleteMonster(0);
      return "Delete";
    } else {
      this.getPassage(num).deleteMonster(1);
      return "Delete";
    }
  }


  public ArrayList<String> getDoorVar(){
    int i;
    int size = myLevel.getTargetMap().size();
    //ArrayList<Door> doors = myLevel.getDoors();
    ArrayList<String> nameList = new ArrayList<>();
    for (i = 0; i < size; i++) {
      ArrayList<Space> spaces = myLevel.getDoor(i).getSpaces();
      if (spaces.get(0) instanceof Chamber) {
        nameList.add("Chamber");
      } else {
        nameList.add("Passage");
      }

      if (spaces.get(1) instanceof Chamber) {
        nameList.add("Chamber");
      } else {
        nameList.add("Passage");
      }
    }

    return nameList;
  }

  public ArrayList<String> getChamberAndPassageList(){
    int i;
    ArrayList<Chamber> chambers = myLevel.getChambers();
    ArrayList<Passage> passages = myLevel.getPassages();
    ArrayList<String> nameList = new ArrayList<>();
    for (i = 0; i < chambers.size(); i++) {
      nameList.add("Chamber" + (i + 1));
    }

    for (i = 0; i < passages.size(); i++) {
      nameList.add("Passage" + (i + 1));
    }

    return nameList;
  }

  public String getChamberDescription(int index){
    Chamber myChamber;
    myChamber = (Chamber)(myLevel.getChambers()).get(index);
    return myChamber.getDescription();
  }

  public Chamber getChamber(int index){
    Chamber myChamber;
    myChamber = (Chamber)(myLevel.getChambers()).get(index);
    return myChamber;
  }

  public String getPassageDescription(int index){
    Passage myPassage;
    myPassage = (Passage)(myLevel.getPassages()).get(index);
    return myPassage.getDescription();
  }

  public Passage getPassage(int index){
    Passage myPassage;
    myPassage = (Passage)(myLevel.getPassages()).get(index);
    return myPassage;
  }

  public ArrayList<String> getDescriptionList(){
    ArrayList<Chamber> chambers = myLevel.getChambers();
    ArrayList<String> chamberList = new ArrayList<>();
    for(Chamber c: chambers){
      chamberList.add(c.getDescription());
    }
    return chamberList;
  }

  public void reactToButton(){
    System.out.println("Thanks for clicking!");
  }

  public String getNewDescription(){
    //return "this would normally be a description pulled from the model of the Dungeon level.";
    return String.join("\n", getDoorVar());
  }

}
