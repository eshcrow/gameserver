package org.gameserver.auth.util;

/**
 * created by jiayaoguang at 2018年3月13日
 */
public interface StateBase {
	void Enter();

	void Leave();

	void Process(int msg);
};
