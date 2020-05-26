/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tecnoweb.bean;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;

/**
 *
 * @author Jesús
 */
@Named(value = "language")
@SessionScoped
public class LanguageBean implements Serializable{
    
    private String local;
    private static Map<String,Object> countries;
    static{
            countries = new LinkedHashMap<String,Object>();
            countries.put("English", new Locale("en", "EN")); //label, value
            countries.put("Spanish", new Locale("es", "ES"));
            countries.put("German", new Locale("de", "DE"));
    }

    /**
     * Creates a new instance of LanguageBean
     */
    public LanguageBean() {
    }

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    public static Map<String, Object> getCountries() {
        return countries;
    }

    public static void setCountries(Map<String, Object> countries) {
        LanguageBean.countries = countries;
    }
    
    public void countryLocaleCodeChanged(ValueChangeEvent e){

        String newLocaleValue = e.getNewValue().toString();

        //loop a map to compare the locale code
        for (Map.Entry<String, Object> entry : countries.entrySet()) {

                if(entry.getValue().toString().equals(newLocaleValue)){

                        FacesContext.getCurrentInstance()
                                .getViewRoot().setLocale((Locale)entry.getValue());

                }
        }

    }
    
}
