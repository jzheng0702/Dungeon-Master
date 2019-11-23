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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.ListView;
import javafx.scene.layout.*;
import javafx.stage.Popup;
import javafx.stage.Stage;


public class GuiDemo<toReturn> extends Application {
  /* Even if it is a GUI it is useful to have instance variables
  so that you can break the processing up into smaller methods that have
  one responsibility.
  */
  private Controller theController;
  private BorderPane root;  //the root element of this GUI
  private Popup descriptionPane;
  private Stage primaryStage;  //The stage that is passed in on initialization
  private TextArea output;
  private String currentSpace;
  private ComboBox<String> boxes = new ComboBox<String>();

  /*a call to start replaces a call to the constructor for a JavaFX GUI*/
  @Override
  public void start(Stage assignedStage) {
    /*Initializing instance variables */
    theController = new Controller(this);
    primaryStage = assignedStage;
    /*Border Panes have  top, left, right, center and bottom sections */
    root = setUpRoot();
    descriptionPane = createPopUp(200, 300, "Example Description of something");
    Scene scene = new Scene(root, 500, 500);
    primaryStage.setTitle("Dungeon");
    primaryStage.setScene(scene);
    primaryStage.show();

  }

  private BorderPane setUpRoot() {
    BorderPane temp = new BorderPane();
    temp.setTop(new Label("All of the variables"));
    ObservableList<String> VarList = FXCollections.observableArrayList(theController.getChamberAndPassageList());
    temp.setLeft(createListView(VarList));
    temp.setRight(setRight());
    output = new TextArea();
    output.setEditable(false);
    temp.setCenter(output);
    Button editButton = createButton("Edit");
    temp.setBottom(handler(editButton));
    return temp;
  }

  private HBox handler (Button myButton) {
    myButton.setOnAction(e -> {
      descriptionPane = createPopUp(200, 300, "");
      VBox editChoice = new VBox();
      Button addT = createButton("Add Treasure");
      addT = clickAddT(addT);
      Button deleteT = createButton("Delete Treasure");
      deleteT = clickDeleteT(deleteT);
      Button addM = createButton("Add Monster");
      addM = clickAddM(addM);
      Button deleteM = createButton("Delete Monster");
      deleteM = clickDeleteM(deleteM);
      Button confirm = createButton("Confirm");
      confirm = confirmButton(confirm);
      editChoice.getChildren().addAll(addT,deleteT,addM,deleteM,confirm);
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


  private Button clickAddT(Button myButton) {
    myButton.setOnAction(event -> {
      int i;
      String treasureInfo;
      String treasureListInfo = "Treasure List\n";
      ObservableList<String> treasureList  = FXCollections.observableArrayList(theController.treasureList());
      for (i = 0; i < treasureList.size(); i++) {
        int count = i;
        treasureListInfo = treasureListInfo.concat(treasureList.get(count) + "\n");
      }
      output.setText(treasureListInfo);
      if (currentSpace.contains("Chamber")) {
        treasureInfo = theController.addTempTreasure(getIndex(currentSpace));
      } else {
        treasureInfo = "Need to fix haha";
        //theController.addTreasurePassage(getIndex(currentSpace));
      }


    });

    return myButton;
  }

  private Button clickDeleteT(Button myButton) {
    return myButton;
  }



  private Button clickAddM(Button myButton) {
    myButton.setOnAction(event -> {
      int i;
      String monsterListInfo = "Monster List\n";
      ObservableList<String> monsterList  = FXCollections.observableArrayList(theController.monsterList());
      for (i = 0; i < monsterList.size(); i++) {
        int count = i;
        monsterListInfo = monsterListInfo.concat(monsterList.get(count) + "\n");
      }
      output.setText(monsterListInfo);
      if (currentSpace.contains("Chamber")) {
        theController.addMonster(getIndex(currentSpace));
      } else {
        theController.addMonsterPassage(getIndex(currentSpace));
      }
      //output.setText("Successfully added Monster to " + currentSpace);
    });
    return myButton;
  }

  private Button clickDeleteM(Button myButton) {
    String result;
    myButton.setOnAction(event -> {
      if (currentSpace.contains("Chamber")) {
        if (theController.DeleteMonster(getIndex(currentSpace)) == "No Monster") {
          output.setText("There is no monster in " + currentSpace);
        } else {
          output.setText("Successfully removed Monster in " + currentSpace);
        }
      } else {
        if (theController.DeleteMonsterPassage(getIndex(currentSpace)) == "No Monster") {
          output.setText("There is no monster in " + currentSpace);
        } else {
          output.setText("Successfully removed Monster in " + currentSpace);
        }
      }
    });
    return myButton;
  }

  private Button confirmButton(Button myButton) {
    myButton.setOnAction(event -> {
      if (currentSpace.contains("Chamber")) {
        theController.addTreasure(getIndex(currentSpace));
      } else {
        //theController.addTreasurePassage(getIndex(currentSpace));
      }
      output.setText("Successfully added a treasure to " + currentSpace);
    });

    return myButton;
  }

  private HBox setRight() {
    Button close = createButton("Close popup");
    close.setOnAction(e -> {
      descriptionPane.hide();
    });

    HBox layout = new HBox(10);
    //layout.setStyle("-fx-background-color: white; -fx-padding: 10;");
    layout.getChildren().addAll(boxes, close);

    return layout;

  }

  private Node createListView(ObservableList<String> spaces){
    ListView temp = new ListView<String>(spaces);
    temp.setPrefWidth(150);
    temp.setPrefHeight(150);
    temp.setOnMouseClicked((MouseEvent event)->{
      output.setText(this.getDescription(temp.getSelectionModel().getSelectedItem().toString()));
      currentSpace = temp.getSelectionModel().getSelectedItem().toString();
      System.out.println("clicked on " + currentSpace);
    });

    return temp;
  }

  private String getDescription(String info) {
    BorderPane temp = new BorderPane();
    int i;
    int num = 0;
    if (info.contains("Chamber")) {
      num = getIndex(info);
      this.setMenu(num);
      return theController.getChamberDescription(num);
    } else {
      num = getIndex(info);
      this.setMenuPassage(num);
      return theController.getPassageDescription(num);
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
      int count = i;
      boxes.setOnAction(event -> {
        descriptionPane = createPopUp(200, 300, theController.getDoorDescription(theController.getChamber(num),count));
        //descriptionPane.show(primaryStage);
        descriptionPane.show(primaryStage);
        System.out.println("clicked on Door");
      });
    }
  }

  private void setMenuPassage(int num) {
    int i;
    ObservableList<String> nameList = FXCollections.observableArrayList(theController.getNameList(theController.getPassage(num)));
    this.boxes.setItems(nameList);

    for (i = 0; i < boxes.getItems().size(); i++) {
      int count = i;
      //String buffer = boxes.getItems().get(i);
      boxes.setOnAction(event -> {
        descriptionPane = createPopUp(200, 300, theController.getDoorDescriptionPassage(theController.getPassage(num),count));
        if (!descriptionPane.isShowing()) {
          descriptionPane.show(primaryStage);
        } else {
          descriptionPane.hide();
        }
        System.out.println("clicked on Door");
      });
    }

  }


  /* an example of a popup area that can be set to nearly any
  type of node
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

  /*generic button creation method ensure that all buttons will have a
  similar style and means that the style only need to be in one place
  */
  private Button createButton(String text) {
    Button btn = new Button();
    btn.setText(text);
    btn.setStyle("");
    return btn;
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
