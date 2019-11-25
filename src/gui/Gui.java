package gui;
import javafx.collections.FXCollections;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.TextField;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ToolBar;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.GridPane;
import javafx.stage.Popup;
import javafx.stage.Stage;


/**
* @author     jzheng06@uoguelph.ca
* @version    1.3
* @param <toReturn> return
*/
public class Gui<toReturn> extends Application implements java.io.Serializable {

  /**
  * The controller.
  */
  private Controller theController;

  /**
  * the root element.
  */
  private BorderPane root;

  /**
  * the pop up pane.
  */
  private Popup descriptionPane;

  /**
  * The stage that is passed in on initialization.
  */
  private Stage primaryStage;

  /**
  * The first output area.
  */
  private TextArea output;

  /**
  * The door description area.
  */
  private TextArea doorDescription;

  /**
  * The second output area.
  */
  private TextField output2;

  /**
  * The input area.
  */
  private TextField input;

  /**
  * current space .
  */
  private String currentSpace;

  /**
  * my boxes contains the chamber and passage list.
  */
  private ComboBox<String> boxes = new ComboBox<String>();

  /**
  * user choice.
  */
  private int userChoice;

  /**
  * current type.
  */
  private int currentType;

  /**
  * current status.
  */
  private int currentStatus;

  /**
  * room.
  */
  private GridPane room;

  /*a call to start replaces a call to the constructor for a JavaFX GUI*/
  @Override
  public void start(Stage assignedStage) {
    /*Initializing instance variables */
    theController = new Controller(this);
    primaryStage = assignedStage;
    /*Border Panes have  top, left, right, center and bottom sections */
    root = setUpRoot();
    descriptionPane = createPopUp(200, 300, "Example Description of something");
    Scene scene = new Scene(root, 1000, 1000);
    scene.getStylesheets().add(getClass().getResource("/res/stylesheet.css").toExternalForm());
    primaryStage.setTitle("Dungeon");
    primaryStage.setScene(scene);
    primaryStage.show();

  }
  /**
  * when load file, start a new stage.
  * @param assignedStage the stage
  */
  public void startLoad(Stage assignedStage) {
    /*Initializing instance variables */
    primaryStage = assignedStage;
    /*Border Panes have  top, left, right, center and bottom sections */
    root = setUpRoot();
    descriptionPane = createPopUp(200, 300, "Example Description of something");
    Scene scene = new Scene(root, 1000, 1000);
    scene.getStylesheets().add(getClass().getResource("/res/stylesheet.css").toExternalForm());
    primaryStage.setTitle("Dungeon");
    primaryStage.setScene(scene);
    primaryStage.show();

  }
  /**
  * the root set up.
  * @return borderpane
  */
  private BorderPane setUpRoot() {
    BorderPane temp = new BorderPane();
    //new Label("All of the variables")
    temp.setTop(myTop());
    ObservableList<String> varList = FXCollections.observableArrayList(theController.getChamberAndPassageList());
    temp.setLeft(createListView(varList));
    temp.setRight(setRight());
    temp.setCenter(centreArea());

    temp.setBottom(myBottom());
    return temp;
  }

  /**
  * when click on edit.
  * @param myButton the button
  * @return a button
  */
  private Button editHandler(Button myButton) {
    myButton.setOnAction(e -> {
      descriptionPane = createPopUp(500, 200, "");
      VBox editChoice = new VBox();
      Button addT = createButton("Add Treasure");
      addT = clickAddT(addT);
      Button deleteT = createButton("Delete Treasure");
      deleteT = clickDeleteT(deleteT);
      Button addM = createButton("Add Monster");
      addM = clickAddM(addM);
      Button deleteM = createButton("Delete Monster");
      deleteM = clickDeleteM(deleteM);
      editChoice.getChildren().addAll(addT, deleteT, addM, deleteM);
      descriptionPane.getContent().add(editChoice);
      descriptionPane.show(primaryStage);
    });

    return myButton;
  }


  /**
  * my bottom set up.
  * @return a horizontal box
  */
  private HBox myBottom() {
    Button editButton = createButton("Edit");
    editButton = editHandler(editButton);

    Button close = createButton("Close popup");
    close = this.closeHandler(close);

    HBox layout = new HBox(10);
    layout.getChildren().addAll(editButton, close);
    return layout;
  }

  /**
  * when click on close.
  * @param myButton the button
  * @return a button
  */
  private Button closeHandler(Button myButton) {
    myButton.setOnAction(e -> {
      descriptionPane.hide();
    });

    return myButton;
  }


  /**
  * my top set up.
  * @return a tool bar
  */
  private ToolBar myTop() {
    ToolBar myToolBar = new ToolBar();

    MenuItem saveFile = new MenuItem("Save File");
    saveFile = saveFileHandler(saveFile);

    MenuItem loadFile = new MenuItem("Load File");
    loadFile = loadFileHandler(loadFile);

    MenuButton file = new MenuButton("File", null, saveFile, loadFile);
    myToolBar.getStyleClass().add("header");

    myToolBar.getItems().add(file);
    return myToolBar;
  }

  /**
  * when click on a menu item.
  * @param saveFile the item
  * @return a menu item
  */
  private MenuItem saveFileHandler(MenuItem saveFile) {
    saveFile.setOnAction(event -> {
      output.setText("Save file!\nPlease enter the full path of the file(including the name of the file) in the input box! Click submit when you're done\nExample: /file/a4/fileName");
      input.setEditable(true);
      currentStatus = 3;
    });

    return saveFile;
  }

  /**
  * when click on a load file.
  * @param loadFile the item
  * @return a menu item
  */
  private MenuItem loadFileHandler(MenuItem loadFile) {
    loadFile.setOnAction(event -> {
      output.setText("Load File!\nPlease enter the full path of the file(includeing the name of the file) in the input box! Click submit when you're done\nExample: /file/a4/fileName");
      input.setEditable(true);
      currentStatus = 4;
    });

    return loadFile;
  }

  /**
  * my centre set up.
  * @return a veritcal box
  */
  private VBox centreArea() {
    room = new ChamberView(0, 0);

    output = new TextArea();
    output.setEditable(false);

    output2 = new TextField();
    output2.setEditable(false);

    input = new TextField();
    input.setEditable(false);

    Button submit = createButton("Submit");
    submit = getInput(submit);

    Button confirm = createButton("Confirm");
    confirm = confirmButton(confirm);

    VBox layout = new VBox(10);
    layout.getChildren().addAll(room, output, input, submit, output2, confirm);
    layout.getStyleClass().add("layout");
    return layout;
  }

  /**
  * when click on a submit.
  * @param myButton the submit button
  * @return submit button
  */
  private Button getInput(Button myButton) {
    myButton.setOnAction(event -> {
      String choice;
      String treasureInfo;
      String monsterInfo;
      if (input.getText() != null) {
        choice = input.getText();
        if (currentStatus == 1) {
          this.addStatus(choice);
        } else if (currentStatus == 2) {
          this.deleteStatus(choice);
        } else if (currentStatus == 3) {
          output2.setText(theController.saveFile(choice));
        } else if (currentStatus == 4) {
          this.load(choice);
        }
      } else {
        System.out.println("Nothing in here");
      }
    });

    return myButton;
  }

  /**
  * when the status is add.
  * @param choice the user choice
  */
  private void addStatus(String choice) {
    userChoice = this.parsemyInt(choice);
    if (currentType == 1) {
      this.treasureAdd();
    } else {
      this.monsterAdd();
    }
  }

  /**
  * add a monster.
  */
  private void monsterAdd() {
    String monsterInfo = theController.addTempMonster(userChoice);
    output2.setText("You have choose monster " + userChoice + " It is: " + monsterInfo + ", please click the confirm button to save changes");
  }

  /**
  * add a treasure.
  */
  private void treasureAdd() {
    String treasureInfo = theController.addTempTreasure(userChoice);
    output2.setText("You have choose treasure " + userChoice + " It is: " + treasureInfo + ", please click the confirm button to save changes");
  }

  /**
  * when the status is delete.
  * @param choice the user choice
  */
  private void deleteStatus(String choice) {
    userChoice = this.parsemyInt(choice);
    if (currentType == 1) {
      this.treasureDelete();
    } else {
      this.monsterDelete();
    }
  }

  /**
  * delete a monster.
  */
  private void monsterDelete() {
    String monsterInfo;
    if (this.chamberOrPassage(currentSpace) == 1) {
      monsterInfo = theController.deleteTempMonster(getIndex(currentSpace), userChoice);
    } else {
      monsterInfo = theController.deleteTempMonsterPassage(getIndex(currentSpace), userChoice);
    }
    output2.setText("You have choose monster " + userChoice + " It is: " + monsterInfo + ", please click the confirm button to save changes");
  }

  /**
  * delete a treasure.
  */
  private void treasureDelete() {
    String treasureInfo;
    if (this.chamberOrPassage(currentSpace) == 1) {
      treasureInfo = theController.deleteTempTreasure(getIndex(currentSpace), userChoice);
    } else {
      treasureInfo = theController.deleteTempTreasurePassage(getIndex(currentSpace), userChoice);
    }
    output2.setText("You have choose treasure " + userChoice + " It is: " + treasureInfo + ", please click the confirm button to save changes");
  }

  /**
  * parse Integer.
  * @param value the string values
  * @return return the int value
  */
  private int parsemyInt(String value) {
    return Integer.parseInt(String.valueOf(value));
  }

  /**
  * load the file.
  * @param choice the user choice
  */
  private void load(String choice) {
    theController.loadFile(choice);
    this.startLoad(primaryStage);
  }

  /**
  * when click on a add treasure.
  * @param myButton the button
  * @return return the button
  */
  private Button clickAddT(Button myButton) {
    myButton.setOnAction(event -> {
      output.setText(this.treasureList());
      input.setEditable(true);
      currentType = 1;
      currentStatus = 1;
    });

    return myButton;
  }


  /**
  * the treasure list info.
  * @return return the information string
  */
  private String treasureList() {
    int i;
    String treasureInfo;
    String treasureListInfo = "Treasure List\n";
    ObservableList<String> treasureList  = FXCollections.observableArrayList(theController.treasureList());
    for (i = 0; i < treasureList.size(); i++) {
      int count = i;
      treasureListInfo = treasureListInfo.concat(treasureList.get(count) + "\n");
    }
    treasureListInfo = treasureListInfo.concat("Please choose one number from the list, enter it in the input box.");
    return treasureListInfo;
  }

  /**
  * when click on a delete treasure.
  * @param myButton the button
  * @return return the button
  */
  private Button clickDeleteT(Button myButton) {
    myButton.setOnAction(event -> {
      output.setText(this.treasureInfo());
      input.setEditable(true);
      currentType = 1;
      currentStatus = 2;
    });
    return myButton;
  }

  /**
  * the treasure info.
  * @return return the information string
  */
  private String treasureInfo() {
    int i;
    String treasureInfo;
    String treasureListInfo = "Treasure List\n";
    ObservableList<String> treasureList;
    if (currentSpace.contains("Chamber")) {
      treasureList = FXCollections.observableArrayList(theController.treasureListCurrent(getIndex(currentSpace)));
    } else {
      treasureList = FXCollections.observableArrayList(theController.treasureListCurrentPassage(getIndex(currentSpace)));
    }

    for (i = 0; i < treasureList.size(); i++) {
      int count = i;
      treasureListInfo = treasureListInfo.concat(treasureList.get(count) + "\n");
    }
    treasureListInfo = treasureListInfo.concat("Please choose one number from the list, enter it in the input box.\n");
    return treasureListInfo;
  }



  /**
  * when click on a add monster.
  * @param myButton the button
  * @return return the button
  */
  private Button clickAddM(Button myButton) {
    myButton.setOnAction(event -> {
      output.setText(this.monsterList());
      input.setEditable(true);
      currentType = 2;
      currentStatus = 1;
    });
    return myButton;
  }


  /**
  * the monster list info.
  * @return return the information string
  */
  private String monsterList() {
    int i;
    String monsterInfo;
    String monsterListInfo = "Monster List\n";
    ObservableList<String> monsterList  = FXCollections.observableArrayList(theController.monsterList());
    for (i = 0; i < monsterList.size(); i++) {
      int count = i;
      monsterListInfo = monsterListInfo.concat(monsterList.get(count) + "\n");
    }
    return monsterListInfo;
  }

  /**
  * when click on a delete monster.
  * @param myButton the button
  * @return return the button
  */
  private Button clickDeleteM(Button myButton) {
    myButton.setOnAction(event -> {
      output.setText(this.monsterInfo());
      input.setEditable(true);
      currentType = 2;
      currentStatus = 2;
    });
    return myButton;
  }

  /**
  * the monster info.
  * @return return the information string
  */
  private String monsterInfo() {
    int i;
    String monsterInfo;
    String monsterListInfo = "Monster List\n";
    ObservableList<String> monsterList;
    if (this.chamberOrPassage(currentSpace) == 1) {
      monsterList = FXCollections.observableArrayList(theController.monsterListCurrent(getIndex(currentSpace)));
    } else {
      monsterList = FXCollections.observableArrayList(theController.monsterListCurrentPassage(getIndex(currentSpace)));
    }

    for (i = 0; i < monsterList.size(); i++) {
      int count = i;
      monsterListInfo = monsterListInfo.concat(monsterList.get(count) + "\n");
    }
    monsterListInfo = monsterListInfo.concat("Please choose one number from the list, enter it in the input box.\n");

    return monsterListInfo;
  }

  /**
  * when click on confirm button.
  * @param myButton the button
  * @return return the button
  */
  private Button confirmButton(Button myButton) {
    myButton.setOnAction(event -> {
      if (this.chamberOrPassage(currentSpace) == 1) {
        if (currentStatus == 1) {
          this.addFinal();
        } else if (currentStatus == 2) {
          this.deleteFinal();
        }
      } else {
        if (currentStatus == 1) {
          this.addPassageFinal();
        } else if (currentStatus == 2) {
          this.deletePassageFinal();
        }
      }
    });

    return myButton;
  }

  /**
  * after click on confirm, adding is final.
  */
  private void addFinal() {
    if (currentType == 1) {
      theController.addTreasure(getIndex(currentSpace));
      output.setText("Successfully added a treasure to " + currentSpace + "\n Please click the space again!");
    } else {
      theController.addMonster(getIndex(currentSpace));
      output.setText("Successfully added a monster to " + currentSpace + "\n Please click the space again!");
    }
  }

  /**
  * after click on confirm, removing is final.
  */
  private void deleteFinal() {
    if (currentType == 1) {
      theController.deleteTreasure(getIndex(currentSpace));
      output.setText("Successfully removed a treasure in " + currentSpace + "\n Please click the space again!");
    } else {
      theController.deleteMonster(getIndex(currentSpace));
      output.setText("Successfully removed a monster in " + currentSpace + "\n Please click the space again!");
    }
  }

  /**
  * after click on confirm, adding things to passage is final.
  */
  private void addPassageFinal() {
    if (currentType == 1) {
      theController.addTreasurePassage(getIndex(currentSpace));
      output.setText("Successfully added a treasure to " + currentSpace + "\n Please click the space again!");
    } else {
      theController.addMonsterPassage(getIndex(currentSpace));
      output.setText("Successfully added a monster to " + currentSpace + "\n Please click the space again!");
    }
  }


  /**
  * after click on confirm, removing things to passage is final.
  */
  private void deletePassageFinal() {
    if (currentType == 1) {
      theController.deleteTreasurePassage(getIndex(currentSpace));
      output.setText("Successfully removed a treasure in " + currentSpace + "\n Please click the space again!");
    } else {
      theController.deleteMonsterPassage(getIndex(currentSpace));
      output.setText("Successfully removed a monster in " + currentSpace + "\n Please click the space again!");
    }
  }

  /**
  * my right set up.
  * @return a veritcal box
  */
  private VBox setRight() {

    doorDescription = new TextArea();
    doorDescription.setPrefHeight(200);
    doorDescription.setPrefWidth(200);
    doorDescription.setEditable(false);

    VBox layout = new VBox(10);
    layout.getChildren().addAll(boxes, doorDescription);
    layout.getStyleClass().add("right-section");

    return layout;
  }

  /**
  * my list.
  * @param spaces the chamber and passage list
  * @return my list view
  */
  private Node createListView(ObservableList<String> spaces) {
    ListView temp = new ListView<String>(spaces);
    temp.setPrefWidth(150);
    temp.setPrefHeight(150);
    temp = listHandler(temp);
    temp.getStyleClass().add("list-section");

    return temp;
  }


  /**
  * when you click on the list.
  * @param temp the chamber and passage list
  * @return my list view
  */
  private ListView listHandler(ListView temp) {
    temp.setOnMouseClicked((MouseEvent event) -> {
      output.setText(this.getDescription(temp.getSelectionModel().getSelectedItem().toString()));
      currentSpace = temp.getSelectionModel().getSelectedItem().toString();
      if (this.chamberOrPassage(currentSpace) == 1) {
        if (theController.checkUnusualShape(getIndex(currentSpace)) == 1) {
          this.unusualShape();
        } else {
          this.usualShape();
        }
      } else {
        int size = theController.getPassageSize(getIndex(currentSpace));
        ((ChamberView) room).addingPassage(size);
        ((ChamberView) room).addTreasure(size, theController.getTreasurePassageSize(getIndex(currentSpace)));
        ((ChamberView) room).addMonster(size, theController.getMonsterPassageSize(getIndex(currentSpace)));
      }

      System.out.println("clicked on " + currentSpace);
    });

    return temp;
  }

  /**
  * when the shape is unusual.
  */
  private void unusualShape() {
    ((ChamberView) room).adding(4, 4);
    ((ChamberView) room).addDoors(theController.getDoorSize(getIndex(currentSpace)));
    ((ChamberView) room).addTreasure(theController.getTreasureSize(getIndex(currentSpace)));
    ((ChamberView) room).addMonster(theController.getMonsterSize(getIndex(currentSpace)));
  }


  /**
  * when the shape is usual.
  */
  private void usualShape() {
    int length = theController.getChamberLength(getIndex(currentSpace));
    int width = theController.getChamberWidth(getIndex(currentSpace));
    ((ChamberView) room).adding(length / 5, width / 5);
    ((ChamberView) room).addDoors(theController.getDoorSize(getIndex(currentSpace)));
    ((ChamberView) room).addTreasure(theController.getTreasureSize(getIndex(currentSpace)));
    ((ChamberView) room).addMonster(theController.getMonsterSize(getIndex(currentSpace)));
    System.out.println("The ratio is 1:2");
  }

  /**
  * get the description.
  * @param info the String
  * @return the info string
  */
  private String getDescription(String info) {
    BorderPane temp = new BorderPane();
    int i;
    int num = 0;
    if (this.chamberOrPassage(info) == 1) {
      num = getIndex(info);
      this.setMenu(num);
      return theController.getChamberDescription(num);
    } else {
      num = getIndex(info);
      this.setMenuPassage(num);
      return theController.getPassageDescription(num);
    }
  }

  /**
  * check the current space is chamber or passage.
  * @param info the String
  * @return the result
  */
  private int chamberOrPassage(String info) {
    if (info.contains("Chamber")) {
      return 1;
    } else {
      return 0;
    }
  }

  /**
  * get the current space.
  * @param info the String
  * @return the result
  */
  private int getIndex(String info) {
    return Integer.parseInt(String.valueOf(info.charAt(7))) - 1;
  }

  /**
  * set the menu of chamber.
  * @param num the number
  */
  private void setMenu(int num) {
    int i;
    ObservableList<String> nameList = FXCollections.observableArrayList(theController.getNameList(theController.getChamber(num)));
    this.boxes.setItems(nameList);

    for (i = 0; i < boxes.getItems().size(); i++) {
      boxes = boxHandler(num, i);
    }
  }

  /**
  * set the menu of passage.
  * @param num the number
  */
  private void setMenuPassage(int num) {
    int i;
    ObservableList<String> nameList = FXCollections.observableArrayList(theController.getNameList(theController.getPassage(num)));
    this.boxes.setItems(nameList);

    for (i = 0; i < boxes.getItems().size(); i++) {
      boxes = boxPassageHandler(num, i);
    }

  }

  /**
  * when you click on the box item.
  * @param num the number
  * @param count the count
  * @return the ComboBox
  */
  private ComboBox boxHandler(int num, int count) {
    boxes.setOnAction(event -> {
      doorDescription.setText(theController.getDoorDescription(theController.getChamber(num), count));
      System.out.println("clicked on Door");
    });

    return boxes;
  }


  /**
  * when you click on the box item for passage.
  * @param num the number
  * @param count the count
  * @return the ComboBox
  */
  private ComboBox boxPassageHandler(int num, int count) {
    boxes.setOnAction(event -> {
      doorDescription.setText(theController.getDoorDescriptionPassage(theController.getPassage(num), count));
      System.out.println("clicked on Door");
    });

    return boxes;
  }


  /**
  * create a popup.
  * @param x the x
  * @param y the y
  * @param text the text
  * @return the popup
  */
  private Popup createPopUp(int x, int y, String text) {
    Popup popup = new Popup();
    popup.setX(x);
    popup.setY(y);
    TextArea textA = new TextArea(text);
    popup.getContent().addAll(textA);
    textA.setStyle(" -fx-background-color: white;");
    textA.setMinWidth(80);
    textA.setMinHeight(50);
    return popup;
  }

  /**
  * create a button.
  * @param text the text on the button
  * @return a button
  */
  private Button createButton(String text) {
    Button button = new Button();
    button.setText(text);
    button.setStyle("");
    return button;
  }

  /**
  * the main.
  * @param args the arguments
  */
  public static void main(String[] args) {
    launch(args);
  }

}
