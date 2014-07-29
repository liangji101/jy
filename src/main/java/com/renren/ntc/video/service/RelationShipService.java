package com.renren.ntc.video.service;

import java.util.List;

public interface RelationShipService {

	// 这里会自动merge 新关注好友的新鲜事
	public abstract boolean addAttention(int uid, int to);

	public abstract boolean delAttention(int uid, int to);

	public abstract int getPassAttentionsCount(int to);

	public abstract int getActiveAttentionsCount(int uid);

	public abstract boolean addPendingAttention(int uid, int to);

	public abstract boolean delPendingAttention(int uid, int to);

	public abstract boolean isPendingAttention(int uid, int to);

	public abstract List<Integer> getActiveAttentions(int uid, int offset,
			int count);

	public abstract List<Integer> getPassAttentions(int to, int offset,
			int count);

	public abstract int getPendingAttentionsCount(int uid);

	public abstract List<Integer> getPendingAttentions(int uid, int offset,
			int count);

	public abstract List<Integer> getPassAttentions(int to);

	public abstract List<Integer> getActiveAttentions(int uid);

	public abstract boolean isAttention(int uid, int to);

	/**
	 * get uid hufeng uids
	 * @param uid
	 * @return
	 */
	public abstract List<Integer> getHuFengUids(int uid);

	public abstract List<Integer> getHuFengUids(int uid, int offset, int count);

	/**
	 * uid_1 and uid_2 is fans of each other?
	 * @param uid_1
	 * @param uid_2
	 * @return
	 */
	public abstract boolean isFans2EachOther(int uid1, int uid2);

	/**
	 * uid is fans of target?
	 * @param uid
	 * @param target
	 * @return
	 */
	public abstract boolean isFans(int uid, int target);

}