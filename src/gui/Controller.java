package gui;

import hydra.Battle;
import hydra.Hydra;
import java.util.ArrayList;

public class Controller {
    private GuiDemo myGui;
    private Battle hydraBattle;
    private static int NUM_HYDRAS = 5;

    public Controller(GuiDemo theGui){
        myGui = theGui;
        hydraBattle = new Battle();
        hydraBattle.createBattleLineup(NUM_HYDRAS);
    }

    public ArrayList<String> getNameList(){
        ArrayList<String> nameList = new ArrayList<>();
        ArrayList<Hydra> hydras = hydraBattle.getBattleLineup();
        for(Hydra h: hydras){
            nameList.add( h.toString());
        }
        return nameList;
    }

    public void reactToButton(){
        System.out.println("Thanks for clicking!");
    }

    public String getNewDescription(){
        //return "this would normally be a description pulled from the model of the Dungeon level.";
        return String.join("\n", getNameList());
    }

}
