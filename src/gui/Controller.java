package gui;

import jzheng.Level;
import jzheng.Chamber;
import jzheng.Passage;
import jzheng.Space;
import jzheng.Door;
import jzheng.PassageSection;
import java.util.ArrayList;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import dnd.models.Monster;
import dnd.models.Treasure;
import dnd.exceptions.UnusualShapeException;

/**
* @author     jzheng06@uoguelph.ca
* @version    1.3
*/
public class Controller implements java.io.Serializable {
  /**
  * my gui.
  */
  private Gui myGui;

  /**
  * my level variable.
  */
  private Level myLevel;

  /**
  * my temp treasure, save the treasure here.
  */
  private Treasure tempTreasure;

  /**
  * my temp monster, save the monster here.
  */
  private Monster tempMonster;

  /**
  * the current monster location.
  */
  private int monsterLocation;

  /**
  * The current treasure location.
  */
  private int treasureLocation;


  /**
  * the constructor.
  * @param theGui gui
  */
  public Controller(Gui theGui) {
    myGui = theGui;
    myLevel = new Level();
    myLevel.levelFactory();
  }


  /**
  * all the doors.
  * @param mySpace myspace
  * @return the arraylist
  */
  public ArrayList<String> getNameList(Space mySpace) {
    int i;
    ArrayList<String> nameList = new ArrayList<>();
    ArrayList<Door> doors = new ArrayList<Door>();

    if (mySpace instanceof Chamber) {
      Chamber temp = (Chamber) mySpace;
      doors = temp.getDoors();
    } else {
      Passage buffer = (Passage) mySpace;
      doors = buffer.getDoors();
    }

    for (i = 0; i < doors.size(); i++) {
      nameList.add("Door" + (i + 1));
    }
    return nameList;
  }

  /**
  * the doors description.
  * @param myChamber myspace
  * @param index the door index
  * @return the string
  */
  public String getDoorDescription(Chamber myChamber, int index) {
    String temp = myChamber.getDoors().get(index).getDescription();
    return temp;
  }

  /**
  * all the doors.
  * @param index the chamber's index
  * @return the door size
  */
  public int getDoorSize(int index) {
    return this.getChamber(index).getDoors().size();
  }

  /**
  * all the passages.
  * @param index the passage's index
  * @return the passage size
  */
  public int getPassageSize(int index) {
    return this.getPassage(index).getPassageSections().size();
  }

  /**
  * the doors description.
  * @param myPassage myspace
  * @param index the door index
  * @return the string
  */
  public String getDoorDescriptionPassage(Passage myPassage, int index) {
    return myPassage.getDoor(index).getDescription();
  }

  /**
  * add temp treasure.
  * @param treasureNum the treasure's num
  * @return the treasure's description
  */
  public String addTempTreasure(int treasureNum) {
    tempTreasure = new Treasure();
    tempTreasure.chooseTreasure(treasureNum);
    return tempTreasure.getDescription();
  }

  /**
  * add temp monster.
  * @param monsterNum the monster's num
  * @return the monster's description
  */
  public String addTempMonster(int monsterNum) {
    tempMonster = new Monster();
    tempMonster.setType(monsterNum);
    return tempMonster.getDescription();
  }

  /**
  * delete temp treasure.
  * @param treasureNum the treasure's num
  * @param num the chamber num
  * @return the treasure's description
  */
  public String deleteTempTreasure(int num, int treasureNum) {
    this.treasureLocation = treasureNum;
    Chamber myChamber = this.getChamber(num);
    return myChamber.getTreasureList().get(treasureNum - 1).getDescription();
  }

  /**
  * delete temp treasure.
  * @param treasureNum the treasure's num
  * @param num the passage num
  * @return the treasure's description
  */
  public String deleteTempTreasurePassage(int num, int treasureNum) {
    this.treasureLocation = treasureNum;
    Passage myPassage = this.getPassage(num);
    Treasure temp = (Treasure) myPassage.getTreasures().get(treasureNum - 1);
    return temp.getDescription();
  }

  /**
  * delete temp monster.
  * @param monsterNum the monster's num
  * @param num the chamber num
  * @return the monster's description
  */
  public String deleteTempMonster(int num, int monsterNum) {
    this.monsterLocation = monsterNum;
    Chamber myChamber = this.getChamber(num);
    return myChamber.getMonsters().get(monsterNum - 1).getDescription();
  }

  /**
  * delete temp monster.
  * @param monsterNum the monster's num
  * @param num the passage num
  * @return the monster's description
  */
  public String deleteTempMonsterPassage(int num, int monsterNum) {
    this.monsterLocation = monsterNum;
    Passage myPassage = this.getPassage(num);
    Monster temp = (Monster) myPassage.getMonsters().get(monsterNum - 1);
    return temp.getDescription();
  }


  /**
  * all of the treasures.
  * @return the treasure's list
  */
  public ArrayList treasureList() {
    ArrayList<String> treasureList = new ArrayList<>();
    int i;
    int start = 1;
    int end;
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

  /**
  * current list of the treasures.
  * @param num chamber's number
  * @return the treasure's list
  */
  public ArrayList treasureListCurrent(int num) {
    ArrayList<String> treasureList = new ArrayList<>();
    Chamber myChamber = this.getChamber(num);
    int i;

    for (i = 0; i < myChamber.getTreasureList().size(); i++) {
      treasureList.add("Treasure " + (i + 1) + ": " + myChamber.getTreasureList().get(i).getDescription());
    }

    return treasureList;
  }

  /**
  * current list of the treasures.
  * @param num passage's number
  * @return the treasure's list
  */
  public ArrayList treasureListCurrentPassage(int num) {
    ArrayList<String> treasureList = new ArrayList<>();
    Passage myPassage = this.getPassage(num);
    int i;

    for (i = 0; i < myPassage.getTreasures().size(); i++) {
      Treasure temp = (Treasure) myPassage.getTreasures().get(i);
      treasureList.add("Treasure " + (i + 1) + ": " + temp.getDescription());
    }

    return treasureList;
  }

  /**
  * current list of the monsters.
  * @param num chamber's number
  * @return the monster's list
  */
  public ArrayList monsterListCurrent(int num) {
    ArrayList<String> monsterList = new ArrayList<>();
    Chamber myChamber = this.getChamber(num);
    int i;

    for (i = 0; i < myChamber.getMonsters().size(); i++) {
      monsterList.add("Monster " + (i + 1) + ": " + myChamber.getMonsters().get(i).getDescription());
    }

    return monsterList;
  }

  /**
  * current list of the monsters.
  * @param num passage's number
  * @return the monster's list
  */
  public ArrayList monsterListCurrentPassage(int num) {
    ArrayList<String> monsterList = new ArrayList<>();
    Passage myPassage = this.getPassage(num);
    int i;

    for (i = 0; i < myPassage.getMonsters().size(); i++) {
      Monster temp = (Monster) myPassage.getMonsters().get(i);
      monsterList.add("Monster " + (i + 1) + ": " + temp.getDescription());
    }

    return monsterList;
  }

  /**
  * all of the monsters.
  * @return the monster's list
  */
  public ArrayList monsterList() {
    ArrayList<String> monsterList = new ArrayList<>();
    int i;
    int start = 1;
    int end;
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

  /**
  * add treasures.
  * @param num chamber's number
  */
  public void addTreasure(int num) {
    this.getChamber(num).addTreasure(tempTreasure);
  }

  /**
  * add treasures.
  * @param num passage's number
  */
  public void addTreasurePassage(int num) {
    int size = this.getPassage(num).getTreasureSize();

    if (size < 2) {
      if (size == 0) {
        this.getPassage(num).addTreasure(tempTreasure, 0);
      } else {
        this.getPassage(num).addTreasure(tempTreasure, 1);
      }
    } else {
      PassageSection temp = new PassageSection();
      this.getPassage(num).addPassageSection(temp);
      this.getPassage(num).addTreasure(tempTreasure, size);
    }
  }

  /**
  * delete treasures.
  * @param num chamber's number
  */
  public void deleteTreasure(int num) {
    this.getChamber(num).deleteTreasure(treasureLocation - 1);
  }

  /**
  * delete treasures.
  * @param num passage's number
  */
  public void deleteTreasurePassage(int num) {
    this.getPassage(num).deleteTreasure(treasureLocation - 1);
  }

  /**
  * add monsters.
  * @param num chamber's number
  */
  public void addMonster(int num) {
    this.getChamber(num).addMonster(tempMonster);
  }

  /**
  * add monsters.
  * @param num passage's number
  */
  public void addMonsterPassage(int num) {
    int size = this.getPassage(num).getMonsterSize();

    if (size < 2) {
      if (size == 0) {
        this.getPassage(num).addMonster(tempMonster, 0);
      } else {
        this.getPassage(num).addMonster(tempMonster, 1);
      }
    } else {
      PassageSection temp = new PassageSection();
      this.getPassage(num).addPassageSection(temp);
      this.getPassage(num).addMonster(tempMonster, size);
    }
  }


  /**
  * delete monsters.
  * @param num chamber's number
  */
  public void deleteMonster(int num) {
    this.getChamber(num).deleteMonster(monsterLocation - 1);
  }

  /**
  * delete monsters.
  * @param num passage's number
  */
  public void deleteMonsterPassage(int num) {
    this.getPassage(num).deleteMonster(monsterLocation - 1);
  }


  /**
  * the list of chamber and passages.
  * @return the list
  */
  public ArrayList<String> getChamberAndPassageList() {
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

  /**
  * get chamber's description.
  * @param index chamber's number
  * @return the description
  */
  public String getChamberDescription(int index) {
    Chamber myChamber;
    myChamber = (Chamber) (myLevel.getChambers()).get(index);
    return myChamber.getDescription();
  }

  /**
  * get chamber.
  * @param index chamber's number
  * @return the chamber
  */
  public Chamber getChamber(int index) {
    Chamber myChamber;
    myChamber = (Chamber) (myLevel.getChambers()).get(index);
    return myChamber;
  }

  /**
  * get chamber length.
  * @param index chamber's number
  * @return the chamber's length
  */
  public int getChamberLength(int index) {
    Chamber myChamber = this.getChamber(index);
    return myChamber.getChamberShape().getLength();
  }

  /**
  * get chamber width.
  * @param index chamber's number
  * @return the chamber's width
  */
  public int getChamberWidth(int index) {
    Chamber myChamber = this.getChamber(index);
    return myChamber.getChamberShape().getWidth();
  }

  /**
  * check the unusual shape.
  * @param index chamber's number
  * @return if it is unusual, then return 1, otherwise 0
  */
  public int checkUnusualShape(int index) {
    Chamber myChamber = this.getChamber(index);
    try {
      myChamber.getChamberShape().getLength();
    } catch (UnusualShapeException e) {
      return 1;
    }

    return 0;
  }

  /**
  * get passage's description.
  * @param index passage's number
  * @return the passage
  */
  public String getPassageDescription(int index) {
    Passage myPassage;
    myPassage = (Passage) (myLevel.getPassages()).get(index);
    return myPassage.getDescription();
  }

  /**
  * get passage.
  * @param index passage's number
  * @return the passage
  */
  public Passage getPassage(int index) {
    Passage myPassage;
    myPassage = (Passage) (myLevel.getPassages()).get(index);
    return myPassage;
  }


  /**
  * save the file.
  * @param path thepath
  * @return the message
  */
  public String saveFile(String path) {
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

  /**
  * load the file.
  * @param path thepath
  */
  public void loadFile(String path) {
    myLevel = null;
    try {
      FileInputStream fileIn = new FileInputStream(path + ".ser");
      ObjectInputStream in = new ObjectInputStream(fileIn);
      myLevel = (Level) in.readObject();
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
