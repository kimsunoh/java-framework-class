package kr.ac.jejuuniv;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

public class TransactionAdvice implements MethodInterceptor{
	private PlatformTransactionManager transactionManager;

	@Override
	public Object invoke(MethodInvocation invocation) throws Throwable  {
		Object object = null;
		TransactionStatus status = getTransactionManager().getTransaction(new DefaultTransactionDefinition());
		try {
			object = invocation.proceed();
			getTransactionManager().commit(status);
		} catch (Exception e) {
			getTransactionManager().rollback(status);
			throw e;
		}
		return object;
	}

	public PlatformTransactionManager getTransactionManager() {
		return transactionManager;
	}

	public void setTransactionManager(PlatformTransactionManager transactionManager) {
		this.transactionManager = transactionManager;
	}
}
