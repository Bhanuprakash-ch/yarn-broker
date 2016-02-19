/**
 * Copyright (c) 2015 Intel Corporation
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.trustedanalytics.servicebroker.yarn.config.store;

import java.io.IOException;

import org.cloudfoundry.community.servicebroker.model.CreateServiceInstanceBindingRequest;
import org.cloudfoundry.community.servicebroker.model.ServiceInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.trustedanalytics.cfbroker.store.api.BrokerStore;
import org.trustedanalytics.cfbroker.store.serialization.RepositoryDeserializer;
import org.trustedanalytics.cfbroker.store.serialization.RepositorySerializer;
import org.trustedanalytics.cfbroker.store.zookeeper.service.ZookeeperClient;
import org.trustedanalytics.cfbroker.store.zookeeper.service.ZookeeperStore;
import org.trustedanalytics.servicebroker.yarn.config.Qualifiers;
import org.trustedanalytics.servicebroker.yarn.config.kerberos.KerberosProperties;

@Configuration
public class BrokerStoreConfig {

  @Autowired
  private KerberosProperties kerberosProperties;

  @Autowired
  @Qualifier(Qualifiers.SERVICE_INSTANCE)
  private RepositorySerializer<ServiceInstance> instanceSerializer;

  @Autowired
  @Qualifier(Qualifiers.SERVICE_INSTANCE)
  private RepositoryDeserializer<ServiceInstance> instanceDeserializer;

  @Autowired
  @Qualifier(Qualifiers.SERVICE_INSTANCE_BINDING)
  private RepositorySerializer<CreateServiceInstanceBindingRequest> bindingSerializer;

  @Autowired
  @Qualifier(Qualifiers.SERVICE_INSTANCE_BINDING)
  private RepositoryDeserializer<CreateServiceInstanceBindingRequest> bindingDeserializer;

  @Bean
  @Qualifier(Qualifiers.SERVICE_INSTANCE)
  public BrokerStore<ServiceInstance> getServiceInstanceStore(ZookeeperClient zkClient)
      throws IOException {
    return new ZookeeperStore<>(zkClient, instanceSerializer, instanceDeserializer);
  }

  @Bean
  @Qualifier(Qualifiers.SERVICE_INSTANCE_BINDING)
  public BrokerStore<CreateServiceInstanceBindingRequest> getServiceInstanceBindingStore(
      ZookeeperClient zkClient) throws IOException {
    return new ZookeeperStore<>(zkClient, bindingSerializer, bindingDeserializer);
  }
}
