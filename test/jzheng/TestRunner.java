package jzheng;

import java.util.List;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

/**
*
* @author Jingjing Zheng
*/
public class TestRunner {
  public static void main(String [] args) {

    System.out.println("=================PassageTest===================");
    Result result = JUnitCore.runClasses(PassageTest.class);
    System.out.print("\n*****Failed Test Report****\n");
    List<Failure> failedList = result.getFailures();
    failedList.forEach(f -> {System.out.println(f);});
    System.out.println("Number of Failed Tests = " + result.getFailureCount() + "\n");

    /*repeat steps the above for each junit test file you have*/
    System.out.println("===================DoorTest====================");
    Result result2 = JUnitCore.runClasses(DoorTest.class);
    System.out.print("\n*****Failed Test Report****\n");
    List<Failure> failedList2 = result2.getFailures();
    failedList2.forEach(f -> {System.out.println(f);});
    System.out.println("Number of Failed Tests = " + result2.getFailureCount() + "\n");

    System.out.println("=================ChamberTest===================");
    Result result3 = JUnitCore.runClasses(ChamberTest.class);
    System.out.print("\n*****Failed Test Report****\n");
    List<Failure> failedList3 = result3.getFailures();
    failedList3.forEach(f -> {System.out.println(f);});
    System.out.println("Number of Failed Tests = " + result3.getFailureCount() + "\n");

    System.out.println("==============PassageSectionTest===============");
    Result result4 = JUnitCore.runClasses(PassageSectionTest.class);
    System.out.print("\n*****Failed Test Report****\n");
    List<Failure> failedList4 = result.getFailures();
    failedList4.forEach(f -> {System.out.println(f);});
    System.out.println("Number of Failed Tests = " + result4.getFailureCount() + "\n");

    System.out.println("===============================================");
    int numOfTest = result.getFailureCount() + result2.getFailureCount() + result3.getFailureCount() + result4.getFailureCount();
    System.out.println("\t   Total Number of Failed Tests = " + numOfTest);
    if (numOfTest == 0) {
      System.out.println("\tCongratulations! You are sooooo clever!!!");
    }
    System.out.println("===============================================");
  }
}
