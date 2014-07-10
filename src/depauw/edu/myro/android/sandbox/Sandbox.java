package depauw.edu.myro.android.sandbox;

import depauw.edu.myro.andriod.Scribbler;
import android.app.Application;

public class Sandbox extends Application {

  private Scribbler scrib;

  public Scribbler getScribbler() {
    return scrib;
  }

  public void setScribbler(Scribbler aScrib) {
    scrib = aScrib;
  }
}
