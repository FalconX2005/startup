package uz.pdp.startup.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.startup.entity.Attachment;

import java.util.Optional;

public interface AttachmentRepository extends JpaRepository<Attachment, Long> {
    Optional<Attachment> findAttachmentById(long id);
}