package md05_ontap.model.entity;

import jakarta.persistence.*;
import lombok.*;


@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "buses")
public class Bus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bus_id")
    private int busId;

    @Column(name = "bus_name", length = 100, nullable = false, unique = true)
    private String busName;

    @Column(name = "registration_number", length = 30,nullable = false, unique = true)
    private String registrationNumber;

    @Column(name = "total_seats", nullable = false)
    private int totalSeats;

    @Column(name = "image_bus", length = 255, nullable = false)
    private String imageBus;

    @Column(name = "status", columnDefinition = "BIT(1) DEFAULT 1")
    private Boolean status = true;
 }
