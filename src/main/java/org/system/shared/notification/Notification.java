package org.system.shared.notification;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.contextmenu.ContextMenu;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.dom.Style;
import com.vaadin.flow.server.VaadinSession;
import com.vaadin.flow.server.WebBrowser;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.system.shared.Utils;
import org.system.shared.model.dto.NotificationDTO;
import org.system.shared.model.entity.NotificationEntity;
import org.system.shared.model.service.NotificationsService;

import java.awt.event.FocusListener;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class Notification{

    private final NotificationsService notificationsService;
    @Setter
    private Long userId;


    @Setter
    @Getter
    private WebBrowser webBrowser;

    @Setter
    @Getter
    private ZoneId zoneId;



    public Notification(NotificationsService notificationsService) {
        this.notificationsService = notificationsService;
    }

    private Span spanCountNotify;

    public void updateDatasOnlyForWS(List<NotificationEntity> list) {
//        final List<NotificationEntity> notifyByDate = getAllNotificationsByDateAndUserId(Instant.now(), userId);

        layoutMain.removeAll();
        itemPopulate(list, layoutMain);
    };

    public NotificationDTO getDateFromDB(Long userId) {
        final List<NotificationEntity> allNotifications = getAllNotificationsByUserId(userId);

        // BELL COUNT
        if(allNotifications.size() > 99) spanCountNotify.setText("99+");
        else spanCountNotify.setText(String.valueOf(allNotifications.size()));

        final List<NotificationEntity> listToday = allNotifications.stream()
                .filter(notificationEntity -> {
                    Instant createdInstant = notificationEntity.getCreated();
//                    LocalDate createdDate = createdInstant.atZone(zoneId).toLocalDate();
                    LocalDate createdDate = createdInstant.atZone(ZoneId.of("UTC")).toLocalDate();
                    LocalDate today1 = LocalDate.now();
                    return createdDate.equals(today1);
                })
                .toList();

        NotificationDTO dto = new NotificationDTO();
        dto.setListAll(allNotifications);
        dto.setListToday(listToday);
        dto.setSizeAll(allNotifications.size());
        dto.setSizeToday(listToday.size());

        return dto;

    }

    public Button initUI(Long userId) {
        this.userId = userId;

        if(zoneId == null)
            zoneId = ZoneId.systemDefault();

//        LocalDate localDate2 = Instant.now().atZone(zoneId).toLocalDate();
        LocalDate localDate2 = Instant.now().atZone(ZoneId.of("UTC")).toLocalDate();
        datePicker = new DatePicker(localDate2);

        spanCountNotify = new Span();
        final NotificationDTO dateFromDB = getDateFromDB(userId);


        spanCountNotify.getElement().getThemeList().addAll(
                Arrays.asList("badge", "error", "primary", "small", "pill"));
        spanCountNotify.getStyle().set("position", "absolute")
                .set("transform", "translate(-60%, -100%)")
                .set("font-size", "14px")
                .set("padding", "3px");

        initNotificationWindow(dateFromDB.getListToday());

        Button bellBtn = new Button(VaadinIcon.BELL_O.create());
        bellBtn.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        bellBtn.getElement().appendChild(spanCountNotify.getElement());
        bellBtn.getElement().getStyle().set("flex-grow", "1");

        ContextMenu menu = new ContextMenu();
        menu.setOpenOnClick(true);
        menu.setTarget(bellBtn);
        menu.add(notificationWindow);

        listenForDatePicker(userId);

        return bellBtn;
    }

    private List<NotificationEntity> getAllNotificationsByDateAndUserId(Instant date, Long userId) {


       return notificationsService.gettAllSameDayAndUserId(date, userId);
    }

    private List<NotificationEntity> getAllNotificationsByUserId(Long userId) {
     return notificationsService.getAllNotificationsByUserId(userId);
    }

    private VerticalLayout notificationWindow;
    private Icon iconArrowLeft;
    private Icon iconArrowRight;
    private DatePicker datePicker;
    private  VerticalLayout layoutMain;
    private HorizontalLayout horizontalLayout;
    private void initNotificationWindow(List<NotificationEntity> notifications) {
//        if(notificationWindow != null)
//        notificationWindow.removeFromParent();
//        if(layoutMain != null)
//        layoutMain.removeFromParent();
//        if(horizontalLayout != null)
//        horizontalLayout.removeFromParent();
//        if(iconArrowLeft != null)
//            iconArrowLeft.removeFromParent();
//        if(iconArrowRight != null)
//            iconArrowRight.removeFromParent();
//        if(datePicker != null)
//            datePicker.removeFromParent();


        notificationWindow = new VerticalLayout();
        notificationWindow.getStyle()
                .set("padding", "0")
                .set("gap", "1px")
                .set("margin-left", "0");


        layoutMain = new VerticalLayout();
        layoutMain.getStyle()
                .set("padding", "0")
                .set("width", "400px")
                .set("gap", "0");




        // date PIcker
        datePicker.getStyle()
                .set("width", "130px");



       horizontalLayout = new HorizontalLayout();
       horizontalLayout.getStyle()
               .set("align-items", "center")
               .set("justify-content", "space-evenly")
               .set("min-width", "100%")
               .set("box-shadow", "4px -9px 20px 1px");

        final Icon iconBell = VaadinIcon.BELL.create();
        iconBell.getStyle()
                .set("font-size", "13px");
         iconArrowLeft = VaadinIcon.CHEVRON_LEFT.create();
        iconArrowLeft.getStyle()
                .set("font-size", "22px")
                .set("padding", "10px")
                .set("border-radius", "5px");
         iconArrowRight = VaadinIcon.CHEVRON_RIGHT.create();
        iconArrowRight.getStyle()
                .set("font-size", "22px")
                .set("padding", "10px")
                .set("border-radius", "5px");

        iconArrowLeft.getElement().addEventListener("mouseover", event -> {
            iconArrowLeft.getStyle()
                    .set("border", "1px solid black");
        });
        iconArrowLeft.getElement().addEventListener("mouseout", event -> {
           iconArrowLeft.getStyle()
                   .set("border", "unset");
        });

        iconArrowRight.getElement().addEventListener("mouseover", event -> {
            iconArrowRight.getStyle()
                    .set("border", "1px solid black");
        });
        iconArrowRight.getElement().addEventListener("mouseout", event -> {
            iconArrowRight.getStyle()
                    .set("border", "unset");
        });

//        horizontalLayout.add(iconBell, datePicker, iconArrowLeft, iconArrowRight);
        horizontalLayout.add(iconBell);
        horizontalLayout.add(datePicker);
        horizontalLayout.add(iconArrowLeft);
        horizontalLayout.add(iconArrowRight);

        notificationWindow.add(horizontalLayout);

       itemPopulate(notifications, layoutMain);

        notificationWindow.add(layoutMain);
    }

    private VerticalLayout itemPopulate(List<NotificationEntity> notifications, VerticalLayout layout) {
        UI.getCurrent().access(() -> {
            for (NotificationEntity entity : notifications) {

                Section sectionItem = new Section();
                sectionItem.addClassName("notification-item");
                sectionItem.getStyle()
                        .set("font-size", "15px")
                        .set("padding", "30px 0 30px 10px")
                        .set("max-width", "100%")
                        .set("width", "-webkit-fill-available")
                        .set("box-shadow", "inset 0px 0px 4px 0px");

                if (entity.getIsRead()) sectionItem.getStyle()
                        .set("border-left", "3px solid green")
                        .set("background", "whitesmoke");
                else sectionItem.getStyle()
                        .set("border-left", "3px solid red");

                Icon timer = VaadinIcon.DATE_INPUT.create();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern(Utils.PATTERN_FORMAT);

                Span time = new Span(entity.getCreated().atZone(zoneId).toLocalDateTime().format(formatter));
                Anchor anchor = new Anchor();
                anchor.add(timer, time);
                time.addClassName("notification-time");
                Span title = new Span(entity.getTitle());
                title.addClassName("notification-title");

                sectionItem.add(anchor, title);
                layout.add(sectionItem);
            }
        });

        return layout;
    }

//    private void listenForDatePicker(Long userId) {
//
//        datePicker.addValueChangeListener(datePicker -> {
//            Instant newInstant = datePicker.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant();
//            final List<NotificationEntity> notifyByDate = getAllNotificationsByDateAndUserId(newInstant, userId);
//
//            layoutMain.removeAll();
//            itemPopulate(notifyByDate, layoutMain);
//        });
//
//        iconArrowLeft.addClickListener(iconClickEvent -> {
//            LocalDate selectedDate = datePicker.getValue().minusDays(1);
//            datePicker.setValue(selectedDate);
//
//            Instant newInstant = datePicker.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant();
//            final List<NotificationEntity> notifyByDate = getAllNotificationsByDateAndUserId(newInstant, userId);
//            layoutMain.removeAll();
//            itemPopulate(notifyByDate, layoutMain);
//        });
//
//        iconArrowRight.addClickListener(iconClickEvent -> {
//            LocalDate selectedDate = datePicker.getValue().plusDays(1);
//            datePicker.setValue(selectedDate);
//
//            Instant newInstant = datePicker.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant();
//            final List<NotificationEntity> notifyByDate = getAllNotificationsByDateAndUserId(newInstant, userId);
//            layoutMain.removeAll();
//            itemPopulate(notifyByDate, layoutMain);
//        });
//
//    }

    private void listenForDatePicker(Long userId) {
        datePicker.addValueChangeListener(datePickerEvent -> {
//            Instant newInstant = datePickerEvent.getValue().atStartOfDay(zoneId).toInstant();
            Instant newInstant = datePickerEvent.getValue().atStartOfDay(ZoneId.of("UTC")).toInstant();

            final List<NotificationEntity> notifyByDate = getAllNotificationsByDateAndUserId(newInstant, userId);

                layoutMain.removeAll();
                itemPopulate(notifyByDate, layoutMain);
        });

        iconArrowLeft.addClickListener(iconClickEvent -> {
            LocalDate selectedDate = datePicker.getValue().minusDays(1);
            datePicker.setValue(selectedDate);
        });

        iconArrowRight.addClickListener(iconClickEvent -> {
            LocalDate selectedDate = datePicker.getValue().plusDays(1);
            datePicker.setValue(selectedDate);
        });
    }

    public void addNewNotificationToUser(NotificationEntity entity) {

        notificationsService.createNewNotification(entity);


    }


}
