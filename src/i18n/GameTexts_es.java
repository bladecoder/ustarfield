package i18n;

import commons.i18n.ResourceBundle;

public class GameTexts_es extends ResourceBundle {
  public GameTexts_es() {
    resources.put( "Instructions", 
            "El objetivo del juego es sobrevivir hasta el final de cada nivel, " +
            "detruyendo tantos enemigos como puedas.\n"+ 
            " Mientras más enemigos destruyas, "+
            "más puntos tendrás y conseguiras una mejor posición en la tabla de marcadores."
            );
    resources.put("LEVEL 1", "NIVEL 1");
    resources.put("LEVEL 2", "NIVEL 2");
    resources.put("REACHING", "LLEGANDO");
    resources.put("END", "AL FINAL");
    resources.put("OF", "DEL");
    resources.put("LEVEL", "NIVEL");
  }
}
