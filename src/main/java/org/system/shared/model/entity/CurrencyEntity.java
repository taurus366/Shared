package org.system.shared.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "currency")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class CurrencyEntity extends BaseEntity {

    @Column(nullable = false)
    private String title;

    @Column()
    private String symbol;

    @Column()
    private boolean active = true;

}
