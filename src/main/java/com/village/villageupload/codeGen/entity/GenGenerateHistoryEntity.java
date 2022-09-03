package com.village.villageupload.codeGen.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.util.Date;

@Data
@TableName("gen_generate_history")
public class GenGenerateHistoryEntity {

	private String id;
    
	private String configContent;
    
	private String md5Value;
    
	private Date generateTime;
    

}
