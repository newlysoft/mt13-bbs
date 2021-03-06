package com.hw.aggregate.comment.model;

import com.hw.aggregate.comment.CommentRepository;
import com.hw.aggregate.comment.exception.CommentAccessException;
import com.hw.aggregate.comment.exception.CommentNotFoundException;
import com.hw.aggregate.reaction.exception.ReferenceNotFoundException;
import com.hw.aggregate.reaction.model.ReferenceService;
import com.hw.shared.Auditable;
import com.hw.shared.IdGenerator;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;
import java.util.Optional;

@Entity
@Table
@Data
@NoArgsConstructor
public class Comment extends Auditable {
    @Id
    private Long id;
    @Column
    private String content;
    @Column
    private String replyTo;
    @Column
    private String referenceId;

    public static Comment create(String content, String replyTo, String refId, List<ReferenceService> refServices, CommentRepository commentRepository, IdGenerator idGenerator) {
        boolean b = refServices.stream().anyMatch(e -> e.existById(refId));
        if (!b)
            throw new ReferenceNotFoundException();
        return commentRepository.save(new Comment(idGenerator.getId(), content, replyTo, refId));
    }

    private Comment(Long id, String content, String replyTo, String postId) {
        this.id = id;
        this.content = content;
        this.replyTo = replyTo;
        this.referenceId = postId;
    }

    public static void delete(String commentId, String userId, CommentRepository commentRepository) {
        Optional<Comment> byId = commentRepository.findById(Long.parseLong(commentId));
        if (byId.isEmpty())
            throw new CommentNotFoundException();
        if (!byId.get().getCreatedBy().equals(userId))
            throw new CommentAccessException();
        commentRepository.delete(byId.get());
    }

    public static void deleteForAdmin(String commentId, CommentRepository commentRepository) {
        Optional<Comment> byId = commentRepository.findById(Long.parseLong(commentId));
        if (byId.isEmpty())
            throw new CommentNotFoundException();
        commentRepository.delete(byId.get());
    }
}
