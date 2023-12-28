package org.system.shared.grid;

import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.renderer.TextRenderer;
import org.springframework.stereotype.Component;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.button.Button;
import org.system.shared.model.dto.GridTableDTO;
import com.vaadin.flow.component.dialog.Dialog;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@Component
public class GridTable {

    private static final String PATTERN_FORMAT = "dd/MM/yyyy HH:mm";


    public GridTable() {}

    public static  <T> GridTableDTO<T> getGridTable(Class<T> entity, boolean autoCreateColumn, String... methodNames) {

        List<String> mNames = null;
        if(methodNames != null) mNames = List.of(methodNames);

        VerticalLayout layout = new VerticalLayout();

        Grid<T> grid = new Grid<T>(entity, autoCreateColumn);

        for (Grid.Column<T> column : grid.getColumns()) {
            String columnType = column.getKey();

            AtomicReference<String> rsForReturn = new AtomicReference<>("");

                if(columnType.equals("created") || columnType.equals("modified") || (mNames != null && mNames.contains(columnType))) {
                column.setRenderer(new TextRenderer<>(item -> {
                    try {
                        Method getMethod;

                       if(columnType.equals("created")) getMethod = item.getClass().getMethod("getCreated");
                       else if(columnType.equals("modified")) getMethod = item.getClass().getMethod("getModified");
                       else {
                           String rs = "get" + columnType.toUpperCase().charAt(0) + columnType.toLowerCase().substring(1, columnType.length());
                           getMethod = item.getClass().getMethod(rs);
                       }
                       rsForReturn.set(getMethod.invoke(item).toString());
                    } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException ignored) {}

                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern(PATTERN_FORMAT);
                    Instant instant = Instant.parse(rsForReturn.get());
                    LocalDateTime time = LocalDateTime.ofInstant(instant,  ZoneId.systemDefault());
                    return time.format(formatter);
                }));
                }

        }

        // ICONS
        final Icon cancelBtnDialogNewItemIconClose = VaadinIcon.CLOSE.create();
        cancelBtnDialogNewItemIconClose.setColor("red");
        final Icon saveBtnDialogNewItemIconCheck = VaadinIcon.CHECK.create();
        saveBtnDialogNewItemIconCheck.setColor("green");
        final Icon removeBtnDialogExistsItemIconRemove = VaadinIcon.TRASH.create();
        removeBtnDialogExistsItemIconRemove.setColor("red");
        final Icon updateBtnDialogExistsItemIconCheck = VaadinIcon.CHECK.create();
        updateBtnDialogExistsItemIconCheck.setColor("green");
        final Icon cancelBtnDialogExistsItemIconClose = VaadinIcon.CLOSE.create();
        cancelBtnDialogExistsItemIconClose.setColor("red");

        // BUTTONS IN DIALOG NEW ITEM
        Button saveBtnDialogNewItem = new Button(saveBtnDialogNewItemIconCheck);
        Button cancelBtnDialogNewItem = new Button(cancelBtnDialogNewItemIconClose);
        cancelBtnDialogNewItem.getStyle().set("margin-left", "auto");

        // BUTTONS IN DIALOG EXISTS ITEM
        Button updateBtnDialogExistsItem = new Button(updateBtnDialogExistsItemIconCheck);
        Button cancelBtnDialogExistsItem = new Button(cancelBtnDialogExistsItemIconClose);
        cancelBtnDialogExistsItem.getStyle().set("margin-left", "auto");
        Button removeBtnDialogExistsItem = new Button(removeBtnDialogExistsItemIconRemove);


        // BUTTONS
        Button createNewItemBtn = new Button(VaadinIcon.PLUS.create());
        Button refreshBtn = new Button(VaadinIcon.ROTATE_LEFT.create());

        // DIALOGS
        Dialog createNewItemDialog = new Dialog();
        createNewItemDialog.getHeader().add(cancelBtnDialogNewItem);
        createNewItemDialog.getFooter().add(saveBtnDialogNewItem);
        createNewItemDialog.setCloseOnEsc(false);
        createNewItemDialog.setCloseOnOutsideClick(false);

        Dialog updateExistItemDialog = new Dialog();
        updateExistItemDialog.getHeader().add(cancelBtnDialogExistsItem);
        updateExistItemDialog.getFooter().add(updateBtnDialogExistsItem, removeBtnDialogExistsItem);
        updateExistItemDialog.setCloseOnEsc(false);
        updateExistItemDialog.setCloseOnOutsideClick(false);

        // LISTENER
        createNewItemBtn.addClickListener(event -> {
            createNewItemDialog.open();
        });

        grid.addItemDoubleClickListener(event -> {
           updateExistItemDialog.open();
        });



        // LISTENER

        HorizontalLayout layout1 = new HorizontalLayout();
            layout1.add(refreshBtn, createNewItemBtn);

            layout.add(layout1);
            layout.add(grid);

            GridTableDTO<T> dto = new GridTableDTO<>();
            dto.setGrid(grid);
            dto.setLayout(layout);

            dto.setRefreshItemsBtn(refreshBtn);
            dto.setCreateNewItemBtn(createNewItemBtn);

            dto.setNewItemDialog(createNewItemDialog);
            dto.setUpdateItemDialog(updateExistItemDialog);

            dto.setSaveBtnDialogNewItem(saveBtnDialogNewItem);
            dto.setCancelSaveItemBtnDialogNewItem(cancelBtnDialogNewItem);

            dto.setUpdateBtnDialogExistsItem(updateBtnDialogExistsItem);
            dto.setCancelBtnDialogExistsItem(cancelBtnDialogExistsItem);
            dto.setRemoveBtnDialogExistsItem(removeBtnDialogExistsItem);
        return dto;
    }
}
