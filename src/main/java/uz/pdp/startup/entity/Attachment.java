package uz.pdp.startup.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import uz.pdp.startup.entity.tempAbs.AbsLongEntity;

/**
 * Created by: Umar
 * DateTime: 7/15/2025 1:27 PM
 */

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@SQLDelete(sql = "UPDATE attachment SET deleted = true WHERE id = ?")
public class Attachment extends AbsLongEntity {
    private String fileName;

    @Column(nullable = false)
    private String fileType;

    private String filePath;

    private Long fileSize;

}
