package com.postman.aftership.entity;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * Created by antonsakhno on 19.02.17.
 */
@Data
@Accessors(chain = true)
public class ASMultiTrackResponce {
    private ASMeta meta;
    private ASMultiTrackData data;
}
