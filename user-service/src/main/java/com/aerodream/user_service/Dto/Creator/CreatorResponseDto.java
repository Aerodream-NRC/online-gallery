package com.aerodream.user_service.Dto.Creator;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreatorResponseDto {

    private Long userId;

    private Long creatorId;

    private String firstName;

    private String lastName;

    private Set<Long> subscribers;

    private Set<Long> collectionsId;

    private boolean isReadyForOrder;
}