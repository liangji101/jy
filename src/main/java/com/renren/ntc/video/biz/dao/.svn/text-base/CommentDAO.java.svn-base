package com.renren.ntc.video.biz.dao;

import java.util.List;

import net.paoding.rose.jade.annotation.DAO;
import net.paoding.rose.jade.annotation.SQL;

import com.renren.ntc.video.biz.model.CommentInfo;
import com.renren.ntc.video.utils.Constants;

@DAO(catalog = Constants.DB_TOKEN)
public interface CommentDAO {
	String TABLE = "comments";
	String FIELD_PK = "id";
	String FIELDS_WITHOUT_PK = "share_id,comment_id,uid,content,des_uid,create_time";
	String FIELDS_ALL = FIELD_PK + "," + FIELDS_WITHOUT_PK;
	@SQL("insert into " + TABLE + "(" + FIELDS_WITHOUT_PK + ") values(:1.shareId,:1.commentId,:1.uid,:1.content,:1.desUid,:1.createTime)")
	public int save(CommentInfo commentInfo);
	
	@SQL("delete from " + TABLE + " where share_id=:1 and comment_id=:2")
	public int del(long shareId,long commentId);
	
	@SQL("update " + TABLE + " set content=:3 where share_id=:1 and comment_id=:2")
	public int update(long shareId,long commentId,String content);
	
	@SQL("select " + FIELDS_ALL + " from " + TABLE + " where share_id=:1 and comment_id=:2")
	public CommentInfo getCommentInfo(long shareId,long commentId);
	
	@SQL("select " + FIELDS_ALL + " from " + TABLE + " where share_id=:1 order by create_time desc")
	public List<CommentInfo> getAllCommentsReverseByShareId(long shareId);
	
	@SQL("select " + FIELDS_ALL + " from " + TABLE + " where share_id=:1 order by create_time desc limit :2,:3")
	public List<CommentInfo> getCommentsReverseByShareId(long shareId,int offset,int count);
	
	@SQL("select " + FIELDS_ALL + " from " + TABLE + " where share_id=:1 order by create_time asc limit :2,:3")
	public List<CommentInfo> getCommentsByShareId(long shareId,int offset,int count);
}
