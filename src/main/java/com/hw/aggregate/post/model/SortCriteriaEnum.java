package com.hw.aggregate.post.model;

import com.hw.shared.BadRequestException;

public enum  SortCriteriaEnum {
    ID("id");
    private String sortCriteria;

    SortCriteriaEnum(String sortCriteria) {
        this.sortCriteria = sortCriteria;
    }

    public String getSortCriteria() {
        return this.sortCriteria;
    }

    public static SortCriteriaEnum fromString(String text) {
        for (SortCriteriaEnum b : SortCriteriaEnum.values()) {
            if (b.sortCriteria.equalsIgnoreCase(text)) {
                return b;
            }
        }
        throw new BadRequestException("No constant with text " + text + " found");
    }
}
