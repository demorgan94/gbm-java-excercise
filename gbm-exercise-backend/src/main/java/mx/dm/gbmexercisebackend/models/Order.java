package mx.dm.gbmexercisebackend.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.lang.NonNull;

import java.sql.Timestamp;

@Entity
@Table(name = "ORDERS")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @CreationTimestamp
    @NonNull
    private Timestamp timestamp;

    @NonNull
    private String operation;

    @JsonProperty("issuer_name")
    @Column(name = "issuer_name")
    @NonNull
    private String issuerName;

    @JsonProperty("total_shares")
    @Column(name = "total_shares")
    @NonNull
    private int totalShares;

    @JsonProperty("share_price")
    @Column(name = "share_price")
    @NonNull
    private int sharePrice;
}
