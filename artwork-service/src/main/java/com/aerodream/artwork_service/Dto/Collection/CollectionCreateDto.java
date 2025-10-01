package com.aerodream.artwork_service.Dto.Collection;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CollectionCreateDto {

    private Long creatorId;

    private String title;

    private String description;

}