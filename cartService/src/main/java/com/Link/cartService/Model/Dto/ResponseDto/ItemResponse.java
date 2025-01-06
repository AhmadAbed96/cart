package com.Link.cartService.Model.Dto.ResponseDto;

import com.Link.cartService.Model.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ItemResponse {


    private String sectionId;
    private String name;
    private Status status;
    private List<Size> sizes;
    private String description;
    private LocalDate createdAt;
    private LocalDate updatedDate;
    private LocalDate deletedAt;

}