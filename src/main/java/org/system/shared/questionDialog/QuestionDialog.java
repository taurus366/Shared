package org.system.shared.questionDialog;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import org.springframework.stereotype.Component;

@Component
public class QuestionDialog {

    public QuestionDialog() {
    }

    public Dialog getQuestionYesNo(String title, String yesBtn, String noBtn, Runnable onYesCallback, Runnable onNoCallback) {

        Button yesBtn1 = new Button(yesBtn);
        Button noBtn1 = new Button(noBtn);

        HorizontalLayout horizontalLayout = new HorizontalLayout();
        horizontalLayout.getStyle().set("justify-content", "space-between");

        horizontalLayout.add(yesBtn1, noBtn1);

        Dialog dialog = new Dialog(title);
        dialog.setCloseOnOutsideClick(false);
        dialog.setCloseOnEsc(false);

        dialog.add(horizontalLayout);

        yesBtn1.addClickListener(event -> {
            if(onYesCallback != null) {
                onYesCallback.run();
            }
            dialog.close();
        });

        noBtn1.addClickListener(event -> {
            if(onNoCallback != null) {
                onNoCallback.run();
            }
            dialog.close();
        });




        return dialog;

    }

    public Dialog getQuestionYesNo(String title, String yesBtn, String noBtn, Runnable onYesCallback) {

        Button yesBtn1 = new Button(yesBtn);
        Button noBtn1 = new Button(noBtn);

        HorizontalLayout horizontalLayout = new HorizontalLayout();
        horizontalLayout.getStyle().set("justify-content", "space-between");

        horizontalLayout.add(yesBtn1, noBtn1);

        Dialog dialog = new Dialog(title);
        dialog.setCloseOnOutsideClick(false);
        dialog.setCloseOnEsc(false);

        dialog.add(horizontalLayout);

        yesBtn1.addClickListener(event -> {
            if(onYesCallback != null) {
                onYesCallback.run();
            }
            dialog.close();
        });

        noBtn1.addClickListener(event -> {
            dialog.close();
        });




        return dialog;

    }

}
