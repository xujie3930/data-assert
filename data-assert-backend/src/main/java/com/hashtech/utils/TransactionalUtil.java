package com.hashtech.utils;

import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

/**
 * 手动事务类
 * 该类当前还不可用
 * @author  maoc
 * @create  2022/6/7 11:52
 * @desc
 **/
public class TransactionalUtil {

    private DataSourceTransactionManager transactionManager;
    private static final TransactionalUtil instance = new TransactionalUtil();
    private TransactionalUtil(){}
    public static TransactionalUtil getInstance(){
        return instance;
    }

    public TransactionStatus newTransactionalStatus(){
        return this.newTransactionalStatus(TransactionDefinition.ISOLATION_READ_COMMITTED, TransactionDefinition.PROPAGATION_REQUIRES_NEW);
    }

    public TransactionStatus newTransactionalStatus(int isolationLevel, int propagationBebavior){

        DefaultTransactionDefinition definition = new DefaultTransactionDefinition();
        definition.setIsolationLevel(isolationLevel);
        definition.setPropagationBehavior(propagationBebavior);
        return transactionManager.getTransaction(definition);
    }

    public void commit(TransactionStatus status){
        transactionManager.commit(status);
    }

    public void rollback(TransactionStatus status){
        transactionManager.rollback(status);
    }
}
