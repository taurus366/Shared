package org.system.shared.model.dto;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import lombok.Data;

@Data
public class GridTableDTO<T> {

    private Button createNewItemBtn;
    private Button refreshItemsBtn;

    private Grid<T> grid;
    private VerticalLayout layout;

    private Dialog newItemDialog;
    private Dialog updateItemDialog;

    private Button saveBtnDialogNewItem;
    private Button cancelSaveItemBtnDialogNewItem;

    private Button updateBtnDialogExistsItem;
    private Button cancelBtnDialogExistsItem;
    private Button removeBtnDialogExistsItem;

}
