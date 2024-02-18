package com.panjuak.maintanance.entities;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Builder
@Setter
@Getter
public class FcmPayload<T> {
    private String title;
    private String body;
    private T data;
}
