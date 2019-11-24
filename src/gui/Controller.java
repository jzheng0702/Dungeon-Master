package gui;

import jzheng.Level;
import jzheng.Chamber;
import jzheng.Passage;
import jzheng.Space;
import jzheng.Door;
import jzheng.PassageSection;
import java.util.ArrayList;
import java.io.*;
import dnd.models.Monster;
import dnd.models.Treasure;
import dnd.exceptions.UnusualShapeException;

public class Controller implements java.io.Serializable {
  private Gui myGui;
  private Level myLevel;
  private Treasure tempTreasure;
  private Monster tempMonster;
  private int monsterLocation;
  private int treasureLocation;

  public Controller(Gui theGui){
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
    return temp;
  }

  public int getDoorSize(int index) {
    return this.getChamber(index).getDoors().size();
  }

  public int getPassageSize(int index) {
    return this.getPassage(index).getPassageSections().size();
  }

  public String getDoorDescriptionPassage(Passage myPassage, int index) {
    return myPassage.getDoor(index).getDescription();
  }

  public String addTempTreasure(int treasureNum) {
    tempTreasure = new Treasure();
    tempTreasure.chooseTreasure(treasureNum);
    return tempTreasure.getDescription();
  }

  public String addTempMonster(int monsterNum) {
    tempMonster = new Monster();
    tempMonster.setType(monsterNum);
    return tempMonster.getDescription();
  }

  public String deleteTempTreasure(int num,int treasureNum) {
    this.treasureLocation = treasureNum;
    Chamber myChamber = this.getChamber(num);
    return myChamber.getTreasureList().get(treasureNum - 1).getDescription();
  }

  public String deleteTempTreasurePassage(int num,int treasureNum) {
    this.treasureLocation = treasureNum;
    Passage myPassage = this.getPassage(num);
    Treasure temp = (Treasure)myPassage.getTreasures().get(treasureNum - 1);
    return temp.getDescription();
  }

  public String deleteTempMonster(int num,int monsterNum) {
    this.monsterLocation = monsterNum;
    Chamber myChamber = this.getChamber(num);
    return myChamber.getMonsters().get(monsterNum - 1).getDescription();
  }

  public String deleteTempMonsterPassage(int num,int monsterNum) {
    this.monsterLocation = monsterNum;
    Passage myPassage = this.getPassage(num);
    Monster temp = (Monster)myPassage.getMonsters().get(monsterNum - 1);
    return temp.getDescription();
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

  public ArrayList treasureListCurrent(int num) {
    ArrayList<String> treasureList = new ArrayList<>();
    Chamber myChamber = this.getChamber(num);
    int i;

    for (i = 0; i < myChamber.getTreasureList().size(); i++) {
      treasureList.add("Treasure " + (i + 1) + ": " + myChamber.getTreasureList().get(i).getDescription());
    }

    return treasureList;
  }

  public ArrayList treasureListCurrentPassage(int num) {
    ArrayList<String> treasureList = new ArrayList<>();
    Passage myPassage = this.getPassage(num);
    int i;

    for (i = 0; i < myPassage.getTreasures().size(); i++) {
      Treasure temp = (Treasure)myPassage.getTreasures().get(i);
      treasureList.add("Treasure " + (i + 1) + ": " + temp.getDescription());
    }

    return treasureList;
  }

  public ArrayList monsterListCurrent(int num) {
    ArrayList<String> monsterList = new ArrayList<>();
    Chamber myChamber = this.getChamber(num);
    int i;

    for (i = 0; i < myChamber.getMonsters().size(); i++) {
      monsterList.add("Monster " + (i + 1) + ": " + myChamber.getMonsters().get(i).getDescription());
    }

    return monsterList;
  }

  public ArrayList monsterListCurrentPassage(int num) {
    ArrayList<String> monsterList = new ArrayList<>();
    Passage myPassage = this.getPassage(num);
    int i;

    for (i = 0; i < myPassage.getMonsters().size(); i++) {
      Monster temp = (Monster)myPassage.getMonsters().get(i);
      monsterList.add("Monster " + (i + 1) + ": " + temp.getDescription());
    }

    return monsterList;
  }

  public ArrayList monsterList() {
    ArrayList<String> monsterList = new ArrayList<>();
    int i;
    int start = 1,end;
    Monster temp = new Monster();
    Monster after = new Monster();

    for (i = 1; i <= 100; i++) {
      temp.setType(i);
      if (i != 100) {
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
        monsterList.add("99-100: " + temp.getDescription());
      }
    }

    return monsterList;
  }

  public void addTreasure(int num) {
    this.getChamber(num).addTreasure(tempTreasure);
  }

  public void addTreasurePassage(int num) {
    int size = this.getPassage(num).getTreasureSize();

    if (size < 2) {
      if (size == 0) {
        this.getPassage(num).addTreasure(tempTreasure,0);
      } else {
        this.getPassage(num).addTreasure(tempTreasure,1);
      }
    } else {
      PassageSection temp = new PassageSection();
      this.getPassage(num).addPassageSection(temp);
      this.getPassage(num).addTreasure(tempTreasure,size);
    }
  }

  public void deleteTreasure(int num) {
    this.getChamber(num).deleteTreasure(treasureLocation - 1);
  }

  public void deleteTreasurePassage(int num) {
    this.getPassage(num).deleteTreasure(treasureLocation - 1);
  }

  public void addMonster(int num) {
    this.getChamber(num).addMonster(tempMonster);
  }

  public void addMonsterPassage(int num) {
    int size = this.getPassage(num).getMonsterSize();

    if (size < 2) {
      if (size == 0) {
        this.getPassage(num).addMonster(tempMonster,0);
      } else {
        this.getPassage(num).addMonster(tempMonster,1);
      }
    } else {
      PassageSection temp = new PassageSection();
      this.getPassage(num).addPassageSection(temp);
      this.getPassage(num).addMonster(tempMonster,size);
    }
  }


  public void deleteMonster(int num) {
    this.getChamber(num).deleteMonster(monsterLocation - 1);
  }

  public void deleteMonsterPassage(int num) {
    this.getPassage(num).deleteMonster(monsterLocation - 1);
  }


  public ArrayList<String> getDoorVar(int i){
    //ArrayList<Door> doors = myLevel.getDoors();
    ArrayList<String> nameList = new ArrayList<>();
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

  public int getChamberLength(int index) {
    Chamber myChamber = this.getChamber(index);
    return myChamber.getChamberShape().getLength();
  }

  public int getChamberWidth(int index) {
    Chamber myChamber = this.getChamber(index);
    return myChamber.getChamberShape().getWidth();
  }

  public int checkUnusualShape(int index) {
    Chamber myChamber = this.getChamber(index);
    try {
      myChamber.getChamberShape().getLength();
    } catch(UnusualShapeException e){
      return 1;
    }

    return 0;
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
    return String.join("\n");
  }

  public String saveFile(String path){
    try {
      FileOutputStream fileOut = new FileOutputStream(path + ".ser");
      ObjectOutputStream out = new ObjectOutputStream(fileOut);
      out.writeObject(myLevel);
      out.close();
      fileOut.close();
      return "Serialized data is saved in " + path + ".ser";
    } catch (IOException i) {
      i.printStackTrace();
      return "Error occurs";
    }
  }

  public void loadFile(String path){
    myLevel = null;
    try {
      FileInputStream fileIn = new FileInputStream(path + ".ser");
      ObjectInputStream in = new ObjectInputStream(fileIn);
      myLevel = (Level)in.readObject();
      in.close();
      fileIn.close();
      System.out.println("Serialized data is loaded in " + path + ".ser");
    } catch (IOException i) {
      i.printStackTrace();
      return;
    } catch (ClassNotFoundException c) {
      System.out.println("Level class not found");
      c.printStackTrace();
      return;
    }
  }

}
