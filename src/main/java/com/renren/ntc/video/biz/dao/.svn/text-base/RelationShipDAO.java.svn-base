package com.renren.ntc.video.biz.dao;

import java.util.List;

import net.paoding.rose.jade.annotation.DAO;
import net.paoding.rose.jade.annotation.SQL;

import com.renren.ntc.video.utils.Constants;

@DAO(catalog = Constants.DB_SHARE)
public interface RelationShipDAO {
	 final static String FIELDS = "uid,`to`,create_time";
	 final static String SELECT_FIELDS = "uid,`to`";
	 final static String TABLE_ACTIVEATTENTIONS = "activeAttentions";
	 final static String TABLE_PASSATTENTIONS = "passAttentions";
	 final static String TABLE_PENDINGATTENTIONS = "pendingAttentions";

	@SQL("insert ignore into " + TABLE_ACTIVEATTENTIONS + " ("+  SELECT_FIELDS +") values (:1,:2)")
	public void addActiveAttention(int uid, int to);

	@SQL("insert ignore into " + TABLE_PASSATTENTIONS + " ("+  SELECT_FIELDS +") values (:1,:2)")
	public void addPassAttention(int uid, int to);

	@SQL("delete from " + TABLE_ACTIVEATTENTIONS + " where uid=:1 and `to`=:2")
	public void delActiveAttention(int uid, int to);

	@SQL("delete from " + TABLE_PASSATTENTIONS + " where uid=:1 and `to`=:2")
	public void delPassAttention(int uid, int to);

	@SQL("select count(uid) from " + TABLE_PASSATTENTIONS + " where `to` =:1")
	public int getPassAttentionsCount(int to);

	@SQL("select count(`to`) from " + TABLE_ACTIVEATTENTIONS + " where uid =:1")
	public int getActiveAttentionsCount(int uid);

	@SQL("insert ignore into " + TABLE_PENDINGATTENTIONS + " ("+  SELECT_FIELDS +") values (:1,:2)")
	public boolean addPendingAttention(int uid, int to);

	@SQL("delete from " + TABLE_PENDINGATTENTIONS + " where uid=:1 and `to`=:2")
	public void delPendingAttention(int uid, int to);

	@SQL("select count(uid) from " + TABLE_PENDINGATTENTIONS + " where uid =:1 and `to`=:2")
	public int isPendingAttention(int uid, int to);

	@SQL("select `to` from "+ TABLE_ACTIVEATTENTIONS + " where uid =:1 limit :2,:3 order by create_time")
	public List<Integer> getActiveAttentions(int uid, int offset, int count);

	@SQL("select uid from "+ TABLE_PASSATTENTIONS + " where `to` =:1 limit :2,:3 by create_time")
	public List<Integer> getPassAttentions(int to, int offset, int count);

	@SQL("select count(`to`) from " + TABLE_PENDINGATTENTIONS + " where uid =:1")
	public int getPendingAttentionsCount(int uid);

	@SQL("select `to` from " + TABLE_PENDINGATTENTIONS + " where uid =:1 limit :2,:3 by create_time")
	public List<Integer> getPendingAttentions(int uid, int offset, int count);

	@SQL("select uid from " + TABLE_PASSATTENTIONS + " where `to`=:1")
	public List<Integer> getPassAttentions(int uid);

	@SQL("select `to` from " + TABLE_ACTIVEATTENTIONS + " where uid=:1 ")
	public List<Integer> getActiveAttentions(int uid);

	@SQL("select count(`to`) from " + TABLE_PASSATTENTIONS + " where uid=:1 and `to`=:2") 
	public int isPassAttention(int uid, int to);

	@SQL("select count(`to`) from " + TABLE_ACTIVEATTENTIONS + " where uid=:1 and `to`=:2")
	public int isActiveAttention(int uid, int to);

}
