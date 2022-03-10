package i18n;

import commons.i18n.ResourceBundle;

public class GameTexts extends ResourceBundle {
  public GameTexts() {
    resources.put( "Instructions", 
            "The target of this game is to survive till the end of levels " +
            "destroying all enemies you can.\n"+ 
            " While more enemies you destroy, "+
            "more points you get and you will be in a better position in " +
            "the hall of fame.\n" 
            );
  }
}
