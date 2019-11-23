package gui;

import jzheng.Level;
import jzheng.Chamber;
import jzheng.Passage;
import jzheng.Space;
import jzheng.Door;
import java.util.ArrayList;

public class Controller {
  private GuiDemo myGui;
  private Level myLevel;
  private static int NUM_HYDRAS = 5;

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
    return myChamber.getDoors().get(index).getDescription();
  }

  public String getDoorDescriptionPassage(Passage myPassage, int index) {
    return myPassage.getDoor(index).getDescription();
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
