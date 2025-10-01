package com.aerodream.user_service.Dto.Creator;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CreatorUpdateDto {

    private Long id;

    private boolean isReadyForOrder;
}