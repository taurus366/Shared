package org.system.shared.alert;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import org.springframework.stereotype.Component;
import com.vaadin.flow.component.notification.Notification;

import java.util.Optional;
import java.util.Timer;
import java.util.TimerTask;

import static com.vaadin.flow.component.button.ButtonVariant.LUMO_TERTIARY_INLINE;

@Component
public class CustomAlert {

    public CustomAlert() {
    }

    public static void showAlertSuccess(String text) {
        Notification notification = new Notification();
        notification.setDuration(5000);
        notification.addThemeVariants(NotificationVariant.LUMO_SUCCESS);

//        Button viewBtn = new Button("View", clickEvent -> notification.close());
//        viewBtn.getStyle().setMargin("0 0 0 var(--lumo-space-l)");

        HorizontalLayout layout = new HorizontalLayout(VaadinIcon.CHECK_CIRCLE.create(), new Text(text),
                createCloseBtn(notification));

        notification.add(layout);
        notification.open();
    }

    public static void showAlertReportError(String text) {
        Notification notification = new Notification();
        notification.setDuration(5000);
        notification.addThemeVariants(NotificationVariant.LUMO_ERROR);

//        Button viewBtn = new Button("View", clickEvent -> notification.close());
//        viewBtn.getStyle().setMargin("0 0 0 var(--lumo-space-l)");

        HorizontalLayout layout = new HorizontalLayout(VaadinIcon.WARNING.create(), new Text(text),
                createCloseBtn(notification));

        notification.add(layout);
        notification.open();
    }

    public static Button createCloseBtn(Notification notification) {
        Button closeBtn = new Button(VaadinIcon.CLOSE_SMALL.create(),
                clickEvent -> notification.close());
        closeBtn.addThemeVariants(LUMO_TERTIARY_INLINE);
        closeBtn.getStyle().set("color", "#fff");

        return closeBtn;
    }

}
