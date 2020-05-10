package com.hw.aggregate.reaction.command;

import com.hw.aggregate.reaction.model.CommonReaction;
import com.hw.aggregate.reaction.model.ReactionEnum;
import com.hw.aggregate.reaction.model.ReferenceEnum;
import lombok.Data;

@Data
public class AddLikeCommentCommand extends CommonReaction {

    public AddLikeCommentCommand(String userId, String commentId) {
        super(userId, commentId, ReactionEnum.LIKE, ReferenceEnum.COMMENT);
    }
}
