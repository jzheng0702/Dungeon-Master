package jzheng;


//import dnd.models.Monster;
import java.util.ArrayList;
import java.util.HashMap;

public class A3 {
  protected A3() {
    throw new UnsupportedOperationException();
  }

  /**
  * main class for creating level.
  * @param args arguments
  **/
  public static void main(String[] args) {
    Level level = new Level();
    Chamber myChamber;
    Passage passage;
    int i;
    int j;

    /*Start the level*/
    level.levelFactory();







    /*System.out.println("==================Level Generation=================");
    System.out.println("====================Five Chambers==================");
    for (i = 0; i < level.getChambers().size(); i++) {
      myChamber = (Chamber)(level.getChambers()).get(i);
      System.out.println((i + 1) + ". " + myChamber.getDescription());
    }
    System.out.println("===================================================");*/

    System.out.println("=======================Doors=======================");
    for (i = 0; i < level.getTargetMap().size(); i++) {
      //System.out.println((i + 1) + ". " + level.getDoor(i).getDescription());
      System.out.println("The first space: " + level.getDoor(i).getSpaces().get(0).getDescription());
      System.out.println("The second space: " + level.getDoor(i).getSpaces().get(1).getDescription());
    }
    System.out.println("===================================================");

    /*System.out.println("=======================Passages====================");
    for (i = 0; i < level.getPassages().size(); i++) {
      passage = (Passage)(level.getPassages()).get(i);
      System.out.println((i + 1) + ". " + passage.getDescription());
    }
    System.out.println("===================================================");*/






  }



}
