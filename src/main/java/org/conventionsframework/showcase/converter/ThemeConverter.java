/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.conventionsframework.showcase.converter;

import org.conventionsframework.model.Theme;
import org.conventionsframework.showcase.controller.ThemeMBean;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;
import org.apache.myfaces.extensions.cdi.core.api.Advanced;

/**
 *
 * @author rmpestano
 */

@FacesConverter(value="themeConverter")
@Advanced
public class ThemeConverter implements Converter{
    
    @Inject
    private ThemeMBean themeMBean;
    
    public Object getAsObject(final FacesContext context, final UIComponent component, final String value) {
                return themeMBean.getThemeByName(value);
        }

        public String getAsString(final FacesContext context, final UIComponent component, final Object value) {
                return value != null ? ((Theme) value).getName():"";
        }
    
}
