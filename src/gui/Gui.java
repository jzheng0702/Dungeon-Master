package gui;
import javafx.collections.FXCollections;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.TextField;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ToolBar;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.ListView;
import javafx.scene.layout.*;
import javafx.stage.Popup;
import javafx.stage.Stage;


public class Gui<toReturn> extends Application implements java.io.Serializable{
  /* Even if it is a GUI it is useful to have instance variables
  so that you can break the processing up into smaller methods that have
  one responsibility.
  */
  private Controller theController;
  private BorderPane root;  //the root element of this GUI
  private Popup descriptionPane;
  private Stage primaryStage;  //The stage that is passed in on initialization
  private TextArea output;
  private TextArea doorDescription;
  private TextField output2;
  private TextField input;
  private String currentSpace;
  private ComboBox<String> boxes = new ComboBox<String>();
  private int userChoice;
  private int currentType;
  private int currentStatus;
  private GridPane room;
  private ChamberView room2;

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
    primaryStage.setTitle("Dungeon");
    primaryStage.setScene(scene);
    primaryStage.show();

  }

  public void startLoad(Stage assignedStage) {
    /*Initializing instance variables */
    primaryStage = assignedStage;
    /*Border Panes have  top, left, right, center and bottom sections */
    root = setUpRoot();
    descriptionPane = createPopUp(200, 300, "Example Description of something");
    Scene scene = new Scene(root, 700, 700);
    primaryStage.setTitle("Dungeon");
    primaryStage.setScene(scene);
    primaryStage.show();

  }

  private BorderPane setUpRoot() {
    BorderPane temp = new BorderPane();
    //new Label("All of the variables")
    temp.setTop(myTop());
    ObservableList<String> VarList = FXCollections.observableArrayList(theController.getChamberAndPassageList());
    temp.setLeft(createListView(VarList));
    temp.setRight(setRight());
    temp.setCenter(centreArea());
    Button editButton = createButton("Edit");
    temp.setBottom(handler(editButton));
    return temp;
  }

  private HBox handler (Button myButton) {
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
      editChoice.getChildren().addAll(addT,deleteT,addM,deleteM);
      descriptionPane.getContent().add(editChoice);
      descriptionPane.show(primaryStage);
    });

    Button close = createButton("Close popup");
    close.setOnAction(e -> {
      descriptionPane.hide();
    });


    HBox layout = new HBox(10);
    layout.getChildren().addAll(myButton, close);
    return layout;
  }

  private ToolBar myTop() {
    ToolBar myToolBar = new ToolBar();

    MenuItem saveFile = new MenuItem("Save File");
    saveFile = saveFileHandler(saveFile);

    MenuItem loadFile = new MenuItem("Load File");
    loadFile = loadFileHandler(loadFile);

    MenuButton file = new MenuButton("File", null, saveFile,loadFile);

    myToolBar.getItems().add(file);
    return myToolBar;
  }

  private MenuItem saveFileHandler(MenuItem saveFile) {
    saveFile.setOnAction(event -> {
      output.setText("Save file!\nPlease enter the full path of the file(including the name of the file) in the input box! Click submit when you're done\nExample: /file/a4/fileName");
      input.setEditable(true);
      currentStatus = 3;
    });

    return saveFile;
  }

  private MenuItem loadFileHandler(MenuItem loadFile) {
    loadFile.setOnAction(event -> {
      output.setText("Load File!Please enter the full path of the file(includeing the name of the file) in the input box! Click submit when you're done\nExample: /file/a4/fileName");
      input.setEditable(true);
      currentStatus = 4;
    });

    return loadFile;
  }

  private VBox centreArea() {
    room = new ChamberView(0,0);

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
    layout.getChildren().addAll(room,output,input,submit,output2,confirm);
    return layout;
  }


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
        } else if (currentStatus == 3){
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

  private void addStatus(String choice) {
    userChoice = this.parsemyInt(choice);
    if (currentType == 1) {
      this.treasureAdd();
    } else {
      this.monsterAdd();
    }
  }

  private void monsterAdd() {
    String monsterInfo = theController.addTempMonster(userChoice);
    output2.setText("You have choose monster " + userChoice + " It is: " + monsterInfo + ", please hit confirm button to save changes");
  }

  private void treasureAdd() {
    String treasureInfo = theController.addTempTreasure(userChoice);
    output2.setText("You have choose treasure " + userChoice + " It is: " + treasureInfo + ", please hit confirm button to save changes");
  }

  private void deleteStatus(String choice) {
    userChoice = this.parsemyInt(choice);
    if (currentType == 1) {
      this.treasureDelete();
    } else {
      this.monsterDelete();
    }
  }

  private void monsterDelete() {
    String monsterInfo;
    if (this.chamberOrPassage(currentSpace) == 1) {
      monsterInfo = theController.deleteTempMonster(getIndex(currentSpace),userChoice);
    } else {
      monsterInfo = theController.deleteTempMonsterPassage(getIndex(currentSpace),userChoice);
    }
    output2.setText("You have choose monster " + userChoice + " It is: " + monsterInfo + ", please hit confirm button to save changes");
  }

  private void treasureDelete() {
    String treasureInfo;
    if (this.chamberOrPassage(currentSpace) == 1) {
      treasureInfo = theController.deleteTempTreasure(getIndex(currentSpace),userChoice);
    } else {
      treasureInfo = theController.deleteTempTreasurePassage(getIndex(currentSpace),userChoice);
    }
    output2.setText("You have choose treasure " + userChoice + " It is: " + treasureInfo + ", please hit confirm button to save changes");
  }

  private int parsemyInt (String value) {
    return Integer.parseInt(String.valueOf(value));
  }

  private void load(String choice) {
    theController.loadFile(choice);
    this.startLoad(primaryStage);
  }


  private Button clickAddT(Button myButton) {
    myButton.setOnAction(event -> {
      output.setText(this.treasureList());
      input.setEditable(true);
      currentType = 1;
      currentStatus = 1;
    });

    return myButton;
  }

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

  private Button clickDeleteT(Button myButton) {
    myButton.setOnAction(event -> {
      output.setText(this.treasureInfo());
      input.setEditable(true);
      currentType = 1;
      currentStatus = 2;
    });
    return myButton;
  }

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



  private Button clickAddM(Button myButton) {
    myButton.setOnAction(event -> {
      output.setText(this.monsterList());
      input.setEditable(true);
      currentType = 2;
      currentStatus = 1;
    });
    return myButton;
  }

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

  private Button clickDeleteM(Button myButton) {
    myButton.setOnAction(event -> {
      output.setText(this.monsterInfo());
      input.setEditable(true);
      currentType = 2;
      currentStatus = 2;
    });
    return myButton;
  }

  private String monsterInfo () {
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

  private Button confirmButton(Button myButton) {
    myButton.setOnAction(event -> {
      if (this.chamberOrPassage(currentSpace) == 1) {
        if (currentStatus == 1) {
          this.addFinal();
        } else if (currentStatus == 2){
          this.deleteFinal();
        }
      } else {
        if (currentStatus == 1) {
          this.addPassageFinal();
        } else if (currentStatus == 2){
          this.deletePassageFinal();
        }
      }
    });

    return myButton;
  }

  private void addFinal() {
    if (currentType == 1) {
      theController.addTreasure(getIndex(currentSpace));
      output.setText("Successfully added a treasure to " + currentSpace);
      ((ChamberView)room).addTreasure();
    } else {
      theController.addMonster(getIndex(currentSpace));
      output.setText("Successfully added a monster to " + currentSpace);
    }
  }

  private void deleteFinal() {
    if (currentType == 1) {
      theController.deleteTreasure(getIndex(currentSpace));
      output.setText("Successfully removed a treasure to " + currentSpace);
    } else {
      theController.deleteMonster(getIndex(currentSpace));
      output.setText("Successfully removed a monster to " + currentSpace);
    }
  }

  private void addPassageFinal() {
    if (currentType == 1) {
      theController.addTreasurePassage(getIndex(currentSpace));
      output.setText("Successfully added a treasure to " + currentSpace);
    } else {
      theController.addMonsterPassage(getIndex(currentSpace));
      output.setText("Successfully added a monster to " + currentSpace);
    }
  }

  private void deletePassageFinal() {
    if (currentType == 1) {
      theController.deleteTreasurePassage(getIndex(currentSpace));
      output.setText("Successfully removed a treasure to " + currentSpace);
    } else {
      theController.deleteMonsterPassage(getIndex(currentSpace));
      output.setText("Successfully removed a monster to " + currentSpace);
    }
  }

  private VBox setRight() {

    doorDescription = new TextArea();
    doorDescription.setPrefHeight(200);
    doorDescription.setPrefWidth(200);
    doorDescription.setEditable(false);

    VBox layout = new VBox(10);
    //layout.setStyle("-fx-background-color: white; -fx-padding: 10;");
    layout.getChildren().addAll(boxes,doorDescription);

    return layout;
  }

  private Node createListView(ObservableList<String> spaces){
    ListView temp = new ListView<String>(spaces);
    temp.setPrefWidth(150);
    temp.setPrefHeight(150);
    temp = listHandler(temp);

    return temp;
  }

  private ListView listHandler(ListView temp) {
    temp.setOnMouseClicked((MouseEvent event)->{
      output.setText(this.getDescription(temp.getSelectionModel().getSelectedItem().toString()));
      currentSpace = temp.getSelectionModel().getSelectedItem().toString();
      if (this.chamberOrPassage(currentSpace) == 1){
        if (theController.checkUnusualShape(getIndex(currentSpace)) == 1) {
          this.unusualShape();
        } else {
          this.usualShape();
        }
      } else {
        int size = theController.getPassageSize(getIndex(currentSpace));
        ((ChamberView)room).addingPassage(size);
      }

      System.out.println("clicked on " + currentSpace);
    });

    return temp;
  }


  private void unusualShape() {
    ((ChamberView)room).adding(4,4);
    ((ChamberView)room).addDoors(theController.getDoorSize(getIndex(currentSpace)));
  }

  private void usualShape() {
    int length = theController.getChamberLength(getIndex(currentSpace));
    int width = theController.getChamberWidth(getIndex(currentSpace));
    ((ChamberView)room).adding(length/5,width/5);
    ((ChamberView)room).addDoors(theController.getDoorSize(getIndex(currentSpace)));
    System.out.println("The scale is 1:2");
  }

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

  private int chamberOrPassage(String info) {
    if (info.contains("Chamber")) {
      return 1;
    } else {
      return 0;
    }
  }

  private int getIndex(String info) {
    return Integer.parseInt(String.valueOf(info.charAt(7))) - 1;
  }

  private void setMenu(int num) {
    int i;
    ObservableList<String> nameList = FXCollections.observableArrayList(theController.getNameList(theController.getChamber(num)));
    this.boxes.setItems(nameList);

    for (i = 0; i < boxes.getItems().size(); i++) {
      boxes = boxHandler(num,i);
    }
  }

  private void setMenuPassage(int num) {
    int i;
    ObservableList<String> nameList = FXCollections.observableArrayList(theController.getNameList(theController.getPassage(num)));
    this.boxes.setItems(nameList);

    for (i = 0; i < boxes.getItems().size(); i++) {
      boxes = boxPassageHandler(num,i);
    }

  }

  private ComboBox boxHandler(int num,int count) {
    boxes.setOnAction(event -> {
      doorDescription.setText(theController.getDoorDescription(theController.getChamber(num),count));
      System.out.println("clicked on Door");
    });

    return boxes;
  }

  private ComboBox boxPassageHandler(int num,int count) {
    boxes.setOnAction(event -> {
      doorDescription.setText(theController.getDoorDescriptionPassage(theController.getPassage(num),count));
      System.out.println("clicked on Door");
    });

    return boxes;
  }


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

  private Button createButton(String text) {
    Button button = new Button();
    button.setText(text);
    button.setStyle("");
    return button;
  }

  private void changeDescriptionText(String text) {
    ObservableList<Node> list = descriptionPane.getContent();
    for (Node t : list) {
      if (t instanceof TextArea) {
        TextArea temp = (TextArea) t;
        temp.setText(text);
      }
    }
  }


  public static void main(String[] args) {
    launch(args);
  }

}
