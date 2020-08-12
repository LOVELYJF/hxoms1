package com.hxoms.modules.leaderSupervision.entity;

import com.hxoms.common.hxannotation.ColumnAnnotation;
import com.hxoms.common.hxannotation.IdAnnotation;
import com.hxoms.common.hxannotation.TableAnnotation;

@TableAnnotation(TableName = "attachment_askforjiwei", TableDescription="附件和征求纪委意见的中间表")
public class AttachmentAskforjiwei {
    @IdAnnotation
    @ColumnAnnotation(FieldName = "id",   FieldDescription="")
    private String id;

    @ColumnAnnotation(FieldName = "attachmentId",   FieldDescription="附件Id")
    private String attachmentid;

    @ColumnAnnotation(FieldName = "leader_batchId",   FieldDescription="业务Id")
    private String leaderBatchId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getAttachmentid() {
        return attachmentid;
    }

    public void setAttachmentid(String attachmentid) {
        this.attachmentid = attachmentid == null ? null : attachmentid.trim();
    }

    public String getLeaderBatchId() {
        return leaderBatchId;
    }

    public void setLeaderBatchId(String leaderBatchId) {
        this.leaderBatchId = leaderBatchId;
    }
}