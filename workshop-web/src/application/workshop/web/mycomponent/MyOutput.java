package application.workshop.web.mycomponent;

import javax.faces.component.FacesComponent;
import javax.faces.component.UIComponentBase;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import java.io.IOException;

@FacesComponent(createTag = true, tagName = "myoutput", 
				namespace = "http://com.workshop.application", 
				value = "com.workshop.application.MyOutput")
public class MyOutput extends UIComponentBase {

	@Override
	public void encodeBegin(FacesContext context) throws IOException {
		ResponseWriter writer = context.getResponseWriter();
		writer.writeAttribute("style", "color : red", null);
		String message = (String) this.getAttributes().get("value");
		writer.write(message + "*");
	}

	@Override
	public String getFamily() {
		return "com.workshop.application.MyOutput";
	}
}