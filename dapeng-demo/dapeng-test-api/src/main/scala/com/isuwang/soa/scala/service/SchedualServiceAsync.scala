
        package com.github.dapeng.soa.scala.service

import com.github.dapeng.core.{Processor, Service}
        import com.github.dapeng.core.SoaGlobalTransactional
        import scala.concurrent.Future

        /**
         * Autogenerated by Dapeng-Code-Generator (1.2.2)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated

        * 
        **/
        @Service(name ="com.github.dapeng.soa.service.SchedualService" , version = "1.0.0")
        @Processor(className = "com.github.dapeng.soa.scala.SchedualServiceAsyncCodec$Processor")
        trait SchedualServiceAsync {
        
            /**
            * 
            **/
            
            @throws[com.github.dapeng.core.SoaException]
            def test(
             timeout : Long): Future[String]

          
      }
      