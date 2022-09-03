package com.village.villageupload.file.entity;

import lombok.Data;

@Data
public class ChunkEntity extends Aaa {

    /**
     * 当前块的次序
     */
    private Integer chunkNumber;
    /**
     * 分块大小
     */
    private Integer chunkSize;
    /**
     * 当前块的大小，实际大小
     */
    private Integer currentChunkSize;
    /**
     * 文件总大小
     */
    private Double totalSize;
    /**
     * 这个就是每个文件的MD5唯一标示
     */
    private String identifier;
    /**
     * 文件名
     */
    private String filename;
    /**
     * 相对路径
     */
    private String relativePath;
    /**
     * 文件被分成块的总数
     */
    private Integer totalChunks;

    private String parentId;

    public String getIdentifier() {
        return this.identifier.substring(0,this.identifier.indexOf("$$$"));
    }

    public String getParentId() {
        return this.identifier.substring(this.identifier.indexOf("$$$")+3);
    }
}
