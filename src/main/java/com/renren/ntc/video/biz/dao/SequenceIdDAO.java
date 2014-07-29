package com.renren.ntc.video.biz.dao;


import net.paoding.rose.jade.annotation.DAO;
import net.paoding.rose.jade.annotation.SQL;
import net.paoding.rose.jade.annotation.SQLParam;

@DAO(catalog ="idseq")
public interface SequenceIdDAO {
        // select nextval('send_inv_id_seq'::text)
        @SQL("select nextval('##(:name)'::text)")
        public int getSendInvNextId(@SQLParam("name") String name);
}