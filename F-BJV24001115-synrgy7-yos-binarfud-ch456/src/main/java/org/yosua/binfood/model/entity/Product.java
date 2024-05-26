package org.yosua.binfood.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@SQLDelete(sql = "UPDATE merchants SET isActive = false WHERE id = ?")
@SQLRestriction("isActive = true")
@Table(name = "products")
public class Product extends BaseEntity {

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Double price;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "merchant_id", nullable = false, referencedColumnName = "id")
    private Merchant merchant;

    @OneToMany(mappedBy = "product")
    private List<OrderDetail> orderDetails;

    @Column
    private boolean isActive = Boolean.TRUE;
}
