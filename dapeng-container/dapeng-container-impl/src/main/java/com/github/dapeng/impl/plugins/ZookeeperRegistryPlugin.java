package com.github.dapeng.impl.plugins;


import com.github.dapeng.api.AppListener;
import com.github.dapeng.api.Container;
import com.github.dapeng.api.ContainerFactory;
import com.github.dapeng.api.Plugin;
import com.github.dapeng.api.events.AppEvent;
import com.github.dapeng.core.ServiceInfo;
import com.github.dapeng.impl.container.DapengApplication;
import com.github.dapeng.registry.RegistryAgent;
import com.github.dapeng.registry.RegistryAgentProxy;
import com.github.dapeng.registry.ZkNodeConfigContext;
import com.github.dapeng.registry.zookeeper.RegistryAgentImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class ZookeeperRegistryPlugin implements AppListener, Plugin {
    private static final Logger LOGGER = LoggerFactory.getLogger(ZookeeperRegistryPlugin.class);

    private final Container container;
    private final RegistryAgent registryAgent = new RegistryAgentImpl(false);

    public ZookeeperRegistryPlugin(Container container) {
        this.container = container;
        container.registerAppListener(this);
    }

    @Override
    public void appRegistered(AppEvent event) {
        LOGGER.info(getClass().getSimpleName() + "::appRegistered AppEvent[" + event.getSource() + "]");
        DapengApplication application = (DapengApplication) event.getSource();
        //TODO: zookeeper注册是否允许部分失败？ 对于整个应用来说应该要保证完整性吧
        application.getServiceInfos().forEach(serviceInfo ->
                registerService(serviceInfo.serviceName, serviceInfo.version)

        );

        // Monitor ZK's config properties for service
    }

    @Override
    public void appUnRegistered(AppEvent event) {
        LOGGER.info(getClass().getSimpleName() + "::appUnRegistered AppEvent[" + event.getSource() + "]");
        DapengApplication application = (DapengApplication) event.getSource();
        application.getServiceInfos().forEach(serviceInfo ->
                unRegisterService(serviceInfo.serviceName, serviceInfo.version)
        );
    }

    @Override
    public void start() {
        LOGGER.warn("Plugin::" + getClass().getSimpleName() + "::start");
        /**
         * set RegistryAgentImpl ,SoaServerHandler 会用到
         */
        RegistryAgentProxy.setCurrentInstance(RegistryAgentProxy.Type.Server, registryAgent);

        registryAgent.setProcessorMap(ContainerFactory.getContainer().getServiceProcessors());
        registryAgent.start();

        container.getApplications().forEach(app -> {
            List<ServiceInfo> serviceInfos = app.getServiceInfos();
            serviceInfos.forEach(serviceInfo -> {
                registerService(serviceInfo.serviceName, serviceInfo.version);
            });
        });
    }

    @Override
    public void stop() {
        LOGGER.warn("Plugin::" + getClass().getSimpleName() + "::stop");
        container.getApplications().forEach(app -> {
            app.getServiceInfos()
                    .forEach(s -> unRegisterService(s.serviceName, s.version));
        });
        registryAgent.stop();
    }

    public void registerService(String serviceName, String version) {
        LOGGER.info(getClass().getSimpleName() + "::appRegistered [serviceName:" + serviceName + ", version:" + version + "]");
        registryAgent.registerService(serviceName, version);
    }

    public void unRegisterService(String serviceName, String version) {
        LOGGER.info(getClass().getSimpleName() + "::unRegisterService [serviceName:" + serviceName + ", version:" + version + "]");
        // TODO do something real?
    }

}
