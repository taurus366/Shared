package org.system.shared.model.dto;

import com.vaadin.flow.component.UI;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class UpdaterDTO {

    private UI ui;

    private Long userId;

    private Map<String, Object> items = new HashMap<>();
}
