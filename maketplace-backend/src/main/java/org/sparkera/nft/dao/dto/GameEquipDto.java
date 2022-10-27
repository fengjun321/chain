package org.sparkera.nft.dao.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class GameEquipDto implements Serializable {
    private Integer op_code;
    private String t_data;
}
