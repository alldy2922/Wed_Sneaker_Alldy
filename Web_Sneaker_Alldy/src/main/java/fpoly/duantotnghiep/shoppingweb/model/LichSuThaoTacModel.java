package fpoly.duantotnghiep.shoppingweb.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@ToString
@Table(name = "lichsuthaotac")
public class LichSuThaoTacModel {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private String id;

    @Column(name = "action")
    private String action;

    @Column(name = "details")
    private String details;

    @Column(name = "username")
    private String username;

    @Column(name = "timechange")
    @CreationTimestamp
    private Date timeChange;


}
