/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tecnoweb.bean;

import java.io.Serializable;
import java.util.Locale;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;

/**
 *
 * @author Jesús
 */
@Named(value = "localeBean")
@SessionScoped
public class LocaleBean implements Serializable{
  private final static Locale ENGLISH = new Locale("en");
  private final static Locale SPANISH = new Locale("es");
  private final static Locale DEUTSCH = new Locale("de");
  private Locale currentLocale=SPANISH; 
    /**
     * Creates a new instance of LocaleBean
     */
    public LocaleBean() {
    }
    
  public Locale getCurrentLocale() {
    return(currentLocale);
  }

  public String setEnglish() {
    currentLocale=ENGLISH;
    changeLocale();
    return null;
  }
  
  public String setSpanish() {
    currentLocale=SPANISH;
    changeLocale();    
    return null;
  }
  
  public String setGerman(){
      currentLocale =DEUTSCH;
      changeLocale();
      return null;
  }
  
  private void changeLocale () {
      UIViewRoot view = FacesContext.getCurrentInstance()
        			.getViewRoot();
      view.setLocale(currentLocale);
  }
    
}
