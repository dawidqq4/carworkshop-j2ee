package application.workshop.web.view.admin;

import javax.el.ValueExpression;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import application.workshop.model.Worker;

@FacesConverter(value = "workerConverter")
public class WorkerConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext ctx, UIComponent uiComponent, String workerId) {
        ValueExpression valueExpression = ctx.getApplication().getExpressionFactory().createValueExpression(ctx.getELContext(),
                                "#{adminOrderBean}", AdminOrderBean.class);

        AdminOrderBean orders = (AdminOrderBean) valueExpression.getValue(ctx.getELContext());
        return orders.getWorker(Long.valueOf(workerId));
    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object worker) {
        return ((Worker) worker).getId().toString();
    }

}