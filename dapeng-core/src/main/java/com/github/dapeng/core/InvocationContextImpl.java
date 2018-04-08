package com.github.dapeng.core;

import com.github.dapeng.core.enums.CodecProtocol;
import com.github.dapeng.core.enums.LoadBalanceStrategy;
import com.github.dapeng.core.helper.DapengUtil;

import java.util.Optional;

/**
 * 客户端上下文
 *
 * @author craneding
 * @date 15/9/24
 */

public class InvocationContextImpl implements InvocationContext {
    /**
     * 服务名称
     */
    private String serviceName;

    /**
     * 方法名称
     */
    private String methodName;

    /**
     * 版本号
     */
    private String versionName;
    private Optional<String> sessionTid = Optional.empty();
    private String callerTid = DapengUtil.generateTid();
    private Optional<Long> userId = Optional.empty();
    private Optional<String> userIp = Optional.empty();

    private String callerIp;
    private Optional<Integer> callerPort;

    private Optional<Integer> timeout = Optional.empty();

    private Optional<LoadBalanceStrategy> loadBalanceStrategy = Optional.empty();

    private CodecProtocol codecProtocol = CodecProtocol.CompressedBinary;

    private Optional<String> calleeIp = Optional.empty();

    private Optional<Integer> calleePort = Optional.empty();

    private Optional<String> callerMid = Optional.empty();

    private Optional<Long> operatorId = Optional.empty();

    private Optional<Integer> transactionId = Optional.empty();

    private Optional<Integer> transactionSequence = Optional.empty();

    /**
     * 包含服务提供端返回来的一些信息, 例如calleeIp, 服务耗时等信息
     */
    private InvocationInfo lastInvocationInfo;

    @Override
    public String serviceName() {
        return serviceName;
    }

    @Override
    public InvocationContext serviceName(String serviceName) {
        this.serviceName = serviceName;
        return this;
    }

    @Override
    public String methodName() {
        return methodName;
    }

    @Override
    public InvocationContext methodName(String methodName) {
        this.methodName = methodName;
        return this;
    }

    @Override
    public String versionName() {
        return versionName;
    }

    @Override
    public InvocationContext versionName(String versionName) {
        this.versionName = versionName;
        return this;
    }

    @Override
    public Optional<Integer> timeout() {
        return timeout;
    }

    @Override
    public InvocationContext timeout(final Integer timeout) {
        this.timeout = Optional.ofNullable(timeout);
        return this;
    }

    // read/write
    @Override
    public CodecProtocol codecProtocol() {
        return codecProtocol;
    }

    @Override
    public Optional<String> calleeIp() {
        return this.calleeIp;
    }

    @Override
    public InvocationContext calleeIp(String calleeIp) {
        this.calleeIp = Optional.ofNullable(calleeIp);
        return this;
    }

    @Override
    public Optional<Integer> calleePort() {
        return this.calleePort;
    }

    @Override
    public InvocationContext callerIp(String callerIp) {
        this.callerIp = callerIp;
        return this;
    }

    @Override
    public String callerIp() {
        return this.callerIp;
    }

    @Override
    public InvocationContext callerPort(Integer callerPort) {
        this.callerPort = calleePort;
        return this;
    }

    @Override
    public Optional<Integer> callerPort() {
        return this.callerPort;
    }

    @Override
    public InvocationContext loadBalanceStrategy(LoadBalanceStrategy loadBalanceStrategy) {
        this.loadBalanceStrategy = Optional.ofNullable(loadBalanceStrategy);
        return this;
    }

    @Override
    public Optional<LoadBalanceStrategy> loadBalanceStrategy() {
        return loadBalanceStrategy;
    }

    @Override
    public InvocationContext calleePort(Integer calleePort) {
        this.calleePort = Optional.ofNullable(calleePort);
        return this;
    }

    @Override
    public InvocationInfo lastInvocationInfo() {
        return this.lastInvocationInfo;
    }

    @Override
    public InvocationContext transactionId(Integer currentTransactionId) {
        this.transactionId = Optional.ofNullable(currentTransactionId);
        return this;
    }

    @Override
    public InvocationContext transactionSequence(Integer currentTransactionSequence) {
        this.transactionSequence = Optional.ofNullable(currentTransactionSequence);
        return this;
    }

    @Override
    public InvocationContext sessionTid(String sessionTid) {
        this.sessionTid = Optional.ofNullable(sessionTid);
        return this;
    }

    @Override
    public Optional<String> sessionTid() {
        return this.sessionTid;
    }

    @Override
    public InvocationContext callerTid(String callerTid) {
        this.callerTid = callerTid;
        return this;
    }

    @Override
    public String callerTid() {
        return this.callerTid;
    }

    @Override
    public InvocationContext lastInvocationInfo(InvocationInfo lastInvocationInfo) {
        this.lastInvocationInfo = lastInvocationInfo;
        return this;
    }

    @Override
    public InvocationContext operatorId(Long operatorId) {
        this.operatorId = Optional.ofNullable(operatorId);
        return this;
    }

    @Override
    public Optional<Long> operatorId() {
        return this.operatorId;
    }


    @Override
    public InvocationContext callerMid(String callerMid) {
        this.callerMid = Optional.ofNullable(callerMid);
        return this;
    }

    @Override
    public Optional<String> callerMid() {
        return this.callerMid;
    }

    @Override
    public InvocationContext userId(Long userId) {
        this.userId = Optional.ofNullable(userId);
        return this;
    }

    @Override
    public Optional<Long> userId() {
        return userId;
    }

    @Override
    public InvocationContext userIp(String userIp) {
        this.userIp = Optional.ofNullable(userIp);
        return this;
    }

    @Override
    public Optional<String> userIp() {
        return userIp;
    }

    @Override
    public InvocationContext codecProtocol(CodecProtocol codecProtocol) {
        this.codecProtocol = codecProtocol;
        return this;
    }

    public interface InvocationContextProxy {
        Optional<String> sessionTid();

        Optional<String> userIp();
        Optional<Long> userId();
        Optional<Long> operatorId();
        Optional<String> callerMid();
    }

    public static class Factory {
        private static ThreadLocal<InvocationContext> threadLocal = new ThreadLocal<>();

        private static InvocationContextProxy invocationContextProxy;

        public static void setInvocationContextProxy(InvocationContextProxy invocationContextProxy) {
            Factory.invocationContextProxy = invocationContextProxy;
        }

        public static InvocationContextProxy getInvocationContextProxy() {
            return invocationContextProxy;
        }

        /**
         * must be invoked one time per thread before work begin
         *
         * @return
         */
        public static InvocationContext createNewInstance() {
            assert threadLocal.get() == null;

            InvocationContext context = new InvocationContextImpl();

            threadLocal.set(context);
            return context;
        }

        public static InvocationContext getCurrentInstance() {
            InvocationContext context = threadLocal.get();

            //客户端可能不手动创建上下文.
            if (context == null) {
                context = createNewInstance();
            }

            return context;
        }

        /**
         * must be invoked after work done
         *
         * @return
         */
        public static void removeCurrentInstance() {
            threadLocal.remove();
        }
    }


}
