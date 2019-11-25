package gui;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

/**
* @author     jzheng06@uoguelph.ca
* @version    1.3
*/
public class ChamberView extends GridPane implements java.io.Serializable {
  /**
  * The floor.
  */
  private String floor;

  /**
  * The treasure.
  */
  private String treasure;

  /**
  * The monster.
  */
  private String monster;

  /**
  * open door image.
  */
  private String openDoor;

  /**
  * Close door image.
  */
  private String closeDoor;

  /**
  * Archway image.
  */
  private String archway;

  /**
  * length .
  */
  private int length;

  /**
  * Width.
  */
  private int width;

  /**
  * tiles.
  */
  private Node[] tiles;



  /**
  * the constructor.
  * @param len the length
  * @param wid the width
  */
  public ChamberView(int len, int wid) {
    floor = "/res/floor.png";
    treasure = "/res/tres.png";
    monster = "/res/monster.png";
    openDoor = "/res/openDoor.png";
    closeDoor = "/res/closeDoor.png";
    archway = "/res/archway.png";
    length = len;
    width = wid; //user these values to decide the size of the view and how many tiles

    tiles = this.makeTiles(length * width);
    this.addTiles();


  }

  /**
  * when adding a new chamber.
  * @param len the length
  * @param wid the width
  */
  public void adding(int len, int wid) {
    this.length = len;
    this.width = wid;

    this.getChildren().clear();
    tiles = this.makeTiles(length * width);
    this.addTiles();
  }

  /**
  * when adding a new passage.
  * @param size the size
  */
  public void addingPassage(int size) {
    this.getChildren().clear();
    tiles = this.makeTiles(size);
    this.addPassageTiles(size);
  }

  /**
  * when adding a new treasure.
  */
  public void addTreasure() {
    int i;
    int j;
    int count = 0;
    int size = length * width;
    tiles[3] = this.floorFactory(treasure);
    add(tiles[3], 0, 1, 1, 1);

  }

  /**
  * when adding a new monster.
  */
  public void addMonster() {
    int i;
    int j;
    int count = 0;
    int size = length * width;
    add(floorFactory(monster), 0, 2, 1, 1);
  }

  /**
  * when adding a new door.
  * @param num the num
  */
  public void addDoors(int num) {
    int i;
    int j;
    int count = 0;
    int size = length * width;
    for (i = 0; i < length; i++) {
      for (j = 0; j < length; j++) {
        if (count == num) {
          break;
        } else {
          add(floorFactory(openDoor), i, j, 1, 1);
        }
        count++;
      }
    }

  }

  /**
  * when adding a new tile.
  */
  private void addTiles() {
    int i;
    int j;
    int count = 0;
    int size = length * width;


    for (i = 0; i < length; i++) {
      for (j = 0; j < width; j++) {
        if (count == size) {
          break;
        } else {
          add(tiles[count], i, j, 1, 1);
          count++;
        }
      }
    }
  }

  /**
  * when adding a new passage tile.
  * @param size the size of the passage
  */
  private void addPassageTiles(int size) {
    int i;
    int j;
    int count = 0;


    for (i = 0; i < size; i++) {
      if (count == size) {
        break;
      } else {
        add(tiles[count], 0, i, 1, 1);
        count++;
      }
    }
  }


  /**
  * make tiles.
  * @param size the size
  * @return the node array
  */
  private Node[] makeTiles(int size) {  //should have a parameter and a loop
    int i;

    Node[] toReturn = new Node[size];
    for (i = 0; i < size; i++) {
      toReturn[i] = floorFactory(floor);
    }
    return toReturn;
  }


  /**
  * the floor factory.
  * @param img the image
  * @return the node
  */
  public Node floorFactory(String img) {
    Image floorImg = new Image(getClass().getResourceAsStream(img));
    Label toReturn = new Label();
    ImageView imageView = new ImageView(floorImg);
    imageView.setFitWidth(50);
    imageView.setFitHeight(50);
    toReturn.setGraphic(imageView);
    return toReturn;
  }


}
