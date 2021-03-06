package com.isuwang.soa.settle;

      import com.github.dapeng.core.*;
      import com.github.dapeng.org.apache.thrift.*;
      import java.util.concurrent.CompletableFuture;
      import java.util.concurrent.Future;
      import java.util.ServiceLoader;
      import com.isuwang.soa.settle.SettleServiceAsyncCodec.*;
      import com.isuwang.soa.settle.service.SettleServiceAsync;

      /**
       * Autogenerated by Dapeng-Code-Generator (2.0.0)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated

      **/
      public class SettleServiceAsyncClient implements SettleServiceAsync{
      private final String serviceName;
      private final String version;

      private SoaConnectionPool pool;

      public SettleServiceAsyncClient() {
        this.serviceName = "com.isuwang.soa.settle.service.SettleService";
        this.version = "1.0.0";

        ServiceLoader<SoaConnectionPoolFactory> factories = ServiceLoader.load(SoaConnectionPoolFactory.class);
        for (SoaConnectionPoolFactory factory: factories) {
          this.pool = factory.getPool();
          break;
        }
        this.pool.registerClientInfo(serviceName,version);
      }

      
          
            /**
            * 
            **/
            
              public CompletableFuture<Void> createSettle(com.isuwang.soa.settle.domain.Settle settle) throws SoaException{

              String methodName = "createSettle";
              createSettle_args createSettle_args = new createSettle_args();
              createSettle_args.setSettle(settle);
                

              CompletableFuture<createSettle_result> response = (CompletableFuture<createSettle_result>) pool.sendAsync(serviceName,version,"createSettle",createSettle_args, new CreateSettle_argsSerializer(), new CreateSettle_resultSerializer());

              
                  return response.thenApply((createSettle_result result )->  null);
                
            }
            
          

        
          
            /**
            * 
            **/
            
              public CompletableFuture<com.isuwang.soa.settle.domain.Settle> getSettleById(Integer settleId) throws SoaException{

              String methodName = "getSettleById";
              getSettleById_args getSettleById_args = new getSettleById_args();
              getSettleById_args.setSettleId(settleId);
                

              CompletableFuture<getSettleById_result> response = (CompletableFuture<getSettleById_result>) pool.sendAsync(serviceName,version,"getSettleById",getSettleById_args, new GetSettleById_argsSerializer(), new GetSettleById_resultSerializer());

              
                  
                      return response.thenApply((getSettleById_result result )->  result.getSuccess());
                    
                
            }
            
          

        

      /**
      * getServiceMetadata
      **/
      public String getServiceMetadata() throws SoaException {
        String methodName = "getServiceMetadata";
        getServiceMetadata_args getServiceMetadata_args = new getServiceMetadata_args();
        getServiceMetadata_result response = pool.send(serviceName,version,methodName,getServiceMetadata_args, new GetServiceMetadata_argsSerializer(), new GetServiceMetadata_resultSerializer());
        return response.getSuccess();
      }

    }
    