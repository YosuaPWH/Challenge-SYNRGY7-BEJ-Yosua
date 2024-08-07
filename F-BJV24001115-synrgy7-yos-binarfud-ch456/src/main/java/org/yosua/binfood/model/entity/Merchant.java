package org.yosua.binfood.model.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@SQLDelete(sql = "UPDATE merchants SET isActive = false WHERE id = ?")
@SQLRestriction("is_active = true")
@Table(name = "merchants")
public class Merchant extends BaseEntity {

    @Column(unique = true, nullable = false)
    private String name;

    @Column(nullable = false)
    private String location;

    @Column(nullable = false, columnDefinition = "boolean default true")
    private boolean open;

    @OneToMany(mappedBy = "merchant")
    private List<Product> products;

    @Column(name = "is_active")
    private boolean isActive = Boolean.TRUE;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;
}
