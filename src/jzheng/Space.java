package jzheng;

/**
 * @author     jzheng06@uoguelph.ca
 * @version    1.3
 */

public abstract class Space implements java.io.Serializable{

 /**
  * get description.
  * @return the description
  */
  public abstract  String getDescription();

 /**
  * set a new door.
  * @param theDoor the new door
  */
  public abstract void setDoor(Door theDoor);

}
