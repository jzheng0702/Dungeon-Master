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
  private MenuButton buttons;

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
    buttons = new MenuButton("List of Doors");
    temp.setRight(buttons);
    ObservableList<String> VarList = FXCollections.observableArrayList(theController.getChamberAndPassageList());
    temp.setLeft(createListView(VarList));
    output = new TextArea();
    output.setEditable(false);
    temp.setCenter(output);
    return temp;
  }

  private Node createListView(ObservableList<String> spaces){
    ListView temp = new ListView<String>(spaces);
    temp.setPrefWidth(150);
    temp.setPrefHeight(150);
    temp.setOnMouseClicked((MouseEvent event)->{
      output.setText(this.getDescription(temp.getSelectionModel().getSelectedItem().toString()));
      System.out.println("clicked on " + temp.getSelectionModel().getSelectedItem());
    });

    return temp;
  }

  private String getDescription(String info) {
    int i;
    int num = 0;
    if (info.contains("Chamber")) {
      num = getIndex(info);
      buttons = setMenuPanel(num);
      return theController.getChamberDescription(num);
    } else {
      num = getIndex(info);
      buttons = setMenuPanelPassage(num);
      return theController.getPassageDescription(num);
    }
  }

  private int getIndex(String info) {
    return Integer.parseInt(String.valueOf(info.charAt(7))) - 1;
  }


  private MenuButton setMenuPanel(int num) {
    int i;

    ObservableList<String> nameList = FXCollections.observableArrayList(theController.getNameList(theController.getChamber(num)));
    MenuItem myItems[] = new MenuItem[nameList.size()];

    for (i = 0; i < nameList.size(); i++) {
      int count = i;
      myItems[i] = new MenuItem(nameList.get(i));
      myItems[i].setOnAction(event -> {
        descriptionPane = createPopUp(200, 300, theController.getDoorDescription(theController.getChamber(num),count));
        descriptionPane.show(primaryStage);
        System.out.println("clicked on Door");
      });
    }

    MenuButton menuButton = new MenuButton("List of Doors",null, myItems);

    return menuButton;

  }

  private MenuButton setMenuPanelPassage(int num) {
    int i;

    ObservableList<String> nameList = FXCollections.observableArrayList(theController.getNameList(theController.getPassage(num)));
    MenuItem myItems[] = new MenuItem[nameList.size()];

    for (i = 0; i < nameList.size(); i++) {
      int count = i;
      myItems[i] = new MenuItem(nameList.get(i));
      myItems[i].setOnAction(event -> {
        descriptionPane = createPopUp(200, 300, theController.getDoorDescriptionPassage(theController.getPassage(num),count));
        descriptionPane.show(primaryStage);
        System.out.println("clicked on Door");
      });
    }

    MenuButton menuButton = new MenuButton("List of Doors",null, myItems);

    return menuButton;

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
  private Button createButton(String text, String format) {
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
